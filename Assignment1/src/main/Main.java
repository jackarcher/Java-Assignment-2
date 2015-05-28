package main;

import java.util.ArrayList;

import javax.xml.bind.ValidationException;

import systemTools.Tools;
import domain.Game;
import domain.Player;
import domain.PlayerList;
import domain.Prize;

public class Main
{

    public static void main(String[] args) throws ValidationException
    {
	// ArrayList<Prize> p = new ArrayList<Prize>();
	// int t = 5;
	// p.add(new Prize("Pen", t * 10, t--));
	// p.add(new Prize("Book", t * 10, t--));
	// p.add(new Prize("DVD", t * 10, t--));
	// p.add(new Prize("Mouse", t * 10, t--));
	// p.add(new Prize("Keyboard", t * 10, t--));
	//
	// System.out.println(p.toString());
	//
	// sort(p);
	//
	// System.out.println(p.toString());

	Game game = new Game();
	game.play();

	// System.out.println(File.separator);
	//
	// File f = new File("src" + File.separator + "prize.txt");
	// System.out.println(f.getAbsolutePath());
	// System.out.println(f.exists());
	// FileInputStream fis = null;
	// StringBuffer sb = new StringBuffer();
	// try
	// {
	// fis = new FileInputStream(f);
	// System.out.println(fis.available());
	// byte[] b = new byte[fis.available()];
	// fis.read(b);
	// for (int i = 0; i < b.length; i++)
	// {
	// // System.out.print((char)b[i]);
	// sb.append((char)b[i]);
	// }
	// System.out.println(sb.toString());
	//
	// } catch (Exception e)
	// {
	// e.printStackTrace();
	// } finally
	// {
	// try
	// {
	// fis.close();
	// } catch (IOException e)
	// {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }
	// StringBuffer temp = new
	// StringBuffer(sb.toString().split(T // test use
	// for (int i = 0; i < sb.length(); i++)
	// {
	// temp = new StringBuffer(sb.toString().split(Tools.SEPARATOR)[i]);
	// String[] str = temp.toString().split(" ");
	// for (int j = 0; j < str.length; j++)
	// {
	// }
	// }

	// ArrayList<Player> plist = new ArrayList<Player>();
	// plist.add(new Player("player1"));
	// plist.add(new Player("player2"));
	// plist.add(new Player("player3"));
	// plist.add(new Player("player4"));
	// plist.add(new Player("player5"));
	// for (int i = 0; i < 5; i++)
	// {
	// for (int j = 0; j < i + 1; j++)
	// {
	// plist.get(i).getPrizeList().add(new Prize("Prize" + (j + 1), j * 10,
	// j));
	// plist.get(i).setWorth((5-j)*10);
	// plist.get(i).setCost(5-j);
	// plist.get(i).setWaste(0);
	// }
	// System.out.println("-------------------------");
	// for (Prize p : plist.get(i).getPrizeList())
	// {
	// System.out.println(p.getName());
	// }
	// }
	// System.out.println("===================");
	// PlayerList playerList = new PlayerList(plist);
	// playerList.sortByPrize();
	//
	// for (Player player : playerList.getpListByPrize())
	// {
	// for (Prize p : player.getPrizeList())
	// {
	// System.out.println(p.getName());
	// }
	// System.out.println("################");
	// System.out.println(player.getName());
	// }

	// ArrayList<Player> o = getSortedList();
	// for (int i = 0; i < 3; i++)
	// {
	// System.out.println(o.get(i).toString());
	// }
    }

    public static void exchange(int i, int j, ArrayList<Prize> list)
    {
	Prize temp = list.get(i);
	list.set(i, list.get(j));
	list.set(j, temp);
    }

    public static void sort(ArrayList<Prize> list)
    {
	for (int i = 0; i < list.size() - 1; i++)
	{
	    int min = i;
	    for (int j = i + 1; j < list.size(); j++)
	    {
		if (list.get(j).getCost() < list.get(min).getCost())
		{
		    min = j;
		}
	    }
	    exchange(i, min, list);
	}
    }

}
