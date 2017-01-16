package engine;

import javafx.scene.control.Button;

public class MinionToButton {
	
	fxDisplay fxd;
	
	public MinionToButton(fxDisplay fxdd){
		fxd = fxdd;
	}
	
	//this button is in the hand and can summon cards
	public Button convertForHand(DisplayMinion m){
		
		String cardText = "";
		cardText = cardText + m.name + " Cost " + m.redCost + m.orangeCost 
		    	+ m.yellowCost + m.greenCost + m.blueCost + m.purpleCost + "\n \n \n " + 
		"ATK " + m.atk + "   HP " + m.health;
		CardButton cb = new CardButton(cardText);
		cb.minion = m;
		cb.setStyle("-fx-font: 20 arial; -fx-base: #2211ee;");
		cb.setOnAction(fxd.clientSummonHandler);
		cb.setOnMouseEntered(fxd.cardViewHandler);
		cb.setOnMouseExited(fxd.cardViewHandler);
		return cb;
	}
	
	//this button is on the field and can attack
	public Button convertForField(DisplayMinion m, int color){
		
		String cardText = "";
		cardText = cardText + m.name + " Cost " + m.redCost + m.orangeCost 
		    	+ m.yellowCost + m.greenCost + m.blueCost + m.purpleCost+ "\n \n \n " + 
		"ATK " + m.atk + "   HP " + m.health;
		CardButton cb = new CardButton(cardText);
		cb.minion = m;
		if(color == 0){
			cb.setStyle("-fx-font: 20 arial; -fx-base: #2211ee;");
		}else{
			cb.setStyle("-fx-font: 20 arial; -fx-base: #ee1122;");
		}
		if(m.attacksThisTurn >= m.maxAttacks || m.summoningSickness){
			cb.setStyle("-fx-font: 20 arial; -fx-base: #777b82;");
		}
		cb.setOnAction(fxd.clientAttackHandler);
		cb.setOnMouseEntered(fxd.cardViewHandler);
		cb.setOnMouseExited(fxd.cardViewHandler);
		return cb;
	}
	
	public Button convertForEffectSelection(DisplayMinion m){
		
		String cardText = "";
		cardText = cardText + m.name + " Cost " + m.redCost + m.orangeCost 
		    	+ m.yellowCost + m.greenCost + m.blueCost + m.purpleCost +  "\n \n \n " + 
		"ATK " + m.atk + "   HP " + m.health;
		CardButton cb = new CardButton(cardText);
		cb.minion = m;
		if(m.owner == 0){
			cb.setStyle("-fx-font: 20 arial; -fx-base: #e4fc6c;");
		}else{
			cb.setStyle("-fx-font: 20 arial; -fx-base: #e4fc6c;");
		}
		cb.setOnAction(fxd.clientAffectSelectHandler);
		cb.setOnMouseEntered(fxd.cardViewHandler);
		cb.setOnMouseExited(fxd.cardViewHandler);
		return cb;
	}
	
	public Button convertForInaction(DisplayMinion m){
		String cardText = "";
		cardText = cardText + m.name + " Cost " + m.redCost + m.orangeCost 
		    	+ m.yellowCost + m.greenCost + m.blueCost + m.purpleCost +  "\n \n \n " + 
		"ATK " + m.atk + "   HP " + m.health;
		CardButton cb = new CardButton(cardText);
		cb.minion = m;
		cb.setStyle("-fx-font: 20 arial; -fx-base: #777b82;");
		return cb;
	}
}
