package util;
import java.util.Scanner;

// klass med statiska hjälpmetoder för input/output
/**
 * 
 * Based on code by Lars Jelleryd, 2018
 * 
 * @author Modification Anosh. D Ullenius <anosh@anosh.se>
 *
 */
public class InputHelper {

	private final Scanner sc;

	public InputHelper() {

            sc = new Scanner(System.in);
	}

	public String getText(String message) {

		System.out.print(message); // print user defined message
		StringBuilder input = new StringBuilder(sc.nextLine()); 
		// använder StringBuilder, för att undvika skit i String Pool
		sc.nextLine(); // tömmer buffern

		return input.toString();
	}

	public int getInt(String message) {

	//	System.out.println("getint med parameter"); // DEBUG

		System.out.print(message); // print user defined message
		int input = sc.nextInt();
		sc.nextLine(); // tömmer buffern

		return input;

	}
}
