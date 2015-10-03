package com.logpath.app.persistence.cache;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import com.logpath.app.persistence.entity.GraphEntity;

public final class CachedGraph implements Serializable, Comparable<CachedGraph> {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2887030784665834826L;

	private GraphEntity graphEntity = new GraphEntity();
	
	private Date timestamp = Calendar.getInstance().getTime();
	
	public CachedGraph( GraphEntity graphEntity ) {
		this.graphEntity = graphEntity;
	}

	public GraphEntity getGraphEntity() {
		return graphEntity;
	}
	
	@Override
	public int compareTo(CachedGraph o) {
		return timestamp.compareTo(o.timestamp);
	}
	

}
