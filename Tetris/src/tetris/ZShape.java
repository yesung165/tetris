package tetris;

import java.awt.Color;

public class ZShape extends Shapes{

	private static int [][] block = {
			{1,1,0},
			{0,1,1}
	} ;
	public ZShape(Color color) {
		
		super(block, color);
		
	}

}
