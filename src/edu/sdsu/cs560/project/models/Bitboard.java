package edu.sdsu.cs560.project.models;

/**
 * A bitboard provides an efficient means of storing grid occupancy information
 * whereas bits are numbered from left to right, top to bottom, starting at 0
 * and wrapping at the width of the bitboard.
 *
 * For example:
 * A bitboard with a width and height of 3 by 2 would be represented by bits in
 * the following configuration:
 * .-----------.
 * | 0 | 1 | 2 |
 * |---+---+---|
 * | 3 | 4 | 5 |
 * '-----------'
 *
 * The 2D axis of the bitboard has it's origin in the upperleft corner with the
 * X axis increasing to the right and the Y axis increasing downward. The same
 * bitboard with points (1, 0) and (2, 1) occupied would look as follows:
 * .-----------.
 * | 0 | 1 | 0 |
 * |---+---+---|
 * | 0 | 0 | 1 |
 * '-----------'
 *
 * The resulting integer value of the above bitboard would be:
 * 0b0000_0000_0000_0000_0000_0000_0010_0010 = 0x22 = 34
 */
public class Bitboard {

	/**
	 * Returns the value of a bitboard by combining the occupancy of the
	 * specified bitboards.
	 *
	 * @param bitboards
	 * @return
	 */
	public static int combine(int... bitboards) {
		int combined = 0;
		for (int bitboard : bitboards) {
			combined |= bitboard;
		}
		return combined;
	}

	public static int draw(int bitboard, int width, int x, int y) {
		return bitboard | 1 << indexOf(width, x, y);
	}

	public static int draw(int bitboard, int width, int x, int y, int w, int h) {
		for (int i = x; i < x + w; i++) {
			for (int j = y; j < y + h; j++) {
				bitboard |= 1 << indexOf(width, i, j);
			}
		}
		return bitboard;
	}

	/**
	 * Returns the value of the bitboard with the specified point erased.
	 *
	 * @param x
	 * @param y
	 */
	public static int erase(int bitboard, int width, int x, int y) {
		return bitboard & ~(1 << indexOf(width, x, y));
	}

	/**
	 * Returns the value of bitboard1 subtracted by bitboard2.
	 *
	 * @param bitboard1
	 * @param bitboard2
	 * @return
	 */
	public static int subtract(int bitboard1, int bitboard2) {
		return bitboard1 & ~bitboard2;
	}

	/**
	 * Determines the bitboard index of the specified point.
	 *
	 * @param x
	 * @param y
	 * @return
	 */
	public static int indexOf(int width, int x, int y) {
		return x + width * y;
	}

	/**
	 * Determines the value (1 or 0) of the bitboard at the specified X and Y.
	 *
	 * @param bitboard
	 * @param width
	 * @param x
	 * @param y
	 * @return
	 */
	public static int valueAt(int bitboard, int width, int x, int y) {
		return bitboard & (1 << indexOf(width, x, y));
	}

	/**
	 * Returns the value of the bitboard when shifted the specified amount in
	 * X and Y.
	 *
	 * @param x
	 * @param y
	 */
	public static int shift(int bitboard, int width, int x, int y) {
		int shift = indexOf(width, x, y);
		if (shift < 0) {
			return bitboard >>> -shift;
		} else {
			return bitboard << shift;
		}
	}

	/**
	 * Determines if the specified point is occupied.
	 *
	 * @param x
	 * @param y
	 * @return
	 */
	public static boolean isAt(int bitboard, int width, int x, int y) {
		return valueAt(bitboard, width, x, y) != 0;
	}

	/**
	 * Determines if the two bitboards have any points occupied that overlap
	 * each other.
	 *
	 * @param bitboard1
	 * @param bitboard2
	 * @return
	 */
	public static boolean overlaps(int bitboard1, int bitboard2) {
		return (bitboard1 & bitboard2) != 0;
	}

	public static String toString(int bitboard, int width, int height, String occupied, String empty) {
		StringBuilder builder = new StringBuilder();
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				builder.append(" ").append(isAt(bitboard, width, x, y) ? occupied : empty);
			}
			builder.append(System.getProperty("line.separator"));
		}
		return builder.toString();
	}
}
