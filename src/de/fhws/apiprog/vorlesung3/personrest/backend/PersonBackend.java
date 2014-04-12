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
	
	public synchronized Person addPerson(Person person)
	{
		person.setId(PersonBackend.getNextKey());
		PersonBackend.getStore().put(person.getId(), person);
		return person;
	}
	
	/**
	 * Aktuallisiert die Person mit der übergebenen ID
	 * mit den Daten aus der übergebenen Person.
	 * @throws KeyException Wenn keine Person unter der ID gefunden werden konnte.
	 */
	public Person updatePerson(long person_id, Person other_person) throws KeyException
	{
		Person person = this.getPerson(person_id);
				
		return person;
	}
	
	protected static Long getNextKey() {
		long key = 0;
		if(PersonBackend.getStore().size() > 0) {
			key = Collections.max(PersonBackend.getStore().keySet()) + 1;
		}
		return new Long(key);
	}

	protected static Map<Long, Person> getStore() {
		return store;
	}

	protected static void setStore(Map<Long, Person> store) {
		PersonBackend.store = store;
	}
	
}
