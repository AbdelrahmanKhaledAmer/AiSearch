package game;

public class Cell
{
	public int x;
	public int y;
	public CellType type;
	
	public Cell(int x, int y, CellType type)
	{
		this.x = x;
		this.y = y;
		this.type = type;
	}
	
	public String toString()
	{
		return (this.type == CellType.EMPTY)? "E" :
			(this.type == CellType.DRAGONSTONE)? "D" : "W";
	}
	
	public boolean equals(Cell other)
	{
		return x == other.x && y == other.y && type == other.type;
	}
}