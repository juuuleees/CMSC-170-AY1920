import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.lang.StringBuilder;

// handles initial data storage
public class Datapack {
	
	private LinkedList<LinkedList<Double>> training_data = new LinkedList<LinkedList<Double>>();
	private double learning_rate;
	private double threshold;
	private double bias;
	private int features;
	private File input = new File("inputs/input.txt");

	// getters and setters
	public LinkedList<LinkedList<Double>> get_training_data() { return this.training_data; }
	public double get_learning_rate() { return this.learning_rate; }
	public double get_threshold() { return this.threshold; }
	public double get_bias() { return this.bias; }
	public int get_features() { return this.features; }
	public File get_input() { return this.input; }

	public void set_training_data(LinkedList<LinkedList<Double>> td) { this.training_data = td; }
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
	public void read_input_file(File in) {

		int lines = 0;

		try {

			if (in.isFile()) {

				BufferedReader reader = new BufferedReader(new FileReader(in));
				StringBuilder sb_temp = new StringBuilder();
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
						String[] row_contents = str_temp.split(" ");

						for (String s : row_contents) {
							row_temp.add(Double.parseDouble(s));
						}

						LinkedList<Double> row_n = new LinkedList<Double>(row_temp);
						training_data.add(row_n);

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
				// for (LinkedList list : training_data) {
				// 	System.out.println(list);
				// }
				// System.out.println("Features: " + get_features());

			}

		} catch (Exception e) {
			System.out.println(e);
		}

	}

}