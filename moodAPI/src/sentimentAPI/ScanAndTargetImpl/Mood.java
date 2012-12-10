package sentimentAPI.ScanAndTargetImpl;

class Mood {
	private Sentiment[] sentiment;

	@Override
	public String toString() {
		String result = "";
		for (int i = 0; i < sentiment.length; i++) {
			result += "\n\tsentiment: " + sentiment[i].toString();
		}
		return result;
	}
	
	Sentiment[] getSentiment() {
		return sentiment;
	}
}
