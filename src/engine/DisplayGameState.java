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

	DisplayMinion commander;
	DisplayMinion enemyCommander;
	
	public int winner = 0; //1 for you win, 2 for enemy wins
	public boolean selectingAttackTarget;
	public boolean selectingAffectTarget;
	
	boolean yourTurn;//true if the player given this object is the turn player
	int enemyHandSize;
	
	int redMana;
	int orangeMana;
	int yellowMana;
	int greenMana;
	int blueMana;
	int purpleMana;
	
	int maxRedMana;
	int maxOrangeMana;
	int maxYellowMana;
	int maxGreenMana;
	int maxBlueMana;
	int maxPurpleMana;
	int life;
	
	int enemyRedMana;
	int enemyOrangeMana;
	int enemyYellowMana;
	int enemyGreenMana;
	int enemyBlueMana;
	int enemyPurpleMana;
	
	int enemyMaxRedMana;
	int enemyMaxOrangeMana;
	int enemyMaxYellowMana;
	int enemyMaxGreenMana;
	int enemyMaxBlueMana;
	int enemyMaxPurpleMana;
	
	int enemyLife;
	
	public DisplayGameState(){
		handMinions = new ArrayList<DisplayMinion>();
		friendlyFieldMinions = new ArrayList<DisplayMinion>();
		enemyFieldMinions = new ArrayList<DisplayMinion>();
	}
	
}
