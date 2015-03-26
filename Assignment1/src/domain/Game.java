package domain;

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

	/**
	 * Default constructor for this class, the player has been set to null for
	 * the game hasn't start yet.
	 */
	public Game() {
		this.player = null;
		// the line below may be moved to other place.
		this.luckyGuessGenerator = new LuckyGuessGenerator();
		this.console = new Scanner(System.in);
		// to be improved
	}

	/**
	 * The menu first output the menu itself and then call the method
	 * makeChoice() for user to choice an option.
	 */
	public void runMenu() {
		showMenu();
		try {
			makeChoice();
		} catch (IllegalInputException e) {
			System.out.println(e.getMessage());
			delay(3);
			runMenu();
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
			throw new IllegalInputException("Please make choice using integer");
		}
		switch (choice) {
		case 1:
			setPlayer(new Player(console.nextLine()));
			break;
		case 2:
			while (true) {
				try {
					guessPrize();
					break;
				} catch (IllegalInputException e) {
					// TODO Auto-generated catch block
					System.out.println(e.getMessage());
				}
			}
			break;
		case 3:
			break;
		case 4:
			displayGameHelp();
			break;
		case 5:
			System.exit(0);
			break;
		default:
			throw new IllegalInputException(
					"Please make choice using integer range 1 - 5");
		}
	}

	private void guessPrize() throws IllegalInputException {
		/*
		 * after the prizelist already done, all the things about 5 should be
		 * change to prizelist.size()
		 */
		System.out.println("Now Guess it! Input an integer from 1 - 5.");
		int systemGuess = luckyGuessGenerator.randomIntGenerator();
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
	public void compareGuess(int userGuess, int systemGuess)
			throws IllegalInputException {
		if (userGuess > 5 || userGuess < 1) {
			throw new IllegalInputException(
					"Please guess a number with in the range");
		}
		System.out.println("Your guess is " + userGuess);
		System.out.println("The lucky number is:" + systemGuess);
		if (systemGuess == userGuess) {
			System.out
					.println("You are lucky! beacause you've just win a prize!");
			// worth++
		} else {
			System.out.println("Damn! You've just waste some money here!");
			// after finish the class about <prize>, here should be improved.
		}
		// player.setSpent();// spent++

	}

	/**
	 * Display the help information
	 */
	private void displayGameHelp() {
		System.out.println("Here is some useful information!");
		System.out
				.println("First,U will have to create a new player before you begin the game."
						+ Tools.SEPARATOR);
	}

	/**
	 * The setter of field player.
	 * 
	 * @param player
	 *            the player should be set.
	 */
	private void setPlayer(Player player) {
		this.player = player;
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
}
