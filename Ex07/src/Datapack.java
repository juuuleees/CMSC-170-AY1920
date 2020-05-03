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
	private File input = new File("inputs/input.txt");

	// getters and setters
	public LinkedList<LinkedList<Double>> get_training_data() { return this.training_data; }
	public double get_learning_rate() { return this.learning_rate; }
	public double get_threshold() { return this.threshold; }
	public double get_bias() { return this.bias; }

	public void set_training_data(LinkedList<LinkedList<Double>> td) { this.training_data = td; }
	public void set_learning_rate(double lr) { this.learning_rate = lr; }
	public void set_threshold(double new_thres) { this.threshold = new_thres; }
	public void set_bias(double new_bias) { this.bias = new_bias; }

	// constructor
	public Datapack() {
		read_input_file(input);
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

				System.out.println("Printing contents...");
				// System.out.println(this.get_learning_rate());
				for (LinkedList list : training_data) {
					System.out.println(list);
				}

			}

		} catch (Exception e) {
			System.out.println(e);
		}

	}

}