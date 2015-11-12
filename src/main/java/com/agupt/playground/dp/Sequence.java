package com.agupt.playground.dp;

import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;

public class Sequence {

	public static int[] randomSeq(final int size, final int bound) {
		final Random r = new Random();
		final int[] seq = new int[size];
		for (int i = 0 ; i < size ; i ++) {
			seq[i] = r.nextInt(bound);
		}
		return seq;
	}

	public static int[] longestSeq(final int[] seq) {
		final int[] longestSeqLength = new int[seq.length];
		longestSeqLength[0] = 1;
		final int[] currentLongestSeq = new int[2];
		currentLongestSeq[0] = 1; // length
		currentLongestSeq[1] = 0; // ends at index
		for (int i =1 ; i < seq.length ; i++) {
			
			if (longestSeqLength[i] == 0) {
				longestSeqLength[i] = 1;
			}

			if(seq[i] > seq[i-1] && longestSeqLength[i] < longestSeqLength[i-1] + 1) {
				longestSeqLength[i] = longestSeqLength[i-1] + 1;
			}

			if (longestSeqLength[i] > currentLongestSeq[0]) {
				currentLongestSeq[0] = longestSeqLength[i];
				currentLongestSeq[1] = i;
			}
		}
		return currentLongestSeq;
	}

	public static int[] longestZigZagSeq(final int[] seq) {
		// longestSeqLength[i] is len of longest ZigZag seq ending at element seq[i]
		final int[] longestSeqLength = new int[seq.length];
		final int[] currentLongestSeq = new int[2]; // length and start index of current longest seq
		if( seq.length >= 1) {
			longestSeqLength[0] = 1;
			currentLongestSeq[0] = 1;
			currentLongestSeq[1] = 0;	
		}
		
		if (seq.length >= 2) {
			longestSeqLength[1] = 2;
			currentLongestSeq[0] = 2;
			currentLongestSeq[1] = 1;
		}
		for (int i = 2 ; i < seq.length ; i++) {
			if(longestSeqLength[i] == 0) {
				longestSeqLength[i] = 1; /// trivial seq
			}
			if (seq[i-2] < seq[i -1] && seq[i-1] > seq[i] && longestSeqLength[i] < longestSeqLength[i-1] + 1) {
				// +- seq 
				longestSeqLength[i] = longestSeqLength[i-1] + 1;
			} else if (seq[i-2] > seq[i -1] && seq[i-1] < seq[i] && longestSeqLength[i] < longestSeqLength[i-1] + 1) {
				// -+ seq 
				longestSeqLength[i] = longestSeqLength[i-1] + 1;
			}

			if (currentLongestSeq[0] < longestSeqLength[i]) {
				currentLongestSeq[0] = longestSeqLength[i];
				currentLongestSeq[1] = i;
			}
		}
		return currentLongestSeq;
	}
}