package hangman;

import hangman.EvilHangmanGame.GuessAlreadyMadeException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class EvilHangman implements EvilHangmanGame {
	
	public HashSet<String> wordList;
	public HashMap<String, HashSet<String>> wordMap;
	public TreeSet<String> guesses;
	public int correct = 0;
	public String dashboard;


	public HashSet<String> beginProcessFile(File file, int length)
	{
		try
		{
			return processFile(file, length);
		}
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public HashSet<String> processFile(File file, int length) throws FileNotFoundException
	{
			Scanner scan = new Scanner(file);
			HashSet<String> dictionary = new HashSet<>();
			
			//System.out.println(length + "\n");
			
			while(scan.hasNext())
			{
				String word = scan.next();
				//System.out.println("Word length = "+ word.length() + "\n");
				if(word.matches("[a-zA-Z]+"));
				{
					if(word.length() == length)
					{
					//System.out.println("HERE");
					dictionary.add(word);
					}
				}
			}
			
			scan.close();
			return dictionary;
	}
	
	public void printWordList()
	{
		System.out.println("Dictionary\n\n");
		System.out.println(wordList.toString());
		System.out.println("FINAL SET SIZE = " + wordList.size());
		//THE SIZE IS OFF BY 13? Appears to lose one after every 20,000 words
		//Answer there are blank lines in the dictionary.txt file
	}
	
	@Override
	public void startGame(File dictionary, int wordLength) 
	{
		wordList = new HashSet<>();
		wordMap = new HashMap<>();
		guesses = new TreeSet<>();
		correct = 0;
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < wordLength; i++)
		{
			sb.append("-");
		}
		dashboard = sb.toString();
		wordList = beginProcessFile(dictionary, wordLength);
		
		//System.out.println(dashboard);			
	}

	
	/**
	 * Make a guess in the current game.
	 * 
	 * @param guess The character being guessed
	 * @return The set of strings that satisfy all the guesses made so far
	 * in the game, including the guess made in this call. The game could claim
	 * that any of these words had been the secret word for the whole game. 
	 * 
	 * @throws GuessAlreadyMadeException If the character <code>guess</code> 
	 * has already been guessed in this game.
	 */	
	@Override
	public Set<String> makeGuess(char guess) throws GuessAlreadyMadeException 
	{
		//boolean duplicate = false; //I can't remeber why I am creating this
		String guessed  = Character.toString(guess);
		if(guesses.contains(guessed))
		{
			throw new GuessAlreadyMadeException();
		}
		
		guesses.add(guessed);
		partition(guess);
		
		return wordList;
	}
	
	
	public void partition(char guess)
	{
		for(String word : wordList)
		{
			String wordKey = matcher(word, guess);
			if(wordMap.containsKey(wordKey))
			{
				wordMap.get(wordKey).add(word);
			}
			else
			{
				HashSet<String> to_add = new HashSet<>();
				to_add.add(word);
				wordMap.put(wordKey, to_add);
			}
		}
		replace(guess);
	}
	
	public String matcher(String word, char guess)
	{
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < word.length(); ++i)
		{
			if(word.charAt(i) == guess)
			{
				sb.append(guess);
			}
			else
			{
				sb.append('-');
			}
		}
		return sb.toString();
	}
	
	public void replace(char guess)
	{
		String holderstr = "";
		HashSet<String> holderset = new HashSet<>();
		
		for(Map.Entry<String, HashSet<String>> temp : wordMap.entrySet())
		{
			if(holderset.size() < temp.getValue().size())
			{
				holderset = temp.getValue();
				holderstr = temp.getKey();
			}
			if(holderset.size() == temp.getValue().size())
			{
				if(compareKeys(holderstr, temp.getKey())) //call this function
				{
					holderset = temp.getValue();
					holderstr = temp.getKey();
				}
			}
		}
		
		if(hasChar(holderstr))
		{
			updateDashboard(holderstr);
		}
		wordList = holderset;
		wordMap.clear();
		
	}
	
	public boolean hasChar(String holderstr)
	{
		for(int i = 0; i < holderstr.length(); ++i)
		{
			if(holderstr.charAt(i) != '-')
			{
				return true;
			}
		}
		return false;
	}
	
	public void updateDashboard(String keyword)
	{
		//System.out.println("KEYWORD " + keyword);
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < keyword.length(); ++i)
		{
			if(keyword.charAt(i) != '-')
			{
				correct++;
				sb.append(keyword.charAt(i));
			}
			else
			{
				sb.append(dashboard.charAt(i));
			}
			//System.out.println("StringBuilder " + sb.toString());
		}
		dashboard = sb.toString();
	}
	
	public boolean compareKeys(String oldKey, String newKey)
	{
		if(newLetters(oldKey) > newLetters(newKey))
		{
			return true;
		}
		if(newLetters(oldKey) < newLetters(newKey))
		{
			return false;
		}
		
		if(furthestRight(oldKey, newKey))
		{
			return true;
		}
		
		return false;
	}
	
	public boolean furthestRight(String oldKey, String newKey)
	{
		int wordlength = oldKey.length();
		for(int i = 0; i < wordlength; ++i)
		{
			if(oldKey.charAt(i) != '-')
			{
				if(newKey.charAt(i) == '-')
				{
					return true;
				}
			}
			else if(newKey.charAt(i) != '-')
			{
				return false;
			}
		}
		return true;
	}
	
	public int newLetters(String key)
	{
		int counter = 0;
		for(int i = 0; i < key.length(); ++i)
		{
			if(key.charAt(i) != '-')
			{
				counter++;
			}
		}
		return counter;
	}

}
