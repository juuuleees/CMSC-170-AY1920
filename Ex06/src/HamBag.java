import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Set;
import java.io.File;
import java.math.RoundingMode;
import java.text.DecimalFormat;


public class HamBag extends BagOfWords {
	
	private static float PROBABILITY_THRESHOLD = 0.50f;
	private static DecimalFormat four_places = new DecimalFormat("0.0000");
	private File ham_folder;

	// getters
	public File get_ham_folder() { return this.ham_folder; }

	// setters
	public void set_ham_folder(File sf) { this.ham_folder = sf; }


	// constructors
	public HamBag() {}
	public HamBag(File ham_files) {
		this.set_ham_folder(ham_files);
	}

	public void list_ham_data() {

		File[] ham_files = ham_folder.listFiles();
		LinkedHashMap<String, Float> bag = this.get_word_bag();

		for (File ham : ham_files) {
			String ham_string = read_file_contents(ham);
			bagger(ham_string);
		}

		System.out.println("Total ham words: " + this.get_word_count());

		// uncomment to see ham bag contents in terminal
		// Set<String> entries = bag.keySet();
		// for (String entry : entries) {
		// 	System.out.println(entry + ": " + bag.get(entry));
		// }

	}

	// methods for computation

	/*
		Computes the value of P(W_n|Ham) for each word in the bag and returns
		an ArrayList containing all the values.
	*/
	public ArrayList<Float> compute_word_probabilities() {

		ArrayList<Float> probabilities = new ArrayList<Float>();
		LinkedHashMap<String, Float> ham_bag = this.get_word_bag();
		float grand_total = this.get_word_count();

		Set<String> keys = ham_bag.keySet();

		for (String key : keys) {

			// probabilities.add(Float.parseFloat(four_places.format(ham_bag.get(key) / grand_total)));
			probabilities.add(ham_bag.get(key) / grand_total);

		}

		// for (float f : probabilities) {
		// 	System.out.println(f);
		// }

		return probabilities;

	}

	/*
		Computes the value of P(message|Ham).
	*/
	public float msg_given_ham(ArrayList<Float> wps) {

		float mgh_value = 1.0f;
		int count = 0;

		for (Float word_prob : wps) {
			System.out.println("mgh_value = " + mgh_value + " * " + word_prob);
			// mgh_value = Float.parseFloat(four_places.format(mgh_value * word_prob));
			mgh_value = mgh_value * word_prob;
			// count++;
			System.out.println("mgh_value = " + mgh_value);
			if (mgh_value == 0.0f) { break; }
		}

		return mgh_value;

	}

}