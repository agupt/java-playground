package com.agupt.playground.data;

import java.util.*;
import com.google.common.collect.*;
import java.lang.Integer;
import java.lang.RuntimeException;


public class Graph {

	public static class Node {
		final String id;
		final String value;

		public Node(final String id, final String value) {
			this.id = id;
			this.value = value;
		}

		@Override
		public boolean equals(Object other) {
			if (null == other) {
				return false;
			}
			if (!(other instanceof Node)) {
				return false;
			}
			final Node otherNode = (Node)other;
			if (otherNode == this) {
				return true;
			}
			return this.id.equals(otherNode.id);
		}

		@Override
		public String toString() {
			return String.format("{\"node\": \"id\"=%s, \"value\"=%s}",id, value);
		}
	}

	public static class Edge {

		public static final String DEFAULT_EDGE_TYPE = "EDGE";
		final String type;
		final Node to;
		final Node from;
		final Integer weight;

		public Edge(final Node from, final Integer weight, final Node to) {
			if (null == to || null == from) {
				throw new RuntimeException("to and from nodes are required to create edge");
			}
			this.weight = weight;
			this.to = to;
			this.from = from;
			this.type = DEFAULT_EDGE_TYPE;
		}

		@Override
		public boolean equals(Object other) {
			if (null == other) {
				return false;
			}
			if (!(other instanceof Edge)) {
				return false;
			}
			final Edge otherEdge = (Edge)other;
			if (this == otherEdge) {
				return true;
			}
			return to.equals(otherEdge.to) && from.equals(otherEdge.from);
		}

		@Override
		public String toString() {
			return String.format("{\"edge\": \"type\"=%s, \"weight\"=%d, \"to\"=%s, \"from\"=%s}",type, weight, to.toString(), from.toString());
		}
	}

	private final Map<String, Node> nodes = new HashMap<String, Node>();
	private final List<Edge> edges = new ArrayList<Edge>();
	private final HashMultimap<Node,Edge> edgeIndex = HashMultimap.create(); // edges index from --> to 

	public boolean addEdge(final Edge edge) {
		if (edgeExist(edge)) {
			return false;
		}
		if (!edgeIndex.put(edge.from, edge)) {
			return false;
		}
		edges.add(edge);
		return true;
	}

	public boolean edgeExist(final Edge edge) {
		if (null == edge) {
			return false;
		}
		return edgeIndex.containsEntry(edge.from, edge);
	}

	public Node addNode(final Node node) {
		if (null == node) {
			return null;
		}
		if (nodes.containsKey(node.id)) {
			return null;
		}
		return nodes.put(node.id, node);
	}

	public ImmutableSet<Node> getNodes() {
		return ImmutableSet.copyOf(nodes.values());
	}

	public ImmutableSet<Edge> getEdges() {
		return ImmutableSet.copyOf(edges);
	}

	public List<Edge> breadthTraversal(final String id) {
		final List<Edge> path = new ArrayList<Edge>();
		final ArrayDeque<Node> q = new ArrayDeque<Node>();
		final HashSet<Node> visited = new HashSet<Node>();
		if(nodes.containsKey(id)) {
			q.add(nodes.get(id));  // add starting node to Q
			visited.add(nodes.get(id)); // mark starting node visited
			while(!q.isEmpty()) {
				final Node n = q.poll();
				for(Edge e : edgeIndex.get(n)) {
					path.add(e);
					if (!visited.contains(e.to)) {
						q.add(e.to);
						visited.add(e.to);
					}
				}
			}
		}
		return path;
	}

	public List<Edge> depthTraversal(final String id) {
		final List<Edge> path = new ArrayList<Edge>();
		final ArrayDeque<Edge> stack = new ArrayDeque<Edge>();
		final HashSet<Node> visited = new HashSet<Node>();
		if(nodes.containsKey(id)) {
			final Node start = nodes.get(id);
			visited.add(start);
			for (Edge e : edgeIndex.get(start)) {
				stack.push(e);
			}
			while(!stack.isEmpty()) {
				final Edge edge  = stack.pop();
				path.add(edge);
				if(!visited.contains(edge.to)) {
					// not visited in past add all its edges to stack
					visited.add(edge.to);
					for(Edge e : edgeIndex.get(edge.to)) {
						stack.push(e);
					}
				}
			}	
		}
		return path;
	}

	public Integer shortestPath(final String start, final String end) {
		Integer length = null;
		if(nodes.containsKey(start) && nodes.containsKey(end)) {
			final Map<Node,Integer> pathLength = new HashMap<Node, Integer>();
			// state `i` represent shorted path from `start` to `i` 
			// state `j` represent shorted path from `start` to `j` which can be reached from i
			final Set<Node> visited = new HashSet<Node>();
			final ArrayDeque<Node> q = new ArrayDeque<Node>();
			q.add(nodes.get(start));
			visited.add(nodes.get(start));
			pathLength.put(nodes.get(start),0); // lenght of path to start 0
			while(!q.isEmpty()) {
				final Node n = q.poll();
				final Integer path2n = pathLength.containsKey(n) ? pathLength.get(n) : Integer.MAX_VALUE;
				for (Edge e : edgeIndex.get(n)) {
					final Integer path2to = pathLength.containsKey(e.to) ? pathLength.get(e.to) : Integer.MAX_VALUE;
					if(path2to > path2n + e.weight) {
						pathLength.put(e.to,path2n + e.weight);
					}
					if(!visited.contains(e.to)) {
						q.add(e.to);
						visited.add(e.to);
					}
				}
			}
			length = pathLength.get(nodes.get(end));
		}
		return length;
	}
}





