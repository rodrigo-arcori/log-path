package com.logpath.app.api.object;

import java.util.List;

import com.logpath.app.api.response.BaseResponse;
import com.logpath.app.api.response.RouteResponse;


public abstract class AbstractGraph< T > {

	protected String name;
	
	protected MapLink<T> map = new MapLink<T>();
	
	public boolean link( AbstractGraph<T> target, Integer path ) {
		
		return map.merge(this, target, path);
	}
	
	public boolean unLink( AbstractGraph<T> target ) {
		
		return map.remove(target);
	}
	
	public boolean findRoute( AbstractGraph<T> target, RouteResponse<T> routeResponse ) {
		
		return map.search(target, routeResponse);
	}

	public Integer getDistanceTo( AbstractGraph<T> target ) {
		
		return map.getDistance(target, new BaseResponse<T>() );
	}
	
	public List<AbstractGraph<T>> getLinks(){
		return map.getListLink();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractGraph<T> other = (AbstractGraph<T>) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
}
