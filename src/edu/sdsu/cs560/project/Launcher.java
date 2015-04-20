package edu.sdsu.cs560.project;

public class Launcher {

	public static void main(String[] args) {
		int width, height, x, y;
		
		width = 5;
		height = 4;
		WoodenPuzzle puzzle = new WoodenPuzzle(width, height);
		
		width = 2;
		height = 1;
		x = 0;
		y = 0;
		WoodenBlock A = puzzle.add(new WoodenBlock(puzzle, 'A', width, height, x, y));
		
		width = 2;
		height = 1;
		x = 2;
		y = 0;
		WoodenBlock B = puzzle.add(new WoodenBlock(puzzle, 'B', width, height, x, y));
		
		width = 2;
		height = 1;
		x = 0;
		y = 3;
		WoodenBlock C = puzzle.add(new WoodenBlock(puzzle, 'C', width, height, x, y));
		
		width = 2;
		height = 1;
		x = 2;
		y = 3;
		WoodenBlock D = puzzle.add(new WoodenBlock(puzzle, 'D', width, height, x, y));
		
		width = 1;
		height = 2;
		x = 2;
		y = 1;
		WoodenBlock E = puzzle.add(new WoodenBlock(puzzle, 'E', width, height, x, y));
		
		width = 1;
		height = 1;
		x = 4;
		y = 0;
		WoodenBlock F = puzzle.add(new WoodenBlock(puzzle, 'F', width, height, x, y));
		
		width = 1;
		height = 1;
		x = 3;
		y = 1;
		WoodenBlock G = puzzle.add(new WoodenBlock(puzzle, 'G', width, height, x, y));
		
		width = 1;
		height = 1;
		x = 3;
		y = 2;
		WoodenBlock H = puzzle.add(new WoodenBlock(puzzle, 'H', width, height, x, y));
		
		width = 1;
		height = 1;
		x = 4;
		y = 3;
		WoodenBlock I = puzzle.add(new WoodenBlock(puzzle, 'I', width, height, x, y));
		
		width = 2;
		height = 2;
		x = 0;
		y = 1;
		WoodenBlock J = puzzle.add(new WoodenBlock(puzzle, 'J', width, height, x, y));
		
		System.out.println(puzzle);
	}
	
}
