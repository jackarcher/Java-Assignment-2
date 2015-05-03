package domain;

import java.io.File;
import java.util.ArrayList;

import systemTools.Tools;

public class AdminControl
{
    File prizeFile;

    public AdminControl()
    {
	prizeFile = new File("src" + File.separator + "prize.txt");
    }

    public void displayMenu()
    {
	System.out.println(Tools.SEPARATOR+"		Welcome Admin:");
	System.out.println("  ---------------------------------------");
	System.out.println("	1.Add a prize.");
	System.out.println("	2.Remove a prize.");
	System.out.println("	3.Save Change.");
	System.out.println("	4.Check the prize list.");
	System.out.println("	5.Return to Player mode.");
	System.out.println("	6.Exit");
	System.out.println("  ---------------------------------------");
	System.out.println("	Make your choice:");
    }

    public boolean addPrize(ArrayList<Prize> prizeList, Prize p)
    {
	return prizeList.add(p);
    }

    public void removePrize(ArrayList<Prize> prizeList, int choice)
    {
	prizeList.remove(choice);
    }
}
