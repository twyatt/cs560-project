package edu.sdsu.cs560.project;

import java.util.ArrayList;
import java.util.List;

public class WoodenPuzzle {

	public final int width;
	public final int height;
	
	private List<WoodenBlock> blocks = new ArrayList<WoodenBlock>();

	public WoodenPuzzle(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	public WoodenBlock add(WoodenBlock block) {
		blocks.add(block);
		return block;
	}
	
	public int indexOf(int x, int y) {
		return x + width * y;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				builder.append(" ");
				boolean found = false;
				for (WoodenBlock block : blocks) {
					if (block.isAt(x, y)) {
						builder.append(block.letter);
						found = true;
						break;
					}
				}
				if (!found) {
					builder.append("-");
				}
			}
			builder.append("\n");
		}
		return builder.toString();
	}
	
}
