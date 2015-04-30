package main;

import java.util.ArrayList;

import domain.Game;
import domain.Player;
import domain.PlayerList;
import domain.Prize;

public class Main
{

    public static void main(String[] args)
    {
	// Game game = new Game();
	// game.play();
	ArrayList<Player> plist = new ArrayList<Player>();
	plist.add(new Player("player1"));
	plist.add(new Player("player2"));
	plist.add(new Player("player3"));
	plist.add(new Player("player4"));
	plist.add(new Player("player5"));
	for (int i = 0; i < 5; i++)
	{
	    for (int j = 0; j < i + 1; j++)
	    {
		plist.get(i).getPrizeList().add(new Prize("Prize" + (j + 1), j * 10, j));
		plist.get(i).setWorth((5-j)*10);
		plist.get(i).setCost(5-j);
		plist.get(i).setWaste(0);
	    }
	    System.out.println("-------------------------");
	    for (Prize p : plist.get(i).getPrizeList())
	    {
		System.out.println(p.getName());
	    }
	}
	System.out.println("===================");
	PlayerList playerList = new PlayerList(plist);
	playerList.sortByPrize();
	
	for (Player player : playerList.getpListByPrize())
	{
	    for (Prize p : player.getPrizeList())
	    {
		System.out.println(p.getName());
	    }
	    System.out.println("################");
//	    System.out.println(player.getName());
	}
    }
}
