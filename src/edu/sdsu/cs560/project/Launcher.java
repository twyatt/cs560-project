package edu.sdsu.cs560.project;

import edu.sdsu.cs560.project.helpers.WoodenPuzzleBuilder;
import edu.sdsu.cs560.project.models.WoodenBlock;
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

		WoodenPuzzleSolver solver = new WoodenPuzzleSolver(puzzle);

		System.out.println(puzzle);
		List<WoodenBlock> blocks = puzzle.getBlocks();
		for (int i = 0; i < blocks.size(); i++) {
			WoodenBlock block = blocks.get(i);
			System.out.println("Block " + block.getName() + " @ " + i);
		}
		System.out.println(puzzle.getConfiguration());
		solver.solve();

		WoodenBlock F = puzzle.getBlockByName("F");
		F.setValue(F.shift(0, 1));

		System.out.println(puzzle);
		System.out.println(puzzle.getConfiguration());
		solver.solve();
	}
	
}
