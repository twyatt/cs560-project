package edu.sdsu.cs560.project.helpers;

import edu.sdsu.cs560.project.models.WoodenPuzzle;

import java.util.HashMap;
import java.util.Map;

public class WoodenPuzzleBuilder {

	private String[] names;

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

		int[] b = new int[blocks.size()];
		names = new String[blocks.size()];
		int i = 0;
		for (Map.Entry<String, Integer> block : blocks.entrySet()) {
			names[i] = block.getKey();
			b[i] = block.getValue();
			i++;
		}
		return new WoodenPuzzle(width, height, b);
	}

	public WoodenPuzzle build(String puzzle) {
		return build(puzzle.split(System.getProperty("line.separator")));
	}

	public String[] getNames() {
		return names;
	}

}
