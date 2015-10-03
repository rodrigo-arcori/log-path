package com.logpath.app.api.test;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

import com.logpath.app.api.object.Graph;
import com.logpath.app.api.response.RouteResponse;

public class GraphTest {
	
	private final String OSASCO = "OSASCO";
	
	private final String COTIA = "COTIA";
	
	private final String CARAPICUIBA = "CARAPICUÍBA";
	
	private final String BARUERI = "BARUERI";
	
	private final String ST_PARNAIBA = "ST.PARNAÍBA";
	
	private final String PIRITUBA = "PIRITUBA";
	
	@Test
	public final void teste(){
		
		Graph osasco = new Graph( OSASCO );
		
		Graph barueri = new Graph( BARUERI );
		
		Graph stParnaiba = new Graph( ST_PARNAIBA );
		
		Assert.assertTrue( "Can not link graph" , osasco.link( barueri, 10 ) );
		
		Assert.assertTrue( "Can not link graph" , osasco.link( barueri, 20 ) );
		
		Assert.assertTrue( "Can not link graph" , barueri.link( stParnaiba, 10 ) );
		
		RouteResponse< Graph > routeResponse = new RouteResponse< Graph >( 10 , new BigDecimal( "2.50" ));
		
		Graph xxx = new Graph( "xxx" );
		
		Assert.assertTrue( "Can not link graph" , xxx.link(osasco, 5) );

		barueri.findRoute(stParnaiba, routeResponse );
		
		Integer distance = xxx.getDistanceTo(stParnaiba);
		
		osasco.unLink(barueri);
		
		osasco.findRoute(stParnaiba, routeResponse );
		
	}
	
//	@Test
	public void validState() {
		
		Graph osasco = new Graph( OSASCO );
		
		Graph barueri = new Graph( BARUERI );
		
		Graph stParnaiba = new Graph( ST_PARNAIBA );
		
		Assert.assertTrue( "Can not link graph" , osasco.link( barueri, 10 ) );
		
		Assert.assertTrue( "Can not link graph" , barueri.link( stParnaiba, 10 ) );
		
		Integer distanceExpected = new Integer( 20 );
		
		Assert.assertEquals( "Inconsistency distance between graphs", distanceExpected  , osasco.getDistanceTo( stParnaiba ) );
		
		Assert.assertTrue( "Can not unlink graph" , barueri.unLink( stParnaiba ) );
		
		Graph pirituba = new Graph( PIRITUBA );
		
		pirituba.link(osasco, 5);
		
		Assert.assertEquals( "Inconsistency distance between graphs", distanceExpected  , osasco.getDistanceTo( stParnaiba ) );
		
	}
	
//	@Test
	public void invalidState(){
		
		Graph osasco = new Graph( OSASCO );
		
		Graph barueri = new Graph( BARUERI );
		
		Graph stParnaiba = new Graph( ST_PARNAIBA );
		
		Assert.assertFalse( "Can not link graph" , osasco.link( osasco, 10 ) );
		
		osasco.link( barueri, 10 );
		
		barueri.link( stParnaiba, 10 );
		
		Assert.assertFalse( "Can not link a graph twice" , barueri.link( stParnaiba, 10 ) );
		
		Integer distanceNotExpected = new Integer( Integer.MAX_VALUE );
		
		Assert.assertNotEquals( "Inconsistency distance between graphs", distanceNotExpected , osasco.getDistanceTo( stParnaiba ) );
		
		RuntimeException exception = null;
		
		try {
			
			osasco.unLink( barueri );
			
		} catch ( RuntimeException e ) {
			
			exception = e;
		}
		
		Assert.assertNotNull( "Exception was not thrown", exception);
		
	}
	
//	@Test
	public void calculateRoute(){
		
		Graph osasco = new Graph( OSASCO );
		
		Graph barueri = new Graph( BARUERI );
		
		Graph stParnaiba = new Graph( ST_PARNAIBA );
		
		Graph carapicuiba = new Graph( CARAPICUIBA );
		
		Graph cotia = new Graph( COTIA );
		
		osasco.link( carapicuiba, 30 );
		
		osasco.link( cotia, 20 );
		
		carapicuiba.link( barueri, 10 );
		
		barueri.link( stParnaiba, 10 );
		
		cotia.link( carapicuiba, 5 );
		
//		RouteResponse< Graph > routeResponse = osasco.find( stParnaiba, 10, new BigDecimal( "2.50" ) );
		
//		Assert.assertTrue( "Route Response without source graph.", routeResponse.isSourcedFrom( osasco ) );
		
//		Assert.assertEquals( "Route Response contain invalid calculate result.", new BigDecimal( "11.25" ), routeResponse.getCalculateResult() );
	}

}
