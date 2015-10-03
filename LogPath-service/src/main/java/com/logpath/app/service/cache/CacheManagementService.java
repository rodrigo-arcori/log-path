package com.logpath.app.service.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import com.logpath.app.api.object.Graph;
import com.logpath.app.persistence.cache.CachedGraph;
import com.logpath.app.persistence.entity.GraphEntity;
import com.logpath.app.persistence.entity.GraphEntity.PersistenceState;
import com.logpath.app.persistence.entity.RouteEntity;
import com.logpath.app.service.BaseService;


@Singleton
@Startup
public class CacheManagementService extends BaseService {

	Map<Graph, GraphEntity> persistenceCache = new ConcurrentHashMap<Graph, GraphEntity>();
	
	private PriorityQueue<CachedGraph> createQueue = new PriorityQueue<CachedGraph>();
	
	
	@PostConstruct
	public void init() {

		loadDataBase();
		
		System.out.println("### cachedMap size "+ persistenceCache.size());
	}

	private void loadDataBase() {
		
		System.out.println("### CacheManagementService loading ...");
		
		Map<String, Graph> loadMap = new HashMap<String, Graph>();
		
		List<GraphEntity> graphs = graphEntityDAO.load();

		for( GraphEntity graph : graphs ) {
			
			if ( graph.getAddress() == null ) {
				
				Graph graph2 = new Graph( graph.getName() );
				
				loadMap.put( graph.getName(), graph2  );
				
				persistenceCache.put(graph2, graph);
			
			} else if( graph.getAddress() != null ) {
				
				loadMap.get( graph.getAddress().getName() ).link( loadMap.get( graph.getName()  ) , graph.getRoute().getDistance() );
				
			}
		}
		
	}
	
	public boolean create( Graph graph ) {
		
		GraphEntity entity = persistenceCache.get(graph);
		
		if( entity == null ){
			
			entity = new GraphEntity();
			
			entity.setName( graph.getName() );
			
			persistenceCache.put(graph, entity);
			
			synchronized (createQueue) {
				
				createQueue.add( new CachedGraph(entity) );
			}
			
			return true;
		}
		
		return false;
	}
	
	public void link( Graph source, Graph target, Integer distance ) {
		
		GraphEntity cachedEntity = persistenceCache.get(source);
		
		GraphEntity entity = new GraphEntity();
		
		entity.persistenceState = PersistenceState.LINK;
		
		entity.setName( target.getName() );
		
		entity.setRoute(new RouteEntity());
		
		entity.getRoute().setDistance( distance );
		
		entity.getRoute().setLinkId( cachedEntity.getGraphId() );
		
		synchronized (createQueue) {
			
			createQueue.add( new CachedGraph(entity) );
		}
	}
	
	public void unLink(Graph source) {
		
		GraphEntity cachedEntity = persistenceCache.get(source);
		
		GraphEntity entity = new GraphEntity();
		
		entity.setGraphId( cachedEntity.getGraphId() );
		
		entity.persistenceState = PersistenceState.UNLINK;
		
		synchronized (createQueue) {
			
			createQueue.add( new CachedGraph(entity) );
		}
		
		
	}
	
	public void remove(Graph source) {
		
		GraphEntity cachedEntity = persistenceCache.get(source);
		
		GraphEntity entity = new GraphEntity();
		
		entity.setGraphId( cachedEntity.getGraphId() );
		
		entity.persistenceState = PersistenceState.REMOVE;
		
		synchronized (createQueue) {
			
			createQueue.add( new CachedGraph(entity) );
		}
		
	}
	
	@Schedule(hour="*", minute="*/2")
	public void flush() {
		
		while( !createQueue.isEmpty() ) {
			GraphEntity graphEntity = createQueue.poll().getGraphEntity();
			
			if( (graphEntity.persistenceState == PersistenceState.NEW) || (graphEntity.persistenceState == PersistenceState.LINK) )
				graphEntityDAO.create(graphEntity);
			
			if( graphEntity.persistenceState == PersistenceState.UNLINK )
				graphEntityDAO.updateUnLink(graphEntity);
			
			if( graphEntity.persistenceState == PersistenceState.REMOVE )
				graphEntityDAO.remove(graphEntity);
			
		}
		
		
	}

}
