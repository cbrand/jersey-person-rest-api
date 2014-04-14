package de.fhws.apiprog.vorlesung3.personrest.tests.personservice;

import de.fhws.apiprog.vorlesung3.personrest.backend.PersonBackend;
import de.fhws.apiprog.vorlesung3.personrest.objects.Person;

public abstract class AbstractPersonExistingServiceTest extends
		AbstractPersonServiceTest {

	/**
	 * Gibt den Pfad zur Ressource der übergebenen
	 * Person zurück.
	 * @param person
	 * @return
	 */
	protected String getPath(Person person)
	{
		return this.getPath(person.getId());
	}
	
	/**
	 * Gibt den Pfad zur Personenressource mit der
	 * übergebenen ID zurück.
	 * @param person_id
	 * @return
	 */
	protected String getPath(long person_id)
	{
		return String.format("persons/%s", person_id);
	}
	
	@Override
	protected Person getTestPerson() 
	{
		Person person = super.getTestPerson();
		PersonBackend backend = new PersonBackend();
		backend.add(person);
		Person return_person = person.clone();
		return return_person;
	}
	
	protected Person getUnclonedTestPerson()
	{
		Person person = super.getTestPerson();
		PersonBackend backend = new PersonBackend();
		backend.add(person);
		return person;
	}
	
}
