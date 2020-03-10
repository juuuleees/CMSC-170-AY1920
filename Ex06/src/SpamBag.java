import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.util.Set;
import java.io.File;
import java.text.DecimalFormat;

public class SpamBag extends BagOfWords {
	
	private static float PROBABILITY_THRESHOLD = 0.50f;
	private static DecimalFormat four_places = new DecimalFormat("0.0000");
	private File spam_folder;

	// getters
	public File get_spam_folder() { return this.spam_folder; }

	// setters
	public void set_spam_folder(File sf) { this.spam_folder = sf; }


	// constructors
	public SpamBag() {}
	public SpamBag(File spam_files) {
		this.set_spam_folder(spam_files);
	}

	public void list_spam_data() {

		File[] spam_files = spam_folder.listFiles();
		LinkedHashMap<String, Float> bag = this.get_word_bag();

		for (File spam : spam_files) {
			String spam_string = read_file_contents(spam);
			bagger(spam_string);
		}

		System.out.println("Total spam words: " + this.get_word_count());

		// uncomment to see ham bag contents in terminal
		// Set<String> entries = bag.keySet();
		// for (String entry : entries) {
		// 	System.out.println(entry + ": " + bag.get(entry));
		// }
	}

	// methods for computation

	/*
		Computes the value of P(W_n|Spam) for each word in the bag and returns
		an ArrayList containing all the values.
	*/
	public ArrayList<Float> compute_word_probabilities() {

		ArrayList<Float> probabilities = new ArrayList<Float>();
		LinkedHashMap<String, Float> ham_bag = this.get_word_bag();
		float grand_total = this.get_word_count();

		Set<String> keys = ham_bag.keySet();

		for (String key : keys) {

			probabilities.add(Float.parseFloat(four_places.format(ham_bag.get(key) / grand_total)));

		}

		return probabilities;

	}

	/*
		Computes the value of P(message|Spam).
	*/
	// public float msg_given_spam(ArrayList<Float> wps) {}

}