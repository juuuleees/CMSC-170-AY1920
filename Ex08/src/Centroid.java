import java.util.Vector;

public class Centroid {
	
	private Vector feature_vector = new Vector();
	private int classification;

	// getters and setter
	public Vector get_feature_vector() { return this.feature_vector; }
	public int get_classification() { return this.classification; }

	public void set_feature_vector(Vector fv) { this.feature_vector = fv; }
	public void set_classification(int nc) {this.classification = nc; }

	// constructor
	public Centroid() {}
	public Centroid(Vector fv) {
		this.feature_vector = fv;
	}
	public Centroid(Vector fv, int new_class) {
		this.feature_vector = fv;
		this.classification = new_class;
	}

}