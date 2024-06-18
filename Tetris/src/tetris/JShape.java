package tetris;

import java.awt.Color;

public class JShape extends Shapes {
	

		private static int [][] block = {
				{1,0,0},
				{1,1,1} 
		} ;
		public JShape(Color color) {
			
			super(block, color);
			
		}

	}


