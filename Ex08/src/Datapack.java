import java.util.LinkedList;
import java.util.Vector;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.File;
import java.lang.StringBuilder;

public class Datapack {
	
	private LinkedList<Datapoint> data_points = new LinkedList<Datapoint>();
	private File input_file = new File("inputs/input.txt");
	private int k_value;

	// getters and setters
	public LinkedList<Datapoint> get_data_points() { return this.data_points; }
	public File get_input_file() { return this.input_file; }
	public int get_k_value() { return this.k_value; }

	public void set_data_points(LinkedList<Datapoint> dpts) { this.data_points = dpts; }
	public void set_input_file(File n_in) { this.input_file = n_in; }
	public void set_k_value(int nkv) { this.k_value = nkv; }

	// constructor
	public Datapack() { read_data(); }
	public Datapack(File input) {
		this.input_file = input;
		read_data();
	}
	public Datapack(LinkedList<Datapoint> dpts, File inf) {
		this.data_points = dpts;
		this.input_file = inf;

		read_data();
	}

	// methods

	/* Reads data from the file into the correct areas for future use. */
	public void read_data() {

		try {

			BufferedReader file_reader = new BufferedReader(new FileReader(input_file));
			String input_str;
			int lines = 0; 

			while ((input_str = file_reader.readLine()) != null) {
				if (lines == 0) {
					this.k_value = Integer.parseInt(input_str);
				} else {

					Vector<Double> features = new Vector<Double>();
					Datapoint d = new Datapoint();
					String[] feature_str = input_str.split(" ");

					for (String f : feature_str) {
						features.add(Double.parseDouble(f));
					}

					d.set_feature_vector(features);
					this.data_points.add(d);

				}
				lines++;
			}

		} catch (Exception e) {
			System.out.println(e);
		}

	}

}