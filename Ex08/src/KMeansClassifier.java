import java.util.HashMap;
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
				Vector<Double> fv = new Vector<Double>(this.training_data.get_data_points().get(data_index).get_feature_vector());
				Centroid centroid = new Centroid(fv, true, centroid_count);
				random_centroids.add(centroid);
			}
			centroid_count++;
		}

		return random_centroids;

	}

	public double find_distance(Centroid c, Datapoint d) {

		Vector<Double> c_fts = c.get_feature_vector();
		Vector<Double> dpt_fts = d.get_feature_vector();
		double distance = 0;
		int ft_index = 0;

		for (double c_coor : c_fts) {
			distance = distance + (Math.pow((c_coor - dpt_fts.get(ft_index)), 2));
			ft_index++;
		}

		distance = Math.sqrt(distance);

		return distance;

	}

	/* Performs the first iteration using the first batch of randomly picked centroids. */
	public HashMap<Centroid, LinkedList<Datapoint>> first_iteration(LinkedList<Centroid> curr_cs, LinkedList<Datapoint> data_pts) {

		HashMap<Centroid, LinkedList<Datapoint>> second_batch = new HashMap<Centroid, LinkedList<Datapoint>>();
		int centroid_index = 0; 

		for (Centroid c : curr_cs) {
			System.out.println(c.get_feature_vector() + ", C" + c.get_centroid_num());
		}

		// put the centroids in the hashmap for easier groupings
		for (Centroid c : curr_cs) {
			LinkedList<Datapoint> batch_pts = new LinkedList<Datapoint>();
			second_batch.put(c, batch_pts);
		}

		for (Datapoint dpt : data_pts) {

			// figure out how to compute the distance then compare it to the previous distance

		}

		return second_batch;

	}

	/* Checks if the centroids have stabilized based on the centroids taken from the previous iteration. */
	/* girlalu mali itez. kulang ng isa pang for loop bc you gotta do this for every class of centroid parang ganoin */ 
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
							break;
						}
					}
				}

				fv_index = 0;
			} else if (prev_cs.size() == 0) { 
				stable = false;
				break; 
			}
			centroid_index++;
		}

		return stable;

	}


	// andaming kailangan i-overhaul, start with Datapack.java and modify the object type
	// to be Datapoint not just Vector<Double>
	public void classify() {

		LinkedList<Centroid> curr_centroids = new LinkedList<Centroid>(randomize_centroids());
		LinkedList<Centroid> prev_centroids = new LinkedList<Centroid>();
		LinkedList<Datapoint> data_points = this.training_data.get_data_points();
		HashMap<Centroid, LinkedList<Datapoint>> classified_points = first_iteration(curr_centroids, data_points);
		int curr_c_index = 0;
		int iterations = 1;

		

	}


}