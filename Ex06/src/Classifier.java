import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.util.Set;
import java.io.File;
import java.text.DecimalFormat;

public class Classifier extends BagOfWords {
	
	private static DecimalFormat four_places = new DecimalFormat("0.0000");
	private File input_folder;

	// getters
	public File get_input_folder() { return this.input_folder; }

	// setters
	public void set_input_folder(File inf) { this.input_folder = inf; }

	// constructors
	public Classifier() {}
	public Classifier(File inf) {
		this.set_input_folder(inf);
	}

	public void clean_classifier_data() {

		File[] input_files = input_folder.listFiles();
		LinkedHashMap<String, Float> bag = this.get_word_bag();

		for (File input : input_files) {
			String input_string = read_file_contents(input);
			bagger(input_string);
		}

		System.out.println("Total input words: " + this.get_word_count());

		// uncomment to see input bag contents in terminal
		// Set<String> entries = bag.keySet();
		// for (String entry : entries) {
		// 	System.out.println(entry + ": " + bag.get(entry));
		// }
	}

	/*
		Computes the value of P(W_n|Spam) for each word in the bag and returns
		an ArrayList containing all the values.
	*/
	public ArrayList<Float> compute_word_probabilities() {

		ArrayList<Float> probabilities = new ArrayList<Float>();
		LinkedHashMap<String, Float> class_bag = this.get_word_bag();
		float grand_total = this.get_word_count();

		Set<String> keys = class_bag.keySet();

		for (String key : keys) {

			probabilities.add(Float.parseFloat(four_places.format(class_bag.get(key) / grand_total)));

		}

		return probabilities;

	}

	public void word_given_class(BagOfWords selected_bag, int h_or_s) {
		
	}


}