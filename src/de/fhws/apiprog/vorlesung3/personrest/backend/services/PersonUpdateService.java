package de.fhws.apiprog.vorlesung3.personrest.backend.services;

import de.fhws.apiprog.vorlesung3.personrest.objects.Person;

public class PersonUpdateService implements UpdateService<Person> {

	/**
	 * Die Person die aktuallisiert werden soll
	 */
	protected Person person;
	/**
	 * Die Daten die gesetzt werden sollen.
	 */
	protected Person data;
	
	/**
	 * Aktuallisiert ein Personenobjekt. Ignoriert dabei
	 * Defaultwerte.
	 * @param person Die Person, die aktuallisiert werden soll.
	 * @param data Die Person, aus denen die Daten gesetzt werden sollen.
	 */
	public PersonUpdateService(Person person, Person data) {
		this.person = person;
		this.data = data;
	}
	
	/**
	 * Führt die Aktuallisierung durch und gibt
	 * das Personenobjekt zurück.
	 */
	public Person update() {
		this.updateNames();
		this.updateEmailAddress();
		this.updateBirthDate();
		this.updateLocation();
		
		return this.person;
	}
	
	protected void updateNames()
	{
		if(this.data.getFirstName() != null) {
			this.person.setFirstName(
				this.data.getFirstName()
			);
		}
		if(this.data.getLastName() != null) {
			this.person.setLastName(
				this.data.getLastName()
			);
		}
	}
	
	protected void updateEmailAddress()
	{
		if(this.data.getEmailAddress() != null) {
			this.person.setEmailAddress(
				this.data.getEmailAddress()
			);
		}
	}
	
	protected void updateLocation()
	{
		if(this.data.getLocation() != null) 
		{
			if(this.person.getLocation() == null) 
			{
				this.person.setLocation(this.data.getLocation());
			} 
			else {
				this.person.setLocation(
					this.getCoordinateUpdateService().update()
				);
			}
		}
	}
	
	protected void updateBirthDate()
	{
		if(this.data.getBirthDate() != null)
		{
			this.person.setBirthDate(
				this.data.getBirthDate()
			);
		}
	}
	
	protected CoordinateUpdateService getCoordinateUpdateService() {
		return new CoordinateUpdateService(
			this.person.getLocation(), 
			this.data.getLocation()
		);
	}
	
}
