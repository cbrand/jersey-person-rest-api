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
		assertEquals(resp.getStatus(), 
				Response.Status.CREATED.getStatusCode()
				);
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
		assertEquals(
				Response.Status.CREATED.getStatusCode(), 
				resp.getStatus()
		);
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
		Coordinate location = person_backend.get(
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
	
	@Test
	/**
	 * Es sollte den Code 400 zurück geben, wenn falsche Daten
	 * übergeben werden.
	 */
	public void testErrorInputCreationJson() {
		String payload = "<?xml version=\"1.0\" encoding=\"utf-8\"?><root></root>";
		Entity<String> malformedPersonEntity = Entity.entity(
			payload, MediaType.APPLICATION_JSON_TYPE
		);
		
		Response resp = target("persons").request().post(malformedPersonEntity);
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
	public void testErrorInputCreationXml() {
		String payload = "not valid xml";
		Entity<String> malformedPersonEntity = Entity.entity(
			payload, MediaType.APPLICATION_XML_TYPE
		);
		
		Response resp = target("persons").request().post(malformedPersonEntity);
		assertEquals(
			resp.getStatus(),
			Response.Status.BAD_REQUEST.getStatusCode()
		);
	}

}
