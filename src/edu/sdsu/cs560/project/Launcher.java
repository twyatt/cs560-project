package edu.sdsu.cs560.project;

import edu.sdsu.cs560.project.helpers.WoodenPuzzleBuilder;
import edu.sdsu.cs560.project.models.WoodenBlock;
import edu.sdsu.cs560.project.models.WoodenBlockMovement;
import edu.sdsu.cs560.project.models.WoodenPuzzle;

import java.util.List;

public class Launcher {

	public static void main(String[] args) {
		WoodenPuzzleBuilder builder = new WoodenPuzzleBuilder();
		WoodenPuzzle puzzle = builder.build(
				"A A B B F",
				"J J E G  ",
				"J J E H  ",
				"C C D D I"
		);
		System.out.println(puzzle);

		WoodenBlock solution = new WoodenBlock(puzzle, puzzle.getBlockByName("J"));
		solution.setValue(solution.shift(3, 0));
		System.out.println(solution);

		WoodenPuzzleSolver solver = new WoodenPuzzleSolver(solution);
		List<WoodenBlockMovement> moves = solver.solve(puzzle);
		for (WoodenBlockMovement move : moves) {
			System.out.println("Move piece at coordinates (?, ?) one unit " + move.direction.toString().toLowerCase());
		}
		System.out.println("Solution took " + moves.size() + " moves.");
	}

}
