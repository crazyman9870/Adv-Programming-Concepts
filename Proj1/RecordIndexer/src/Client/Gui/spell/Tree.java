package Client.Gui.spell;

//import spell.Trie.Node;

public class Tree implements Trie {
	
	@Override
	public int hashCode() {
		final int prime = 51;
		int result = 1;
		result = prime * result + ncount;
		result = prime * result + wcount;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Tree other = (Tree) obj;
		if (ncount != other.ncount) {
			return false;
		}
		if (wcount != other.wcount) {
			return false;
		}
		if (!this.toString().equals(other.toString()))
		{
			return false;
		}
		return true;
	}
	
	@Override
	public String toString()
	{
		
		for(int i = 0; i < 28; i++)
		{
			MyNode temp = root;
			//System.out.println("I = " + i);
			if(temp.getNodeAt(i) != null)
			{
				
				if(temp.getNodeAt(i).getValue() > 0)
				{
					//System.out.println("BEGINING TO ADD TO SB");
					char c = 'a';
					c += i;
					sb.append(c);
					sb.append(" ");
					sb.append(temp.getNodeAt(i).getValue());
					sb.append(System.getProperty("line.separator"));
				}
				char c = 'a';
				c += i;
				String word = new String();
				word = Character.toString(c);
				sb.append(recToString(word, temp.getNodeAt(i)));
			}
			//System.out.println(sb.toString());
			
		}
		
		System.out.println("\n\n=================================\n");
		System.out.println(sb.toString());
		String end = new String(sb.toString());
		StringBuilder empty = new StringBuilder();
		sb = empty;
		return end;
	}
	
	private String recToString(String word, MyNode temp)
	{
		StringBuilder answer = new StringBuilder("");
		for(int i = 0; i < 28; i++)
		{
			if(temp.getNodeAt(i) != null)
			{
				if(temp.getNodeAt(i).getValue() > 0)
				{
					sb.append(word);
					char c = 'a';
					c += i;
					sb.append(c);
					sb.append(" ");
					sb.append(temp.getNodeAt(i).getValue());
					sb.append(System.getProperty("line.separator"));
					//System.out.println(sb.toString());
				}
				StringBuilder sb2 = new StringBuilder(word);
				sb2.append(Character.toString((char)(i + 'a')));
				answer.append(recToString(sb2.toString(), temp.getNodeAt(i)));
			}
		}
		return answer.toString();
		//System.out.println("ENDING RECURSIVE\n" + sb.toString());
	}
	
	private MyNode root;
	private int wcount;
	private int ncount;
	private MyNode curN;
	private StringBuilder sb;
	public int nodeTracker;
	public static int valueAdder;
	
	public Tree()
	{
		root = new MyNode();
		wcount = 0;
		ncount = 1;
		sb = new StringBuilder();
		//System.out.println("BRAND NEW TRIE!");
		nodeTracker = ++valueAdder;
	}

	@Override
	public void add(String word) {
		
		curN = root;
		word = word.toLowerCase();
		
		for(int i = 0; i < word.length(); i++)
		{
			char c = word.charAt(i);
			int pos = -1;
			
			if (c == ' ') {
				pos = 26;
			}
			else if (c == '-') {
				pos = 27;
			}
			else {
				pos = (c-'a');
			}
						
			if(curN.getNodeAt(pos) == null)
			{
				MyNode n = new MyNode();
				ncount++;
				curN.setNodeAt(pos, n);
				curN = n;
			}
			else
			{
				curN = curN.getNodeAt(pos);
			}
		}
		//System.out.println("frequency1 = " + curN.getValue());
		if(curN.getValue() < 1)
		{
			wcount++;
		}
		curN.fplus();
		
	}

	/**
	 * Searches the trie for the specified word
	 * 
	 * @param word The word being searched for
	 * 
	 * @return A reference to the trie node that represents the word,
	 * 			or null if the word is not in the trie
	 */
	@Override
	public Node find(String word) 
	{
		curN = root;	
		StringBuilder output = new StringBuilder();
		word = word.toLowerCase();
		char c;
		
		for(int i = 0; i < word.length(); i++)
		{
			c = word.charAt(i);
			output.append(c);
			int pos = -1;
			
			if (c == ' ') {
				pos = 26;
			}
			else if (c == '-') {
				pos = 27;
			}
			else {
				pos = (c-'a');
			}
			
			if(curN.getNodeAt(pos) != null)
			{
				curN = curN.getNodeAt(pos);
			}
			else
			{
				return null;
			}
			
		}
		
		if(curN.getValue() > 0)
		{
			//System.out.println("word frequency = " + curN.getValue());
			Node n = curN;
			return n;
		}
		else
		{
			return null;
		}
	}

	@Override
	public int getWordCount() {
		// TODO Auto-generated method stub
		return wcount;
	}

	@Override
	public int getNodeCount() {
		// TODO Auto-generated method stub
		return ncount;
		
	//=================================================================
		

	

		
		
	}
	//=================================================================
	
	public class MyNode implements Trie.Node {

		private int frequency;
		protected MyNode[] narray;
		
		public MyNode() {
			narray  = new MyNode[28];
			frequency = 0;
		}
		
		@Override
		public int getValue() {
			
			return frequency;
		}
		
		public MyNode getNodeAt(int pos)
		{
			return narray[pos];
		}
		
		public void setNodeAt(int pos, MyNode n)
		{
			narray[pos] = n;
		}
		
		public void fplus()
		{
			frequency++;
		}
		
		
	}

}
