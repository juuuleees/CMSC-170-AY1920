import java.nio.file.Path;
import java.nio.file.Paths;

public class SpamBag extends BagOfWords {
	
	private final float PROBABILITY_THRESHOLD = 0.50;
	private Path spam_folder;

	// getters
	public Path get_spam_folder() { return this.spam_folder; }

	// setters
	public void set_spam_folder(Path sf) { this.spam_folder = sf; }


	// constructors
	public SpamBag() {}
	public SpamBag(Path spam_file) {

	}

	// methods for computation

	/*
		Computes the value of P(W_n|Spam) for each word in the bag and returns
		an ArrayList containing all the values.
	*/
	public ArrayList<Float> compute_word_probabilities() {}

	/*
		Computes the value of P(message|Spam).
	*/
	public float msg_given_spam(ArrayList<Float> wps) {}

}