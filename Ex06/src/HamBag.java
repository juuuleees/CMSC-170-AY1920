import java.nio.file.Path;
import java.nio.file.Paths;

public class HamBag extends BagOfWords {
	
	private final float PROBABILITY_THRESHOLD = 0.50;
	private Path ham_folder;

	// getters
	public Path get_ham_folder() { return this.ham_folder; }

	// setters
	public void set_ham_folder(Path sf) { this.ham_folder = sf; }


	// constructors
	public HamBag() {}
	public HamBag(Path ham_file) {

	}

	// methods for computation

	/*
		Computes the value of P(W_n|Ham) for each word in the bag and returns
		an ArrayList containing all the values.
	*/
	public ArrayList<Float> compute_word_probabilities() {}

	/*
		Computes the value of P(message|Ham).
	*/
	public float msg_given_ham(ArrayList<Float> wps) {}

}