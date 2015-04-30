package edu.sdsu.cs560.project;

import edu.sdsu.cs560.project.models.Bitboard;
import edu.sdsu.cs560.project.models.WoodenBlock;
import edu.sdsu.cs560.project.models.WoodenBlockMovement;
import edu.sdsu.cs560.project.models.WoodenPuzzle;

import java.util.*;

public class WoodenPuzzleSolver {

	private final Map<Long, WoodenPuzzle> visited = new HashMap<>();
	private final Queue<WoodenPuzzle> queue = new LinkedList<>();

	private WoodenBlock solution;

	public List<WoodenBlockMovement> solve(WoodenPuzzle p, WoodenBlock s) {
		solution = s;
		queue.add(p);
		while (!queue.isEmpty()) {
			solve();
		}
		return null;
	}

	public List<WoodenBlockMovement> solve() {
		WoodenPuzzle p = queue.poll();
		if (p == null) return null;

		int occupied = Bitboard.combine(p.getBlocks()).getValue();

		for (WoodenBlock b : p.getBlocks()) {
			int o = occupied & ~b.getValue(); // subtract current block from occupancy

			if (!p.isAtTop(b) && !WoodenBlock.overlaps(b.shift(0, -1), o)) {
				// can move up
//				System.out.println(b.getName() + " can move up.");
				move(p, b, 0, -1);
			}
			if (!p.isAtRight(b) && !WoodenBlock.overlaps(b.shift(1, 0), o)) {
				// can move right
//				System.out.println(b.getName() + " can move right.");
				move(p, b, 1, 0);
			}
			if (!p.isAtBottom(b) && !WoodenBlock.overlaps(b.shift(0, 1), o)) {
				// can move down
//				System.out.println(b.getName() + " can move down.");
				move(p, b, 0, 1);
			}
			if (!p.isAtLeft(b) && !WoodenBlock.overlaps(b.shift(-1, 0), o)) {
				// can move left
//				System.out.println(b.getName() + " can move left.");
				move(p, b, -1, 0);
			}
		}
		return null;
	}

	private void move(WoodenPuzzle puzzle, WoodenBlock block, int dx, int dy) {
		WoodenPuzzle p = new WoodenPuzzle(puzzle);
		WoodenBlock b = p.getBlockByName(block.getName());
		b.setValue(b.shift(dx, dy));
		long configuration = p.getConfiguration();
//		System.out.print(".");

//		System.out.println(p);
		if (b.getName().equals(solution.getName()) && b.getValue() == solution.getValue()) {
			System.out.println("Solution found!");
			queue.clear();
			return;
		}

		if (visited.containsKey(configuration)) {
			// already tried this configuration
			// TODO check if it took less steps this way than what is already stored, if so, replace existing entry in visited
//			System.out.println("already tried: " + configuration);
		} else {
			visited.put(p.getConfiguration(), null);
//			System.out.println("new configuration attempt: " + configuration);
			queue.add(p);
		}
	}

}
