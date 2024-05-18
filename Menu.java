package interface_wordle;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.layout.Priority;

public class Menu {
	
	public Menu(Stage menuStage) {
		startMenu(menuStage);
	}

    public void startMenu(Stage menuStage) {
        menuStage.setTitle("Menu de Jeux");
        menuStage.setMaxWidth(Double.MAX_VALUE);
        menuStage.setMaxHeight(Double.MAX_VALUE);

        // Créez un en-tête avec le texte "BIENVENUE"
        Text headerText = new Text("BIENVENUE");
        headerText.setFont(Font.font(24)); // Taille de la police
        headerText.setFill(Color.BLACK); // Couleur du texte
        StackPane header = new StackPane(headerText);
        header.setStyle("-fx-background-color: #87CEEB;"); // Couleur de fond

        // Créez des boutons pour chaque jeu avec des images et des titres
        Button jeu1Button = createGameButton("kraftable.png", "Kraftable", 64, 64);
        jeu1Button.getStyleClass().add("button"); // Ajoutez la classe CSS "button"
        // Définissez des actions pour chaque bouton (vous devrez les implémenter)
        jeu1Button.setOnAction(e -> {
        	menuStage.close();

            Stage menuGameStage = new Stage();
            GameMenu gameMenu = new GameMenu(menuGameStage);
        });
        // Créez une mise en page pour organiser les boutons
        VBox layout = new VBox(100);
        layout.setAlignment(Pos.CENTER);
        layout.setMargin(jeu1Button, new Insets(10)); // Ajoutez une marge de 10 pixels autour du bouton

        layout.getChildren().addAll(jeu1Button);

        // Créez un footer avec un fond vert et les noms et prénoms de 4 personnes
        HBox footer = createFooter();
        VBox.setMargin(footer, new Insets(10, 0, 10, 0)); // Ajoutez une marge de 10 pixels autour du footer
        VBox.setVgrow(footer, Priority.ALWAYS); // Le footer prend toute la place disponible en hauteur

        // Créez une mise en page pour organiser le header, les boutons et le footer
        VBox rootLayout = new VBox();
        rootLayout.getChildren().addAll(header, layout, footer);

        // Créez une scène
        Scene scene = new Scene(rootLayout, 500, 300); // Taille de la scène modifiée

        // Chargez la feuille de style CSS
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

        // Définir la taille minimale de la fenêtre en fonction du contenu
        menuStage.setMinWidth(500);
        menuStage.setMinHeight(300);

        // Affichez la scène
        menuStage.setScene(scene);
        menuStage.show();
    }


    // Créez un bouton de jeu avec une image, un titre et des dimensions d'icône
    public Button createGameButton(String imageName, String title, double iconWidth, double iconHeight) {
    	Image image = new Image(getClass().getResourceAsStream(imageName));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(iconWidth); // Largeur de l'icône
        imageView.setFitHeight(iconHeight); // Hauteur de l'icône
        Text titleText = new Text(title); // Titre du jeu
        titleText.setFill(Color.BLACK); // Couleur du texte
        titleText.getStyleClass().add("text"); // Ajoutez la classe CSS "text" au titre
        VBox buttonContent = new VBox(5); // Empile l'image et le titre verticalement
        buttonContent.setAlignment(Pos.CENTER);
        buttonContent.getChildren().addAll(imageView, titleText);
        Button button = new Button("", buttonContent); // Texte vide pour le bouton
        return button;
    }

    // Créez le footer avec les noms et prénoms de 4 personnes
    private HBox createFooter() {
        Text author1 = new Text("Allègre Romain");
        Text author2 = new Text("Esteve Kelian");
        Text author3 = new Text("Calvin Tom");
        Text author4 = new Text("Alguazil Florian");

        HBox footer = new HBox(10);
        footer.setAlignment(Pos.CENTER);
        footer.setStyle("-fx-background-color: #87CEEB;"); // Couleur de fond verte
        footer.getChildren().addAll(author1, author2, author3, author4);

        return footer;
    }
}