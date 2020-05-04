import java.util.LinkedList;

public class Iteration {
	
	public LinkedList<LinkedList<Double>> rows = new LinkedList<LinkedList<Double>>();

	// getters and setters
	public LinkedList<LinkedList<Double>> get_rows() { return this.rows; }

	public void set_rows(LinkedList<LinkedList<Double>> rs) { this.rows = rs; }

	// constructor
	public Iteration() {}
	public Iteration(Iteration i) {
		this.rows = i.rows;
	}

}