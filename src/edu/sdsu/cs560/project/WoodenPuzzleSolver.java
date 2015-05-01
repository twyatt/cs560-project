package edu.sdsu.cs560.project;

import edu.sdsu.cs560.project.models.WoodenBlock;
import edu.sdsu.cs560.project.models.WoodenBlockMovement;
import edu.sdsu.cs560.project.models.WoodenPuzzle;

import java.util.*;

public class WoodenPuzzleSolver {

	static final WoodenBlockMovement.Direction[] DIRECTIONS = WoodenBlockMovement.Direction.values();

	private final Set<WoodenPuzzle> visited = new HashSet<>();
	private final Queue<WoodenPuzzle> queue = new LinkedList<>();

	private final WoodenBlock solution;

	public WoodenPuzzleSolver(WoodenBlock solution) {
		this.solution = solution;
	}

	public List<WoodenBlockMovement> solve(WoodenPuzzle puzzle) {
		queue.add(puzzle);
		visited.add(puzzle);
		return solve();
	}

	private List<WoodenBlockMovement> solve() {
		while (!queue.isEmpty()) {
			WoodenPuzzle puzzle = queue.poll();
			if (isSolution(puzzle)) {
				queue.clear();
				return getSolution(puzzle);
			}

			for (WoodenPuzzle nextPuzzle : getPuzzles(puzzle)) {
//				System.out.println(nextPuzzle);

				if (!visited.contains(nextPuzzle)) {
					queue.add(nextPuzzle);
					visited.add(nextPuzzle);
				}
			}
		}
		return null;
	}

	private List<WoodenPuzzle> getPuzzles(WoodenPuzzle puzzle) {
		ArrayList<WoodenPuzzle> puzzles = new ArrayList<>();
		for (WoodenBlock block : puzzle.getBlocks()) {
			for (WoodenBlockMovement.Direction direction : DIRECTIONS) {
				if (puzzle.canMove(block, direction)) {
					puzzles.add(puzzle.move(block, direction));
				}
			}
		}
		return puzzles;
	}

	public boolean isSolution(WoodenPuzzle puzzle) {
		WoodenBlock block = puzzle.getBlockByName(solution.getName());
		return solution.getValue() == block.getValue();
	}

	private List<WoodenBlockMovement> getSolution(WoodenPuzzle puzzle) {
		List<WoodenBlockMovement> moves = new ArrayList<>();
		while (puzzle != null) {
			moves.add(puzzle.movement);
			puzzle = puzzle.parent;
		}
		Collections.reverse(moves);
		return moves;
	}

}
