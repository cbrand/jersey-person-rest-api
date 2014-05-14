package de.fhws.apiprog.vorlesung3.personrest.tests.personservice;


import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.junit.Test;

import com.owlike.genson.Genson;
import com.owlike.genson.TransformationException;
import com.owlike.genson.stream.ObjectReader;

import de.fhws.apiprog.vorlesung3.personrest.backend.PersonBackend;
import de.fhws.apiprog.vorlesung3.personrest.objects.Person;

public class TestPersonServiceSearch extends AbstractPersonServiceTest {
	
	@Override
	public void setUp() throws Exception
	{
		super.setUp();
		PersonBackend backend = new PersonBackend();
		backend.add(new Person("Christoph", "Brand"));
		backend.add(new Person("Mario", "Man"));
		backend.add(new Person("Max", "Mustermann"));
	}
	
	@Test
	/**
	 * Es sollte nach dem Vornamen Parameter suchen können.
	 */
	public void testSearchFirstName() 
			throws TransformationException, IOException
	{
		Response resp = target("persons")
				.queryParam("firstName", "Christoph")
				.request()
				.get();
		Person[] persons = getPersonsJson(resp);
		assertEquals(1, persons.length);
		assertEquals("Brand", persons[0].getLastName());
	}
	
	@Test
	/**
	 * Es sollte nach dem Nachnamen Parameter suchen können.
	 */
	public void testSearchLastName() 
			throws TransformationException, IOException
	{
		Response resp = target("persons")
				.queryParam("lastName", "Brand")
				.request()
				.get();
		Person[] persons = getPersonsJson(resp);
		assertEquals(1, persons.length);
		assertEquals("Christoph", persons[0].getFirstName());
	}
	
	@Test
	/**
	 * Es sollte ein Limit Parametereintrag beachten.
	 * @throws Exception
	 */
	public void testSearchLimit()
		throws Exception
	{
		Response resp = target("persons")
				.queryParam("limit", "1")
				.request()
				.get();
		Person[] persons = getPersonsJson(resp);
		assertEquals(1, persons.length);
	}
	
	@Test
	/**
	 * Es sollte wenn das Limit größer als die Maximaldaten
	 * sind keinen Fehler werfen.
	 */
	public void testSearchLimitBigNumber()
			throws Exception
		{
			Response resp = target("persons")
					.queryParam("limit", "200")
					.request()
					.get();
			Person[] persons = getPersonsJson(resp);
			assertEquals(3, persons.length);
		}
	
	@Test
	/**
	 * Es sollte einen Offset beachten.
	 */
	public void testOffset()
			throws Exception
		{
			Response resp = target("persons")
					.queryParam("offset", "2")
					.request()
					.get();
			Person[] persons = getPersonsJson(resp);
			assertEquals(1, persons.length);
		}
	
	@Test
	/**
	 * Es sollte bei einem zu hohen Offset eine
	 * leere Liste zurück geben.
	 */
	public void testOffsetHigh()
			throws Exception
		{
			Response resp = target("persons")
					.queryParam("offset", "200")
					.request()
					.get();
			Person[] persons = getPersonsJson(resp);
			assertEquals(0, persons.length);
		}
	
	@Test
	/**
	 * Es sollte ein Offset und ein Limit gleichzeitig
	 * verarbeiten können.
	 */
	public void testOffsetAndLimit()
			throws Exception
		{
			Response resp = target("persons")
					.queryParam("offset", "1")
					.queryParam("limit", "1")
					.request()
					.get();
			Person[] persons = getPersonsJson(resp);
			assertEquals(1, persons.length);
			assertEquals(persons[0].getFirstName(), "Mario");
		}
	
	@Test
	/**
	 * Es sollte die next page in einem Link Header angeben,
	 * wenn eine weitere Seite existiert.
	 */
	public void testNextPageWithOffsetAndLimit()
	{
		Response resp = target("persons")
				.queryParam("offset", "0")
				.queryParam("limit", "1")
				.request()
				.get();
		List<String> links = getLinkHeaders(resp);
		if(links.size() <= 0) {
			fail("There should be at least one link header.");
		}
		boolean check = false;
		for(String link: links) {
			if(link.contains("rel=\"after\"")) {
				assertThat(link, containsString("rel=\"after\""));
				assertThat(link, containsString("limit=1"));
				assertThat(link, containsString("offset=1"));
				check = true;
			}
		}
		if(!check) {
			fail("No link header to the before entry set.");
		}
	}
	
	@Test
	/**
	 * Falls keine vorherige Seite mehr existiert sollte kein before Link in die
	 * Headers gesetzt werden.
	 */
	public void testPrevPageWithOffsetZero()
	{
		Response resp = target("persons")
				.queryParam("offset", "0")
				.queryParam("limit", "1")
				.request()
				.get();
		List<String> links = getLinkHeaders(resp);
		boolean check = false;
		for(String link: links) {
			if(link.contains("rel=\"before\"")) {
				check = true;
			}
		}
		assertFalse(
				"A before link has been set even though there is no before page.", 
				check
			);
	}
	
	@Test
	/**
	 * Es sollte die vorherige Seite als Link angegeben werden, wenn eine
	 * vorherige Seite zur Verfügung steht.
	 */
	public void testPrevPageWithOffsetAndLimit()
	{
		Response resp = target("persons")
				.queryParam("offset", "1")
				.queryParam("limit", "1")
				.request()
				.get();
		List<String> links = getLinkHeaders(resp);
		if(links.size() <= 0) {
			fail("There should be at least one link header.");
		}
		boolean check = false;
		for(String link: links) {
			if(link.contains("rel=\"before\"")) {
				assertThat(link, containsString("rel=\"before\""));
				assertThat(link, containsString("limit=1"));
				assertThat(link, containsString("offset=0"));
				check = true;
			}
		}
		if(!check) {
			fail("No link header to the before entry set.");
		}
	}
	
	@Test
	/**
	 * Es sollte, wenn die nächste Seite nicht existiert keine
	 * nachträgliche Seite hinzufügen.
	 */
	public void testNextPageWithMaxOffset() {
		Response resp = target("persons")
				.queryParam("offset", "2")
				.queryParam("limit", "1")
				.request()
				.get();
		List<String> links = getLinkHeaders(resp);
		if(links.size() <= 0) {
			fail("There should be at least one link header.");
		}
		boolean check = false;
		for(String link: links) {
			if(link.contains("rel=\"after\"")) {
				check = true;
			}
		}
		assertFalse("There should not be a after link if the next page is not existing", check);
	}
	
	@Test
	/**
	 * Es sollte keine After Page anzeigen, wenn kein offset angegeben wurde, das Limit
	 * jedoch alle Daten abfragt.
	 */
	public void testNextPageWithOnlyLimit() {
		Response resp = target("persons")
				.queryParam("limit", "3")
				.request()
				.get();
		List<String> links = getLinkHeaders(resp);
		boolean check = false;
		for(String link: links) {
			if(link.contains("rel=\"after\"")) {
				check = true;
			}
		}
		assertFalse("There should not be a after link if the next page is not existing", check);
	}
	
	@Test
	/**
	 * Es sollte einen negativen Offset mit einem
	 * Fehler quittieren.
	 */
	public void testNegativeOffset() throws Exception
	{
		Response resp = target("persons")
				.queryParam("offset", "-1")
				.request()
				.get();
		assertEquals(
				Response.Status.BAD_REQUEST.getStatusCode(),
				resp.getStatus()
				);
	}
	
	@Test
	/**
	 * Es sollte einen negativen Limit mit einem
	 * Fehler quittieren können.
	 */
	public void testNegativeLimit() throws Exception
	{
		Response resp = target("persons")
				.queryParam("limit", "-1")
				.request()
				.get();
		assertEquals(
				Response.Status.BAD_REQUEST.getStatusCode(),
				resp.getStatus()
				);
	}
	
	/**
	 * Gibt die Liste an Personen zurück, die als JSON
	 * encodiert im übergebenen Response gespeichert
	 * sind.
	 * @throws IOException 
	 * @throws TransformationException 
	 */
	protected Person[] getPersonsJson(Response resp) 
			throws TransformationException, IOException
	{
		Object entity = resp.getEntity();
		ByteArrayInputStream input_stream = (ByteArrayInputStream)entity;
		Genson g = new Genson();
		ObjectReader object_reader = g.createReader(input_stream);
		return (Person[]) g.deserialize(
				Person[].class, object_reader, null
				);
	}
	
	protected List<String> getLinkHeaders(Response resp)
	{
		MultivaluedMap<String, Object> headers = resp.getHeaders();
		List<String> link_list = new ArrayList<>();
		
		if(headers.containsKey("Link")) {
			List<Object> links = headers.get("Link");
			for(Object link: links) {
				link_list.add((String)link);
			}
		}
		return link_list;
	}
	
}
