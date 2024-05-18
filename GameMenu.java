package interface_wordle;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GameMenu {
	
	public GameMenu(Stage gameMenuStage) {
		startGameMenu(gameMenuStage);
	}
	
    public void startGameMenu(Stage gameMenuStage) {
        Text headerText = new Text("KRAFTABLE");
        headerText.setFill(Color.WHITE);
        headerText.getStyleClass().add("headerMenuGameText"); // Ajoutez la classe CSS au texte du titre
        StackPane header = new StackPane(headerText);
        header.getStyleClass().add("headerMenuGame"); // Ajoutez la classe CSS à l'en-tête
        
        Button classicBtn = new Button("CLASSIC");
        classicBtn.getStyleClass().add("butonMenuGame");
        
        Button hardBtn = new Button("HARD");
        hardBtn.getStyleClass().add("butonMenuGame");
        
        
        Button closeBouton = new Button("QUITTER");
        closeBouton.getStyleClass().add("butonMenuGame");
        // Gérer l'événement du bouton "Play"
        classicBtn.setOnAction(e -> {
        	gameMenuStage.close();

        	Stage wordleStage = new Stage();
        	Wordle wordle = new Wordle(wordleStage, "CLASSIC");
        });
        
        hardBtn.setOnAction(e -> {
        	gameMenuStage.close();

        	Stage wordleStage = new Stage();
        	Wordle wordle = new Wordle(wordleStage, "HARD");
        });
        
        closeBouton.setOnAction(e -> {
          Stage stage = (Stage) closeBouton.getScene().getWindow();
          stage.close();

          // Affichez à nouveau la fenêtre Main
          Main mainApp = new Main();
          Stage mainStage = new Stage();
          mainApp.start(mainStage);
        });

        // Créer une mise en page (layout) pour les boutons
        VBox layout = new VBox(10); // 10 est l'espacement vertical entre les boutons
        layout.getStyleClass().add("buttonBoxMenuGame"); // Ajoutez la classe CSS au conteneur
        layout.getChildren().addAll(classicBtn,hardBtn, closeBouton);

        VBox rootLayout = new VBox();
        rootLayout.getChildren().addAll(header, layout);
        // Créer une scène et l'ajouter à l'étape (Stage)
        Scene scene = new Scene(rootLayout, 300, 300);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm()); // Configurer la fenêtre de l'application
        gameMenuStage.setScene(scene);
        gameMenuStage.setTitle("Menu de Jeux");
        gameMenuStage.show();
    }

}
