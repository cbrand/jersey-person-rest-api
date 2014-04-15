package de.fhws.apiprog.vorlesung3.personrest.tests.personorderservice;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import de.fhws.apiprog.vorlesung3.personrest.objects.Order;

public class TestPersonOrderServicePut extends AbstractPersonOrderServiceTest {

	@Test
	/**
	 * Es sollte einen not found error werfen, wenn versucht
	 * wird eine Order zu aktuallisieren, die nicht vorhanden ist.
	 */
	public void testOrderNotFound()
	{
		Order test_order = this.getTestOrder();
		test_order.setId(123);
		Entity<Order> orderEntity = Entity.entity(
			test_order, MediaType.APPLICATION_JSON_TYPE
		);
		Response resp = target(getPath(test_order)).request().put(orderEntity);
		assertEquals(
				Response.Status.NOT_FOUND.getStatusCode(),
				resp.getStatus()
				);
	}
	
	@Test
	/**
	 * Es sollte eine Testorder anpassen.
	 */
	public void testOrderUpdate() throws Exception
	{
		Order test_order = addTestOrderAndReturn().clone();
		test_order.setProductName("Nuclear Plant");
		
		Entity<Order> orderEntity = Entity.entity(
				test_order, MediaType.APPLICATION_JSON_TYPE
			);
		Response resp = target(getPath(test_order)).request().put(orderEntity);
		assertEquals(
				Response.Status.NO_CONTENT.getStatusCode(),
				resp.getStatus()
				);
		assertEquals(
				getPersonOrderBackend().get(test_order.getId()).getProductName(),
				"Nuclear Plant"
				);
	}
	
}
