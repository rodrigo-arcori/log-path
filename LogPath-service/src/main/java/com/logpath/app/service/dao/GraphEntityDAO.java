package com.logpath.app.service.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.logpath.app.persistence.entity.GraphEntity;

@Stateless
@LocalBean
public class GraphEntityDAO {

	@PersistenceContext(unitName = "LogPathPU")
	private EntityManager entityManager;
	
	public List<GraphEntity> load() {
		
		String query = "SELECT g1 FROM GraphEntity g1 LEFT OUTER JOIN GraphEntity g2 ON ( g1.route.linkId = g2.graphId ) ORDER BY g1.graphId ASC";
		
		return entityManager.createQuery( query ).getResultList();
		
	}
	
	public void create( GraphEntity graphEntity ) {
		entityManager.persist(graphEntity);
	}
	
	public void updateLink(GraphEntity graphEntity) {
		entityManager.createQuery("update GraphEntity g1 set g1.route.linkId = :linkId where g1.route.linkId = :graphId")
		.setParameter("linkId", null).setParameter("graphId", graphEntity.getGraphId() )
		.executeUpdate();
	}
	
	public void updateUnLink(GraphEntity graphEntity) {
		entityManager.createQuery("update GraphEntity g1 set g1.route.linkId = :linkId where g1.route.linkId = :graphId")
		.setParameter("linkId", null).setParameter("graphId", graphEntity.getGraphId() )
		.executeUpdate();
	}
	
	public void remove( GraphEntity graphEntity ) {
		updateLink(graphEntity);
		
		entityManager.createQuery("delete from GraphEntity g1 where g1.graphId = :graphId")
		.setParameter("graphId", graphEntity.getGraphId() ).executeUpdate();

	}

}
