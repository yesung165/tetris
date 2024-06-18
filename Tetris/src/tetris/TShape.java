package tetris;

import java.awt.Color;

public class TShape extends Shapes{

	private static int [][] block = {
			{0,1,0},
			{1,1,1}
	} ;
	public TShape(Color color) {
		
		super(block, color);
		
	}

}
