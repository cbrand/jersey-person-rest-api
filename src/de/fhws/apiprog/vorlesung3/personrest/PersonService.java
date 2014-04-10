package de.fhws.apiprog.vorlesung3.personrest;

import java.security.KeyException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import de.fhws.apiprog.vorlesung3.personrest.backend.PersonBackend;
import de.fhws.apiprog.vorlesung3.personrest.objects.Person;

@Path("/persons")
public class PersonService {
	
	@Context
	UriInfo uriInfo;

	@GET
	@Path("/{personId: \\d+}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getPerson(@PathParam("personId") long id) 
	{
		PersonBackend person_query = new PersonBackend();
		Person person;
		try {
			person = person_query.getPerson(id);
		}
		catch(KeyException e) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		return Response.ok(person).build();
	}
	
	@POST
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response post(Person person) {
		PersonBackend person_backend = new PersonBackend();
		person_backend.addPerson(person);
		return Response.created(
			this.uriInfo.getAbsolutePathBuilder().path(
				new Long(person.getId()).toString()
			).build()
		).build();
	}
	
}
