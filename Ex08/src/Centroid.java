import java.util.Vector;

public class Centroid extends Datapoint {
	
	private int classification;

	// getters and setter
	public int get_classification() { return this.classification; }

	public void set_classification(int nc) {this.classification = nc; }

	// constructor
	public Centroid() {}
	public Centroid(Vector<Double> fv) {
		this.set_feature_vector(fv);
	}
	public Centroid(Vector<Double> fv, int new_class) {
		this.set_feature_vector(fv);
		this.classification = new_class;
	}

}