import java.awt.*;
import java.util.*;

public class Serpente
{
	private ArrayList <Point> corpo =new ArrayList<Point>();
	private int lung;
	
	public Serpente ()
	{
		creaSerpente();
	}
	public ArrayList<Point> getCorpo ()
	{
		return corpo;
	}
	public void creaSerpente()
	{
		lung=4;
		corpo.clear();
		for (int i=0;i<lung;i++)
		{
			Point body = new Point(4-i, 1); //posizione iniziale del serpente
			corpo.add(body);
			
		}
	}
	
	public boolean isSnakeThereX(int row)
	{
		for(Point body:corpo)
		{
			if (body.x==row)
			{
				
				return true;
			}
		}
		return false;
	}
	
	public boolean isSnakeThereY(int col)
	{
		for(Point body:corpo)
		{
			if (body.y==col)
			{
			
				return true;
			}
		}
		return false;
	}
	
	
	
}
