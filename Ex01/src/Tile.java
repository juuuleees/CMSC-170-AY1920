// make it terminal-ready before dealing with graphics
import java.util.ArrayList;

public class Tile {
	
	private int tile_value;
	private ArrayList<String> possible_moves = new ArrayList<String>();
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
	}

	// methods
	/* TODO: onClick slide tile into empty space
			 update possible moves
	*/

}