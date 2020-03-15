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
	public GraphPanel(ArrayList<Point> p) {	this.points = points; }

	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2.setColor(Color.WHITE);
		// g2.fillRect()

	}

}

// Reference: https://gist.github.com/roooodcastro/6325153