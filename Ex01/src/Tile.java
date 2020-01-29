// make it terminal-ready before dealing with graphics
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JButton;

public class Tile extends JPanel {
	
	private int tile_value;
	private ArrayList<String> possible_moves = new ArrayList<String>();
	private JButton tile_button;
	private boolean is_final_pos;

	// getters
	public int get_tile_value() { return this.tile_value; }
	public ArrayList<String> get_possible_moves() { return this.possible_moves; }
	public boolean get_is_final_pos() { return this.is_final_pos; }

	// setters
	public void set_tile_value(int tv) { this.tile_value = tv; }
	public void set_possible_moves(ArrayList<String> moveset) { this.possible_moves = moveset; }
	public void set_is_final_pos(boolean f) { this.is_final_pos = f; }

	// constructors
	public Tile() {}
	public Tile(int value) {
		this.tile_value = value;
		this.tile_button = new JButton(Integer.toString(value));
		tile_button.setPreferredSize(new Dimension(100,100));
		add(tile_button);
	}

	// methods
	/* TODO: onClick slide tile into empty space
			 update possible moves
	*/

	// private void draw_tile(Graphics g) {
		 
	// 	 Graphics2D graphics = (Graphics2D)g;
	// 	 // Rectangle tile_shape = new Rectangle(10,10,100,100);
	// 	 // tile_shape.addActionListener(this);
	// 	 JButton tile = new JButton(Integer.toString(this.tile_value));

	// 	 // graphics.setPaint(new Color(130,130,130));
	// 	 // graphics.drawString(Integer.toString(this.tile_value),55,70);
	// 	 // graphics.drawRect(10,10,100,100);
	// 	 // graphics.fillRect(10,10,100,100);

	// }

	// @Override
	// public void paintComponent(Graphics g) {
	// 	super.paintComponent(g);
	// 	draw_tile(g);
	// }

}