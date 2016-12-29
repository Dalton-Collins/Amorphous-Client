package engine;

import java.io.IOException;

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
			DisplayMinion dm = fxd.attackingMinion;
			GameCommand gc = new GameCommand("directAttack");
			gc.displayMinion1 = dm;
			fxd.client.write(gc);
		}
		
	}
	
}
