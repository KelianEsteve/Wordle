package interface_wordle;

public class Score {
	private int baseScore;
	private int score;
	private int malus = 0;
	
	public int getScore() {
		return score;
	}
	
	public Score() {
		baseScore = 1000;
		score = 1000;	
	}
	
	
	public void updateMalus(Timer a) {
		malus = 5 * (int)a.getElapsedTime();
	}
	
	public void updateScore(Timer a) {
		this.updateMalus(a);
		if(baseScore - malus > 0)
			score = baseScore - malus;
	}	

	
	public void updateScoreEndGame(int turnNumber, boolean winStatus) {
		int a = 6 - turnNumber;
		if(a == 5) a = 4;
		if(a == 0) a = 1;
		if(winStatus) {
			score += a*1000;
			baseScore = score;
			malus = 0;
		}
		else{
			score = baseScore;
		}
		
	}
}