package domain;

/**
 * This class is kind of a tools, anyone can use it to generate a random
 * integer.
 * 
 * @author archer
 *
 */
public class LuckyGuessGenerator {

	/**
	 * Default generator for this assignment, in which a random integer from 1
	 * to 5 will be given.
	 * 
	 * @return a random integer value from 1 to 5.
	 */
	public int randomIntGenerator() {
		return 1 + (int) (Math.random() * 5);
	}

	/**
	 * The customized generator for further use, by which you can give a max
	 * value.
	 * 
	 * @param Maxvalue
	 *            The max value you give.
	 * @return An integer from 1 to the max value you gave.
	 */
	public int randomIntGenerator(int maxvalue) {
		return 1 + (int) (Math.random() * (maxvalue));
	}

}
