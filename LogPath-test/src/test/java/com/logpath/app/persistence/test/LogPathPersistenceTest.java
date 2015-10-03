package com.logpath.app.persistence.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.logpath.app.api.object.Graph;
import com.logpath.app.persistence.cache.CachedGraph;
import com.logpath.app.persistence.entity.GraphEntity;
import com.logpath.app.persistence.entity.RouteEntity;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LogPathPersistenceTest {

	private static EntityManager em = null;
	
//	@BeforeClass
    public static void setUpClass() throws Exception {
        if (em == null) {
            em = (EntityManager) Persistence.createEntityManagerFactory("LogPathPU").createEntityManager();
        }
    }
	
//	@Test
	public final void a_create() {
		
		em.getTransaction().begin();
		
		GraphEntity osasco = new GraphEntity();
		
		osasco.setName( "Osasco" );
		
		em.persist(osasco);
		
		GraphEntity cotia = new GraphEntity();
		
		cotia.setName( "Cotia" );
		
		em.persist(cotia);
		
		em.getTransaction().commit();		
	}
	
//	@Test
	public final void b_link() {
		
		em.getTransaction().begin();
		GraphEntity entitySource;
		
		
		
		try {
			
			entitySource = (GraphEntity) em.createQuery("select g1 from GraphEntity g1 where g1.name = :name and g1.route.linkId is null")
					.setParameter("name", "Osasco") .getSingleResult();

		} catch (Exception e) {
			entitySource = null;
		}

		
		GraphEntity entityTarget = new GraphEntity();
		entityTarget.setName("Cotia");
		entityTarget.setRoute(new RouteEntity());
		entityTarget.getRoute().setDistance( 10 );
		entityTarget.getRoute().setLinkId( entitySource.getGraphId() );
		
		em.persist( entityTarget );
		em.getTransaction().commit();
	}
	
//	@Test
	public final void c_load(){
		
//		StringBuilder query = new StringBuilder(); 
//		query.append("SELECT graph.grapId, graph.Name as \"Graph\", link.Name as \"Link\", graph.distance \"Distance\""); 
//		query.append(" FROM APP.GRAPH graph");
//		query.append(" LEFT OUTER JOIN GRAPH link");
//		query.append(" ON graph.linkid = link.grapid");
//		query.append( "SELECT grapId, name, distance, linkId FROM APP.GRAPH" );
		
		em.getTransaction().begin();
				
		List<GraphEntity> list = em.createQuery( "select g1 from GraphEntity g1 left outer join GraphEntity g2 on ( g1.route.linkId = g2.graphId) order by g1.graphId asc" ).getResultList();
		
		
		Map<String, Graph> loadMap = new HashMap<String, Graph>();
		
		Map<Graph, CachedGraph> cachedMap = new HashMap<Graph, CachedGraph>();

		for( GraphEntity graph : list ) {
			
			if ( graph.getAddress() == null ) {
				
				Graph graph2 = new Graph( graph.getName() );
				
				loadMap.put( graph.getName(), graph2  );
				
//				cachedMap.put(graph2, new CachedGraph());
			
			} else if( graph.getAddress() != null ) {
				
				loadMap.get( graph.getAddress().getName() ).link( loadMap.get( graph.getName()  ) , graph.getRoute().getDistance() );
				
			}
		}
		
	}
	
//	@Test
	public final void d_unlink(){

		GraphEntity entitySource;
		
		try {
			
			entitySource = (GraphEntity) em.createQuery("select g1 from GraphEntity g1 where g1.name = :name and g1.route.linkId is null")
					.setParameter("name", "Osasco") .getSingleResult();

		} catch (Exception e) {
			entitySource = null;
		}
		
		em.createQuery("update GraphEntity g1 set g1.route.linkId = :linkId where g1.route.linkId = :graphId")
		.setParameter("linkId", null).setParameter("graphId", entitySource.getGraphId() ).executeUpdate();
		
		em.getTransaction().commit();
	}
	
//	@Test
	public final void e_remove() {

		em.getTransaction().begin();
		
		GraphEntity entitySource;
		
		try {
			
			entitySource = (GraphEntity) em.createQuery("select g1 from GraphEntity g1 where g1.name = :name and g1.route.linkId is null")
					.setParameter("name", "Osasco") .getSingleResult();

		} catch (Exception e) {
			entitySource = null;
		}
		
		em.createQuery("update GraphEntity g1 set g1.route.linkId = :linkId where g1.route.linkId = :graphId")
		.setParameter("linkId", null).setParameter("graphId", entitySource.getGraphId() ).executeUpdate();
		
		em.createQuery("delete from GraphEntity g1 where g1.graphId = :graphId")
		.setParameter("graphId", entitySource.getGraphId() ).executeUpdate();
		
		em.getTransaction().commit();
	}
	
}
