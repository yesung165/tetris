package tetris;

import java.awt.Color;

public class LShape extends Shapes {
	

	private static int [][] block = {
			{0,0,1},
			{1,1,1} 
	} ;
	public LShape(Color color) {
		
		super(block, color);
		
	}

}
