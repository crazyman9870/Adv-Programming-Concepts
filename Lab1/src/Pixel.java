
public class Pixel 
{
	private int r;
	private int g;
	private int b;

	
	public Pixel()
	{
		
	}
	public Pixel(int ir, int ig, int ib)
	{
		r = ir;
		g = ig;
		b = ib;
	}

	
	public int getRed()
	{
		return r;
	}
	
	public void setRed(int newr)
	{
		r = newr;
	}
	
	public int getGreen()
	{
		return g;
	}
	
	public void setGreen(int newg)
	{
		g = newg;
	}
	
	public int getBlue()
	{
		return b;
	}
	
	public void setBlue(int newb)
	{
		b = newb;
	}
	
	public void invert()
	{
		r = (255 - r);
		g = (255 - g);
		b = (255 - b);
		
	}
	
	public void grayscale()
	{
		int conversion = (r + g + b) / 3;
		r = conversion;
		g = conversion;
		b = conversion;
	}
	
	public void emboss1()
	{
		/*
		int rDif = r - 128;
		int gDif = g - 128;
		int bDif = b - 128;
		
		int maxDif = findGreatest(Math.abs(rDif), Math.abs(gDif), Math.abs(bDif));
		
		maxDif += 128;
		maxDif = check(maxDif);
		*/
	
	
		r = 128;
		g = 128;
		b = 128;
		
	}
	
	public void emboss2(Pixel p)
	{
		int rDif = r - p.getRed();
		int gDif = g - p.getGreen();
		int bDif = b - p.getBlue();
		
		int maxDif = findGreatest(rDif, gDif, bDif);
		
		int v = 128 + maxDif;
		v = check(v);

		r = v;
		g = v;
		b = v;
	}
	
	public int check(int n) //checks max or min values
	{
		if(n > 255)
		{
			n = 255;
			return n;
		}
		if(n < 0)
		{
			n = 0;
			return n;
		}
		return n;
	}
	
	public int findGreatest(int rd, int gd, int bd)
	{
		int max = rd;
		
		if(Math.abs(max) < Math.abs(gd)) //checks red difference against green difference
		{
			max = gd;
		}
		if(Math.abs(max) < Math.abs(bd)) //checks winner against blue difference
		{
			max = bd;
		}
		
		return max;
	}
}
