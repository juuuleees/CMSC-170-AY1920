import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableModel;
import javax.swing.BoxLayout;
import javax.swing.JTextArea;
import javax.swing.Box;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Vector;
import java.io.File;

public class GUI extends JFrame {
	
	// private final JFileChooser input_chooser = new JFileChooser("input");
	private final JFileChooser data_chooser = new JFileChooser("data");
	private final int WINDOW_LENGTH = 500;
	private final int WINDOW_WIDTH = 800;
	private final int GRAPH_AREA = 300;
	private final String POINTS_HEADER = "Points";
	private final String CLASS_HEADER = "Class";
	// private FIle training_data;
	private KNN knn_classifier;

	// getters and setters
	public KNN get_knn_classifier() { return this.knn_classifier; }
	// public File get_training_data() { return this.training_data; }

	public void set_knn_classifier(KNN k_class) { this.knn_classifier = k_class; }
	// public void set_training_data(File td) { this.training_data = td; }

	// constructor
	public GUI() {}

	public void initialize() {

		// set up the needed panels
		JPanel main = new JPanel();
		JPanel graph_panel = new JPanel();
		JPanel in_out_panel = new JPanel();

		// set up graph panel contents
		JButton set_t_data = new JButton("Select training data");
		graph_panel.setLayout(new BoxLayout(graph_panel, BoxLayout.Y_AXIS));
		graph_panel.setPreferredSize(new Dimension(500,500));
		GraphPanel point_graph = new GraphPanel();

		graph_panel.add(Box.createRigidArea(new Dimension(0,20)));
		graph_panel.add(set_t_data);
		graph_panel.add(point_graph);
		graph_panel.add(Box.createRigidArea(new Dimension(0,20)));

		// set up I/O panel
		in_out_panel.setLayout(new BoxLayout(in_out_panel, BoxLayout.Y_AXIS));
		in_out_panel.setPreferredSize(new Dimension(300,500));
		in_out_panel.add(Box.createRigidArea(new Dimension(0,20)));
		JTextArea point_input = new JTextArea(5,5);
		JScrollPane input_text_area = new JScrollPane(point_input);
		// input_text_area.setSize(new Dimension(300, 50));
		JButton classify_inputs = new JButton("Classify");
		classify_inputs.setAlignmentX(in_out_panel.CENTER_ALIGNMENT);
		JTable point_to_class = new JTable();
		JScrollPane scrollable_outputs = new JScrollPane(point_to_class);
		scrollable_outputs.setPreferredSize(new Dimension(200, 50));
		
		// temporary settings for the table
		Vector<Vector> empty_table = new Vector<Vector>();
		Vector<String> headers = new Vector<String>();

		headers.add(POINTS_HEADER);
		headers.add(CLASS_HEADER);

		DefaultTableModel temp_table = new DefaultTableModel(empty_table, headers);
		point_to_class.setModel(temp_table);

		in_out_panel.add(input_text_area);
		in_out_panel.add(Box.createRigidArea(new Dimension(0,5)));
		in_out_panel.add(classify_inputs);
		in_out_panel.add(Box.createRigidArea(new Dimension(0,5)));
		in_out_panel.add(scrollable_outputs);
		in_out_panel.add(Box.createRigidArea(new Dimension(0,20)));

		// add them all to the main panel set up
		main.setLayout(new BoxLayout(main, BoxLayout.X_AXIS));
		main.add(Box.createRigidArea(new Dimension(20,0)));
		main.add(graph_panel);
		main.add(Box.createRigidArea(new Dimension(20,0)));
		main.add(in_out_panel);
		main.add(Box.createRigidArea(new Dimension(20,0)));

		add_actions(set_t_data, classify_inputs, point_input);

		add(main);
		setSize(WINDOW_WIDTH, WINDOW_LENGTH);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	public void add_actions(JButton data, JButton classifier, JTextArea input_area) {
		System.out.println("yes!");

		data.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int open_t_data = data_chooser.showOpenDialog(null);
				File training_data = null;

				if (open_t_data == JFileChooser.APPROVE_OPTION) {
					training_data = data_chooser.getSelectedFile();
					System.out.println("Training data accessed.");
				}

				knn_classifier = new KNN(training_data);
			}
		});

		classifier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String input_str = input_area.getText();
				String[] pieces = input_str.split(" ");
				Vector<Double> new_point = new Vector<Double>();

				for (String p : pieces) {
					System.out.println(p);
					double pt_element = Double.parseDouble(p);
					new_point.add(pt_element);
				}

				knn_classifier.run_knn_algorithm(new_point);

			}
		});
	}

}