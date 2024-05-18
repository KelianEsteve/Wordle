package interface_wordle;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.UnaryOperator;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;


public class Wordle {
    private GameWordle game;
    private Label timerLabel;
    private Label scoreLabel;
    private Label winStreakLabel;
    private AnimationTimer animationTimer;
    private AnimationTimer animationScore;
    private ObservableList<String> hintList;
    private int []i;
    private boolean gameEndPopupShown = false;
    private String filePath = "src/interface_wordle/data";
    private List<Data> data;
    
    public GameWordle getGame() {
    	return this.game;
    }
    
    public String getNextHint(int i) {
        return game.getHint(i);

    }
    
    public Wordle(Stage wordleStage, String difficulty) {
        wordleStage.setTitle("Wordle");
    	this.data = Data.readFile(filePath); //data for leaderboard
        this.game = new GameWordle(difficulty);
        this.i = new int[]{0}; 
        
        
        BorderPane root = new BorderPane();
        
        /*
         *            						
         *				RESET BUTTON
         *
         */
        Button resetButton = new Button("Reset");
        resetButton.getStyleClass().add("label");
        resetButton.getStyleClass().add("subtopbox");
        resetButton.getStyleClass().add("buttontopbox");
        resetButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	if (game.getWinStreak() >= 1) {
                	System.out.println(game.getScore());
                	dataStorage(wordleStage);
                }
                i = new int[]{0};
                hintList.clear();
                game.resetGame();
                game.pauseGame();
                game.setTimerStatus(false); 
                int a = game.getWordSize() * 75;
                if (a < 600) a = 600;
                wordleStage.setMinWidth(a);
                hintList.clear();
            }
        });
        
        /*
         *            						
         *				BACK TO THE MENU BUTTON
         *
         */
        Button menuButton = new Button("Menu");
        menuButton.getStyleClass().add("label");
        menuButton.getStyleClass().add("subtopbox");
        menuButton.getStyleClass().add("buttontopbox");
        menuButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	if (game.getWinStreak() >= 1) {
                	dataStorage(wordleStage);
                	wordleStage.close();
                    Stage gameMenuStage = new Stage();
                    GameMenu gameMenu = new GameMenu(gameMenuStage);
                }
            	else {
	            	wordleStage.close();
	                Stage gameMenuStage = new Stage();
	                GameMenu gameMenu = new GameMenu(gameMenuStage);
            	}
            }
        });
        
        
        /*
         *            						
         *				LEARDERBOARD BUTTON
         *
         */
        Button leaderboardbtn = new Button("LeaderBoard");
        leaderboardbtn.getStyleClass().add("label");
        leaderboardbtn.getStyleClass().add("subtopbox");
        leaderboardbtn.getStyleClass().add("buttontopbox");
        leaderboardbtn.setOnAction(e -> openLeaderBoard(wordleStage));
        
        
        /*
         *            						
         *				RULE BUTTON
         *
         */
        Button rulebtn = new Button("??");
        rulebtn.getStyleClass().add("label");
        rulebtn.getStyleClass().add("subtopbox");
        rulebtn.getStyleClass().add("buttontopbox");
        rulebtn.setOnAction(e -> openRule(wordleStage));
        
        
        /*
         *            						
         *				HINT BUTTON
         *
         */
        hintList = FXCollections.observableArrayList();
        ComboBox<String> hintComboBox = new ComboBox<>(hintList);
        hintComboBox.getStyleClass().add("label");
        hintComboBox.getStyleClass().add("smallsubtopbox");
        hintComboBox.setOnShowing(event -> {
            // Ajout du prochain indice à la liste déroulante
            i[0]++;
            String nextHint = getNextHint(i[0]);
            if(i[0]<=4) {
            	hintList.add("Indice: " + nextHint);
            }
            hintComboBox.setItems(hintList);
        });
        
        
        /*
         *            						
         *				LEFTBOX:  TIMER, SCORE, WINSTREAK
         *
         */
        HBox leftBox = new HBox();
        leftBox.getStyleClass().add("subtopbox");
        
        timerLabel = new Label("TIMER: 0");
        timerLabel.getStyleClass().add("label");
        scoreLabel = new Label();
        scoreLabel.getStyleClass().add("label");
        winStreakLabel = new Label("0W");
        winStreakLabel.getStyleClass().add("label");
        
        leftBox.getChildren().addAll(timerLabel, scoreLabel, winStreakLabel);
        
        
        
        //SOME SPACERS
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        Region spacer1 = new Region();
        HBox.setHgrow(spacer1, Priority.ALWAYS);
        Region spacer2 = new Region();
        HBox.setHgrow(spacer2, Priority.ALWAYS);
        Region spacer3 = new Region();
        HBox.setHgrow(spacer3, Priority.ALWAYS);
        Region spacer4 = new Region();
        HBox.setHgrow(spacer4, Priority.ALWAYS);
        Region spacer5 = new Region();
        HBox.setHgrow(spacer5, Priority.ALWAYS);
        
        /*
         *            						
         *				TOP BOX
         *
         */    
        HBox topBox = new HBox();
        topBox.getStyleClass().add("topbox");
        topBox.getChildren().addAll(leftBox, spacer2, hintComboBox, spacer3, leaderboardbtn, spacer, rulebtn, spacer4, resetButton, spacer5, menuButton);
        root.setTop(topBox);
        
        
        /*
         *            						
         *				GRID AND KEYBOARD
         *
         */
        GridPane grid = game.getGrid();
        Node keyboard = game.getVirtualKeyboard();
        grid.getStyleClass().add("grid");
        keyboard.getStyleClass().add("virtualKeyboard");
        root.setCenter(grid);
        root.setBottom(keyboard);

        
        /*
         *            						
         *				SCENE
         *
         */
        Scene scene = new Scene(root, game.getWordSize() * 80, 700);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        wordleStage.setScene(scene);
        //ajusting scene size
        int a = game.getWordSize() * 75;
        if (a < 600) a = 600;
        wordleStage.setMinWidth(a);
        wordleStage.setMinHeight(750);

        
        /*
         *            						
         *				VIRTUAL AND PHYSICAL KEYBOARD GESTION
         *
         */
        game.getVirtualKeyboard().setOnKeyClick((letter) -> {
            game.keyboardInteraction(letter);
        });
        scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            KeyCode code = event.getCode();
            String letter = null;

            if (code.isLetterKey()) {
                letter = code.toString().toLowerCase();
            } else if (code == KeyCode.ENTER) {
                letter = "<ENTREE>";
            } else if (code == KeyCode.BACK_SPACE) {
                letter = "<BACKSPACE>";
            }

            if (letter != null) {
                game.keyboardInteraction(letter);
                event.consume();
            }
        });

        
        /*
         *            						
         *				SHOWING THE WINDOW
         *
         */
        wordleStage.show();
        wordleStage.setMinWidth(a+1);
        wordleStage.setMinHeight(760);
        wordleStage.setMinWidth(a);
        wordleStage.setMinHeight(750);
        
        
        /*
         *            						
         *				STARTING ANIMATIONS
         *
         */
        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateTimerLabel();
            }
        };
        animationTimer.start();

        animationScore = new AnimationTimer() {
            public void handle(long now) {
                updateScoreLabel();
                if (game.getGameStatus()) {
                    showGameEndPopup(wordleStage);
                }
            }
        };
        animationScore.start();
    }

    private void updateTimerLabel() {
        timerLabel.setText(game.getTimer().getElapsedTime() + "s");
    }

    private void updateScoreLabel() {
        game.updateScore();
        scoreLabel.setText("WP: " + game.getScore());
    }
    
    private void updateWinStreakLabel() {
        winStreakLabel.setText(game.getWinStreak() + "W");
    }
    
    
    /*
     *            						
     *				RULES FUNCTIONS
     *
     */
    private void openRule(Stage ownerStage) {
    	Stage subStage = new Stage(StageStyle.UTILITY);
        subStage.setTitle("Rules");
        subStage.initOwner(ownerStage);
        
    	VBox vbox = new VBox(10);
		
		Text headerText = new Text("Instruction");
        headerText.setFill(Color.WHITE);
        headerText.getStyleClass().add("label");
        StackPane header = new StackPane(headerText);
        header.getStyleClass().add("headerMenuGame");
        vbox.getChildren().add(header);

	    

	    GridPane grid = new GridPane();
	    grid.setVgap(10);
	    grid.setHgap(10);

	    grid.add(createColorRect(Color.valueOf("#4ab0af")), 0, 0);
	    grid.add(new Label("La lettre est dans le mot et bien placée"), 1, 0);

	    grid.add(createColorRect(Color.valueOf("#ffda53")), 0, 1);
	    grid.add(new Label("La lettre est dans le mot mais mal placée"), 1, 1);

	    grid.add(createColorRect(Color.valueOf("#3B3A51")), 0, 2);
	    grid.add(new Label("La lettre n'est pas dans le mot"), 1, 2);

	    vbox.getChildren().add(grid);

	    Label instructions = new Label("Devinez le mot en six tentatives. Apres chaque essai les couleurs des lettres vous donneront des indices");
	    instructions.setStyle("-fx-wrap-text: true;");
	    vbox.getChildren().add(instructions);

	    AnchorPane root = new AnchorPane(vbox);
	    AnchorPane.setRightAnchor(vbox, 100.0);
	    AnchorPane.setLeftAnchor(vbox, 100.0);
	    Scene scene = new Scene(root, 500, 350);
	    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	    subStage.setScene(scene);
	    subStage.setTitle("Wordle Tutorial");
	    subStage.show();
    }
    
    private Rectangle createColorRect(Color color) {
        Rectangle rect = new Rectangle(30, 30);
        rect.setFill(color);
        return rect;
    }
    
    /*
     *            						
     *				LEARDERBOARD FUNCTIONS
     *
     */
    private void openLeaderBoard(Stage ownerStage) {
        Stage subStage = new Stage(StageStyle.UTILITY);
        subStage.setTitle("LeaderBoard");
        subStage.initOwner(ownerStage);

        Button scorebtn = new Button("SCORE");
        scorebtn.getStyleClass().add("subtopbox");
        scorebtn.getStyleClass().add("buttontopbox");
        scorebtn.getStyleClass().add("label");
        
        Button wsbtn = new Button("VICTOIRE");
        wsbtn.getStyleClass().add("subtopbox");
        wsbtn.getStyleClass().add("buttontopbox");
        wsbtn.getStyleClass().add("label");
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox buttonsHBox = new HBox(scorebtn,spacer, wsbtn);
        buttonsHBox.getStyleClass().add("topbox");
        VBox vbox = new VBox(buttonsHBox);
        vbox.getChildren().add(createListVBox(true));
        vbox.getStyleClass().add("root");

        scorebtn.setOnAction(event -> {
            vbox.getChildren().removeIf(node -> node instanceof VBox);
            vbox.getChildren().add(createListVBox(true));
        });
        wsbtn.setOnAction(event -> {
            vbox.getChildren().removeIf(node -> node instanceof VBox);
            vbox.getChildren().add(createListVBox(false));
        });
        subStage.setScene(new Scene(vbox, 200, 210));
        subStage.setResizable(false);
        subStage.getScene().getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        subStage.show();
    }

    private VBox createListVBox(boolean scoreOrWs) {
        VBox vbox = new VBox();
        List<Data> sortedDatas = trierData(scoreOrWs);
        for (Data data : sortedDatas) {
        	if(scoreOrWs) {
        		Text datatext = new Text(data.getDataStringForScore());
        		datatext.setFill(Color.WHITE);
        		vbox.getChildren().add(datatext);
        		vbox.getStyleClass().add("topbox");
        		vbox.getStyleClass().add("label");
        	}
        	else {
        		Text datatext = new Text(data.getDataStringForWinStreak());
        		datatext.setFill(Color.WHITE);
        		vbox.getChildren().add(datatext);
        		vbox.getStyleClass().add("topbox");
        		vbox.getStyleClass().add("label");
        	}
        }
        return vbox;
    }

    private List<Data> trierData(boolean scoreOrWs) {
        List<Data> datatable = new ArrayList<>();
        if(scoreOrWs) {
        	for (Data data : data) {
        		if(data.getFlag()[0] == 1) {
        			datatable.add(data);
        		}
        	}
        	Collections.sort(datatable, Comparator.comparingInt(Data::getScore).reversed());
        }
        else{
        	for (Data data : data) {
        		if(data.getFlag()[1] == 1) {
        			datatable.add(data);
        		}
        	}
        	Collections.sort(datatable, Comparator.comparingInt(Data::getWinStreak).reversed());
        }
        return datatable;
    }
    
    /*
     *            						
     *				ENDGAME MANAGEMENT
     *
     */
    private void showGameEndPopup(Stage wordleStage) {
        if (!gameEndPopupShown) {
            Platform.runLater(() -> {
            	Alert alert = new Alert(AlertType.INFORMATION);
            	alert.setHeaderText("Winstreak: " + game.getWinStreak());
            	alert.initStyle(StageStyle.UNDECORATED);
                alert.initOwner(wordleStage);
                alert.setTitle(null);
                alert.setGraphic(null);

            	ButtonType replayButton = (game.getWinStatus()) ? new ButtonType("CONTINUE") : new ButtonType("NEW GAME");
            	ButtonType quitButton = new ButtonType("QUIT");
                
                alert.getButtonTypes().setAll(replayButton, quitButton);
                alert.getDialogPane().getStyleClass().add("subwindow");
                
                alert.showAndWait().ifPresent(buttonType -> {
                    game.updateScoreEndGame();
                    if (buttonType == replayButton) {
                    	if(!game.getWinStatus()) {
							dataStorage(wordleStage);
                    	}
                    	i = new int[]{0};
						hintList.clear();
						game.resetGame();
						game.pauseGame();
						game.setTimerStatus(false);
						updateWinStreakLabel();
						gameEndPopupShown = false;
                    } else if (buttonType == quitButton) {
                    	
                    	dataStorage(wordleStage);
                        wordleStage.close();
                        Stage gameMenuStage = new Stage();
                        GameMenu gameMenu = new GameMenu(gameMenuStage);
                    }
                });
            });
        gameEndPopupShown = true;
        }
        return;
    }
    
    public void dataStorage(Stage wordleStage) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.initStyle(StageStyle.UNDECORATED);
        dialog.initOwner(wordleStage);
        dialog.getDialogPane().getStyleClass().add("subwindow");
        dialog.setTitle(null);
        dialog.setGraphic(null);
        dialog.setHeaderText("ENTER YOUR NAME");

        dialog.showAndWait().ifPresent(name -> {
            int[] a = {0,0,0,0};
            data = Data.insertData(new Data(name, game.getWinStreak(), game.getScore(), game.getDifficulty(), a), data);
            Data.writeFile(data, filePath);
        });
    }
}
