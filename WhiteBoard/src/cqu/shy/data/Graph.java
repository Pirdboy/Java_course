package cqu.shy.data;

import java.awt.Color;
import java.io.Serializable;

import cqu.shy.paintmode.PaintMode;

public class Graph implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String userName;
	public int startX,startY,endX,endY;
	public PaintMode paintmode;
	public float penWidth;
	public Color pencolor;
	public Graph(String name,int startX, int startY, int endX, int endY,
			PaintMode paintmode, float penWidth,Color pencolor) {
		super();
		userName = name;
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
		this.paintmode = paintmode;
		this.penWidth = penWidth;
		this.pencolor = pencolor;
	}
	
}
