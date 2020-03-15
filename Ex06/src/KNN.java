import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Set;
import java.util.Vector;
import java.lang.StringBuilder;
import java.lang.Math;
import java.io.File;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

public class KNN {
		
	private final int ELEMENT_SIZE_ERROR = 1;
	private LinkedHashMap<Vector<Double>, Integer> points = new LinkedHashMap<Vector<Double>, Integer>();
	private File training_data;
	private int chosen_k;
	private int elements;

	// getters and setters
	public LinkedHashMap<Vector<Double>, Integer> get_points() { return this.points; }
	public File get_training_data() { return this.training_data; }
	public int get_chosen_k() { return this.chosen_k; }
	public int get_elements() { return this.elements; }

	public void set_points(LinkedHashMap<Vector<Double>, Integer> ps) { this.points = ps; }
	public void set_training_data(File new_td) { this.training_data = new_td; }
	public void set_chosen_k(int ck) { this.chosen_k = ck; }
	public void set_elements(int e) { this.elements = e; }

	// constructor
	public KNN(File td) {
		this.training_data = td;
		clean_training_data();
	}

	// methods
	public void clean_training_data() {

		File t_data = this.get_training_data();

		try {

			if (t_data.isFile()) {

				BufferedReader data_reader = new BufferedReader(new FileReader(t_data));
				StringBuilder file_data = new StringBuilder();
				String temp;
				Vector<Double> temp_point =  new Vector<Double>();
				int line = 0;

				while ((temp = data_reader.readLine()) != null) {
					if (line == 0) {
						this.set_chosen_k(Integer.parseInt(temp));
						System.out.println("k: " + this.chosen_k);
					} else {
						
						String[] point_data = temp.split(" ");
						this.elements = point_data.length - 1;
						
						for (int i = 0; i < point_data.length; i++) {
							// System.out.println(point_data[i]);
							// get the class at the end of the input line
							if (i == (point_data.length-1)) {
								// System.out.println("Checking point class...");
								Vector<Double> stored_point = new Vector<Double>();
								for (int j = 0; j < temp_point.size(); j++) {
									stored_point.add(temp_point.get(j));
								}
								this.points.put(stored_point, Integer.parseInt(point_data[i]));
							}
							temp_point.add(Double.valueOf(point_data[i]));
						}

						temp_point.clear();

					}
					line++;
				}

				// use this to check if the points were saved to the linked hashmap correctly
				// for (Vector point : this.points.keySet()) {
				// 	System.out.println(point + ": " + this.points.get(point));
				// }

			}

		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public void run_knn_algorithm(Vector<Double> new_point) {

		LinkedList<Double> distances = new LinkedList<Double>();
		LinkedHashMap<Double, Vector<Double> pt_distances = new LinkedHashMap<Double, Vector<Double>();
		LinkedList<Vector<Double>> k_nearest = new LinkedList<Vector<Double>>();
		Set<Vector<Double>> keys = this.points.keySet();

		for (Vector<Double> point : keys) {
			Double initial = 0d;
			// System.out.println("second_val: " + second_val.getClass());
			
			for (int i = 0; i < this.elements; i++) {
				System.out.println(new_point.get(i) + " (new), " + point.get(i) + " (old)");
				Double a = new_point.get(i);
				Double b = point.get(i);
				initial = initial + Math.pow((a - b), 2);	
			}
			System.out.println(initial);
			Double distance = Math.sqrt(initial);
			System.out.println("Distance: " + distance + "\n");
			distances.add(distance);
			pt_distances.put(distance, point);
		}

		// sort in ascending order
		// double temp = 0;
		// for (int i = 0; i < distances.size()-1; i++) {
		// 	int min = i;
		// 	for (int j = i+1; j < distances.size(); j++) {
		// 		if (distances.get(j) < distances.get(min)) {
		// 			min = j;
		// 		}
		// 	}

		// 	// swap(min, i)
		// 	temp = distances.get(min);
		// 	distances.set(min, distances.get(i));
		// 	distances.set(i, temp);
		// }

		// // get the first k distances
		// for (int i = 0; i < this.chosen_k; i++) {
			
		// }

		// TODO: Sort LinkedHashMap in ascending order.

	}

}