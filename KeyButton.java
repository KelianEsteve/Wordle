package interface_wordle;

public class KeyButton {
	private int helpInfo = 0;
	private char key;
	
	public KeyButton(char key) {
		this.key = key;
	}
	
	public char getKey() {
		return key;
	}
	
	public int getHelpInfo() {
		return helpInfo;
	}
	
	public void setHelpInfo(int value){
		this.helpInfo = value;
	}
}
