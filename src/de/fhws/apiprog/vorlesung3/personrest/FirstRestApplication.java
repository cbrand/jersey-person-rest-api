package de.fhws.apiprog.vorlesung3.personrest;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/personapi")
public class FirstRestApplication extends Application {
	@Override
	public Set<Class<?>> getClasses() {
		final Set<Class<?>> returnValue = new HashSet<Class<?>>();
		returnValue.add(PersonService.class);
		return returnValue;
	}
}