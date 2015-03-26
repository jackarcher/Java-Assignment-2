package domain;

import java.util.Scanner;

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
		this.player = new Player();
		// the line below may be moved to other place.
		this.luckyGuessGenerator = new LuckyGuessGenerator();
		this.console = new Scanner(System.in);
		//to be improved
	}

	/**
	 * The menu first output the menu itself and then call the method
	 * makeChoice() for user to choice an option.
	 */
	public void menu() {

		System.out.println("Welcome to the Lucky Vending Machine");
		System.out.println("====================================");
		System.out.println("(1) Set Up New Player");
		System.out.println("(2) Guess A Prize");
		System.out.println("(3) What Have I Won So Far?");
		System.out.println("(4) Display Game Help");
		System.out.println("(5) Exit Game");
		System.out.println("====================================");
		System.out.println("Choose an option :");
		try {
			makeChoice();
		} catch (IllegalInputException e) {
			System.out.println(e.getMessage());
			menu();
		}
	}

	/**
	 * The method that use for make choice. Basically choice only happens after
	 * the menu has been output, so this is a private method which only be
	 * called by the menu method.
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
			/*
			 * As for the nextInt will throw some Exception(basically
			 * RuntimeExceptions) so the solution should be simply change the
			 * choice to -1 to let the user choice again.
			 */
			choice = -1;
		}
		switch (choice) {
		case 1:
			setPlayer(new Player(console.nextLine()));
			break;
		case 2:
			try {
				guessPrize();
			} catch (IllegalInputException e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
				guessPrize();
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
					"Please make choice using integer 1 - 5");
		}
	}

	private void guessPrize() throws IllegalInputException {
		System.out.println("Now Guess it! Input an integer from 1 - 5.");
		int systemGuess = luckyGuessGenerator.randomIntGenerator();
		int userGuess;
		try {
			userGuess = console.nextInt();
		} catch (Exception e) {
			throw new IllegalInputException("Please guess an integer 1 - 5");
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
		// spent++
	}

	/**
	 * Display the help information
	 */
	private void displayGameHelp() {
		System.out.println("Here is some useful information!");
		System.out.println();
	}
/**
 * The setter of field player.
 * 
 * @param player the player should be set.
 */
	private void setPlayer(Player player) {
		this.player = player;
	}
}
