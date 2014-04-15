package de.fhws.apiprog.vorlesung3.personrest.tests.personorderservice;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.ws.rs.core.Response;

import org.junit.Test;

import com.owlike.genson.Genson;
import com.owlike.genson.TransformationException;
import com.owlike.genson.stream.ObjectReader;

import de.fhws.apiprog.vorlesung3.personrest.backend.PersonBackend;
import de.fhws.apiprog.vorlesung3.personrest.objects.Order;
import de.fhws.apiprog.vorlesung3.personrest.objects.Person;

public class TestPersonOrderServiceSearch extends
		AbstractPersonOrderServiceTest {

	private Person searchPerson;
	private Person otherPerson;
	
	protected Person getSearchPerson() {
		return searchPerson;
	}

	protected void setSearchPerson(Person searchPerson) {
		this.searchPerson = searchPerson;
	}

	protected Person getOtherPerson() {
		return otherPerson;
	}

	protected void setOtherPerson(Person otherPerson) {
		this.otherPerson = otherPerson;
	}

	@Override
	public void setUp() throws Exception
	{
		super.setUp();
		
		setSearchPerson(new Person("Christoph", "Brand"));
		setOtherPerson(new Person("Mario", "Man"));
		
		PersonBackend backend = new PersonBackend();
		backend.add(getSearchPerson());
		backend.add(getOtherPerson());
		
		Order order = getTestOrder();
		order.setProductName("Nuclear Device");
		getPersonOrderBackend(getSearchPerson()).add(order);
		
		order = getTestOrder();
		order.setProductName("Nuclear Plant");
		getPersonOrderBackend(getSearchPerson()).add(order);
	}
	
	@Test
	/**
	 * Es sollte nach dem Produktnamen Parameter suchen können.
	 */
	public void testSearchByProductName() 
			throws Exception
	{
		
		Response resp = target(getPath(getSearchPerson()))
				.queryParam("productName", "Nuclear Device")
				.request()
				.get();
		Order[] orders = getOrderJson(resp);
		assertEquals(1, orders.length);
		assertEquals("Nuclear Device", orders[0].getProductName());
	}
	
	@Test
	/**
	 * Es sollte keine Einträge zurück geben, wenn 
	 * ein Objekt auf einem anderen Eintrag existiert.
	 */
	public void testSearchFromNonEntry() throws Exception
	{
		Response resp = target(getPath(getOtherPerson()))
				.queryParam("productName", "Nuclear Device")
				.request()
				.get();
		Order[] orders = getOrderJson(resp);
		assertEquals(0, orders.length);
	}
	
	/**
	 * Gibt die Liste an Order-Objekten zurück, die 
	 * als JSON encodiert im übergebenen Response 
	 * gespeichert sind.
	 * @throws IOException 
	 * @throws TransformationException 
	 */
	protected Order[] getOrderJson(Response resp) 
			throws TransformationException, IOException
	{
		Object entity = resp.getEntity();
		ByteArrayInputStream input_stream = (ByteArrayInputStream)entity;
		Genson g = new Genson();
		ObjectReader object_reader = g.createReader(input_stream);
		return (Order[]) g.deserialize(
				Order[].class, object_reader, null
				);
	}
	
}
