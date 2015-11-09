package com.agupt.playground;

import java.lang.Comparable;
import java.util.*;
import java.lang.Math;
import java.lang.RuntimeException;
import java.lang.NullPointerException;

class BinaryTree<T extends Comparable> {
	Node root;

	public Node put(T t) {
		if (root == null) {
			root = new Node<T>(t);
			return root;
		} else {
			return this.root.Put(t);
		}
		
	}

	public String inorder() {
		return root != null ? root.inorder(false).toString() : "";
	}

	public String preorder() {
		return root != null ? root.preorder(false).toString() : "";
	}

	public String postorder() {
		return root != null ? root.postorder(false).toString() : "";
	}

	public Node root() {
		return root;
	}

	public List<T> serialize() {
		return root != null ? root.preorder(true) : new ArrayList<T>();
	}

	public void deSerialize(Iterator<T> itr) {
		if (root == null && itr.hasNext()) {
			T rootValue = itr.next();
			root = new Node<T>(rootValue);
			root.serialize(itr);
		}
	}

	public Node nearestNeighbour(T key) {
		Node nearest = null;
		Integer minDistance = Integer.MAX_VALUE;
		Node next = root;
		while(next != null) {
			Integer dist = next.distance(key) ;
			if (dist < minDistance) {
				nearest = next;
				minDistance = dist;
			}
			if (next.value.compareTo(key) >= 0) {
				next = next.left;
			} else {
				next = next.right;
			}
		}
		return nearest;
	}

	public Node get(T key) {
		Node next = root;
		Node contains = null;
		while (next != null) {
			if (next.value.compareTo(key) == 0) {
				contains = next;
				break;
			} else if (next.value.compareTo(key) > 0) {
				next = next.left;
			} else {
				next = next.right;
			}
		}
		return contains;
	}

	public boolean containsKey(T key) {
		return null != get(key);
	}

	public boolean remove(T key) {
		Node next = root;
		Node parent = null;
		Node contains = null;
		int side = 0;
		while (next != null) {
			if (next.value.compareTo(key) == 0) {
				contains = next;
				break;
			} else if (next.value.compareTo(key) > 0) {
				parent = next;
				side = -1;
				next = next.left;
			} else {
				parent = next;
				side = 1;
				next = next.right;
			}
		}
		if(contains == null) {
			return false;
		}
		// remove contains node and adjust tree
		remove(contains, parent, side);
		return true;
	}

	private void remove(Node toDelete, Node parent, int side) {
		if (toDelete == null) {
			return ; // do nothing
		}
		Node parent_child;
		if (toDelete.left == null && toDelete.right == null) {
			// delete leaf node
			parent_child = null;
		} else if (toDelete.left == null) {
			// attach right child of node toDeleted to parent
			parent_child = toDelete.right;
		} else if (toDelete.right == null) {
			// attach left child of node toDeleted to parent
			parent_child = toDelete.left;
		} else {
			// find largest node in tree rooted at toDelete.left
			Node pRightmost = toDelete;
			Node rightMost = toDelete.left; // can't be null
			Node next = rightMost.right;
			while(next != null) {
				pRightmost = rightMost;
				rightMost = next;
				next = next.right;
			}

			if (rightMost.right != null) {
				throw new RuntimeException("Rightmost node has a right child... wtf");
			}
			// attach left of largest node to right of its parent
			if (pRightmost != toDelete) {
				pRightmost.right = rightMost.left;
				rightMost.left = toDelete.left;
			}
			rightMost.right = toDelete.right;
			parent_child = rightMost;
		}

		if (parent == null) {
			root = parent_child;
		}
		if (parent != null && side == -1) {
			parent.left = parent_child;
		}
		if (parent != null && side == 1) {
			parent.right = parent_child;
		}
		return;
	}
}

class Node<T extends Comparable> {
	T value;
	Node left;
	Node right;

	public Node(T t) {
		value = t;
	}

	public Node Put(T t) {
		if (value.compareTo(t) >= 0) {
			// add to left
			if (left == null) {
				left = new Node<T>(t);
				return left;
			} 
			return left.Put(t);
		} else {
			// add to right
			if (right == null) {
				right = new Node<T>(t);
				return right;
			}
			return right.Put(t);
		}
	}

	public List<T> inorder(boolean addMarker) {
		List<T> buf = new ArrayList<T>();
		if (left != null) {
			buf.addAll(left.inorder(addMarker));
		} else if (addMarker) {
			buf.add(null);
		}
		buf.add(value);
		if (right != null) {
			buf.addAll(right.inorder(addMarker));
		} else if (addMarker) {
			buf.add(null);
		}
		return buf;
	}

	public List<T> preorder(boolean addMarker) {
		List<T> buf = new ArrayList<T>();
		buf.add(value);
		if (left != null) {
			buf.addAll(left.preorder(addMarker));
		} else if (addMarker) {
			buf.add(null);
		}
		if (right != null) {
			buf.addAll(right.preorder(addMarker));
		} else if (addMarker) {
			buf.add(null);
		}
		return buf;
	}

	public List<T> postorder(boolean addMarker) {
		List<T> buf = new ArrayList<T>();
		if (left != null) {
			buf.addAll(left.postorder(addMarker));
		} else if (addMarker) {
			buf.add(null);
		}
		if (right != null) {
			buf.addAll(right.postorder(addMarker));
		} else if (addMarker) {
			buf.add(null);
		}
		buf.add(value);
		return buf;
	}

	public String toString() {
		return value.toString();
	}

	public void serialize(Iterator<T> itr) {
		if (itr.hasNext()) {
			T value = itr.next();
			System.out.println(value);
			if (value != null) {
				left = new Node<T>(value);
				left.serialize(itr);
			}
		}
		if (itr.hasNext()) {
			T value = itr.next();
			System.out.println(value);
			if (value != null) {
				right = new Node<T>(value);
				right.serialize(itr);
			}
		}
	}

	public Integer distance(T from) {
		if (from == null) {
			throw new RuntimeException("");
		}
		if (from instanceof Integer) {
			return Math.abs(((Integer) from) - ((Integer)value));
		}
		return 0;
	}

	public Node smallest() {
		Node next = this;
		while(next.left != null) {
			next = next.left;
		}
		return next;
	}

	public Node largest() {
		Node next = this;
		while(next.right != null) {
			next = next.right;
		}
		return next;
	}
}

class Entry<K extends Comparable, V> implements Comparable {
	final K key;
	V value;

	Entry(K key, V value) {
		this.key = key;
		this.value = value;
	}

	public int compareTo(Object entry) {
		if(entry == null){
			return 1;
		}
		if (!(entry instanceof Entry)) {
			return 1;
		}
		Entry compareTo = (Entry<K,V>)entry;
		return this.key.compareTo(compareTo.key);
	}

	public String toString() {
		return String.format("[%s,%s]",key.toString(),value != null ? value.toString() : "null");
	}
}

class TreeMap<K extends Comparable, V> {

	int size = 0; // size of map
	
	private final BinaryTree bt = new BinaryTree<Entry<K,V>>();

	public V put(K key, V value) {
		System.out.println(String.format("TreeMap.BinaryTree %s",bt.inorder()));
		if (key == null) {
			throw new NullPointerException("key is null");
		}
		final Entry entry = new Entry<K,V>(key,value);
		final Node<Entry<K,V>> contains = bt.get(entry);
		if (contains != null) {
			V oldValue = contains.value.value;
			contains.value.value = value;
			return oldValue;
		}
		size++;
		final Node<Entry<K,V>> putKey = bt.put(entry);
		return putKey.value.value;
	}

	public V get(K key) {
		if (key == null) {
			return null;
		}
		final Entry entry = new Entry<K,V>(key, null);
		final Node<Entry<K,V>> contains = bt.get(entry);
		if (contains == null) {
			return null;
		}
		return contains.value.value;
	}

	public int size() {
		return size;
	}

	public boolean containsKey(K key) {
		if (key == null) {
			return false;
		}
		final Entry entry = new Entry<K,V>(key, null);
		final Node<Entry<K,V>> contains = bt.get(entry);
		if (contains == null) {
			return false;
		}
		return true;
	}

	public Set<Entry<K,V>> entrySet() {
		return new HashSet<Entry<K,V>>(bt.root.inorder(false));
	}

	public boolean remove(K key) {
		if (key == null) {
			return false;
		}
		final Entry entry = new Entry<K,V>(key, null);
		return bt.remove(entry);
	}

}