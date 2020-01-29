import java.util.ArrayList;

public class State {
	
	private ArrayList<String> move_path = new ArrayList<String>();
	private ArrayList<String> allowed_moves = new ArrayList<String>();
	private int[][] current_board;
	private String prev_move;
	private int empty_space_loc;
	private boolean is_initial;
	private boolean is_final;

	// getters
	public ArrayList<String> get_move_path() { return this.move_path; }
	public ArrayList<String> get_allowed_moves() { return this.allowed_moves; }
	public int[][] get_current_board() { return this.current_board; }
	public String get_prev_move() { return this.prev_move; }
	public int get_empty_space_loc() { return this.empty_space_loc; }
	public boolean get_is_initial() { return this.is_initial; }
	public boolean get_is_final() { return this.is_final; }

	// setters
	public void set_move_path(ArrayList<String> mp) { this.move_path = mp; }
	public void set_allowed_moves(ArrayList<String> am) { this.allowed_moves = am; }
	public void set_current_board(int[][] cb) { this.current_board = cb; }
	public void set_prev_move(String pm) { this.prev_move = pm; }
	public void set_empty_space_loc(int loc) { this.empty_space_loc = loc; }
	public void set_is_final(boolean f) { this.is_final = f; }
	public void set_is_initial(boolean i) { this.is_initial = i; }

	// constructors
	public State() {}
	public State(int[][] cb, int spc_loc, String prev) {
		this.set_current_board(cb);
		this.set_empty_space_loc(spc_loc);
		this.set_prev_move(prev);
	}

}