import java.util.Vector;

public class Centroid extends Datapoint {
	
	// 1 = is centroid, 0 = not a centroid
	private boolean centroid_status;
	private int centroid_num;

	// getters and setter
	public boolean get_centroid_status() { return this.centroid_status; }
	public int get_centroid_num() { return this.centroid_num;}

	public void set_centroid_status(boolean cs) { this.centroid_status = cs; }
	public void set_centroid_num(int cn) { this.centroid_num = cn; }
	// constructor
	public Centroid() {
		this.centroid_status = true;
	}
	public Centroid(Vector<Double> fv) {
		this.set_feature_vector(fv);
	}
	public Centroid(Vector<Double> fv, boolean cs) {
		this.set_feature_vector(fv);
		this.centroid_status = cs;
	}
	public Centroid(Vector<Double> fv, boolean cs, int cn) {
		this.set_feature_vector(fv);
		this.centroid_status = cs;
		this.centroid_num = cn;	
	}

}