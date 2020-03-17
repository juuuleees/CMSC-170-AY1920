import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Collections;
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

		LinkedHashMap<Double, Vector<Double>> pt_distances = new LinkedHashMap<Double, Vector<Double>>();
		LinkedHashMap<Integer, Integer> classes = new LinkedHashMap<Integer, Integer>();
		LinkedList<Vector<Double>> k_nearest = new LinkedList<Vector<Double>>();
		Set<Vector<Double>> keys = this.points.keySet();
		int new_pt_class = 0;

		for (Vector<Double> point : keys) {
			System.out.println(point);
			Double initial = 0d;
			
			for (int i = 0; i < this.elements; i++) {
				Double a = new_point.get(i);
				Double b = point.get(i);
				initial = initial + Math.pow((a - b), 2);	
			}
			
			Double distance = Math.sqrt(initial);
			pt_distances.put(distance, point);
		}

		// alternative ascending sort
		// Reference: https://stackoverflow.com/questions/922528/how-to-sort-map-values-by-key-in-java
		List<Double> asc_keys = new ArrayList<Double>(pt_distances.keySet());
		Collections.sort(asc_keys);

		// save the first k points mapped to the first k distances
		for (int i = 0; i < pt_distances.size(); i++) {
			if (i < this.chosen_k) {
				k_nearest.add(pt_distances.get(asc_keys.get(i)));
			}
		}

		// get the majority's class
		int count = 0;
		for (Vector<Double> k_near : k_nearest) {
			int class_val = this.points.get(k_near);
			if (!classes.containsKey(class_val)) {
				classes.put(class_val, 1);
			} else {
				classes.replace(class_val, classes.get(class_val)+1);
			}
		}

		int majority = 0;
		for (int c : classes.keySet()) {
			if (classes.get(c) > majority) {
				majority = classes.get(c);
				new_pt_class = c;
			}
		}

		this.points.put(new_point, new_pt_class);

	}

}