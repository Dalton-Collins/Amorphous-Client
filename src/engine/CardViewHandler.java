package engine;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class CardViewHandler implements EventHandler<MouseEvent>{
	
	fxDisplay fxd;
	public CardViewHandler(fxDisplay fx){
		fxd= fx;
	}
	@Override
	public void handle(MouseEvent event) {
		if(fxd.displayingDetailedCard){
			//remove the detailed card display
			fxd.removeDetailedCard();
		}else{
			//display the detailed card
			CardButton cb = (CardButton)event.getSource();
			fxd.displayDetailedCard(cb.minion);
		}
	}

}
