package com.logpath.app.persistence.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class RouteEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5685126204116407102L;

	@Column
	private Integer distance;
	
	@Column
	private Integer linkId;

	public Integer getDistance() {
		return distance;
	}

	public void setDistance(Integer distance) {
		this.distance = distance;
	}

	public Integer getLinkId() {
		return linkId;
	}

	public void setLinkId(Integer linkId) {
		this.linkId = linkId;
	}
	
}
