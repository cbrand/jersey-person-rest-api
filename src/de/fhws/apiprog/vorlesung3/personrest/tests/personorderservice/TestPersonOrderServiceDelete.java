package de.fhws.apiprog.vorlesung3.personrest.tests.personorderservice;

import java.security.KeyException;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import de.fhws.apiprog.vorlesung3.personrest.objects.Order;
import de.fhws.apiprog.vorlesung3.personrest.objects.Person;

public class TestPersonOrderServiceDelete extends AbstractPersonOrderServiceTest {

	@Test
	/**
	 * Es sollte einen not found error werfen, wenn versucht
	 * wird eine Order zu aktuallisieren, die nciht vorhanden ist.
	 */
	public void testOrderNotFound()
	{
		Order test_order = this.getTestOrder();
		test_order.setId(123);
		Response resp = target(getPath(test_order)).request().delete();
		assertEquals(
				Response.Status.NOT_FOUND.getStatusCode(),
				resp.getStatus()
				);
	}
	
	@Test(expected=KeyException.class)
	/**
	 * Es sollte eine Testorder anpassen.
	 */
	public void testOrderDelete() throws Exception
	{
		Order test_order = addTestOrderAndReturn();
		Response resp = target(getPath(test_order)).request().delete();
		assertEquals(
				Response.Status.NO_CONTENT.getStatusCode(),
				resp.getStatus()
				);
		getPersonOrderBackend().get(test_order.getId());	
	}
	
	@Test()
	/**
	 * Es sollte einen NotFound werfen, wenn versucht
	 * wird eine Order, das einer anderen Person zugeordnet
	 * wird zurück gegeben.
	 */
	public void testOrderDeleteRemove() throws Exception
	{
		Order test_order = addTestOrderAndReturn();
		Person person = new Person("Christian", "Mustermann");
		getPersonBackend().add(person);
		Response resp = target(getPath(person, test_order)).request().delete();
		assertEquals(
				Response.Status.NOT_FOUND.getStatusCode(),
				resp.getStatus()
				);
	}
	
}
