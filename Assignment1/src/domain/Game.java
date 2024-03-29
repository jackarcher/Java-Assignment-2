package domain;

import java.util.ArrayList;

import javax.xml.bind.ValidationException;

import systemTools.Tools;
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
     * references whether the user has right to try accessing the admin.
     */
    private boolean adminFlag;

    /**
     * The list contain all players.
     */
    private PlayerList playerList;

    /**
     * The new "window" for admin to control the whole system.
     */
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
	adminControl = new AdminControl();
	systemPrizeList = adminControl.getList();
	range = systemPrizeList.size();
	adminFlag = true;
    }

    /**
     * Compare the 2 numbers only if the userGuess pass the check!
     * 
     * @param userGuess
     *            the number user guessed.
     * @param systemGuess
     *            the random number system generated.
     */
    private void compareGuess(int userGuess, int systemGuess)
    {
	System.out.println("Your guess is " + userGuess);
	System.out.println("The lucky number is:" + systemGuess);
	Prize prize = systemPrizeList.get(userGuess - 1);
	if (systemGuess == userGuess)
	{
	    System.out.println("You are lucky! beacause you've just win a " + prize.getName() + "!");
	    player.getPrizeList().add(prize);
	    player.setWorth(prize.getWorth());
	} else
	{
	    System.out.println("Damn! You've just waste $" + prize.getCost() + " here!");
	    player.setWaste(prize.getCost());
	}
	player.setCost(prize.getCost());
	Tools.hold();
    }

    /**
     * Display the help information
     */
    public void displayGameHelp()
    {
	System.out.println("Here is some useful information!" + Tools.SEPARATOR);
	System.out.println("First, U will have to create a new player before you begin the game." + Tools.SEPARATOR);
	System.out.println("Second, if U do something wrong, just follow the instruction" + Tools.SEPARATOR);
	System.out.println("And here is the prize list!" + Tools.SEPARATOR);
	displayPrizes();
	System.out.println(Tools.SEPARATOR + "When you guess, choice the No. in the table." + Tools.SEPARATOR);
	System.out.println("Go win the prize!");
	Tools.hold();
    }

    /**
     * Display the most luckiest guy in the game.
     * 
     * @param i
     *            references the number that should be displayed.
     */
    private void displayLuckiest(int i)
    {
	displayPlayersInformation(i, false);
    }

    /**
     * Simply printout the Menu.
     */
    private void displayMenu()
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
	System.out.println("    (8) Admin Mode");
	System.out.println("  ============================================");
	System.out.println("  Choose an option :");
    }

    /**
     * Displayer all users information.
     */
    private void displayPlayersInformation()
    {
	displayPlayersInformation(26298090, true);
    }

    /**
     * Display some information about the players. user can select display all
     * or part of all the players. If the given number is greater than the
     * list's size, it will automatically display all players.
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
	    System.out.println("Sadly, no one has played with me Q.Q, would U please be the first?");
	// if display all the limit should be the size of the arraylist
	// if not display all and number is longer than the limit
	if (flag || number > playerList.size())
	    number = playerList.size();

	for (int i = 0; i < number; i++)
	{
	    if (!flag)
		System.out.println(playerList.getOrderedList().get(i).toString());
	    else
		System.out.println(playerList.getPlayerList().get(i).toString());
	}
	Tools.hold();
    }

    /**
     * Show a "table" in which contains all the information about the prize that
     * the machine can give out.
     */
    private void displayPrizes()
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
     * This method is used for show all the information about the player. Such
     * as his or her name, and the game status so far.
     */
    private void displayUsersInformation()
    {
	System.out.println("Dear " + this.player.getName() + "," + Tools.SEPARATOR);
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
		System.out.printf(format, i++, prize.getName(), prize.getWorth(), prize.getCost());
	    }
	    System.out.printf(format, "", "", "", "");
	    System.out.printf(format, "", "Waste", "", player.getWaste());
	    System.out.printf(format, "", "", "", "");
	    System.out.printf(format, "Total", "", player.getWorth(), player.getCost());
	} else if (player.getCost() != 0)
	    System.out.println("Em...I know U have spent $" + player.getCost() + " on me, but sometime it is your luck to blame, right?");
	else
	    System.out.println("U must be kidding me, u haven't spent even 1 cent on me!");
	Tools.hold();

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
	displayPrizes();
	System.out.println("Now Guess it! Input an integer from 1 - " + range + ".");
	System.out.println("The lucky number is:" + systemGuess); // test
	int userGuess = Tools.inputInteger();
	if (userGuess > range || userGuess < 1)
	{
	    throw new IllegalInputException("Please guess a number with in the range");
	}
	compareGuess(userGuess, systemGuess);
    }

    /**
     * The method that use for make choice. Basically choice only (and should
     * only) happens after the menu has been output, so this is a private method
     * which only be called by the menu method.
     * 
     * @throws IllegalInputException
     *             This exception happens when user input something other than
     *             integer 1-5 and let people choice again.
     * @return true if wanna display "welcome".
     */
    private boolean makeChoice() throws IllegalInputException
    {
	boolean displayWelcome = false;
	int choice = Tools.inputInteger();
	if ((choice == 2 || choice == 3) && player == null)
	{
	    System.out.println("You will have to create a new player first!" + Tools.SEPARATOR);
	    if (!setPlayer(3))
		return true;
	    if (choice == 3)
		return false;
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
		displayUsersInformation();
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
		System.out.println("Hope U have enjoyed the game!" + Tools.SEPARATOR);
		System.out.println("You can resume your game by input your name when creating new player!");
		player = null;
		Tools.hold();
		break;
	    case 8:
		if (!adminFlag)
		{
		    System.out.println("You have try 3 times");
		    Tools.hold();
		} else
		{
		    System.out.print('\u000C');
		    adminFlag = adminControl.runAdmin();
		    range = systemPrizeList.size();
		    System.out.print('\u000C');
		}
		break;
	    default:
		throw new IllegalInputException("Please make choice using integer range 1 -  8.");
	}
	return displayWelcome;
    }

    /**
     * The menu first output the menu itself and then call the method
     * makeChoice() for user to choice an option.
     */
    public void play()
    {
	System.out.println("	Welcome to the Lucky Vending Machine");
	while (true)
	{
	    displayMenu();
	    try
	    {
		if (makeChoice())
		{
		    System.out.println("	Welcome to the Lucky Vending Machine");
		}
	    } catch (IllegalInputException e)
	    {
		System.out.println(e.getMessage());
		Tools.delay(2);
	    }
	}
    }

    /**
     * Try resume the game for a existed player
     * 
     * @param newPlayer
     *            The new created player object, with the name existed.
     * @return Tue if the player resume the game, false if the player wanna pick
     *         a new name.
     */
    private boolean resumeGame(Player newPlayer)
    {
	System.out.println("Player already exist");
	System.out.println("Would you like to resume your ganme:y/n?");
	String answer = Tools.console.nextLine();
	if (answer.equalsIgnoreCase("y"))
	{
	    player = playerList.sameNamePlayer(newPlayer);
	    return true;
	} else if (answer.equalsIgnoreCase("n"))
	    return false;
	else
	{
	    System.out.println("only Y or N is accept");
	    return resumeGame(newPlayer);
	}
    }

    /**
     * Another option for setPlayer(), use as default one, ask the player until
     * he/she give the name.
     * 
     * @return True if player set successfully. False if anything goes wrong.
     */
    private boolean setPlayer()
    {
	return setPlayer(26298090, true);
    }

    /**
     * Another option for setPlayer(), ask the player a specific times to set
     * the player.
     * 
     * @return True if player set successfully. False if anything goes wrong.
     */
    private boolean setPlayer(int x)
    {
	return setPlayer(x, false);
    }

    /**
     * The setter of field player.
     * 
     * @param x
     *            the loop limit.
     * @param flag
     *            Whether to give player infinity chance to input name, being
     *            true for infinite loop
     * 
     */
    private boolean setPlayer(int x, boolean flag)
    {
	for (int i = 0; flag || i < x; i++)
	{
	    Player newPlayer;
	    System.out.println("Enter your name plz. Enter\"exit\" to quit:");
	    String temp = Tools.console.nextLine();
	    if (temp.equalsIgnoreCase("exit"))
	    {
		return false;
	    } else
	    {
		try
		{
		    newPlayer = new Player(temp);
		    if (playerList.validation(newPlayer))
		    {
			player = newPlayer;
			playerList.addPlayer(newPlayer);
			return true;
		    } else if (resumeGame(newPlayer))
		    {
			System.out.println("Hi," + this.player.getName() + ". Welcome to the LUCKY VENDING MACHINE!!");
			System.out.println("Loading...");
			Tools.delay(1, false);
			return true;
		    } else
			return false;
		} catch (ValidationException e)
		{
		    System.out.println(" Validation Fail" + Tools.SEPARATOR);
		    System.out.println(" Detail:" + e.getMessage());
		    System.out.println(Tools.SEPARATOR);
		}
	    }

	}
	System.out.println("U really just wanna play with me,right?");
	Tools.delay(10);
	return false;
    }

}
