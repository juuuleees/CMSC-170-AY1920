public class Main {
	public static void main(String args[]) {
		System.out.println("Running K-means...");
		Datapack training_data = new Datapack();
		KMeansClassifier k_means = new KMeansClassifier(training_data);
		k_means.classify();
	}
}