package com.logpath.app.api.object;

import java.io.Serializable;

public class Graph extends AbstractGraph<Graph> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1515218342013391158L;
	
	public Graph( String name ) {
		
		this.name = name;
		
	}

	@Override
	public String toString() {
		return String.format("Graph{ name:%s }", this.name);
	}
}
