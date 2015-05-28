package domain;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.xml.bind.ValidationException;

import systemTools.Tools;

import comparator.SortForPrizeList;

/**
 * This class refer to the game admin, or an admin "console", in which the admin
 * can maintain the system prize list after validation.
 * 
 * @author archer
 *
 */
public class AdminControl
{
    /**
     * references the exactly same list at class Game
     */
    private ArrayList<Prize> systemPrizeList;

    private ArrayList<Prize> backUpList;
    /**
     * whether the change saved, true refers to saved.
     */
    private boolean saveFlag;

    /**
     * The only constructor in this class. Everyone wanna use this class will
     * have to give a prize lise.
     * 
     * @param prizeList
     *            references the exactly same list at class Game
     */
    public AdminControl()
    {
	systemPrizeList = new ArrayList<Prize>();
	saveFlag = true;
	load(readFromFile());
	backUpList = new ArrayList<Prize>(systemPrizeList);
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
	    {
		System.out.println("Add to list successfully.");
		saveFlag = false;
	    } else
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
    private boolean adminValidation()
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
	    if (pwd.equals("FIT9131"))
		return true;
	}
	System.out.println("You are not admin, are you?");
	System.out.println("You won't got permission until u reboot whole system.");
	return false;
    }

    /**
     * back up the back-up list.
     */
    private void BackUp()
    {
	backUpList.clear();
	backUpList.addAll(systemPrizeList);
	saveFlag = true;
    }

    /**
     * 
     * @return true means : ok to quit.
     */
    private boolean canQuit()
    {
	if (!saveFlag)
	{
	    System.out.println("unsaved change detect." + Tools.SEPARATOR);
	    System.out.println("what to do?");
	    System.out.println("1.Save");
	    System.out.println("2.Quit without save");
	    System.out.println("3.Cancle");
	    switch (Tools.inputInteger())
	    {
		case 1:
		    writeFile();
		    saveFlag = true;
		    return true;
		case 2:
		    systemPrizeList.clear();
		    systemPrizeList.addAll(backUpList);
		    saveFlag = true;
		    return true;
		case 3:
		    return false;
		default:
		    System.out.println("Unknow command, back to Admin menu");
		    return false;
	    }
	} else
	{
	    System.out.print('\u000C');
	    return true;
	}
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

    public ArrayList<Prize> getList()
    {
	return systemPrizeList;
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

    // need more validation?
    /**
     * 
     * @param sb
     */
    private void load(StringBuffer sb)
    {
	if (sb.length() == 0)
	    System.exit(0);
	String[] prize = sb.toString().split(Tools.SEPARATOR);
	Prize newPrize = null;
	System.out.println("Load report:");
	for (String attribute : prize)
	{
	    String[] temp = attribute.split(",");
	    if (temp.length == 3)
	    {
		try
		{
		    newPrize = new Prize(temp[0], Integer.parseInt(temp[1]), Integer.parseInt(temp[2]));
		    if (prizeListValidation(newPrize))
			systemPrizeList.add(newPrize);
		    else
			System.out.println("line: \"" + attribute + "\" has already exist. This line will be ignored");
		} catch (NumberFormatException e)
		{
		    System.out.println("line: \"" + attribute + "\" Worth or Cost onvert to integer fail, this line will be ignored");
		} catch (ValidationException e)
		{
		    System.out.println(e.getMessage());
		}
	    } else
	    {
		System.out.println("line: \"" + attribute + "\" is a illegal line");
	    }
	}
	Collections.sort(systemPrizeList, new SortForPrizeList());
	/*
	 * for (int i = 1; i < systemPrizeList.size(); i++) { if
	 * (systemPrizeList.get(i).getCost() == systemPrizeList.get(i -
	 * 1).getCost()) { System.out.println("Remove the same cost prize!" +
	 * Tools.SEPARATOR + "Detail:");
	 * System.out.println(systemPrizeList.get(i).toString());
	 * systemPrizeList.remove(i); } }
	 */
	// this.range = systemPrizeList.size();
	System.out.println("Load done!");
	Tools.hold();
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
    private boolean prizeListValidation(Prize newPrize)
    {
	if (newPrize == null)
	    return false;
	for (Prize prize : systemPrizeList)
	{
	    if (prize.equals(newPrize))
		return false;
	}
	return true;
    }

    /**
     * Load or reload the prize txt file, convert it to a StringBuffer.
     */
    private StringBuffer readFromFile()
    {
	FileInputStream fis = null;
	StringBuffer sb = new StringBuffer();
	try
	{
	    fis = new FileInputStream(Tools.prizeFile);
	    byte[] b = new byte[fis.available()];
	    fis.read(b);
	    for (int i = 0; i < b.length; i++)
	    {
		sb.append((char) b[i]);
	    }
	    System.out.println("Read from file successfully");
	    // Tools.delay(1);
	    Tools.hold();
	} catch (FileNotFoundException e)
	{
	    System.out.println(Tools.prizeFile.getAbsolutePath());
	    System.out.println("No such file");
	    System.exit(0);
	} catch (IOException e)
	{
	    e.printStackTrace();
	} finally
	{
	    try
	    {
		fis.close();
	    } catch (IOException e)
	    {
		e.printStackTrace();
	    }
	}
	return sb;
    }

    /**
     * Remove a prize inside the prize list.
     */
    private void removePrize()
    {
	System.out.println("This is the Prize List");
	showPrizes();
	System.out.println(Tools.SEPARATOR + "Please input the No. you wanna remove, input 0 to quit.");
	int choice = Tools.inputInteger();
	if (choice == 0)
	    return;
	try
	{
	    System.out.println("Prize:" + Tools.SEPARATOR + systemPrizeList.remove(choice - 1).getName() + Tools.SEPARATOR + "remove successfully");
	    saveFlag = false;
	    Tools.hold();
	} catch (IndexOutOfBoundsException e)
	{
	    System.out.println("Please input a number within the range: 1 - " + systemPrizeList.size());
	    Tools.hold();
	    removePrize();
	}
    }

    /**
     * The main process here in this method.
     * 
     * @return true if the admin validation pass, false if not.
     */
    public boolean runAdmin()
    {
	if (!adminValidation())
	    return false;
	int choice = 0;
	BackUp();
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
		    removePrize();
		    break;
		case 3:
		    writeFile();
		    break;
		case 4:
		    showPrizes();
		    Tools.hold();
		    break;
		case 5:
		    if (canQuit())
			return true;
		    else
			break;
		case 6:
		    if (canQuit())
			System.exit(0);
		    else
			break;
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
