package tetris;

import java.awt.Color;

import javax.swing.JFrame;

public class MainWindow {
	private JFrame window; 
	private Board board;
	
	public MainWindow () {
		
		window = new JFrame("Tetris");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        
        window.getContentPane().setBackground(Color.RED);
        window.setSize(445 + 30, 629); //account for window size this is the size of window
        board = new Board();
        window.add(board);
       // window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        // Ensure the board has focus to receive key events
        board.requestFocusInWindow();
	}
	public static void main (String [] args) {
		new MainWindow() ; 
		
	}
}
