
public class Image 
{
	private int h;
	private int w;
	private int max_p;
	public Pixel[][] parray;
	
	public Image()
	{
		
	}
	public Image(int nw, int nh, int nmp)
	{
		h = nh;
		w = nw;
		max_p = nmp;
		parray = new Pixel[w][h];
	}

	public int getWidth()
	{
		return w;
	}
	
	public int getHeight()
	{
		return h;
	}
	
	public int getMaxP()
	{
		return max_p;
	}
	
	public Pixel[][] getParray()
	{
		return parray;
	}
	
	public void invert()
	{
		for(int i = 0; i < h; i++)
		{
			for(int j = 0; j < w; j++)
			{
				parray[j][i].invert();
			}
		}
	}
	
	public void grayscale()
	{
		for(int i = 0; i < h; i++)
		{
			for(int j = 0; j < w; j++)
			{
				parray[j][i].grayscale();
			}
		}
	}
	
	public void emboss()
	{
		//System.out.println("HEIGHT:\t" + h + "\nWIDTH:\t" + w);
		for(int i = h-1; i >= 0; i--)
		{
			for(int j = w-1; j >= 0; j--)
			{
				//System.out.println("j =  " + j + "\tw = " + w);
				//System.out.println("i =  " + i + "\th = " + h);
				if(j == 0 || i == 0)
				{
					parray[j][i].emboss1();
				}	
				else
				{
					Pixel p = parray[j-1][i-1];
					parray[j][i].emboss2(p);
				}
			}
		}
	}
	
	public void motionblur(int blur)
	{
		int aver = 0;
		int aveg = 0;
		int aveb = 0;
		for(int i = 0; i < h; i++) //Iterates through the rows
		{
			for(int j = 0; j < w; j++) //Iterates through the columns
			{
				aver = parray[j][i].getRed();
				aveg = parray[j][i].getGreen();
				aveb = parray[j][i].getBlue();
				int count = 1;
						
				for(int k = 1; k < blur; k++) //Begins averaging the width numbers
				{
					if((j + k) > w-1)
					{
						
					}
					else
					{
						aver += parray[j+k][i].getRed();
						aveg += parray[j+k][i].getGreen();
						aveb += parray[j+k][i].getBlue();
						count++;
						
					}
				}
				aver = aver/count;
				aveg = aveg/count;
				aveb = aveb/count;
				
				parray[j][i].setRed(aver);
				parray[j][i].setGreen(aveg);
				parray[j][i].setBlue(aveb);
			}
		}
	}

	
}
