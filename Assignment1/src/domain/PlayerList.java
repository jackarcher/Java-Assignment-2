package domain;

import java.util.ArrayList;
import java.util.Collections;

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
    private ArrayList<Player> pList;
    /**
     * The ordered(sorted) list.
     */
    private ArrayList<Player> OrderedList;

    /**
     * Default constructor, the ordered list is initialized by the pList.
     */
    public PlayerList()
    {
	pList = new ArrayList<Player>();
	OrderedList = new ArrayList<Player>(pList);
    }

    /**
     * Similar to the add method in Arraylist.
     * 
     * @param player
     *            The player should be added to the array.
     * @return Whether the player added successfully, being true refers to
     *         successful.
     */
    public boolean addPlayer(Player player)
    {
	return pList.add(player) && OrderedList.add(player);
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
    public ArrayList<Player> getpList()
    {
	return pList;
    }

    /**
     * Check if the "List" is empty.
     * 
     * @return True represents empty.
     */
    public boolean isEmpty()
    {
	return pList.isEmpty();
    }

    /**
     * Get the number of elements the List contains.
     * 
     * @return the number of elements the List contains
     */
    public int size()
    {
	return pList.size();
    }

    /**
     * Sort the ordered list by prize.
     */
    public void sortByPrize()
    {
	Collections.sort(OrderedList, new ComparatorForPlayer());
    }
}
