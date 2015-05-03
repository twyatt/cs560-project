import edu.sdsu.cs560.project.Bitboard;
import edu.sdsu.cs560.project.Builder;
import edu.sdsu.cs560.project.Movement;
import edu.sdsu.cs560.project.Board;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class BoardTest {

	private int getBlockIndexByName(String name, String[] names) {
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

		int C = getBlockIndexByName("C", names);
		Board puzzle3 = puzzle1.move(C, Movement.Direction.DOWN);
		assertFalse(puzzle3.equals(puzzle1));
		assertFalse(puzzle3.equals(puzzle2));

		Board puzzle4 = puzzle2.move(C, Movement.Direction.UP);
		assertFalse(puzzle4.equals(puzzle2));
		assertTrue(puzzle4.equals(puzzle3));
	}

	@Test
	public void testHashSet() throws Exception {
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

		int C = getBlockIndexByName("C", names);
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

		int A = getBlockIndexByName("A", names);
		assertNull(puzzle1.move(A, Movement.Direction.RIGHT));
		assertNull(puzzle1.move(A, Movement.Direction.DOWN));

		int C = getBlockIndexByName("C", names);
		Board puzzle2 = puzzle1.move(C, Movement.Direction.DOWN);
		assertNotNull(puzzle2);
	}

	@Test
	public void testGroup() throws Exception {
		Builder builder = new Builder();
		Board puzzle1 = builder.build(
				"A A B B F",
				"J J E G  ",
				"J J E H  ",
				"C C D D I"
		);
		String[] names = builder.getNames();
		assertEquals(2, puzzle1.groups.length);

		int group0 = builder.build(
				"        1",
				"      1  ",
				"      1  ",
				"        1"
		).blocks[0];
		int group1 = builder.build(
				"1 1 1 1  ",
				"         ",
				"         ",
				"1 1 1 1  "
		).blocks[0];
		assertEquals(group0, puzzle1.groups[0]);
		assertEquals(group1, puzzle1.groups[1]);


		group0 = builder.build(
				"         ",
				"      1 1",
				"      1  ",
				"        1"
		).blocks[0];
		Board puzzle2 = puzzle1.move(getBlockIndexByName("F", names), Movement.Direction.DOWN);
		assertEquals(group0, puzzle2.groups[0]);

//		Board puzzle2 = puzzle1.move(getBlockIndexByName("F", names), Movement.Direction.DOWN);
//		puzzle2 = puzzle2.move(getBlockIndexByName("F", names), Movement.Direction.DOWN);
//		puzzle2 = puzzle2.move(getBlockIndexByName("G", names), Movement.Direction.RIGHT);
//		puzzle2 = puzzle2.move(getBlockIndexByName("G", names), Movement.Direction.UP);
//		puzzle2 = puzzle2.move(getBlockIndexByName("F", names), Movement.Direction.UP);
//		puzzle2 = puzzle2.move(getBlockIndexByName("F", names), Movement.Direction.LEFT);
//		System.out.println(Bitboard.toString(puzzle2.getMembership(1), puzzle1.width, puzzle1.height));
	}

	@Test
	public void testEquality() throws Exception {
		Builder builder = new Builder();
		Board puzzle1 = builder.build(
				"A A B B C",
				"D E F F  ",
				"G H F F  ",
				"G H I I J"
		);
		Board puzzle2 = builder.build(
				"A A B B E",
				"D C F F  ",
				"G H F F  ",
				"G H I I J"
		);
		String[] names = builder.getNames();
		Board puzzle3 = puzzle2.move(getBlockIndexByName("E", names), Movement.Direction.DOWN);

		assertEquals(puzzle1.hashCode(), puzzle2.hashCode());
		assertEquals(puzzle1, puzzle2);
		assertNotEquals(puzzle1.hashCode(), puzzle3.hashCode());
		assertNotEquals(puzzle2.hashCode(), puzzle3.hashCode());
	}

}
