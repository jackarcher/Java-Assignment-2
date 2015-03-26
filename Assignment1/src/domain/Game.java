package domain;

import java.util.ArrayList;
import java.util.Scanner;

import systemTools.Tools;
import Exceptions.IllegalInputException;

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
	 * Console is an object used for user input.
	 */
	private Scanner console;
	private ArrayList<Prize> systemPrizeList;

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
		// if (choice != 1 && player.getName().isEmpty()) {
		// System.out.println("You will have to create a new player first!");
		// choice = 2;
		// }
		switch (choice) {
		case 1:
			while (true) {
				try {
					setPlayer();
					break;
				} catch (IllegalInputException e) {
					System.out.println(e.getMessage());
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
			showUsersPrizes();
			break;
		case 4:
			displayGameHelp();
			break;
		case 5:
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
	 */
	private void setPlayer() throws IllegalInputException {
		System.out.println("Enter your name plz:");
		String temp = "Archer";
		try {
			temp = console.nextLine();
		} catch (Exception e) {
			e.printStackTrace();
		}
		temp = "Archer";
		if (temp.isEmpty())
			throw new IllegalInputException("No one is NULL!");
		else
			this.player.setName(temp);
	}

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
		player.setSpent(systemPrizeList.get(systemGuess - 1).getCost());// player.setSpent();//
																		// spent++

	}

	/**
	 * Display the help information
	 */
	private void displayGameHelp() {
		System.out.println("Here is some useful information!");
		System.out
				.println("First,U will have to create a new player before you begin the game.");
		System.out.println(showPrizes());
	}

	private void delay(int delaytime) {
		try {
			Thread.sleep(delaytime * 1000);
			// whether syso?
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
	}

	private void showMenu() {
		System.out.println("Welcome to the Lucky Vending Machine");
		System.out.println("====================================");
		System.out.println("(1) Set Up New Player");
		System.out.println("(2) Guess A Prize");
		System.out.println("(3) What Have I Won So Far?");
		System.out.println("(4) Display Game Help");
		System.out.println("(5) Exit Game");
		System.out.println("====================================");
		System.out.println("Choose an option :");
	}

	private String showPrizes() {

		String p = "Number Generated" + "	" + "Prize is" + "	" + "Prize Worth"
				+ "	" + "Cost to player" + Tools.SEPARATOR;
		for (Prize prize : systemPrizeList) {
			p = p + (systemPrizeList.indexOf(prize) + 1) + "			"
					+ prize.getName() + "		" + prize.getWorth() + "		"
					+ prize.getCost() + Tools.SEPARATOR;
		}
		return p;
	}

	private void showUsersPrizes() {
		if (!player.getPrizeList().isEmpty()) {
			System.out.print("U have won: ");
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
					.println("Em...I know U have spent some money on me, but sometime it is your luck to blame, right?");
		else
			System.out
					.println("U must be kidding me, u haven't spent even 1 cent on me!");
	}
}
