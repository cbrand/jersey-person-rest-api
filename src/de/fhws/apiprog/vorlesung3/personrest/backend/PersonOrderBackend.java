package de.fhws.apiprog.vorlesung3.personrest.backend;

import java.security.KeyException;
import java.util.ArrayList;
import java.util.Arrays;

import de.fhws.apiprog.vorlesung3.personrest.backend.seacher.OrderSearcher;
import de.fhws.apiprog.vorlesung3.personrest.backend.seacher.Searcher;
import de.fhws.apiprog.vorlesung3.personrest.objects.Order;
import de.fhws.apiprog.vorlesung3.personrest.objects.Person;

public class PersonOrderBackend extends OrderBackend {
	
	private Person person;
	
	protected Person getPerson() {
		return person;
	}

	protected void setPerson(Person person) {
		this.person = person;
	}

	/**
	 * @param person Die Person zu dem diese Bestellungen zugeordnet werden sollen.
	 */
	public PersonOrderBackend(Person person)
	{
		this.setPerson(person);
	}
	
	@Override
	public Iterable<Order> get() {
		return this.person.getOrders();
	}

	@Override
	public Order get(Long id) throws KeyException {
		return this.person.getOrder(id);
	}

	@Override
	public synchronized Order add(Order item) {
		super.add(item);
		this.person.addOrder(item);
		return item;
	}

	@Override
	public void delete(Long id) throws KeyException {
		this.person.removeOrder(id);
		super.delete(id);
	}

	@Override
	public void reset() {
		for(Order order: this.person.getOrders()) {
			getStore().remove(order);
		}
		this.person.clearOrders();
	}

	@Override
	public Searcher<Order> search() {
		ArrayList<Order> orders = new ArrayList<>();
		for(Order order: person.getOrders()) {
			orders.add(order);
		}
		return new OrderSearcher(orders);
	}
	
}
