package tsi.too.lucasfonseca.dataaccess.impl.filestorage;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

public class SerializableFile<E extends Serializable> {
	private ObjectInputStream objectInputStream;
	private ObjectOutputStream objectOutputStream;

	/**
	 * Specifies what type of object we are working with. It is used to retrieve a
	 * record from the stream.
	 */
	private final Class<E> clazz;

	private final String fileName;

	public SerializableFile(Class<E> clazz, String fileName) {
		super();

		Objects.requireNonNull(clazz);

		this.fileName = fileName;
		this.clazz = clazz;
	}

	/**
	 * opens a file for read.
	 * 
	 * @throws FileNotFoundException if the file does not exist, is a directory
	 *                               rather than a regular file, or for some other
	 *                               reason cannot be opened for reading.
	 * 
	 * @throws IOException           if an I/O error occurs.
	 */
	public void openForRead() throws FileNotFoundException, IOException {
		objectInputStream = new ObjectInputStream(new FileInputStream(fileName));
	}

	/**
	 * creates a file for write.
	 * 
	 * @throws FileNotFoundException if the file does not exist, is a directory
	 *                               rather than a regular file, or for some other
	 *                               reason cannot be opened for reading.
	 * 
	 * @throws IOException           if an I/O error occurs.
	 */
	public void create() throws FileNotFoundException, IOException {
		objectOutputStream = new ObjectOutputStream(new FileOutputStream(fileName));
	}

	/**
	 * Closes both the streams for read and write.
	 * 
	 * @throws IOException if an I/O error occurs.
	 */
	public void close() throws IOException {
		closeOutputStream();
		closeInputStream();
	}

	public void write(List<E> elements) throws IOException {
		for (E e : elements)
			write(e);
	}

	/**
	 * Writes an element in file.
	 * 
	 * @param e the element to be written.
	 * @throws IOException if an I/O error occurs.
	 */
	public void write(E e) throws IOException {
		objectOutputStream.writeObject(e);
	}

	/**
	 * reads a record from file.
	 * 
	 * @return an {@link Optional} containing the read element or null if an
	 *         exception occurs.
	 * @throws IOException            if an I/O error occurs.
	 * @throws ClassNotFoundException if class of a serialized object cannot be
	 *                                found.
	 */
	public Optional<E> read() throws IOException, ClassNotFoundException {
		try {
			var element = objectInputStream.readObject();

			if (clazz.isInstance(element))
				return Optional.of(clazz.cast(element));
		} catch (EOFException ignored) {
		}

		return Optional.empty();
	}

	/**
	 * Fetch a object based on a{@link Predicate}.
	 * 
	 * @param predicate the {@link Predicate} for item test.
	 * @return a {@link Optional} containing: object if found or null if not found.
	 * @throws IOException            if an I/O error occurs.
	 * @throws ClassNotFoundException if class of a serialized object cannot be
	 *                                found.
	 */
	public Optional<E> fetch(Predicate<E> predicate) throws IOException, ClassNotFoundException {
		resetInputStream();

		Optional<E> target;

		while ((target = read()).isPresent()) {
			if (predicate.test(target.get())) {
				return target;
			}
		}

		return Optional.empty();
	}

	/**
	 * Reads all file as a list.
	 *
	 * @return the resulting list.
	 * @throws FileNotFoundException  if the file
	 * @throws IOException            if an I/O error occurs.
	 * @throws ClassNotFoundException if class of a serialized object cannot be
	 *                                found.
	 */
	public List<E> readFile() throws FileNotFoundException, IOException, ClassNotFoundException {
		var items = new ArrayList<E>();

		resetInputStream();
		Optional<E> read;

		while ((read = read()).isPresent()) {
			items.add(read.get());
		}

		return items;
	}

	/**
	 * closes and reopens the input stream to force to read from the beginning.
	 * 
	 * @throws IOException if an I/O error occurs.
	 */
	private void resetInputStream() throws IOException {
		try {
			closeInputStream();
			openForRead();
		} catch (Exception e) {
			throw new IOException(e);
		}
	}

	/**
	 * Closes the inputStream.
	 * 
	 * @throws IOException if an I/O error occurs.
	 */
	private void closeOutputStream() throws IOException {
		if (objectOutputStream != null)
			objectOutputStream.close();
	}

	/**
	 * Closes the outputStream.
	 * 
	 * @throws IOException if an I/O error occurs.
	 */
	private void closeInputStream() throws IOException {
		if (objectInputStream != null)
			objectInputStream.close();
	}

	/**
	 * delete all contents of the file.
	 * 
	 * @throws IOException if an I/O error occurs.
	 */
	public void clear() throws IOException {
		create();
	}
}