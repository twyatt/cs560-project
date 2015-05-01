package edu.sdsu.cs560.project;

import edu.sdsu.cs560.project.models.WoodenBlock;
import edu.sdsu.cs560.project.models.WoodenBlockMovement;
import edu.sdsu.cs560.project.models.WoodenPuzzle;

import java.util.*;

public class WoodenPuzzleSolver {

	/**
	 * List of block movement directions.
	 */
	static final WoodenBlockMovement.Direction[] DIRECTIONS = WoodenBlockMovement.Direction.values();

	private final Set<WoodenPuzzle> visited = new HashSet<>();
	private final Queue<WoodenPuzzle> queue = new LinkedList<>();

	private final WoodenBlock solution;

	public WoodenPuzzleSolver(WoodenBlock solution) {
		this.solution = solution;
	}

	/**
	 * Initiates the solution search by adding the starting puzzle configuration
	 * to the queue and marking it as visited.
	 *
	 * @param puzzle
	 * @return
	 */
	public List<WoodenBlockMovement> solve(WoodenPuzzle puzzle) {
		queue.add(puzzle);
		visited.add(puzzle);
		return solve();
	}

	/**
	 * Continually tries new puzzle configurations until a solution is found, at
	 * which point it returns a list of movements to the solution puzzle
	 * configuration.
	 *
	 * Breadth-first algorithm is used when searching for the solution.
	 *
	 * @return
	 */
	private List<WoodenBlockMovement> solve() {
		while (!queue.isEmpty()) {
			WoodenPuzzle puzzle = queue.poll();
			if (isSolution(puzzle)) {
				queue.clear();
				return getMovements(puzzle);
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

	/**
	 * Generates a list of puzzles that can be reached (using valid block moves)
	 * from the specified puzzle.
	 *
	 * @param puzzle
	 * @return
	 */
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

	/**
	 * Determines if the puzzle specified is a solution (determines if the
	 * "solution" block is in the correct location).
	 *
	 * @param puzzle
	 * @return
	 */
	public boolean isSolution(WoodenPuzzle puzzle) {
		WoodenBlock block = puzzle.getBlockByName(solution.getName());
		return solution.getValue() == block.getValue();
	}

	/**
	 * Generates an ordered list of block movements that were taken to get to
	 * the specified puzzle configuration.
	 *
	 * @param puzzle
	 * @return
	 */
	private List<WoodenBlockMovement> getMovements(WoodenPuzzle puzzle) {
		List<WoodenBlockMovement> moves = new ArrayList<>();
		while (puzzle != null) {
			moves.add(puzzle.movement);
			puzzle = puzzle.parent;
		}
		Collections.reverse(moves);
		return moves;
	}

}
