/**
 * 
 */
package cqu.shy.game;

import java.awt.Graphics;

/**
 * @author SHY
 *
 */
abstract class FlyingObject {
	protected static int Canvas_width=500;
	protected static int Canvas_height=700;
	
	public void setCanvasWidthAndHeight(int w,int h){
		Canvas_width = w;
		Canvas_height = h;
	}
	abstract public void init();
	abstract public void move();
	abstract public void draw(Graphics g);
}
