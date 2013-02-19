package kimp;

import java.awt.Point;
import java.util.ArrayList;

/**
 * @author joosep.pollumae
 *
 */
class ShapeList {
	
	/**
	 * List which contains list of every state of shapes.
	 */
	ArrayList<ArrayList<Shape>> shapes = new ArrayList<ArrayList<Shape>>();
	
	/**
	 * index for displaying proper state of shapes.
	 */
	int stateIndex = 0;
	
	/**
	 * Minimal distance from shape to get selected.
	 */
	public static final int MIN_DIST = 10;
	
	/**
	 * Constructor for ShapeList.
	 */
	public ShapeList() {
		shapes.add(new ArrayList<Shape>());
	}
	
	/**
	 * @return List of current state of objects.
	 * 
	 */
	public ArrayList<Shape> getShapes() {
		return shapes.get(stateIndex);
	}
	/**
	 * @param s Shape
	 * Adds new shape to shapes list and creates new state.
	 */
	public void addShape(Shape s) {
		newState();
		shapes.get(stateIndex).add(s);
	}
	
	/**
	 * Removes selected shape from shapes state and creates new state.
	 */
	public void removeShape() {
		newState();
		shapes.get(stateIndex).remove(getSelectedShape());
		
	}
	
	/**
	 * Creates new state of shape objects.
	 */
	public void newState() {
		int shapesSize = shapes.size();
		for (int i = shapesSize - 1; i > stateIndex; i--) {
			shapes.remove(i);
		}
		// copy elements
		shapes.add(new ArrayList<Shape>());
		ArrayList<Shape> newState = shapes.get(stateIndex + 1);
		for (Shape s : getShapes()) {
			newState.add(s.copy());
		}
		this.stateIndex++;
	}
	
	/**
	 * @return last shape which was added to shapes.
	 */
	public Shape getLastShape() {
		int size = shapes.get(stateIndex).size();
		if (size > 0) {
			return shapes.get(stateIndex).get(size - 1);
		}
		return null;
	}
	
	/**
	 * @return selected shape.
	 */
	public Shape getSelectedShape() {
		for (Shape s : getShapes()) {
			if (s.selected) {
				return s;
			}
		}
		return null;
	}
	
	/**
	 * @return boolean undo
	 * If false then there is nothing to undo.
	 */
	public boolean undo() {
		if (stateIndex > 0) {
			stateIndex--;
			return true;
		}
		return false;
	}
	
	/**
	 * @return boolean redo.
	 * If false then there is nothing to redo.
	 */
	public boolean redo() {
		if (stateIndex < shapes.size() - 1) {
			stateIndex++;
			return true;
		}
		return false;
	}
	
	/**
	 * @param p Point
	 * Selects nearest shape.
	 */
	public void select(Point p) {
		unselectAll();
		int minDist = MIN_DIST;
		Shape closeShape = null;
		for (Shape s : getShapes()) {
			int dist = s.getDistance(p);
			if (dist < minDist) {
				minDist = dist;
				closeShape = s;
			}
		}
		if (closeShape != null) {
			closeShape.selected = true;
		}
	}
	
	/**
	 * Deselects all current shapes.
	 */
	public void unselectAll() {
		for (Shape s : getShapes()) {
			s.selected = false;
		}
	}
}
