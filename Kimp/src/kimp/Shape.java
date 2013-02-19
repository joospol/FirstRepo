package kimp;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 * @author joosep.pollumae
 * Abstract class for shape implementation protocol. 
 */
/**
 * @author joosep.pollumae
 *
 */
abstract class Shape {
	
	/**
	 * Sets shape color.
	 */
	public Color color = Color.BLUE;
	
	/**
	 * Says if shape is selected or not by user.
	 */
	public boolean selected;
	
	/**
	 * @return copy of shape.
	 * Each shape must implement its own copy method.
	 */
	public abstract Shape copy();
	
	/**
	 * @param s Shape
	 *
	 */
	protected void copyHelper(Shape s) {
		s.selected = selected;
		s.color = new Color(color.getRGB());
	}
	
	/**
	 * @param p point
	 * Finalizes shape object. Must be implemented by each shape.
	 */
	public abstract void draw(Point p);
	
	/**
	 * @param dx distance from last point.x
	 * @param dy distance from last point.y
	 * Moves object on canvas. Must be implemented by each shape.
	 */
	public abstract void move(int dx, int dy);
	
	/**
	 * @param g Graphics
	 */
	public void paint(Graphics g) {
		if (selected) {
			g.setColor(Color.RED);
		} else {
			g.setColor(color);
		}
	}
	
	/**
	 * @param p Point
	 * @return distance from Shape.
	 * Must be implemented by each shape.
	 */
	public abstract int getDistance(Point p);
}
