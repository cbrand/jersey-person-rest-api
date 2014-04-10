package de.fhws.apiprog.vorlesung3.personrest.objects;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;



@XmlRootElement
public class Person {
	private Long id;
	private String firstName;
	private String lastName;
	private Map<String,String> something;

	public Person() {
		super();
		this.setId(null);
	}

	public Person(String firstName, String lastName) {
		this();
		this.firstName = firstName;
		this.lastName = lastName;
		this.something = new HashMap<String, String>();
		this.something.put("String", "test");
	}
	
	public Map<String, String> getSomething() {
		return this.something;
	}
	
	public void setSomething(Map<String, String> something) {
		this.something = something;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result
				+ ((lastName == null) ? 0 : lastName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		return true;
	}

	public Long getId() {
		return id;
	}

	public void setId(long id) {
		this.setId(new Long(id));
	}
	
	public void setId(Long id) {
		this.id = id;
	}

}
