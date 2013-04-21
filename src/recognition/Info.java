package recognition;

public class Info {
	private final int status;
	private final String text;
	private final Double confidence;
	
	public Info(String Text, int Status, Double Confidence) {
		text = Text;
		status = Status;
		confidence = Confidence;
	}

	public int getStatus() {
		return status;
	}

	public String getText() {
		return text;
	}

	public Double getConfidence() {
		return confidence;
	}
	
	public String toString() {
		return "text="+text+";status="+status+";confidence="+confidence;
	}
	
}
