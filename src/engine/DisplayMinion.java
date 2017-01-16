package engine;

import java.io.Serializable;

public class DisplayMinion implements Serializable{
	
	private static final long serialVersionUID = -740483854398002466L;
	
	public Long uniqueId;
	public String name;
	public int redCost;
	public int orangeCost;
	public int yellowCost;
	public int greenCost;
	public int blueCost;
	public int purpleCost;
	public int atk;
	public int baseAtk;
	public int health;
	public int maxHealth;
	public String type;// curent types: humanoid beast machine
	public int owner;//0 for player 1, 1 for player 2
	public String affectText;
	public String triggerText;
	public int attacksThisTurn = 0;
	public int maxAttacks = 1;
	boolean summoningSickness;
	
	public int fieldLocation;//0 for my field, 1 for enemys field
	
}
