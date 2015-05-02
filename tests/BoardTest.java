import edu.sdsu.cs560.project.Builder;
import edu.sdsu.cs560.project.Movement;
import edu.sdsu.cs560.project.Board;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class BoardTest {

	private int getBlockIndexByName(String[] names, String name) {
		for (int i = 0; i < names.length; i++) {
			String s = names[i];
			if (s.equals(name)) return i;
		}
		return -1;
	}

	@Test
	public void testEquals() throws Exception {
		Builder builder = new Builder();
		Board puzzle1 = builder.build(
			"A A B B C",
			"D E F F  ",
			"G H F F  ",
			"G H I I J"
		);
		Board puzzle2 = builder.build(
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
		Board puzzle3 = puzzle1.move(C, Movement.Direction.DOWN);
		assertFalse(puzzle3.equals(puzzle1));
		assertFalse(puzzle3.equals(puzzle2));

		Board puzzle4 = puzzle2.move(C, Movement.Direction.UP);
		assertFalse(puzzle4.equals(puzzle2));
		assertTrue(puzzle4.equals(puzzle3));
	}

	@Test
	public void testHashset() throws Exception {
		Set<Board> puzzles = new HashSet<>();

		Builder builder = new Builder();
		Board puzzle1 = builder.build(
				"A A B B C",
				"D E F F  ",
				"G H F F  ",
				"G H I I J"
		);
		Board puzzle2 = builder.build(
				"A A B B  ",
				"D E F F C",
				"G H F F  ",
				"G H I I J"
		);
		String[] names = builder.getNames();

		puzzles.add(puzzle1);
		assertFalse(puzzles.contains(puzzle2));

		int C = getBlockIndexByName(names, "C");
		Board puzzle3 = puzzle2.move(C, Movement.Direction.UP);
		assertTrue(puzzles.contains(puzzle3));
	}

	@Test
	public void testMove() throws Exception {
		Builder builder = new Builder();
		Board puzzle1 = builder.build(
				"A A B B C",
				"D E F F  ",
				"G H F F  ",
				"G H I I J"
		);
		String[] names = builder.getNames();

		int A = getBlockIndexByName(names, "A");
		assertNull(puzzle1.move(A, Movement.Direction.RIGHT));
		assertNull(puzzle1.move(A, Movement.Direction.DOWN));

		int C = getBlockIndexByName(names, "C");
		Board puzzle2 = puzzle1.move(C, Movement.Direction.DOWN);
		assertNotNull(puzzle2);
	}

}
