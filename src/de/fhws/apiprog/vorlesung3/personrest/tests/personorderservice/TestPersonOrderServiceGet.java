package de.fhws.apiprog.vorlesung3.personrest.tests.personorderservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.KeyException;

import javax.ws.rs.core.Response;

import org.junit.Test;

import com.owlike.genson.Genson;
import com.owlike.genson.TransformationException;
import com.owlike.genson.stream.ObjectReader;

import de.fhws.apiprog.vorlesung3.personrest.backend.OrderBackend;
import de.fhws.apiprog.vorlesung3.personrest.objects.Order;
import de.fhws.apiprog.vorlesung3.personrest.objects.Person;

public class TestPersonOrderServiceGet extends AbstractPersonOrderServiceTest {

	/**
	 * Es sollte Daten zurück geben können. 
	 */
	@Test
	public void testGet() throws Exception {
		Order test_order = addTestOrderAndReturn();
		
		String path = getPath(test_order);
		Response resp = target(path).request().get();
		assertEquals(200, resp.getStatus());
		Object entity = resp.getEntity();
		
		assertTrue(entity instanceof ByteArrayInputStream);
		ByteArrayInputStream input_stream = (ByteArrayInputStream)entity;
		Genson g = new Genson();
		ObjectReader object_reader = g.createReader(input_stream);
		Order result_order;
		result_order = (Order)g.deserialize(Order.class, object_reader, null);
		assertEquals(result_order.getId(), test_order.getId());
	}
	
	
	/**
	 * Es sollte einen NotFound Status Code zurück geben,
	 * wenn von einem anderen Personenobjekt eine Order
	 * abgefragt wird, die nicht diesem zugeordnet ist.
	 */
	@Test
	public void testGetNotFound() {
		Order test_order = addTestOrderAndReturn();
		
		Person person = new Person("Christoph", "Brand");
		getPersonBackend().add(person);
		
		Response resp = target(getPath(person, test_order)).request().get();
		assertEquals(
				Response.Status.NOT_FOUND.getStatusCode(), 
				resp.getStatus()
				);
	}
	
}
