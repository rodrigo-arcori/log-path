package com.logpath.app.api.object;

import java.io.Serializable;

public class Route<T> implements Serializable, Comparable<Route<T>> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7450159092450114625L;

	Integer distance = Integer.MAX_VALUE;

	Integer weight = 0;
	
	AbstractGraph<T> graph;
	
	public Route(){
		
	}
	
	public Route(AbstractGraph<T> graph) {
		this.graph = graph;
	}
	
	public AbstractGraph<T> getGraph() {
		return graph;
	}

	public int compareTo(Route<T> o) {
		return Integer.compare(distance, o.distance);
	}
	
	@Override
	public String toString() {
		return String.format("Route{ grpah:%s, weight:%s }",  graph, weight);
	}

}
