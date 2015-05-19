package domain;

import java.util.ArrayList;

import javax.xml.bind.ValidationException;

import systemTools.Tools;

/**
 * Class player, contains all the information of a player.
 * 
 * @author archer
 * 
 */
public class Player
{
    /**
     * The name of the player.
     */
    private String name;
    /**
     * The prizes that the player has got.
     */
    private String prizes;// to be replaced by PrizeList
    // discuss with tutor on whether I can do this.
    /**
     * The prize list save all the prizes that the player has already won.
     */
    private ArrayList<Prize> prizeList;
    /**
     * The total worth of the prizes.
     */
    private int worth;
    /**
     * The money that the player has spend.
     */
    private int cost;
    /**
     * The money that the player has waste;
     */
    private int waste;

    /**
     * Default constructor. May seldom be called.
     * 
     * @throws ValidationException
     */
    public Player() throws ValidationException
    {
	this.name = "";
	this.prizes = "";
	this.prizeList = new ArrayList<Prize>();
	this.worth = 0;
	this.cost = 0;
	this.waste = 0;
	validation();
    }

    /**
     * Constructor using field name.
     * 
     * @param name
     *            The name of the player defined by the player.
     * @throws ValidationException
     */
    public Player(String name) throws ValidationException
    {
	this.name = name.trim();
	this.prizes = "";
	this.prizeList = new ArrayList<Prize>();
	this.worth = 0;
	this.cost = 0;
	this.waste = 0;
	validation();

    }

    /**
     * The getter of the field spent.
     * 
     * @return The money the player has spent.
     */
    public int getCost()
    {
	return cost;
    }

    /**
     * The getter of field name .
     * 
     * @return The name of the player.
     */
    public String getName()
    {
	return name;
    }

    /**
     * The getter of the field PrizeList.
     * 
     * @return the prize list that the player has already won.
     */
    public ArrayList<Prize> getPrizeList()
    {
	return prizeList;
    }

    /**
     * The getter of field prizes.
     * 
     * @return The prizes that the player has already got.
     */
    public String getPrizes()
    {
	return prizes;
    }

    /**
     * The getter of the field waste.
     * 
     * @return The money the player has waste.
     */
    public int getWaste()
    {
	return waste;
    }

    /**
     * The getter of the field worth of all the prizes the player has won.
     * 
     * @return The total worth of all the prizes
     */
    public int getWorth()
    {
	return worth;
    }

    /**
     * The setter of the field spent. Only ADD the money the player has just
     * spent.
     * 
     * @param cost
     *            The money the player has just spent(NOT TOTAL).
     */
    public void setCost(int cost)
    {
	this.cost += cost;
    }

    /**
     * The setter of the field name.
     * 
     * @param name
     *            The name that u wanna set.
     */
    public void setName(String name)
    {
	this.name = name;
    }

    /**
     * The setter of field prizes. Only ADD the new prize.
     * 
     * @param prizes
     *            The new prizes that should be ADDED to the prizes.
     */
    public void setPrizes(String prizes)
    {
	this.prizes = this.prizes + prizes + " ";
    }

    /**
     * The setter of the field waste. Only ADD the money the player has just
     * waste.
     * 
     * @param waste
     *            The money the player has just waste.
     */
    public void setWaste(int waste)
    {
	this.waste += waste;
    }

    /**
     * The setter of the field worth. Only ADD the worth of the new prizes.
     * 
     * @param worth
     *            The worth of the prize that the player has just won.
     */
    public void setWorth(int worth)
    {
	this.worth = this.worth + worth;
    }

    @Override
    public String toString()
    {
	// TODO Auto-generated method stub
	return "Player Name: " + name + Tools.SEPARATOR + "	total prize:" + worth;
    }

    public void validation() throws ValidationException
    {
	if (name.isEmpty())
	    throw new ValidationException("Empty Name is not permitted");
    }

    @Override
    public boolean equals(Object obj)
    {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Player other = (Player) obj;
	if (name == null)
	{
	    if (other.name != null)
		return false;
	} else if (!name.equals(other.name))
	    return false;
	return true;
    }
}
