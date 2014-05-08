package de.fhws.apiprog.vorlesung3.personrest.backend.seacher;

import static ch.lambdaj.Lambda.select;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hamcrest.Matcher;

public class Searcher<T> {
	
	protected List<T> searchResults;
	protected int limit;
	protected boolean use_limit;
	protected int offset;
	protected boolean use_offset;
	protected int total_size;
	
	public Searcher(Collection<T> baseSet) {
		super();
		
		searchResults = new ArrayList<T>();
		searchResults.addAll(baseSet);
	}
	
	public List<T> getResult() {
		List<T> return_list = new ArrayList<T>();
		int to_use_offset = 0;
		int to_use_limit = searchResults.size();
		setTotalSize(to_use_limit);
		
		if(use_offset)
		{
			to_use_offset = offset;
		}
		if(use_limit)
		{
			to_use_limit = limit;
		}
		
		to_use_offset = Math.min(to_use_offset, searchResults.size());
		to_use_limit = Math.max(
				Math.min(to_use_limit, searchResults.size() - to_use_offset),
				0
				);
		
		return_list = searchResults.subList(
				to_use_offset, 
				to_use_offset + to_use_limit
				);
	
		
		return return_list;
	}
	
	public void updateWith(Matcher<?> matcher)
	{
		searchResults = select(searchResults, matcher);
	}
	
	public void limit(int limit)
	{
		this.limit = limit;
		use_limit = true;
	}
	
	public void offset(int offset)
	{
		this.offset = offset;
		use_offset = true;
	}

	public int getTotalSize() {
		return total_size;
	}

	protected void setTotalSize(int total_size) {
		this.total_size = total_size;
	}
	
}
