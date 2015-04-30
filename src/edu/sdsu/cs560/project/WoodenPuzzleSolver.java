package edu.sdsu.cs560.project;

import edu.sdsu.cs560.project.models.Bitboard;
import edu.sdsu.cs560.project.models.WoodenBlock;
import edu.sdsu.cs560.project.models.WoodenBlockMovement;
import edu.sdsu.cs560.project.models.WoodenPuzzle;

import java.util.*;

public class WoodenPuzzleSolver {

	private class SolutionNode {

		SolutionNode parent;
		WoodenPuzzle puzzle;
		String block;
		WoodenBlockMovement.Direction movement;
		long moves;

		public SolutionNode(SolutionNode parent, WoodenPuzzle puzzle, String block, WoodenBlockMovement.Direction movement) {
			this.parent = parent;
			this.puzzle = puzzle;
			this.block = block;
			this.movement = movement;
			moves = parent == null ? 0 : parent.moves + 1;
		}

	}

	private final Map<Long, SolutionNode> visited = new HashMap<>();
	private final Queue<SolutionNode> queue = new LinkedList<>();

	private WoodenBlock solution;

	public List<WoodenBlockMovement> solve(WoodenPuzzle puzzle, WoodenBlock solution) {
		queue.add(new SolutionNode(null, puzzle, null, null));
		this.solution = solution;

		while (!queue.isEmpty()) {
			solve();
		}

		return null;
	}

	public void solve() {
		SolutionNode node = queue.poll();
		if (node == null) return;
		WoodenPuzzle p = node.puzzle;

		int occupied = Bitboard.combine(p.getBlocks()).getValue();

		for (WoodenBlock b : p.getBlocks()) {
			int o = occupied & ~b.getValue(); // subtract current block from occupancy

			if (!p.isAtTop(b) && !WoodenBlock.overlaps(b.shift(0, -1), o)) {
				// can move up
				move(node, b, WoodenBlockMovement.Direction.UP);
			}
			if (!p.isAtRight(b) && !WoodenBlock.overlaps(b.shift(1, 0), o)) {
				// can move right
				move(node, b, WoodenBlockMovement.Direction.RIGHT);
			}
			if (!p.isAtBottom(b) && !WoodenBlock.overlaps(b.shift(0, 1), o)) {
				// can move down
				move(node, b, WoodenBlockMovement.Direction.DOWN);
			}
			if (!p.isAtLeft(b) && !WoodenBlock.overlaps(b.shift(-1, 0), o)) {
				// can move left
				move(node, b, WoodenBlockMovement.Direction.LEFT);
			}
		}
		node.puzzle = null; // clear up some memory
	}

	private void move(SolutionNode node, WoodenBlock block, WoodenBlockMovement.Direction movement) {
		WoodenPuzzle puzzleNext = new WoodenPuzzle(node.puzzle);
		WoodenBlock blockNext = puzzleNext.getBlockByName(block.getName());
		SolutionNode nodeNext = new SolutionNode(node, puzzleNext, blockNext.getName(), movement);

		blockNext.setValue(blockNext.shift(movement.x, movement.y));
		long configuration = puzzleNext.getConfiguration();

		if (blockNext.getValue() == solution.getValue() && blockNext.getName().equals(solution.getName())) {
//			queue.clear();
			onSolution(nodeNext);
			return;
		}

		if (!visited.containsKey(configuration)) {
			visited.put(puzzleNext.getConfiguration(), nodeNext);
			queue.add(nodeNext);
		}
	}

	private void onSolution(SolutionNode node) {
		System.out.println("Solution found!");
		System.out.println(node.puzzle);

		while (node != null) {
			System.out.println(node.moves + ") move " + node.block + " " + node.movement);
			node = node.parent;
		}
	}

}
