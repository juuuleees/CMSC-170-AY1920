import java.util.ArrayList;
import java.util.Scanner;
import java.lang.StringBuilder;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.File;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.Color;
// import java.awt.event.ActionListener;
// import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Game extends JFrame {
	
	private final int WINDOW_SIZE = 450;
	private ArrayList<Tile> tiles = new ArrayList<Tile>();
	private ArrayList<String> all_moves = new ArrayList<String>();
	private ArrayList<State> frontier = new ArrayList<State>();
	private File layout_file = new File("src/puzzle2.in");
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
	public void set_win_status(boolean win) { this.win_status = win; }

	// constructors
	public Game() {}

	// methods 

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

	/*
		Finds tiles adjacent to the given tile.
	*/
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

	/*
		Makes the win condition for later comparison.
	*/
	public void make_win_condition() {
		int tile_count = this.get_matrix_dimension() * this.get_matrix_dimension();
		int[][] final_board = new int[this.get_matrix_dimension()][this.get_matrix_dimension()];
		int counter = 1;

		for (int i = 0; i < this.get_matrix_dimension(); i++) {
			for (int j = 0; j < this.get_matrix_dimension(); j++) {
				if (counter == (this.get_matrix_dimension() * this.get_matrix_dimension())) {
					counter = 0;
					final_board[i][j] = counter;
					break;
				} else {
					final_board[i][j] = counter;
				}
				counter++; 
			}
		}

		this.set_win_condition(final_board); 

	}

	/*
		Checks to see if the current board is the winning board.
	*/
	public void detect_win_state(int[][] board) {

		boolean u_won = true;

		for (int i = 0; i < this.get_matrix_dimension(); i++) {
			for (int j = 0; j < this.get_matrix_dimension(); j++) {
				if (board[i][j] != this.get_win_condition()[i][j]) {
					u_won = false;
					break;
				}
			}
		}

		this.set_win_status(u_won);

	}

	// Functions for BFS

	/*
		Looks for the position of the empty space on the board
	*/
	public int find_empty_space(int[][] layout) {
		
		int total_tiles = this.get_matrix_dimension() * this.get_matrix_dimension();
		int free_space_index = 0;

		for (int i = 0; i < this.get_matrix_dimension(); i++) {
			for (int j = 0; j < this.get_matrix_dimension(); j++) {
				if (layout[i][j] == 0) {
					break;
				}
				free_space_index++;
			}
		}

		return free_space_index;

	}

	public void BFSearch(State initial) {

		State current_state = new State();
		int last_index = 0;

		frontier.add(initial);

		// while(!frontier.isEmpty()) {	
		// 	last_index =  frontier.size();
		// 	current_state = frontier.get(last_index);
			
		// }

	}

	/*
		Sets up game graphics.
	*/
	public void graphics_setup() {

		int dimension = this.get_matrix_dimension();
		setSize(new Dimension(WINDOW_SIZE, WINDOW_SIZE));

		JPanel main = new JPanel();
		main.setLayout(new GridLayout(dimension, dimension));
		main.setSize(new Dimension(WINDOW_SIZE, WINDOW_SIZE));
		main.setBackground(Color.WHITE);

		for (int i = 0; i < dimension; i++) {
			for(int j = 0; j < dimension; j++) {
				Tile tile = new Tile(current_layout[i][j], WINDOW_SIZE/this.get_matrix_dimension());
				main.add(tile);
			}
		}

		add(main);

		setTitle("8-Puzzle");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

	}

	/*
		Sets up backend.
	*/
	public void setup_backend() {
		read_game_layout();
		make_win_condition();
	}

	/*
		Run the game.	
	*/
	public void run() {
		setup_backend();
		graphics_setup();
	}

}

/*

	NOTES:
		Solve button will solve based on the current board configuration. So we replace the contents
		of puzzle.in with the contents of the current board.

	References:
		http://zetcode.com/tutorials/javagamestutorial/movingsprites/
	
*/