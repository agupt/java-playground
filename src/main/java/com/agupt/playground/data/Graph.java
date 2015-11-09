package com.agupt.playground.data;

import java.util.*;
import com.google.common.collect.*;

public class Graph {
	final List<Node> nodes = new ArrayList<Node>();
	final List<Edge> edge = new ArrayList<Edge>();
	final Multimap<Node,Edge> edgeIndex = ArrayListMultimap.create();
}

class Node {
	final String id;
	final String value;

	public Node(final String id, final String value) {
		this.id = id;
		this.value = value;
	}
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
}

class Edge {
	final String id;
	final Node to;
	final Node from;

	public Edge(final String id, final Node to, final Node from) {
		this.id = id;
		this.to = to;
		this.from = from;
	}
}

