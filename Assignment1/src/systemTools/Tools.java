package systemTools;

import java.io.File;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Tools
{
    /**
     * Console is an object used for user input.
     */
    public final static Scanner console = new Scanner(System.in);

    public final static File prizeFile = new File("src" + File.separator + "prizeForLinux.txt");

    public final static String SEPARATOR = System.getProperty("line.separator");

    /**
     * Hold the program for some seconds. Basically used for player to read some
     * simply instructions.
     * 
     * @param delaytime
     *            The number of the second that U wanna hold for the player.
     */
    public static void delay(int delaytime)
    {
	delay(delaytime, true);
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
    public static void delay(int delaytime, boolean flag)
    {
	if (flag)
	{
	    System.out.println(Tools.SEPARATOR + "Back in " + delaytime + " seconds." + Tools.SEPARATOR);
	}
	try
	{
	    Thread.sleep(delaytime * 1000);
	    System.out.print('\u000C');
	} catch (InterruptedException ex)
	{
	    Thread.currentThread().interrupt();
	}
    }

    /**
     * Hold the program until the user type any key. Basically used for player
     * to read some long instruction or other system output(such as game help in
     * this program).
     */
    public static void hold()
    {
	System.out.println(Tools.SEPARATOR + "Press <Enter> to continue...." + Tools.SEPARATOR);
	Tools.console.nextLine();
	System.out.print('\u000C');
    }

    public static int inputInteger()
    {
	try
	{
	    int i = console.nextInt();
	    console.nextLine();
	    return i;
	} catch (InputMismatchException e)
	{
	    System.out.println("Please use integer ONLY.");
	    console.nextLine();
	    return inputInteger();
	} catch (Exception e)
	{
	    System.out.println("Unknow error, please do it again.");
	    console.nextLine();
	    return inputInteger();
	}
    }
}
