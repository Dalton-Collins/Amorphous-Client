package engine;

import java.io.Serializable;

//this is used to give clients information on available games
//so they can select one and join it
public class GameIdentifier implements Serializable{

	private static final long serialVersionUID = -2762037831276042906L;
	int id;
	String name;
}
