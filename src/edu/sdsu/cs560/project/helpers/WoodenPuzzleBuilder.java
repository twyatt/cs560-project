package edu.sdsu.cs560.project.helpers;

import edu.sdsu.cs560.project.models.WoodenBlock;
import edu.sdsu.cs560.project.models.WoodenPuzzle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WoodenPuzzleBuilder {

	public WoodenPuzzle build(String... lines) {
		Map<String, Integer> blocks = new HashMap<>();

		int width = 0;
		int height = lines.length;
		String[][] array = new String[height][];

		for (int y = 0; y < height; y++) {
			String line = lines[y];
			array[y] = line.split("(?<=\\G.)\\s");
			width = Math.max(width, array[y].length);
		}

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < array[y].length; x++) {
				String name = array[y][x];
				if (!name.trim().isEmpty()) {
					int block = blocks.get(name) == null ? 0 : blocks.get(name);
					int index = x + width * y;
					block |= (1 << index);
					blocks.put(name, block);
				}
			}
		}

		WoodenPuzzle puzzle = new WoodenPuzzle(width, height);
		List<WoodenBlock> list = new ArrayList<>();
		for (Map.Entry<String, Integer> b : blocks.entrySet()) {
			list.add(new WoodenBlock(puzzle, b.getKey(), b.getValue()));
		}
		puzzle.setBlocks(list);
		return puzzle;
	}

	public WoodenPuzzle build(String puzzle) {
		return build(puzzle.split(System.getProperty("line.separator")));
	}

}
