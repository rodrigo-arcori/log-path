package com.logpath.app.service.api;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.logpath.app.api.object.Graph;
import com.logpath.app.service.cache.CacheManagementService;

@Stateless
public class ApiService implements IApiServiceLocal {

	
	@Inject CacheManagementService cacheManagementService;
	
	@Override
	public void create(String name) {
		
		boolean status = cacheManagementService.create( new Graph(name) );
		
		if( !status )
			throw new IllegalArgumentException("Can not create graphs");
		
	}

	@Override
	public void link(String source, String target, Integer path) {
		
		Graph graphSource = new Graph(source);
		
		Graph graphTarget = new Graph(target);
		
		boolean status = graphSource.link(graphTarget, path);
		
		if( status )
			cacheManagementService.link(graphSource, graphTarget, path);
		else
			throw new IllegalArgumentException("Can not link graphs");
		
	}
	
	@Override
	public void unLink(String source, String target) {
	
		Graph graphSource = new Graph(source);
		
		Graph graphTarget = new Graph(target);
	}
}
