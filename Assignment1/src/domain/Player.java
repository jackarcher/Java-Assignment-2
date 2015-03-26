package domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Class player, contains all the information of a player.
 * 
 * @author archer
 *
 */
public class Player {
	/**
	 * The name of the player.
	 */
	private String name;
	/**
	 * The prizes that the player has got.
	 */
	private String prizes;// to be replaced by PrizeList
	//discuss with tutor on whether I can do this.
	/**
	 * The prize list save all the prizes that the player has already won.
	 */
	private List<Prize> prizeList;
	/**
	 * The total worth of the prizes.
	 */
	private int worth;
	/**
	 * The money that the player has spend.
	 */
	private int spent;

	/**
	 * Default constructor. May never be called.
	 */
	public Player() {
		this.name = "new";
		this.prizes = "";
		this.prizeList = new ArrayList<Prize>();
		this.worth = 0;
		this.spent = 0;
	}

	/**
	 * Constructor using field name.
	 * 
	 * @param name
	 *            The name of the player defined by the player.
	 */
	public Player(String name) {
		this.name = name;
		this.prizes = "";
		this.worth = 0;
		this.spent = 0;
	}

	/**
	 * The getter of field name .
	 * 
	 * @return The name of the player.
	 */
	public String getName() {
		return name;
	}

	/**
	 * The getter of field prizes.
	 * 
	 * @return The prizes that the player has already got.
	 */
	public String getPrizes() {
		return prizes;
	}

	/**
	 * The setter of field prizes. Only ADD the new prize.
	 * 
	 * @param prizes
	 *            The new prizes that should be ADDED to the prizes.
	 */
	public void setPrizes(String prizes) {
		this.prizes = this.prizes + " " + prizes;
	}

	/**
	 * The getter of the field worth of all the prizes the player has won.
	 * 
	 * @return The total worth of all the prizes
	 */
	public int getWorth() {
		return worth;
	}

	/**
	 * The setter of the field worth. Only ADD the worth of the new prizes.
	 * 
	 * @param worth
	 *            The worth of the prize that the player has just won.
	 */
	public void setWorth(int worth) {
		this.worth = this.worth + worth;
	}

	/**
	 * The getter of the field spent.
	 * 
	 * @return The money the player has spent.
	 */
	public int getSpent() {
		return spent;
	}

	/**
	 * The setter of the field spent. Only ADD the money the player has just
	 * spent.
	 * 
	 * @param spent
	 *            The money the player has just spent(not total).
	 */
	public void setSpent(int spent) {
		this.spent = this.spent + spent;
	}
}