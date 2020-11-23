package tsi.too.lucasfonseca.dataaccess.utils;

import java.io.IOException;

import tsi.too.lucasfonseca.dataaccess.api.IStorage;
import tsi.too.lucasfonseca.dataaccess.api.MyComparable;
import tsi.too.lucasfonseca.dataaccess.impl.dbstorage.DatabaseStorage;
import tsi.too.lucasfonseca.dataaccess.impl.filestorage.FileStorage;

/**
 * Convenience class for opening connection with IStore.
 * 
 * @author Lucas Cristovam
 *
 */
public final class Connection {

	private Connection() {
	}

	public static <E extends MyComparable<E>> FileStorage<E> openConnection(String filename, Class<E> clazz)
			throws IOException {
		return new FileStorage<>(clazz, filename);
	}

	@SuppressWarnings("unused")
	public static <E extends Comparable<E>> IStorage<E> openConnection(String dbUrl, String username, String password)
			throws IOException {
		return new DatabaseStorage<>(dbUrl, username, password);
	}
}