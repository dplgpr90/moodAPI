package resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import Exception.APIErrorException;
import energyAPIImpl.*;
import sentimentAPI.*;
import sentimentAPI.AlchemyImpl.Alchemy;
import model.Text;

@Path("/{text}")
public class TextResource {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String analyzeMood(@PathParam("text") String text) {
		String result = "";
		try {
			result = this.getTextObject(text).toJSON();
		} catch (APIErrorException exc) {
			result = "{\"error\":{\"text\":\"" + exc.getMessage() + "\"}}";
			exc.printStackTrace();
		}
		return result;
	}

	private Text getTextObject(String originalText) throws APIErrorException{
		// Deletes blank spaces at the beginning and at the end of the text
		String text = originalText.trim();	
		
		// Gets energy index from text
		double energyIndex = getEnergyIndex(text);
		// Sets right interval of energy index
		energyIndex += 0.1; // Origin of system
		energyIndex += -1; // To obtain interval between -1 and 1
		
		double polarityIndex = 0;
		try{
			// Gets polarity index from text
			polarityIndex = getPolarityIndex(replaceRepeatedLetter(2,text));
		} catch (APIErrorException exc){
			polarityIndex = getPolarityIndex(replaceRepeatedLetter(1,text));
		}

		// Gets mood from polarity and energy indices
		String mood = getMood(polarityIndex, energyIndex);

		return new Text(polarityIndex, energyIndex, mood, originalText);
	}
	
	// Replaces occurrence of a repeated letter (more than one) in the text
	// with only one letter.
	private String replaceRepeatedLetter(int n, String text) {
		for (int i = 0; i < text.length();) {
			int count = 0;
			for (int j = i; j < text.length(); j++) {
				if (text.charAt(j) == text.charAt(i)) {
					count++;
					if (count > n) {
						String temp = text.substring(0, j) + ':';
						if (j + 1 < text.length()) {
							temp += text.substring(j + 1);
						}
						text = temp;
					}
				} else {
					break;
				}
			}
			i += count;
		}
		text = text.replace(":", "");
		return text;
	}

	// Gets polarity index from text using Alchemy API
	private double getPolarityIndex(String text) throws APIErrorException {
		SentimentAPI api = new Alchemy(); // or new ScanAndTarget();
		return api.getPolarityIndex(text);
	}

	// Gets energy index from text
	private double getEnergyIndex(String text) {
		EnergyIndex energy = new EnergyIndex();
		return energy.getEnergyIndex(text);
	}

	// Gets mood from polarity and energy indices
	private String getMood(double polarityIndex, double energyIndex) {
		if (polarityIndex >= 0 && energyIndex >= 0) {
			return "active";
		}
		if (polarityIndex >= 0 && energyIndex < 0) {
			return "calm";
		}
		if (polarityIndex < 0 && energyIndex >= 0) {
			return "anxiety";
		}
		// polarityIndex < 0 && energyIndex < 0
		return "depression";
	}
}