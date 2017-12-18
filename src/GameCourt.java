// Reversi

import java.awt.*;

// Class for the logic behind what happens when a piece is placed
import javax.swing.*;
import java.util.*;

@SuppressWarnings("serial")
public class GameCourt extends JPanel {
	
	private Space[][] board;
	private ArrayList<Space> playerPossMoves;
	private ArrayList<Space> CPUPossMoves;
	private boolean playerTurn;
	
	public GameCourt() {
		super();
		this.playerTurn = true;
		this.board = new Space[8][8];
		this.playerPossMoves = new ArrayList<Space>();
		this.CPUPossMoves = new ArrayList<Space>();
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				this.board[i][j] = new Space();
			}
		}
		GridLayout tiles = new GridLayout(0, 8);
		this.setLayout(tiles);
	}
	// getter for board, which is represented by a 2D array of Spaces
	public Space[][] getBoard() {
		return this.board;
	}
	
	// returns number of black pieces on the board
	public int numBlack() {
		int count = 0;
		for (int i = 0; i < this.board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j].getPiece() != null && board[i][j].getPiece().getColor().equals(Color.BLACK)) {
					count++;
				}
			}
		}
		return count;
	}
	
	// returns number of white pieces on the board
	public int numWhite() {
		int count = 0; 
		for (int i = 0; i < this.board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j].getPiece() != null && board[i][j].getPiece().getColor().equals(Color.WHITE)) {
					count++;
				}
			}
		}
		return count;
	}
	
	// sets up the board initially, which pieces in the middle
	public void setBoard() {
		for (int i = 0; i < this.board.length; i++) {
			for (int j = 0; j < this.board[i].length; j++) {
				this.add(board[i][j]);
			}
		}
		
		this.board[3][3].setPiece(new GamePiece(Color.WHITE));
		this.board[3][4].setPiece(new GamePiece(Color.BLACK));
		this.board[4][3].setPiece(new GamePiece(Color.BLACK));
		this.board[4][4].setPiece(new GamePiece(Color.WHITE));
		
		this.playerPossMoves = getPlayerPossMoves();
		this.CPUPossMoves = getCPUPossMoves();
	}
	
	// true if it's the players turn, else false
	public boolean getPlayerTurn() {
		return this.playerTurn;
	}
	
	// setter for player turn
	public void setPlayerTurn(boolean b) {
		this.playerTurn = b;
	}
	
	// set the available moves for the player
	public void setPlayerPossMoves(ArrayList<Space> ar) {
		this.playerPossMoves = ar;
	}
	
	// set the available moves for the CPU
	public void setCPUPossMoves(ArrayList<Space> ar) {
		this.CPUPossMoves = ar;
	}
	
	// get all available CPU moves
	public ArrayList<Space> getCPUPossMoves() {
		ArrayList<Space> temp = new ArrayList<Space>();
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j].getType() != SpaceType.EMPTY) {
					continue;
				}
				boolean captures = false;
				for (int k = i; k < board.length; k++) {
					if (board[k][j].getType() == SpaceType.PLAYER && k != i) {
						captures = true;
					}
					if (board[k][j].getType() == SpaceType.EMPTY && k != i) {
						break;
					}
					if (board[k][j].getType() == SpaceType.CPU && !captures && k != i) {
						break;
					}
					if (board[k][j].getType() == SpaceType.CPU && captures && k != i) {
						temp.add(board[i][j]);
						break;
					}
				}
				captures = false;
				for (int k = j; k < board[i].length; k++) {
					if (board[i][k].getType() == SpaceType.PLAYER && k != j) {
						captures = true;
						continue;
					}
					if (board[i][k].getType() == SpaceType.EMPTY && k != j) {
						break;
					}
					if (board[i][k].getType() == SpaceType.CPU && !captures && k != j) {
						break;
					}
					if (board[i][k].getType() == SpaceType.CPU && captures && k != j) {
						temp.add(board[i][j]);
						break;
					}
				}
				captures = false;
				for (int k = i; k >= 0; k--) {
					if (board[k][j].getType() == SpaceType.PLAYER && k != i) {
						captures = true;
						continue;
					}
					if (board[k][j].getType() == SpaceType.EMPTY && k != i) {
						break;
					}
					if (board[k][j].getType() == SpaceType.CPU && !captures && k != i) {
						break;
					}
					if (board[k][j].getType() == SpaceType.CPU && captures && k != i) {
						temp.add(board[i][j]);
						break;
					}
				}
				captures = false;
				for (int k = j; k >= 0; k--) {
					if (board[i][k].getType() == SpaceType.PLAYER && k != j) {
						captures = true;
						continue;
					}
					if (board[i][k].getType() == SpaceType.EMPTY && k != j) {
						break;
					}
					if (board[i][k].getType() == SpaceType.CPU && !captures && k != j) {
						break;
					}
					if (board[i][k].getType() == SpaceType.CPU && captures && k != j) { 
						temp.add(board[i][j]);
						break;
					}
				}
				captures = false;
				for (int k = 0; k < Math.min(8 - i, 8 - j); k++) {
					if (board[k+i][k+j].getType() == SpaceType.PLAYER && k != 0) {
						captures = true;
					}
					if (board[k+i][k+j].getType() == SpaceType.EMPTY && k != 0) {
						break;
					}
					if (board[i+k][j+k].getType() == SpaceType.CPU && !captures && k != 0) {
						break;
					}
					if (board[k+i][k+j].getType() == SpaceType.CPU && captures && k != 0) {
						temp.add(board[i][j]);
						break;
					}
				}
				captures = false;
				for (int k = 0; k < Math.min(8 - i, j); k++) {
					if (board[i+k][j-k].getType() == SpaceType.PLAYER && k != 0) {
						captures = true;
					}
					if (board[i+k][j-k].getType() == SpaceType.EMPTY && k != 0) {
						break;
					}
					if (board[i+k][j-k].getType() == SpaceType.CPU && !captures && k != 0) {
						break;
					}
					if (board[i+k][j-k].getType() == SpaceType.CPU && captures && k != 0) {
						temp.add(board[i][j]);
						break;
					}
				}
				captures = false;
				for (int k = 0; k < Math.min(i , j); k++) {
					if (board[i-k][j-k].getType() == SpaceType.PLAYER && k != 0) {
						captures = true;
					}
					if (board[i-k][j-k].getType() == SpaceType.EMPTY && k != 0) {
						break;
					}
					if (board[i-k][j-k].getType() == SpaceType.CPU && !captures && k != 0) {
						break;
					}
					if (board[i-k][j-k].getType() == SpaceType.CPU && captures && k != 0) {
						temp.add(board[i][j]);
						break;
					}
				}
				captures = false;
				for (int k = 0; k < Math.min(i, 8-j); k++) {
					if (board[i-k][j+k].getType() == SpaceType.PLAYER && k != 0) {
						captures = true;
					}
					if (board[i-k][j+k].getType() == SpaceType.EMPTY && k != 0) {
						break;
					}
					if (board[i-k][j+k].getType() == SpaceType.CPU && !captures && k != 0) {
						break;
					}
					if (board[i-k][j+k].getType() == SpaceType.CPU && captures && k != 0) {
						temp.add(board[i][j]);
						break;
					}
				}
				captures = false;
			}
		}
		return temp;
	}
			
	// get all available player moves
	public ArrayList<Space> getPlayerPossMoves() {
		ArrayList<Space> temp = new ArrayList<Space>();
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j].getType() != SpaceType.EMPTY) {
					continue;
				}
				boolean captures = false;
				for (int k = i; k < 8; k++) {
					if (board[k][j].getType() == SpaceType.CPU && k != i) {
						captures = true;
						continue;
					}
					if (board[k][j].getType() == SpaceType.EMPTY && k != i) {
						break;
					}
					if (board[k][j].getType() == SpaceType.PLAYER && !captures && k != i) {
						break;
					}
					if (board[k][j].getType() == SpaceType.PLAYER && captures && k != i) {
						temp.add(board[i][j]);
						break;
					}
				}
				captures = false;
				for (int k = j; k < board[i].length; k++) {
					if (board[i][k].getType() == SpaceType.CPU && k != j) {
						captures = true;
						continue;
					}
					if (board[i][k].getType() == SpaceType.EMPTY && k != j) {
						break;
					}
					if (board[i][k].getType() == SpaceType.PLAYER && !captures && k != j) {
						break;
					}
					if (board[i][k].getType() == SpaceType.PLAYER && captures && k != j) {
						temp.add(board[i][j]);
						break;
					}
				}
				captures = false;
				for (int k = i; k >= 0; k--) {
					if (board[k][j].getType() == SpaceType.CPU && k != i) {
						captures = true;
					}
					if (board[k][j].getType() == SpaceType.EMPTY && k != i) {
						break;
					}
					if (board[k][j].getType() == SpaceType.PLAYER && !captures && k != i) {
						break;
					}
					if (board[k][j].getType() == SpaceType.PLAYER && captures && k != i) {
						temp.add(board[i][j]);
						break;
					}
				}
				captures = false;
				for (int k = j; k >= 0; k--) {
					if (board[i][k].getType() == SpaceType.CPU && k != j) {
						captures = true;
						continue;
					}
					if (board[i][k].getType() == SpaceType.EMPTY && k != j) {
						break;
					}
					if (board[i][k].getType() == SpaceType.PLAYER && !captures && k != j) {
						break;
					}
					if (board[i][k].getType() == SpaceType.PLAYER && captures && k != j) {
						temp.add(board[i][j]);
						break;
					}
				}
				captures = false;
				for (int k = 0; k < Math.min(8 - i, 8 - j); k++) {
					if (board[k+i][k+j].getType() == SpaceType.CPU && k != 0) {
						captures = true;
						continue;
					}
					if (board[k+i][k+j].getType() == SpaceType.EMPTY && k != 0) {
						break;
					}
					if (board[i+k][j+k].getType() == SpaceType.PLAYER && !captures && k != 0) {
						break;
					}
					if (board[k+i][k+j].getType() == SpaceType.PLAYER && captures) {
						temp.add(board[i][j]);
						break;
					}
				}
				captures = false;
				for (int k = 0; k < Math.min(8 - i, j); k++) {
					if (board[i+k][j-k].getType() == SpaceType.CPU && k != 0) {
						captures = true;
						continue;
					}
					if (board[i+k][j-k].getType() == SpaceType.EMPTY && k != 0) {
						break;
					}
					if (board[i+k][j-k].getType() == SpaceType.PLAYER && !captures && k != 0) {
						break;
					}
					if (board[i+k][j-k].getType() == SpaceType.PLAYER && k != 0) {
						temp.add(board[i][j]);
						break;
					}
				}
				captures = false;
				for (int k = 0; k < Math.min(i , j); k++) {
					if (board[i-k][j-k].getType() == SpaceType.CPU && k != 0) {
						captures = true;
					}
					if (board[i-k][j-k].getType() == SpaceType.EMPTY && k != 0) {
						break;
					}
					if (board[i-k][j-k].getType() == SpaceType.PLAYER && !captures && k != 0) {
						break;
					}
					if (board[i-k][j-k].getType() == SpaceType.PLAYER && captures && k != 0) {
						temp.add(board[i][j]);
						break;
					}
				}
				captures = false;
				for (int k = 0; k < Math.min(i, 8-j); k++) {
					if (board[i-k][j+k].getType() == SpaceType.CPU && k != 0) {
						captures = true;
					}
					if (board[i-k][j+k].getType() == SpaceType.EMPTY && k != 0) {
						break;
					}
					if (board[i-k][j+k].getType() == SpaceType.PLAYER && !captures && k != 0) {
						break;
					}
					if (board[i-k][j+k].getType() == SpaceType.PLAYER && captures && k != 0) {
						temp.add(board[i][j]);
						break;
					}
				}
				captures = false;
			}
		}
		return temp;
	}
	
	// choose a random move for the CPU, out of all available moves
	public void CPUChooseMove() {
		int choice = (int) (Math.random() * CPUPossMoves.size());
		Space s = CPUPossMoves.get(choice);
		s.setPiece(new GamePiece(Color.BLACK));
		flipPieces(s);
	}

	// flip pieces as a result of last move
	public void flipPieces(Space s) {
		Color toFlip = s.getPiece().getColor();
		int row = 0;
		int col = 0;
		if (toFlip.equals(Color.WHITE)) {
			for (int i = 0; i < board.length; i++) {
				for (int j = 0; j < board[i].length; j++) {
					if (board[i][j] == s) {
						row = i;
						col = j;
					}
				}
			}
			boolean captures = false;
			for (int k = row; k < board.length; k++) {
				if (board[k][col].getType() == SpaceType.CPU && k != row) {
					captures = true;
				}
				if (board[k][col].getType() == SpaceType.PLAYER && !captures && k != row) {
					break;
				}
				if (board[k][col].getType() == SpaceType.PLAYER && captures) {
					int l = k - 1;
					while(board[l][col].getType() == SpaceType.CPU) {
						board[l][col].setPiece(new GamePiece(Color.WHITE));
						l--;
					}
					break;
				}
				if (board[k][col].getType() == SpaceType.EMPTY) {
					break;
				}
			}
			captures = false;
			for (int k = row; k >= 0; k--) {
				if (board[k][col].getType() == SpaceType.CPU) {
					captures = true;
				}
				if (board[k][col].getType() == SpaceType.PLAYER && !captures && k != row) {
					break;
				}
				if (board[k][col].getType() == SpaceType.PLAYER && captures) {
					int l = k + 1;
					while (board[l][col].getType() == SpaceType.CPU) {
						board[l][col].setPiece(new GamePiece(Color.WHITE));
						l++;
					}
					break;
				}
				if (board[k][col].getType() == SpaceType.EMPTY) {
					break;
				}
			}
			captures = false;
			for (int k = col; k < board.length; k++) {
				if (board[row][k].getType() == SpaceType.CPU) {
					captures = true;
				}
				if (board[row][k].getType() == SpaceType.PLAYER && !captures && k != col) {
					break;
				}
				if (board[row][k].getType() == SpaceType.PLAYER && captures) {
					int l = k - 1;
					while (board[row][l].getType() == SpaceType.CPU) {
						board[row][l].setPiece(new GamePiece(Color.WHITE));
						l--;
					}
					break;
				}
				if (board[row][k].getType() == SpaceType.EMPTY) {
					break;
				}
			}
			captures = false;
			for (int k = col; k >= 0; k--) {
				if (board[row][k].getType() == SpaceType.CPU) {
					captures = true;
				}
				if (board[row][k].getType() == SpaceType.PLAYER && !captures && k != col) {
					break;
				}
				if (board[row][k].getType() == SpaceType.PLAYER && captures) {
					int l = k + 1;
					while (board[row][l].getType() == SpaceType.CPU) {
						board[row][l].setPiece(new GamePiece(Color.WHITE));
						l++;
					}
					break;
				}
				if (board[row][k].getType() == SpaceType.EMPTY) {
					break;
				}
			}
			captures = false;
			for (int k = 0; k < Math.min(8- row, 8 - col); k++) {
				if (board[row+k][col+k].getType() == SpaceType.CPU) {
					captures = true;
				}
				if (board[row+k][col+k].getType() == SpaceType.PLAYER && !captures && k != 0) {
					break;
				}
				if (board[row+k][col+k].getType() == SpaceType.PLAYER && captures) {
					int l = k - 1;
					while (board[row+l][col+l].getType() == SpaceType.CPU) {
						board[row+l][col+l].setPiece(new GamePiece(Color.WHITE));
						l--;
					}
					break;
				}
				if (board[row+k][col+k].getType() == SpaceType.EMPTY) {
					break;
				}
			}
			captures = false;
			for (int k = 0; k < Math.min(row, 8 - col); k++) {
				if (board[row-k][col+k].getType() == SpaceType.CPU) {
					captures = true;
				}
				if (board[row-k][col+k].getType() == SpaceType.PLAYER && !captures && k != 0) {
					break;
				}
				if (board[row-k][col+k].getType() == SpaceType.PLAYER && captures) {
					int l = k - 1;
					while (board[row-l][col+l].getType() == SpaceType.CPU) {
						board[row-l][col+l].setPiece(new GamePiece(Color.WHITE));
						l--;
					}
					break;
				}
				if (board[row-k][col+k].getType() == SpaceType.EMPTY) {
					break;
				}
			}
			captures = false;
			for (int k = 0; k < Math.min(row, col); k++) {
				if (board[row-k][col-k].getType() == SpaceType.CPU) {
					captures = true;
				}
				if (board[row-k][col-k].getType() == SpaceType.PLAYER && !captures && k != 0) {
					break;
				}
				if (board[row-k][col-k].getType() == SpaceType.PLAYER && captures) {
					int l = k - 1;
					while (board[row-l][col-l].getType() == SpaceType.CPU) {
						board[row-l][col-l].setPiece(new GamePiece(Color.WHITE));
						l--;
					}
					break;
				}
				if (board[row-k][col-k].getType() == SpaceType.EMPTY) {
					break;
				}
			}
			captures = false; 
			for (int k = 0; k < Math.min(8-row, col); k++) {
				if (board[row+k][col-k].getType() == SpaceType.CPU) {
					captures = true;
				}
				if (board[row+k][col-k].getType() == SpaceType.PLAYER && !captures && k != 0) {
					break;
				}
				if (board[row+k][col-k].getType() == SpaceType.PLAYER && captures) {
					int l = k - 1;
					while (board[row+l][col-l].getType() == SpaceType.CPU) {
						board[row+l][col-l].setPiece(new GamePiece(Color.WHITE));
						l--;
					}
					break;
				}
				if (board[row+k][col-k].getType() == SpaceType.EMPTY) {
					break;
				}
			} 
		}
		else {	
			for (int i = 0; i < board.length; i++) {
				for (int j = 0; j < board[i].length; j++) {
					if (board[i][j] == s) {
						row = i;
						col = j;
					}
				}
			}
			boolean captures = false;
			for (int k = row; k < board.length; k++) {
				if (board[k][col].getType() == SpaceType.PLAYER && k != row) {
					captures = true;
				}
				if (board[k][col].getType() == SpaceType.CPU && !captures && k != row) {
					break;
				}
				if (board[k][col].getType() == SpaceType.CPU && captures) {
					int l = k - 1;
					while(board[l][col].getType() == SpaceType.PLAYER) {
						board[l][col].setPiece(new GamePiece(Color.BLACK));
						l--;
					}
					break;
				}
				if (board[k][col].getType() == SpaceType.EMPTY) {
					break;
				}
			}
			captures = false;
			for (int k = row; k >= 0; k--) {
				if (board[k][col].getType() == SpaceType.PLAYER) {
					captures = true;
				}
				if (board[k][col].getType() == SpaceType.CPU && !captures && k != row) {
					break;
				}
				if (board[k][col].getType() == SpaceType.CPU && captures) {
					int l = k + 1;
					while (board[l][col].getType() == SpaceType.PLAYER) {
						board[l][col].setPiece(new GamePiece(Color.BLACK));
						l++;
					}
					break;
				}
				if (board[k][col].getType() == SpaceType.EMPTY) {
					break;
				}
			}
			captures = false;
			for (int k = col; k < board.length; k++) {
				if (board[row][k].getType() == SpaceType.PLAYER) {
					captures = true;
				}
				if (board[row][k].getType() == SpaceType.CPU && !captures && k != col) {
					break;
				}
				if (board[row][k].getType() == SpaceType.CPU && captures) {
					int l = k - 1;
					while (board[row][l].getType() == SpaceType.PLAYER) {
						board[row][l].setPiece(new GamePiece(Color.BLACK));
						l--;
					}
					break;
				}
				if (board[row][k].getType() == SpaceType.EMPTY) {
					break;
				}
			}
			captures = false;
			for (int k = col; k >= 0; k--) {
				if (board[row][k].getType() == SpaceType.PLAYER) {
					captures = true;
				}
				if (board[row][k].getType() == SpaceType.CPU && !captures && k != col) {
					break;
				}
				if (board[row][k].getType() == SpaceType.CPU && captures) {
					int l = k + 1;
					while (board[row][l].getType() == SpaceType.PLAYER) {
						board[row][l].setPiece(new GamePiece(Color.BLACK));
						l++;
					}
				}
				if (board[row][k].getType() == SpaceType.EMPTY) {
					break;
				}
			}
			captures = false;
			for (int k = 0; k < Math.min(8- row, 8 - col); k++) {
				if (board[row+k][col+k].getType() == SpaceType.PLAYER) {
					captures = true;
				}
				if (board[row+k][col+k].getType() == SpaceType.CPU && !captures && k != 0) {
					break;
				}
				if (board[row+k][col+k].getType() == SpaceType.CPU && captures) {
					int l = k - 1;
					while (board[row+l][col+l].getType() == SpaceType.PLAYER) {
						board[row+l][col+l].setPiece(new GamePiece(Color.BLACK));
						l--;
					}
					break;
				}
				if (board[row+k][col+k].getType() == SpaceType.EMPTY) {
					break;
				}
			}
			captures = false;
			for (int k = 0; k < Math.min(row, 8 - col); k++) {
				if (board[row-k][col+k].getType() == SpaceType.PLAYER) {
					captures = true;
				}
				if (board[row-k][col+k].getType() == SpaceType.CPU && !captures && k != 0) {
					break;
				}
				if (board[row-k][col+k].getType() == SpaceType.CPU && captures) {
					int l = k - 1;
					while (board[row-l][col+l].getType() == SpaceType.PLAYER) {
						board[row-l][col+l].setPiece(new GamePiece(Color.BLACK));
						l--;
					}
					break;
				}
				if (board[row-k][col+k].getType() == SpaceType.EMPTY) {
					break;
				}
			}
			captures = false;
			for (int k = 0; k < Math.min(row, col); k++) {
				if (board[row-k][col-k].getType() == SpaceType.PLAYER) {
					captures = true;
				}
				if (board[row-k][col-k].getType() == SpaceType.CPU && !captures && k != 0) {
					break;
				}
				if (board[row-k][col-k].getType() == SpaceType.CPU && captures) {
					int l = k - 1;
					while (board[row-l][col-l].getType() == SpaceType.PLAYER) {
						board[row-l][col-l].setPiece(new GamePiece(Color.BLACK));
						l--;
					}
				}
			}
			captures = false; 
			for (int k = 0; k < Math.min(8-row, col); k++) {
				if (board[row+k][col-k].getType() == SpaceType.PLAYER) {
					captures = true;
				}
				if (board[row+k][col-k].getType() == SpaceType.CPU && !captures && k != 0) {
					break;
				}
				if (board[row+k][col-k].getType() == SpaceType.CPU && captures) {
					int l = k - 1;
					while (board[row+l][col-l].getType() == SpaceType.PLAYER) {
						board[row+l][col-l].setPiece(new GamePiece(Color.BLACK));
						l--;
					}
				}
			}
		}
	}
}