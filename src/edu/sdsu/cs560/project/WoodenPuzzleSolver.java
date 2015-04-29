package edu.sdsu.cs560.project;

import edu.sdsu.cs560.project.models.Bitboard;
import edu.sdsu.cs560.project.models.WoodenBlock;
import edu.sdsu.cs560.project.models.WoodenBlockMovement;
import edu.sdsu.cs560.project.models.WoodenPuzzle;

import java.util.List;

public class WoodenPuzzleSolver {

	private final WoodenPuzzle puzzle;

	public WoodenPuzzleSolver(WoodenPuzzle puzzle) {
		this.puzzle = puzzle;
	}

	public List<WoodenBlockMovement> solve() {
		int occupied = Bitboard.combine(puzzle.getBlocks()).getValue();
		for (WoodenBlock block : puzzle.getBlocks()) {
			int o = occupied & ~block.getValue(); // subtract this block from occupancy

			if (!block.isAtTop() && !WoodenBlock.overlaps(block.shift(0, -1), o)) {
				// can move up
				System.out.println(block.getName() + " can move up.");
			}
			if (!block.isAtRight() && !WoodenBlock.overlaps(block.shift(1, 0), o)) {
				// can move right
				System.out.println(block.getName() + " can move right.");
			}
			if (!block.isAtBottom() && !WoodenBlock.overlaps(block.shift(0, 1), o)) {
				// can move down
				System.out.println(block.getName() + " can move down.");
			}
			if (!block.isAtLeft() && !WoodenBlock.overlaps(block.shift(-1, 0), o)) {
				// can move left
				System.out.println(block.getName() + " can move left.");
			}
		}
		return null;
	}

}
