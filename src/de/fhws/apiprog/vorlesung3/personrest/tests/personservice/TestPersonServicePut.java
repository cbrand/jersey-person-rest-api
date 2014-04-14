package de.fhws.apiprog.vorlesung3.personrest.tests.personservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.security.KeyException;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Test;

import de.fhws.apiprog.vorlesung3.personrest.backend.PersonBackend;
import de.fhws.apiprog.vorlesung3.personrest.objects.Coordinate;
import de.fhws.apiprog.vorlesung3.personrest.objects.Person;


public class TestPersonServicePut extends AbstractPersonExistingServiceTest {

	/**
	 * Validiert die Location die beim erstellen einer Klasse
	 * durchgeführt wird.
	 * @param response
	 */
	public void validateUpdateLocation(Response response) {
		assertTrue(
			this.pathMatcher(response).matches()
		);
	}
	
	@Test
	/**
	 * Es sollte die Person erstellen über XML.
	 */
	public void testUpdateXml() {
		Person test_person = this.getTestPerson();
		test_person.setFirstName("M");
		test_person.setLastName("XXX");
		Entity<Person> personEntity = Entity.entity(
			test_person, MediaType.APPLICATION_XML_TYPE
		);
		Response resp = target(
				this.getPath(test_person)
				).request().put(personEntity);
		assertEquals(
				Response.Status.NO_CONTENT.getStatusCode(), 
				resp.getStatus()
				);
		this.validateUpdateLocation(resp);
	}
	
	@Test
	/**
	 * Es sollte die Person erstellen über JSON.
	 */
	public void testUpdateJson() {
		Person test_person = this.getTestPerson();
		test_person.setFirstName("M");
		test_person.setLastName("XXX");
		Entity<Person> personEntity = Entity.entity(
			test_person, MediaType.APPLICATION_JSON_TYPE
		);
		Response resp = target(
				this.getPath(test_person)
				).request().put(personEntity);
		assertEquals(
				Response.Status.NO_CONTENT.getStatusCode(), 
				resp.getStatus()
		);
		this.validateUpdateLocation(resp);
	}
	
	@Test
	/**
	 * Es sollte den Vornamen des Eintrags korrekt setzen können.
	 */
	public void testUpdateFirstName() throws KeyException {
		Person test_person = this.getTestPerson();
		test_person.setFirstName("M");
		Entity<Person> personEntity = Entity.entity(
			test_person, MediaType.APPLICATION_JSON_TYPE
		);
		target(
				this.getPath(test_person)
				).request().put(personEntity);
		PersonBackend backend = new PersonBackend();
		Person p = backend.get(test_person.getId());
		assertEquals("M", p.getFirstName());
	}
	
	@Test
	/**
	 * Es sollte die Koordinate über JSON gesetzt werden.
	 */
	public void testUpdateOfCoordinate() throws KeyException {
		Person test_person = this.getTestPerson();
		test_person.setLocation(new Coordinate(300, 300));
		Entity<Person> personEntity = Entity.entity(
			test_person, MediaType.APPLICATION_JSON_TYPE
		);
		Response resp = target(
				this.getPath(test_person)
				).request().put(personEntity);
		PersonBackend person_backend = new PersonBackend();
		Coordinate location = person_backend.get(
				this.getIdFromLocation(resp)
			).getLocation();
		assertEquals(
				location.getLatitude(),
				new Double(300)
				);
		assertEquals(
				location.getLongitude(),
				new Double(300)
		);
	}
	
	@Test
	/**
	 * Es sollte den Code 400 zurück geben, wenn falsche Daten
	 * übergeben werden.
	 */
	public void testErrorInputUpdateJson() {
		String payload = "<?xml version=\"1.0\" encoding=\"utf-8\"?><root></root>";
		Entity<String> malformedPersonEntity = Entity.entity(
			payload, MediaType.APPLICATION_JSON_TYPE
		);
		
		Response resp = target(
				this.getPath(this.getTestPerson())
				).request().put(malformedPersonEntity);
		assertEquals(
			resp.getStatus(),
			Response.Status.BAD_REQUEST.getStatusCode()
		);
	}
	
	@Test
	/**
	 * Es sollte den Code 400 zurück geben, wenn falsche Daten
	 * über XML weiter gegeben werden.
	 */
	public void testErrorInputUpdateXml() {
		String payload = "not valid xml";
		Entity<String> malformedPersonEntity = Entity.entity(
			payload, MediaType.APPLICATION_XML_TYPE
		);
		Response resp = target(
				this.getPath(this.getTestPerson())
				).request().put(malformedPersonEntity);
		assertEquals(
			resp.getStatus(),
			Response.Status.BAD_REQUEST.getStatusCode()
		);
	}
	
	@Test
	/**
	 * Es sollte ein Not Found Entry werfen, wenn im Backend
	 * kein Eintrag mit der ID angegeben wird.
	 */
	public void testNotFoundEntry() {
		Person test_person = this.getTestPerson();
		test_person.setFirstName("M");
		test_person.setLastName("XXX");
		Entity<Person> personEntity = Entity.entity(
			test_person, MediaType.APPLICATION_XML_TYPE
		);
		String path = this.getPath(this.getTestPerson().getId() + 3000);
		Response resp = target(path).request().put(personEntity);
		assertEquals(
			Response.Status.NOT_FOUND.getStatusCode(),
			resp.getStatus()
		);
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

}
