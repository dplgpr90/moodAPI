package sentimentAPI.ScanAndTargetImpl;

class ScanAndTargetResponse {
	private String text;
	private int completed_at;
	private float duration;
	private boolean success;
	private int count;
	private Mood generic;

	@Override
	public String toString() {
		String result = "";
		result += "text: \"" + text + "\"\n";
		result += "completed_at: " + completed_at + "\n";
		result += "duration: " + duration + "\n";
		result += "success: " + success + "\n";
		result += "count: " + count + "\n";
		result += "generic: " + generic.toString() + "\n";
		return result;
	}
	
	int getCompleted_at() {
		return completed_at;
	}
	
	int getCount() {
		return count;
	}
	
	float getDuration() {
		return duration;
	}
	
	Mood getGeneric() {
		return generic;
	}
	
	String getText() {
		return text;
	}
	
	boolean isSuccess() {
		return success;
	}
}
