package edu.sdsu.cs560.project;

import java.util.List;

public class Launcher {

	static String[] names;

	public static void main(String[] args) {
		Builder builder = new Builder();
		Board puzzle = builder.build(
				"A A B B F",
				"J J E G  ",
				"J J E H  ",
				"C C D D I"
		);
		names = builder.getNames();

		String block = "J";
		int solution = builder.build(
				"         ",
				"      1 1",
				"      1 1",
				"         "
		).blocks[0];

		System.out.println("Initial configuration:");
		System.out.println(puzzle.toString(names));

		System.out.println("Solution configuration:");
		System.out.println(Bitboard.toString(solution, puzzle.width, puzzle.height, block, Board.EMPTY_CELL_TEXT));

		Solver solver = new Solver(solution, Board.indexOf(block, names));
		puzzle = solver.solve(puzzle);

		List<String> moves = solver.replay(puzzle);
		for (String move : moves) {
			System.out.println(move);
		}
		System.out.println("Solution took " + solver.getDuration() + " seconds and " + moves.size() + " moves.");
	}

}
