package engine;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

//this class will send commands to the server when the player clicks buttons
public class ClientEndTurnHandler implements EventHandler<ActionEvent>{
	
	fxDisplay fxd;
	
	public ClientEndTurnHandler(fxDisplay fxdd){
		fxd = fxdd;
	}
	
	@Override
	public void handle(ActionEvent event) {
		
		GameCommand gc = new GameCommand("endTurn");
		
		fxd.client.write(gc);
	}

}