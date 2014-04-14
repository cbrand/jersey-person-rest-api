package de.fhws.apiprog.vorlesung3.personrest.backend;

import java.util.HashMap;
import java.util.Map;

import de.fhws.apiprog.vorlesung3.personrest.backend.seacher.PersonSearcher;
import de.fhws.apiprog.vorlesung3.personrest.backend.services.PersonUpdateService;
import de.fhws.apiprog.vorlesung3.personrest.objects.Person;

public class PersonBackend extends AbstractBackend<Person> {
	
	private static Map<Long, Person> store = new HashMap<Long, Person>();
	
	public void reset() 
	{
		PersonBackend.store = new HashMap<Long, Person>();
	}
	
	public PersonSearcher search()
	{
		return new PersonSearcher(getStore().values());
	}

	@Override
	protected PersonUpdateService getUpdateService(Person item, Person data) {
		return new PersonUpdateService(item, data);
	}

	@Override
	protected Map<Long, Person> getStore() {
		return PersonBackend.store;
	}
	
}
