package listem;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class LC extends FileProcessor implements LineCounter {

	public HashMap<File, Integer> lcmap = new HashMap<>();
		
	@Override
	public Map<File, Integer> countLines(File directory,
			String fileSelectionPattern, boolean recursive) {
		
		
		lcmap.clear();
		
		processDir(directory, fileSelectionPattern, recursive);		
		
		return lcmap;
	}

	@Override
	public void processFile(File file) {
		
		try 
		{
			int lcount = scanFile(file);
			
			lcmap.put(file, lcount);
		}
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		
	}
	
	public int scanFile(File file) throws FileNotFoundException
	{
		Scanner scan = new Scanner(file);
		int lcount = 0;
		while(scan.hasNextLine())
		{
			lcount++;
			scan.nextLine();
		}
		if(scan.hasNext())
		{
			lcount++;
		}
		scan.close();
		return lcount;
	}
	

}
