import edu.sdsu.cs560.project.helpers.WoodenPuzzleBuilder;
import edu.sdsu.cs560.project.models.WoodenBlockMovement;
import edu.sdsu.cs560.project.models.WoodenPuzzle;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class WoodenPuzzleTest {

	private int getBlockIndexByName(String[] names, String name) {
		for (int i = 0; i < names.length; i++) {
			String s = names[i];
			if (s.equals(name)) return i;
		}
		return -1;
	}

	@Test
	public void testEquals() throws Exception {
		WoodenPuzzleBuilder builder = new WoodenPuzzleBuilder();
		WoodenPuzzle puzzle1 = builder.build(
			"A A B B C",
			"D E F F  ",
			"G H F F  ",
			"G H I I J"
		);
		WoodenPuzzle puzzle2 = builder.build(
			"A A B B C",
			"D E F F  ",
			"G H F F  ",
			"G H I I J"
		);
		String[] names = builder.getNames();

		assertTrue(puzzle1.equals(puzzle2));


		puzzle1 = builder.build(
				"A A B B C",
				"D E F F  ",
				"G H F F  ",
				"G H I I J"
		);
		puzzle2 = builder.build(
				"A A B B  ",
				"D E F F  ",
				"G H F F C",
				"G H I I J"
		);
		assertFalse(puzzle1.equals(puzzle2));

		int C = getBlockIndexByName(names, "C");
		WoodenPuzzle puzzle3 = puzzle1.move(C, WoodenBlockMovement.Direction.DOWN);
		assertFalse(puzzle3.equals(puzzle1));
		assertFalse(puzzle3.equals(puzzle2));

		WoodenPuzzle puzzle4 = puzzle2.move(C, WoodenBlockMovement.Direction.UP);
		assertFalse(puzzle4.equals(puzzle2));
		assertTrue(puzzle4.equals(puzzle3));
	}

	@Test
	public void testHashset() throws Exception {
		Set<WoodenPuzzle> puzzles = new HashSet<>();

		WoodenPuzzleBuilder builder = new WoodenPuzzleBuilder();
		WoodenPuzzle puzzle1 = builder.build(
				"A A B B C",
				"D E F F  ",
				"G H F F  ",
				"G H I I J"
		);
		WoodenPuzzle puzzle2 = builder.build(
				"A A B B  ",
				"D E F F C",
				"G H F F  ",
				"G H I I J"
		);
		String[] names = builder.getNames();

		puzzles.add(puzzle1);
		assertFalse(puzzles.contains(puzzle2));

		int C = getBlockIndexByName(names, "C");
		WoodenPuzzle puzzle3 = puzzle2.move(C, WoodenBlockMovement.Direction.UP);
		assertTrue(puzzles.contains(puzzle3));
	}

	@Test
	public void testMove() throws Exception {
		WoodenPuzzleBuilder builder = new WoodenPuzzleBuilder();
		WoodenPuzzle puzzle1 = builder.build(
				"A A B B C",
				"D E F F  ",
				"G H F F  ",
				"G H I I J"
		);
		String[] names = builder.getNames();

		int A = getBlockIndexByName(names, "A");
		assertNull(puzzle1.move(A, WoodenBlockMovement.Direction.RIGHT));
		assertNull(puzzle1.move(A, WoodenBlockMovement.Direction.DOWN));

		int C = getBlockIndexByName(names, "C");
		WoodenPuzzle puzzle2 = puzzle1.move(C, WoodenBlockMovement.Direction.DOWN);
		assertNotNull(puzzle2);
	}

}
