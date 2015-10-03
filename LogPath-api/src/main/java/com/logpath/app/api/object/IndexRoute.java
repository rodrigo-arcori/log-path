package com.logpath.app.api.object;

import java.util.TreeMap;

public class IndexRoute<K, V> extends TreeMap<K, V> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8168562770511258138L;
	
	private Integer keyDistance = new Integer(0);
	
	public V getRoute(){
		
		if( this.isEmpty() ) return null;
		
		K k = this.getKey();
		
		V v =  this.get( k );
		
		return ( v != null ? v : null );
	}

	public Integer getKeyDistance() {
		
		if( this.isEmpty() ) return new Integer(0);
		
		K k = this.getKey();
		
		this.keyDistance = new Integer( k.toString() );
		
		return keyDistance;
	}
	
	private K getKey(){
		return this.firstEntry().getKey();
	}
	
}
