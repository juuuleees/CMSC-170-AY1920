import java.util.LinkedList;
import java.util.Random;
import java.util.Vector;
import java.io.File;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.lang.Math;

public class KMeansClassifier {
	
	private Datapack training_data;
	private LinkedList<Centroid> classified_centroids = new LinkedList<Centroid>();

	// getters and setters
	public Datapack get_training_data() { return this.training_data; }
	public LinkedList<Centroid> get_classified_centroids() { return this.classified_centroids; }

	public void set_training_data(Datapack td) { this.training_data = td; }
	public void set_classified_centroids(LinkedList<Centroid> clcs) { this.classified_centroids = clcs; }

	// constructor
	public KMeansClassifier() {}
	public KMeansClassifier(Datapack td) {
		this.training_data = td; 
	}

	// methods

	/* Randomly selects centroids from the given data points. */
	public LinkedList<Centroid> randomize_centroids() {

		LinkedList<Centroid> random_centroids = new LinkedList<Centroid>();
		int k = this.training_data.get_k_value();
		int data_count = this.training_data.get_data_points().size();
		int data_index = 0;
		int centroid_count = 0;
		Random randomizer = new Random(System.currentTimeMillis());

		while (centroid_count != k) {
			if (centroid_count != k) {
				data_index = randomizer.nextInt(data_count);
				Centroid centroid = new Centroid(new Vector<Double>(this.training_data.get_data_points().get(data_index)));
				random_centroids.add(centroid);
			}
			centroid_count++;
		}

		return random_centroids;

	}

	/* Checks if the centroids have stabilized based on the centroids taken from the previous iteration. */
	public boolean stable_centroids(LinkedList<Centroid> curr_cs, LinkedList<Centroid> prev_cs) {

		boolean stable = true;
		int centroid_index = 0;
		int fv_index = 0;

		for (Centroid c: curr_cs) {
			if (centroid_index != prev_cs.size()) {
				Vector<Double> curr = curr_cs.get(centroid_index).get_feature_vector();
				Vector<Double> prev = prev_cs.get(centroid_index).get_feature_vector();

				for (double feature : curr) {
					if (fv_index != prev.size()) {
						if (curr.get(fv_index) != prev.get(fv_index)) {
							stable = false;
						}
					}
				}

				fv_index = 0;
			}
			centroid_index++;
		}

		return stable;

	}

	public void classify() {

		LinkedList<Centroid> curr_centroids = new LinkedList<Centroid>(randomize_centroids());
		LinkedList<Centroid> prev_centroids = new LinkedList<Centroid>();
		LinkedList<Vector<Double>> data_points = this.training_data.get_data_points();

		for (Centroid c : curr_centroids) {
			System.out.println(c.get_feature_vector());
		}

		// while (!stable_centroids(curr_centroids, prev_centroids)) {

		// 	// Find the centroid nearest each point


		// 	break;

		// }

	}


}