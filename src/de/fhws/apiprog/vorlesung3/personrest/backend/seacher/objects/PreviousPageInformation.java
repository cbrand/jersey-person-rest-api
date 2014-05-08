package de.fhws.apiprog.vorlesung3.personrest.backend.seacher.objects;

public class PreviousPageInformation extends PageInformation {

	public PreviousPageInformation(int used_offset, int used_limit, int total_size)
	{
		super(used_offset, used_limit, total_size);
	}
	
	@Override
	public Integer getOffset()
	{
		Integer offset = super.getOffset();
		if(offset == null) {
			offset = 0;
		}
		if(getLimit() != null) {
			offset = offset - getLimit();
		}
		
		offset = Math.min(
			offset,
			0
		);
		
		return offset;
	}
	
}
