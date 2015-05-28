package domain;

import javax.xml.bind.ValidationException;

import systemTools.Tools;

/**
 * This class indicates every kind of prize that the user may win. However, this
 * class will used for every element used in PrizeList.
 * 
 * @author archer
 * 
 */
public class Prize
{
    /**
     * The name of the prize.
     */
    private String name;
    /**
     * The worth of the prize.
     */
    private int worth;
    /**
     * The money that the prize will cost.
     */
    private int cost;

    /**
     * The constructor used if you don't have the description of the prize.
     * However you will have to give the name and worth of it.
     * 
     * @param name
     *            The name of the prize.
     * @param worth
     *            The worth of the prize.
     * @param cost
     *            The cost of the prize if user lose.
     * @throws ValidationException
     */
    public Prize(String name, int worth, int cost) throws ValidationException
    {
	super();
	this.name = name.trim();
	this.worth = worth;
	this.cost = cost;
	// validation();
	if (worth <= 0)
	    throw new ValidationException("Negative/zero worth value is not permitted");
	if (cost <= 0)
	    throw new ValidationException("Negative/zero cost value is not permitted");
	if (name.isEmpty())
	    throw new ValidationException("Empty Name is not permitted");
    }

    /**
     * To campare if 2 prize are "equal".
     * 
     * Here in this case, "equal" means same name.
     * 
     * @Override from class Object
     * 
     * @return true if equal, false if not.
     */
    public boolean equals(Object obj)
    {
	if (this == obj)
	    return true;
	if (getClass() != obj.getClass())
	    return false;
	Prize prize = (Prize) obj;
	if (!this.name.equalsIgnoreCase(prize.name))
	    return false;
	else
	    return true;
    }

    /**
     * The getter of the field cost.
     * 
     * @return The money that the prize will cost.
     */
    public int getCost()
    {
	return cost;
    }

    /**
     * The getter of the field name.
     * 
     * @return The String of the field.
     */
    public String getName()
    {
	return name;
    }

    /**
     * The getter of the field worth.
     * 
     * @return The worth of the prize.
     */
    public int getWorth()
    {
	return worth;
    }

    public String toString()
    {
	return name + "," + worth + "," + cost + Tools.SEPARATOR;
    }

    /*
     * public void validation() throws ValidationException { // boolean flag =
     * true; // if (!(worth > 0 && cost > 0 && !name.isEmpty())) // flag =
     * false; // return flag; if (worth < 0) throw new
     * ValidationException("Negative worth value is not permitted"); if (cost <
     * 0) throw new ValidationException("Negative cost value is not permitted");
     * if (name.isEmpty()) throw new
     * ValidationException("Empty Name is not permitted"); }
     */
}
