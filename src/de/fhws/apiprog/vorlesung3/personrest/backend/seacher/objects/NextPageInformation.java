package de.fhws.apiprog.vorlesung3.personrest.backend.seacher.objects;

public class NextPageInformation extends PageInformation {

	public NextPageInformation(Integer offset, Integer limit, Integer totalSize)
	{
		super(offset, limit, totalSize);
	}
	
	@Override
	public Integer getLimit()
	{
		Integer limit = super.getLimit();
		if(limit == null)
		{
			limit = getTotalSize();
		}
		
		if(getOffset() != null) 
		{
			Integer offset = getOffset();
			limit = Math.min(limit, getTotalSize() - offset);
		}
		
		return limit;
	}
	
	@Override
	public Integer getOffset()
	{
		Integer offset = super.getOffset();
		if(offset == null)
		{
			offset = 0;
		}
		if(super.getLimit() != null)
		{
			offset = offset + super.getLimit();
		}
		offset = Math.min(getTotalSize(), offset);
		
		return offset;
	}
	
}
