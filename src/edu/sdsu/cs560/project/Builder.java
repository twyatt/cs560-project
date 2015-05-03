package edu.sdsu.cs560.project;

import edu.sdsu.cs560.project.Board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Builder {

	private String[] names;

	public Board build(String... lines) {
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

		Map<Integer, List<Integer>> groups = new HashMap<>();

		int[] b = new int[blocks.size()];
		names = new String[blocks.size()];
		int i = 0;
		for (Map.Entry<String, Integer> block : blocks.entrySet()) {
			names[i] = block.getKey(); // build names array
			b[i] = block.getValue(); // build blocks array

			// build groups based on bitboard "shape"
			Integer shape = Bitboard.shape(block.getValue(), width, height);
			List<Integer> group = groups.get(shape);
			if (group == null) {
				group = new ArrayList<>();
				groups.put(shape, group);
			}
			group.add(block.getValue());

			i++;
		}

		// build groups
		List<Integer> groupValues = new ArrayList<>();
		for (Map.Entry<Integer, List<Integer>> group : groups.entrySet()) {
			int v = 0;
			if (group.getValue().size() > 1) {
				for (int g : group.getValue()) {
					v |= g;
				}
				groupValues.add(v);
			}
		}
		int[] theGroups = new int[groupValues.size()];
		for (int j = 0; j < theGroups.length; j++) {
			theGroups[j] = groupValues.get(j).intValue();
		}
		return new Board(width, height, b, theGroups);
	}

	public Board build(String puzzle) {
		return build(puzzle.split(System.getProperty("line.separator")));
	}

	public String[] getNames() {
		return names;
	}

}
