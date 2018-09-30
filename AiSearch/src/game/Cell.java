package game;

public class Cell
{
	public int c;
	public int r;
	public CellType type;
	
	public Cell(int r, int c, CellType type)
	{
		this.c = c;
		this.r = r;
		this.type = type;
	}
	
	public String toString()
	{
		return (this.type == CellType.EMPTY)? "E" :
			(this.type == CellType.DRAGONSTONE)? "D" : "W";
	}
	
	public boolean equals(Cell other)
	{
		return c == other.c && r == other.r && type == other.type;
	}
}