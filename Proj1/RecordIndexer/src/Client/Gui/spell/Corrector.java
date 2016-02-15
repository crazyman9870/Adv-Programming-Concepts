package Client.Gui.spell;


import java.io.IOException;
import java.util.*;




public class Corrector implements SpellCorrector {
	
	public Tree words = new Tree();
	public boolean implemented = false;
	
	@Override
	public void useDictionary(String knownWordList) throws IOException 
	{
		Scanner scan = new Scanner(knownWordList);
		implemented = true;
		
		while(scan.hasNext())
		{
			String word = scan.next();
			List<String> newList = Arrays.asList(word.split(","));
			for(String s : newList) {
				
				if(s.matches("[-a-zA-Z ]+")) //"[a-zA-Z]{2,}"
				{
					words.add(s);
				}
			}
		}
		
		//words.toString();
		scan.close();
	}

	@Override
	public ArrayList<String> suggestSimilarWord(String inputWord) throws NoSimilarWordFoundException {
		
		if(!inputWord.matches("[-a-zA-Z ]+"))
		{
			throw new NoSimilarWordFoundException();
		}
		ArrayList<String> originalWordList = new ArrayList<>();
		originalWordList.add(inputWord);
		inputWord = inputWord.toLowerCase();
		if(words.find(inputWord) != null)
		{
			return originalWordList;
		}
		
		editDistance(originalWordList);
		editDistance(originalWordList);
		
		ArrayList<String> finalWordList = new ArrayList<>();
		
		for(String s : originalWordList) {
			if(words.find(s) != null) {
				if(!finalWordList.contains(s)) {
					finalWordList.add(s);
				}
			}
		}
		
		Collections.sort(finalWordList);
		return finalWordList;
		
//		throw new NoSimilarWordFoundException();
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
