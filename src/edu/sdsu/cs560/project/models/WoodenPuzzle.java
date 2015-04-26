package edu.sdsu.cs560.project.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class WoodenPuzzle {

	public static final String EMPTY_CELL_TEXT = "_";

	public final int width;
	public final int height;
	
	private List<WoodenBlock> blocks = new ArrayList<>();

	public WoodenPuzzle(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	public WoodenPuzzle setBlocks(List<WoodenBlock> blocks) {
		this.blocks.clear();
		this.blocks.addAll(blocks);
		return this;
	}

	public List<WoodenBlock> getBlocks() {
		return blocks;
	}

	public long getConfiguration() {
		List<WoodenBlock> withEmpties = new ArrayList<>(blocks);
		int empty = ~Bitboard.combine(blocks).getValue();
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int index = x + width * y;
				int bitboard = (1 << index);
				if ((empty & bitboard) != 0) {
					WoodenBlock block = new WoodenBlock(this, WoodenPuzzle.EMPTY_CELL_TEXT, bitboard);
					withEmpties.add(block);
				}
			}
		}
		
		List<WoodenBlock> sorted = new ArrayList<>(withEmpties);
		Collections.sort(sorted, new Comparator<WoodenBlock>() {
			@Override
			public int compare(WoodenBlock o1, WoodenBlock o2) {
				int i1 = Integer.lowestOneBit(o1.getValue());
				int i2 = Integer.lowestOneBit(o2.getValue());

				if (i1 > i2) {
					return 1;
				} else if (i1 < i2) {
					return -1;
				} else {
					return 0;
				}
			}
		});

		long configuration = 0;
		int bitsPerBlock = Integer.SIZE - Integer.numberOfLeadingZeros(blocks.size());

		for (int i = 0; i < sorted.size(); i++) {
			WoodenBlock block = sorted.get(i);
			System.out.println("Block " + block.getName() + " has index of " + withEmpties.indexOf(block));
			configuration |= (long) withEmpties.indexOf(block) << (i * bitsPerBlock);
			System.out.println(Long.toBinaryString(configuration));
		}
		System.out.println(Long.toBinaryString(configuration));
		return configuration;
	}

	public WoodenBlock getBlockByName(String name) {
		if (name == null) return null;
		for (WoodenBlock block : blocks) {
			if (block.getName().equals(name)) {
				return block;
			}
		}
		return null;
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
						builder.append(block.name);
						found = true;
						break;
					}
				}
				if (!found) {
					builder.append(EMPTY_CELL_TEXT);
				}
			}
			builder.append(System.getProperty("line.separator"));
		}
		return builder.toString();
	}
	
}
