

import java.util.*;
import java.lang.Math;


public class Main {
	public static void main(String[] args) throws Exception {
		int[] a = new int[] {5, 17, 1, 8, 4};
		int[] b = new int[] {90, 3 , 4, 19 , 7, 28, 16,};
		BinaryTree<Integer> bt = new BinaryTree<Integer>();
		for(int i : a) {
			bt.put(i);
		}
		for(int i : b) {
			bt.put(i);
		}
		System.out.println(String.format("inorder -- > %s",bt.inorder()));
		System.out.println(String.format("preorder -- > %s", bt.preorder()));
		System.out.println(String.format("postorder --> %s",bt.postorder()));
		List<Integer> serialize = bt.serialize();
		System.out.println(String.format("preorder -- > %s", serialize));
		BinaryTree<Integer> newbt = new BinaryTree<Integer>();
		newbt.deSerialize(serialize.iterator());
		System.out.println(String.format("inorder -- > %s",newbt.inorder()));
		System.out.println(String.format("preorder -- > %s", newbt.preorder()));
		System.out.println(String.format("postorder --> %s",newbt.postorder()));
		System.out.println(String.format("Nearest Neighbour %s", newbt.nearestNeighbour(16)));
		for(int i : a) {
			System.out.println(String.format("%d is cointained in bt %b", i, bt.containsKey(i)));
		}
		System.out.println(String.format("%d is cointained in bt %b",100, bt.containsKey(100)));
		System.out.println(String.format("%s is smallest node in bt", bt.root.left.smallest().toString()));
		System.out.println(String.format("%s is largetst node in bt", bt.root.left.largest().toString()));
		bt.remove(28);
		System.out.println(String.format("inorder -- > %s",bt.inorder()));
		bt.remove(1);
		System.out.println(String.format("inorder -- > %s",bt.inorder()));
		bt.remove(17);
		System.out.println(String.format("inorder -- > %s",bt.inorder()));
		bt.remove(90);
		System.out.println(String.format("inorder -- > %s",bt.inorder()));
	}

	/*
		Merge two sorted arrays
	*/
	public static int[] MergeSrorted(int[] a, int[] b) throws Exception {
		int len_a = a.length;
		int len_b = b.length;
		int nums = len_a + len_b;
		int[] merged = new int[nums];
		int idx_a = 0;
		int idx_b = 0;
		for (int k = 0 ; k < nums ; k++) {
			if (idx_a < len_a && idx_b < len_b) {
				// compare two and one to list
				if(a[idx_a] <= b[idx_b]) {
					// copy a to merged
					merged[k] = a[idx_a];
					idx_a++;
				} else {
					merged[k] = b[idx_b];
					idx_b++;
				}
				continue;
			}
			if (idx_a < len_a) {
				// copy a to merged
				Copy(a, idx_a, len_a, merged, k);
				break;
			}
			if(idx_b < len_b) {
				// copy b to merged
				Copy(b, idx_b, len_b, merged, k);
				break;
			}
		}
		return merged;
	}

	/*
		copy content of 'from' array starting from 'start' index upto 'end' index to 'to' array starting at 'index'
		func tries best to accomodate source to destination. 
	*/
	public static void Copy(int[] from, int start, int end, int[] to, int index) throws Exception {
		if (start < 0 || end < 0 || start > end) {
			throw new Exception("start and end index should be greater than 0 and start should be" + 
				" less than equal to end");
		}
		int j = Math.min(index, to.length);
		for (int i = Math.min(start, from.length) ; i < Math.min(end, from.length) ; i++) {
			if (j < to.length) {
				// copy 
				to[j] = from[i];
				j++;
			} else {
				break;
			}
		}
	}
}