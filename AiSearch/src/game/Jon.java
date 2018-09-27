package game;

public class Jon
{
	public int xPos;
	public int yPos;
	public int dragonglass;

	private final int XMAX;
	private final int YMAX;
	
	public Jon(int xMax, int yMax)
	{
		this.xPos = 0;
		this.yPos = 0;
		this.dragonglass = 0;
		this.XMAX = xMax;
		this.YMAX = yMax;
	}
	
	public void destroyDragonglass()
	{
		this.dragonglass--;
	}
	
	public void pick(int dragonglass)
	{
		this.dragonglass = dragonglass;
	}
	
	public void updatePosition(int x, int y)
	{
		if(isValid(x , y))
		{
			this.xPos = x;
			this.yPos = y;
		}
	}
	
	private boolean isValid(int x, int y)
	{
		return !(x >= XMAX || y >= YMAX || x < 0 || y < 0);
	}
	
	public void north()
	{
		updatePosition(this.xPos, this.yPos--);
	}
	
	public void south()
	{
		updatePosition(this.xPos, this.yPos++);
	}
	
	public void east()
	{
		updatePosition(this.xPos++, this.yPos);
	}

	public void west()
	{
		updatePosition(this.xPos--, this.yPos);
	}
}