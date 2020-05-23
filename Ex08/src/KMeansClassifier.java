import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;
import java.util.Vector;
import java.io.File;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.lang.Math;
import java.lang.StringBuilder;

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
		LinkedList<Datapoint> data_points = new LinkedList<Datapoint>(this.training_data.get_data_points());
		int data_count = data_points.size();
		int data_index = 0;
		int centroid_count = 0;
		Random randomizer = new Random(System.currentTimeMillis());

		while (centroid_count != k) {

			Centroid centroid = new Centroid(); 

			if (centroid_count != k) {
				data_index = randomizer.nextInt(data_count);
				Vector<Double> fv = new Vector<Double>(data_points.get(data_index).get_feature_vector());
	
				centroid.set_feature_vector(fv);
				centroid.set_centroid_num(centroid_count);

				random_centroids.add(centroid);
				data_points.remove(data_index);
				data_count = data_points.size();

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
	public HashMap<Centroid, LinkedList<Datapoint>> organize_datapoints(LinkedList<Centroid> curr_cs, LinkedList<Datapoint> data_pts) {

		HashMap<Centroid, LinkedList<Datapoint>> second_batch = new HashMap<Centroid, LinkedList<Datapoint>>();
		int centroid_index = 0; 
		boolean first = true;

		// put the centroids in the hashmap for easier groupings
		for (Centroid c : curr_cs) {
			LinkedList<Datapoint> batch_pts = new LinkedList<Datapoint>();
			second_batch.put(c, batch_pts);
		}

		for (Datapoint dpt : data_pts) {

			Vector<Double> features = dpt.get_feature_vector();
			LinkedList<Double> distances = new LinkedList<Double>();
			double distance = 0;
			double smallest_dist = 0;
			int ft_index = 0;

			for (Centroid c : curr_cs) {
				
				distance = find_distance(c, dpt);

				if (first == true) { smallest_dist = distance; first = false; }
				else {
					if (distance < smallest_dist) {
						smallest_dist = distance;
					}
				}

				distances.add(distance);

				distance = 0;
				ft_index = 0;
			}

			int c_index = distances.indexOf(smallest_dist);
			second_batch.get(curr_cs.get(c_index)).add(dpt);

			distances.clear();
			first = true;

		}

		return second_batch;

	}

	public LinkedList<Centroid> compute_new_centroids(HashMap<Centroid, LinkedList<Datapoint>> curr_data) {

		LinkedList<Centroid> new_centroids = new LinkedList<Centroid>();
		int coor_index = 0;
		int iterations = 0;

		for (Centroid c : curr_data.keySet()) {

			Centroid updated_centroid = new Centroid();

			for (Datapoint dpt : curr_data.get(c)) {

				for (Double dpt_coor : dpt.get_feature_vector()) {
					if (iterations == 0) {
						
						updated_centroid.get_feature_vector().add(dpt_coor);

					} else if (iterations != 0) {
						
						updated_centroid.get_feature_vector().set(coor_index, (updated_centroid.get_feature_vector().get(coor_index) + dpt_coor));

					}
					coor_index++;
				}

				coor_index = 0;
				iterations++;

			}

			iterations = 0;

			for (double c_coor : updated_centroid.get_feature_vector()) {
				updated_centroid.get_feature_vector().set(coor_index, (updated_centroid.get_feature_vector().get(coor_index) / curr_data.get(c).size()));
				coor_index++;
			}

			coor_index = 0;

			new_centroids.add(updated_centroid);

		}

		return new_centroids;

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

				if (curr.equals(prev)) {
					continue;
				} else {
					stable = false;
					break;
				}

			}

			centroid_index++;

			if (stable == false) { break; }

		}

		return stable;

	}

	public void write_to_file(LinkedList<LinkedList<Centroid>> all_iterations) {

		StringBuilder iteration_temp = new StringBuilder();
		StringBuilder centroid_temp = new StringBuilder();
		int list_counter = 1;
		int c_counter = 1;

		iteration_temp.append("Iteration ");
		centroid_temp.append("Centroid ");

		try {

			FileWriter write_to_file = new FileWriter(new File("output.txt"));
			PrintWriter print_to_file = new PrintWriter(write_to_file);

			for (LinkedList<Centroid> i : all_iterations) {

				print_to_file.print(iteration_temp);
				print_to_file.print(list_counter + "\n");

				for (Centroid c : i) {
					print_to_file.print(centroid_temp);
					print_to_file.print(c_counter + ": ");
					print_to_file.print(c.get_feature_vector() + "\n");
					c_counter++;
				}

				print_to_file.print("\n");
				c_counter = 1;
				list_counter++;

			}

			print_to_file.close();

		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public void classify() {

		LinkedList<LinkedList<Centroid>> all_iterations = new LinkedList<LinkedList<Centroid>>();
		LinkedList<Centroid> curr_centroids = new LinkedList<Centroid>(randomize_centroids());
		LinkedList<Centroid> prev_centroids = new LinkedList<Centroid>();
		LinkedList<Datapoint> data_points = this.training_data.get_data_points();
		HashMap<Centroid, LinkedList<Datapoint>> classified_points = organize_datapoints(curr_centroids, data_points);
		int curr_c_index = 0;
		int iterations = 0;
		boolean is_stable = false;

		all_iterations.add(curr_centroids);

		while (is_stable != true) {

			prev_centroids = new LinkedList<Centroid>(curr_centroids);
			curr_centroids = compute_new_centroids(classified_points);
			all_iterations.add(new LinkedList<Centroid>(curr_centroids));

			is_stable = stable_centroids(curr_centroids, prev_centroids);
			iterations++;

			if (is_stable == true) {
				break;
			} else {
				classified_points = organize_datapoints(curr_centroids, data_points);
			}

		}

		write_to_file(all_iterations);

		System.out.println("Classification process completed. Output in output.txt.");

	}


}