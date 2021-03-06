// TODO: specify java imports
import java.util.ArrayList;
import java.util.Scanner;
import java.lang.StringBuilder;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.File;
import java.awt.GridLayout;
// import java.awt.event.ActionListener;
// import java.awt.event.ActionEvent;
import javax.swing.JFrame;

public class Game extends JFrame {
	
	private JFrame frame;
	private ArrayList<Tile> tiles = new ArrayList<Tile>();
	private ArrayList<String> all_moves = new ArrayList<String>();
	private File layout_file = new File("src/puzzle.in");
	private String[][] puzzle_layout;
	private int[][] current_layout;
	private int[][] win_condition;
	private int matrix_dimension = 0;
	private boolean win_status;

	// getters
	public ArrayList<Tile> get_tiles() { return this.tiles; }
	public ArrayList<String> get_all_moves() { return this.all_moves; }
	public File get_layout_file() { return this.layout_file; }
	public String[][] get_puzzle_layout() { return this.puzzle_layout; }
	public int[][] get_current_layout() { return this.current_layout; }
	public int[][] get_win_condition() { return this.win_condition; }
	public int get_matrix_dimension() { return this.matrix_dimension; }
	public boolean get_win_status() { return this.win_status; }

	// setters
	public void set_tiles(ArrayList<Tile> ts) { this.tiles = ts; }
	public void set_all_moves(ArrayList<String> ms) { this.all_moves = ms; }
	public void set_layout_file(File pl) { this.layout_file = pl; }
	public void set_puzzle_layout(String[][] pl) { this.puzzle_layout = pl; }
	public void set_current_layout(int[][] cl) { this.current_layout = cl; }
	public void set_win_condition(int[][] w) { this.win_condition = w; }
	public void set_matrix_dimension(int md) { this.matrix_dimension = md; }
	public void set_win_state(boolean w) { this.win_status = w; }

	// constructors
	public Game() {}

	// methods 

	/* TODO: determine if puzzle is solvable				 <------ ON HOLD
			 popup box saying that the puzzle isn't solvable <------ ON HOLD
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
				
				// really roundabout deep copying 
				ArrayList<String> input_lines = new ArrayList<String>();

				while ((line = reader.readLine()) != null) {
					// System.out.println(line);
					input_lines.add(line);
					line_counter++;
				}

				int[][] puzzle_matrix = new int[line_counter][line_counter];

				for (int i = 0; i < input_lines.size(); i++) {
					String[] values = input_lines.get(i).split(" ");
					for (int j = 0; j < line_counter; j++) {
						puzzle_matrix[i][j] = Integer.parseInt(values[j]);
					}
				}

				set_matrix_dimension(line_counter);
				set_current_layout(puzzle_matrix);

			} else {
				System.out.println("File not found.");
			}

		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public void find_adjacent_tiles(Tile t) {

		ArrayList<Integer> adj = new ArrayList<Integer>();
		int[][] curr = this.get_current_layout();
		int stop = this.get_matrix_dimension();
		int start = 0;

		for (int row = 0; row < stop; row++) {
			for (int col = 0; col < stop; col++) {
				if (curr[row][col] == t.get_tile_value()) {
					if (row == start) {							// top row
						if (col == start) {						// UL corner
							adj.add(curr[row+1][col]);
							adj.add(curr[row][col+1]);
						} else if (col == (stop - 1)) {			// UR corner
							adj.add(curr[row+1][col]);
							adj.add(curr[row][col-1]);
						} else {								// top row
							adj.add(curr[row+1][col]);
							adj.add(curr[row][col-1]);
							adj.add(curr[row][col+1]);
						}
					} else if (row == (stop - 1)) {				// bottom row
						if (col == start) {						// LL corner
							adj.add(curr[row-1][col]);
							adj.add(curr[row][col+1]);
						} else if (col == (stop - 1)) {			// LR corner
							adj.add(curr[row-1][col]);
							adj.add(curr[row][col-1]);
						} else {								// bottom row
							adj.add(curr[row-1][col]);
							adj.add(curr[row][col-1]);
							adj.add(curr[row][col+1]);
						}
					} else if (col == start) {					// left edge
						adj.add(curr[row-1][col]);
						adj.add(curr[row+1][col]);
						adj.add(curr[row][col+1]);
					} else if (col == (stop - 1)) {				// right edge
						adj.add(curr[row-1][col]);
						adj.add(curr[row+1][col]);
						adj.add(curr[row][col-1]);
					} else {
						adj.add(curr[row-1][col]);
						adj.add(curr[row+1][col]);
						adj.add(curr[row][col-1]);
						adj.add(curr[row][col+1]);
					}
				}
			}	
		}

		t.set_adjacent_tiles(adj);

	}

	public void graphics_setup() {

		setLayout(new GridLayout(this.get_matrix_dimension(), this.get_matrix_dimension()));

		for (int i = 0; i < this.get_matrix_dimension(); i++) {
			for (int j = 0; j < this.get_matrix_dimension(); j++) {
				Tile tile = new Tile(current_layout[i][j], this.get_current_layout());
				find_adjacent_tiles(tile);
				add(tile);
			}
		}

		setTitle("8-Puzzle");
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

	}

	/*
		Run the game.	
	*/
	public void run() {

		read_game_layout();
		graphics_setup();

	}

}

/*

	References:
		http://zetcode.com/tutorials/javagamestutorial/movingsprites/
	
*/