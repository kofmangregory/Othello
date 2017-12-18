import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Test;

public class GameTest {

	@Test
	public void testNumPiecesInSetup() {
		GameCourt court = new GameCourt();
		court.setBoard();
		assertEquals(court.numBlack(), 2);
		assertEquals(court.numWhite(), 2);
	}
	
	@Test
	public void testNumPossMovesInSetup() {
		GameCourt court = new GameCourt();
		court.setBoard();
		assertEquals(court.getPlayerPossMoves().size(), court.getCPUPossMoves().size());
		assertEquals(court.getCPUPossMoves().size(), 4);
	}
	
	@Test
	public void testFlipWhite() {
		GameCourt court = new GameCourt();
		court.setBoard();
		Space[][] board = court.getBoard();
		board[2][3].setPiece(new GamePiece(Color.BLACK));
		court.flipPieces(board[2][3]);
		assertEquals(court.numBlack(), 4);
		assertEquals(court.numWhite(), 1);
	}
}
