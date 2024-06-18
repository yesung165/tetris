package tetris;

import java.awt.Color;

public class SShape extends Shapes{

	private static int [][] block = {
			{0,1,1},
			{1,1,0}
	} ;
	public SShape(Color color) {
		
		super(block, color);
		
	}

}
