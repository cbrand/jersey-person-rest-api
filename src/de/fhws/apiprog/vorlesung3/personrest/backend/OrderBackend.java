package de.fhws.apiprog.vorlesung3.personrest.backend;

import java.util.HashMap;
import java.util.Map;

import de.fhws.apiprog.vorlesung3.personrest.backend.seacher.OrderSearcher;
import de.fhws.apiprog.vorlesung3.personrest.backend.seacher.Searcher;
import de.fhws.apiprog.vorlesung3.personrest.backend.services.OrderUpdateService;
import de.fhws.apiprog.vorlesung3.personrest.backend.services.UpdateService;
import de.fhws.apiprog.vorlesung3.personrest.objects.Order;

public class OrderBackend extends AbstractBackend<Order> {

	private static Map<Long, Order> store = new HashMap<>();

	@Override
	public Searcher<Order> search() {
		return new OrderSearcher(this.getStore().values());
	}

	@Override
	protected UpdateService<Order> getUpdateService(Order item, Order data) {
		return new OrderUpdateService(item, data);
	}

	@Override
	protected Map<Long, Order> getStore() {
		return OrderBackend.store;
	}
	
	
	
}
