package domain;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import systemTools.Tools;
import comparator.SortForPrizeList;
import exceptions.IllegalInputException;

/**
 * This class refer to the game itself. As U can see, many method is actually
 * private. The only two public method is the constructor and runMenu(). The
 * 
 * The first one is used for construct the game, because this is not a singleton
 * pattern program, there is no need to private the constructor.
 * 
 * The second one is used for begin the game, so it should be a public one so
 * that others can actually play the game.
 * 
 * @author archer
 * 
 */
public class Game
{
    /**
     * Player refer to the object indicates a player right now.
     */
    private Player player;

    /**
     * LuckyGuessGenerator refer to the object indicates the random number
     * generator.
     */
    private LuckyGuessGenerator luckyGuessGenerator;
    /**
     * systemPrizeList is an ArrayList contains all the prizes in this machine.
     */
    private ArrayList<Prize> systemPrizeList;
    /**
     * Range here means how many prizes here in this program.
     */
    private int range;

    /**
     * 
     */
    private PlayerList playerList;

    private AdminControl adminControl;

    /**
     * Default constructor for this class, the player has been set to null for
     * the game hasn't start yet.
     */
    public Game()
    {
	playerList = new PlayerList();
	player = null;
	luckyGuessGenerator = new LuckyGuessGenerator();
	systemPrizeList = new ArrayList<Prize>();
	adminControl = new AdminControl(systemPrizeList);
	loadFile();
    }

    

    /**
     * Load or reload the prize file.
     */
    private void loadFile()
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
	    Tools.hold();
	} catch (FileNotFoundException e)
	{
	    System.out.println("No such file");
	    System.exit(0);
	} catch (IOException e)
	{
	    // IO exception
	    // to be improved
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

	if (sb.length() == 0)
	    System.exit(0);

	String[] eachPrize = sb.toString().split(Tools.SEPARATOR);
	for (int i = 0; i < eachPrize.length; i++)
	{
	    String[] temp = eachPrize[i].split(",");
	    if (temp.length == 3)
	    {
		systemPrizeList.add(new Prize(temp[0], Integer
			.parseInt(temp[1]), Integer.parseInt(temp[2])));
	    }
	}
	Collections.sort(systemPrizeList, new SortForPrizeList());
	for (int i = 1; i < systemPrizeList.size(); i++)
	{
	    if (systemPrizeList.get(i).getCost() == systemPrizeList.get(i - 1)
		    .getCost())
	    {
		System.out.println("Remove the same cost prize!"
			+ Tools.SEPARATOR + "Detail:");
		System.out.println(systemPrizeList.get(i).toString());
		systemPrizeList.remove(i);
	    }
	}
	this.range = systemPrizeList.size();
    }

    /**
     * The menu first output the menu itself and then call the method
     * makeChoice() for user to choice an option.
     */
    public void play()
    {
	System.out.println("Welcome to the Lucky Vending Machine");
	while (true)
	{
	    showMenu();
	    try
	    {
		makeChoice();
	    } catch (IllegalInputException e)
	    {
		System.out.println(e.getMessage());
		Tools.delay(2);
	    }
	}
    }

    /**
     * The method that use for make choice. Basically choice only (and should
     * only) happens after the menu has been output, so this is a private method
     * which only be called by the menu method.
     * 
     * @throws IllegalInputException
     *             This exception happens when user input something other than
     *             integer 1-5 and let people choice again.
     */
    private void makeChoice() throws IllegalInputException
    {
	int choice = Tools.input();
	if ((choice == 2 || choice == 3) && player == null)
	{
	    System.out.println("You will have to create a new player first!"
		    + Tools.SEPARATOR);
	    setPlayer(3);
	}
	switch (choice)
	{
	    case 1:
		setPlayer();
		break;
	    case 2:
		int systemGuess = luckyGuessGenerator.randomIntGenerator(range);
		// also u can use no paramount one
		while (true)
		{
		    try
		    {
			inputGuess(systemGuess);
			break;
		    } catch (IllegalInputException e)
		    {
			System.out.println(e.getMessage());
			Tools.delay(1);
		    }
		}
		break;
	    case 3:
		showUsersInformation();
		break;
	    case 4:
		displayLuckiest(3);
		break;
	    case 5:
		displayPlayersInformation();
		break;
	    case 6:
		displayGameHelp();
		break;
	    case 7:
		System.out.println("Hope U have enjoyed the game!");
		System.exit(0);
		break;
	    case 26298090:
		System.out.print('\u000C');
		adminControl.runAdmin();
		break;
	    default:
		throw new IllegalInputException(
			"Please make choice using integer range 1 - 7.");
	}
    }

    




    /**
     * 
     * @param i
     */
    private void displayLuckiest(int i)
    {
	playerList.sortByPrize();
	displayPlayersInformation(i, false);
    }

    /**
     * 
     */
    private void displayPlayersInformation()
    {
	displayPlayersInformation(26298090, true);
    }

    /**
     * 
     * @param number
     *            The max number of players u wanna display
     * @param flag
     *            Reference whether u wanna display all players information.
     *            Being true to display all. Being false to display only the
     *            given number.
     */
    private void displayPlayersInformation(int number, boolean flag)
    {
	if (playerList.isEmpty())
	    System.out
		    .println("Sadly, no one has played with me Q.Q, would U please be the first?");
	// if display all the limit should be the size of the arraylist
	if (flag)
	    number = playerList.size();
	// if not display all and number is longer than the limit
	else if (number > playerList.size())
	    number = playerList.size();

	for (int i = 0; i < number; i++)
	{
	    if (!flag)
		System.out.println(playerList.getOrderedList().get(i)
			.toString());
	    else
		System.out.println(playerList.getpList().get(i).toString());
	}
	Tools.hold();
    }

    private void setPlayer()
    {
	setPlayer(26298090, true);
    }

    private void setPlayer(int x)
    {
	setPlayer(x, false);
    }

    /**
     * The setter of field player.
     * 
     * @param player
     *            the player should be set.
     * @throws IllegalInputException
     */
    private void setPlayer(int x, boolean flag)
    {
	for (int i = 0; true; i++)
	{
	    if (!flag && i >= x)
		break;
	    System.out.println("Enter your name plz:");
	    String temp = Tools.console.nextLine();
	    if (temp.isEmpty())
	    {
		System.out.println("No one is NULL!");
		Tools.delay(1);
		System.out.print('\u000C');
	    } else
	    {
		player = new Player(temp);
		playerList.addPlayer(player);
		System.out.println("Hi," + this.player.getName()
			+ ". Welcome to the LUCKY VENDING MACHINE!!");
		System.out.println("Loading...");
		Tools.delay(1, false);
		return;
	    }

	}
	System.out.println("U really just wanna play with me,right?");
	System.exit(0);
    }

    /**
     * This method is used for actually "play" the guess game.
     * 
     * @throws IllegalInputException
     *             In this method, this exception happens usually because user
     *             input something else rather than an integer. User should then
     *             follow the instruction to do it over again.
     */
    private void inputGuess(int systemGuess) throws IllegalInputException
    {
	System.out.println("Now Guess it! Input an integer from 1 - " + range
		+ ".");
	System.out.println("The lucky number is:" + systemGuess); // test
	int userGuess;
	try
	{
	    userGuess = Tools.console.nextInt();
	} catch (Exception e)
	{
	    throw new IllegalInputException("Please input interger ONLY!");
	} finally
	{
	    Tools.console.nextLine();
	}
	if (userGuess > range || userGuess < 1)
	{
	    throw new IllegalInputException(
		    "Please guess a number with in the range");
	}
	compareGuess(userGuess, systemGuess);
    }

    /**
     * Compare the 2 numbers only if the userGuess pass the check!
     * 
     * @param userGuess
     * @param systemGuess
     */
    private void compareGuess(int userGuess, int systemGuess)
    {
	System.out.println("Your guess is " + userGuess);
	System.out.println("The lucky number is:" + systemGuess);
	if (systemGuess == userGuess)
	{
	    System.out.println("You are lucky! beacause you've just win a "
		    + systemPrizeList.get(systemGuess - 1).getName() + "!");
	    player.getPrizeList().add(systemPrizeList.get(systemGuess - 1));// prize+=newprize
	    player.setPrizes(systemPrizeList.get(systemGuess - 1).getName());
	    player.setWorth(systemPrizeList.get(systemGuess - 1).getWorth());
	} else
	{
	    System.out.println("Damn! You've just waste $" + userGuess
		    + " here!");
	    player.setWaste(userGuess);
	}
	player.setCost(systemPrizeList.get(userGuess - 1).getCost());// player.setSpent();
	// (spent++)
	Tools.hold();
    }

    /**
     * This method is used for show all the information about the player. Such
     * as his or her name, and the game status so far.
     */
    private void showUsersInformation()
    {
	System.out.println("Dear" + this.player.getName() + ","
		+ Tools.SEPARATOR);
	if (!player.getPrizeList().isEmpty())
	{
	    System.out.println("So far, U have won: " + Tools.SEPARATOR);
	    int longest = 0;
	    for (Prize prize : player.getPrizeList())
	    {
		if (longest < prize.getName().length())
		    longest = prize.getName().length();
	    }
	    longest += 3;
	    String format = "|%5s|%" + longest + "s|%7s|%7s|" + Tools.SEPARATOR;
	    System.out.printf(format, "No", "Prize", " Worth", "Cost");
	    int i = 1;
	    for (Prize prize : player.getPrizeList())
	    {
		System.out.printf(format, i++, prize.getName(),
			prize.getWorth(), prize.getCost());
	    }
	    System.out.printf(format, "", "", "", "");
	    System.out.printf(format, "", "Waste", "", player.getWaste());
	    System.out.printf(format, "", "", "", "");
	    System.out.printf(format, "Total", "", player.getWorth(),
		    player.getCost());
	} else if (player.getCost() != 0)
	    System.out.println("Em...I know U have spent $" + player.getCost()
		    + " on me, but sometime it is your luck to blame, right?");
	else
	    System.out
		    .println("U must be kidding me, u haven't spent even 1 cent on me!");
	Tools.hold();

    }

    /**
     * Display the help information
     */
    public void displayGameHelp()
    {
	System.out
		.println("Here is some useful information!" + Tools.SEPARATOR);
	System.out
		.println("First, U will have to create a new player before you begin the game."
			+ Tools.SEPARATOR);
	System.out
		.println("Second, if U do something wrong, just follow the instruction"
			+ Tools.SEPARATOR);
	System.out.println("And here is the prize list!" + Tools.SEPARATOR);
	showPrizes();
	Tools.hold();
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

    /**
     * Simply printout the Menu.
     */
    private void showMenu()
    {
	System.out.printf("  %24s" + Tools.SEPARATOR, "Menu");
	System.out.println("  ============================================");
	System.out.println("    (1) Set Up New Player");
	System.out.println("    (2) Guess A Prize");
	System.out.println("    (3) What Have I Won So Far?");
	System.out.println("    (4) Who are the top 3 luckiest players?");
	System.out.println("    (5) Display all players's statistics");
	System.out.println("    (6) Display Game Help");
	System.out.println("    (7) Exit Game");
	System.out.println("  ============================================");
	System.out.println("  Choose an option :");
    }

}
