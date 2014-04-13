package de.fhws.apiprog.vorlesung3.personrest.backend;

import java.security.KeyException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import de.fhws.apiprog.vorlesung3.personrest.backend.seacher.PersonSearcher;
import de.fhws.apiprog.vorlesung3.personrest.backend.services.PersonUpdateService;
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
		
		PersonUpdateService update_service = new PersonUpdateService(person, other_person);
		update_service.update();
		
		return person;
	}
	
	/**
	 * Löscht die Person die die übergebene ID besitzt
	 * @param person_id
	 * @throws KeyException Wenn die ID der Person nicht gespeichert ist.
	 */
	public void deletePerson(long person_id) throws KeyException 
	{
		if(PersonBackend.store.remove(person_id) == null)
		{
			String errorMessage = String.format(
					"Person with id %s not found", person_id
					);
			throw new KeyException(errorMessage);
		}
	}
	
	public PersonSearcher search()
	{
		return new PersonSearcher(PersonBackend.getStore().values());
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
