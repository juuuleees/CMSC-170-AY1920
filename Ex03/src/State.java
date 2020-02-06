import java.util.ArrayList;
import java.awt.Point;

public class State {
	
	private ArrayList<String> move_path = new ArrayList<String>();
	private ArrayList<String> allowed_moves = new ArrayList<String>();
	private State parent_state;
	private Point empty_space_loc;
	private int[][] current_board;
	private String prev_move;
	private int matrix_size;
	private int path_cost;
	private boolean is_initial;
	private boolean is_final;

	// getters
	public ArrayList<String> get_move_path() { return this.move_path; }
	public ArrayList<String> get_allowed_moves() { return this.allowed_moves; }
	public State get_parent_state() { return this.parent_state; }
	public int[][] get_current_board() { return this.current_board; }
	public String get_prev_move() { return this.prev_move; }
	public int get_matrix_size() { return this.matrix_size; }
	public int get_path_cost() { return this.path_cost; }
	public Point get_empty_space_loc() { return this.empty_space_loc; }
	public boolean get_is_initial() { return this.is_initial; }
	public boolean get_is_final() { return this.is_final; }

	// setters
	public void set_move_path(ArrayList<String> mp) { this.move_path = mp; }
	public void set_allowed_moves(ArrayList<String> am) { this.allowed_moves = am; }
	public void set_parent_state(State p) { this.parent_state = p; }
	public void set_current_board(int[][] cb) { this.current_board = cb; }
	public void set_prev_move(String pm) { this.prev_move = pm; }
	public void set_matrix_size(int ms) { this.matrix_size = ms; }
	public void set_path_cost(int p) { this.path_cost = p; }
	public void set_empty_space_loc(Point loc) { this.empty_space_loc = loc; }
	public void set_is_final(boolean f) { this.is_final = f; }
	public void set_is_initial(boolean i) { this.is_initial = i; }

	// constructors
	public State() {}
	public State(int[][] cb, int ms, Point spc_loc, String prev) {
		this.set_current_board(cb);
		this.set_matrix_size(ms);
		this.set_empty_space_loc(spc_loc);
		this.set_prev_move(prev);
		this.set_allowed_moves(this.find_possible_moves());
		this.set_is_initial(true);
	}
	public State(int[][] cb, Point spc_loc, State parent, String prev) {
		this.set_current_board(cb);
		this.set_matrix_size(parent.get_matrix_size());
		this.set_empty_space_loc(spc_loc);
		this.set_prev_move(prev);
		this.set_allowed_moves(this.find_possible_moves());
		this.set_is_initial(false);
		this.list_previous_moves(parent);
		this.get_move_path().add(prev);
		this.set_path_cost(this.get_move_path().size());
	}

	// methods
	public ArrayList<String> find_possible_moves() {

		ArrayList<String> moveset = new ArrayList<String>();
		Point loc = this.get_empty_space_loc();
		int row = (int)loc.getX();
		int col = (int)loc.getY();

		if ((row - 1) >= 0) {
			String up = "U";
			moveset.add(up);
		}
		if ((col + 1) < this.get_matrix_size()) {
			String right = "R";
			moveset.add(right);
		}
		if ((row + 1) < this.get_matrix_size()) {
			String down = "D";
			moveset.add(down);
		}
		if ((col - 1) >= 0) {
			String left = "L";
			moveset.add(left);
		}

		return moveset;

	}

	public void list_previous_moves(State parent) {

		if (!parent.get_is_initial()) {
			for (String move : parent.get_move_path()) {
				this.get_move_path().add(move);
			}
		}

	}

}