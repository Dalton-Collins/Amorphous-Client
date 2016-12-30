package engine;

import java.io.Serializable;

public class GameCommand implements Serializable{
	
	private static final long serialVersionUID = 1599402744907448809L;
	String commandType;
	DisplayMinion displayMinion1;
	DisplayMinion displayMinion2;
	int n;
	
	public GameCommand(String cmdtype){
		commandType = cmdtype;
	}
}
