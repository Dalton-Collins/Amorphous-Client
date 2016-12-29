package engine;

import java.io.Serializable;

public class GameCommand implements Serializable{
	String commandType;
	DisplayMinion displayMinion1;
	DisplayMinion displayMinion2;
	int n;
	
	public GameCommand(String cmdtype){
		commandType = cmdtype;
	}
}
