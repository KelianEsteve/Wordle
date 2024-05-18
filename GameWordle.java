package interface_wordle;



import java.util.List;

import javafx.animation.RotateTransition;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class GameWordle {
	private Dictionary dico;
	private String word;
	private String wordsend;
	private char[] wordChar;
	private int wordSize;
	private List<String> hintList;
	private String hint;
	private String hint1;
	private String hint2;
	private String hint3;
	private Difficulty difficulty;
	
	//status du jeu
	private boolean gameStatus = false;
	private boolean winStatus = false;
	private int turnNumber = 0;
	
	//clavier et grille du jeu
	private WordleGrid wordleGrid;
	private VirtualKeyboard virtualKeyboard;
	
	//timer et status(lancé ou non) du timer
	private Timer Timer;
	private boolean TimerStatus = false;
	
	//score
	private Score score;
	private int winStreak = 0;
	
	public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
        resetGame();
    }
	
	public String getDifficulty() {
		return difficulty.getDifficulty();
	}
	

	public GameWordle(String difficulty) {	
		this.dico = new Dictionary();
		this.difficulty = new Difficulty(difficulty);
		this.wordsend = dico.randomWord(this.difficulty.getDifficulty());
		this.word = this.wordsend.toUpperCase();
		System.out.println(this.word);
		this.wordChar = word.toCharArray();
		this.wordSize = word.length();

		try{
			this.hintList = dico.sendRandomWord(this.wordsend);
			this.hint = this.hintList.get(0);
			this.hint1 = this.hintList.get(1);
			this.hint2 = this.hintList.get(2);
			this.hint3 = this.hintList.get(3);
		}
		catch(Exception e){
			this.hint = "Le serveur python n'est pas connecté";
			this.hint1 = "Le serveur python n'est pas connecté";
			this.hint2 = "Le serveur python n'est pas connecté";
			this.hint3 = "Le serveur python n'est pas connecté";
		}

		
		System.out.println(this.hint);

		Timer = new Timer();
		score = new Score();
		//initialisation du clavier et de la grille
		this.wordleGrid = new WordleGrid(this.wordSize);
		this.virtualKeyboard = new VirtualKeyboard();
	}
	
	public int getWinStreak() {
		return this.winStreak;
	}
	
	public int getScore() {
		return score.getScore();
	}
	
	public Timer getTimer() {
		return Timer;
	}

	public int getWordSize() {
		return wordSize;
	}
	
	public boolean getGameStatus() {
		return this.gameStatus;
	}
	
	public boolean getWinStatus() {
		return this.winStatus;
	}
	
	public void setTimerStatus(boolean a) {
		this.TimerStatus = a;
	}
	
	public int getTurnNumber() {
		return turnNumber;
	}
	
	//getters clavier et grille
	public WordleGrid getGrid() {
		return this.wordleGrid;
	}
	
	public VirtualKeyboard getVirtualKeyboard() {
		return this.virtualKeyboard;
	}
	
	public String getHint(int i) {
		if(i<5) {
			if(i==1)return this.hint;
			else if(i==2)return this.hint1;
			else if(i==3)return this.hint2;
			else if(i==4)return this.hint3;
		}
		
		return null;
	}
	

	//fonction qui teste le mot en input et met a jour les infoAides des cases de la grille et qui met a jour le status de la partie
	public void testingWord(char[] input, int line) {
		int[] helpInfoOfTarget = new int[wordSize];
		for (int j = 0; j < wordSize; j++) {
				if (input[j] == wordChar[j]) {
					
					this.wordleGrid.getTextBox(line, j).setHelpInfo(2);
					helpInfoOfTarget[j] = 2;
					updateKeyboardKey(input[j],2);
				}
		}
		for (int j = 0; j < wordSize; j++) {
			for (int k = 0; k < wordSize; k++) {
				if (input[k] == wordChar[j] && j != k && this.wordleGrid.getTextBox(line, k).getHelpInfo() != 2 && this.wordleGrid.getTextBox(line, j).getHelpInfo() != 2 && helpInfoOfTarget[j] != 1) {
					
					this.wordleGrid.getTextBox(line, k).setHelpInfo(1);
					helpInfoOfTarget[j] = 1;
					updateKeyboardKey(input[k],1);
				}
			}
		}
		int a = 0;
		for (int i = 0; i < wordSize; i++) {
			if(this.wordleGrid.getTextBox(line, i).getHelpInfo() != 2 && this.wordleGrid.getTextBox(line, i).getHelpInfo() != 1) {
				this.wordleGrid.getTextBox(line, i).setHelpInfo(3);
				updateKeyboardKey(input[i],3);
			}
			if (this.wordleGrid.getTextBox(line, i).getHelpInfo() == 2) {
				a++;
			}
			if (a == wordSize) {
				gameStatus = true;
				winStatus = true;
				winStreak++;
			}
		}
		a = 0;
		if(turnNumber == 5)
			gameStatus = true;
	}

	
	//fonction qui verifie si un string est une lettre de l'alphabet
	public static boolean isLetter(String str) {
		   	// Vérifie si la chaîne a une longueur de 1 et si son unique caractère est une lettre minuscule
			return str.length() == 1 && str.matches("[a-z]");
	}

	
	// fonction interaction clavier
	public void keyboardInteraction(String letter) {
		//on ne permet les interactions clavier que si la partie n'est pas finie
		if (!this.gameStatus) {
			//si une lettre est tappee :
			if (isLetter(letter))
			this.wordleGrid.write(letter);
				
			//si on veut effacer :
			else if (letter.equals("<") || letter.equals("<BACKSPACE>"))
				this.wordleGrid.erase();
				
			//si on veut valider un ligne
			else if (letter.equals(">") || letter.equals("<ENTREE>")) {

				String mot = this.wordleGrid.verifLine();
				if (!mot.equals("")) {
						
					//si le mot est valide alors on le teste et on lance le timer
					System.out.println("mot "+mot+" valide !");
					testingWord(mot.toCharArray(), this.wordleGrid.getLineToTest());
					animateLine(wordleGrid.getLineToTest());
						
					//on lance le timer apres le premier essai
					if(this.TimerStatus != true) {
						this.Timer.start();
						TimerStatus = true;
					}
					
					turnNumber++;
					
					//annonce si la partie est finie et pause le timer
					if (this.gameStatus || turnNumber == 6) {
						System.out.println("Partie terminée");
						this.pauseGame();
						
					}
				}
			}
		}
	}
	
	private void animateLine(int line) {
        int delay = 100; // Délai en millisecondes entre chaque case

        for (int col = 0; col < wordSize; col++) {
            TextBox cell = wordleGrid.getTextBox(line, col);

            // Créez une animation de "backflip" rapide autour de l'axe X pour la cellule
            RotateTransition backflip = new RotateTransition(Duration.seconds(0.35), cell);
            backflip.setAxis(Rotate.X_AXIS); // Pour un "backflip" autour de l'axe X
            backflip.setByAngle(360); // Effectue un "backflip" de 360 degrés autour de l'axe X

            // Appliquez un délai pour l'animation de chaque case
            backflip.setDelay(Duration.millis(delay * col));

            // Écoutez la fin de l'animation pour mettre à jour la classe cellule
            backflip.setOnFinished(event -> {
                if (cell.getHelpInfo() == 1) {
                    cell.getStyleClass().add("textBoxCssWrongPlacement");
                } else if (cell.getHelpInfo() == 2) {
                	cell.getStyleClass().remove("textBoxCssWrongPlacement");
                    cell.getStyleClass().add("textBoxCssRight");
                }
                else if (cell.getHelpInfo() == 3) {
                    cell.getStyleClass().add("textBoxCssWrong");
                }
            });

            // Lancez l'animation
            backflip.play();
        }
    }
	
	public void updateKeyboardKey(char key, int value) {
		for (int i = 0; i<28;i++) {
			if(this.virtualKeyboard.getKeyButtonKey(i) == key) {
				if(value == 1 && this.virtualKeyboard.getKeyButtonAideInfoByChar(key) == 0) {
					this.virtualKeyboard.setKeyButtonAideInfo(i, value);
					this.virtualKeyboard.getButton(i).getStyleClass().add("virtualKeyboardButtonWrongPlacement");
				}
				if(value == 2) {
					this.virtualKeyboard.setKeyButtonAideInfo(i, value);
					this.virtualKeyboard.getButton(i).getStyleClass().remove("virtualKeyboardButtonWrongPlacement");
					this.virtualKeyboard.getButton(i).getStyleClass().add("virtualKeyboardButtonRight");
				}
				if(value == 3 && this.virtualKeyboard.getKeyButtonAideInfoByChar(key) == 0) {
					this.virtualKeyboard.setKeyButtonAideInfo(i, value);
					this.virtualKeyboard.getButton(i).getStyleClass().add("virtualKeyboardButtonWrong");
				}
			}
		}
	}

	
	//quelques fonctions pour gerer le timer
	public void pauseGame() {
        Timer.pause();
	}

    public void resumeGame() {
    	Timer.resume();
    }

    public void resetGame() {
    	Timer.reset();
    	this.wordsend = dico.randomWord(this.difficulty.getDifficulty());
   	 	this.word = this.wordsend.toUpperCase();
		try {
			this.hintList = dico.sendRandomWord(this.wordsend);
			this.hint = this.hintList.get(0);
			this.hint1 = this.hintList.get(1);
			this.hint2 = this.hintList.get(2);
			this.hint3 = this.hintList.get(3);
		}catch (Exception e){
			this.hint = "Le serveur python n'est pas connecté";
			this.hint1 = "Le serveur python n'est pas connecté";
			this.hint2 = "Le serveur python n'est pas connecté";
			this.hint3 = "Le serveur python n'est pas connecté";
		}
		this.wordChar = word.toCharArray();
		this.wordSize = word.length();
		this.gameStatus = false;
		if(winStatus == false)
			winStreak = 0;
		else
			this.winStatus = false;
		this.turnNumber = 0;
		System.out.println(this.word);
    	this.wordleGrid.updateGridSize(wordSize);
    	this.wordleGrid.resetGrid();
    	this.virtualKeyboard.resetKeyboard(turnNumber+1);
	 System.gc();
    }
    
    public boolean isWinStatus() {
		return winStatus;
	}

	public void setWinStatus(boolean winStatus) {
		this.winStatus = winStatus;
	}

	public void updateScore() {
    	score.updateScore(Timer);
    }
    
    public void updateScoreEndGame() {
    	if(gameStatus == true)
    		score.updateScoreEndGame(turnNumber, winStatus);
    }
    
	
}


