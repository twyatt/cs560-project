package edu.sdsu.cs560.project;

public class WoodenBlock {

	final WoodenPuzzle puzzle;
	final char letter;
	int bitboard;

	public WoodenBlock(WoodenPuzzle puzzle, char letter, int width, int height, int x, int y) {
		this.puzzle = puzzle;
		this.letter = letter;
		
		for (int h = 0; h < height; h++) {
			bitboard |= ((1 << width) - 1) << puzzle.indexOf(0, h);
		}
		move(x, y);
	}
	
	public void move(int x, int y) {
		int shift = puzzle.indexOf(x, y);
		if (shift < 0) {
			bitboard >>>= -shift;
		} else {			
			bitboard <<= shift;
		}
	}
	
	/**
	 * Determines if the wooden block occupies the specified point.
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean isAt(int x, int y) {
		return (bitboard & (1 << puzzle.indexOf(x, y))) != 0;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (int y = 0; y < puzzle.height; y++) {
			for (int x = 0; x < puzzle.width; x++) {
				builder.append(" ").append(isAt(x, y) ? letter : "-");
			}
			builder.append("\n");
		}
		return builder.toString();
	}
	
}
