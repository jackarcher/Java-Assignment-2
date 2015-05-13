package domain;

import java.util.ArrayList;
import java.util.Collections;

import javax.xml.bind.ValidationException;

import comparator.ComparatorForPlayer;

/**
 * This class refer contains 2 ArrayList. One is a list contains players in the
 * original order, while the other one contains players in other orders(after
 * sort).
 * 
 * @author archer
 * 
 */
public class PlayerList
{
    /**
     * The list in original order.
     */
    private ArrayList<Player> playerList;
    /**
     * The ordered(sorted) list.
     */
    private ArrayList<Player> OrderedList;

    /**
     * Default constructor, the ordered list is initialized by the pList.
     */
    public PlayerList()
    {
	playerList = new ArrayList<Player>();
	OrderedList = new ArrayList<Player>(playerList);
    }

    /**
     * Similar to the add method in Arraylist.
     * 
     * @param player
     *            The player should be added to the array.
     * @return Whether the player added successfully, being true refers to
     *         successful.
     * @throws ValidationException
     */
    public boolean addPlayer(Player player) throws ValidationException
    {
	for (Player eachPlayer : playerList)
	{
	    if (player.getName().equalsIgnoreCase(eachPlayer.getName()))
		throw new ValidationException("Player already exist!");
	}
	return playerList.add(player) && OrderedList.add(player);
    }

    /**
     * The mutator to the field OrderedList.
     * 
     * @return The field OrderedList.
     */
    public ArrayList<Player> getOrderedList()
    {
	return OrderedList;
    }

    /**
     * The mutator to the field pList.
     * 
     * @return The field pList.
     */
    public ArrayList<Player> getPlayerList()
    {
	return playerList;
    }

    /**
     * Check if the "List" is empty.
     * 
     * @return True represents empty.
     */
    public boolean isEmpty()
    {
	return playerList.isEmpty();
    }

    /**
     * Get the number of elements the List contains.
     * 
     * @return the number of elements the List contains
     */
    public int size()
    {
	return playerList.size();
    }

    /**
     * Sort the ordered list by prize.
     */
    public void sortByPrize()
    {
	Collections.sort(OrderedList, new ComparatorForPlayer());
    }

    public boolean validation(Player newPlayer)
    {
	boolean flag = true;
	if (sameNamePlayer(newPlayer) != null)
	    flag = false;
	return flag;
    }

    public Player sameNamePlayer(Player newPlayer)
    {
	for (Player player : playerList)
	{
	    if (player.equals(newPlayer))
	    {
		return player;
	    }
	}
	return null;
    }

}
