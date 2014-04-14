package de.fhws.apiprog.vorlesung3.personrest.backend;

import java.security.KeyException;

import de.fhws.apiprog.vorlesung3.personrest.backend.seacher.Searcher;

public interface Backend<T> {

	public Iterable<T> get();
	public T get(Long id) throws KeyException;
	public Searcher<T> search();
	
	public T add(T item);
	public T update(Long id, T other) throws KeyException;
	public void delete(Long id) throws KeyException;
	
	public void reset();
	
}
