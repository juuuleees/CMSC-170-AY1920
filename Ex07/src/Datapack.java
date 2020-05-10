import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

// handles initial data storage
public class Datapack {
	
	private LinkedList<LinkedList<Double>> training_data = new LinkedList<LinkedList<Double>>();
	private LinkedList<LinkedList<Double>> weight_computation_values = new LinkedList<LinkedList<Double>>();
	private LinkedList<Double> z_values = new LinkedList<Double>();
	private double learning_rate;
	private double threshold;
	private double bias;
	private int features;
	private File input = new File("inputs/input.txt");

	// getters and setters
	public LinkedList<LinkedList<Double>> get_training_data() { return this.training_data; }
	public LinkedList<LinkedList<Double>> get_weight_computation_values() { return this.weight_computation_values; }
	public LinkedList<Double> get_z_values() { return this.z_values; }
	public double get_learning_rate() { return this.learning_rate; }
	public double get_threshold() { return this.threshold; }
	public double get_bias() { return this.bias; }
	public int get_features() { return this.features; }
	public File get_input() { return this.input; }

	public void set_training_data(LinkedList<LinkedList<Double>> td) { this.training_data = td; }
	public void set_weight_computation_values(LinkedList<LinkedList<Double>> wcvs) { this.weight_computation_values = wcvs; }
	public void set_z_values(LinkedList<Double> zs) { this.z_values = zs; }
	public void set_learning_rate(double lr) { this.learning_rate = lr; }
	public void set_threshold(double new_thres) { this.threshold = new_thres; }
	public void set_bias(double new_bias) { this.bias = new_bias; }
	public void set_features(int fs) { this.features = fs; }
	public void set_input(File in) { this.input = in; }

	// constructors
	public Datapack() {
		read_input_file(input);
	}
	public Datapack(File data_file) {
		read_input_file(data_file);
	}

	// methods

	/* Read and format data for storage. */
	public void read_input_file(File in) {

		int lines = 0;

		try {

			if (in.isFile()) {

				BufferedReader reader = new BufferedReader(new FileReader(in));
				String str_temp;

				while ((str_temp = reader.readLine()) != null) {
					
					if (lines == 0) {
						set_learning_rate(Double.parseDouble(str_temp));
						lines++;
					} else if (lines == 1) {
						set_threshold(Double.parseDouble(str_temp));
						lines++;
					} else if (lines == 2) {
						set_bias(Double.parseDouble(str_temp));
						lines++;
					} else {
						
						LinkedList<Double> row_temp = new LinkedList<Double>();
						LinkedList<Double> computation_values = new LinkedList<Double>();
						String[] row_contents = str_temp.split(" ");
						int value_counter = 0;

						for (String s : row_contents) {
							if (value_counter != (row_contents.length-1)) {
								computation_values.add(Double.parseDouble(s));
							} else { z_values.add(Double.parseDouble(s)); }
							row_temp.add(Double.parseDouble(s));
							value_counter++;
						}

						computation_values.add(get_bias());
						LinkedList<Double> row_n = new LinkedList<Double>(row_temp);
						LinkedList<Double> feature_row = new LinkedList<Double>(computation_values);
						training_data.add(row_n);
						weight_computation_values.add(feature_row);

					}

				}

				reader.close();

				double b = this.get_bias();

				for (LinkedList<Double> list : training_data) {
					set_features(list.size()-1);
					list.add((list.size() - 1), b);
				}

				// System.out.println("Printing contents...");
				// System.out.println(this.get_learning_rate());
				// System.out.println(this.get_threshold());
				// System.out.println(this.get_bias());
				// for (LinkedList list : weight_computation_values) {
				// 	System.out.println(list);
				// }
				// System.out.println("Features: " + get_features());

			}

		} catch (Exception e) {
			System.out.println(e);
		}

	}

}