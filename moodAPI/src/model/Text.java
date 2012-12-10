package model;

public class Text {

	private double polarityIndex;
	private double energyIndex;
	private String mood;	
	private String text;
	
	public Text(){
		this.polarityIndex = 0;
		this.energyIndex = 0;
		this.mood = "";
		this.text = "";
	}
	
	public Text (double polarityIndex, double energyIndex, String mood, String text) {
		this.polarityIndex = polarityIndex;
		this.energyIndex = energyIndex;
		this.mood = mood;
		this.text = text;
	}
			
	public String toJSON() {
		return "{\"polarityIndex\":\"" + this.polarityIndex + "\"," + 
				"\"energyIndex\":\"" + this.energyIndex + "\"," +
				"\"mood\":\"" + this.mood + "\"," +
				"\"text\":\"" + this.text + "\"" +
				"}";
	}
		
	public double getPolarityIndex() {
		return polarityIndex;
	}
	
	public void setPolarityIndex(double polarityIndex) {
		this.polarityIndex = polarityIndex;
	}
	
	public double getEnergyIndex() {
		return energyIndex;
	}
	
	public void setEnergyIndex(double energyIndex) {
		this.energyIndex = energyIndex;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText (String text) {
		this.text = text;
	}
}