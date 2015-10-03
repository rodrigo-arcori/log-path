package com.logpath.app.api.response;

import java.util.ArrayList;
import java.util.List;

import com.logpath.app.api.object.Route;

public class BaseResponse<T> {

	private boolean finalRoute;
	
	private List<Route<T>> routes = new ArrayList<Route<T>>();
	
	public void add(Route<T> route) {
		routes.add(route);
	}

	public Route<T> getRoute() {
		
		if( !routes.isEmpty() ) {
			
			int lastIndex = (routes.size() -1);
			
			return routes.get( lastIndex );
		}

		return null;
	}
	
	public boolean isFinalRoute() {
		return finalRoute;
	}

	public void setFinalRoute(boolean finalRoute) {
		this.finalRoute = finalRoute;
	}


}
