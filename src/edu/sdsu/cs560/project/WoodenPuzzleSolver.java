package edu.sdsu.cs560.project;

import edu.sdsu.cs560.project.models.WoodenBlockMovement;
import edu.sdsu.cs560.project.models.WoodenPuzzle;

import java.util.*;

public class WoodenPuzzleSolver {

	/**
	 * List of block movement directions.
	 */
	static final WoodenBlockMovement.Direction[] DIRECTIONS = WoodenBlockMovement.Direction.values();

	private final Set<WoodenPuzzle> visited = new HashSet<>(1048576);
	private final Queue<WoodenPuzzle> queue = new LinkedList<>();

	private final int solution;
	private int depth;
	private int lastDepth;

	public WoodenPuzzleSolver(int solution) {
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
	 * Breadth-first search algorithm is used for finding the solution.
	 * http://en.wikipedia.org/wiki/Breadth-first_search
	 *
	 * @return
	 */
	private List<WoodenBlockMovement> solve() {
		while (!queue.isEmpty()) {
			WoodenPuzzle puzzle = queue.poll();
			depth = getMovements(puzzle).size();

			if (depth != lastDepth) {
				lastDepth = depth;
				System.out.println(depth);
			}

			if (isSolution(puzzle)) {
				System.out.println(puzzle.toString(Launcher.names));
				queue.clear();
				return getMovements(puzzle);
			}

			queueNextPuzzles(puzzle);
		}
		return null;
	}

	private void queueNextPuzzles(WoodenPuzzle puzzle) {
		for (int i = 0; i < puzzle.blocks.length; i++) {
			for (WoodenBlockMovement.Direction direction : DIRECTIONS) {
				WoodenPuzzle moved = puzzle.move(i, direction);

				if (moved != null && !visited.contains(moved)) {
					queue.add(moved);
					visited.add(moved);
				}
			}
		}
	}

	/**
	 * Determines if the puzzle specified is a solution.
	 *
	 * @param puzzle
	 * @return
	 */
	public boolean isSolution(WoodenPuzzle puzzle) {
		for (int i = 0; i < puzzle.blocks.length; i++) {
			if (solution == puzzle.blocks[i]) return true;
		}
		return false;
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
			if (puzzle.movement != null) {
				moves.add(puzzle.movement);
			}
			puzzle = puzzle.parent;
		}
		Collections.reverse(moves);
		return moves;
	}

}
