package hangman;

import hangman.EvilHangmanGame.GuessAlreadyMadeException;

import java.io.File;
import java.util.Scanner;


public class GamePlayer {
	
	GamePlayer()
	{
		
	}
	
	public static void main (String[] args)
	{
		EvilHangman eh = new EvilHangman();
		
		
		int length = 0;
		int tries = 0;
		try 
		{
			length = Integer.parseInt(args[1]);
			tries = Integer.parseInt(args[2]);
		} catch (NumberFormatException e) 
		{
		      System.out.println("Either the word length or guess attemps was in invalid format");
		}
		
		eh.startGame(new File(args[0]), length);
		Scanner input = new Scanner(System.in);
		
		//System.out.println("DASHBOARD = " + eh.dashboard.toString());
		//System.out.println(eh.guesses.toString());
		//eh.printWordList();
		
		int remains = length;
		while(tries > 0)
		{
			StringBuilder guessed = new StringBuilder();
			for(String word : eh.guesses)
			{
				guessed.append(word);
				guessed.append(" ");
			}
			
			System.out.println("You have " + tries + " guesses left");
			//System.out.println("Used Letters: " + eh.guesses.toString());
			System.out.println("Used Letters: " + guessed.toString());
			System.out.println("Word: " + eh.dashboard);
			System.out.println("Enter guess: ");
			
			String c = input.next().toLowerCase();
			
			if(c.matches("[a-zA-Z]+"))
			{
				if(c.length() == 1)
				{
					if(eh.guesses.contains(c))
					{
						System.out.println("\nYou have already guessed " + c + "!\nPlease Try again\n");
					}
					else
					{
						for(int i = 0; i < c.length(); ++i)
						{
							try 
							{
								eh.makeGuess(c.charAt(i));
							} catch (GuessAlreadyMadeException e) {
								e.printStackTrace();
							}
						}
						if(eh.correct == length)
						{
							System.out.println("How did you do that?\nYOU WON!\n");
							return;
						}
						if((eh.correct + remains) != length)
						{
							System.out.println("\n");
							remains = length - eh.correct;
						}
						else
						{
							System.out.println("Sorry, there are no " + c + "'s\n");
							tries--;
						}
						
					}
				}
				else
				{
					System.out.println("\nInvalid Guess Size!\nPlease Try again\n");
				}
			}
			else
			{
				System.out.println("\nInvalid Input!\nPlease Try again\n");
			}
			

		}
	
		Scanner scan = new Scanner(eh.wordList.toString());
		StringBuilder sb = new StringBuilder();
		String endword = scan.next();
		for(int j = 0; j < length; ++j)
		{
			sb.append(endword.charAt(j+1));
		}
		scan.close();
		
		//System.out.println(eh.wordList.toString());
		System.out.println("You Lose!\nThe word was " + sb.toString());
		System.out.println("\n><  ><\n ____    <-- U DEAD\n   U ");
	
		input.close();
		

	}
	
	
	
	
			/*
		System.out.println("Dictionary\n\n");
		for(int i = 0; i < dictionary.size(); i++)
		{
			System.out.println("Item " + (i+1) +" = " + dictionary.get(i));
		}
		System.out.println("FINAL ARRAY SIZE = " + dictionary.size());
		*/
		
		//THE SIZE IS OFF BY 13? Appears to lose one after every 20,000 words	

}
