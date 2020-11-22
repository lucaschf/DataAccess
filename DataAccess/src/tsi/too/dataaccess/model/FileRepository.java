package tsi.too.dataaccess.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class FileRepository<E extends MyComparable<E>> implements Dao<E>, AutoCloseable {

	SerializableFile<E> file;

	private String fileName;

	public FileRepository(Class<E> clazz, String fileName) {
		super();
		this.fileName = fileName;
		this.file = new SerializableFile<>(clazz);
	}

	@Override
	public void connect() throws DataAccessObjectException {
		try {
			file.openInWriteMode(fileName);
			file.openInReadMode(fileName);
		} catch (IOException ex) {
			throw new DataAccessObjectException(
					String.format("Failed to connect to %s data base.\n%s", fileName, ex.getMessage()));
		}
	}

	@Override
	public void disconnect() throws DataAccessObjectException {
		try {
			file.close();
		} catch (IOException ex) {
			throw new DataAccessObjectException(
					String.format("Failed to disconnect from %s data base.\n%s", fileName, ex.getMessage()));
		}
	}

	@Override
	public void insert(E e) throws DataAccessObjectException {
		try {
			file.write(e);
		} catch (IOException ex) {
			throw new DataAccessObjectException(ex);
		}
	}

	@Override
	public void update(E e) throws DataAccessObjectException {
		
	}

	@Override
	public void delete(E e) throws DataAccessObjectException {
	}

	@Override
	public Optional<E> get(E e) throws DataAccessObjectException {
		Optional<E> target;

		try {
			while ((target = file.read()).isPresent()) {
				if (e.compareTo(target.get()) == 0) {
					return Optional.of(e);
				}
			}
		} catch (ClassNotFoundException | IOException ex) {
			throw new DataAccessObjectException(ex);
		}

		return Optional.ofNullable(null);
	}

	@Override
	public List<E> list() throws DataAccessObjectException {
		var all = new ArrayList<E>();

		Optional<E> target;

		try {
			while ((target = file.read()).isPresent())
				all.add(target.get());
		} catch (ClassNotFoundException | IOException ex) {
			throw new DataAccessObjectException(ex);
		}

		return all;
	}

	@Override
	public long count() throws DataAccessObjectException {
		return list().size();
	}
}
