package engine;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ClientDirectAttackHandler implements EventHandler<ActionEvent>{

	fxDisplay fxd;
	
	public ClientDirectAttackHandler(fxDisplay fxdd){
		fxd = fxdd;
	}
	
	@Override
	public void handle(ActionEvent event) {
		if(fxd.attackingMinion != null){
			GameCommand gc = new GameCommand("directAttack");
			gc.displayMinion1 = fxd.attackingMinion;
			gc.displayMinion1.cardPosition = fxd.displayGameState.friendlyFieldMinions.indexOf(fxd.attackingMinion);
			assert(gc.displayMinion1.cardPosition > -1);
			fxd.attackingMinion = null;
			fxd.client.write(gc);
		}
		
	}
	
}
