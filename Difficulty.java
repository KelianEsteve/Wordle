package interface_wordle;

public class Difficulty {
    String difficulty; //CLASSIC or HARD

    public Difficulty(String difficulty) {
        this.difficulty = difficulty;
    }
    
    public String getDifficulty() {
    	return difficulty;
    }
}
