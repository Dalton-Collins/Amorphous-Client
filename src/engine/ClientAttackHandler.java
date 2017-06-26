package engine;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ClientAttackHandler implements EventHandler<ActionEvent>{

	fxDisplay fxd;
	
	ClientAttackHandler(fxDisplay fxdd){
		fxd = fxdd;
	}
	@Override
	public void handle(ActionEvent event) {
		//selects a unit for attacking
		//and allows a unit to be attacked
		if(fxd.attackingMinion == null){
			CardButton cb = (CardButton)event.getSource();
			fxd.attackingMinion = cb.minion;
		}else{
			CardButton cb = (CardButton)event.getSource();
			GameCommand gc = new GameCommand("attack");
			gc.displayMinion1 = fxd.attackingMinion;
			gc.displayMinion2 = cb.minion;
			
			System.out.println(gc.displayMinion1.name + " attacking " + gc.displayMinion2.name);
			fxd.attackingMinion = null;
			
			fxd.client.write(gc);
		}
		
	}

}
