package tsi.too.dataaccess.model;

import java.util.List;
import java.util.Optional;

interface Dao<E extends MyComparable<E>> {

	public void connect() throws DataAccessObjectException;

	public void disconnect() throws DataAccessObjectException;

	public void insert(E e) throws DataAccessObjectException;

	public void update(E e) throws DataAccessObjectException;

	public void delete(E e) throws DataAccessObjectException;

	public Optional<E> get(E e) throws DataAccessObjectException;

	public List<E> list() throws DataAccessObjectException;

	public long count() throws DataAccessObjectException;
}