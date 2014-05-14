package de.fhws.apiprog.vorlesung3.personrest.backend.services.headers;

import javax.ws.rs.core.Response.ResponseBuilder;

public abstract class LinkHeaderService {
	
	protected abstract String getRelName();
	
	public abstract String getURIString();
	
	public String getLinkHeaderValue()
	{
		return String.format("<%s>; rel=\"%s\"", getURIString(), getRelName());
	}
	
	public ResponseBuilder apply(ResponseBuilder response_builder)
	{
		return response_builder.header("Link", getLinkHeaderValue());
	}
	
}
