package de.fhws.apiprog.vorlesung3.personrest.backend.services.headers;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

import de.fhws.apiprog.vorlesung3.personrest.backend.seacher.objects.PageInformation;

/**
 * Hilfsklasse zum hinzufügen der Hypermedia APIs.
 */
public class PaginationService extends ParameterLinkHeaderService {

	private PageInformation pageInformation;
	private String relName;
	
	protected PageInformation getPageInformation() {
		return pageInformation;
	}

	protected void setPageInformation(PageInformation pageInformation) {
		this.pageInformation = pageInformation;
	}

	@Override
	protected String getRelName() {
		return relName;
	}

	protected void setRelName(String relName) {
		this.relName = relName;
	}

	public PaginationService(
			UriInfo current_uri_info,
			PageInformation page_information, 
			String rel_name
			) {
		super(current_uri_info);
		setPageInformation(page_information);
		setRelName(rel_name);
	}
	
	protected MultivaluedMap<String, String> getLinkParameters() {
		MultivaluedMap<String, String> values = getClonedParameters();
		PageInformation page_information = getPageInformation();
		values.putSingle("limit", page_information.getLimit().toString());
		values.putSingle("offset", page_information.getOffset().toString());
		
		return values;
	}
	
}
