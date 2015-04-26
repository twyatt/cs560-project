package edu.sdsu.cs560.project.models;

import java.util.List;

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

	public final int width;
	public final int height;
	private int value;

	public Bitboard(int width, int height) {
		int size = width * height;
		if (size > Integer.SIZE) {
			String name = getClass().getSimpleName();
			String message = name + " size (" + size + ") exceeds integer storage: " + Integer.SIZE + ".";
			throw new IllegalArgumentException(message);
		}

		this.width = width;
		this.height = height;
	}

	/**
	 * Creates a new bitboard by combining the occupancy of the specified
	 * bitboards.
	 *
	 * @param bitboards
	 * @return
	 */
	public static Bitboard combine(List<? extends Bitboard> bitboards) {
		Bitboard bitboard = null;
		for (Bitboard b : bitboards) {
			if (bitboard == null) {
				bitboard = new Bitboard(b.width, b.height);
			} else if (bitboard.width != b.width || bitboard.height != b.height) {
				throw new IllegalArgumentException("Cannot combine bitboards of unequal sizes.");
			}
			bitboard.value |= b.value;
		}
		return bitboard;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public void draw(int x, int y, int width, int height) {
		for (int h = 0; h < height; h++) {
			value |= ((1 << width) - 1) << indexOf(0, h);
		}
		shift(x, y);
	}

	/**
	 * Determines the bitboard index of the specified point.
	 *
	 * @param x
	 * @param y
	 * @return
	 */
	public int indexOf(int x, int y) {
		return x + width * y;
	}

	/**
	 * Determines if the specified point is occupied.
	 *
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean isAt(int x, int y) {
		return valueAt(x, y) != 0;
	}

	public int valueAt(int x, int y) {
		return value & (1 << indexOf(x, y));
	}

	/**
	 * Shifts bits the specified amount in the X and Y.
	 *
	 * @param x
	 * @param y
	 */
	public void shift(int x, int y) {
		int shift = indexOf(x, y);
		if (shift < 0) {
			value >>>= -shift;
		} else {
			value <<= shift;
		}
	}

	/**
	 * Determines if this bitboard has points occupied that overlap occupied
	 * points on the specified other bitboard.
	 *
	 * @param other
	 * @return
	 */
	public boolean overlaps(Bitboard other) {
		return (getValue() & other.getValue()) != 0;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				builder.append(" ").append(valueAt(x, y));
			}
			builder.append(System.getProperty("line.separator"));
		}
		return builder.toString();
	}

}
