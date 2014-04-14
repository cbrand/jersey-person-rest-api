package de.fhws.apiprog.vorlesung3.personrest.backend.services;

import de.fhws.apiprog.vorlesung3.personrest.objects.Order;

public class OrderUpdateService implements UpdateService<Order> {

	/**
	 * Die Order die aktuallisiert werden soll
	 */
	private Order order;
	/**
	 * Die Daten die gesetzt werden sollen.
	 */
	private Order data;
	
	protected Order getOrder() {
		return order;
	}

	protected void setOrder(Order order) {
		this.order = order;
	}

	protected Order getData() {
		return data;
	}

	protected void setData(Order data) {
		this.data = data;
	}

	/**
	 * Aktuallisiert ein Order-Objekt. Ignoriert dabei
	 * Defaultwerte.
	 * @param order Die Person, die aktuallisiert werden soll.
	 * @param data Die Person, aus denen die Daten gesetzt werden sollen.
	 */
	public OrderUpdateService(Order order, Order data)
	{
		this.setOrder(order);
		this.setData(data);
	}
	
	@Override
	/**
	 * Aktuallisiert die übergebenen Order Einträge.
	 */
	public Order update() {
		Order order = this.getOrder();
		Order data = this.getData();
		if(data.getProductName() != null) {
			order.setProductName(data.getProductName());
		}
		if(data.getPrice() != null) {
			order.setPrice(data.getPrice());
		}
		if(data.getAmmount() != null) {
			order.setAmmount(data.getAmmount());
		}
		if(data.getState() != null) {
			order.setState(data.getState());
		}
		return order;
	}

}
