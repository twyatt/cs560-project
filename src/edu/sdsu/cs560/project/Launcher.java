package edu.sdsu.cs560.project;

import edu.sdsu.cs560.project.helpers.WoodenPuzzleBuilder;
import edu.sdsu.cs560.project.models.Bitboard;
import edu.sdsu.cs560.project.models.WoodenBlockMovement;
import edu.sdsu.cs560.project.models.WoodenPuzzle;

import java.util.Arrays;
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
		System.out.println(puzzle.toString(names));

		int solution = builder.build(
				"         ",
				"      J J",
				"      J J",
				"         "
		).blocks[0];
		System.out.println(Bitboard.toString(solution, puzzle.width, puzzle.height, "J", "_"));

		WoodenPuzzleSolver solver = new WoodenPuzzleSolver(solution);
		List<WoodenBlockMovement> moves = solver.solve(puzzle);
		for (WoodenBlockMovement move : moves) {
			System.out.println(move);
//			System.out.println("Move piece at coordinates (?, ?) one unit " + move.direction.toString().toLowerCase());
		}
		System.out.println("Solution took " + moves.size() + " moves.");
	}

}
