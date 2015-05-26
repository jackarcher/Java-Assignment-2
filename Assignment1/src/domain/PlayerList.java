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

    /**
     * Default constructor, the ordered list is initialized by the pList.
     */
    public PlayerList()
    {
	playerList = new ArrayList<Player>();
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
	return playerList.add(player);
    }

    /**
     * The mutator to the field OrderedList.
     * 
     * @return The field OrderedList.
     */
    public ArrayList<Player> getOrderedList()
    {
	ArrayList<Player> orderedList = new ArrayList<Player>(playerList);
	Collections.sort(orderedList, new ComparatorForPlayer());
	return orderedList;
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
     * Find a duplicate name player.
     * 
     * @param newPlayer
     *            the new created player.
     * @return the player if there is a player has the same name, null if the
     *         player does not exist.
     */
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

    /**
     * Get the number of elements the List contains.
     * 
     * @return the number of elements the List contains
     */
    public int size()
    {
	return playerList.size();
    }

//    /**
//     * Sort the ordered list by prize.
//     */
//    private void sortByPrize(ArrayList<Player> list)
//    {
//	Collections.sort(list, new ComparatorForPlayer());
//    }

    /**
     * Check if there is already an player has the name of newPlayer.
     * 
     * @param newPlayer
     *            New created player.
     * @return True if validation pass(no duplicate name), False if validation
     *         fail(player already exist).
     */
    public boolean validation(Player newPlayer)
    {
	boolean flag = true;
	if (sameNamePlayer(newPlayer) != null)
	    flag = false;
	return flag;
    }

}
