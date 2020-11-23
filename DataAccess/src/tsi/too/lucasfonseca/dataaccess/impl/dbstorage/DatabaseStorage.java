package tsi.too.lucasfonseca.dataaccess.impl.dbstorage;

import java.io.IOException;
import java.util.Optional;

import tsi.too.lucasfonseca.dataaccess.api.IStorage;

public class DatabaseStorage<E extends Comparable<E>> implements IStorage<E> {

	public DatabaseStorage(String dbUrl, String username, String password) throws IOException {
		throw new IOException("Database connection protocol not implemented");
	}

	@Override
	public void insert(E newObject) throws IOException {
		throw new RuntimeException("not implemented");
	}

	@Override
	public boolean find(E similar) throws IOException {
		throw new RuntimeException("not implemented");
	}

	@Override
	public void update(E updatedObject) throws IOException {
		throw new RuntimeException("not implemented");
	}

	@Override
	public Optional<E> get(E similar) throws IOException {
		throw new RuntimeException("not implemented");
	}

	@Override
	public void delete(E target) throws IOException {
		throw new RuntimeException("not implemented");
	}

	@Override
	public long count() throws IOException {
		throw new RuntimeException("not implemented");
	}

	@Override
	public Iterable<E> all() throws IOException {
		throw new RuntimeException("not implemented");
	}

	@Override
	public void disconnect() throws IOException {
		throw new RuntimeException("not implemented");
	}
}
