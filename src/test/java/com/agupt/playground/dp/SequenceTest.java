package com.agupt.playground.dp;

import org.junit.*;

public class SequenceTest {


	@Test 
	public void testZigZig() {
		int[] input = new int[]{ 1, 7, 4, 9, 2, 5 };
		int[] zigzag = Sequence.longestZigZagSeq(input);
		Assert.assertEquals(input.length-1,zigzag[1]); // end index
		Assert.assertEquals(input.length, zigzag[0]); // length of seq
		input = new int[]{ 1, 17, 10, 13, 10, 16, 8 };
		zigzag = Sequence.longestZigZagSeq(input);
		Assert.assertEquals(7, zigzag[0]); // length of seq
		input = new int[]{ 44 };
		zigzag = Sequence.longestZigZagSeq(input);
		Assert.assertEquals(1, zigzag[0]); // length of seq
		input = new int[]{ 44, 68 };
		zigzag = Sequence.longestZigZagSeq(input);
		Assert.assertEquals(2, zigzag[0]); // length of seq
		input = new int[]{ 44, 32 };
		zigzag = Sequence.longestZigZagSeq(input);
		Assert.assertEquals(2, zigzag[0]); // length of seq
		input = new int[]{ 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		zigzag = Sequence.longestZigZagSeq(input);
		Assert.assertEquals(2, zigzag[0]); // length of seq
		input = new int[]{ 70, 55, 13, 2, 99, 2, 80, 80, 80, 80, 100, 19, 7, 5, 5, 5, 1000, 32, 32 };
		zigzag = Sequence.longestZigZagSeq(input);
		Assert.assertEquals(4, zigzag[0]); // length of seq
		input = new int[]{ 374, 40, 854, 203, 203, 156, 362, 279, 812, 955, 600, 947, 978, 46, 100, 953, 670, 862, 568, 188, 67, 669, 810, 704, 52, 861, 49, 640, 370, 908, 477, 245, 413, 109, 659, 401, 483, 308, 609, 120, 249, 22, 176, 279, 23, 22, 617, 462, 459, 244 };
		zigzag = Sequence.longestZigZagSeq(input);
		Assert.assertEquals(12, zigzag[0]); // length of seq
	}
}
