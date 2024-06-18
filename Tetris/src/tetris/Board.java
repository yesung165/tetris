package tetris;
import javax.swing.JPanel;
import javax.swing.*;
import java.awt.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random; 

public class Board extends JPanel implements KeyListener
{
	private final static int rows = 20;
	private final static int col = 10; 
	private final int UNIT = 30;
	
	//all the colors blocks can have
	private static final Color[] PIECE_COLORS = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.ORANGE, Color.MAGENTA, Color.CYAN};
	 
  
	
	private Shapes block ; //block
	private Shapes nextBlock ; 
	private Shapes projectionBlock ; 
	private boolean isPaused = false;

	
	private static Color[][] background ; //blocks in the background 
	
	 private static final Random random = new Random();
	private Timer timer; //timer var
    private final int DELAY = 500; // in mili seconds
    private boolean isGameOver = false; //see if game is over
    private int score; //score
    
    
	public Board () //constructor
	{
		setPreferredSize(new Dimension(col * UNIT, rows * UNIT));
        setFocusable(true);
        addKeyListener(this);
        
        requestFocusInWindow(); // Request focus so the panel gets key events
        background = new Color [rows][col] ; //initiate empty background with size of the game area 
        spawnNextBlock() ; 
        spawnBlock();
    ; 
        
      
        
        
        timer = new Timer(DELAY, 
        		new ActionListener() {
            @Override
            
			public void actionPerformed(ActionEvent e) {
            	if (!isGameOver ) {
				 blockDown();
				// TODO Auto-generated method stub
            	}
				
			}
        });
        timer.start();
	}
	public static int getRows() {
		return rows;
	}
	
	public static int getCols() {
		return col ; 
	}
	
	
	public void drawBlock (Graphics g) { //draw a single block
	
		Color color = block.getColor() ; 
		int [][] shape = block.getBlock() ;
		for (int row = 0 ; row < block.getHeight() ; row ++ ) {
			for (int col = 0 ; col < block.getWidth() ; col ++) {
				if (shape[row][col] == 1) {
					g.setColor(color);
					g.fillRect(block.getX() * UNIT + col * UNIT,block.getY() * UNIT + row * UNIT , UNIT, UNIT);
					g.setColor(Color.BLACK);
					g.drawRect(block.getX() * UNIT + col * UNIT,block.getY() * UNIT + row * UNIT , UNIT, UNIT);
				}
			}
		}

		
	}
	
	private void drawBackground (Graphics g) {
		Color color ;
		for (int y = 0; y < rows; y++) { //for the whole game area
			for (int x = 0 ; x < col ; x ++ ) {
				color = background [y][x] ; //read values of background
				if (color != null) { //if theres things to draw
					//draw
					g.setColor(color);
					g.fillRect(x * UNIT , y * UNIT, UNIT, UNIT);
					g.setColor(Color.BLACK);
					g.drawRect(x * UNIT, y * UNIT , UNIT, UNIT);
				}
			}
			
		}
	}
	
	private void moveBackground() {
		int [][]shape = block.getBlock() ; //read the shape to move to background
		Color color = block.getColor() ; //get its color
		for (int row = 0 ; row < block.getHeight() ; row ++ ) {//for its size
			for (int col = 0 ; col < block.getWidth() ; col ++) {
				
				if (shape[row][col] == 1) { //if we have to draw 
					if (row + block.getY() > rows ) { //if we have to draw above the game board its game over (for faster gameover) 
						isGameOver = true ;
						break;
					}
					if (row + block.getY()>= 0 && row + block.getY()< rows && col + block.getX() >= 0 && col + block.getX()< this.col) { //make sure to only move to background within the region of the board 
					background[row + block.getY()][col + block.getX()] = color; // move that to the background with the color
				}
				}
			}
		}
	}
	
	private boolean checkProjectionBottom() {
	    for (int row = 0; row < projectionBlock.getHeight(); row++) {
	        for (int col = 0; col < projectionBlock.getWidth(); col++) {
	            if (projectionBlock.getBlock()[row][col] == 1) {
	                int newY = projectionBlock.getY() + row + 1;
	                int newX = projectionBlock.getX() + col;
	                if (newY >= rows || (newY >= 0 && background[newY][newX] != null)) {
	                    return false;
	                }
	            }
	        }
	    }
	    return true;
	}
	private void drawProjection (Graphics g ) {
		projectionBlock = new Shapes (block.getBlock(), block.getColor()) ; 
		projectionBlock.setX(block.getX());
	    projectionBlock.setY(block.getY());
		int [][]shape = projectionBlock.getBlock() ; 
		
		while (checkProjectionBottom()) {
	        projectionBlock.moveDown();
	      
	    }
		//repaint();
		Color c = new Color(128, 128, 128, 128); //transparent color for where the block is going to be

		
		
		Color color = block.getColor() ; 
	
		for (int row = 0 ; row < block.getHeight() ; row ++ ) {
			for (int col = 0 ; col < block.getWidth() ; col ++) {
				if (shape[row][col] == 1) {
					g.setColor(c);
					g.fillRect(block.getX() * UNIT + col * UNIT,projectionBlock.getY() * UNIT + row * UNIT , UNIT, UNIT);
					g.setColor(Color.BLACK);
					g.drawRect(block.getX() * UNIT + col * UNIT,projectionBlock.getY() * UNIT + row * UNIT , UNIT, UNIT);
				}
			}
		}

		
	}

	  private void drawNextBlock(Graphics g) {
	        if (nextBlock != null) {
	            Color color = nextBlock.getColor();
	            int[][] shape = nextBlock.getBlock();
	            
	            for (int row = 0; row < nextBlock.getHeight(); row++) {
	                for (int col = 0; col < nextBlock.getWidth(); col++) {
	                	
	                    if (shape[row][col] == 1) {
	                    	
	                        g.setColor(color);
	                        g.fillRect(col * UNIT + 11 * UNIT, row  * UNIT + 2 * UNIT, UNIT, UNIT); // Adjust positioning
	                        g.setColor(Color.BLACK);
	                        g.drawRect(col * UNIT + 11 * UNIT, row * UNIT + 2 * UNIT, UNIT, UNIT); // Adjust positioning
	                    }
	                }
	            }
	        }
	    }
	  
	private void spawnNextBlock () {
		Color randColor = PIECE_COLORS[random.nextInt(7)] ; //random color
		switch(random.nextInt(7) ) { //a random switch to spawn random shape
		case 0:
            nextBlock = new IShape(randColor);
            break;
        case 1:
        	nextBlock = new JShape(randColor);
            break;
        case 2:
        	nextBlock = new LShape(randColor);
            break;
        case 3:
        	nextBlock = new OShape(randColor);
            break;
        case 4:
        	nextBlock = new SShape(randColor);
            break;
        case 5:
        	nextBlock = new TShape(randColor);
            break;
        case 6:
        	nextBlock = new ZShape(randColor);
            break;
    }
	}
	
	
	public void spawnBlock () {
		block = nextBlock ; 
		spawnNextBlock() ; 
	 
	
		if (!checkBottom()) {
            isGameOver = true;
            timer.stop();
        } 
		
		repaint()  ; 
	}
	
	private void checkLine () { //check and clear line
		int linesCleared = 0;
        for (int y = rows - 1; y >= 0; y--) {
            boolean fullLine = true;
            for (int x = 0; x < col; x++) {
                if (background[y][x] == null) {
                    fullLine = false;
                    break;
                }
            }
            if (fullLine) {
                linesCleared++;
                // Move all rows above down
                for (int row = y; row > 0; row--) {
                    for (int x = 0; x < col; x++) {
                        background[row][x] = background[row - 1][x];
                    }
                }
                // Clear top row
                for (int x = 0; x < col; x++) {
                    background[0][x] = null;
                }
                y++; // Recheck the same row
            }
        }
        
        // Update score based on the number of lines cleared
        switch (linesCleared) {
            case 1:
                score += 100;
                break;
            case 2:
                score += 300;
                break;
            case 3:
                score += 500;
                break;
            case 4:
                score += 800; //tetris	
                break;
        }
    }
	

	
	public void blockDown () {//moving block down     
	       
		if (!checkBottom())  { //if touching ground
			moveBackground();  //move to background
			checkLine() ; //check if we need to clear line
			spawnBlock() ; //spawn new block
			if (!checkBottom()) {
                timer.stop(); // Stop the timer if the new block cannot move down
			}
			return ;
		}
					
		block.moveDown(); 
		repaint() ; 
		
	}
	
	public void blockLeft () {
		
		if (!checkLeft()) return; 

		block.moveLeft () ; //move block left if nothing blocking 
		repaint();
	}
	
	public void blockRight () {
		if (!checkRight ()) return ; 
		
		block.moveRight() ; //move right if nothign blocking 
		repaint();
	}
	
	public void blockDrop() { //drop to the ground
		timer.stop(); //stop timer for a bit during block drop
	    while (checkBottom()) {
	        block.moveDown();
	    }
	    moveBackground();
	    checkLine();
	    spawnBlock();
	    repaint();
	    if (!isGameOver) {
            timer.start();
        }
	}

    private boolean checkBottom() {
        for (int row = 0; row < block.getHeight(); row++) {
            for (int col = 0; col < block.getWidth(); col++) {
                if (block.getBlock()[row][col] == 1) {
                    int newY = block.getY() + row + 1;
                    int newX = block.getX() + col;
                    if (newY >= rows || newY >= 0 && background[newY][newX] != null) { //if bottom is null
//                    	if (newY >rows) {
//                    		isGameOver = true;
//                    	}
                        return false;
                    }
                    
                }
            }
        }
        return true;
    }

    private boolean checkRight() {
        for (int row = 0; row < block.getHeight(); row++) {
            for (int col = block.getWidth() - 1; col >= 0; col--) {
                if (block.getBlock()[row][col] == 1) {
                    int newY = block.getY() + row;
                    int newX = block.getX() + col + 1;
                    if (newX >= this.col || (newY >= 0 && background[newY][newX] != null)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean checkLeft() {
        for (int row = 0; row < block.getHeight(); row++) {
            for (int col = 0; col < block.getWidth(); col++) {
                if (block.getBlock()[row][col] == 1) {
                    int newY = block.getY() + row;
                    int newX = block.getX() + col - 1;
                    if (newX < 0 || (newY >= 0 && background[newY][newX] != null)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
	


    public void rotateBlock() {
        block.rotate();

        // Ensure the block is within bounds after rotation
        if (block.getX() < 0) {
            block.setX(0);
        }
        if (block.getX() + block.getWidth() > col) {
            block.setX(col - block.getWidth());
        }
        if (block.getY() < 0) {
            block.setY(0);
        }
        if (block.getY() + block.getHeight() > rows) {
            block.setY(rows - block.getHeight());
        }

        // Check for collision with the background after rotation
        if (checkCollision()) {
            // If there is a collision, undo the rotation
            block.rotateBack();
        }

        repaint();
    }
	
    private boolean checkCollision() {
        int[][] shape = block.getBlock();
        for (int row = 0; row < block.getHeight(); row++) {
            for (int col = 0; col < block.getWidth(); col++) {
                if (shape[row][col] == 1) {
                    int x = block.getX() + col;
                    int y = block.getY() + row;
                    if (x < 0 || x >= this.col || y < 0 || y >= rows || (background[y][x] != null)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
  


	
	@Override
	protected void paintComponent(Graphics g) { 
	
		super.paintComponent(g);
		g.setColor(Color.BLACK); // Set color for drawing the grid lines

		
		g.drawRect(0, 0, col * UNIT, rows * UNIT); //big board
		
		
		for (int y = 0 ; y < rows + 1; y ++ ) {
			g.drawLine(0, y * UNIT, col * UNIT, y * UNIT);
		}
		
		for (int x = 0; x < col +1; x ++) {
			g.drawLine(x * UNIT, 0, x * UNIT, rows * UNIT);
		}
		drawBackground(g) ; 
		drawBlock(g) ; 
		  drawScore(g) ;//draw score
		  drawNextBlock(g); 
		  drawProjection(g) ; 
		  
		  if (isPaused) {
		        g.setColor(Color.RED); //draw paused
		        g.setFont(new Font("Arial", Font.BOLD, 50));
		        g.drawString("Paused", 50, 250);
		    }
		 if (isGameOver) {
	            g.setColor(Color.black );//draw game over 
	            g.setFont(new Font("Arial", Font.BOLD, 50));
	            g.drawString("Game Over!", 30, 250);
	            g.setFont(new Font("Arial", Font.PLAIN, 40));
	            g.drawString("Score: " + score , 80, 290);
	          
	        }
	} 
	
	private void drawScore(Graphics g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Score: " + score, 310, 30);
    }
	
	private void restartGame() {
        isGameOver = false;
        background = new Color[rows][col]; //reset background 
        spawnNextBlock() ; 
        spawnBlock();
        score = 0; //reset score
        timer.start(); //start timer
        
    }
	
	private void togglePause() {
	    isPaused = !isPaused;
	    if (isPaused) {
	        timer.stop();
	    } else {
	        timer.start();
	    }
	    repaint();
	}

public void keyPressed(KeyEvent e) {
	// System.out.println("Key Pressed: " + e.getKeyCode()); // Debug statement
	 if (isGameOver) {
         if (e.getKeyCode() == KeyEvent.VK_R) {
             restartGame();
         }
         return;
     }
	 
	 if (e.getKeyCode() == KeyEvent.VK_P) {
	        togglePause();
	    }
    switch (e.getKeyCode()) {
        case KeyEvent.VK_LEFT:
            blockLeft();
            break;
        case KeyEvent.VK_RIGHT:
        	blockRight() ; 
            break;
        case KeyEvent.VK_DOWN:
            blockDown();
            break;
        case KeyEvent.VK_UP: // Added case for 'R' key to rotate
            rotateBlock();
            break;
        case KeyEvent.VK_SPACE : 
        	blockDrop() ; 
        	break;
    }
   // repaint(); // Repaint the board to reflect the new game state
}
@Override
public void keyTyped(KeyEvent e) {
	// TODO Auto-generated method stub
//	if (e.getKeyChar() == 's') {
//		blockDown(); 
//	}
}

@Override
public void keyReleased(KeyEvent e) {
	// TODO Auto-generated method stub
	
}


	
}
