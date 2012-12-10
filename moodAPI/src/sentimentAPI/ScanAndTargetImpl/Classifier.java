package sentimentAPI.ScanAndTargetImpl;

class Classifier {
	String polarity;

	@Override
	public String toString() {
		return "\n\t\t\t" + "polarity: " + polarity;
	}
	
	String getPolarity() {
		return polarity;
	}
}
