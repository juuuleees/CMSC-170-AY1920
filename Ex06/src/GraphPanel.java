import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class GraphPanel extends JPanel {
	
	private int width = 800;
	private int length = 400;
	private Color point_color = new Color(44, 102, 230, 180);
	private int point_width = 4;
	private int number_y_divisions = 10;
	private ArrayList<Point> points;

	public GraphPanel() {}
	public GraphPanel(ArrayList<Point> p) {	
		this.points = points;
	}

	// TODO:	Draw points dynamically and with different colors per point.

	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// draw the canvas and borders
		g2.setColor(Color.BLACK);
		g2.drawRect(12,12,450,390);
		g2.setColor(Color.WHITE);
		g2.fillRect(13,13,449,389);

		// draw your basic cartesian plane
		// start with the axes
		g2.setColor(Color.BLACK);
		g2.drawLine(245,12,245,402);		// x
		g2.drawLine(12,205,460,205);		// y

		// then draw the ticks for x-axis then y-axis
		for (int i = 0; i < 11;i++) {
			// x ticks
			g2.drawLine(265+(i*20),200,265+(i*20),210);
			g2.drawLine(225-(i*20),200,225-(i*20),210);	
			if (((i%2) == 0) && (i != 0) && (i != 10)) {
				g2.drawString(Integer.toString(i),226,210-(i*20));
				g2.drawString(Integer.toString(-i),226,210+(i*20));
			}
			// y ticks
			g2.drawLine(240,225+(i*20),250,225+(i*20));		// y
			g2.drawLine(240,225-(i*20),250,225-(i*20));	
			if (((i%2) == 0) && (i != 0)) {
				g2.drawString(Integer.toString(-i),238-(i*20),222);
				g2.drawString(Integer.toString(i),242+(i*20),222);
			}	
		}

	}

}

/*
	References: 
		https://gist.github.com/roooodcastro/6325153
		https://www3.ntu.edu.sg/home/ehchua/programming/java/J4b_CustomGraphics.html

*/