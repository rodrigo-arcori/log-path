package com.logpath.app.web.rest;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.logpath.app.service.api.IApiServiceLocal;

@Path("logPath")
@Produces(MediaType.APPLICATION_JSON)
public class LogPath {
	
	@EJB IApiServiceLocal apiServiceLocal;
	
    @GET
    @Path("/message")
	public Response hello() {
    	return Response.ok( new ResponseObject()).build();
	}
    
  @POST
  @Path("/graph/create/{name}")
  public Response create( @PathParam("name") String name ) {
	  
	try {
		
		apiServiceLocal.create(name);
		
	} catch (IllegalArgumentException illegalArgumentException) {
		
		return Response.status(Status.BAD_REQUEST).build();
	}  
	  
  	
  	return Response.ok().build();
  	
  }
  
  @POST
  @Path("/graph/{source}/link/graph/{target}/{path}")
  public Response link( @PathParam("source") String source, @PathParam("target") String target, @PathParam("path") Integer path ) {
	  
	try {
		
		apiServiceLocal.link(source, target, path);
		
	} catch( IllegalArgumentException illegalArgumentException) {
		
		return Response.status(Status.BAD_REQUEST).build();	
	}
  	
  	return Response.ok().build();
  	
  }
  
  
    
//    @POST
//    @Path("/create/{name}")
//    public Response create( @PathParam("name") String name ) {
//    	
//    	Graph graph = apiServiceLocal.create(name);
//    	
//    	LogPathResponse response = new LogPathResponse();
//    	response.getGraph().setName( graph.getName() );
//    	
//    	return Response.ok( response ).build();
//    	
//    }

}
