
import java.util.*;
import java.lang.*;

public class Main {

	static class Coin implements Value {
		final int value;
		public Coin(final int value) {
			this.value= value;
		}
		public int getValue() {
			return this.value;
		}
		@Override
		public String toString() {
			return String.valueOf(value);
		}
	}

	public static void testDenomination() {
		final ArrayList	<Value> coins = new ArrayList<Value>();
		coins.add(new Coin(1));
		coins.add(new Coin(3));
		coins.add(new Coin(5));
		final Denomination d = new Denomination(coins);
		Map<Integer, Denomination.Result> results = d.MinNumberOfDenomination2(11);
		System.out.println(results);
	}

	public static void main(String[] args) {
		int[] seq = Sequence.randomSeq(100, 100);
		System.out.println(Arrays.toString(seq));
		int[] longest = Sequence.longestSeq(seq);
		for(int i = longest[1] /*end index*/ - longest[0] /*length*/ +1 ; i <= longest[1] ; i++) {
			System.out.println(seq[i]);
		}
	}
}