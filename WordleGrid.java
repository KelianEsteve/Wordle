package interface_wordle;

import java.text.Normalizer;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import javafx.animation.TranslateTransition;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;


/*
 * 
 * 			THIS CLASS IS THE GRID THAT CONTAINS THE TEXTBOXES 
 * 			
 * 			THERE ARE FUNCTIONS TO WRITE AND ERASE DIRECTLY WHERE IT SHOULD(WHERE THE CURSOR IS SUPPOSED)
 * 
 * 			ITS ALSO HERE THAT IS VERIFIED IF THE WORD IS FRENCH OR NOT
 * 
 * 
 */

public class WordleGrid extends GridPane {

	private int nbLine;
	private int nbColumn;
	

	private Map<String, Boolean> dictionary = new HashMap<>();
	private TextBox[][] textFields;
	
	
	public WordleGrid(int wordSize) {
		nbLine = 6;
		nbColumn = wordSize;

		chargeDictionary();
		textFields = new TextBox[nbLine][nbColumn];
		for (int row = 0; row < nbLine; row++) {
			for (int col = 0; col < nbColumn; col++) {
				TextBox textField = new TextBox();
				textField.setEditable(false);
				textField.getStyleClass().add("textBoxCss");
				textFields[row][col] = textField;
				add(textField, col, row);
			}
		}
	}
	
	// writing function
	public void write(String letter) {

		// we search the first free TextField 
		for (int row = 0; row < nbLine; row++) {
			for (int col = 0; col < nbColumn; col++) {

				TextBox textField = textFields[row][col];
				if (textField.isFree()) {

					//we make sure that you can't write in a new line before validating the one before
					if (row > 0) {
						if (col == 0) {
							TextBox prevField = textFields[row-1][nbColumn-1];
							if (!prevField.isValid()) {
								return;
							}
						}
					}
					
					// we write in the TextField and remove the free status of the TextField
					textField.setText(letter.toUpperCase());
					textField.setLibre(false);
					return;
				}
			}
		}
	}
	
	
	//erasing function
	public void erase() {
		
		// we search for the last filled TextField 
		for (int row = nbLine-1; row >= 0; row--) {
			for (int col = nbColumn-1; col >= 0; col--) {
				
				TextBox textField = textFields[row][col];
				if (!textField.isFree() && !textField.isValid()) {
					
					//we erase the letter in the TextField and give back the free status of the TextField
					textField.setLibre(true);
					textField.setText("");
					return;
				}
			}
		}
	}
	
	
	//fonction qui verifie une ligne, si le mot est valide il est retourné sinon elle retourne un String vide
	public String verifLine() {
		// on parcours la grille pour trouver la premiere ligne non validée
		for (int row = 0; row < nbLine; row++) {
			TextBox textField = textFields[row][0];
			if (!textField.isValid()) {
				
				String word = "";
				//on regarde si le mot formé dans la ligne est valide puis on quitte la fonction
				for (int i = 0; i < nbColumn; i++) {
					
					TextBox currField = textFields[row][i];
					word = word + currField.getText();
				}
						
				//notification pour savoir si le mot est valide ou non
				if (isFrench(word) && word!="" && word.length() == nbColumn) {
					for (int i = 0; i < nbColumn; i++) {
						//validation des cases de la lignee du mot validé
						TextBox currField = textFields[row][i];
						currField.setValide(true);
					}
					return word;
				}
				else {
					overswaying();
					return "";
				}
				}
		}
		return "";
	}
	
	void overswaying() {
		for (int col = 0; col < nbColumn; col++) {
	        TextBox cell = textFields[getLineToTest()+1] [col];

	        // Créez une animation d'oscillation gauche-droite
	        TranslateTransition overswaying = new TranslateTransition(Duration.seconds(0.05), cell);
	        overswaying.setAutoReverse(true); 
	        overswaying.setByX(5); 
	        overswaying.setByX(-5); 
	        overswaying.setCycleCount(10); 

	        overswaying.setOnFinished(event -> {
	            cell.setTranslateX(0); 
	        });

	        // Lancez l'animation d'oscillation
	        overswaying.play();
	    }
	}
	
	//fonction pour recupérer chaque case
	public TextBox getTextBox(int row, int col) {
		return textFields[row][col];
	}
	
	//fonction  renvoie le num de la premiere ligne non validee de la grille
	public int getLineToTest() {
		int li = 0;
		for (int i = 0; i < this.nbLine; i++) {
			TextBox textField = textFields[i][0];
			
			if (textField.isValid())
				li++;
			else
				break;
		}
		
		return li-1;
	}
	
	public boolean isFrench(String word) {
		 String normalisedWord = normaliseWord(word);
		 return dictionary.containsKey(normalisedWord); //on vérifie si le mot est dans la table
	}
	
	private String normaliseWord(String mot) {
	    // Supprimer les accents et convertir en minuscules
	    String normalisedWord = Normalizer.normalize(mot, Normalizer.Form.NFD)
	            .replaceAll("[^\\p{ASCII}]", "")
	            .toLowerCase();
	    return normalisedWord;
	}
	
	public void chargeDictionary() {
        Dictionary verif = new Dictionary();
        List<String> grDico = verif.getBigDictionary();
        for (String wordInList : grDico) {
            String normalisedWord = normaliseWord(wordInList);
            dictionary.put(normalisedWord, true);
        }
    }
	
	public void updateGridSize(int newWordSize) {
	    // Supprimez la grille actuelle
	    getChildren().clear();

	    // Mettez à jour la taille de la grille avec le nouveau mot
	    nbLine = 6;
	    nbColumn = newWordSize;
	    textFields = new TextBox[nbLine][nbColumn];

	    // Recréez la grille avec la nouvelle taille
	    for (int row = 0; row < nbLine; row++) {
	        for (int col = 0; col < nbColumn; col++) {
	            TextBox textField = new TextBox();
	            textField.setEditable(false);
	            textField.getStyleClass().add("textBoxCss");
	            textFields[row][col] = textField;
	            add(textField, col, row);
	        }
	    }
	}
	
	public void resetGrid() {
	    for (int row = 0; row < nbLine; row++) {
	        for (int col = 0; col < nbColumn; col++) {
	            TextBox textField = textFields[row][col];
	            textField.setText(""); // Effacer le contenu de chaque case
	            textField.setLibre(true); // Débloquer chaque case
	            textField.setValide(false);
	        }
	    }
	}
}
