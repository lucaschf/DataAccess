package tsi.too.lucasfonseca.dataaccess.api;

import java.io.IOException;
import java.util.Optional;

public interface IStorage<E> {

	void insert(E newObject) throws IOException;

	boolean find(E similar) throws IOException;

	void update(E updatedObject) throws IOException;

	Optional<E> get(E similar) throws IOException;

	void delete( E similar) throws IOException;

	long count() throws IOException;

	Iterable<E> all() throws IOException;

	void disconnect() throws IOException;
}