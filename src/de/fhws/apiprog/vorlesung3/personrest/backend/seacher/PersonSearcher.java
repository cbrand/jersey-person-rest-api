package de.fhws.apiprog.vorlesung3.personrest.backend.seacher;

import static ch.lambdaj.Lambda.having;
import static ch.lambdaj.Lambda.on;
import static org.hamcrest.Matchers.equalTo;

import java.util.Collection;
import java.util.Date;

import de.fhws.apiprog.vorlesung3.personrest.objects.Coordinate;
import de.fhws.apiprog.vorlesung3.personrest.objects.Person;

public class PersonSearcher extends Searcher<Person> {

	public PersonSearcher(Collection<Person> baseSet) {
		super(baseSet);
	}

	public void byLastName(String last_name) {
		this.updateWith(having(on(Person.class).getLastName(),
				equalTo(last_name)));
	}

	public void byFirstName(String first_name) {
		this.updateWith(having(on(Person.class).getFirstName(),
				equalTo(first_name)));
	}

	public void byEmailAdress(String email_address) {
		this.updateWith(having(on(Person.class).getEmailAddress(),
				equalTo(email_address)));
	}

	public void byGeoLocation(Coordinate location) {
		this.byGeoLocation(location.getLatitude(), location.getLongitude());
	}

	public void byGeoLocation(Double latitude, Double longitude) {
		this.updateWith(having(on(Person.class).getLocation().getLatitude(),
				equalTo(latitude)));
		this.updateWith(having(on(Person.class).getLocation().getLongitude(),
				equalTo(longitude)));
	}

	public void byBirthDate(Date birth_date) {
		this.updateWith(having(on(Person.class).getBirthDate(),
				equalTo(birth_date)));
	}

}
