package de.fhws.apiprog.vorlesung3.personrest.backend.seacher.objects;

public abstract class PageInformation {

	protected Integer offset;
	protected Integer limit;
	protected Integer totalSize;
	
	public PageInformation(Integer offset, Integer limit, Integer totalSize) {
		this.setOffset(offset);
		this.setLimit(limit);
		this.setTotalSize(totalSize);
	}
	
	public Integer getOffset() {
		return offset;
	}
	
	protected void setOffset(Integer offset) {
		if(offset == null) {
			offset = 0;
		}
		this.offset = offset;
	}
	
	public Integer getLimit() {
		return limit;
	}
	
	protected void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Integer getTotalSize() {
		return totalSize;
	}

	protected void setTotalSize(Integer totalSize) {
		this.totalSize = totalSize;
	}
	
}
