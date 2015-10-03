package com.logpath.app.web.rest.response;

import java.io.Serializable;

public class LogPathResponse implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -6451620781180202769L;
	private GraphResponse graph = new GraphResponse();

	public GraphResponse getGraph() {
		return graph;
	}

	public void setGraph(GraphResponse graph) {
		this.graph = graph;
	}

	public static class  GraphResponse {
		
		private String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
		
		
	}
	
	
}
