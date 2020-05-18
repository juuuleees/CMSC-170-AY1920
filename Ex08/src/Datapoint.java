import java.util.Vector;

public class Datapoint {
	
	private Vector<Double> feature_vector = new Vector<Double>();
	private int classification;

	// getters and setter
	public Vector<Double> get_feature_vector() { return this.feature_vector; }
	public int get_classification() { return this.classification; }

	public void set_feature_vector(Vector<Double> fv) { this.feature_vector = fv; }
	public void set_classification(int nc) {this.classification = nc; }

	// constructor
	public Datapoint() {}
	public Datapoint(Vector<Double> fv) {
		this.feature_vector = fv;
	}
	public Datapoint(Vector<Double> fv, int c) {
		this.feature_vector = fv;
		this.classification = c; 
	}

}