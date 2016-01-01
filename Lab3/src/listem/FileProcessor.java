package listem;

import java.io.File;
import java.util.ArrayList;
import java.util.regex.Pattern;

public abstract class FileProcessor {
	
	protected ArrayList<File> flist = new ArrayList<>();
	
	public void processDir(File dir, String srchpatrn, boolean recurse)
	{		
		flist.clear();
		Pattern p = Pattern.compile(srchpatrn);
		if(recurse == true) 
		{
			File[] files = dir.listFiles();
			for(File f : files)
			{
				if(f.isDirectory())
				{
					recDir(f, p);
				}
				
				if(f.isFile() && p.matcher(f.getName()).matches())
				{
					//flist.add(f);
					processFile(f);
				}
			}
		} 
		else 
		{
			File[] files = dir.listFiles();
			for(File f : files)
			{
				if(f.isFile() && p.matcher(f.getName()).matches())
				{
					//flist.add(f);
					processFile(f);
				}
			}
			
		}
	}

	private void recDir(File dir, Pattern p)
	{
		File[] files = dir.listFiles();
		for(File f : files) 
		{
			if(f.isDirectory())
			{
				recDir(f, p);
			}
			
			if(f.isFile() && p.matcher(f.getName()).matches())
			{
				//flist.add(f);
				processFile(f);
			}
		}
	}

	
	public abstract void processFile(File file);

}
