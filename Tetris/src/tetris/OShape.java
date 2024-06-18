
package tetris;

import java.awt.Color;

public class OShape extends Shapes{

	private static int [][] block = {
			{1,1},
			{1,1}
	} ;
	
	public OShape(Color color) {
		
		super(block, color);
		
	}

}
