import java.util.Scanner;


public class Cout 
{
	private static String str1;
	private static String str2;
	
	private static int i1;
	private static int i2;
	
	public static final double CM_PER_INCH = 2.54;

	public static void main(String[] args)
	{
		System.out.println("\n\nTesting Printing Output");
		str1 = "Place 1";
		str2 = "Place 2";
		
		i1 = 1;
		i2 = 2;
		
		
		System.out.printf("%d, %d\n", i1, i2);
		System.out.println(str1 + ", " + str2);
		
		double paperWidth = 8.5;
		double paperHeight = 11;
		System.out.println("Paper size in cm: "
				+ paperWidth * CM_PER_INCH + " by " 
				+ paperHeight * CM_PER_INCH);
		
		//This is how you do printf and println functions in java
		
		
		//Above is printing out stuff
		//=========================================================================================
		//Below is reading in stuff you want to use
		
		
		Scanner input = new Scanner(System.in);
		System.out.println("Enter any Word");
		String in = input.next();
		System.out.println("Word you entered = " + in);
		input.close();
		
		
		//Above is reading in stuff you want to use
		//=========================================================================================
		//Below is the converting of Strings to ints
		
		
		String len = "5";
		String triesstr = "10";
		//These strings are just for example
		
		int length = 0;
		int triesnum = 0;
		try {
			length = Integer.parseInt(len); //Here where we put in len you could put in an argument
			triesnum = Integer.parseInt(triesstr); // or an input of some sort
			
		} catch (NumberFormatException e) {
		      //Will Throw exception!
		      //do something! anything to handle the exception.
		}
		
	}
}
