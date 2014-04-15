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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import de.fhws.apiprog.vorlesung3.personrest.backend.Backend;
import de.fhws.apiprog.vorlesung3.personrest.backend.PersonOrderBackend;
import de.fhws.apiprog.vorlesung3.personrest.backend.seacher.OrderSearcher;
import de.fhws.apiprog.vorlesung3.personrest.backend.seacher.parameter.OrderSearcherParameterHandler;
import de.fhws.apiprog.vorlesung3.personrest.objects.Order;
import de.fhws.apiprog.vorlesung3.personrest.objects.Person;

public class PersonOrderService {

	@Context
	UriInfo uriInfo;
	private Person person;
	
	protected Person getPerson() {
		return person;
	}

	protected void setPerson(Person person) {
		this.person = person;
	}

	public PersonOrderService(Person person)
	{
		setPerson(person);
	}
	
	protected Backend<Order> getBackend()
	{
		return new PersonOrderBackend(getPerson());
	}
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response search()
	{
		OrderSearcherParameterHandler handler = 
				new OrderSearcherParameterHandler(
						(OrderSearcher)getBackend().search()
						);
		try {
			handler.apply(uriInfo.getQueryParameters(true));
		}
		catch(IllegalArgumentException e) {
			throw new WebApplicationException(
					e.getMessage(), 
					Response.Status.BAD_REQUEST
					);
		}
		return Response.ok(handler.getResult()).build();
	}
	
	@GET
	@Path("/{orderId: \\d+}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response get(@PathParam("orderId") long id) 
	{
		Order order;
		try {
			order = getBackend().get(id);
		}
		catch(KeyException e) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		return Response.ok(order).build();
	}
	
	@POST
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response post(Order order) {
		getBackend().add(order);
		return Response.created(
			this.uriInfo.getAbsolutePathBuilder().path(
				new Long(order.getId()).toString()
			).build()
		).build();
	}
	
	@PUT
	@Path("/{orderId: \\d+}")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response put(@PathParam("orderId") long id, Order order) {
		try {
			getBackend().update(id, order);
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
	@Path("/{orderId: \\d+}")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response delete(@PathParam("orderId") long id) {
		try {
			getBackend().delete(id);
		}
		catch(KeyException e) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		return Response.noContent().build();
	}
	
}
