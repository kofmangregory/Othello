// Reversi

import java.awt.*;
import java.util.TreeSet;

import javax.swing.*;

// Space class that represents a board tile
@SuppressWarnings("serial")
public class Space extends JPanel implements Comparable<Space>{
	
	private GamePiece piece;
	private SpaceType type;
	public final int WIDTH = 200;
	public final int HEIGHT = 200;
	
	public Space() {
		super();
		this.type = SpaceType.EMPTY;
		this.setBackground(Color.GREEN);
		this.setSize(200, 200);
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	}
	
	//checks if space contains a piece
	public boolean isEmpty() {
		return (this.type == SpaceType.EMPTY);
	}
	
	//puts a piece on the space
	public void setPiece(GamePiece play) {
		if (play.getColor().equals(Color.WHITE)) {
			this.type = SpaceType.PLAYER;
		}
		else if (play.getColor().equals(Color.BLACK)) {
			this.type = SpaceType.CPU;
		}
		this.piece = play;
		repaint();
		
	}
	
	// returns the type of space (EMPTY, PLAYER, CPU)
	public SpaceType getType() {
		return this.type;
	}
	
	//returns the piece on the tile, or null if none exists
	public GamePiece getPiece() {
		if (!isEmpty()) {
			return this.piece;
		}
		return null;
	}
	
	// paints
	@Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (!isEmpty()) {
        	this.piece.draw(g);
        }
	}
	
	//compareTo
	@Override
	public int compareTo(Space that) {
		if (this == that) {
			return 0;
		}
		if (this.getType() == SpaceType.EMPTY && that.getType() != SpaceType.EMPTY) {
			return -1;
		}
		if (this.getType() != SpaceType.EMPTY && that.getType() == SpaceType.EMPTY) {
			return 1;
		}
		if (this.getType() == SpaceType.PLAYER && that.getType() == SpaceType.CPU) {
			return 1;
		}
		if (this.getType() == SpaceType.CPU && that.getType() == SpaceType.PLAYER) {
			return -1;
		}
		return 0;
	}
}
