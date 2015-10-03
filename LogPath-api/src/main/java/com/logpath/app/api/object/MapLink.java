package com.logpath.app.api.object;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import com.logpath.app.api.response.BaseResponse;
import com.logpath.app.api.response.RouteResponse;


public class MapLink<T> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7914787761163433545L;

	private Map<AbstractGraph<T>, Route<T>> indexGraphRoute = new HashMap<AbstractGraph<T>, Route<T>>();
	
	private Map<AbstractGraph<T>, Route<T>> masterTree = new HashMap<AbstractGraph<T>, Route<T>>();
	
	AbstractGraph<T> hook;
	
	boolean hasLink;
	
	private final List<Route<T>> listGraph = new LinkedList<Route<T>>();
	
	private List<AbstractGraph<T>> listLink = new CopyOnWriteArrayList<AbstractGraph<T>>();
	
	public boolean merge( AbstractGraph<T> source, AbstractGraph<T> target, Integer path ) {
		
		try {
			
			this.hook = source;
			
			if( this.hook.equals(target) ) return false;
			
			Route<T> route = indexGraphRoute.get(target);
			
			if( route != null ) {
				
				route.distance = path;
				
			} else {
				
				route = new Route<T>();
				route.distance = path;
				route.graph = target;
				
				indexGraphRoute.put(target, route);
				
				listGraph.add( route );
			}
			
			
			return true;
			
		} catch (NullPointerException nullPointerException) {
			
			 return false;
		}
		
	}
	
	public List<AbstractGraph<T>> getListLink() {
		return listLink;
	}

	public boolean remove( AbstractGraph<T> target ) {
		
		try {
			
			Route<T> route = indexGraphRoute.get(target);
			
			if( route != null && route.graph.equals(target) ) {
				
				boolean removed = listGraph.remove(route);
				
				if( removed ) 
					indexGraphRoute.remove(target);
				
				return removed;
			}
			
		} catch (NullPointerException e) {
			return false;
		}
		
		return false;
	}
	
	Integer getDistance( AbstractGraph<T> target, BaseResponse<T> routeResponse ) {
		
		searchNext(target, 0, routeResponse);
		
		return routeResponse.getRoute().weight;
	}
	
	boolean search( AbstractGraph<T> target, RouteResponse<T> routeResponse ) {
		
		return searchNext(target, 0, routeResponse);
		
	}

	
	private boolean searchNext( AbstractGraph<T> target, Integer path, BaseResponse<T> routeResponse ) {
		
		
		if( !listGraph.isEmpty() ) {
			
			Collections.sort( listGraph );
			
			Route<T> route = listGraph.get(0);
			
			routeResponse.add(route);
			
			Integer sum = path;
			
			route.weight = route.distance + sum;
			
			if( route.graph.equals(target) ) { 
				routeResponse.setFinalRoute( true );
				return routeResponse.isFinalRoute();
			}
			
			route.graph.map.searchNext( target , route.weight, routeResponse );
			
		}
		
		return routeResponse.isFinalRoute();
		
	}
	
	

}
