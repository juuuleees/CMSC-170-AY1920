import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.util.Set;
import java.util.Vector;
import java.io.File;
import java.text.DecimalFormat;

public class Classifier extends BagOfWords {
	
	private static DecimalFormat places = new DecimalFormat("0.0000000000");
	private File input_folder;
	private float spam_count;
	private float ham_count;
	private float p_spam;
	private float p_ham;
	private float given_k;
	private boolean has_k;

	// getters
	public File get_input_folder() { return this.input_folder; }
	public float get_spam_count() { return this.spam_count; }
	public float get_ham_count() { return this.ham_count; }
	public float get_p_spam() { return this.p_spam; }
	public float get_p_ham() { return this.p_ham; }
	public float get_given_k() { return this.given_k; }
	public boolean get_has_k() { return this.has_k; }

	// setters
	public void set_input_folder(File inf) { this.input_folder = inf; }
	public void set_spam_count(float sc) { this.spam_count = sc; }
	public void set_ham_count(float hc) { this.ham_count = hc; }
	public void set_p_spam(float ps) { this.p_spam = ps; }
	public void set_p_ham(float ph) { this.p_ham = ph; }
	public void set_given_k(float k) { this.given_k = k; }
	public void set_has_k(boolean hk) { this.has_k = hk; }

	// constructors
	public Classifier() {}
	public Classifier(File inf) {
		this.set_input_folder(inf);
	}
	public Classifier(File inf, float spams, float hams) {
		this.set_input_folder(inf);
		this.set_spam_count(spams);
		this.set_ham_count(hams);
		this.set_has_k(false);

		generate_p_spam(spams, hams);
		generate_p_ham(spams, hams);

		System.out.println("p_spam: " + Float.parseFloat(places.format(this.get_p_spam())) 
			+ ", p_ham: " + Float.parseFloat(places.format(this.get_p_ham())));
	}
	public Classifier(File inf, float spams, float hams, float k) {
		this.set_input_folder(inf);
		this.set_spam_count(spams);
		this.set_ham_count(hams);
		this.set_given_k(k);
		this.set_has_k(true);

		generate_p_spam(spams, hams);
		generate_p_ham(spams, hams);

		System.out.println("p_spam: " + Float.parseFloat(places.format(this.get_p_spam())) 
			+ ", p_ham: " + Float.parseFloat(places.format(this.get_p_ham())));
	}

	// methods
	public void list_classifier_data() {

		File[] classifier_files = input_folder.listFiles();
		LinkedHashMap<String, Float> bag = this.get_word_bag();

		for (File class_doc : classifier_files) {
			String classifier_string = read_file_contents(class_doc);
			bagger(classifier_string);
		}

		System.out.println("Total classifier size: " + this.get_dict_size());
		System.out.println("Total classifier words: " + this.get_word_count());

		// uncomment to see classifier bag contents in terminal
		// Set<String> entries = bag.keySet();
		// for (String entry : entries) {
		// 	System.out.println(entry + ": " + bag.get(entry));
		// }

	}

	// Computes P(Spam).
	public void generate_p_spam(float sc, float hc) {
		
		boolean present_k = this.get_has_k();

		if (present_k) {
			float k_value = this.get_given_k();

			this.set_p_spam(Float.parseFloat(places.format((sc + k_value) / ((sc + hc) + 2*k_value))));
		} else {
			this.set_p_spam(Float.parseFloat(places.format(sc / (sc + hc))));
		}

	}

	// Computes P(Ham).
	public void generate_p_ham(float sc, float hc) {
		boolean present_k = this.get_has_k();

		if (present_k) {
			float k_value = this.get_given_k();

			this.set_p_ham(Float.parseFloat(places.format((hc + k_value) / ((sc + hc) + 2*k_value))));
		} else {
			this.set_p_ham(Float.parseFloat(places.format(hc / (sc + hc))));
		}
	}

	// Computes P(message|Ham).
	public float compute_message_given_ham(HamBag ham, String input) {

		LinkedHashMap<String, Float> the_bag = ham.get_word_bag();
		ArrayList<String> new_words = new ArrayList<String>();
		float m_given_ham = 1.0f;
		float word_temp = 1.0f;
		float instances = 1.0f;
		float bag_contents = ham.get_word_count();
		float k_value = this.get_given_k();
		String[] chopped_input = input.split(" ");
		boolean present_k = this.get_has_k();

		// do this if the user entered a value for k
		if (present_k) {

			for (String word : chopped_input) {
				if (!the_bag.containsKey(word)) {
					new_words.add(word);
				}
			}

			for (String word : chopped_input) {

				if (the_bag.containsKey(word)) {
					instances = the_bag.get(word);
					// System.out.println("instances of the word in ham: " + instances);
				} else {
					instances = 0.0f;
					// System.out.println("instances of the word in ham: " + instances);
				}
				// instances = the_bag.get(word);
				System.out.println("instances of the word " + word + " in ham: " + instances);
				System.out.println("k_value: " + k_value);
				System.out.println("bag_contents: " + bag_contents);
				System.out.println("bag.size(): " + the_bag.size());
				System.out.println("new_words.size(): " + new_words.size());

				word_temp = Float.parseFloat(places.format((
					(instances + k_value) / (bag_contents + (k_value * (the_bag.size() + new_words.size())))))
				);
				System.out.println("p(w|ham) = " + word_temp);

				m_given_ham = Float.parseFloat(places.format(m_given_ham * word_temp));
				System.out.println("P(message|Ham) = " + m_given_ham + "\n");

				word_temp = 0;
	
			}

		} else {

			for (String word : chopped_input) {

				if (the_bag.containsKey(word)) {
					instances = the_bag.get(word);
					// System.out.println("instances of the word in ham: " + instances);
				} else {
					instances = 0.0f;
					// System.out.println("instances of the word in ham: " + instances);
				}

				System.out.println("instances of the word in ham: " + instances);

				word_temp = Float.parseFloat(places.format((instances / the_bag.size())));
				System.out.println("p(w|ham) = " + word_temp);					
				m_given_ham = Float.parseFloat(places.format(m_given_ham * word_temp));

				// System.out.printl	n("P(message|Ham) = " + m_given_ham);	
				word_temp = 0;
	
			}

		}

		// System.out.println("P(message|Ham) = " + m_given_ham);

		return m_given_ham;

	}

	// Computes P(message|Spam).
	public float compute_message_given_spam(SpamBag spam, String input) {

		LinkedHashMap<String, Float> the_bag = spam.get_word_bag();
		ArrayList<String> new_words = new ArrayList<String>();
		float m_given_spam = 1.0f;
		float word_temp = 1.0f;
		float instances = 1.0f;
		float bag_contents = spam.get_word_count();
		float k_value = this.get_given_k();
		String[] chopped_input = input.split(" ");
		boolean present_k = this.get_has_k();

		// do this if the user entered a value for k
		if (present_k) {

			for (String word : chopped_input) {
				if (!the_bag.containsKey(word)) {
					new_words.add(word);
				}
			}

			for (String word : chopped_input) {

				instances = the_bag.get(word);
				System.out.println("instances of the word in spam: " + instances);

				word_temp = Float.parseFloat(places.format(
					((instances + k_value) / (bag_contents + (k_value * (the_bag.size() + new_words.size()))))
				));
				System.out.println("p(w|spam) = " + word_temp);

				m_given_spam = Float.parseFloat(places.format(m_given_spam * word_temp));
				System.out.println("P(message|spam) = " + m_given_spam);

				word_temp = 0;
	
			}

		} else {

			for (String word : chopped_input) {

				if (!the_bag.containsKey(word)) {
					instances = the_bag.get(word);
					System.out.println("instances of the word in spam: " + instances);
				} else {
					instances = 0.0f;
					System.out.println("instances of the word in spam: " + instances);
				}

				word_temp = Float.parseFloat(places.format((instances / the_bag.size())));
				System.out.println("p(w|spam) = " + word_temp);					
				m_given_spam = Float.parseFloat(places.format(m_given_spam * word_temp));

				System.out.println("P(message|spam) = " + m_given_spam);	
				word_temp = 0;
	
			}

		}

		return m_given_spam;

	}

	public float compute_p_message(float mgs, float ps, float mgh, float ph) {
		return (Float.parseFloat(places.format((mgs * ps) + (mgh * ph))));
	}

	public float compute_spam_given_message(float mgs, float message) {
		return (Float.parseFloat(places.format(mgs / message)));
	}

	public float compute_ham_given_message(float mgh, float message) {
		return (Float.parseFloat(places.format(mgh / message)));
	}

	public LinkedHashMap<Vector<String>, Float> classify_data(HamBag ham, SpamBag spam) {
		
		LinkedHashMap<Vector<String>, Float> message_classes = new LinkedHashMap<Vector<String>, Float>();
		File[] input_files = this.get_input_folder().listFiles();

		for (File input : input_files) {
			System.out.println("starting!!");

			// Read the file and save as a string
			String contents = read_file_contents(input);
			System.out.println("Final P(message|Ham) = " + this.compute_message_given_ham(ham, contents));

		}

		return message_classes;

	}

}