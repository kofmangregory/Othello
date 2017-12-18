// Reversi 
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

// Game Main Class that specifies the frame and widgets of the GUI

public class Game implements Runnable {

	public void run() {

		//Top level frame in which the game components live
		final JFrame frame = new JFrame("Reversi!");
		frame.setLocation(500, 500);
		frame.setSize(1000, 1000);
		
		//Main playing area
		GameCourt court = new GameCourt();
		frame.add(court, BorderLayout.CENTER);
		
		//Exit the game when closing
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
		//Launch Instructions
		JOptionPane.showMessageDialog(frame, "Welcome to Reversi.\nHere are the rules of the game:\nYour goal is to capture the opponent's pieces. The player with the most pieces at the end of the game wins.\n" 
				                      + "To capture pieces, you must have two pieces at both ends of a line of opponent pieces. These lines can be vertical, horizontal, or diagonal.\nValid moves must capture at least one opponent piece.\n"
				                      + "Click on a tile to place a piece there. Click again for the CPU to place a piece.\n"
				                      + "If either Player or CPU has no available moves, game will give control to other party.\nEnjoy the game!", "Instructions", JOptionPane.PLAIN_MESSAGE);
		
		// Toolbar that contains running score
		JPanel toolbar = new JPanel();
		JLabel label = new JLabel("Player: " + court.numWhite() + "\n" 
		                          + "CPU: " + court.numBlack());
		toolbar.add(label);
		frame.add(toolbar, BorderLayout.NORTH);
		
		//Mechanism for updating score and board at each click
		Space[][] board = court.getBoard();
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				Space s = board[i][j];
				board[i][j].addMouseListener(new java.awt.event.MouseAdapter() {
					public void mouseClicked(java.awt.event.MouseEvent e) {
						if (!court.getPlayerPossMoves().isEmpty() && court.getPlayerTurn()) {
							if (court.getPlayerPossMoves().contains(s)) {
								s.setPiece(new GamePiece(Color.WHITE));
								court.setPlayerPossMoves(court.getPlayerPossMoves());
								court.setCPUPossMoves(court.getCPUPossMoves());
								court.flipPieces(s);
								court.setPlayerTurn(!court.getPlayerTurn()); 
							}					
						}
						else if (court.getPlayerTurn() && court.getPlayerPossMoves().isEmpty()) {
							court.setPlayerTurn(!court.getPlayerTurn());
						}
						else if (!court.getCPUPossMoves().isEmpty() && !court.getPlayerTurn()){
							court.setCPUPossMoves(court.getCPUPossMoves());
							court.CPUChooseMove();
							court.setPlayerPossMoves(court.getPlayerPossMoves());
							court.setPlayerTurn(!court.getPlayerTurn()); 
						}
						else if (!court.getPlayerTurn() && court.getCPUPossMoves().isEmpty()) {
							court.setPlayerTurn(!court.getPlayerTurn());
						}
						label.setText("Player: " + court.numWhite() + " CPU: " + court.numBlack());
					}
				});
			}
		}
		
		//Make the frame visible
		frame.setVisible(true);

        // Start game
        court.setBoard();
       // court.setMouseClickers();
        
	}
	
	// Main
	public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }

}
