import edu.sdsu.cs560.project.helpers.Vector2i;
import edu.sdsu.cs560.project.models.Bitboard;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BitboardTest {

	@Test
	public void testPosition() throws Exception {
		int x = 3;
		int y = 1;
		int bitboard = Bitboard.draw(0, 5, x, y, 2, 2);
		Vector2i position = Bitboard.position(bitboard, 5);
		assertEquals(x, position.x);
		assertEquals(y, position.y);
	}

}
