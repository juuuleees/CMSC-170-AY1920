public class Program {
	
	private GUI prog_interface = new GUI();

	// getters and setters
	public GUI get_prog_interface() { return this.prog_interface; }

	public void set_prog_interface(GUI pi) { this.prog_interface = pi; }
	
	// constructor
	public Program() {}

	public void run() {
		prog_interface.initialize();
	}

}