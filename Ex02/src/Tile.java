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

public class Tile extends JPanel implements ActionListener {
	
	private int tile_value;
	private ArrayList<String> possible_moves = new ArrayList<String>();
	private ArrayList<Integer> adjacent_tiles = new ArrayList<Integer>();
	private int[][] curr_board;
	private JButton tile_button;
	private int switch_value;
	private boolean moveable;
	private boolean is_final_pos;

	// getters
	public int get_tile_value() { return this.tile_value; }
	public ArrayList<String> get_possible_moves() { return this.possible_moves; }
	public ArrayList<Integer> get_adjacent_tiles() { return this.adjacent_tiles; }
	public int[][] get_curr_board() { return this.curr_board; }
	public boolean get_moveable() { return this.moveable; }
	public boolean get_is_final_pos() { return this.is_final_pos; }

	// setters
	public void set_tile_value(int tv) { this.tile_value = tv; }
	public void set_possible_moves(ArrayList<String> moveset) { this.possible_moves = moveset; }
	public void set_adjacent_tiles(ArrayList<Integer> adj) { this.adjacent_tiles = adj; }
	public void set_curr_board(int[][] cb) { this.curr_board = cb; }
	public void set_moveable(boolean m) { this.moveable = m; }
	public void set_is_final_pos(boolean f) { this.is_final_pos = f; }

	// constructors
	public Tile(int value, int[][] cb) {
		this.tile_value = value;
		this.curr_board = cb;
		this.tile_button = new JButton(Integer.toString(value));
		if (value == 0) {
			tile_button.setVisible(false);
		}
		tile_button.setPreferredSize(new Dimension(100,100));
		tile_button.addActionListener(this);
		add(tile_button);
	}

	// methods
	/* TODO: onClick slide tile into empty space
			 update possible moves 						<-------- move to Game.java
	*/
	public void actionPerformed(ActionEvent e) {

		if (this.get_adjacent_tiles().contains(0)) {
			// System.out.println("halp");		
			this.switch_value = this.get_tile_value();
		}

	}

}