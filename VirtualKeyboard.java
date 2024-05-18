package interface_wordle;


import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.scene.control.Button;

public class VirtualKeyboard extends GridPane {
	private KeyButton keyboardInfo[] = new KeyButton[28]; //tableau qui sert a stocker les info des key pour les couleurs
	private Button keyboardButton[] = new Button[28];
	
	public char getKeyButtonKey(int i) {
		return keyboardInfo[i].getKey();
	}
	
	public int getKeyButtonAideInfo(int i){
		return keyboardInfo[i].getHelpInfo();
	}
	
	public int getKeyButtonAideInfoByChar(char i){
		for(int j = 0 ; j < 27 ; j++) {
			if (keyboardInfo[j].getKey() == i)
				return keyboardInfo[j].getHelpInfo();
		}
		return 0;
	}
	
	public void setKeyButtonAideInfo(int i, int value){
		keyboardInfo[i].setHelpInfo(value);
	}
	
	public Button getButton(int i){
		return this.keyboardButton[i];
	}

	public VirtualKeyboard() {
	    char azerty[] = {'a', 'z', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', 'q', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'w', 'x', 'c', 'v', 'b', 'n', '<', '>'};
	    
	    VBox container = new VBox(); // Utilisez une VBox pour contenir les lignes de boutons
	    container.setAlignment(Pos.CENTER); // Centrez le contenu verticalement

	    int currentCol = 0;
	    HBox currentRow = new HBox(3);// Utilisez une HBox pour chaque ligne de boutons
	    currentRow.getStyleClass().add("keyboardRow");
	    currentRow.setAlignment(Pos.CENTER); // Centrez la ligne horizontalement

	    for (int i = 0; i < azerty.length; i++) {
	        Button button = new Button(Character.toString(azerty[i]).toUpperCase());
	        final String lettre = Character.toString(azerty[i]);
	        keyboardInfo[i]= new KeyButton(Character.toUpperCase(azerty[i]));

	        button.getStyleClass().add("virtualKeyboardButton");

	        // Gestionnaire d'événements pour le clic sur le bouton
	        button.setOnAction(event -> {
	            if (onKeyClick != null) {
	                onKeyClick.onKeyClick(lettre);
	            }
	        });

	        currentRow.getChildren().add(button); // Ajoutez le bouton à la ligne actuelle
	        keyboardButton[i] = button;
	        
	        currentCol++;

	        if (currentCol == 10) {
	            container.getChildren().add(currentRow); // Ajoutez la ligne à la VBox
	            currentRow = new HBox(3); // Créez une nouvelle ligne
	            currentRow.getStyleClass().add("keyboardRow");
	            currentRow.setAlignment(Pos.CENTER); // Centrez la nouvelle ligne horizontalement
	            currentCol = 0;
	        }
	    }

	    if (!currentRow.getChildren().isEmpty()) {
	        container.getChildren().add(currentRow); // Ajoutez la dernière ligne s'il en reste une
	    }

	    getChildren().add(container); // Ajoutez la VBox contenant les lignes à votre clavier virtuel
	    getStyleClass().add("virtualKeyboard");
	}
	

    // Interface pour le gestionnaire d'événements de clic de touche
    public interface OnKeyClick {
        void onKeyClick(String lettre);
    }

    private OnKeyClick onKeyClick;

    public void setOnKeyClick(OnKeyClick onKeyClick) {
        this.onKeyClick = onKeyClick;
    }
    
    public void resetKeyboard(int a) {
    	for(int i = 0;i<28;i++) {
    		keyboardButton[i].getStyleClass().clear();
        	keyboardButton[i].getStyleClass().add("virtualKeyboardButton");
    		keyboardInfo[i].setHelpInfo(0);
    	}
	}
}

