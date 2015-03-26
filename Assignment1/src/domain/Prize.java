package domain;

/**
 * This class indicates every kind of prize that the user may win. However, this
 * class will used for every element used in PrizeList.
 * 
 * @author archer
 *
 */
public class Prize {
	/**
	 * The name of the prize.
	 */
	private String name;
	/**
	 * The description of the prize.
	 */
	private String description;
	/**
	 * The worth of the prize.
	 */
	private int worth;

	/**
	 * The recommended constructor here, you should give all the informations
	 * about the prize.
	 * 
	 * @param name
	 *            The name of the prize.
	 * @param description
	 *            The description of the prize.
	 * @param worth
	 *            The worth of the prize.
	 */
	public Prize(String name, String description, int worth) {
		super();
		this.name = name;
		this.description = description;
		this.worth = worth;
	}

	/**
	 * The constructor used if you don't have the description of the prize.
	 * However you will have to give the name and worth of it.
	 * 
	 * @param name
	 *            The name of the prize.
	 * @param worth
	 *            The worth of the prize.
	 */
	public Prize(String name, int worth) {
		super();
		this.name = name;
		this.worth = worth;
	}

	/**
	 * The getter of the field name.
	 * 
	 * @return The String of the field.
	 */
	public String getName() {
		return name;
	}

	/**
	 * The setter of the field Name. If you wanna change the name of the prize,
	 * you can use it.
	 * 
	 * @param name
	 *            The name you would like to change.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * The getter of the field description.
	 * 
	 * @return The Description of the prize.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * The setter of the filed description.
	 * 
	 * @param description
	 *            the description you wanna change.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * The getter of the field worth.
	 * 
	 * @return The worth of the prize.
	 */
	public int getWorth() {
		return worth;
	}

	/**
	 * The setter of the field worth.
	 * 
	 * @param worth
	 *            The worth you wanna change.
	 */
	public void setWorth(int worth) {
		this.worth = worth;
	}
}
