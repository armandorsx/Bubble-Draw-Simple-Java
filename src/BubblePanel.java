import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;

public class BubblePanel extends JPanel {

	private ArrayList<Bubble> bubbleList;
	private int size = 60;
	private Timer timer;
	private final int DELAY = 15; // ms of delay for 30 fps

	public BubblePanel() {

		bubbleList = new ArrayList<Bubble>();

		addMouseListener(new BubbleListener());
		addMouseMotionListener(new BubbleListener());
		addMouseWheelListener(new BubbleListener());

		timer = new Timer(DELAY, new BubbleListener());

		setBackground(Color.BLACK);

		setPreferredSize(new Dimension(2000, 1600));

		timer.start();

	}

	public void paintComponent(Graphics page) {
		super.paintComponent(page);

		// draw all the Bubbles from bubbleList
		for (Bubble bubble : bubbleList) {
			page.setColor(bubble.color);
			page.fillOval(bubble.x - bubble.size / 2, bubble.y - bubble.size / 2, bubble.size, bubble.size);
		}

		// count how many bubbles on screen

		page.setColor(Color.GREEN);
		page.drawString("Count: " + bubbleList.size(), 5, 15);

	}

	private class BubbleListener implements MouseListener, MouseMotionListener, MouseWheelListener, ActionListener {

		@Override
		public void mouseClicked(MouseEvent e) {

		}

		@Override
		public void mouseEntered(MouseEvent e) {

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent e) {
			timer.stop(); // stop animation

			// add to the bubbleList my mouse location
			bubbleList.add(new Bubble(e.getX(), e.getY(), size));

			repaint();
		}

		@Override
		public void mouseReleased(MouseEvent e) {

			timer.start();
		}

		@Override
		public void mouseDragged(MouseEvent e) {

			// add to the bubbleList my mouse location
			bubbleList.add(new Bubble(e.getX(), e.getY(), size));

			repaint();

		}

		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {

			// change size when the wheel is moved
			size -= e.getWheelRotation();
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// update the location of each bubble for the animation

			for (Bubble bubble : bubbleList)
				bubble.update();

			// repaint screen
			repaint();

		}

	}

	private class Bubble {
		/** A bubble needs an x,y location and a size */

		public int x;
		public int y;
		public int size;
		public Color color;
		public int xspeed;
		public int yspeed;
		private final int MAX_SPEED = 7;

		public Bubble(int newX, int newY, int newSize) {
			x = newX;
			y = newY;
			size = newSize;
			color = new Color((float) Math.random(), (float) Math.random(), (float) Math.random());
			xspeed = (int) (Math.random() * MAX_SPEED * 2 - MAX_SPEED);
			yspeed = (int) (Math.random() * MAX_SPEED * 2 - MAX_SPEED);
			if (xspeed == 0 && yspeed == 0) {
				xspeed = 3;
				yspeed = 3;
			}

		}

		public void update() {
			x += xspeed;
			y += yspeed;

			// collision detection with the edges of the panel

			if (x < 0 || x > getWidth())
				xspeed = -1 * xspeed;

			if (y < 0 || y > getHeight())
				yspeed = -yspeed;

		}
	}

}
