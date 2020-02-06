// make it terminal-ready before dealing with graphics
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import java.awt.Dimension;
import java.awt.Canvas;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JButton;

public class Tile extends Canvas implements MouseListener,MouseMotionListener {
	
	private final int FONT_SIZE = 10;
	private ArrayList<String> possible_moves = new ArrayList<String>();
	private ArrayList<Integer> adjacent_tiles = new ArrayList<Integer>();
	private int tile_value;
	private int tile_size;
	private int x_pos;
	private int y_pos; 
	private boolean moveable;
	private boolean is_final_pos;

	// getters
	public int get_tile_value() { return this.tile_value; }
	public int get_tile_size() { return this.tile_size; }
	public ArrayList<String> get_possible_moves() { return this.possible_moves; }
	public ArrayList<Integer> get_adjacent_tiles() { return this.adjacent_tiles; }
	public boolean get_moveable() { return this.moveable; }
	public boolean get_is_final_pos() { return this.is_final_pos; }

	// setters
	public void set_tile_value(int tv) { this.tile_value = tv; }
	public void set_tile_size(int ts) { this.tile_size = ts; }
	public void set_possible_moves(ArrayList<String> moveset) { this.possible_moves = moveset; }
	public void set_adjacent_tiles(ArrayList<Integer> adj) { this.adjacent_tiles = adj; }
	public void set_moveable(boolean m) { this.moveable = m; }
	public void set_is_final_pos(boolean f) { this.is_final_pos = f; }

	// constructors
	// public Tile(int value, int[][] cb) {
	// 	this.tile_value = value;
	// 	this.curr_board = cb;
	// 	this.tile_button = new JButton(Integer.toString(value));
	// 	if (value == 0) {
	// 		tile_button.setVisible(false);
	// 	}
	// 	tile_button.setPreferredSize(new Dimension(100,100));
	// 	tile_button.addActionListener(this);
	// 	add(tile_button);
	// }

	public Tile(int value, int size) {
		this.tile_value = value;
		this.tile_size = size;
		addMouseListener(this);
		addMouseMotionListener(this);
	}

	// methods
	/* TODO: onClick slide tile into empty space
			 update possible moves 						<-------- move to Game.java
	*/
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {
		System.out.println("u relees");
	}
	public void mousePressed(MouseEvent e) {
		System.out.println("u press");
	}
	public void mouseDragged(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		System.out.println("u drag: " + x + "," + y);
	}
	public void mouseMoved(MouseEvent e) {
	}

	public void paint(Graphics g) {
		if (this.get_tile_value() != 0) {
			g.setColor(Color.WHITE);
			g.drawRect(0,0,this.get_tile_size(),this.get_tile_size());
			g.drawRect(1,1,this.get_tile_size()-1,this.get_tile_size()-1);
			g.setColor(Color.YELLOW);
			g.fillRect(2,2,this.get_tile_size()-2,this.get_tile_size()-2);
			g.setColor(Color.BLACK);
			g.setFont(new Font("Helvetica",Font.PLAIN,FONT_SIZE));
			g.drawString(Integer.toString(this.get_tile_value()),this.get_tile_size()/2,this.get_tile_size()/2);
		} else {
			g.setColor(Color.WHITE);
			g.drawRect(0,0,this.get_tile_size(),this.get_tile_size());
			g.drawRect(1,1,this.get_tile_size()-1,this.get_tile_size()-1);
			g.setColor(Color.WHITE);
			g.fillRect(2,2,this.get_tile_size()-2,this.get_tile_size()-2);
		}
	}

}