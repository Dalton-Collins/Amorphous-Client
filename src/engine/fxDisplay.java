package engine;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javax.swing.UIManager.*;

 
public class fxDisplay extends Application {
	
	//public static fxDisplay self = new fxDisplay();
	
	Client client;
	
	Stage primaryStage;
	StackPane mainStack;
	Scene boardScene;
	BorderPane gameSelectLayout;
	GridPane loginLayout;
	
	
	
	BoardLayoutMaker boardLayoutMaker;
	MinionToButton minionToButton;
	
	//display states
	boolean displayingDetailedCard = false;
	boolean affectSelection = false;//if this is true then the server is waiting on
									//input from this client to get an affect target
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
    	//CHANGE THIS FOR SERVER &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
    	client = new Client(this, "player", "localhost", 9083);
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
        
        boardLayoutMaker = new BoardLayoutMaker();
        StackPane boardLayout = boardLayoutMaker.getLayout();
        //scenes
        Scene boardScenee = new Scene (boardLayout, 1200, 1000);
        boardScene = boardScenee;
        
        openLoginScene();

        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX(primaryScreenBounds.getMinX());
        primaryStage.setY(primaryScreenBounds.getMinY());
        primaryStage.setWidth(primaryScreenBounds.getWidth());
        primaryStage.setHeight(primaryScreenBounds.getHeight());
        primaryStage.show();
/*
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth())/5);
        primaryStage.setY((primScreenBounds.getWidth()- primaryStage.getHeight()));
        */
    }
    void openLoginScene(){
    	
    	//layout
    	GridPane loginGrid = new GridPane();
    	loginGrid.setAlignment(Pos.CENTER);
    	loginGrid.setHgap(10);
    	loginGrid.setVgap(10);
    	loginGrid.setPadding(new Insets(25, 25, 25, 25));
    	
    	Text scenetitle = new Text("Amorphous");
    	scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
    	loginGrid.add(scenetitle, 0, 0, 2, 1);

    	Label userName = new Label("User Name:");
    	loginGrid.add(userName, 0, 1);

    	TextField userNameField = new TextField();
    	userNameField.setPromptText("Enter Account Name");
    	loginGrid.add(userNameField, 1, 1);

    	Label pw = new Label("Password:");
    	loginGrid.add(pw, 0, 2);

    	PasswordField passwordField = new PasswordField();
    	passwordField.setPromptText("Enter Password");
    	loginGrid.add(passwordField, 1, 2);
    	
    	//scene
    	Scene loginScene = new Scene(loginGrid, 1200, 1000);

        //buttons
		Button regInBtn = new Button("Register");
		regInBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String username = null;
				String password = null;
				if(userNameField.getText() != null && !userNameField.getText().isEmpty()){
					username = userNameField.getText();
				}else{
					//need to display this message on the display
					System.out.println("no account name entered");
					return;
				}
				if(passwordField.getText() != null && !passwordField.getText().isEmpty()){
					password = passwordField.getText();
				}else{
					//need to display this message on the display
					System.out.println("no password entered");
					return;
				}
				GameCommand gc = new GameCommand("regME");
				gc.s1 = username;
				gc.s2 = password;
				client.write(gc);
			}
		});
    	Button signInBtn = new Button("Sign in");
    	signInBtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
            	String username = null;
            	String password = null;
            	if(userNameField.getText() != null && !userNameField.getText().isEmpty()){
            		username = userNameField.getText();
            	}else{
            		//need to display this message on the display
            		System.out.println("no account name entered");
            		return;
            	}
            	if(passwordField.getText() != null && !passwordField.getText().isEmpty()){
            		password = passwordField.getText();
            	}else{
            		//need to display this message on the display
            		System.out.println("no password entered");
            		return;
            	}
            	GameCommand gc = new GameCommand("accountInfo");
            	gc.s1 = username;
            	gc.s2 = password;
				client.write(gc);
            }
        });




    	HBox hbBtn = new HBox(10);
    	hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
    	hbBtn.getChildren().add(signInBtn);
		hbBtn.getChildren().add(regInBtn);
    	loginGrid.add(hbBtn, 0, 4);
    	
    	/*
    	Button strtbtn = new Button();
        strtbtn.setText("Title Screen");
        strtbtn.setOnAction(new EventHandler<ActionEvent>() {
        	 
            @Override
            public void handle(ActionEvent event) {
            	openTitleScene();
            }
        });
        loginGrid.add(strtbtn, 1, 4);
        */
        loginLayout = loginGrid;
    	
    	primaryStage.setScene(loginScene);
    }
    
    void loginFailed(){
    	Label loginFailed = new Label("Incorrect Account Name or Password");
    	loginLayout.add(loginFailed, 1, 5);
    }
    void openTitleScene(){
    	//layouts
        StackPane titleLayout = new StackPane();
        GridPane titleButtons = new GridPane();
        titleLayout.getChildren().add(titleButtons);
        
        //scenes
        Scene titleScreen = new Scene (titleLayout, 1200, 1000);
        
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
        joinbtn.setText("Find Game");
        joinbtn.setOnAction(new EventHandler<ActionEvent>() {
       	 
            @Override
            public void handle(ActionEvent event) {
            	openGameSelectScene();
            }
        });
        
        //layout buttons
        titleButtons.setAlignment(Pos.CENTER);
        titleButtons.add(strtbtn, 10, 0);
        titleButtons.add(joinbtn, 10, 30);
        
        primaryStage.setScene(titleScreen);
        
    }
    // HERE IS THE SELECT GAME MENU &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
    public void openGameSelectScene(){
    	gameSelectLayout = new BorderPane();
    	Scene gameSelectScene = new Scene(gameSelectLayout, 1200, 1000);



		VBox gamesList = new VBox();
    	HBox menuButtons = new HBox();
    	
    	gameSelectLayout.setLeft(gamesList);
    	gameSelectLayout.setTop(menuButtons);
    	
    	//buttons
    	Button refresh = new Button();
        refresh.setText("Refresh List");
        refresh.setOnAction(new EventHandler<ActionEvent>() {
       	 
            @Override
            public void handle(ActionEvent event) {
            	GameCommand gc = new GameCommand("refreshGames");
				client.write(gc);
            }
        });
        
        //add buttons
        menuButtons.getChildren().add(refresh);
        
        GameCommand gc = new GameCommand("refreshGames");
		client.write(gc);
        
    	primaryStage.setScene(gameSelectScene);
    }
    
    public void refreshGamesList(ArrayList<GameIdentifier> openGames){
    	VBox gamesList = new VBox();
    	for(GameIdentifier game : openGames){
    		MenuButton joinableGame = new MenuButton();
    		joinableGame.n = game.id;
    		joinableGame.setText(game.name);
    		joinableGame.setOnAction(new EventHandler<ActionEvent>() {
           	 
                @Override
                public void handle(ActionEvent event) {
                	MenuButton btn = (MenuButton) event.getSource();
                	GameCommand gc = new GameCommand("joinGame");
                	gc.n = btn.n;
    				client.write(gc);
                }
            });
    		
    		gamesList.getChildren().add(joinableGame);
    	}
    	gameSelectLayout.setLeft(gamesList);
    }
    
    public void updateDisplay(DisplayGameState dgs){
    	if(dgs.winner == 1){
    		//display you win!
    		System.out.println("you win");
    		openGameSelectScene();
    		return;
    	}else if(dgs.winner == 2){
    		//display you lose!
    		System.out.println("you lose");
    		openGameSelectScene();
    		return;
    	}
    	displayGameState = dgs;
    	if(dgs.selectingAffectTarget){
    		affectSelection(dgs);
    		return;
    	}
    	if(affectSelection){
    		return;//do not update the display if input is needed for affect resolution
    	}
    	displayGameState = dgs;
    	
    	//create new scene
    	StackPane boardStack = boardLayoutMaker.getLayout();
    	mainStack = boardStack;
    	BorderPane boardLayout = (BorderPane) boardStack.getChildren().get(0);
    	Scene boardScene = new Scene(boardStack, 1200, 1000);
    	
    	//buttons
    	if(dgs.yourTurn){
    		setEndTurnButton(boardLayout);
    	}
    	Button concede = new Button();
        concede.setText("Concede");
        concede.setFont(new Font("Arial", 18));
        concede.setOnAction(new EventHandler<ActionEvent>() {
          	 
            @Override
            public void handle(ActionEvent event) {
            	GameCommand gc = new GameCommand("concede");
				client.write(gc);
            }
        });
        ((VBox)boardLayout.getLeft()).getChildren().add(concede);
        
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
    	affectSelection = true;
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
        		//to tell it to ignore the affect
        		/*
        		GameState gs = GameState.getGameState();
        		gs.selectingAffectTarget = false;
        		gs.affectStack.processing = false;
        		gs.affectStack.pauseProcessing = false;
        		gs.affectStack.afterSelectionAffect = null;
        		updateDisplay();
        		*/
        		GameCommand gc = new GameCommand("cancelAffect");
        		client.write(gc);
        		
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
    	cardText+= m.name + "    " + m.redCost + m.orangeCost 
    	+ m.yellowCost + m.greenCost + m.blueCost + m.purpleCost + "\n\n"
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
    		Button card = minionToButton.convertForField(m, 0);
    		GridPane gridPane = (GridPane) boardLayout.getCenter();
    		HBox bottomFieldHBox = (HBox) gridPane.getChildren().get(0);
    		bottomFieldHBox.getChildren().add(card);	
    		
    	}
    	
    	for(DisplayMinion m: dgs.enemyFieldMinions){
    		Button card = minionToButton.convertForField(m, 1);
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
    	
    	for(int i = 0; i < dgs.enemyHandSize; i++){
    		Rectangle rect = new Rectangle(20,20,200,105);
    	    
    	    rect.setArcHeight(15);
    	    rect.setArcWidth(15);
    	    
    	    Color c = Color.web("#ee1122");
    	    rect.setFill(c);
    		HBox topHBox = (HBox) boardLayout.getTop();
    		topHBox.getChildren().add(rect);
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
        p2Mana.setText("Mana: " + dgs.enemyRedMana + dgs.enemyOrangeMana + dgs.enemyYellowMana
        		+ dgs.enemyGreenMana + dgs.enemyBlueMana + dgs.enemyPurpleMana);
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
        p1Mana.setText("Mana: " + dgs.redMana + dgs.orangeMana + dgs.yellowMana
        		+ dgs.greenMana + dgs.blueMana + dgs.purpleMana);
        p1Mana.setFont(new Font("Arial", 25));
        p1Mana.setTextFill(Color.web("#38d0ff"));
        ((VBox)boardLayout.getLeft()).getChildren().add(p1Mana);
    }
    
}
