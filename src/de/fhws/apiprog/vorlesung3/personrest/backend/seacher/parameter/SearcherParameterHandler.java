package de.fhws.apiprog.vorlesung3.personrest.backend.seacher.parameter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import javax.ws.rs.core.MultivaluedMap;

import de.fhws.apiprog.vorlesung3.personrest.backend.seacher.Searcher;
import de.fhws.apiprog.vorlesung3.personrest.helpers.NumberHelper;

/**
 * Handelt ein übergebenes Parameterdictionary, welches
 * er mittels Reflektion auf den übergebenen Searcher
 * mappt.
 */
public abstract class SearcherParameterHandler<Bean, S extends Searcher<Bean>> {

	protected S searcher;
	
	public SearcherParameterHandler(S searcher)
	{
		this.searcher = searcher;
	}
	
	public void apply(MultivaluedMap<String, String> parameters)
	{
		for(String key: parameters.keySet())
		{
			applyArgumentIfSupported(key, parameters.get(key));
		}
		
		if(parameters.containsKey("offset")) {
			applyOffset(parameters.getFirst("offset"));
		}
		if(parameters.containsKey("limit")) {
			applyLimit(parameters.getFirst("limit"));
		}
	}
	
	public List<Bean> getResult() {
		return ((Searcher<Bean>)this.searcher).getResult();
	}
	
	protected void applyArgumentIfSupported(String key, List<String> values)
	{
		try {
			this.queryMethodFor(key);
		}
		catch(NoSuchMethodException e) {
			// Not supported
			return;
		}
		this.applyArgument(key, values);
	}
	
	protected void applyArgument(String key, List<String> values)
	{
		Method query_method;
		try {
			query_method = this.queryMethodFor(key);
		}
		catch(NoSuchMethodException e) {
			throw new IllegalArgumentException("The search key does not exist.");
		}
		for(String value: values) {
			try {
				query_method.invoke(this.searcher, value);
			} catch (IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				throw new IllegalArgumentException(e);
			}
		}
	}
	
	protected void applyOffset(String offset)
	{
		if(NumberHelper.isNumber(offset)) {
			this.applyOffset(new Integer(offset));
		}
		else {
			throw new IllegalArgumentException(
					String.format("Offset must be a number. Got %s.", offset)
				);
		}
	}
	
	protected void applyOffset(int offset)
	{
		this.searcher.offset(offset);
	}
	
	protected void applyLimit(String limit)
	{
		if(NumberHelper.isNumber(limit)) {
			this.applyLimit(new Integer(limit));
		}
		else {
			throw new IllegalArgumentException(
					String.format("Limit must be a number. Got %s.", limit)
				);
		}
	}
	
	protected void applyLimit(int limit)
	{
		this.searcher.limit(limit);
	}
	
	protected abstract Method queryMethodFor(String key) throws NoSuchMethodException, SecurityException;
	
	protected String getSearchMethodName(String key)
	{
		if(key.length() <= 1) {
			// Wir akzeptieren nur Strings die mindestens zwei Zeichen besitzen.
			return "";
		}
		else {
			return String.format("by%s%s", Character.toUpperCase(key.charAt(0)), key.substring(1));
		}
	}
	
}
