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
		CardButton cb = (CardButton)event.getSource();
		GameCommand gc = new GameCommand("affectTarget");
		gc.displayMinion1 = cb.minion;
		System.out.println("about to send affect select command to server thread");
		fxd.affectSelection = false;
		fxd.client.write(gc);
	}
}
