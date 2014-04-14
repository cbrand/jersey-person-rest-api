package de.fhws.apiprog.vorlesung3.personrest.backend.seacher;

import static ch.lambdaj.Lambda.having;
import static ch.lambdaj.Lambda.on;
import static org.hamcrest.Matchers.equalTo;

import java.util.Collection;

import de.fhws.apiprog.vorlesung3.personrest.helpers.NumberHelper;
import de.fhws.apiprog.vorlesung3.personrest.objects.Order;

public class OrderSearcher extends Searcher<Order> {

	public OrderSearcher(Collection<Order> baseSet) {
		super(baseSet);
	}
	
	public void byProductName(String product_name)
	{
		this.updateWith(
				having(on(Order.class).getProductName(), equalTo(product_name))
				);
	}
	
	public void byPrice(Double price)
	{
		this.updateWith(
				having(on(Order.class).getPrice(), equalTo(price))
				);
	}
	
	public void byPrice(String price)
	{
		if(NumberHelper.isDouble(price)) {
			this.byPrice(new Double(price));
		}
		else {
			throw new IllegalArgumentException("The string must represent a double value");
		}
	}
	
	public void byState(String state)
	{
		this.updateWith(
				having(on(Order.class).getState(), equalTo(state))
				);
	}

}
