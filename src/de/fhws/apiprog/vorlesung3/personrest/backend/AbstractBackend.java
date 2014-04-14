package de.fhws.apiprog.vorlesung3.personrest.backend;

import java.security.KeyException;
import java.util.Collections;
import java.util.Map;

import de.fhws.apiprog.vorlesung3.personrest.backend.services.UpdateService;
import de.fhws.apiprog.vorlesung3.personrest.objects.Bean;

public abstract class AbstractBackend<T extends Bean> implements Backend<T> {
	
	@Override
	public Iterable<T> get() {
		return getStore().values();
	}

	@Override
	public T get(Long id) throws KeyException {
		T item = null;
		if(getStore().containsKey(id)) {
			item = getStore().get(id);
		}
		else {
			throw new KeyException("Key not found");
		}
		return item;
	}

	@Override
	public synchronized T add(T item) {
		item.setId(getNextKey());
		getStore().put(item.getId(), item);
		return item;
	}

	@Override
	public T update(Long id, T other) throws KeyException {
		T item = this.get(id);
		getUpdateService(item, other).update();
		return item;
	}

	@Override
	public void delete(Long id) throws KeyException {
		if(getStore().remove(id) == null)
		{
			String errorMessage = String.format(
					"Item with id %s not found", id
					);
			throw new KeyException(errorMessage);
		}
	}

	@Override
	public void reset() {
		this.getStore().clear();
	}
	
	protected Long getNextKey() {
		long key = 0;
		if(getStore().size() > 0) {
			key = Collections.max(getStore().keySet()) + 1;
		}
		return new Long(key);
	}
	
	abstract protected UpdateService<T> getUpdateService(T item, T data);
	abstract protected Map<Long, T> getStore();

}
