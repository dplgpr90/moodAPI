package sentimentAPI.AlchemyImpl;

public class DocSentiment {
	String type;
	String score;
	String mixed;

	@Override
	public String toString() {
		String result = "\n";
		result += "\t\ttype: " + type + "\n";
		result += "\t\tscore: " + score + "\n";
		result += "\t\tmixed: " + mixed;
		return result;
	}
	
	String getType() {
		return type;
	}
	
	String getScore() {
		return score;
	}
	
	String getMixed() {
		return mixed;
	}
}
