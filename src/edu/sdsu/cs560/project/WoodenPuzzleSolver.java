package edu.sdsu.cs560.project;

import edu.sdsu.cs560.project.helpers.Vector2i;
import edu.sdsu.cs560.project.models.Bitboard;
import edu.sdsu.cs560.project.models.WoodenBlockMovement;
import edu.sdsu.cs560.project.models.WoodenPuzzle;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class WoodenPuzzleSolver {

	/**
	 * List of block movement directions.
	 */
	static final WoodenBlockMovement.Direction[] DIRECTIONS = WoodenBlockMovement.Direction.values();

	private static final int INITIAL_CAPACTITY = 1_048_576; // 2^20

	private final List<Set<WoodenPuzzle>> visited = new ArrayList<>();
	private final Queue<WoodenPuzzle> queue = new LinkedList<>();

	private final int solution;

	private long duration;

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
	public WoodenPuzzle solve(WoodenPuzzle puzzle) {
		long start = System.nanoTime();

		for (int i = 0; i < puzzle.width * puzzle.height; i++) {
			visited.add(new HashSet<WoodenPuzzle>(INITIAL_CAPACTITY));
		}

		queue.add(puzzle);
		addVisited(puzzle);

		WoodenPuzzle solution = solve();
		duration = TimeUnit.NANOSECONDS.toSeconds(System.nanoTime() - start);
		return solution;
	}

	private void addVisited(WoodenPuzzle puzzle) {
		int index = Integer.numberOfTrailingZeros(puzzle.blocks[0]);
		visited.get(index).add(puzzle);
	}

	private boolean hasVisited(WoodenPuzzle puzzle) {
		int index = Integer.numberOfTrailingZeros(puzzle.blocks[0]);
		return visited.get(index).contains(puzzle);
	}

	public long getDuration() {
		return duration;
	}

	/**
	 * Generates a list of block movements that were taken to get to the
	 * specified puzzle.
	 *
	 * @param puzzle
	 * @return
	 */
	public List<String> replay(WoodenPuzzle puzzle) {
		List<String> replay = new ArrayList<>();

		List<WoodenBlockMovement> moves = new ArrayList<>();
		while (puzzle != null) {
			if (puzzle.parent == null) break;
			moves.add(puzzle.movement);
			puzzle = puzzle.parent;
		}
		Collections.reverse(moves);

		for (WoodenBlockMovement move : moves) {
			Vector2i position = Bitboard.position(puzzle.blocks[move.index], puzzle.width);
			replay.add("Move piece at coordinates " + position + " one unit " + move.direction.toString().toLowerCase());
			puzzle = puzzle.move(move.index, move.direction);
		}

		return replay;
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
	private WoodenPuzzle solve() {
		while (!queue.isEmpty()) {
			WoodenPuzzle puzzle = queue.poll();

			if (isSolution(puzzle)) {
				queue.clear();
				return puzzle;
			}

			queueNextPuzzles(puzzle);
		}
		return null;
	}

	private void queueNextPuzzles(WoodenPuzzle puzzle) {
		for (int i = 0; i < puzzle.blocks.length; i++) {
			for (int j = 0; j < DIRECTIONS.length; j++) {
				WoodenPuzzle moved = puzzle.move(i, DIRECTIONS[j]);

				if (moved != null && !hasVisited(moved)) {
					queue.add(moved);
					addVisited(moved);
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
	private boolean isSolution(WoodenPuzzle puzzle) {
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
	public List<WoodenBlockMovement> getMovements(WoodenPuzzle puzzle) {
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
