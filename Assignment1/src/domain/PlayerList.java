package domain;

import java.util.ArrayList;
import java.util.Collections;

import comparator.ComparatorForPlayer;

public class PlayerList
{
    private ArrayList<Player> pList;
    private ArrayList<Player> pListByPrize;

    public PlayerList()
    {
	pList = new ArrayList<Player>();
	pListByPrize = new ArrayList<Player>(pList);
    }

    // public PlayerList(ArrayList<Player> pList)
    // {
    // super();
    // this.pList = pList;
    // pListByPrize = new ArrayList<Player>(pList);
    // }
    // test use

    public boolean addPlayer(Player player)
    {
	return pList.add(player) && pListByPrize.add(player);
    }

    public void sortByPrize()
    {
	Collections.sort(pListByPrize, new ComparatorForPlayer());
    }

    public ArrayList<Player> getpList()
    {
	return pList;
    }

    public ArrayList<Player> getpListByPrize()
    {
	return pListByPrize;
    }

    public boolean isEmpty()
    {
	return pList.isEmpty();
    }
    
    public int size(){
	return pList.size();
    }
}
