package Client.Gui.spell;

import java.io.IOException;
import java.util.ArrayList;

import Client.Gui.spell.SpellCorrector.NoSimilarWordFoundException;

/**
 * A simple main class for running the spelling corrector
 */
public class Main {
	
	/**
	 * Give the dictionary file name as the first argument and the word to correct
	 * as the second argument.
	 */
	public static void main(String[] args) throws NoSimilarWordFoundException, IOException {
		
		String dictionaryFileName = args[0];
		String inputWord = args[1];
		
		/**
		 * Create an instance of your corrector here
		 */
		Corrector c = new Corrector();
		
		c.useDictionary(dictionaryFileName);
		ArrayList<String> suggestion = c.suggestSimilarWord(inputWord);
		
		System.out.println(c.words.toString());
		
		System.out.println("Suggestion is: " + suggestion.toString());
	}

}
