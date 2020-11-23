package tsi.too.lucasfonseca.dataaccess.impl.filestorage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

import tsi.too.lucasfonseca.dataaccess.api.IStorage;
import tsi.too.lucasfonseca.dataaccess.api.MyComparable;

public class FileStorage<E extends MyComparable<E>> implements IStorage<E> {

	private final SerializableFile<E> storage;

	public FileStorage(Class<E> objectType, String filename) throws IOException {
		storage = new SerializableFile<>(objectType, filename);
		storage.create();
		storage.openForRead();
	}

	@Override
	public void insert(E newObject) throws IOException, IllegalArgumentException {
		if (find(newObject))
			throw new IllegalArgumentException(String.format("object already present in database"));

		storage.write(newObject);
	}

	@Override
	public boolean find(E similar) throws IOException {
		try {
			return storage.fetch(obj -> similar.compareTo(obj) == 0).isPresent();
		} catch (ClassNotFoundException e) {
			throw new IOException("Inconsistent storage");
		}
	}

	@Override
	public void update(E updatedObject) throws IOException, IllegalArgumentException {
		List<E> storedItems = allAsList();

		IntStream.range(0, storedItems.size()).filter(i -> storedItems.get(i).compareTo(updatedObject) == 0).findFirst()
				.ifPresent(i -> storedItems.set(i, updatedObject));

		if (!storedItems.contains(updatedObject)) {
			throw new IllegalArgumentException("Target not found in storage");
		}

		storage.clear();
		storage.write(storedItems);
	}

	@Override
	public Optional<E> get(E similar) throws IOException {
		try {
			return storage.fetch(obj -> similar.compareTo(obj) == 0);
		} catch (ClassNotFoundException e) {
			throw new IOException("Inconsistent storage");
		}
	}

	@Override
	public void delete(E similar) throws IOException {
		List<E> storedItems = allAsList();

		var status = new Object() {
			boolean found = false;
		};

		IntStream.range(0, storedItems.size()).filter(i -> storedItems.get(i).compareTo(similar) == 0).findFirst()
				.ifPresent(i -> {
					storedItems.remove(i);
					status.found = true;
				});

		if (!status.found) {
			throw new IllegalArgumentException("Target not present in storage");
		}

		storage.clear();
		storage.write(storedItems);
	}

	@Override
	public long count() throws IOException {
		return allAsList().size();
	}

	@Override
	public Iterable<E> all() throws IOException {
		try {
			return storage.readFile();
		} catch (FileNotFoundException e) {
			throw new IOException("Failed to fetch data");
		} catch (ClassNotFoundException e) {
			throw new IOException("Inconsistent storage");
		}
	}

	@Override
	public void disconnect() throws IOException {
		storage.close();
	}

	private List<E> allAsList() throws IOException {
		return StreamSupport.stream(all().spliterator(), false).collect(Collectors.toList());
	}
}
