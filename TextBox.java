package interface_wordle;

import javafx.scene.control.TextField;

/*
 * 
 * 			THE GOAL OF THIS CLASS IS TO BE THE BOXES OF THE GRID 
 * 			
 * 			EACH BOXES CONTAIN A LETER OR NOT AND THE INFORMATION ON THE PLACEMENT OF THE LETTER
 * 			(RIGHT/WRONG PLACE OR NOT IN THE WORD)
 * 
 * 
 */

public class TextBox extends TextField {

	private int helpInfo;		//0 = deflault + not in the word, 1 = wrong place, 2 = right place
	private boolean valid;
	private boolean free;
	
	public TextBox() {
		this.helpInfo = 0;
		this.valid = false;
		this.free = true;
	}

	public int getHelpInfo() {
		return helpInfo;
	}

	public void setHelpInfo(int helpInfo) {
		this.helpInfo = helpInfo;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValide(boolean valid) {
		this.valid = valid;
	}

	public boolean isFree() {
		return free;
	}

	public void setLibre(boolean free) {
		this.free = free;
	}
	
	
}

