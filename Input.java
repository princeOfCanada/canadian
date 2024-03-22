import java.util.*;

public class Input {

	public static void main(String args[])
	{	// creating the scanner class object
		Scanner input = new Scanner (System.in);
		// getting the full name as input
		String fullName = input.nextLine();
		// splitting the name into two parts
		String names [] = fullName.split(" ");
		
		// creating the formatted output
		System.out.println(names[1] + ", " + names[0]);
		
		input.close();
	}
}
 