package com.softlynx.util;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class Natural<E> {

	private Natural() {
	}

	/**
	 * Returns new instance of natural comparator which uses the smart comparision
	 * of objects (see compare(o1,o2)).
	 */
	public static <E> Comparator<E> newComparator() {
		return new Comparator<E>() {
			public int compare(E o1, E o2) {
				return Natural.compare(o1, o2);
			}
		};
	}

	/**
	 * Smart compare of two objects: numbers and dates will compared by value, for
	 * all other objects a natural comparision of string-values will be used.
	 */
	public static int compare(Object o1, Object o2) {
		if (o1 instanceof Number && o2 instanceof Number) {
			return Double.compare(((Number) o1).doubleValue(), ((Number) o2).doubleValue());
		}

		if (o1 instanceof Date && o2 instanceof Date) {
			return ((Date) o1).compareTo(((Date) o2));
		}

		return compareObjects(String.valueOf(o1), String.valueOf(o2), Collator.getInstance());
	}

	/**
	 * Returns a natural sorted list of given collection.
	 */
	public static <E> List<E> sort(Collection<E> collection) {
		List<E> list = new ArrayList<E>(collection);
		list.sort(Natural.newComparator());
		return list;
	}

	/**
	 * Returns a natural sorted array of given array.
	 */
	public static <E> E[] sort(E[] array) {
		E[] arrayCopy = array.clone();
		Arrays.sort(arrayCopy, Natural.newComparator());
		return arrayCopy;
	}

	/**
	 * @param s
	 *            first string
	 * @param t
	 *            second string
	 * @param collator
	 *            used to compare subwords that aren't numbers - if null, characters
	 *            will be compared individually based on their Unicode value
	 * @return zero if <code>s</code> and <code>t</code> are equal, a value less
	 *         than zero if <code>s</code> lexicographically precedes <code>t</code>
	 *         and a value larger than zero if <code>s</code> lexicographically
	 *         follows <code>t</code>
	 */
	private static int compareObjects(String s, String t, Collator collator) {
		int sIndex = 0;
		int tIndex = 0;

		int sLength = s.length();
		int tLength = t.length();

		while (true) {
			// both character indices are after a subword (or at zero)

			// Check if one string is at end
			if (sIndex == sLength && tIndex == tLength) {
				return 0;
			}
			if (sIndex == sLength) {
				return -1;
			}
			if (tIndex == tLength) {
				return 1;
			}

			// Compare sub word
			char sChar = s.charAt(sIndex);
			char tChar = t.charAt(tIndex);

			boolean sCharIsDigit = Character.isDigit(sChar);
			boolean tCharIsDigit = Character.isDigit(tChar);

			if (sCharIsDigit && tCharIsDigit) {
				// Compare numbers

				// skip leading 0s
				int sLeadingZeroCount = 0;
				while (sChar == '0') {
					++sLeadingZeroCount;
					++sIndex;
					if (sIndex == sLength) {
						break;
					}
					sChar = s.charAt(sIndex);
				}
				int tLeadingZeroCount = 0;
				while (tChar == '0') {
					++tLeadingZeroCount;
					++tIndex;
					if (tIndex == tLength) {
						break;
					}
					tChar = t.charAt(tIndex);
				}
				boolean sAllZero = sIndex == sLength || !Character.isDigit(sChar);
				boolean tAllZero = tIndex == tLength || !Character.isDigit(tChar);
				if (sAllZero && tAllZero) {
					continue;
				}
				if (sAllZero && !tAllZero) {
					return -1;
				}
				if (tAllZero) {
					return 1;
				}

				int diff = 0;
				do {
					if (diff == 0) {
						diff = sChar - tChar;
					}
					++sIndex;
					++tIndex;
					if (sIndex == sLength && tIndex == tLength) {
						return diff != 0 ? diff : sLeadingZeroCount - tLeadingZeroCount;
					}
					if (sIndex == sLength) {
						if (diff == 0) {
							return -1;
						}
						return Character.isDigit(t.charAt(tIndex)) ? -1 : diff;
					}
					if (tIndex == tLength) {
						if (diff == 0) {
							return 1;
						}
						return Character.isDigit(s.charAt(sIndex)) ? 1 : diff;
					}
					sChar = s.charAt(sIndex);
					tChar = t.charAt(tIndex);
					sCharIsDigit = Character.isDigit(sChar);
					tCharIsDigit = Character.isDigit(tChar);
					if (!sCharIsDigit && !tCharIsDigit) {
						// both number sub words have the same length
						if (diff != 0) {
							return diff;
						}
						break;
					}
					if (!sCharIsDigit) {
						return -1;
					}
					if (!tCharIsDigit) {
						return 1;
					}
				} while (true);
			} else {
				// To use the collator the whole subwords have to be
				// compared - character-by-character comparision
				// is not possible. So find the two subwords first
				int aw = sIndex;
				int bw = tIndex;
				do {
					++sIndex;
				} while (sIndex < sLength && !Character.isDigit(s.charAt(sIndex)));
				do {
					++tIndex;
				} while (tIndex < tLength && !Character.isDigit(t.charAt(tIndex)));

				String as = s.substring(aw, sIndex);
				String bs = t.substring(bw, tIndex);
				int subwordResult = collator.compare(as, bs);
				if (subwordResult != 0) {
					return subwordResult;
				}
			}
		}
	}
}
