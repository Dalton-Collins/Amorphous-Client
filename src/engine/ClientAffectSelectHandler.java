package engine;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ClientAffectSelectHandler implements EventHandler<ActionEvent>{

	fxDisplay fxd;
	
	public ClientAffectSelectHandler(fxDisplay fxdd){
		fxd = fxdd;
	}
	@Override
	public void handle(ActionEvent event) {
		CardButton target = (CardButton)event.getSource();
		
	}
	//this class should send an object to the
	//server gamestate AffectSelectHandler so
	//that it can be processed
	
	
}
