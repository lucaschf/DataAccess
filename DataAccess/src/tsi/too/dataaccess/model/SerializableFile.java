package tsi.too.dataaccess.model;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;

public class SerializableFile<E extends Serializable> {
	private ObjectInputStream objectInputStream;
	private ObjectOutputStream objectOutputStream;

	private Class<E> clazz;

	public SerializableFile(Class<E> clazz) {
		super();
		Objects.requireNonNull(clazz);

		this.clazz = clazz;
	}

	public void openInReadMode(String fileName) throws FileNotFoundException, IOException {
		objectInputStream = new ObjectInputStream(new FileInputStream(fileName));
	}

	public void openInWriteMode(String fileName) throws FileNotFoundException, IOException {
		objectOutputStream = new ObjectOutputStream(new FileOutputStream(fileName));
	}

	public void close() throws IOException {
		if (objectInputStream != null)
			objectInputStream.close();

		if (objectOutputStream != null)
			objectOutputStream.close();
	}

	public void write(E e) throws IOException {
		objectOutputStream.writeObject(e);
	}

	public Optional<E> read() throws IOException, ClassNotFoundException, InvalidClassException {
		try {
			var element = objectInputStream.readObject();

			if (clazz.isInstance(element))
				return Optional.ofNullable(clazz.cast(element));
		} catch (EOFException e) {
		}

		return Optional.ofNullable(null);
	}

	public void closeFile() throws IOException {
		if (objectInputStream != null)
			objectInputStream.close();

		if (objectOutputStream != null)
			objectOutputStream.close();
	}
}