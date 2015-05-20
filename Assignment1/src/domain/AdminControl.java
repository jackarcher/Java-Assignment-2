package domain;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import systemTools.Tools;

public class AdminControl
{
    /**
     * references the exactly same list at class Game
     */
    private ArrayList<Prize> systemPrizeList;

    /**
     * The only constructor in this class. Everyone wanna use this class will
     * have to give a prize lise.
     * 
     * @param prizeList
     *            references the exactly same list at class Game
     */
    public AdminControl(ArrayList<Prize> prizeList)
    {
	systemPrizeList = prizeList;
    }

    /**
     * This method is used to add a prize to the system prize list.
     * 
     * Inside this method, it will call a method inputPrize() in this class.
     */
    private void addPrize()
    {
	Prize input = inputPrize();
	if (input != null)
	{
	    if (prizeListValidation(input) && systemPrizeList.add(input))
		System.out.println("Add to list successfully.");
	    else
	    {
		System.out.println("Add to list fail.");
		System.out.println("	Detail: Validation fail: duplicate prize name.");
	    }
	}
	Tools.hold();
    }

    /**
     * This is a method to validate if the user has access to the admin
     * user(this class).
     * 
     * @return true if the passwod is the same as here. False if the user has
     *         tried 3 times.
     */
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

    /**
     * Displayer the admin menu.
     */
    private void displayMenu()
    {
	System.out.println(Tools.SEPARATOR + "		Admin Menu");
	System.out.println("  ---------------------------------------");
	System.out.println("	1.Add a prize.");
	System.out.println("	2.Remove a prize.");
	System.out.println("	3.Save Change.");
	System.out.println("	4.Check the prize list.");
	System.out.println("	5.Return to Player mode.");
	System.out.println("	6.Shut Down!");
	System.out.println("  ---------------------------------------");
	System.out.println("	Make your choice:");
    }

    /**
     * Let the user input some necessary information about the new prize
     * 
     * @return A new validated prize(validated here means the prize it self is
     *         legal, however it may be illegal to the prize list, which will be
     *         validate latter) or null if the user just wanna quit the add
     *         action.
     */
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
		System.out.println("Please input the worth of prize, input 0 to quit.");
		worth = Tools.inputInteger();
		if (worth == 0)
		    return null;
		System.out.println("Please input the cost of prize, input 0 to quit.");
		cost = Tools.inputInteger();
		if (cost == 0)
		    return null;
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

    /**
     * This method is to validate the newPrzie if it is validate to the prize
     * list.
     * 
     * @param newPrize
     *            the given newPrize
     * @return ture if the newPrize is legal to the prize list, which means
     *         there is no duplicate name in this list. Or false if there is.
     */
    public boolean prizeListValidation(Prize newPrize)
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

    /**
     * Remove a prize inside the prize list.
     */
    private void remove()
    {
	System.out.println("This is the Prize List");
	showPrizes();
	System.out.println(Tools.SEPARATOR + "Please input the No. you wanna remove, input 0 to quit.");
	int choice = Tools.inputInteger();
	if (choice == 0)
	    return;
	try
	{
	    systemPrizeList.remove(choice - 1);
	    Tools.hold();
	} catch (IndexOutOfBoundsException e)
	{
	    System.out.println("Please input a number within the range: 1 - " + systemPrizeList.size());
	    Tools.hold();
	    remove();
	}
    }

    /**
     * The main process here in this method.
     */
    public void runAdmin()
    {
	int choice = 0;
	while (true)
	{
	    displayMenu();
	    choice = Tools.inputInteger();
	    switch (choice)
	    {
		case 1:
		    addPrize();
		    break;
		case 2:
		    remove();
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
	String format = "|%5s|%" + longest + "s|%12s|%15s|" + Tools.SEPARATOR;
	/*
	 * http://examples.javacodegeeks.com/core-java/lang/string/java-string-
	 * format-example/
	 */
	System.out.printf(format, "No.", "Prize is", "Prize Worth", "Cost to player");
	for (Prize prize : systemPrizeList)
	{
	    System.out.printf(format, systemPrizeList.indexOf(prize) + 1, prize.getName(), prize.getWorth(), prize.getCost());
	}
    }

    /**
     * Write the prizelist to the txt file.
     */
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
