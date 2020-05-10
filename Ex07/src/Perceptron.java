import java.util.LinkedList;
import java.lang.Math;
import java.lang.StringBuilder;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.File;

public class Perceptron {
	
	private Datapack initial_data;
	private LinkedList<Double> converged_weights = new LinkedList<Double>();
	private LinkedList<Iteration> all_iterations = new LinkedList<Iteration>();
	private double t;
	private double r;

	// getters and setters
	public Datapack get_initial_data() { return this.initial_data; }
	public LinkedList<Double> get_converged_weights() { return this.converged_weights; }
	public LinkedList<Iteration> get_all_iterations() { return this.all_iterations; }
	public double get_t() { return this.t; }
	public double get_r() { return this.r; }

	public void set_initial_data(Datapack id) { this.initial_data = id; }
	public void set_converged_weights(LinkedList<Double> cws) { this.converged_weights = cws; }
	public void	set_all_iterations(LinkedList<Iteration> summ_i) { this.all_iterations = summ_i; }
	public void set_t(double nt) { this.t = nt; }
	public void set_r(double nr) { this.r = nr; }

	// constructors
	public Perceptron() {}
	public Perceptron(Datapack first_data) {
		this.initial_data = first_data;
		this.t = first_data.get_threshold();
		this.r = first_data.get_learning_rate();
		find_converged_weights();
		write_results_to_file(all_iterations);
		System.out.println("Results in output.txt. Exiting...");
	}

	// methods

	/* Checks if the weights converge given an iteration. */
	public boolean check_consistency(Iteration curr_iteration) {

		int weight_index = 0;
		boolean is_consistent = true;
		LinkedList<Double> first_row = curr_iteration.rows.get(0);
		LinkedList<Double> sample_weights = new LinkedList<Double>(first_row.subList((initial_data.get_features()+1),(first_row.size()-3)));

		for (LinkedList<Double> row : curr_iteration.rows) {

			LinkedList<Double> weights = new LinkedList<Double>(row.subList((initial_data.get_features()+1),(row.size()-3)));

			for (double w : weights) {

				if (weight_index != weights.size()) {
					if (w != sample_weights.get(weight_index)) {
						is_consistent = false; 
						break;
					}
				}

				weight_index++;

			}

			weight_index = 0;

			if (is_consistent == false) { break; }
			else { set_converged_weights(weights); }

		}

		return is_consistent;

	}

	/* Computes the values for the first iteration. */
	public void compute_first_iteration() {

		double a = 0;
		int list_index = 0;
		int is_first = 0;
		int weights = this.initial_data.get_features() + 1;
		LinkedList<Double> weight_list = new LinkedList<Double>();
		Iteration iteration_data = new Iteration();

		for (LinkedList<Double> list : initial_data.get_training_data()) {

			LinkedList<Double> new_first_row = new LinkedList<Double>(list);
			int content_index = 0;
			if (is_first == 0) {
				while (weights != 0) {
					weight_list.add(0d);
					new_first_row.add((new_first_row.size()-1), 0d);
					weights--;
				}

				// compute a
				for (double w : weight_list) {
					if (content_index != (new_first_row.size()-1)){
						a = a + (w*content_index);
						content_index++;
					}
				}

				new_first_row.add((new_first_row.size()-1), a);

				if (a > t) {
					new_first_row.add((new_first_row.size()-1), 1d);
				} else {
					new_first_row.add((new_first_row.size()-1), 0d);
				}
				
				is_first++;
				a = 0;
				weights = this.initial_data.get_features() + 1;
				content_index = 0;
				iteration_data.rows.add(new_first_row);

			} else {

				LinkedList<Double> prev_row = iteration_data.rows.get(list_index);
				LinkedList<Double> current_weights = new LinkedList<Double>(prev_row.subList(weights, weights*2));
				LinkedList<Double> updated_row = new LinkedList<Double>(list);
				double z = prev_row.get(prev_row.size()-1);
				double y = prev_row.get(prev_row.size()-2);

				// update weights
				for (double x_n : prev_row.subList(0, weights)) {
					if (content_index != weights) {
						double adjusted_weight = current_weights.get(content_index) + ((r * x_n) * (z-y));
						adjusted_weight = Math.floor(adjusted_weight * 100) / 100;
						updated_row.add((weights+content_index), adjusted_weight);
						current_weights.set(content_index, adjusted_weight);
						content_index++;
					}
				}

				content_index = 0;

				// recompute a
				for (double cw : current_weights) {
					if (content_index != weights) {
						a = a + (cw * (updated_row.get(content_index)));
						content_index++;
					}
				}

				updated_row.add((updated_row.size()-1),a);

				// update y
				if (a > t) {
					updated_row.add((updated_row.size()-1), 1d);
				} else {
					updated_row.add((updated_row.size()-1), 0d);
				}

				iteration_data.rows.add(updated_row);

				list_index++;
				content_index = 0;
				a = 0;

			}

		}

		Iteration first_iteration = new Iteration(iteration_data);
		all_iterations.add(first_iteration);

	}

	/* Computes for the converged weights. */
	public void find_converged_weights() {

		LinkedList<Double> weight_list = new LinkedList<Double>();
		Iteration iteration_data = new Iteration();
		boolean converged = false;
		boolean first_row = true;
		double current_a = 0;
		int list_index = 0;
		int content_index = 0;
		int iteration_index = 0;
		int z_index = 0;
		int weights = this.initial_data.get_features() + 1;

		compute_first_iteration();

		Iteration curr_iteration = new Iteration(all_iterations.get(iteration_index));
		converged = check_consistency(curr_iteration);

		LinkedList<LinkedList<Double>> features = initial_data.get_weight_computation_values();
		LinkedList<Double> all_zs = initial_data.get_z_values();
		LinkedList<Double> prev_row = new LinkedList<Double>(curr_iteration.rows.get(curr_iteration.rows.size()-1));
		LinkedList<Double> current_weights = new LinkedList<Double>(prev_row.subList(weights,weights*2));

		while (converged == false) {

			curr_iteration = new Iteration();
			LinkedList<Double> new_row = new LinkedList<Double>();

			for (LinkedList<Double> value_row : features) {

				for (double v : value_row) {
					new_row.add(v);
				}

				for (double cw : current_weights) {
					new_row.add(cw);
				}

				// recompute a
				for (double cw : current_weights) {
					if (content_index != weights) {
						current_a = current_a + (cw * (new_row.get(content_index)));
						content_index++;
					}
				}

				new_row.add(current_a);
				content_index = 0;

				// update y
				if (current_a > t) {
					new_row.add(1d);
				} else {
					new_row.add(0d);
				}

				current_a = 0;
				new_row.add(all_zs.get(z_index));

				LinkedList<Double> new_i_row = new LinkedList<Double>(new_row);
				curr_iteration.rows.add(new_i_row);
				new_row.clear();

				double z = all_zs.get(z_index);
				double y = new_i_row.get(new_i_row.size()-2);

				for (double x_n : new_i_row) {
					if (content_index != weights) {
						double adjusted_weight = current_weights.get(content_index) + ((r * x_n) * (z-y));
						adjusted_weight = Math.floor(adjusted_weight * 100) / 100;
						current_weights.set(content_index, adjusted_weight);
						content_index++;
					}
				}

				content_index = 0;
				z_index++;

			}

			z_index = 0;

			all_iterations.add(curr_iteration);

			if (check_consistency(curr_iteration)) {
				break;
			}

		}

	}

	/* Standard write-results-to-file function. */
	public void write_results_to_file(LinkedList<Iteration> is) {

		StringBuilder th_temp = new StringBuilder();
		StringBuilder h_temp = new StringBuilder();
		int counter = 0;

		while (counter != initial_data.get_features()) {
			h_temp.append("x" + counter);
			th_temp.append(h_temp.toString() + "   ");
			h_temp.delete(0, h_temp.length());
			counter++;
		}

		counter = 0;

		th_temp.append("b ");

		while (counter != (initial_data.get_features()+1)) {
			if (counter < initial_data.get_features()) {
				h_temp.append("w" + counter);
				th_temp.append(h_temp.toString() + "   ");
				h_temp.delete(0, h_temp.length());
			} else {
				h_temp.append("wb   ");
				th_temp.append(h_temp.toString());
				h_temp.delete(0, h_temp.length());
			}
			counter++;
		}

		counter = 0;

		th_temp.append("a   y   z");

		String table_header = th_temp.toString();

		try {

			FileWriter write_to_file = new FileWriter(new File("output.txt"));
			PrintWriter print_to_file = new PrintWriter(write_to_file);

			for (Iteration i : is) {
				print_to_file.print(table_header + "\n");
				for (LinkedList<Double> row : i.rows) {
					for (double value : row) {
						print_to_file.print(value + "   ");
					}
					print_to_file.print("\n");
				}
				print_to_file.print("\n");
			}

			print_to_file.print("Final weights: " + get_converged_weights());

			print_to_file.close();

		} catch (Exception e) {
			System.out.println(e);
		}

	}

}