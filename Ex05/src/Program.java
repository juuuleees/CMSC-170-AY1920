import java.util.Scanner;
import java.lang.StringBuilder;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.File;

public class Program {

	private GUI ui = new GUI();
	private BagOfWords bag = new BagOfWords();

	// methods
	public void run() {
		ui.graphics_setup();
	}

}