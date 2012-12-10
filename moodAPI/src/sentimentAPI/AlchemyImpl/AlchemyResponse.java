package sentimentAPI.AlchemyImpl;

class AlchemyResponse {
	private String status;
	private String usage;
	private String url;
	private String language;
	private String text;
	private DocSentiment docSentiment;
	private String statusInfo;

	@Override
	public String toString() {
		String result = "";
		result += "text: \"" + text + "\"\n";
		result += "language: " + language + "\n";
		result += "url: " + url + "\n";
		result += "usage: " + usage + "\n";
		result += "status: " + status + "\n";
		result += "docSentiment: " + docSentiment.toString() + "\n";
		result += "statusInfo: " + statusInfo + "\n";
		return result;
	}

	String getStatus() {
		return status;
	}

	String getUsage() {
		return usage;
	}

	String getUrl() {
		return url;
	}

	String getLanguage() {
		return language;
	}

	String getText() {
		return text;
	}

	DocSentiment getDocSentiment() {
		return docSentiment;
	}

	String getStatusInfo() {
		return statusInfo;
	}
}
