package edu.sdsu.cs560.project.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
	private Bitboard top;
	private Bitboard right;
	private Bitboard bottom;
	private Bitboard left;

	private Bitboard occupied;

	List<WoodenBlock> blocks;

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

		List<WoodenBlock> blocks = new ArrayList<>();
		for (WoodenBlock block : parent.blocks) {
			blocks.add(new WoodenBlock(this, block));
		}
		setBlocks(blocks);
	}

	public WoodenPuzzle(int width, int height) {
		parent = null;
		movement = null;

		this.width = width;
		this.height = height;

		buildEdges();
	}

	public void invalidate() {
		occupied = null;
	}

	private void buildEdges() {
		top    = new Bitboard(width, height);
		right  = new Bitboard(width, height);
		bottom = new Bitboard(width, height);
		left   = new Bitboard(width, height);

		top.draw   (        0,          0, width,      1);
		right.draw (width - 1,          0,     1, height);
		bottom.draw(        0, height - 1, width,      1);
		left.draw  (        0,          0,     1, height);
	}

	public boolean canMove(WoodenBlock block, WoodenBlockMovement.Direction direction) {
		if (WoodenBlockMovement.Direction.UP.equals(direction) && top.overlaps(block)) {
			return false;
		}
		if (WoodenBlockMovement.Direction.RIGHT.equals(direction) && right.overlaps(block)) {
			return false;
		}
		if (WoodenBlockMovement.Direction.DOWN.equals(direction) && bottom.overlaps(block)) {
			return false;
		}
		if (WoodenBlockMovement.Direction.LEFT.equals(direction) && left.overlaps(block)) {
			return false;
		}

		int occupied = getOccupied().getValue() & ~block.getValue();
		int shift = block.shift(direction.x, direction.y);
		boolean canMove = !Bitboard.overlaps(shift, occupied);
//		System.out.println("canMove "+block.getName()+" "+direction+" "+canMove+"...\n"+this+"\n"+ new Bitboard(width, height, occupied) + "\n"+ new Bitboard(width, height, shift));
		return canMove;
	}

	private Bitboard getOccupied() {
		if (occupied == null) {
			occupied = Bitboard.combine(blocks);
		}
		return occupied;
	}

	public WoodenPuzzle move(WoodenBlock block, WoodenBlockMovement.Direction direction) {
		WoodenBlockMovement movement = new WoodenBlockMovement(this, block, direction);
		WoodenPuzzle puzzle = new WoodenPuzzle(this, movement);
		WoodenBlock toMove = puzzle.getBlockByName(block.getName());
		toMove.setValue(toMove.shift(direction.x, direction.y));
		puzzle.invalidate(); // sets occupied to update next time it is requested
		return puzzle;
	}

	public WoodenPuzzle setBlocks(List<WoodenBlock> blocks) {
		this.blocks = blocks;
		invalidate();
		return this;
	}

	public List<WoodenBlock> getBlocks() {
		return blocks;
	}

	public WoodenBlock getBlockByName(String name) {
		if (name == null) return null;
		for (WoodenBlock block : blocks) {
			if (block.getName().equals(name)) {
				return block;
			}
		}
		return null;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		WoodenPuzzle that = (WoodenPuzzle) o;
		if (blocks.size() != that.getBlocks().size()) return false;
		for (int i = 0; i < blocks.size(); i++) {
			if (blocks.get(i).getValue() != that.getBlocks().get(i).getValue()) return false;
			if (!blocks.get(i).getName().equals(that.getBlocks().get(i).getName())) return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		int h = hash;
		if (h == 0) {
			for (WoodenBlock block : blocks) {
				h += block.getValue();
			}
			hash = h;
		}
		return h;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				builder.append(" ");
				boolean found = false;
				for (WoodenBlock block : blocks) {
					if (block.isAt(x, y)) {
						builder.append(block.name);
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
