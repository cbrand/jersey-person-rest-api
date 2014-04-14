package de.fhws.apiprog.vorlesung3.personrest.tests.personservice;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.inmemory.InMemoryTestContainerFactory;
import org.glassfish.jersey.test.spi.TestContainerFactory;

import de.fhws.apiprog.vorlesung3.personrest.PersonApplication;
import de.fhws.apiprog.vorlesung3.personrest.backend.PersonBackend;
import de.fhws.apiprog.vorlesung3.personrest.objects.Person;

public abstract class AbstractPersonServiceTest extends JerseyTest {

	@Override
    protected Application configure() {
        Application config = new PersonApplication();
        return config;
    }
	
	@Override
    protected TestContainerFactory getTestContainerFactory() {
        return new InMemoryTestContainerFactory();
    }
	
	public void tearDown()
	{
		new PersonBackend().reset();
	}
	
	protected Person getTestPerson() 
	{
		Person person = new Person();
		person.setFirstName("James");
		person.setLastName("Bond");
		person.setEmailAddress("jbond@mi6.com");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			person.setBirthDate(dateFormat.parse("2014-01-01"));
		} catch (ParseException e) {
		
		}
		person.setLocation(200, 200);
		return person;
	}
	
	protected String extractLocationHeaderString(Response response)
	{
		List<Object> location_headers = response.getHeaders().get("Location");
		assertTrue(location_headers.size() > 0);
		
		String location_header = (String)location_headers.get(0);
		String path_piece;
		try {
			path_piece = (new URI(location_header)).getPath();
		} catch (URISyntaxException e) {
			fail("Non valid URL returned.");
			return null;
		}
		return path_piece;
	}
	
	protected Matcher pathMatcher(Response response) {
		
		Pattern p = Pattern.compile("/persons/([0-9]+)$");
		Matcher m = p.matcher(extractLocationHeaderString(response));
		return m;
	}
	
	/**
	 * Gibt die ID für die übergebene Response zurück.
	 */
	protected Long getIdFromLocation(Response response)
	{
		Matcher m = this.pathMatcher(response);
		m.matches();
		String group = m.group(1);
		return Long.parseLong(
				group
			);
	}
	
	protected PersonBackend getPersonBackend()
	{
		return new PersonBackend();
	}
	
}
