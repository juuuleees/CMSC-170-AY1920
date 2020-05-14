import java.util.Vector;

public class Datapoint {
	
	private Vector<Double> feature_vector = new Vector<Double>();

	// getters and setter
	public Vector<Double> get_feature_vector() { return this.feature_vector; }

	public void set_feature_vector(Vector<Double> fv) { this.feature_vector = fv; }

	// constructor
	public Datapoint() {}
	public Datapoint(Vector<Double> fv) {
		this.feature_vector = fv;
	}

}