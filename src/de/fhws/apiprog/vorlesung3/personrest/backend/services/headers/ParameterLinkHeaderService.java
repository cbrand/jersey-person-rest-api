package de.fhws.apiprog.vorlesung3.personrest.backend.services.headers;

import java.net.URI;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

public abstract class ParameterLinkHeaderService extends LinkHeaderService {

	private UriInfo currentUriInfo;
	
	protected UriInfo getCurrentUriInfo() {
		return currentUriInfo;
	}

	protected void setCurrentUriInfo(UriInfo currentUriInfo) {
		this.currentUriInfo = currentUriInfo;
	}
	
	protected abstract MultivaluedMap<String, String> getLinkParameters();
	
	public ParameterLinkHeaderService(UriInfo current_uri_info)
	{
		setCurrentUriInfo(current_uri_info);
	}
	
	public URI getUri()
	{
		
		UriBuilder uri_builder = getCurrentUriInfo()
				.getAbsolutePathBuilder();
		MultivaluedMap<String, String> parameter_map = getLinkParameters();
		for (String key : parameter_map.keySet()) {
			for(String value: parameter_map.get(key)) {
				uri_builder.queryParam(key, value);
			}
		}
		return uri_builder.build();
	}
	
	protected MultivaluedMap<String, String> getClonedParameters() {
		UriInfo uri_info = getCurrentUriInfo();
		MultivaluedMap<String, String> values = new MultivaluedHashMap<>();
		MultivaluedMap<String, String> query_parameters = uri_info.getQueryParameters();
		values.putAll(query_parameters);
		return values;
	}

}
