package spell;


import java.io.File;
import java.io.IOException;
import java.util.*;

import spell.Tree.MyNode;




public class Corector implements SpellCorrector {
	
	private Tree words = new Tree();
	
	@Override
	public void useDictionary(String dictionaryFileName) throws IOException 
	{
		Scanner scan = new Scanner(new File(dictionaryFileName));
		
		
		
		
		while(scan.hasNext())
		{
			
			String word = scan.next();
			if(word.matches("[a-zA-Z]+")) //"[a-zA-Z]{2,}"
			{
				words.add(word);
			}
		}
		
		//words.toString();
		scan.close();
		
	}

	@Override
	public String suggestSimilarWord(String inputWord) throws NoSimilarWordFoundException {
		
		if(!inputWord.matches("[a-zA-Z]+"))
		{
			throw new NoSimilarWordFoundException();
		}
		inputWord = inputWord.toLowerCase();
		if(words.find(inputWord) != null)
		{
			return inputWord;
		}
		
		ArrayList<String> list = new ArrayList<String>();
		list.add(inputWord);
		editDistance(list);		
		
		String suggest = "";
		int curF = 0;
		for(int i = 0; i < list.size(); i++)
		{
			//System.out.println("Current Frequency = " + curF);
			//System.out.println("Word = " + suggest);
			if(words.find(list.get(i)) != null)
			{
				Tree.MyNode place = (MyNode) words.find(list.get(i));
				//System.out.println("New Word Frequency = " + place.getValue());
				//System.out.println("Word = " + list.get(i));
				if(place.getValue() > curF)
				{
					curF = place.getValue();
					suggest = list.get(i);
				}
				if(place.getValue() == curF)
				{
					if(suggest.compareTo(list.get(i)) > 0)
					{
						suggest = list.get(i);
					}
				}
			}
			
		}
		
		//System.out.println("Final Frequency = " + curF);
		//System.out.println("Final Suggestion = " + suggest);
		
		if(curF > 0)
		{
			return suggest;
		}
		else
		{
			editDistance(list);//This is the edit distance two
		}
		
		for(int i = 0; i < list.size(); i++)
		{
			//System.out.println("Current Frequency = " + curF);
			//System.out.println("Word = " + suggest);
			if(words.find(list.get(i)) != null)
			{
				Tree.MyNode place = (MyNode) words.find(list.get(i));
				//System.out.println("New Word Frequency = " + place.getValue());
				//System.out.println("Word = " + list.get(i));
				if(place.getValue() > curF)
				{
					curF = place.getValue();
					suggest = list.get(i);
				}
				if(place.getValue() == curF)
				{
					if(suggest.compareTo(list.get(i)) > 0)
					{
						suggest = list.get(i);
					}
				}
			}
			
		}
		
		//System.out.println("Final Frequency = " + curF);
		//System.out.println("Final Suggestion = " + suggest);
		if(curF > 0)
		{
			return suggest;
		}
		//Set<String> set = new TreeSet<String>();
		//String s = args[1];
		
		throw new NoSimilarWordFoundException();
	}
	
	//This function needs to call all the edits and fill in the words created
	public void editDistance(ArrayList<String> list)
	{
		int size = list.size();
		for(int i = 0; i < size; i++)
		{
			//System.out.println("Arrived Here");
			
			deletion(list.get(i), list);
			transposition(list.get(i), list);
			alteration(list.get(i), list);
			insertion(list.get(i), list);
			
		}
	}
	/*
	public void editDistance2(ArrayList<String> list)
	{
		int size = list.size();
		for(int i = 0; i < size; i++)
		{
			
		}
		
	}
	*/
	
	public void deletion(String word, ArrayList<String> list)
	{
		for(int i = 0; i < word.length(); i++)
		{
			StringBuilder sb = new StringBuilder(word);
			sb.deleteCharAt(i);
			
			//System.out.println("Added Word = " + sb);
			list.add(sb.toString());
		}
		
	}
	public void transposition(String word, ArrayList<String> list)
	{
		for(int i = 0; i < word.length() - 1; i++)
		{
			char c = word.charAt(i);
			StringBuilder sb = new StringBuilder(word);
			sb.deleteCharAt(i);
			sb.insert(i + 1, c);
			
			//System.out.println("Added Word = " + sb);
			list.add(sb.toString());
		}
	}
	public void alteration(String word, ArrayList<String> list)
	{
		//System.out.println("Arrived Here");
		for(int i = 0; i < word.length(); i++)
		{
			for(int j = 0; j < 26; j++)
			{
				StringBuilder sb = new StringBuilder(word);
				char c = 'a';
				c += j;
				
				sb.deleteCharAt(i);
				sb.insert(i, c);
				
				//System.out.println("Added Word = " + sb);
				list.add(sb.toString());
			}
		}
	}
	public void insertion(String word, ArrayList<String> list)
	{
		//System.out.println("Arrived Here");
		for(int i = 0; i <= word.length(); i++)
		{
			for(int j = 0; j < 26; j++)
			{
				StringBuilder sb = new StringBuilder(word);
				char c = 'a';
				c += j;
				sb.insert(i, c);
				
				//System.out.println("Added Word = " + sb);
				list.add(sb.toString());
			}
		}
	}
	

}
