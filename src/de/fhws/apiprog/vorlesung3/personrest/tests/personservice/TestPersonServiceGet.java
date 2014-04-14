package de.fhws.apiprog.vorlesung3.personrest.tests.personservice;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.ws.rs.core.Response;

import org.junit.Test;

import com.owlike.genson.Genson;
import com.owlike.genson.TransformationException;
import com.owlike.genson.stream.ObjectReader;

import de.fhws.apiprog.vorlesung3.personrest.backend.PersonBackend;
import de.fhws.apiprog.vorlesung3.personrest.objects.Person;


public class TestPersonServiceGet extends AbstractPersonServiceTest {
	
	/**
	 * Es sollte Daten zurück geben können.
	 */
	@Test
	public void testGetJson() {
		PersonBackend backend = new PersonBackend();
		Person person = backend.add(new Person("James", "Bond"));
		
		String path = String.format("persons/%s",  person.getId());
		Response resp = target(path).request().get();
		assertEquals(200, resp.getStatus());
		Object entity = resp.getEntity();
		
		assertTrue(entity instanceof ByteArrayInputStream);
		ByteArrayInputStream input_stream = (ByteArrayInputStream)entity;
		Genson g = new Genson();
		ObjectReader object_reader = g.createReader(input_stream);
		Person result_person;
		try {
			result_person = (Person)g.deserialize(Person.class, object_reader, null);
		} catch (TransformationException e) {
			fail();
			return;
		} catch (IOException e) {
			fail();
			return;
		}
		assertEquals(result_person.getId(), person.getId());
	}
	
	/**
	 * Es soltle die Daten als XML zurück geben.
	 */
	@Test
	public void testGetXml() {
		PersonBackend backend = new PersonBackend();
		Person person = backend.add(new Person("James", "Bond"));
		
		String path = String.format("persons/%s",  person.getId());
		Response resp = target(path).request().accept("application/xml").get();
		assertEquals(200, resp.getStatus());
		Object entity = resp.getEntity();
		
		assertTrue(entity instanceof ByteArrayInputStream);
		ByteArrayInputStream input_stream = (ByteArrayInputStream)entity;
		Genson g = new Genson();
		ObjectReader object_reader = g.createReader(input_stream);
		Person result_person;
		try {
			result_person = (Person)g.deserialize(Person.class, object_reader, null);
		} catch (TransformationException e) {
			fail();
			return;
		} catch (IOException e) {
			fail();
			return;
		}
		assertEquals(result_person.getId(), person.getId());
	}
	
	/**
	 * Es sollte not Found zurück geben, wenn 
	 * kein Eintrag mit dieser ID existiert.
	 */
	@Test
	public void testGetJsonNotFound() {
		String path = String.format("persons/%s",  2143);
		Response resp = target(path).request().get();
		assertEquals(404, resp.getStatus());
	}

}
