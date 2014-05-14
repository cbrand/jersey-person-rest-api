package de.fhws.apiprog.vorlesung3.personrest;

import java.security.KeyException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.UriInfo;

import de.fhws.apiprog.vorlesung3.personrest.backend.PersonBackend;
import de.fhws.apiprog.vorlesung3.personrest.backend.seacher.parameter.PersonSearcherParameterHandler;
import de.fhws.apiprog.vorlesung3.personrest.objects.Person;

@Path("/persons")
public class PersonService extends AbstractService {
	
	@Context
	UriInfo uriInfo;

	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response search()
	{
		PersonSearcherParameterHandler handler = 
				new PersonSearcherParameterHandler(
						getBackend().search()
						);
		try {
			handler.apply(uriInfo.getQueryParameters());
		}
		catch(IllegalArgumentException e) {
			throw new WebApplicationException(
					e.getMessage(), 
					Response.Status.BAD_REQUEST
					);
		}
		ResponseBuilder response_builder = Response.ok(handler.getResult());
		return applyPagination(
			response_builder,
			handler
		).build();
	}
	
	@GET
	@Path("/{personId: \\d+}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response get(@PathParam("personId") long id) 
	{
		return Response.ok(personByIdOrRaise(id)).build();
	}
	
	@POST
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response post(Person person) {
		getBackend().add(person);
		return Response.created(
			this.uriInfo.getAbsolutePathBuilder().path(
				new Long(person.getId()).toString()
			).build()
		).build();
	}
	
	@PUT
	@Path("/{personId: \\d+}")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response put(@PathParam("personId") long id, Person person) {
		try {
			getBackend().update(id, person);
		} catch(KeyException e) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		} catch(IllegalArgumentException e) {
			throw new WebApplicationException(Response.Status.BAD_REQUEST);
		}
		return Response.noContent().location(
			this.uriInfo.getAbsolutePathBuilder().build()
		).build();
	}
	
	@DELETE
	@Path("/{personId: \\d+}")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response delete(@PathParam("personId") long id) {
		try {
			getBackend().delete(id);
		}
		catch(KeyException e) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		return Response.noContent().build();
	}
	
	@Path("/{personId: \\d+}/orders")
    public PersonOrderService getPersonOrderService(
    		@Context ResourceContext rc, 
    		@PathParam("personId") long id) {
		Person person = personByIdOrRaise(id);
        return rc.initResource(new PersonOrderService(person));
    }
	
	protected Person personByIdOrRaise(long id)
	{
		Person person;
		try {
			person = getBackend().get(id);
		}
		catch(KeyException e) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		return person;
	}
	
	protected PersonBackend getBackend()
	{
		return new PersonBackend();
	}
	
}
