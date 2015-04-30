package comparator;

import java.util.Comparator;

import domain.Player;

public class ComparatorForPlayer implements Comparator<Player>
{

    @Override
    public int compare(Player o1, Player o2)
    {
	// TODO Auto-generated method stub
	int result = o2.getWorth() - o1.getWorth();
	if (result == 0)
	    result = o2.getCost() - o1.getCost();
	if (result == 0)
	    result = o2.getWaste() - o1.getWaste();
	if (result == 0)
	    result = 0;
	return result;

    }

}
