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
			gc.displayMinion1.cardPosition = fxd.displayGameState.friendlyFieldMinions.indexOf(fxd.attackingMinion);
			if(gc.displayMinion1.cardPosition == -1){
				System.out.println("couldn't find minion to attack with");
				return;
			}
			assert(gc.displayMinion1.cardPosition > -1);
			gc.displayMinion2 = cb.minion;
			gc.displayMinion2.cardPosition = fxd.displayGameState.enemyFieldMinions.indexOf(cb.minion);
			if(gc.displayMinion2.cardPosition == -1){
				System.out.println("couldn't find minion to attack");
				return;
			}
			assert(gc.displayMinion2.cardPosition > -1);
			fxd.attackingMinion = null;
			fxd.client.write(gc);
		}
		
	}

}
