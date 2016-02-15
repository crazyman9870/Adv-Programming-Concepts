package client.unitTests;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.*;

import Client.Gui.spell.Corrector;
import Client.Gui.spell.SpellCorrector.NoSimilarWordFoundException;


public class testQualityCheck {

	private Corrector corrector;
	private final String dictionary = "Anthony,Kaylee,Scott,Austin,Kyle,Bill,Bob";
	
	@Before
	public void setup() {
		corrector = new Corrector();
	}
	
	@After
	public void teardown() {
		corrector = null;
	}
	
	@Test
	public void testInputSize() {
		
		try {
			corrector.useDictionary(dictionary);
			assertEquals(corrector.words.getWordCount(), 7);
			
		} catch (IOException e) {
			System.out.println("caught exception testInputSize");
			assertEquals(false, true);
			e.printStackTrace();
		}
	}
	
	@Test
	public void testExistingWord() {
		
		//testing the word already exists 
		try {
			corrector.useDictionary(dictionary);
			ArrayList<String> suggestions =	corrector.suggestSimilarWord("anthony");
			assertEquals(suggestions.size(), 1);
//			System.out.println("Suggestion size " + suggestions.size());
			
		} catch (IOException | NoSimilarWordFoundException e) {
			System.out.println("caught exception testExistingWord");
			assertEquals(false, true);
			e.printStackTrace();
		}
	}
	
	@Test
	public void testEditDistances() {
		
		try {
			//edit distance 1
			corrector.useDictionary(dictionary);
			ArrayList<String> suggestions =	corrector.suggestSimilarWord("anthon");
//			System.out.println(suggestions.toString());
			assertEquals(suggestions.size(), 1);
			
			
			//edit distance 2
			corrector.useDictionary(dictionary);
			suggestions = corrector.suggestSimilarWord("antho");
//			System.out.println(suggestions.toString());
			assertEquals(suggestions.size(), 1);
			
			
			
			
		} catch (IOException | NoSimilarWordFoundException e) {
			System.out.println("caught exception testEditDistances");
			assertEquals(false, true);
			e.printStackTrace();
		}
	}
	
	@Test
	public void testBadInput() {
		
		try {
			corrector.useDictionary(dictionary);
			ArrayList<String> suggestions =	corrector.suggestSimilarWord("nosuggestions");
			//word does not exist
			assertEquals(suggestions.size(), 0);
			
			//should throw the exception
			suggestions = corrector.suggestSimilarWord("&");
			//shouldn't reach this line
			assertEquals(false, true);
			
		} catch (IOException | NoSimilarWordFoundException e) {
			System.out.println("caught exception testExistingWord");
			//corrector threw exception correctly
			assertEquals(true, true);
			e.printStackTrace();
		}
	}
	
	@Test
	public void testMutipleWordsFound() {
		
		try {
			corrector.useDictionary(dictionary);
			ArrayList<String> suggestions =	corrector.suggestSimilarWord("bi");
//			System.out.println(suggestions.toString());
			//Both bill and bob should be suggestions
			assertEquals(suggestions.size(), 2);
			
		} catch (IOException | NoSimilarWordFoundException e) {
			System.out.println("caught exception testExistingWord");
			assertEquals(false, true);
			e.printStackTrace();
		}
		
	}
	
}
