package com.agupt.playground.data;

import java.util.*;
import org.junit.*;

public class GraphTest {

	// build following graph
		/*
			sf -5-> la -----2-----> san
			|		/\	\            /
			4		|	  \			3
			|		7	    4	   /
			\/      |		\/   \/
			yose----+-6-----> las

		*/
	
	public Graph geoMap(final Map<String, Graph.Node> cities) {
		Graph geo = new Graph();
		List<Graph.Edge> edges = new ArrayList<Graph.Edge>();
		edges.add(new Graph.Edge(cities.get("sfo"), 5, cities.get("lax")));
		edges.add(new Graph.Edge(cities.get("sfo"), 4, cities.get("yose")));
		edges.add(new Graph.Edge(cities.get("yose"), 7, cities.get("lax")));
		edges.add(new Graph.Edge(cities.get("yose"), 6, cities.get("las")));
		edges.add(new Graph.Edge(cities.get("lax"), 2, cities.get("san")));
		edges.add(new Graph.Edge(cities.get("lax"), 4, cities.get("las")));
		edges.add(new Graph.Edge(cities.get("san"), 3, cities.get("las")));
		// add circular edge
		edges.add(new Graph.Edge(cities.get("san"), 9 , cities.get("sfo")));
		for (Graph.Node n : cities.values()) {
			geo.addNode(n);
		}
		for (Graph.Edge e : edges) {
			geo.addEdge(e);
		}
		return geo;
	}

	Graph geo ;
	final Map<String, Graph.Node> cities = new HashMap<String, Graph.Node>();

	@Before
	public void init() {
		cities.put("sfo", new Graph.Node("sfo",""));
		cities.put("lax", new Graph.Node("lax",""));
		cities.put("san", new Graph.Node("san",""));
		cities.put("yose", new Graph.Node("yose",""));
		cities.put("las", new Graph.Node("las",""));
		geo = geoMap(cities);
	}


	@Test
	public void testGeoGraph() {
		Assert.assertEquals(geo.getNodes().size(),5);
		Assert.assertEquals(geo.getEdges().size(),8);
		List<Graph.Edge> path = geo.breadthTraversal("sfo");
		Assert.assertEquals(8,path.size());
		path = geo.depthTraversal("sfo");
		Assert.assertEquals(8,path.size());
	}

	@Test
	public void testShortestPath() {
		Integer shortestPath = geo.shortestPath("sfo","yose");
		Assert.assertNotNull(shortestPath);
		Assert.assertEquals(4,shortestPath.intValue());
		shortestPath = geo.shortestPath("sfo","las");
		Assert.assertEquals(9,shortestPath.intValue());
		shortestPath = geo.shortestPath("sfo","san");
		Assert.assertEquals(7,shortestPath.intValue());
		// add direct shortest path from sfo to las of 2 weight
		geo.addEdge(new Graph.Edge(cities.get("sfo"),2,cities.get("las")));
		shortestPath = geo.shortestPath("sfo","las");
		Assert.assertEquals(2,shortestPath.intValue());
	}
}