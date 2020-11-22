package tsi.too.lucasfonseca.dataaccess.utils;

import java.io.IOException;

import tsi.too.lucasfonseca.dataaccess.api.IStorage;
import tsi.too.lucasfonseca.dataaccess.api.MyComparable;
import tsi.too.lucasfonseca.dataaccess.impl.dbstorage.DatabaseStorage;
import tsi.too.lucasfonseca.dataaccess.impl.filestorage.FileStorage;

public final class Connection {

	private Connection() {
	};

	public static <E extends MyComparable<E>> FileStorage<E> openConnection(String filename, Class<E> clazz)
			throws IOException {
		return new FileStorage<E>(clazz, filename);
	}

	public static <E extends Comparable<E>> IStorage<E> openConnection(String dbUrl, String username, String password)
			throws IOException {
		return new DatabaseStorage<E>(dbUrl, username, password);
	}
}