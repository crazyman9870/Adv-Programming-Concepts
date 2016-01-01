package listem;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GrepImp extends FileProcessor implements Grep {

	public HashMap<File, List<String>> gmap = new HashMap<>();
	public String substr;
		
	@Override
	public Map<File, List<String>> grep(File directory,
			String fileSelectionPattern, String substringSelectionPattern,
			boolean recursive) 
	{
		gmap.clear();
		
		substr = substringSelectionPattern;
		processDir(directory, fileSelectionPattern, recursive);
		
		return gmap;
	}



	@Override
	public void processFile(File file) 
	{
		try 
		{
			List<String> list = new ArrayList<>();
			scanFile(file, list);
			
			if(!list.isEmpty())
			{
				gmap.put(file, list);
			}
			
			
		}
		catch (FileNotFoundException e) 
		{
			
			e.printStackTrace();
		}
		
	}
	
	public void scanFile(File file, List<String> list) throws FileNotFoundException
	{
		Scanner scan = new Scanner(file);
		Pattern p = Pattern.compile(substr);

		
		while(scan.hasNextLine())
		{
			String line = scan.nextLine();
			Matcher m = p.matcher(line);
			if(m.find())
			{
				list.add(line);
			}
		}
		
		scan.close();
	}

}
