package edu.sdsu.cs560.project;

import edu.sdsu.cs560.project.helpers.WoodenPuzzleBuilder;
import edu.sdsu.cs560.project.models.Bitboard;
import edu.sdsu.cs560.project.models.WoodenPuzzle;

import java.util.List;

public class Launcher {

	static String[] names;

	public static void main(String[] args) {
		WoodenPuzzleBuilder builder = new WoodenPuzzleBuilder();
		WoodenPuzzle puzzle = builder.build(
				"A A B B F",
				"J J E G  ",
				"J J E H  ",
				"C C D D I"
		);
		names = builder.getNames();
		System.out.println("Initial configuration:");
		System.out.println(puzzle.toString(names));

		int solution = builder.build(
				"         ",
				"      J J",
				"      J J",
				"         "
		).blocks[0];
		System.out.println("Solution configuration:");
		System.out.println(Bitboard.toString(solution, puzzle.width, puzzle.height, "J", "_"));

		WoodenPuzzleSolver solver = new WoodenPuzzleSolver(solution);
		puzzle = solver.solve(puzzle);

		List<String> moves = solver.replay(puzzle);
		for (String move : moves) {
			System.out.println(move);
		}
		System.out.println("Solution took " + solver.getDuration() + " seconds and " + moves.size() + " moves.");
	}

}
