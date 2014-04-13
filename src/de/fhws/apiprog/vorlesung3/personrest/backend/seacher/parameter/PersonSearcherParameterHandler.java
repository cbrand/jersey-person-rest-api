package de.fhws.apiprog.vorlesung3.personrest.backend.seacher.parameter;

import java.lang.reflect.Method;

import de.fhws.apiprog.vorlesung3.personrest.backend.seacher.PersonSearcher;
import de.fhws.apiprog.vorlesung3.personrest.objects.Person;

public class PersonSearcherParameterHandler extends
		SearcherParameterHandler<Person, PersonSearcher> {

	public PersonSearcherParameterHandler(PersonSearcher searcher)
	{
		super(searcher);
	}
	
	@Override
	protected Method queryMethodFor(String key) throws NoSuchMethodException,
			SecurityException {
		return PersonSearcher.class.getMethod(getSearchMethodName(key), String.class);
	}

}
