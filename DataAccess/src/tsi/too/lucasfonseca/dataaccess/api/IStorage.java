package tsi.too.lucasfonseca.dataaccess.api;

import java.io.IOException;
import java.util.Optional;

/**
 * Abstraction layer for data access. Offers the basic operations of CRUD.
 * 
 * @author Lucas Cristovam
 *
 * @param <E> the type of object.
 */
public interface IStorage<E> {

	/**
	 * Inserts a new object in the data base.
	 * 
	 * @param newObject the object to be inserted.
	 * @throws IOException if an error occurs.
	 */
	void insert(E newObject) throws IOException;

	/**
	 * checks if an item exists in the database.
	 * 
	 * @param similar the item to be checked.
	 * @return whether the item exists or not in the database
	 * @throws IOException if an error occurs.
	 */
	boolean find(E similar) throws IOException;

	/**
	 * Updates an item in the database.
	 * 
	 * @param updatedObject the updated data.
	 * @throws IOException if an error occurs.
	 */
	void update(E updatedObject) throws IOException;

	/**
	 * Retrieves an item from database based on another.
	 * 
	 * @param similar the base item.
	 * @return an {@link Optional} containing the found item or null if not found.
	 * @throws IOException if an error occurs.
	 */
	Optional<E> get(E similar) throws IOException;

	/**
	 * Deletes an item from database.
	 * 
	 * @param similar the base item to be deleted.
	 * @throws IOException if an error occurs.
	 */
	void delete(E similar) throws IOException;

	/**
	 * Count the number of records in database.
	 * 
	 * @return the number of records.
	 * @throws IOException if an error occurs.
	 */
	long count() throws IOException;

	/**
	 * @return all items in database.
	 * @throws IOException if an error occurs.
	 */
	Iterable<E> all() throws IOException;

	/**
	 * Closes the connection to the database
	 * 
	 * @throws IOException if an error occurs.
	 */
	void disconnect() throws IOException;
}