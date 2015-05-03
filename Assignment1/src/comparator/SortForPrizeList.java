package comparator;

import java.util.Comparator;

import domain.Prize;

public class SortForPrizeList implements Comparator<Prize>
{

    @Override
    public int compare(Prize o1, Prize o2)
    {
	// TODO Auto-generated method stub
	return o1.getCost() - o2.getCost();
    }

}
