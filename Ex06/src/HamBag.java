import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Set;
import java.io.File;
import java.math.RoundingMode;
import java.text.DecimalFormat;


public class HamBag extends BagOfWords {
	
	private static float PROBABILITY_THRESHOLD = 0.50f;
	private static DecimalFormat five_places = new DecimalFormat("0.0000");
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

		System.out.println("Total ham size: " + this.get_dict_size());
		System.out.println("Total ham words: " + this.get_word_count());

		// uncomment to see ham bag contents in terminal
		// Set<String> entries = bag.keySet();
		// for (String entry : entries) {
		// 	System.out.println(entry + ": " + bag.get(entry));
		// }

	}

}