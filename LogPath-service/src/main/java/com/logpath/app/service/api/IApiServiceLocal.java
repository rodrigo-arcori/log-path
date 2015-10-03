package com.logpath.app.service.api;

import javax.ejb.Local;

@Local
public interface IApiServiceLocal {

//	Graph create(String name);
	
	void create(String name);
	
	void link(String source, String target, Integer path);
	
	void unLink(String source, String target);
	
	
	
}
