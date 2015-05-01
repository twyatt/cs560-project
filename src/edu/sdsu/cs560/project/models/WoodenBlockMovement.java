package edu.sdsu.cs560.project.models;

public class WoodenBlockMovement {

	public enum Direction {
		UP   ( 0, -1),
		RIGHT( 1,  0),
		DOWN ( 0,  1),
		LEFT (-1,  0),
		;
		public final int x;
		public final int y;
		Direction(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	private WoodenPuzzle puzzle;
	public WoodenBlock block;
	public Direction direction;

	public WoodenBlockMovement(WoodenPuzzle puzzle, WoodenBlock block, Direction direction) {
		this.puzzle = puzzle;
		this.block = block;
		this.direction = direction;
	}

}
