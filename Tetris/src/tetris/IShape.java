package tetris;

import java.awt.Color;

public class IShape extends Shapes{

	private static int [][] block = {
			{1,1,1,1}
			 
	} ;
	public IShape(Color color) {
		
		super(block, color);
		
	}
	
//	public void rotateShape() {
//		super.rotateShape(block) ;
//		
//		
//		if (getHeight() == 1) { // Horizontal to Vertical
//			this.setX(this.getX() + 1);
//			this.setY(this.getY() -1 );
//		} else {
//			this.setX(this.getX() -1 );
//			this.setY(this.getY() + 1);
//		}
//           
//	}
	
	public int[][] rotateShape(int[][] shape) {
        int width = shape.length;
        int height = shape[0].length;
        int[][] rotated = new int[height][width];
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                rotated[row][col] = shape[width - col - 1][row];
            }
        }
        
		if (getHeight() == 1) { // Horizontal to Vertical
			this.setX(this.getX() + 1);
			this.setY(this.getY() -1 );
		} else {
			this.setX(this.getX() -1 );
			this.setY(this.getY() + 1);
		}
        return rotated;
    }

  

}
