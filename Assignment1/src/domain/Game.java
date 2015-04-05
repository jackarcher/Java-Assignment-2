package domain;

import java.util.ArrayList;
import java.util.Scanner;

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
	 * Range here means how many prizes here in this program.
	 */
	private int range;
	/**
	 * Console is an object used for user input.
	 */
	private Scanner console;

	/**
	 * Default constructor for this class, the player has been set to null for
	 * the game hasn't start yet.
	 */
	public Game() {
		this.player = null;
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
		systemPrizeList.add(new Prize("TestOne", t * 10, t++));
		systemPrizeList.add(new Prize("asdfasdfsdafsdafasdf", t * 10, t++));
		this.range = systemPrizeList.size();
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
			console.nextLine();
			throw new IllegalInputException("Please make choice using integer.");
		}
		/*
		 * http://stackoverflow.com/questions/14898617/scanner-nextline-is-being-
		 * skipped This line below is added for the reason mentioned by this
		 * page.
		 */
		console.nextLine();
		if ((choice == 2 || choice == 3) && player == null) {
			System.out.println("You will have to create a new player first!"
					+ Tools.SEPARATOR);
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
			System.exit(0);
		case 2:
			int systemGuess = luckyGuessGenerator.randomIntGenerator(range);
			// also u can use no paramount one
			while (true) {
				try {
					inputGuess(systemGuess);
					break;
				} catch (IllegalInputException e1) {
					System.out.println(e1.getMessage());
					delay(3);
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
			break;
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
	public void setPlayer() throws IllegalInputException {
		System.out.println("Enter your name plz:");
		String temp = console.nextLine();
		if (temp.isEmpty())
			throw new IllegalInputException("No one is NULL!");
		else {
			player = new Player(temp);
			System.out.println("Hi," + this.player.getName()
					+ ". Welcome to the LUCKY VENDING MACHINE!!");
			System.out.println("Loading...");
			delay(2, false);
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
	public void inputGuess(int systemGuess) throws IllegalInputException {
		System.out.println("Now Guess it! Input an integer from 1 - " + range
				+ ".");
		System.out.println("The lucky number is:" + systemGuess); // test
		int userGuess;
		try {
			userGuess = console.nextInt();
		} catch (Exception e) {
			console.nextLine();
			throw new IllegalInputException("Please input interger ONLY!");
		}
		if (userGuess > range || userGuess < 1) {
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
	private void compareGuess(int userGuess, int systemGuess) {
		System.out.println("Your guess is " + userGuess);
		System.out.println("The lucky number is:" + systemGuess);
		if (systemGuess == userGuess) {
			System.out.println("You are lucky! beacause you've just win a "
					+ systemPrizeList.get(systemGuess - 1).getName() + "!");
			player.getPrizeList().add(systemPrizeList.get(systemGuess - 1));// prize+=newprize
			player.setWorth(systemPrizeList.get(systemGuess - 1).getWorth());
		} else {
			System.out.println("Damn! You've just waste some money here!");
		}
		player.setSpent(systemPrizeList.get(userGuess - 1).getCost());// player.setSpent();
																		// (spent++)
	}

	/**
	 * This method is used for show all the information about the player. Such
	 * as his or her name, and the game status so far.
	 */
	public void showUsersInformation() {
		System.out.println("Dear" + this.player.getName() + ",");
		if (!player.getPrizeList().isEmpty()) {
			System.out.print("So far, U have won: ");
			for (Prize prize : player.getPrizeList()) {
				System.out.print(prize.getName() + " ");
			}
			System.out.println(".");
			System.out.println("Total worth is $" + player.getWorth() + ".");
			System.out.println("And U have spent $" + player.getSpent() + ".");
		} else if (player.getSpent() != 0)
			System.out.println("Em...I know U have spent $" + player.getSpent()
					+ " on me, but sometime it is your luck to blame, right?");
		else
			System.out
					.println("U must be kidding me, u haven't spent even 1 cent on me!");
		hold();
	}

	/**
	 * Display the help information
	 */
	public void displayGameHelp() {
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
		hold();
	}

	/**
	 * Show a "table" in which contains all the information about the prize that
	 * the machine can give out.
	 */
	private void showPrizes() {
		int longest = 0;
		for (Prize prize : systemPrizeList) {
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
		int i = 1;
		for (Prize prize : systemPrizeList) {
			System.out.printf(format, i++, prize.getName(), prize.getWorth(),
					prize.getCost());
		}
	}

	/**
	 * Simply printout the Menu.
	 */
	private void showMenu() {
		System.out.printf("%16s" + Tools.SEPARATOR, "Menu");
		System.out.println("===========================");
		System.out.println("(1) Set Up New Player");
		System.out.println("(2) Guess A Prize");
		System.out.println("(3) What Have I Won So Far?");
		System.out.println("(4) Display Game Help");
		System.out.println("(5) Exit Game");
		System.out.println("===========================");
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
		System.out.println(Tools.SEPARATOR + "Back in " + delaytime
				+ " seconds.");
		try {
			Thread.sleep(delaytime * 1000);
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
	}

	/**
	 * Same as method delay(int), but people can decided whether to give out the
	 * instruction.
	 * 
	 * @param delaytime
	 *            The number of the second that U wanna hold for the player.
	 * @param flag
	 *            Being true to give out the instruction
	 */
	private void delay(int delaytime, boolean flag) {
		if (flag) {
			System.out.println(Tools.SEPARATOR + "Back in " + delaytime
					+ " seconds.");
		}
		try {
			Thread.sleep(delaytime * 1000);
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
