package de.fhws.apiprog.vorlesung3.personrest.tests;

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


public class TestPersonServicePost extends AbstractPersonServiceTest {
	
	/**
	 * Validiert die Location die beim erstellen einer Klasse
	 * durchgeführt wird.
	 * @param response
	 */
	public void validateCreateLocation(Response response) {
		assertTrue(
			this.pathMatcher(response).matches()
		);
	}
	
	@Test
	/**
	 * Es sollte die Person erstellen über XML.
	 */
	public void testCreationXml() {
		Person test_person = this.getTestPerson();
		Entity<Person> personEntity = Entity.entity(
			test_person, MediaType.APPLICATION_XML_TYPE
		);
		Response resp = target("persons").request().post(personEntity);
		assertEquals(resp.getStatus(), 201);
		this.validateCreateLocation(resp);
	}
	
	@Test
	/**
	 * Es sollte die Person erstellen über JSON.
	 */
	public void testCreationJson() {
		Person test_person = this.getTestPerson();
		Entity<Person> personEntity = Entity.entity(
			test_person, MediaType.APPLICATION_JSON_TYPE
		);
		Response resp = target("persons").request().post(personEntity);
		assertEquals(resp.getStatus(), 201);
		this.validateCreateLocation(resp);
	}
	
	@Test
	/**
	 * Es sollte die Koordinate über JSON gesetzt werden.
	 */
	public void testCreationOfCoordinate() throws KeyException {
		Person test_person = this.getTestPerson();
		Entity<Person> personEntity = Entity.entity(
			test_person, MediaType.APPLICATION_JSON_TYPE
		);
		Response resp = target("persons").request().post(personEntity);
		PersonBackend person_backend = new PersonBackend();
		Coordinate location = person_backend.getPerson(
				this.getIdFromLocation(resp)
			).getLocation();
		assertEquals(
				location.getLatitude(),
				new Double(200)
				);
		assertEquals(
				location.getLongitude(),
				new Double(200)
		);
	}

}
