package com.logpath.app.service.dao;

import java.util.List;

import com.logpath.app.persistence.entity.GraphEntity;

public interface IGraphEntityDAO {
	
	List<GraphEntity> load();

}
