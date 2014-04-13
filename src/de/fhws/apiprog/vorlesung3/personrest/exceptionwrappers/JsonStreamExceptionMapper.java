package de.fhws.apiprog.vorlesung3.personrest.exceptionwrappers;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.owlike.genson.stream.JsonStreamException;

@Provider
public class JsonStreamExceptionMapper implements
		ExceptionMapper<JsonStreamException> {

	@Override
    public Response toResponse(JsonStreamException exception)
    {
		
        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity( "Could not decode JSON.")
                .type( MediaType.TEXT_PLAIN )
                .build();
    }
	
}
