package comparator;

import java.util.Comparator;

import domain.Player;

/**
 * This comparator is most common used in real word.
 * 
 * First, it check the prizes' total worth the player has already won, greater
 * worth is greater.
 * 
 * Second, it check the players' cost, less cost is greater.
 * 
 * Finally, it check the players' waste, less waste is greater.
 * 
 * After all, if they all the same, it will say they are equal, and the order
 * should keep original(like the earlier player is greater).
 * 
 * @author archer
 *
 */
public class ComparatorForPlayer implements Comparator<Player>
{

    /**
     * This method implements the rules that mentioned above.
     * 
     * @Override comparator
     */
    public int compare(Player o1, Player o2)
    {
	int result = o2.getWorth() - o1.getWorth();
	if (result == 0)
	    result = o2.getCost() - o1.getCost();
	if (result == 0)
	    result = o2.getWaste() - o1.getWaste();
	return result;

    }

}
