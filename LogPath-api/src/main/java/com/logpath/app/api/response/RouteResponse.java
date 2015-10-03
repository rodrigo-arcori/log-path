package com.logpath.app.api.response;

import java.io.Serializable;
import java.math.BigDecimal;

public class RouteResponse<T> extends BaseResponse<T> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4535027469839210419L;

	
	
	private BigDecimal result;
	
	public int distance;
	
	public BigDecimal priceLiter;
	
	public RouteResponse( int distance, BigDecimal priceLiter ) {
		
		this.distance = distance;
		
		this.priceLiter = priceLiter;
	}
	
	
	public BigDecimal getCalculateResult() {
		return result;
	}


}
