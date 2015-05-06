package domain;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import exceptions.IllegalInputException;
import systemTools.Tools;

public class AdminControl
{
    private ArrayList<Prize> systemPrizeList;

    public AdminControl(ArrayList<Prize> p)
    {
	systemPrizeList = p;
    }

    private void displayMenu()
    {
	System.out.println(Tools.SEPARATOR + "		Welcome Admin:");
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

    public void runAdmin()
    {

	while (true)
	{
	    displayMenu();
	    int choice = Tools.input();
	    switch (choice)
	    {
		case 1:
		    Prize input = inputPrize();
		    if (input == null)
		    {
			break;
		    }
		    systemPrizeList.add(input);
		    Tools.hold();
		    break;
		case 2:
		    systemPrizeList.remove(inputRemove());
		    Tools.hold();
		    break;
		case 3:
		    writeFile();
		    break;
		case 4:
		    showPrizes();
		    Tools.hold();
		    break;
		case 5:
		    System.out.print('\u000C');
		    return;
		case 6:
		    System.exit(0);
		default:
		    System.out
			    .println("Please make choice using integer range 1 - 6.");
		    choice = Tools.input();
		    break;
	    }
	}
    }

    private int inputRemove()
    {
	System.out.println("This is the Prize List");
	for (int i = 0; i < systemPrizeList.size(); i++)
	{
	    System.out.println("No." + i + ". "
		    + systemPrizeList.get(i).toString());
	}
	System.out.println(Tools.SEPARATOR
		+ "Please input the one you wanna remove");
	int result = 0;
	while (true)
	{
	    try
	    {
		result = Tools.input();
		return result;
	    } catch (IllegalInputException e)
	    {
		System.out.println(e.getMessage());
	    }
	}
    }

    private Prize inputPrize()
    {
	String name;
	int worth;
	int cost;
	while (true)
	{
	    try
	    {
		System.out
			.println("Please input the name of prize, input \"exit\" to quit");
		name = Tools.console.nextLine();
		if (name.isEmpty())
		    throw new IllegalInputException("No prize is null");
		if (name.compareTo("exit") == 0)
		    return null;
		System.out.println("Please input the worth of prize");
		worth = Tools.input();
		System.out.println("Please input the cost of prize");
		cost = Tools.input();
		if (validateCost(cost))
		    return new Prize(name, worth, cost);
		else
		    throw new RuntimeException(
			    "The cost U input has already exist.");
	    } catch (Exception e)
	    {
		System.out.println("Validation fail." + Tools.SEPARATOR);
		System.out.println("Detail:");
		System.out.println(e.getMessage() + Tools.SEPARATOR);
		System.out.println("Please do it again" + Tools.SEPARATOR);
		continue;
	    }
	}
    }

    private void writeFile()
    {
	FileOutputStream fos = null;
	StringBuffer sb = new StringBuffer();
	for (Prize p : systemPrizeList)
	{
	    sb.append(p.toString());
	}
	byte[] b = sb.toString().getBytes();
	try
	{
	    fos = new FileOutputStream(Tools.prizeFile);
	    fos.write(b);
	    System.out.println("Write to file successfully.");
	    Tools.hold();
	} catch (Exception e)
	{
	    e.printStackTrace();
	} finally
	{
	    try
	    {
		fos.close();

	    } catch (IOException e)
	    {
		e.printStackTrace();
	    }
	}
    }

    private boolean validateCost(int cost)
    {
	boolean flag = true;
	for (Prize prize : systemPrizeList)
	{
	    if (cost == prize.getCost())
	    {
		flag = false;
		break;
	    }
	}
	return flag;
    }

    /**
     * Show a "table" in which contains all the information about the prize that
     * the machine can give out.
     */
    private void showPrizes()
    {
	int longest = 0;
	for (Prize prize : systemPrizeList)
	{
	    if (longest < prize.getName().length())
		longest = prize.getName().length();
	}
	longest += 3;
	String format = "|%17s|%" + longest + "s|%12s|%15s|" + Tools.SEPARATOR;
	/*
	 * http://examples.javacodegeeks.com/core-java/lang/string/java-string-
	 * format-example/
	 */
	System.out.printf(format, "Number Generated", "Prize is",
		"Prize Worth", "Cost to player");
	for (Prize prize : systemPrizeList)
	{
	    System.out.printf(format, systemPrizeList.indexOf(prize) + 1,
		    prize.getName(), prize.getWorth(), prize.getCost());
	}
    }

}