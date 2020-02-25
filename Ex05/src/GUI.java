import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JFrame;
import javax.swing.JFileChooser;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.BoxLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Vector;
import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.io.File;

public class GUI extends JFrame {
	
	private final JFileChooser file_chooser = new JFileChooser("inputs");
	private final int WINDOW_WIDTH = 500;
	private final int WINDOW_LENGTH = 550;
	private final String WORDS_HEADER = "Word";
	private final String FREQUENCY_HEADER = "Frequency";
	private BagOfWords word_bag;
	private Vector cleaned_bag;
	private File chosen_file;
	private int bag_size;

	// getters
	public BagOfWords get_word_bag() { return this.word_bag; }
	public Vector get_cleaned_bag() { return this.cleaned_bag; }
	public File get_chosen_file() { return this.chosen_file; }
	public int get_bag_size() { return this.bag_size; }

	// setters
	public void set_word_bag(BagOfWords b) { this.word_bag = b; }
	public void set_cleaned_bag(Vector cb) { this.cleaned_bag = cb; }
	public void set_chosen_file(File cf) { this.chosen_file = cf; }
	public void set_bag_size(int s) { this.bag_size = s; }

	// constructor
	public GUI() {}

	// methods
	public void graphics_setup() {

		JPanel main = new JPanel();
		main.setLayout(new BoxLayout(main, BoxLayout.PAGE_AXIS));

		JPanel file_selection = new JPanel();
		JLabel fs_label = new JLabel("Select File: ");
		JButton select = new JButton("Browse...");

		file_selection.add(fs_label);
		file_selection.add(select);
		
		JPanel word_bag_details = new JPanel();
		word_bag_details.setLayout(new GridLayout(2,2));
		JLabel dict_label = new JLabel("Dictionary Size: ");
		JLabel word_count_label = new JLabel("Total Words: ");
		JTextField dict_size = new JTextField();
		JTextField total_word_count = new JTextField();

		word_bag_details.add(dict_label);
		word_bag_details.add(dict_size);
		word_bag_details.add(word_count_label);
		word_bag_details.add(total_word_count);

		JPanel words_and_counts = new JPanel();
		words_and_counts.setVisible(false);

		select.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int opening = file_chooser.showOpenDialog(null);

				if (opening == JFileChooser.APPROVE_OPTION) {
					chosen_file = file_chooser.getSelectedFile();
					System.out.println("file set.");
				}

				BagOfWords new_word_bag = new BagOfWords(chosen_file);
				Vector<Vector> row_data = clean_for_table(new_word_bag);
				Vector<String> headers = new Vector<String>();

				headers.add(WORDS_HEADER);
				headers.add(FREQUENCY_HEADER);

				dict_size.setText(Integer.toString(new_word_bag.get_word_bag().size()));
				total_word_count.setText(String.valueOf(new_word_bag.get_word_count()));

				JTable word_table = new JTable(row_data, headers);
				JScrollPane scrollable_table = new JScrollPane(word_table);

				words_and_counts.add(scrollable_table);
				setSize(new Dimension(WINDOW_WIDTH, WINDOW_LENGTH));
				words_and_counts.setVisible(true);

			}
		});

		main.add(file_selection);
		main.add(word_bag_details);
		main.add(words_and_counts);

		add(main);

		pack();
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	public Vector<Vector> clean_for_table(BagOfWords bag) {

		Vector<Vector> master_vector = new Vector<Vector>();
		LinkedHashMap<String, Float> the_bag = bag.get_word_bag();
		ArrayList<String> words = new ArrayList<String>(the_bag.keySet());

		for (String word : words) {

			Vector<Vector> values_vec = new Vector<Vector>();
			Vector<String> word_key = new Vector<String>();
			Vector<Float> frequency_vec = new Vector<Float>();

			float frequency = the_bag.get(word);

			word_key.add(word);
			frequency_vec.add(frequency);

			values_vec.add(word_key);
			values_vec.add(frequency_vec);

			master_vector.add(values_vec);

		}

		return master_vector;

	}

}