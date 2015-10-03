package com.logpath.app.service;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.logpath.app.service.dao.GraphEntityDAO;

@Stateless
@LocalBean
public abstract class BaseService {

	@PersistenceContext(unitName = "LogPathPU")
	private EntityManager entityManager;
	
	@EJB
	protected GraphEntityDAO graphEntityDAO;

	protected EntityManager getEntityManager() {
		return entityManager;
	}
	
}
