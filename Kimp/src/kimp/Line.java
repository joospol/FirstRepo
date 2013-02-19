package kimp;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

/**
 * @author joosep.pollumae
 *
 */
public class Line extends Shape {
	
	/**
	 * Holds Line points.
	 */
	private ArrayList<Point> points = new ArrayList<Point>(); 
	/**
	 * Single point in line.
	 */
	private Point point = new Point();
	
	/**
	 * Max dist from point to object.
	 */
	public static final int MAX_DIST = 1000;
	
	/**
	 * Constructor for initializing new Line without arguments.
	 */
	public Line() {
		
	}
	
	/**
	 * Constructor for Line.
	 * @param p point which is added to drawing line.
	 */
	public Line(Point p) {
		points.add(p);
	}
	
	/** (non-Javadoc).
	 * @see ylesanded.kimp.Shape#paint(java.awt.Graphics)
	 * @param g Graphics.
	 */
	public void paint(Graphics g) {
		super.paint(g);
	    for (int i = 0; i < points.size() - 2; i++) {
	        Point p1 = points.get(i);
	        Point p2 = points.get(i + 1);
	        g.drawLine(p1.x, p1.y, p2.x, p2.y);
	    }
	}

	@Override
	public Line copy() {
		Line l = new Line();
		copyHelper(l);
		for (Point p : points) {
			l.point = new Point(p);
			l.points.add(l.point);
		}
		return l;
	}

	@Override
	public void draw(Point p) {
		this.points.add(p);
		
	}

	@Override
	public void move(int dx, int dy) {
		for (Point point : points) {
			point.x += dx;
			point.y += dy;
		}
		
	}

	@Override
	public int getDistance(Point p) {
		int minDist = MAX_DIST;
		for (Point point : points) {
			int actDist = (int) p.distance(point);
			if (minDist > actDist) {
				minDist = actDist;
			}
		}
		return minDist;
	}

}
