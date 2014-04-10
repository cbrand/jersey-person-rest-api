package de.fhws.apiprog.vorlesung3.personrest.backend;

import java.security.KeyException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import de.fhws.apiprog.vorlesung3.personrest.objects.Person;

public class PersonBackend {
	
	private static Map<Long, Person> store = new HashMap<Long, Person>();
	
	public static void reset() 
	{
		PersonBackend.store = new HashMap<Long, Person>();
	}
	
	public Person getPerson(long id) throws KeyException 
	{
		Person person = null;
		if(PersonBackend.getStore().containsKey(id)) {
			person = PersonBackend.getStore().get(id);
		}
		else {
			throw new KeyException("Key not found");
		}
		return person;
	}
	
	public Iterable<Person> getPersons() 
	{
		return PersonBackend.getStore().values();
	}
	
	protected static Long getNextKey() {
		long key = 0;
		if(PersonBackend.getStore().size() > 0) {
			key = Collections.max(PersonBackend.getStore().keySet()) + 1;
		}
		return new Long(key);
	}
	
	public synchronized Person addPerson(Person person)
	{
		person.setId(PersonBackend.getNextKey());
		PersonBackend.getStore().put(person.getId(), person);
		return person;
	}

	protected static Map<Long, Person> getStore() {
		return store;
	}

	protected static void setStore(Map<Long, Person> store) {
		PersonBackend.store = store;
	}
	
}
