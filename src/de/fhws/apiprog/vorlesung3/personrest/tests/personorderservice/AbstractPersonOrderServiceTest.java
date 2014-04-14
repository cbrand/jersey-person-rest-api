package de.fhws.apiprog.vorlesung3.personrest.tests.personorderservice;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.core.Response;

import de.fhws.apiprog.vorlesung3.personrest.backend.OrderBackend;
import de.fhws.apiprog.vorlesung3.personrest.backend.PersonOrderBackend;
import de.fhws.apiprog.vorlesung3.personrest.objects.Order;
import de.fhws.apiprog.vorlesung3.personrest.objects.Person;
import de.fhws.apiprog.vorlesung3.personrest.tests.personservice.AbstractPersonExistingServiceTest;

public abstract class AbstractPersonOrderServiceTest extends AbstractPersonExistingServiceTest {
	
	protected Pattern locationRegexp = Pattern.compile("^/persons/(\\d+)/orders/(\\d+)$");
	
	private Person testPerson;
	
	@Override
	public void tearDown()
	{
		super.tearDown();
		testPerson = null;
		new OrderBackend().reset();
	}
	
	protected String getPath(Person person, Order order)
	{
		return String.format(
				"%s/%s",
				getPath(person),
				order.getId()
				);
	}
	
	protected String getPath(Order order) {
		return String.format(
				"%s/%s",
				getPath(),
				order.getId()
				);
	}
	
	protected String getPath() {
		return getPath(getTestPerson());
	}
	
	@Override
	protected String getPath(long person_id)
	{
		return String.format(
				"%s/orders", super.getPath(person_id)
				);
	}

	@Override
	protected Person getTestPerson() {
		if(testPerson == null) {
			testPerson = getUnclonedTestPerson();
		}
		return testPerson;
	}
	
	protected Order getTestOrder() {
		Order order = new Order();
		order.setProductName("Nuclear Device");
		order.setAmmount(1);
		order.setPrice(new Double(2000000));
		order.setState(Order.OrderStates.NEW);
		return order;
	}
	
	protected Order addTestOrderAndReturn() {
		Order test_order = getTestOrder();
		addTestOrder(test_order);
		return test_order;
	}
	
	protected void addTestOrder(Order order) {
		getPersonOrderBackend().add(order);
	}
	
	protected PersonOrderBackend getPersonOrderBackend() {
		return getPersonOrderBackend(getTestPerson());
	}
	
	protected PersonOrderBackend getPersonOrderBackend(Person person) {
		return new PersonOrderBackend(person);
	}
	
	@Override
	protected Matcher pathMatcher(Response response) {
		String header = extractLocationHeaderString(response);
		Matcher m = locationRegexp.matcher(header);
		return m;
	}
	
	
}
