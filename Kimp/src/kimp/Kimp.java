package kimp;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;


import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 * @author joosep.pollumae
 * UserInterface class for program.
 */
public class Kimp extends JFrame {
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * List of shapes. Holds different statuses of shape arrays.
	 */
	public ShapeList shapeList = new ShapeList();
	/**
	 * Boolean for detecting "select mode" in runtime.
	 */
	public boolean selectMode = false;
	/**
	 * Used for calculating distance for moving shapes.
	 */
	public Point lastPoint = null;
	/**
	 * Default width for program window.
	 */
	public static final int DEFAULT_WIDTH = 800;
	/**
	 * Default height for program window.
	 */
	public static final int DEFAULT_HEIGHT = 600;
	/**
	 * @param args input arguments.
	 * Main method for program.
	 */
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		Kimp k = new Kimp();
	}
	
	/**
	 * Place where all UI magic happens.
	 */
	public Kimp() {
		this.setTitle("Kimp");
		this.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {

				super.mousePressed(e);
				
				if (selectMode && SwingUtilities.isLeftMouseButton(e)) {
					shapeList.newState();
					lastPoint = e.getPoint();
				} else if (selectMode && SwingUtilities.isRightMouseButton(e)) {
					shapeList.removeShape();
					repaint();
				} else {
					Line l = new Line(e.getPoint());
					shapeList.addShape(l);
					repaint();
				}
			}
		});
		
		this.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				super.mouseDragged(e);
				
				if (selectMode) {
					Shape s = shapeList.getSelectedShape();
					if (s != null) {
						s.move(e.getPoint().x - lastPoint.x,
								e.getPoint().y - lastPoint.y);
						repaint();
					}
					lastPoint = e.getPoint();
				} else {
					Shape s = shapeList.getLastShape();
					if (s != null) {
						s.draw(e.getPoint());
						repaint();
					}
				}
			}
			
			@Override
			public void mouseMoved(MouseEvent e) {
				super.mouseMoved(e);
				
				if (selectMode) {
					shapeList.select(e.getPoint());
					repaint();
				}
			}
		});
		
		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				super.keyPressed(e);
				
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					boolean undone = shapeList.undo();
					if (!undone) {
						int result = JOptionPane.showConfirmDialog(
								null, "Oled kindel?", "Exit?",
								JOptionPane.YES_NO_OPTION);
						if (result == JOptionPane.YES_OPTION) {
							System.exit(0);
						}
					}
					repaint();
				}
				
				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					shapeList.redo();
					repaint();
				}
				
				if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
					selectMode = true;
				}
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				super.keyReleased(e);
				if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
					selectMode = false;
					shapeList.unselectAll();
					repaint();
				}
			}
		});
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		for (Shape s : shapeList.getShapes()) {
			s.paint(g);
		}
	}

}