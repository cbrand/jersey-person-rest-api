package de.fhws.apiprog.vorlesung3.personrest.tests.personorderservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Test;

import de.fhws.apiprog.vorlesung3.personrest.objects.Order;


public class TestPersonOrderServicePost extends AbstractPersonOrderServiceTest {
	
	
	/**
	 * Validiert die Location die beim erstellen einer Klasse
	 * durchgeführt wird.
	 * @param response
	 */
	public void validateCreateLocation(Response response) {
		assertTrue(
			pathMatcher(response).matches()
		);
	}
	
	@Test
	/**
	 * Es sollte die Person erstellen über JSON.
	 */
	public void testCreation() throws Exception {
		Order test_order = this.getTestOrder();
		Entity<Order> orderEntity = Entity.entity(
			test_order, MediaType.APPLICATION_JSON_TYPE
		);
		Response resp = target(getPath()).request().post(orderEntity);
		assertEquals(
				Response.Status.CREATED.getStatusCode(), 
				resp.getStatus()
		);
		this.validateCreateLocation(resp);
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
		
		Response resp = target(getPath()).request().post(malformedPersonEntity);
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
		
		Response resp = target(getPath()).request().post(malformedPersonEntity);
		assertEquals(
			resp.getStatus(),
			Response.Status.BAD_REQUEST.getStatusCode()
		);
	}

}
