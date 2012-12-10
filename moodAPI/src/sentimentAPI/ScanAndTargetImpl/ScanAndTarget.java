package sentimentAPI.ScanAndTargetImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import sentimentAPI.SentimentAPI;

import Exception.APIErrorException;

import com.google.gson.Gson;

public class ScanAndTarget implements SentimentAPI {

	public double getPolarityIndex(String text) throws APIErrorException {
				
		// API settings
		String APIkey = "your_api_key";
		String language = "eng";
		String categories = "sentiment";

		// Encodes text
		try {
			text = URLEncoder.encode(text,"ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} 
		
		// Builds request and response
		String APIrequest = "http://api.scanandtarget.com/api/1/scan.json?key="
				+ APIkey + "&lang=" + language + "&categories=" + categories
				+ "&text=" + text;
		String APIresponse = "";

		// Executes request
		URL url;
		try {
			url = new URL(APIrequest);
			BufferedReader responseReader = new BufferedReader(
					new InputStreamReader(url.openStream()));
			String line;
			while ((line = responseReader.readLine()) != null) {
				APIresponse += line;
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Adapts response to JSON format
		Gson gson = new Gson();
		ScanAndTargetResponse response = gson.fromJson(APIresponse,	ScanAndTargetResponse.class);
		
		// Returns result (value)
		double value = 0; // default value returned if the API does not detect any sentiment		
		if (response.isSuccess()) {
			// API call successful
			if (response.getCount() != 0) {
				// Mood detected in the text
				Sentiment[] sentiment = response.getGeneric().getSentiment();
				for (int i = 0; i < sentiment.length; i++) {
					Classifier classifier = sentiment[i].getClassifier();
					// Calculates value according to polarity
					if (classifier.getPolarity().equals("positive")) {
						value += sentiment[i].getRating();
					} else if (classifier.getPolarity().equals("negative")) {
						value -= sentiment[i].getRating();
					}
				}
			}
		} else {
			// ScanAndTarget API call fails
			throw new APIErrorException("An error occurs during the computation of positivity index (external call)");
		}				
		return value;
	}
}
