package de.fhws.apiprog.vorlesung3.personrest;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import de.fhws.apiprog.vorlesung3.personrest.exceptionwrappers.JsonStreamExceptionMapper;

@ApplicationPath("/personapi")
public class PersonApplication extends Application {
	@Override
	public Set<Class<?>> getClasses() {
		final Set<Class<?>> classes = new HashSet<Class<?>>();
		classes.add(JsonStreamExceptionMapper.class);
		classes.add(PersonService.class);
		return classes;
	}
}