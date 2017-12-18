// Reversi

import java.awt.*;
// Class to represent a piece on the board

public class GamePiece {
	
	private Color pieceColor;
	
	public GamePiece(Color pieceColor) {
		this.pieceColor = pieceColor;
	}
	
	// getter for the color
	public Color getColor() {
		return this.pieceColor;
	}
	
	// draw
	public void draw(Graphics g) {
		g.setColor(getColor());
		g.fillOval(0, 0, 100, 100);
	}
}
