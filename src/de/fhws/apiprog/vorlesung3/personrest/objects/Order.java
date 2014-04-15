package de.fhws.apiprog.vorlesung3.personrest.objects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Order extends AbstractBean {

	public static enum OrderStates {
		NEW ("New"), 
		IN_PROGRESS ("In Progress"), 
		DELIVERED ("Delivered");
		
		private OrderStates(final String text) {
	        this.text = text;
	    }

	    private final String text;

	    @Override
	    public String toString() {
	        return text;
	    }
	}
	
	private String productName;
	private Integer ammount;
	private Double price;
	private String state;
	
	public Order() {
		super();
	}
	
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Integer getAmmount() {
		return ammount;
	}
	public void setAmmount(Integer ammount) {
		this.ammount = ammount;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getState() {
		return state;
	}
	
	public List<String> getPossibleStateNames()
	{
		ArrayList<String> stateNames = new ArrayList<>();
		for(OrderStates state: Arrays.asList(OrderStates.values()))
		{
			stateNames.add(state.toString());
		}
		return stateNames;
	}
	
	public void setState(OrderStates state)
	{
		setState(state.toString());
	}
	public void setState(String state) {
		if(
				state != null && !getPossibleStateNames().contains(state)
				)
		{
			throw new IllegalArgumentException(
					String.format(
							"Unrecognized state %s",
							state
							)
					);
		}
		this.state = state;
	}
	
	public Order clone()
	{
		Order clone = new Order();
		clone.setId(getId());
		clone.setAmmount(getAmmount());
		clone.setPrice(getPrice());
		clone.setProductName(getProductName());
		clone.setState(getState());
		return clone;
	}
	
	
	
}
