import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.lang.StringBuilder;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.File;

public class BagOfWords {
	
	private LinkedHashMap<String, Float> word_bag = new LinkedHashMap<String, Float>();
	private File input_file;
	private File output_file = new File("output.txt");
	private String message = null;
	private float dict_size;
	private float word_count = 0;

	// getters
	public LinkedHashMap<String, Float> get_word_bag() { return this.word_bag; }
	public File get_input_file() { return this.input_file; }
	public File get_output_file() { return this.output_file; }
	public String get_message() { return this.message; }
	public float get_dict_size() { return this.dict_size; }
	public float get_word_count() { return this.word_count; }

	// setters
	public void set_word_bag(LinkedHashMap<String, Float> wb) { this.word_bag = wb; }
	public void set_input_file(File i) { this.input_file = i; }
	public void set_output_file(File o) { this.output_file = o; }
	public void set_message(String m) { this.message = m; }
	public void set_dict_size(float ds) { this.dict_size = ds; }
	public void set_word_count(float wc) { this.word_count = wc; }

	// constructor
	public BagOfWords() {}
	public BagOfWords(File in) {
		this.set_message(read_file_contents(in));
	}

	// methods
	/*
		Saves file contents as a String after cleaning it up.
	*/
	public String read_file_contents(File in_file) {

		String contents = null;

		try {

			if (in_file.isFile()) {

				BufferedReader message_reader = new BufferedReader(new FileReader(in_file));
				StringBuilder message_string = new StringBuilder();

				while ((message = message_reader.readLine()) != null) {
					message_string.append(message + " ");
				}

				contents = message_string.toString().toLowerCase().replaceAll("[^A-Za-z0-9\r ]", "");

				message_reader.close();

			}

		} catch (Exception e) {
			System.out.println(e);
		}

		return contents;

	}

	/*
		Writes contents of hashmap to file.
	*/
	public void write_to_file(LinkedHashMap<String, Float> bag) {
		
		try {

			ArrayList<String> words = new ArrayList<String>(bag.keySet());
			FileWriter write = new FileWriter(output_file);
			PrintWriter print = new PrintWriter(write);

			for (String word : words) {
				print.print(word + " " + bag.get(word) + "\n");
			}

			print.close();

		} catch (Exception e) {
			System.out.println(e);
		}

	}

	/*
		Count the words.
	*/
	public void bagger(String sentence) {

		LinkedHashMap<String, Float> bag = this.get_word_bag();
		float all_words = 0;
		float grand_total = 0;

		try {

			String[] word_array = sentence.split(" ");
			for (String word : word_array) {
				
				if (!bag.containsKey(word)) {
					bag.put(word, 1f);
				} else {
					bag.put(word, bag.get(word)+1f);
				}
				all_words++;
			}

			for (String word_key : bag.keySet()) {
				grand_total = grand_total + bag.get(word_key);
			}

			this.set_dict_size(all_words);
			this.set_word_count(grand_total);

			// write_to_file(bag);

		} catch (Exception e) {
			System.out.println(e);
		}

	}
	
}