package tetris;

import java.awt.Color;

public class Shapes {
	private int[][] block;
	private int x , y; 
	private Color color; 
	private int[][] previousBlock;
	
	public Shapes (int [][] block, Color color) {
		this.block = block ; 
		x = 3; 
		y = 0; 
		this.color = color; 
		this.previousBlock = null; 
		
	}
	
	public int[][] getBlock () {
		return block ; 
	}
	public int getHeight () {
		return block.length ; 
	}
	
	public int getWidth() {
		return block[0].length ; 
	}
	public int getX () {
		return x; 
	}
	
	public int getY () {
		return y; 
	}
	 public void setX(int x) {
	        this.x = x;
	    }
	 public void setY(int y) {
	        this.y = y;
	    }
	
	public int moveDown() {
		return y++; 
	}
	
	public int moveRight () {
		
		return x ++ ; 
	}
	
	public int moveLeft () {
		return x -- ; 
	}
	
	public Color getColor() {
		return color; 
	}
	
	 public void rotate() {
	        previousBlock = copyShape(block);
	        block = rotateShape(block);
	    }

	  public void rotateBack() {
	        if (previousBlock != null) {
	            block = previousBlock;
	        }
	    }

	    public int[][] rotateShape(int[][] shape) {
	        int width = shape.length;
	        int height = shape[0].length;
	        int[][] rotated = new int[height][width];
	        for (int row = 0; row < height; row++) {
	            for (int col = 0; col < width; col++) {
	                rotated[row][col] = shape[width - col - 1][row];
	            }
	        }
	        return rotated;
	    }

	    private int[][] copyShape(int[][] shape) {
	        int[][] copy = new int[shape.length][];
	        for (int i = 0; i < shape.length; i++) {
	            copy[i] = shape[i].clone();
	        }
	        return copy;
	    }

  
}
