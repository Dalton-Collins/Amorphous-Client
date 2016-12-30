package engine;

import java.io.Serializable;
import java.util.ArrayList;

//this class holds all the information sent by the server
//that needs to be displayed to the client
public class DisplayGameState implements Serializable{

	private static final long serialVersionUID = 8046300629055914900L;
	
	ArrayList<DisplayMinion> handMinions;
	ArrayList<DisplayMinion> friendlyFieldMinions;
	ArrayList<DisplayMinion> enemyFieldMinions;
	
	int enemyHandSize;
	public boolean selectingAttackTarget;
	public boolean selectingAffectTarget;
	
	boolean yourTurn;//true if the player given this object is the turn player
	
	int mana;
	int maxMana;
	int life;
	
	int enemyMana;
	int enemyMaxMana;
	int enemyLife;
	
	public DisplayGameState(){
		handMinions = new ArrayList<DisplayMinion>();
		friendlyFieldMinions = new ArrayList<DisplayMinion>();
		enemyFieldMinions = new ArrayList<DisplayMinion>();
	}
	
}
