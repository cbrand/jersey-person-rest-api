package de.fhws.apiprog.vorlesung3.personrest.objects;

public abstract class AbstractBean implements Bean {

	private Long id;
	
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
