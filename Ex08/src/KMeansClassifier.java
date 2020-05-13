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
	public LinkedList<Vector<Double>> randomize_centroids() {

		LinkedList<Vector<Double>> random_centroids = new LinkedList<Vector<Double>>();
		int k = this.training_data.get_k_value();
		int data_count = this.training_data.get_data_points().size();
		int data_index = 0;
		int centroid_count = 0;
		Random randomizer = new Random(System.currentTimeMillis());

		while (centroid_count != k) {
			if (centroid_count != k) {
				data_index = randomizer.nextInt(data_count);
				random_centroids.add(new Vector<Double>(this.training_data.get_data_points().get(data_index)));
			}
			centroid_count++;
		}

		return random_centroids;

	}

	public void classify() {

		LinkedList<Vector<Double>> random_centroids = new LinkedList<Vector<Double>>(randomize_centroids());


	}


}