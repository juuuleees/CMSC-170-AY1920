// TODO: specify java imports
import java.util.ArrayList;
import java.lang.StringBuilder;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.File;
import javax.swing.*;

public class Game extends JFrame {
	
	private JFrame frame;
	private ArrayList<Tile> tiles = new ArrayList<Tile>();
	private ArrayList<String> all_moves = new ArrayList<String>();
	private File layout_file = new File("puzzle.in");
	private String[][] puzzle_layout;
	private int[][] current_layout;
	private boolean win_status;

	// getters
	public ArrayList<Tile> get_tiles() { return this.tiles; }
	public ArrayList<String> get_all_moves() { return this.all_moves; }
	public File get_layout_file() { return this.layout_file; }
	public String[][] get_puzzle_layout() { return this.puzzle_layout; }
	public int[][] get_current_layout() { return this.current_layout; }
	public boolean get_win_status() { return this.win_status; }

	// setters
	public void set_tiles(ArrayList<Tile> ts) { this.tiles = ts; }
	public void set_all_moves(ArrayList<String> ms) { this.all_moves = ms; }
	public void set_layout_file(File pl) { this.layout_file = pl; }
	public void set_puzzle_layout(String[][] pl) { this.puzzle_layout = pl; }
	public void set_current_layout(int[][] cl) { this.current_layout = cl; }
	public void set_win_state(boolean w) { this.win_status = w; }

	// constructors
	public Game() {}

	// methods 

	/* TODO: determine if puzzle is solvable
			 graphics
			 popup box saying that the puzzle isn't solvable
	*/
	/*
		Build the game layout based on input from puzzle.in.
	*/
	public void read_game_layout() {

		try {

			BufferedReader reader = new BufferedReader(new FileReader(layout_file));
			String line;
			int line_counter = 0;

			if (layout_file.exists()) {
				while ((line = reader.readLine()) != null) {
					
					String[] values = line.split(" ");
					// TODO: how to get deepy copy of values into 2d array. I'm probably overthinking it. Also gutom na ako.
					for (int i = 0; i < values.length; i++) {
						puzzle_layout[line_counter][i] = values[i];
						// System.out.println(values[i]);
						// String tile_value = values[i];
						// System.out.println(tile_value);
					}

					line_counter++;
				}
			}

			// System.out.println(puzzle_layout);

		} catch (Exception e) {
			System.out.println(e);
		}

	}

	/*
		Run the game.	
	*/
	public void run() {

		// setSize(400,400);
		// setLayout(null);
		// setVisible(true);
		read_game_layout();

	}

}

/*

	References:
		http://zetcode.com/tutorials/javagamestutorial/movingsprites/
	
*/