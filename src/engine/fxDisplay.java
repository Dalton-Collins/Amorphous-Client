package engine;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

 
public class fxDisplay extends Application {
	
	//public static fxDisplay self = new fxDisplay();
	
	Client client;
	
	Stage primaryStage;
	StackPane mainStack;
	
	BoardLayoutMaker boardLayoutMaker;
	MinionToButton minionToButton;
	
	//display states
	boolean displayingDetailedCard = false;
	DisplayMinion attackingMinion = null;
	Text detailedCard;
	
	//handlers
	CardViewHandler cardViewHandler;
	ClientEndTurnHandler clientEndTurnHandler;
	ClientDirectAttackHandler clientDirectAttackHandler;
	ClientSummonHandler clientSummonHandler;
	ClientAffectSelectHandler clientAffectSelectHandler;
	ClientAttackHandler clientAttackHandler;
	
	DisplayGameState displayGameState;
	
	
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStagee) throws UnknownHostException, IOException {
    	
    	client = new Client(this, "player", "localhost", 9082);
    	client.connect();
    	
    	
    	System.out.println("client connected...");
    	
    	
    	//Set Handlers
    	cardViewHandler = new CardViewHandler(this);
    	clientEndTurnHandler = new ClientEndTurnHandler(this);
    	clientDirectAttackHandler = new ClientDirectAttackHandler(this);
    	clientSummonHandler = new ClientSummonHandler(this);
    	clientAffectSelectHandler = new ClientAffectSelectHandler(this);
    	clientAttackHandler = new ClientAttackHandler(this);
    	
    	minionToButton = new MinionToButton(this);
    	
    	primaryStage = primaryStagee;
    	primaryStage.setTitle("Amorphous");
    	
    	
    	//layouts
        StackPane titleLayout = new StackPane();
        GridPane titleButtons = new GridPane();
        titleLayout.getChildren().add(titleButtons);
        
        boardLayoutMaker = new BoardLayoutMaker();
        StackPane boardLayout = boardLayoutMaker.getLayout();
        //scenes
        Scene titleScreen = new Scene (titleLayout, 1200, 1000);
        Scene boardScene = new Scene (boardLayout, 1200, 1000);
        
        //buttons
        
        Button strtbtn = new Button();
        strtbtn.setText("Make New Game");
        strtbtn.setOnAction(new EventHandler<ActionEvent>() {
        	 
            @Override
            public void handle(ActionEvent event) {
            	primaryStage.setScene(boardScene);
            	GameCommand gc = new GameCommand("makeGame");
				client.write(gc);
            }
        });
        
        Button joinbtn = new Button();
        joinbtn.setText("Join Game");
        joinbtn.setOnAction(new EventHandler<ActionEvent>() {
       	 
            @Override
            public void handle(ActionEvent event) {
            	primaryStage.setScene(boardScene);
            	GameCommand gc = new GameCommand("joinGame");
            	gc.n = 0;//this needs to change later to accomodate more games.
				client.write(gc);
            }
        });
        
        
        
        //layout buttons
        titleButtons.add(strtbtn, 10, 0);
        titleButtons.add(joinbtn, 10, 30);
        
        primaryStage.setScene(titleScreen);
        primaryStage.show();
        
    }
    
    public void updateDisplay(DisplayGameState dgs){
    	if(dgs.selectingAffectTarget){
    		affectSelection(dgs);
    		return;
    	}
    	displayGameState = dgs;
    	
    	//create new scene
    	StackPane boardStack = boardLayoutMaker.getLayout();
    	mainStack = boardStack;
    	BorderPane boardLayout = (BorderPane) boardStack.getChildren().get(0);
    	Scene boardScene = new Scene(boardStack, 1200, 1000);
    	
    	//buttons 
    	setEndTurnButton(boardLayout);
        
        //labels
        //update mana/life
        setManaLife(dgs, boardLayout);
        
    	//update hands
        setHandCards(dgs, boardLayout);
    	
    	//update field
    	setFieldCards(dgs, boardLayout);
    	primaryStage.setScene(boardScene);
    }
    
    public void affectSelection(DisplayGameState dgs){
    	System.out.println("enter affect selection");
    	
    	StackPane boardStack = boardLayoutMaker.getLayout();
    	mainStack = boardStack;
    	BorderPane boardLayout = (BorderPane) boardStack.getChildren().get(0);
    	Scene boardScene = new Scene(boardStack, 1200, 1000);
    	
        setEndTurnButton(boardLayout);
        
        Button cancelEffectSelection = new Button();
        cancelEffectSelection.setText("Cancel Effect");
        cancelEffectSelection.setOnAction(new EventHandler<ActionEvent>(){
        	public void handle(ActionEvent event) {
        		//this needs to send a message to the servers gamestate
        		//with the selected affect target
        		GameCommand gc = new GameCommand("affectTarget");
        		CardButton cb = (CardButton)event.getSource();
        		gc.displayMinion1 = cb.minion;
				client.write(gc);
        		/*
        		GameState gs = GameState.getGameState();
        		gs.selectingAffectTarget = false;
        		gs.affectStack.processing = false;
        		gs.affectStack.pauseProcessing = false;
        		gs.affectStack.afterSelectionAffect = null;
        		updateDisplay();
        		*/
        	}
        });
        ((VBox)boardLayout.getLeft()).getChildren().add(cancelEffectSelection);
        
        //labels
        //update mana/life
        setManaLife(dgs, boardLayout);
        
    	//update hands
        setHandCardsInactive(dgs, boardLayout);
    	
    	//update field
    	setEffectSelectionCards(dgs, boardLayout);

    	primaryStage.setScene(boardScene);
    }
    
    void displayDetailedCard(DisplayMinion m){
    	Text card = new Text();
    	String cardText = "";
    	cardText+= m.name + "    " + m.cost + "\n\n"
    	+ m.triggerText + "\n"
    	+ m.affectText + "\n\nATK "
    	+ m.atk + "     HP " + m.health;
    	
    	card.setWrappingWidth(200);
    	card.setText(cardText);
    	card.setStyle("-fx-font: 25 arial;");
        
    	detailedCard = card;
    	displayingDetailedCard = true;
    	mainStack.getChildren().add(card);
    }
    
    void removeDetailedCard(){
    	mainStack.getChildren().remove(detailedCard);
    	detailedCard = null;
    	displayingDetailedCard = false;
    }
    
    void setEndTurnButton(BorderPane boardLayout){
    	Button endTurn = new Button();
        endTurn.setText("End Turn");
        endTurn.setFont(new Font("Arial", 18));
        endTurn.setOnAction(clientEndTurnHandler);
        ((VBox)boardLayout.getRight()).getChildren().add(endTurn);
    }
    
    void setPlayerDamageButton(BorderPane boardLayout){
    	CardButton attackPlayer = new CardButton("      Direct Attack      ");
    	attackPlayer.setFont(new Font("Arial", 30));
    	attackPlayer.setOnAction(clientDirectAttackHandler);
    	GridPane grid = (GridPane)boardLayout.getCenter();
    	attackPlayer.setStyle("-fx-font: 20 arial; -fx-base: #ee1122;");
    	grid.add(attackPlayer, 0, 0);
    }
    
    void setFieldCards(DisplayGameState dgs, BorderPane boardLayout){
    	if(dgs.enemyFieldMinions.isEmpty()){
    		setPlayerDamageButton(boardLayout);
    	}
    	for(DisplayMinion m: dgs.friendlyFieldMinions){
    		Button card = minionToButton.convertForField(m);
    		GridPane gridPane = (GridPane) boardLayout.getCenter();
    		HBox bottomFieldHBox = (HBox) gridPane.getChildren().get(0);
    		bottomFieldHBox.getChildren().add(card);	
    		
    	}
    	
    	for(DisplayMinion m: dgs.enemyFieldMinions){
    		Button card = minionToButton.convertForField(m);
    		GridPane gridPane = (GridPane) boardLayout.getCenter();
    		HBox topFieldHBox = (HBox) gridPane.getChildren().get(1);
    		topFieldHBox.getChildren().add(card);
    		
    	}
    }
    
    void setEffectSelectionCards(DisplayGameState dgs, BorderPane boardLayout){
    	for(DisplayMinion m: dgs.friendlyFieldMinions){
    		Button card = minionToButton.convertForEffectSelection(m);
    		GridPane gridPane = (GridPane) boardLayout.getCenter();
    		HBox bottomFieldHBox = (HBox) gridPane.getChildren().get(0);
    		bottomFieldHBox.getChildren().add(card);	
    		
    	}
    	
    	for(DisplayMinion m: dgs.enemyFieldMinions){
    		Button card = minionToButton.convertForEffectSelection(m);
    		GridPane gridPane = (GridPane) boardLayout.getCenter();
    		HBox topFieldHBox = (HBox) gridPane.getChildren().get(1);
    		topFieldHBox.getChildren().add(card);
    		
    	}
    }
    
    void setHandCards(DisplayGameState dgs, BorderPane boardLayout){
    	for(DisplayMinion m : dgs.handMinions){
    		Button card = minionToButton.convertForHand(m);
    		HBox bottomHBox = (HBox) boardLayout.getBottom();
    		bottomHBox.getChildren().add(card);
    	}
    	
    	/*
    	for(Minion m : GameState.getGameState().players.get(1).hand.cards){
    		Button card = minionToButton.convertForHand(m);
    		HBox topHBox = (HBox) boardLayout.getTop();
    		topHBox.getChildren().add(card);
    		
    	}
    	*/
    }
    
    void setHandCardsInactive(DisplayGameState dgs, BorderPane boardLayout){
    	for(DisplayMinion m : dgs.handMinions){
    		Button card = minionToButton.convertForInaction(m);
    		HBox bottomHBox = (HBox) boardLayout.getBottom();
    		bottomHBox.getChildren().add(card);
    	}
    	
    	/*
    	for(Minion m : GameState.getGameState().players.get(1).hand.cards){
    		Button card = minionToButton.convertForInaction(m);
    		HBox topHBox = (HBox) boardLayout.getTop();
    		topHBox.getChildren().add(card);
    		
    	}
    	*/
    }
    
    void setManaLife(DisplayGameState dgs, BorderPane boardLayout){
    	Label p2Mana = new Label();
        p2Mana.setText("Mana: " + dgs.enemyMana);
        p2Mana.setFont(new Font("Arial", 25));
        p2Mana.setTextFill(Color.web("#ff38c3"));
        ((VBox)boardLayout.getLeft()).getChildren().add(p2Mana);
        
        Label p2Life = new Label();
        p2Life.setText("Life: " + dgs.enemyLife);
        p2Life.setFont(new Font("Arial", 25));
        p2Life.setTextFill(Color.web("#ff5e5e"));
        ((VBox)boardLayout.getLeft()).getChildren().add(p2Life);
        
        Label p1Life = new Label();
        p1Life.setText("Life: " + dgs.life);
        p1Life.setFont(new Font("Arial", 25));
        p1Life.setTextFill(Color.web("#3891ff"));
        ((VBox)boardLayout.getLeft()).getChildren().add(p1Life);
        
        Label p1Mana = new Label();
        p1Mana.setText("Mana: " + dgs.mana);
        p1Mana.setFont(new Font("Arial", 25));
        p1Mana.setTextFill(Color.web("#38d0ff"));
        ((VBox)boardLayout.getLeft()).getChildren().add(p1Mana);
    }
    
}
