package engine;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ClientSummonHandler implements EventHandler<ActionEvent>{

	fxDisplay fxd;
	
	public ClientSummonHandler(fxDisplay fxdd){
		fxd = fxdd;
	}
	
	@Override
	public void handle(ActionEvent event) {
		CardButton cb = (CardButton)event.getSource();
		GameCommand gc = new GameCommand("summon");
		gc.displayMinion1 = cb.minion;
		gc.n = fxd.displayGameState.handMinions.indexOf(cb.minion);
		fxd.client.write(gc);
	}
}
	