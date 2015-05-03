package comparator;

import java.util.Comparator;
import domain.Player;

/**
 * the comparator for this assignment, compare the total worth of the prize the
 * players have already get, greater worth is greater.
 * 
 * @author archer
 *
 */
public class ComparatorByPrize implements Comparator<Player>
{
    /**
     * The comparator for this assignment.
     * 
     * @Override
     */
    public int compare(Player o1, Player o2)
    {
	return o2.getWorth() - o1.getWorth();
    }

}
