package de.fhws.apiprog.vorlesung3.personrest.tests;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.inmemory.InMemoryTestContainerFactory;
import org.glassfish.jersey.test.spi.TestContainerFactory;
import org.junit.Test;

import com.owlike.genson.Genson;
import com.owlike.genson.TransformationException;
import com.owlike.genson.stream.ObjectReader;

import de.fhws.apiprog.vorlesung3.personrest.PersonService;
import de.fhws.apiprog.vorlesung3.personrest.backend.PersonBackend;
import de.fhws.apiprog.vorlesung3.personrest.objects.Person;


public class TestPersonService extends JerseyTest {

	@Override
    protected Application configure() {
        ResourceConfig config = new ResourceConfig(PersonService.class);
        return config;
    }
	
	@Override
    protected TestContainerFactory getTestContainerFactory() {
        return new InMemoryTestContainerFactory();
    }
	
	public void tearDown()
	{
		PersonBackend.reset();
	}
	
	protected Person getTestPerson() 
	{
		Person person = new Person();
		person.setFirstName("James");
		person.setLastName("Bond");
		return person;
	}
	
	/**
	 * Validiert die Location die beim erstellen einer Klasse
	 * durchgeführt wird.
	 * @param response
	 */
	public void validateCreateLocation(Response response) {
		List<Object> location_headers = response.getHeaders().get("Location");
		assertTrue(location_headers.size() > 0);
		
		String location_header = (String)location_headers.get(0);
		String path_piece;
		try {
			path_piece = (new URI(location_header)).getPath();
		} catch (URISyntaxException e) {
			fail("Non valid URL returned.");
			return;
		}
		Pattern p = Pattern.compile("/persons/[0-9]+$");
		Matcher m = p.matcher(path_piece);
		assertTrue(
			m.matches()
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
	
	/**
	 * Es sollte Daten zurück geben können.
	 */
	@Test
	public void testGetJson() {
		PersonBackend backend = new PersonBackend();
		Person person = backend.addPerson(new Person("James", "Bond"));
		
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
