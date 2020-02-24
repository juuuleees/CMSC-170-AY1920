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
import java.io.File;
// remember to specify which things you're importing

public class GUI extends JFrame {
	
	private final int WINDOW_WIDTH = 300;
	private final int WINDOW_LENGTH = 450;
	private final JFileChooser file_chooser = new JFileChooser("inputs");
	private File chosen_file;

	// getters
	public File get_chosen_file() { return this.chosen_file; }

	// setters
	public void set_chosen_file(File cf) { this.chosen_file = cf; }

	// constructor
	public GUI() {}

	// methods
	public void graphics_setup() {

		setSize(new Dimension(WINDOW_WIDTH, WINDOW_LENGTH));

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

				JTable word_table = new JTable(2,2);
				JScrollPane scrollable_table = new JScrollPane(word_table);

				words_and_counts.add(scrollable_table);
				words_and_counts.setVisible(true);

			}
		});

		main.add(file_selection);
		main.add(word_bag_details);
		main.add(words_and_counts);

		add(main);

		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

}