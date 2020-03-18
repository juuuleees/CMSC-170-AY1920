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
import java.util.ArrayList;
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

		add_data_actions(set_t_data,point_graph);
		add_classify_actions(classify_inputs,point_input,point_to_class,headers,point_graph);

		add(main);
		setSize(WINDOW_WIDTH, WINDOW_LENGTH);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	public void generate_new_table(KNN update, JTable knn_table, Vector<String> header_vec) {

		// ArrayList<Vector<Double>> updated_points = new ArrayList<Vector<Double>>(update.get_points().keySet());
		// ArrayList<Integer> updated_classes = new ArrayList<Integer>(update.get_points().values());
		Vector<Vector> updated_table = new Vector<Vector>();

		for (Vector<Double> key : update.get_points().keySet()) {

			Vector<Vector> values_vec = new Vector<Vector>();
			Vector<Vector<Double>> points_vec = new Vector<Vector<Double>>();
			Vector<Integer> classes_vec = new Vector<Integer>();

			points_vec.add(key);
			classes_vec.add(update.get_points().get(key));

			values_vec.add(points_vec);
			values_vec.add(classes_vec);

			updated_table.add(values_vec);

		}

		DefaultTableModel new_table = new DefaultTableModel(updated_table, header_vec);
		knn_table.setModel(new_table);

	}

	public void add_data_actions(JButton data, GraphPanel gp) {
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
				gp.place_points(new Vector<Vector<Double>>(knn_classifier.get_points().keySet()));
			}
		});
	}

	public void add_classify_actions(JButton classifier, JTextArea input_area, JTable data_table, Vector<String> header_vec, GraphPanel gp) {
		classifier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String input_str = input_area.getText();
				String[] pieces = input_str.split("\\n");
				Vector<Double> temp_point = new Vector<Double>();
				ArrayList<Vector<Double>> given_points = new ArrayList<Vector<Double>>();

				for (String p : pieces) {
					System.out.println(p);
					String[] sub_pieces = p.split(" ");
					for (String sp : sub_pieces) {
						double pt_element = Double.parseDouble(sp);
						temp_point.add(pt_element);
					}
					Vector<Double> new_point = new Vector<Double>(temp_point);
					given_points.add(new_point);
					temp_point.clear();
				}

				for (Vector<Double> np : given_points) {
					System.out.println(np);
					knn_classifier.run_knn_algorithm(np);
					generate_new_table(knn_classifier, data_table, header_vec);
				}

				gp.place_points(new Vector<Vector<Double>>(knn_classifier.get_points().keySet()));

			}
		});
	}

}