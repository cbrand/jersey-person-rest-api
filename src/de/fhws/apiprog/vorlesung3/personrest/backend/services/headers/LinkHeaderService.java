package de.fhws.apiprog.vorlesung3.personrest.backend.services.headers;

import java.net.URI;
import javax.ws.rs.core.Response.ResponseBuilder;

public abstract class LinkHeaderService {
	
	protected abstract String getRelName();
	
	public abstract URI getUri();
	
	public ResponseBuilder apply(ResponseBuilder response_builder)
	{
		return response_builder.link(getUri(), getRelName());
	}
	
}
