package de.fhws.apiprog.vorlesung3.personrest.tests;

import java.security.KeyException;

import javax.ws.rs.core.Response;

import org.junit.Test;

import de.fhws.apiprog.vorlesung3.personrest.backend.PersonBackend;
import de.fhws.apiprog.vorlesung3.personrest.objects.Person;
import static org.junit.Assert.assertEquals;

public class TestPersonServiceDelete extends AbstractPersonExistingServiceTest {

	@Test
	/**
	 * Es sollte beim löschen eines Eintrags einen korrekten
	 * Eintrag zurück geben.
	 */
	public void testDeletionStatusCode() {
		Person test_person = this.getTestPerson();
		
		Response resp = target(this.getPath(test_person)).request().delete();
		
		assertEquals(
				Response.Status.NO_CONTENT.getStatusCode(),
				resp.getStatus()
				);
	}
	
	@Test(expected=KeyException.class)
	/**
	 * Es sollte beim löschen einen Eintrag tatsächlich aus dem 
	 * Backend entfernen.
	 */
	public void testDeletion() throws KeyException {
		Person test_person = this.getTestPerson();
		
		target(this.getPath(test_person)).request().delete();
		PersonBackend backend = new PersonBackend();
		backend.getPerson(test_person.getId());
	}
	
	@Test
	/**
	 * Es sollte beim Versuch der Löschung einer Person
	 * dessen ID nicht existiert einen NotFound zurück
	 * geben.
	 */
	public void testDeletionNotFound() {
		Response resp = target(this.getPath(2000)).request().delete();
		assertEquals(
				Response.Status.NOT_FOUND.getStatusCode(), 
				resp.getStatus()
		);
	}
	
}
