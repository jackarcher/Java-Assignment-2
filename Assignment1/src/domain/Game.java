package domain;

import java.util.ArrayList;
import java.util.Scanner;

import systemTools.Tools;
import Exceptions.IllegalInputException;

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
public class Game {
	/**
	 * Player refer to the object indicates a player.
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
	 * Console is an object used for user input.
	 */
	private Scanner console;

	/**
	 * Default constructor for this class, the player has been set to null for
	 * the game hasn't start yet.
	 */
	public Game() {
		this.player = new Player();
		this.luckyGuessGenerator = new LuckyGuessGenerator();
		this.console = new Scanner(System.in);
		this.systemPrizeList = new ArrayList<Prize>();
		// for further improve, here can be stored to a file.
		int t = 1;
		systemPrizeList.add(new Prize("Pen", t * 10, t++));
		systemPrizeList.add(new Prize("Book", t * 10, t++));
		systemPrizeList.add(new Prize("DVD", t * 10, t++));
		systemPrizeList.add(new Prize("Mouse", t * 10, t++));
		systemPrizeList.add(new Prize("Keyboard", t * 10, t++));
	}

	/**
	 * The menu first output the menu itself and then call the method
	 * makeChoice() for user to choice an option.
	 */
	public void runMenu() {
		System.out.println("Welcome to the Lucky Vending Machine");
		while (true) {
			showMenu();
			try {
				makeChoice();
			} catch (IllegalInputException e) {
				System.out.println(e.getMessage());
				delay(3);
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
	private void makeChoice() throws IllegalInputException {
		int choice;
		try {
			choice = console.nextInt();
		} catch (Exception e) {
			throw new IllegalInputException("Please make choice using integer.");
		}
		/*
		 * http://stackoverflow.com/questions/14898617/scanner-nextline-is-being-
		 * skipped This line below is added for the reason mentioned by this
		 * page.
		 */
		console.nextLine();
		/*
		 * If u wanna actually PLAY the game. u will have to create a new
		 * player. However if U only wanna check the game rule, this would'n be
		 * necessary.
		 */
		if ((choice == 2 || choice == 3) && player.getName().isEmpty()) {
			System.out.println("You will have to create a new player first!");
			choice = 1;
		}
		switchLoop: switch (choice) {
		case 1:
			for (int i = 0; i < 3; i++) {
				try {
					setPlayer();
					break switchLoop;
				} catch (IllegalInputException e) {
					System.out.println(e.getMessage());
					delay(3);
				}
			}
			System.out.println("U really just wanna play with me,right?");
			return;
		case 2:
			while (true) {
				try {
					guessPrize();
					break;
				} catch (IllegalInputException e1) {
					System.out.println(e1.getMessage());
				}
			}
			break;
		case 3:
			showUsersInformation();
			break;
		case 4:
			displayGameHelp();
			break;
		case 5:
			System.out.println("Hope U have enjoyed the game!");
			System.exit(0);
		default:
			throw new IllegalInputException(
					"Please make choice using integer range 1 - 5.");
		}
	}

	/**
	 * The setter of field player.
	 * 
	 * @param player
	 *            the player should be set.
	 * @throws IllegalInputException
	 */
	private void setPlayer() throws IllegalInputException {
		System.out.println("Enter your name plz:");
		String temp = console.nextLine();
		if (temp.isEmpty())
			throw new IllegalInputException("No one is NULL!");
		else {
			this.player.setName(temp);
			System.out.println("Hi," + this.player.getName()
					+ ". Welcome to the LUCKY VENDING MACHINE!!");
		}
	}

	
	/**
	 * This method is used for actually "play" the guess game.
	 * 
	 * @throws IllegalInputException
	 *             In this method, this exception happens usually because user
	 *             input something else rather than an integer. User should then
	 *             follow the instruction to do it over again.
	 */
	private void guessPrize() throws IllegalInputException {
		/*
		 * after the prizelist already done, all the things about 5 should be
		 * change to prizelist.size()
		 */
		System.out.println("Now Guess it! Input an integer from 1 - 5.");
		int systemGuess = luckyGuessGenerator.randomIntGenerator();
		System.out.println("The lucky number is:" + systemGuess); // test
		int userGuess;
		try {
			userGuess = console.nextInt();
		} catch (Exception e) {
			throw new IllegalInputException("Please input interger ONLY!");
		}
		try {
			compareGuess(userGuess, systemGuess);
		} catch (IllegalInputException ex) {
			throw ex;
		}
	}

	/**
	 * Compare the 2 numbers only if the userGuess pass the check!
	 * 
	 * @param userGuess
	 * @param systemGuess
	 */
	private void compareGuess(int userGuess, int systemGuess)
			throws IllegalInputException {
		if (userGuess > 5 || userGuess < 1) {
			throw new IllegalInputException(
					"Please guess a number with in the range");
		}
		System.out.println("Your guess is " + userGuess);
		System.out.println("The lucky number is:" + systemGuess);
		if (systemGuess == userGuess) {
			System.out.println("You are lucky! beacause you've just win a "
					+ systemPrizeList.get(systemGuess - 1).getName() + "!");
			player.getPrizeList().add(systemPrizeList.get(systemGuess - 1));// prize+=newprize
		} else {
			System.out.println("Damn! You've just waste some money here!");
		}
		player.setSpent(systemPrizeList.get(userGuess-1).getCost());// player.setSpent(); (spent++)

	}

	/**
	 * This method is used for show all the information about the player. Such
	 * as his or her name, and the game status so far.
	 */
	private void showUsersInformation() {
		System.out.println("Dear," + this.player.getName()+",");
		if (!player.getPrizeList().isEmpty()) {
			System.out.print("So far, U have won: ");
			int totalWorth = 0;
			for (Prize prize : player.getPrizeList()) {
				totalWorth += prize.getWorth();
				System.out.print(prize.getName() + " ");
			}
			System.out.println(".");
			System.out.println("Total worth is $" + totalWorth + ".");
			System.out.println("And U have spent $" + player.getSpent() + ".");
		} else if (player.getSpent() != 0)
			System.out
					.println("Em...I know U have spent $"+player.getSpent()+" on me, but sometime it is your luck to blame, right?");
		else
			System.out
					.println("U must be kidding me, u haven't spent even 1 cent on me!");
		hold();
	}

	/**
	 * Display the help information
	 */
	private void displayGameHelp() {
		System.out.println("Here is some useful information!");
		System.out
				.println("First,U will have to create a new player before you begin the game."
						+ Tools.SEPARATOR);
		System.out.println("And here is the prize list!" + Tools.SEPARATOR);
		System.out.println(constructPrizes());
		hold();
	}

	/**
	 * Construct an String contain a "table" shows all the information about the
	 * prize that the machine can give out.
	 * 
	 * @return One single String contains all the prizes.
	 */
	private String constructPrizes() {

		String p = "Number Generated" + "	" + "Prize is" + "	" + "Prize Worth"
				+ "	" + "Cost to player" + Tools.SEPARATOR;
		for (Prize prize : systemPrizeList) {
			p = p + (systemPrizeList.indexOf(prize) + 1) + "			"
					+ prize.getName() + "		" + prize.getWorth() + "		"
					+ prize.getCost() + Tools.SEPARATOR;
		}
		return p;
	}

	/**
	 * Simply printout the Menu.
	 */
	private void showMenu() {
		System.out.println("	Please choice an option:");
		System.out.println("====================================");
		System.out.println("(1) Set Up New Player");
		System.out.println("(2) Guess A Prize");
		System.out.println("(3) What Have I Won So Far?");
		System.out.println("(4) Display Game Help");
		System.out.println("(5) Exit Game");
		System.out.println("====================================");
		System.out.println("Choose an option :");
	}

	/**
	 * Hold the program for some seconds. Basically used for player to read some
	 * simply instructions.
	 * 
	 * @param delaytime
	 *            The number of the second that U wanna hold for the player.
	 */
	private void delay(int delaytime) {
		try {
			Thread.sleep(delaytime * 1000);
			// whether syso?
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
	}

	/**
	 * Hold the program until the user type any key. Basically used for player
	 * to read some long instruction or other system output(such as game help in
	 * this program).
	 */
	private void hold() {
		System.out.println(Tools.SEPARATOR + "Press <Enter> to continue....");
		console.nextLine();
	}
}
