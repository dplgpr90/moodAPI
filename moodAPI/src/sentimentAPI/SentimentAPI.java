package sentimentAPI;

import Exception.APIErrorException;

public interface SentimentAPI {
	public double getPolarityIndex(String text) throws APIErrorException;
}
