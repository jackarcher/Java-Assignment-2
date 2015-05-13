package domain;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import systemTools.Tools;
import exceptions.IllegalInputException;

public class AdminControl
{
    private ArrayList<Prize> systemPrizeList;

    public AdminControl(ArrayList<Prize> prizeList)
    {
	systemPrizeList = prizeList;
    }

    public boolean adminValidation()
    {
	/*
	 * http://stackoverflow.com/questions/8138411/masking-password-input-from
	 * -the -console-java
	 * 
	 * password input reference this page
	 */
	/*
	 * the one above won't work either in BlueJ or eclipse
	 * 
	 * long term bug reported:
	 * https://bugs.eclipse.org/bugs/show_bug.cgi?id=122429
	 */
	String pwd;
	for (int i = 3; i > 0; i--)
	{
	    System.out.println("You have " + i + " times left");
	    System.out.print("Password: ");
	    pwd = Tools.console.nextLine();
	    if (pwd.equals("12345"))
		return true;
	}
	System.out.println("You are not admin, are you?");
	System.out.println("You won't got permission until u reboot whole system.");
	return false;
    }

    private boolean prizeListValidation(Prize newPrize)
    {
	boolean flag = true;
	if (newPrize == null)
	    flag = false;
	for (Prize prize : systemPrizeList)
	{
	    if (prize.equals(newPrize))
		flag = false;
	    break;
	}
	return flag;
    }

    private void displayMenu()
    {
	System.out.println(Tools.SEPARATOR + "		Admin Menu");
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

    private Prize inputPrize()
    {
	String name;
	int worth;
	int cost;
	while (true)
	{
	    try
	    {
		System.out.println("Please input the name of prize, input \"exit\" to quit");
		name = Tools.console.nextLine();
		if (name.equalsIgnoreCase("exit"))
		    return null;
		System.out.println("Please input the worth of prize");
		worth = Tools.inputInteger("Worth should be integer");
		System.out.println("Please input the cost of prize");
		cost = Tools.inputInteger("Cost should be integer");
		Prize newPrize = new Prize(name, worth, cost);
		return newPrize;
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

    private int inputRemove()
    {
	System.out.println("This is the Prize List");
	for (int i = 0; i < systemPrizeList.size(); i++)
	{
	    System.out.println("No." + i + ". " + systemPrizeList.get(i).toString());
	}
	System.out.println(Tools.SEPARATOR + "Please input the one you wanna remove");
	int result = 0;
	while (true)
	{
	    try
	    {
		result = Tools.inputInteger();
		return result;
	    } catch (IllegalInputException e)
	    {
		System.out.println(e.getMessage());
	    }
	}
    }

    public void runAdmin()
    {
	int choice = 0;
	while (true)
	{
	    displayMenu();
	    try
	    {
		choice = Tools.inputInteger("Integer accept only.");
	    } catch (IllegalInputException e)
	    {
		System.out.println(e.getMessage());
		Tools.delay(1);
		continue;
	    }
	    switch (choice)
	    {
		case 1:
		    Prize input = inputPrize();
		    // validation
		    if (prizeListValidation(input))
		    {
			systemPrizeList.add(input);
			Tools.hold();
		    }
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
		    System.out.println("Please make choice using integer range 1 - 6.");
		    break;
	    }
	}
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
	System.out.printf(format, "Number Generated", "Prize is", "Prize Worth", "Cost to player");
	for (Prize prize : systemPrizeList)
	{
	    System.out.printf(format, systemPrizeList.indexOf(prize) + 1, prize.getName(), prize.getWorth(), prize.getCost());
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

}
