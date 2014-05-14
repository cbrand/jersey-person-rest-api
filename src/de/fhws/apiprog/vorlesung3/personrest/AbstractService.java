package de.fhws.apiprog.vorlesung3.personrest;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.ResponseBuilder;

import de.fhws.apiprog.vorlesung3.personrest.backend.seacher.objects.PageInformation;
import de.fhws.apiprog.vorlesung3.personrest.backend.seacher.parameter.SearcherParameterHandler;
import de.fhws.apiprog.vorlesung3.personrest.backend.services.headers.PaginationService;

public abstract class AbstractService {

	@Context
	UriInfo uriInfo;
	
	protected ResponseBuilder applyPagination(
			ResponseBuilder response_builder, 
			SearcherParameterHandler<?, ?> handler
		)
	{
		if(handler.getPreviousPage() != null) {
			response_builder = applyBeforePagination(response_builder, handler.getPreviousPage());
		}
		if(handler.getNextPage() != null) {
			response_builder = applyAfterPagination(response_builder, handler.getNextPage());
		}
		return response_builder;
	}
	
	protected ResponseBuilder applyBeforePagination(
			ResponseBuilder response_builder,
			PageInformation before
			) {
		PaginationService pagination_service = new PaginationService(uriInfo, before, "before");
		return pagination_service.apply(response_builder);
	}
	
	protected ResponseBuilder applyAfterPagination(
			ResponseBuilder response_builder,
			PageInformation after
			) {
		PaginationService pagination_service = new PaginationService(uriInfo, after, "after");
		return pagination_service.apply(response_builder);
	}
	
}
