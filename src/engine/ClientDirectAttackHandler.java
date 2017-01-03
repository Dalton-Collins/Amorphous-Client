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
			fxd.attackingMinion = null;
			fxd.client.write(gc);
		}
		
	}
	
}
