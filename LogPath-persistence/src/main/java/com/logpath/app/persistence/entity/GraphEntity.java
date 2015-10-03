package com.logpath.app.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Table(name="APP.GRAPH")
@Entity
public class GraphEntity {
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer graphId;
	
	@Column
	private String name;
	
	@Embedded
	RouteEntity route;
	
	@Transient
	public PersistenceState persistenceState = PersistenceState.NEW; 
	
	public Integer getGraphId() {
		return graphId;
	}

	public void setGraphId(Integer graphId) {
		this.graphId = graphId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public RouteEntity getRoute() {
		return route;
	}

	public void setRoute(RouteEntity route) {
		this.route = route;
	}
	
	@OneToOne(fetch=FetchType.LAZY)
	  @JoinColumn(name="linkId", insertable=false, updatable=false)
	  private GraphEntity address;

	public GraphEntity getAddress() {
		return address;
	}

	public void setAddress(GraphEntity address) {
		this.address = address;
	}
	
	public enum PersistenceState {
		NEW, LINK, UNLINK, REMOVE;
	}

}
