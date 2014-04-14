package de.fhws.apiprog.vorlesung3.personrest.backend.seacher.parameter;

import java.lang.reflect.Method;

import de.fhws.apiprog.vorlesung3.personrest.backend.seacher.OrderSearcher;
import de.fhws.apiprog.vorlesung3.personrest.objects.Order;

public class OrderSearcherParameterHandler extends
		SearcherParameterHandler<Order, OrderSearcher> {

	public OrderSearcherParameterHandler(OrderSearcher searcher)
	{
		super(searcher);
	}
	
	@Override
	protected Method queryMethodFor(String key) throws NoSuchMethodException,
			SecurityException {
		return OrderSearcher.class.getMethod(getSearchMethodName(key), String.class);
	}

}
