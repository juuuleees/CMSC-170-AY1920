import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JFrame;
import javax.swing.JFileChooser;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.BoxLayout;
import javax.swing.table.DefaultTableModel;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Vector;
import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.io.File;

public class GUI extends JFrame {
	
	private final JFileChooser file_chooser = new JFileChooser("data");
	private final int WINDOW_WIDTH = 1700;
	private final int WINDOW_LENGTH = 600;
	private final int TABLE_WIDTH = 250;
	private final int TABLE_LENGTH = 400;
	private final String WORDS_HEADER = "Word";
	private final String FREQUENCY_HEADER = "Frequency";
	private final String FILENAME_HEADER = "Filename";
	private final String CLASS_HEADER = "Class";
	private final String P_SPAM_HEADER = "P(Spam)";
	private SpamBag new_spam;
	private HamBag new_ham;
	private Classifier classifier;
	private Vector cleaned_bag;
	private File chosen_folder;
	private int bag_size;
	private int k_input;

	// getters
	public SpamBag get_new_spam_bag() { return this.new_spam; }
	public HamBag get_new_ham_bag() { return this.new_ham; }
	public Classifier get_classifier() { return this.classifier; }
	public Vector get_cleaned_bag() { return this.cleaned_bag; }
	public File get_chosen_folder() { return this.chosen_folder; }
	public int get_bag_size() { return this.bag_size; }
	public int get_k_input() { return this.k_input; }

	// setters
	public void set_new_spam_bag(SpamBag sb) { this.new_spam = sb; }
	public void set_new_ham_bag(HamBag hb) { this.new_ham = hb; }
	public void set_classifier(Classifier c) { this.classifier = c; }
	public void set_cleaned_bag(Vector cb) { this.cleaned_bag = cb; }
	public void set_chosen_folder(File cf) { this.chosen_folder = cf; }
	public void set_bag_size(int s) { this.bag_size = s; }
	public void set_k_input(int k) { this.k_input = k; }

	// constructor
	public GUI() {}

	// methods
	public void graphics_setup() {

		JPanel main = new JPanel();
		main.setLayout(new GridLayout(1,3));

		JPanel spam_side = new JPanel();
		JPanel ham_side = new JPanel();
		JPanel classifier_panel = new JPanel();

		// for the spam side
		JButton sfolder_select = new JButton("Select Spam Folder");

		JPanel spam_details = new JPanel();
		spam_details.setLayout(new BoxLayout(spam_details, BoxLayout.PAGE_AXIS));
		spam_details.setAlignmentX(spam_details.CENTER_ALIGNMENT);

		JPanel spam_labels = new JPanel();
		spam_labels.setLayout(new GridLayout(1,2));

		JLabel total_spam_label = new JLabel("Total Words in Spam: ");
		JTextField spam_bag_size = new JTextField();

		JTable spam_table = new JTable();
		JScrollPane scrollable_spam = new JScrollPane(spam_table);
		scrollable_spam.setSize(TABLE_WIDTH, TABLE_LENGTH);

		spam_labels.add(total_spam_label);
		spam_labels.add(spam_bag_size);

		spam_details.add(scrollable_spam);
		spam_details.add(spam_labels);

		// spam_details.setVisible(false);

		sfolder_select.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				file_chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int opening = file_chooser.showOpenDialog(null);

				if (opening == JFileChooser.APPROVE_OPTION) {
					chosen_folder = file_chooser.getSelectedFile();
					System.out.println("Spam folder selected.");
				}

				new_spam = new SpamBag(chosen_folder);
				new_spam.list_spam_data();

				String spam_total = String.valueOf(new_spam.get_word_count());
				spam_bag_size.setText(spam_total);
				spam_bag_size.setEditable(false);

				// for the table
				Vector<Vector> spam_row_data = clean_for_table(new_spam);
				Vector<String> spam_headers = new Vector<String>();

				spam_headers.add(WORDS_HEADER);
				spam_headers.add(FREQUENCY_HEADER);

				DefaultTableModel spam_table_data = new DefaultTableModel(spam_row_data, spam_headers);
				spam_table.setModel(spam_table_data);

				// spam_details.setVisible(true);

			}
		});

		spam_side.add(sfolder_select);
		spam_side.add(spam_details);

		// for the ham side
		JButton hfolder_select = new JButton("Select Ham Folder");
		JLabel total_ham_label = new JLabel("Total Words in Ham: ");
		JTextField ham_bag_size = new JTextField();

		JPanel ham_details = new JPanel();
		ham_details.setLayout(new BoxLayout(ham_details, BoxLayout.PAGE_AXIS));
		ham_details.setAlignmentX(ham_details.CENTER_ALIGNMENT);

		JPanel ham_labels = new JPanel();
		ham_labels.setLayout(new GridLayout(1,2));

		JTable ham_table = new JTable();
		JScrollPane scrollable_ham = new JScrollPane(ham_table);
		scrollable_ham.setSize(TABLE_WIDTH, TABLE_LENGTH);

		ham_labels.add(total_ham_label);
		ham_labels.add(ham_bag_size);

		ham_details.add(scrollable_ham);
		ham_details.add(ham_labels);

		// ham_details.setVisible(false);

		hfolder_select.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				file_chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int opening = file_chooser.showOpenDialog(null);

				if (opening == JFileChooser.APPROVE_OPTION) {
					chosen_folder = file_chooser.getSelectedFile();
					System.out.println("Ham folder selected.");
				}

				new_ham = new HamBag(chosen_folder);
				new_ham.list_ham_data();

				String ham_total = String.valueOf(new_ham.get_word_count());
				ham_bag_size.setText(ham_total);
				ham_bag_size.setEditable(false);

				// for the table
				Vector<Vector> ham_row_data = clean_for_table(new_ham);
				Vector<String> ham_headers = new Vector<String>();

				ham_headers.add(WORDS_HEADER);
				ham_headers.add(FREQUENCY_HEADER);

				DefaultTableModel ham_table_data = new DefaultTableModel(ham_row_data, ham_headers);
				ham_table.setModel(ham_table_data);

				// ham_details.setVisible(true);

			}
		});

		ham_side.add(hfolder_select);
		ham_side.add(ham_details);

		// for classifier
		classifier_panel.setLayout(new BoxLayout(classifier_panel, BoxLayout.PAGE_AXIS));
		JPanel classifier_details = new JPanel();
		classifier_details.setLayout(new GridLayout(3,2));
		classifier_details.setAlignmentX(classifier_details.CENTER_ALIGNMENT);
		JLabel k_label = new JLabel("Enter k value: ");
		JTextField k_textbox = new JTextField();
		JLabel dict_label = new JLabel("Dictionary Size: ");
		JLabel word_count_label = new JLabel("Total Words: ");
		JTextField dict_size = new JTextField();
		JTextField total_word_count = new JTextField();

		JTable classifier_table = new JTable();
		JScrollPane scrollable_classifier = new JScrollPane(classifier_table);
		scrollable_classifier.setSize(TABLE_WIDTH, TABLE_LENGTH);

		JButton cfolder_select = new JButton("Select Classify Folder");
		cfolder_select.setAlignmentX(classifier_details.CENTER_ALIGNMENT);
		cfolder_select.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				file_chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int opening = file_chooser.showOpenDialog(null);

				if (opening == JFileChooser.APPROVE_OPTION) {
					chosen_folder = file_chooser.getSelectedFile();
					System.out.println("Classify folder selected.");
				}
			}
		});

		JButton filter_button = new JButton("Filter");
		filter_button.setAlignmentX(classifier_details.CENTER_ALIGNMENT);
		filter_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// classifier.word_given_class(new_ham);
				if (!k_textbox.getText().isEmpty()) {
					System.out.println(Integer.parseInt(k_textbox.getText()));
					set_k_input(Integer.parseInt(k_textbox.getText()));

					classifier = new Classifier(chosen_folder, 
					new_spam.get_word_count(), new_ham.get_word_count(), get_k_input());
				} else {
					classifier = new Classifier(chosen_folder, 
					new_spam.get_word_count(), new_ham.get_word_count());
				}
				// classifier.clean_classifier_data();
				// classifier.word_given_class(new_spam);
				// for the table
				classifier.list_classifier_data();
				Vector<Vector> classifier_row_data = clean_for_table(classifier);
				Vector<String> classifier_headers = new Vector<String>();

				classifier_headers.add(FILENAME_HEADER);
				classifier_headers.add(CLASS_HEADER);
				classifier_headers.add(P_SPAM_HEADER);

				DefaultTableModel classifier_table_data = new DefaultTableModel(classifier_row_data, classifier_headers);
				classifier_table.setModel(classifier_table_data);

				dict_size.setText(String.valueOf(classifier.get_dict_size()));
				total_word_count.setText(String.valueOf(classifier.get_word_count()));

				classifier.classify_data(new_ham, new_spam);
			}
		});

		classifier_details.add(k_label);
		classifier_details.add(k_textbox);
		classifier_details.add(dict_label);
		classifier_details.add(dict_size);
		classifier_details.add(word_count_label);
		classifier_details.add(total_word_count);

		classifier_panel.add(classifier_details);
		classifier_panel.add(cfolder_select);
		classifier_panel.add(filter_button);
		classifier_panel.add(scrollable_classifier);

		main.add(spam_side);
		main.add(ham_side);
		main.add(classifier_panel);

		add(main);

		// pack();
		setSize(WINDOW_WIDTH, WINDOW_LENGTH);
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