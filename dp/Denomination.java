import java.util.*;
import java.lang.*;

public class Denomination {

	class Result {
		Integer sum;
		Integer denominationCount;
		List<Value> denomination;

		public Result(final Integer sum) {
			this.sum = sum;
		}

		@Override
		public String toString() {
			return String.format("{Sum: %d, denominationCount: %d, denomination: [%s]}",sum, denominationCount, denomination);
		}
	}

	final ArrayList<Value> denomination; // assuming sorted list of denomination

	public Denomination(final ArrayList<Value> denomination) {
		this.denomination = denomination;
	}

	public Map<Integer, Result> MinNumberOfDenomination(final int sum) {
		Map<Integer, Result> mincount = new HashMap<Integer, Result>();
		for (int i = 0; i <= sum; i++) {
			final Result r = new Result(i);
			r.denominationCount = i == 0 ? 0 : Integer.MAX_VALUE;
			r.denomination = new ArrayList<Value>();
			mincount.put(i, r);
		}
		for (int i = 1 ; i <= sum ; i++) {
			final Result r = mincount.get(i);
			for (Value v : denomination) {
				if (v.getValue() <= i && mincount.get(i - v.getValue()).denominationCount + 1 < r.denominationCount) {
					r.denominationCount = mincount.get(i - v.getValue()).denominationCount + 1;
					// add min denomination required for sum = i -v
					r.denomination = new ArrayList<Value>();
					r.denomination.addAll(mincount.get(i - v.getValue()).denomination);
					// add denomination of v
					r.denomination.add(v);
				}
			}
		}
		return mincount;
	}

	public Map<Integer, Result> MinNumberOfDenomination2(final int sum) {
		Map<Integer, Result> mincount = new HashMap<Integer, Result>();
		for (int i = 0; i <= sum; i++) {
			final Result r = new Result(i);
			r.denominationCount = i == 0 ? 0 : Integer.MAX_VALUE;
			r.denomination = new ArrayList<Value>();
			mincount.put(i, r);
		}
		for (Value v : denomination) {
			for (Result r : mincount.values()) {
				final int newSum = r.sum + v.getValue();
				if(mincount.containsKey(newSum) && mincount.get(newSum).denominationCount > r.denominationCount + 1) {
					mincount.get(newSum).denominationCount = r.denominationCount + 1;
					mincount.get(newSum).denomination = new ArrayList<Value>();
					mincount.get(newSum).denomination.addAll(r.denomination);
					mincount.get(newSum).denomination.add(v);
				}
			}
		}
		return mincount;
	}

}