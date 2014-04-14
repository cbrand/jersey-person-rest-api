package de.fhws.apiprog.vorlesung3.personrest.objects;

import java.security.KeyException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.owlike.genson.annotation.JsonIgnore;

import de.fhws.apiprog.vorlesung3.personrest.helpers.MailAddressValidator;


@XmlRootElement
public class Person extends AbstractBean {
	private Long id;
	private String firstName;
	private String lastName;
	private String emailAddress;
	private Date birthDate;
	private Coordinate location;
	@XmlTransient
	@JsonIgnore
	private Map<Long, Order> orders;

	public Person() {
		super();
		this.orders = new HashMap<Long, Order>();
	}

	public Person(String firstName, String lastName) {
		this();
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		if(emailAddress == null || 
				MailAddressValidator.isValid(emailAddress)
				) {
			this.emailAddress = emailAddress;
		}
		else {
			throw new IllegalArgumentException("Not a valid mail address.");
		}
	}
	

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public Coordinate getLocation() {
		return location;
	}

	public void setLocation(Coordinate location) {
		this.location = location;
	}
	
	public void setLocation(double latitude, double longitude)
	{
		this.setLocation(new Coordinate(latitude, longitude));
	}
	
	/**
	 * Klont das Personenobjekt und gibt es zurück.
	 */
	public Person clone() {
		Person new_person = new Person();
		new_person.setId(this.getId());
		new_person.setFirstName(this.getFirstName());
		new_person.setLastName(this.getLastName());
		new_person.setEmailAddress(this.getEmailAddress());
		new_person.setBirthDate(this.getBirthDate());
		new_person.setLocation(this.getLocation().clone());
		for(Order order: getOrders()) {
			new_person.addOrder(order.copy());
		}
		return new_person;
	}
	
	public Iterable<Order> getOrders() {
		return orders.values();
	}
	
	public Order getOrder(Long order_id) throws KeyException {
		if(orders.containsKey(order_id)) {
			return orders.get(order_id);
		}
		else {
			throw new KeyException(
					String.format("Order id %s not found", order_id)
					);
		}
	}
	
	public void addOrder(Order order) {
		orders.put(order.getId(), order);
	}
	
	public void clearOrders() {
		orders.clear();
	}
	
	public void removeOrder(Long order_id) throws KeyException {
		if(orders.remove(order_id) == null)
		{
			throw new KeyException(
					String.format("Order with id %s not found.", order_id)
					);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((birthDate == null) ? 0 : birthDate.hashCode());
		result = prime * result
				+ ((emailAddress == null) ? 0 : emailAddress.hashCode());
		result = prime * result
				+ ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result
				+ ((location == null) ? 0 : location.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Person other = (Person) obj;
		if (birthDate == null) {
			if (other.birthDate != null) {
				return false;
			}
		} else if (!birthDate.equals(other.birthDate)) {
			return false;
		}
		if (emailAddress == null) {
			if (other.emailAddress != null) {
				return false;
			}
		} else if (!emailAddress.equals(other.emailAddress)) {
			return false;
		}
		if (firstName == null) {
			if (other.firstName != null) {
				return false;
			}
		} else if (!firstName.equals(other.firstName)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (lastName == null) {
			if (other.lastName != null) {
				return false;
			}
		} else if (!lastName.equals(other.lastName)) {
			return false;
		}
		if (location == null) {
			if (other.location != null) {
				return false;
			}
		} else if (!location.equals(other.location)) {
			return false;
		}
		return true;
	}

}
