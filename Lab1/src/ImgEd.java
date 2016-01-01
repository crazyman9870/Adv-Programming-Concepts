import java.util.*;
import java.io.*;

public class ImgEd 
{
	
	ImgEd()
	{
		
	}
	public Image parseFile(String picture) throws FileNotFoundException
	{
		Scanner scanner = new Scanner(new FileReader(picture));
		scanner.useDelimiter("(\\s+)(#[^\\n]*\\n)?(\\s*)|(#[^\\n]*\\n)(\\s+)");
		
		scanner.next();
		int w = scanner.nextInt();
		int h = scanner.nextInt();
		int max_p = scanner.nextInt();
		Image img = new Image(w,h, max_p);
		
		//System.out.println("HEIGHT:\t" + h + "\nWIDTH:\t" + w);
		
		for(int i = 0; i < h; i++)
		{
			for(int j = 0; j < w; j++)
			{
				img.parray[j][i] = new Pixel(
						scanner.nextInt(),
						scanner.nextInt(),
						scanner.nextInt());
			}
		}
		
		scanner.close();
		return img;
		
	}
	
	
	public void FileOut(Image img, String outputFile) throws IOException
	{	
			StringBuilder sb = new StringBuilder("P3");
			sb.append("\n" + img.getWidth());
			sb.append(" " + img.getHeight());
			sb.append("\n" + img.getMaxP());
			
			for(int i = 0; i < img.getHeight(); i++)
			{
				for(int j = 0; j < img.getWidth(); j++)
				{
					sb.append("\n" + img.parray[j][i].getRed());
					sb.append("\n" + img.parray[j][i].getGreen());
					sb.append("\n" + img.parray[j][i].getBlue());
				}
			}
			
			String str = sb.toString();
		
	
			PrintWriter pw = new PrintWriter(
					new BufferedWriter(new FileWriter(outputFile)));
			
			pw.print(str);
			pw.close();
	
	}
	
	
	
	
	
	public static void main(String[] args) throws IOException
	{
			ImgEd ie = new ImgEd();
			Image img = new Image();
			img = ie.parseFile(args[0]);
			
			String change = (args[2]);
			
			if(change.equals("invert"))
			{
				img.invert();
			}
			if(change.equals("grayscale"))
			{
				img.grayscale();
			}
			if(change.equals("emboss"))
			{
				img.emboss();
			}
			if(change.equals("motionblur"))
			{
				String blur = (args[3]);
				int num = Integer.valueOf(blur);
				if(num <= 0)
				{
					System.out.println("Error: input is less than 1");
				}
				else
				{
					img.motionblur(num);
				}
			}
			
			String outputFile = args[1];
			ie.FileOut(img, outputFile);
		
	}

}
