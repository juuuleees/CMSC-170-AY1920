import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
import java.lang.StringBuilder;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.File;
import java.lang.Math;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.BoxLayout;

public class Game extends JFrame {
	
	private final int WINDOW_SIZE = 450;
	private ArrayList<Tile> tiles = new ArrayList<Tile>();
	private ArrayList<String> all_moves = new ArrayList<String>();
	private ArrayList<State> frontier = new ArrayList<State>();
	private File layout_file = new File("src/test1.in");
	private String[][] puzzle_layout;
	private int[][] current_layout;
	private int[][] win_condition;
	private int matrix_dimension = 0;
	private boolean solvable;
	private boolean win_status;

	// getters
	public ArrayList<Tile> get_tiles() { return this.tiles; }
	public ArrayList<String> get_all_moves() { return this.all_moves; }
	public File get_layout_file() { return this.layout_file; }
	public String[][] get_puzzle_layout() { return this.puzzle_layout; }
	public int[][] get_current_layout() { return this.current_layout; }
	public int[][] get_win_condition() { return this.win_condition; }
	public int get_matrix_dimension() { return this.matrix_dimension; }
	public boolean get_solvable() { return this.solvable; }
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
	public void set_solvable(boolean sol) { this.solvable = sol; }
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
		Check if the puzzle is solvable using inversions.
	*/
	public void check_solvability(int[][] puzzle) {

		ArrayList<Integer> all_inversions = new ArrayList<Integer>();
		int matrix_size = this.get_matrix_dimension();
		int accounted = 0;
		int inv_count = 0;
		int total_inv = 0;

		for (int i = 0; i < matrix_size; i++) {
			for (int j = 0; j < matrix_size; j++) {
				all_inversions.add(puzzle[i][j]);
			}
		}

		for (Integer value : all_inversions) {
			
			for (int k = accounted; k < all_inversions.size(); k++) {
				if ((value > all_inversions.get(k)) && (value != 0)
					&& (all_inversions.get(k) != 0)) {
					inv_count++;
				}
			}

			total_inv += inv_count;
			accounted++;
			inv_count = 0;

		}
		
		if (total_inv%2 == 0) {
			this.set_solvable(true);
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

	// Functions for BFS and DFS

	/*
		Looks for the position of the empty space on the board
	*/
	public Point find_empty_space(int[][] layout) {
		
		int total_tiles = this.get_matrix_dimension() * this.get_matrix_dimension();
		int x = 0;
		int y = 0;

		for (int i = 0; i < this.get_matrix_dimension(); i++) {
			for (int j = 0; j < this.get_matrix_dimension(); j++) {
				if (layout[i][j] == 0) {
					x = i;
					y = j;
					break;
				}
			}
		}

		Point location = new Point(x,y);

		return location;

	}

	/*
		Swaps tiles for the find_results() function.
	*/
	public int[][] swapper(int[][] cb, Point loc, String m) {
		
		int[][] new_board = new int[this.get_matrix_dimension()][this.get_matrix_dimension()];
		int holding_cell = 0;
		int row = (int)loc.getX();
		int col = (int)loc.getY();

		for (int i = 0; i < this.get_matrix_dimension(); i++) {
			for (int j = 0; j < this.get_matrix_dimension(); j++) {
				new_board[i][j] = cb[i][j];
			}
		}

		if(m.equals("U")) {
			// System.out.println("up");
			new_board[row][col] = new_board[row-1][col];	// 0
			new_board[row-1][col] = 0;
		} else if (m.equals("R")) {
			// System.out.println("right");
			new_board[row][col] = new_board[row][col+1];	// 0
			new_board[row][col+1] = 0;
		} else if (m.equals("D")) {
			// System.out.println("down");
			new_board[row][col] = new_board[row+1][col];	// 0
			new_board[row+1][col] = 0;
		} else if (m.equals("L")) {
			// System.out.println("left");
			new_board[row][col] = new_board[row][col-1];	// 0
			new_board[row][col-1] = 0;
		}

		return new_board;

	}

	/*
		Checks to see if the current board is the winning board.
		(GoalTest())
	*/
	public boolean detect_win_state(int[][] board) {

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
		return u_won;

	}

	/*
		Returns the resulting state given a move.
		(Results(s,a))
	*/
	public State find_results(State curr, String m) {

		int[][] cb = curr.get_current_board();
		Point empty_space = curr.get_empty_space_loc();

		int[][] new_board = swapper(cb, empty_space, m);
		Point updated_es_loc = find_empty_space(new_board);

		State resulting_state = new State(new_board, updated_es_loc, curr, m);

		return resulting_state;

	}

	/*
		Returns the final state's path cost.
		Also doubles as a function to get g forthe A* algorithm.
	*/
	public int show_path_cost(State s) {
		return s.get_path_cost();
	}

	/*
		Compares two given states's board layouts and returns TRUE if they match.
	*/
	public boolean compare_boards(State state1, State state2) {
		
		int[][] board1 = state1.get_current_board();
		int[][] board2 = state2.get_current_board();
		// I admit I'm being a bit lax here. If the two states had different
		// sizes I'd be screwed.
		int board_dimension = state1.get_matrix_size();	
		int tile_count = board_dimension * board_dimension;
		int matching_tiles = 0;
		boolean boards_match = true;

		for (int i = 0; i < board_dimension; i++) {
			for (int j = 0; j < board_dimension; j++) {
				if (board1[i][j] == board2[i][j]) {
					matching_tiles++;
				}
			}
		}

		if (matching_tiles != tile_count) {
			boards_match = false;
		}

		return boards_match;

	}

	// Basic functions (in pseudocode) for A* algorithm since apparently Windows can't print a string
	// while while running the GUI. Why is Windows like this. Now I have to code blind.

	public int compute_h(State curr, int[][] win_cond) {

		int[][] current_board = curr.get_current_board();
		int matrix_size = this.get_matrix_dimension();
		int temp_h = 0;

		for (int i = 0; i < matrix_size; i++)  {
			for (int j = 0; j < matrix_size; j++) {
				if (current_board[i][j] == 0) { 
					System.out.println("skipped");
					// continue; 
				} else if (current_board[i][j] == win_cond[i][j]) {
					temp_h = temp_h + 0;
					System.out.println("h + 0 = " + temp_h);
				} else if (current_board[i][j] != win_cond[i][j]) {

					int curr_value = current_board[i][j];

					for (int k = 0; k < matrix_size; k++) {
						for (int l = 0; l < matrix_size; l++) {
							if (curr_value == win_cond[k][l]) {
								temp_h = temp_h + (Math.abs(i - k) + Math.abs(j - l));
								System.out.println("temp_h + " + (Math.abs(i - k) + Math.abs(j - l)) + " = " + temp_h);
								break;
							}
						}
					}
					break;

				}
			}
		}

		// System.out.println("h value: " + temp_h);

		return temp_h;

	}

	/*
		Computes for the value of f. Note: Move this to State.java so it can initialize its own f value.
	*/
	public int compute_f(int g, int h) {
		return g + h;
	}

	public State remove_min_f(ArrayList<State> frontier) {

		int min_f = 0;
		int min_f_index = 0;

		for (State state : frontier) {
			if (state.get_f_value() < min_f) {
				min_f = state.get_f_value();
				min_f_index = frontier.indexOf(state);
			}
		}

		State min_f_state = frontier.get(min_f_index);
		frontier.remove(min_f_index);

		return min_f_state;

	}

	public boolean search_closed_list(State s, ArrayList<State> closed) {

		boolean found = false;

		for (State closed_state : closed) {
			if (compare_boards(s, closed_state)) {
				found = true;
			}
		}

		return found;

	}

	public boolean search_open_list(State s, ArrayList<State> open) {

		boolean found = false;

		for (State open_state : open) {
			if (compare_boards(s, open_state)) {
				found = true;
			}
		}

		return found;

	}

	public State find_duplicate(State s, ArrayList<State> open) {

		State duplicate = new State();

		for (State open_state : open) {
			if (compare_boards(s, open_state)) {
				duplicate = open_state;
			}
		}

		return duplicate;

	}

	public State AStar() {

		// Use LinkedList this time?
		// Will have to add linked list functionality to the state class
		// but then it shouldn't be a problem because A* runs on an heuristic

		ArrayList<State> open_list = new ArrayList<State>();
		ArrayList<State> closed_list = new ArrayList<State>();
		int[][] present_layout = this.get_current_layout();
		int matrix_size = this.get_matrix_dimension();
		Point empty_space = this.find_empty_space(present_layout);
		String previous_move = " ";
		State winning_state = new State();
		State best_node = new State();

		State current_state = new State(present_layout,matrix_size,empty_space,previous_move);
		current_state.set_f_value(compute_f(current_state.get_g_value(), compute_h(current_state, this.get_win_condition())));

		open_list.add(current_state);

		while (!open_list.isEmpty()) {

			best_node = remove_min_f(open_list);
			System.out.println("best_node.f = " + best_node.get_f_value() + "\n");
			closed_list.add(best_node);

			if (detect_win_state(best_node.get_current_board())) {
				System.out.println("got it!");
				winning_state = best_node;
				break;
			} else {

				for (String move : best_node.get_allowed_moves()) {
					System.out.println("move: " + move);
					State next_state = find_results(best_node, move);
					next_state.set_f_value(compute_f(next_state.get_g_value(), compute_h(next_state, this.get_win_condition())));

					if (!search_closed_list(next_state, closed_list)) {
						if (search_open_list(next_state, open_list)) {
							System.out.println("next state is in open list");
							State duplicate = find_duplicate(next_state, open_list);
							duplicate.set_f_value(compute_f(duplicate.get_g_value(), compute_h(duplicate, this.get_win_condition())));

							if (next_state.get_g_value() < duplicate.get_g_value()) {
								next_state.set_parent_state(best_node);
								open_list.add(next_state);
							}
						} else {
							System.out.println("not in closed or open list");
							next_state.set_parent_state(best_node);
							open_list.add(next_state);
						}
					}

				}

			}

		}

		// uncomment to see win state on console
		// for (int i = 0; i < this.get_matrix_dimension(); i++) {
		// 	for (int j = 0; j < this.get_matrix_dimension(); j++) {
		// 		System.out.print(winning_state.get_current_board()[i][j]);
		// 	}
		// 	System.out.println();
		// }

		// uncomment to see correct path on console
		// for (String move : winning_state.get_move_path()) {
		// 	System.out.print(move + " ");
		// }

		System.out.println("writing winning state to file...");
		write_path_to_file(winning_state);
		return winning_state;

	}

	/*
		Writes results of BFSearch() to file.
	*/
	public void write_path_to_file(State win) {

		try {

			FileWriter write_to_file = new FileWriter(new File("puzzle.out"));
			PrintWriter print_to_file = new PrintWriter(write_to_file);

			for (String move : win.get_move_path()) {
				print_to_file.print(move + " ");
			}
			
			print_to_file.print("\n");
			print_to_file.close();

		} catch (Exception e) {
			System.out.println(e);
		}

	}

	/*
		Initiates BFSearch using the board's current configuration.

		2/2/2020: Since graphics are still not interactive, supply different layouts
				  for testing.
	*/
	public State BFSearch() {

		this.check_solvability(this.get_current_layout());

		ArrayList<State> explored = new ArrayList<State>();
		int[][] present_layout = this.get_current_layout();
		int matrix_size = this.get_matrix_dimension();
		Point empty_space = this.find_empty_space(present_layout);
		String previous_move = " ";
		State winning_state = new State();

		State current_state = new State(present_layout,matrix_size,empty_space,previous_move);
		int frontier_index = 0;

		boolean not_in_explored = false;

		frontier.add(current_state);

		while(!frontier.isEmpty()) {	

			current_state = frontier.get(frontier_index);
			frontier.remove(frontier_index);

			if (detect_win_state(current_state.get_current_board())) {
				System.out.println("got it!");
				winning_state = current_state;
				break;
			} else {
				if (explored.isEmpty()) {
					for (String move : current_state.get_allowed_moves()) {
						State next_state = find_results(current_state, move);
						frontier.add(next_state);
					}

				} else {
					for (State previously_explored : explored) {
						boolean matched_state = compare_boards(current_state,previously_explored);
						if (matched_state == false) {
							not_in_explored = true;
						} else {
							not_in_explored = false;
							break;
						}
					}

					if (not_in_explored) {

						for (String move : current_state.get_allowed_moves()) {
							State next_state = find_results(current_state, move);
							frontier.add(next_state);
						}
						explored.add(current_state);

					} 

				}

			}

		}

		// uncomment to see win state on console
		// for (int i = 0; i < this.get_matrix_dimension(); i++) {
		// 	for (int j = 0; j < this.get_matrix_dimension(); j++) {
		// 		System.out.print(winning_state.get_current_board()[i][j]);
		// 	}
		// 	System.out.println();
		// }

		// uncomment to see correct path on console
		// for (String move : winning_state.get_move_path()) {
		// 	System.out.print(move + " ");
		// }

		winning_state.set_is_final(true);
		System.out.println("saving to file...");
		write_path_to_file(winning_state);

		return winning_state;

	}

	/*
		Initiates DFSearch using the board's current configuration.
		Copy pasted from previous BFSearch(), but edited to fit DFSearch algorithm description.

		It will get an answer, but when?
	*/
	public State DFSearch() {

		this.check_solvability(this.get_current_layout());

		ArrayList<State> explored = new ArrayList<State>();
		int[][] present_layout = this.get_current_layout();
		int matrix_size = this.get_matrix_dimension();
		Point empty_space = this.find_empty_space(present_layout);
		String previous_move = " ";
		State winning_state = new State();

		State current_state = new State(present_layout,matrix_size,empty_space,previous_move);

		boolean not_in_explored = false;

		// for testing
		// int run_counter = 0;

		frontier.add(current_state);

		while(!frontier.isEmpty()) {	

			// System.out.println("frontier size: " + frontier.size());
			// System.out.println("last frontier element at index: " + (frontier.size()-1));
			// run_counter++;
			// if (run_counter == 20) {
			// 	break;
			// }
			current_state = frontier.get(frontier.size() - 1);
			frontier.remove(frontier.size() - 1);

			for (int i = 0; i < this.get_matrix_dimension(); i++) {
				for (int j = 0; j < this.get_matrix_dimension(); j++) {
					System.out.print(current_state.get_current_board()[i][j]);
				}
				System.out.println();
			}
			System.out.println();

			if (detect_win_state(current_state.get_current_board())) {
				System.out.println("got it!");
				winning_state = current_state;
				break;
			} else {

				if (explored.isEmpty()) {

					for (String move : current_state.get_allowed_moves()) {
						State next_state = find_results(current_state, move);
						frontier.add(next_state);
					}

				} else {

					for (State previously_explored : explored) {
						boolean matched_state = compare_boards(current_state,previously_explored);
						if (matched_state == false) {
							not_in_explored = true;
						} else {
							not_in_explored = false;
							break;
						}
					}

					if (not_in_explored) {

						for (String move : current_state.get_allowed_moves()) {
							State next_state = find_results(current_state, move);
							frontier.add(next_state);
						}
						explored.add(current_state);

					} 

				}

			}

		}

		// uncomment to see win state on console
		// for (int i = 0; i < this.get_matrix_dimension(); i++) {
		// 	for (int j = 0; j < this.get_matrix_dimension(); j++) {
		// 		System.out.print(winning_state.get_current_board()[i][j]);
		// 	}
		// 	System.out.println();
		// }

		// uncomment to see correct path on console
		// for (String move : winning_state.get_move_path()) {
		// 	System.out.print(move + " ");
		// }

		winning_state.set_is_final(true);
		System.out.println("saving to file...");
		write_path_to_file(winning_state);

		return winning_state;

	}

	/*
		Sets up game graphics.
	*/
	public void graphics_setup() {

		int dimension = this.get_matrix_dimension();
		setSize(new Dimension(WINDOW_SIZE, WINDOW_SIZE+(WINDOW_SIZE/2)));
		
		JPanel main = new JPanel();
		main.setLayout(new BoxLayout(main, BoxLayout.PAGE_AXIS));

		JPanel game = new JPanel();
		game.setLayout(new GridLayout(dimension, dimension));
		game.setSize(new Dimension(WINDOW_SIZE, WINDOW_SIZE));
		game.setBackground(Color.BLACK);

		for (int i = 0; i < dimension; i++) {
			for(int j = 0; j < dimension; j++) {
				Tile tile = new Tile(current_layout[i][j], WINDOW_SIZE/this.get_matrix_dimension());
				game.add(tile);
			}
		}

		JPanel solve_button_panel = new JPanel();
		solve_button_panel.setSize(new Dimension(WINDOW_SIZE,WINDOW_SIZE/2));
		// solve_button_panel.setLayout(new BoxLayout(solve_button_panel, BoxLayout.PAGE_AXIS));

		JPanel options_panel = new JPanel();
		options_panel.setVisible(false);					// should become visible if user clicks solve button 

		JButton solve_button = new JButton("Solve!");
		JButton bfs_solve = new JButton("BFSearch");
		JButton dfs_solve = new JButton("DFSearch");
		JButton a_star_solve = new JButton("A*");

		options_panel.add(bfs_solve);
		options_panel.add(dfs_solve);
		options_panel.add(a_star_solve);

		solve_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				options_panel.setVisible(true);
				solve_button.setVisible(false);
			}
		});

		bfs_solve.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BFSearch();
			}
		});

		dfs_solve.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DFSearch();
			}
		});

		a_star_solve.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AStar();
			}
		});

		solve_button_panel.add(solve_button);
		solve_button_panel.add(options_panel);

		main.add(game);
		main.add(solve_button_panel);
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

		A*: OpenList/Frontier is a priority queue so add nodes by priority
		  : TODO: Make 2DArray comparison function/find easier way to compare 2D arrays
		  		: Functions to compute f, g, and h values
		  		: May babaguhin sa last if condition ng A*. Can't remember exactly what but it has to
		  		  do with closedList
		  		: Tamang docu naman pag may time.

	References:
		http://zetcode.com/tutorials/javagamestutorial/movingsprites/
	
*/


