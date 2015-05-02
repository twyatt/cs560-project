package edu.sdsu.cs560.project.models;

public class WoodenPuzzle {

	public static final String EMPTY_CELL_TEXT = "_";

	public final WoodenPuzzle parent;
	public final WoodenBlockMovement movement;

	/*
	 * Dimensions of the puzzle.
	 */
	public final int width;
	public final int height;

	/*
	 * Bitboards for the edges of the wooden puzzle (used for edge detection).
	 */
	private int top;
	private int right;
	private int bottom;
	private int left;

	public int occupied;

	public int[] blocks;

	private int hash;

	public WoodenPuzzle(WoodenPuzzle parent, WoodenBlockMovement movement) {
		this.parent   = parent;
		this.movement = movement;

		width  = parent.width;
		height = parent.height;

		top    = parent.top;
		right  = parent.right;
		bottom = parent.bottom;
		left   = parent.left;

		occupied = parent.occupied;

		blocks = new int[parent.blocks.length];
		System.arraycopy(parent.blocks, 0, blocks, 0, blocks.length);
	}

	public WoodenPuzzle(int width, int height, int[] blocks) {
		parent   = null;
		movement = null;

		this.width  = width;
		this.height = height;

		top    = Bitboard.draw(0, width,         0,          0, width,      1);
		right  = Bitboard.draw(0, width, width - 1,          0,     1, height);
		bottom = Bitboard.draw(0, width,         0, height - 1, width,      1);
		left   = Bitboard.draw(0, width,         0,          0,     1, height);

		occupied = Bitboard.combine(blocks);

		this.blocks = blocks;
	}

	public WoodenPuzzle move(int index, WoodenBlockMovement.Direction direction) {
		int block = blocks[index];

		if (WoodenBlockMovement.Direction.UP.equals(direction) && Bitboard.overlaps(top, block)) {
			return null;
		}
		if (WoodenBlockMovement.Direction.RIGHT.equals(direction) && Bitboard.overlaps(right, block)) {
			return null;
		}
		if (WoodenBlockMovement.Direction.DOWN.equals(direction) && Bitboard.overlaps(bottom, block)) {
			return null;
		}
		if (WoodenBlockMovement.Direction.LEFT.equals(direction) && Bitboard.overlaps(left, block)) {
			return null;
		}

		int occupied = Bitboard.subtract(this.occupied, block);
		block = Bitboard.shift(block, width, direction.x, direction.y);
		if (Bitboard.overlaps(occupied, block)) {
			return null;
		}

		WoodenBlockMovement movement = new WoodenBlockMovement(index, direction);
		WoodenPuzzle puzzle = new WoodenPuzzle(this, movement);
		puzzle.blocks[index] = block;
		puzzle.occupied = occupied | block;
		return puzzle;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		WoodenPuzzle that = (WoodenPuzzle) o;
		if (blocks.length != that.blocks.length) return false;
		for (int i = 0; i < blocks.length; i++) {
			if (blocks[i] != that.blocks[i]) return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		int h = hash;
		if (h == 0) {
			for (int i = 0; i < blocks.length; i++) {
				h = 31*h + blocks[i];
			}
			hash = h;
		}
		return h;
	}

	@Override
	public String toString() {
		return toString(null);
	}

	public String toString(String[] names) {
		StringBuilder builder = new StringBuilder();
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				builder.append(" ");
				boolean found = false;
				for (int i = 0; i < blocks.length; i++) {
					int block = blocks[i];
					if (Bitboard.isAt(block, width, x, y)) {
						if (names == null) {
							builder.append(i);
						} else {
							builder.append(names[i]);
						}
						found = true;
						break;
					}
				}
				if (!found) {
					builder.append(EMPTY_CELL_TEXT);
				}
			}
			builder.append(System.getProperty("line.separator"));
		}
		return builder.toString();
	}

}
