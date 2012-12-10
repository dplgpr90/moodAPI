package sentimentAPI.ScanAndTargetImpl;

class Sentiment {
	Classifier classifier;
	float rating;
	int accuracy;

	@Override
	public String toString() {
		String result = "\n\t\t";
		result += "classifier: " + classifier.toString() + "\n";
		result += "\t\trating: " + rating + "\n";
		result += "\t\taccuracy: " + accuracy + "\n";
		return result;
	}
	
	int getAccuracy() {
		return accuracy;
	}
	
	Classifier getClassifier() {
		return classifier;
	}
	
	float getRating() {
		return rating;
	}
}
