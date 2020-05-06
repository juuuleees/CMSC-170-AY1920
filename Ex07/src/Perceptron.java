import java.util.LinkedList;
import java.lang.Math;

public class Perceptron {
	
	private Datapack initial_data;
	private LinkedList<LinkedList<Double>> row_dock;
	private LinkedList<Iteration> all_iterations = new LinkedList<Iteration>();
	private double t;
	private double r;

	// getters and setters
	public Datapack get_initial_data() { return this.initial_data; }
	public LinkedList<LinkedList<Double>> get_row_dock() { return this.row_dock; }
	public LinkedList<Iteration> get_all_iterations() { return this.all_iterations; }
	public double get_t() { return this.t; }
	public double get_r() { return this.r; }

	public void set_initial_data(Datapack id) { this.initial_data = id; }
	public void set_row_dock(LinkedList<LinkedList<Double>> rd) { this.row_dock = rd; }
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
	}

	public boolean check_consistency(Iteration curr_iteration) {

		LinkedList<Double> curr_weights = curr_iteration.rows.get(curr_iteration.rows.size()-1);
		int weight_index = 0;
		boolean is_consistent = true;
		for (LinkedList<Double> row : curr_iteration.rows.subList(1,(curr_iteration.rows.size()-1))) {

			for (double weight : row) {

				if (weight == row.get(weight_index)) {
					continue;
				} else { 
					is_consistent = false; 
					System.out.println("Weights did not converge.");
					break;
				}

			}

			if (is_consistent == false) { break; }

		}

		return is_consistent;

	}

	// methods
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

		// for (LinkedList l : all_iterations.get(0).rows) {
		// 	System.out.println(l);
		// }
		// System.out.println();

	}

	public void find_converged_weights() {

		LinkedList<Double> weight_list = new LinkedList<Double>();
		Iteration iteration_data = new Iteration();
		boolean converged = false;
		boolean first_row = true;
		double a = 0;
		int list_index = 0;
		int content_index = 0;
		int weights = this.initial_data.get_features() + 1;

		compute_first_iteration();

		Iteration curr_iteration = new Iteration(all_iterations.get(0));
		converged = check_consistency(curr_iteration);

		LinkedList<Double> prev_row = new LinkedList<Double>(curr_iteration.rows.get(curr_iteration.rows.size()-1));
		LinkedList<Double> current_weights = new LinkedList<Double>(prev_row.subList(weights,weights*2));

		// compute using the current row not the previous row jusqlordt
		while (converged == false) {

			for (LinkedList<Double> row : curr_iteration.rows) {

				double z = row.get(row.size()-1);
				double y = row.get(row.size()-2);
				System.out.println("current weights:" + current_weights);
				LinkedList<Double> updated_row = new LinkedList<Double>(row);
				System.out.println(row);

				// recompute a
				for (double cw : current_weights) {
					if (content_index != weights) {
						a = a + (cw * (updated_row.get(content_index)));
						content_index++;
					}
				}

				content_index = 0;

				updated_row.set((updated_row.size()-3),a);

				// update y
				if (a > t) {
					updated_row.set((updated_row.size()-2), 1d);
				} else {
					updated_row.set((updated_row.size()-2), 0d);
				}

				System.out.println("updating row...");
				if (first_row == true) {

					for (double w : current_weights) {
						if (content_index != weights) {
							updated_row.set((weights+content_index), w);
							content_index++;
						}
					}

					first_row = false;

				} else {

					for (double x_n : row) {
						if (content_index != weights) {
							System.out.println("current weight: " + current_weights.get(content_index));
							System.out.println("x_n: " + x_n);
							double adjusted_weight = current_weights.get(content_index) + ((r * x_n) * (z-y));
							System.out.println("adjusted weight: " + adjusted_weight);
							adjusted_weight = Math.floor(adjusted_weight * 100) / 100;
							updated_row.set((weights+content_index), adjusted_weight);
							current_weights.set(content_index, adjusted_weight);
							content_index++;
						}
					}

				}

				System.out.println("updated row: " + updated_row);
				System.out.println("updated weights: " + current_weights);

				content_index = 0;

				iteration_data.rows.add(updated_row);

				list_index++;
				content_index = 0;
				a = 0;

			}

			first_row = true;

			Iteration new_iteration = new Iteration(iteration_data);
			all_iterations.add(new_iteration);

			System.out.println("all iterations");
			for (Iteration i : all_iterations) {
				for (LinkedList l : i.rows) {
					System.out.println(l);
				}
				System.out.println();
			}

			break;
		}

	}

}