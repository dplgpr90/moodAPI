package sentimentAPI.AlchemyImpl;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import com.google.gson.Gson;

import sentimentAPI.SentimentAPI;
import sentimentAPI.AlchemyImpl.AlchemyResponse;
import Exception.APIErrorException;

public class Alchemy implements SentimentAPI {

	public double getPolarityIndex(String text) throws APIErrorException {

		// API settings
		String APIkey = "your_api_key";
		String outputMode = "json";
		String showSourceText = "1";
		String APIresponse = "";
		
		// Removes not allowed chars
		text = text.replaceAll("[ % \\[ \\] \\ / ]", " ");

		// Encodes text
		try {
			text = URLEncoder.encode(text, "ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// Builds request
		String request = "http://access.alchemyapi.com/calls/text/TextGetTextSentiment";
		String urlParameters = "apikey=" + APIkey + "&text=" + text + "&outputMode=" + outputMode + "&showSourceText=" + showSourceText;

		URL url;
		try {
			url = new URL(request);

			HttpURLConnection connection;
			connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			connection.setRequestProperty("charset", "utf-8");
			connection.setRequestProperty("Content-Length",
					"" + Integer.toString(urlParameters.getBytes().length));
			connection.setUseCaches(false);

			DataOutputStream wr = new DataOutputStream(
					connection.getOutputStream());

			wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();

			DataInputStream inStream = new DataInputStream(
					connection.getInputStream());
			String buffer;
			while ((buffer = inStream.readLine()) != null) {
				APIresponse += buffer;
			}

			// Closes I/O streams
			inStream.close();
			connection.disconnect();

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Adapts response to JSON format 
		Gson gson = new Gson();
		AlchemyResponse response = gson.fromJson(APIresponse,AlchemyResponse.class);
		
		// Returned value 
		double value = 0; 		
		if(response.getStatus().equals("OK")) { 
			// API call successful
			String score = response.getDocSentiment().getScore();
			if(score == null){
				value = 0; // default value returned if the API does not detect any sentiment
			} else {
				value = Double.parseDouble(score); 
			}
		} else {
			// Alchemy API call fails 
			throw new APIErrorException("An error occurs during the computation of polarity index (external call error: " + response.getStatusInfo() + ")"); 
		}		
		return value;
	}
}
