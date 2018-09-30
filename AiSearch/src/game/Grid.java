package game;

public class Grid
{
	public Cell[][] map;
	
	public Grid(int height, int width)
	{
		map = new Cell[width][height];
		genGrid();
	}
	
	public void genGrid()
	{
		int numWhiteWalkers = (int)(1 + Math.random() * 2);
		int numDragonglassPieces = (int)(1 + Math.random() * 2);
		
		for(int i = 0; i < map.length; i++)
		{
			for(int j = 0; j < map[i].length; j++)
			{
				map[i][j] = new Cell(i, j, CellType.EMPTY);
			}
		}
		
		for(int k = 0; k < numWhiteWalkers; k++)
		{
			int newX = (int)(Math.random() * this.map[0].length);
			int newY = (int)(Math.random() * this.map.length);
			if(newX != 0 && newY != 0 && map[newX][newY].type == CellType.EMPTY)
			{
				map[newX][newY] = new Cell(newX, newY, CellType.WHITEWALKER);
			}
			else
			{
				k--;
			}
		}
		
		boolean noStone = true;
		
		do
		{
			int newX = (int)(Math.random() * this.map[0].length);
			int newY = (int)(Math.random() * this.map.length);
			if(newX != this.map[0].length - 1 && newY != this.map.length - 1 && map[newX][newY].type == CellType.EMPTY)
			{
				map[newX][newY] = new Cell(newX, newY, CellType.DRAGONSTONE);
				noStone = false;
			}
		}while(noStone);
	}
	
	public void print()
	{
		for (int i = 0; i < map.length; i++)
		{
			for (int j = 0; j < map[i].length; j++)
			{
				System.out.print("[" + map[i][j] + "]");
			}
			System.out.println("");
		}
	}
	
	public boolean isGoal()
	{
		for (int i = 0; i < map.length; i++)
		{
			for (int j = 0; j < map[i].length; j++)
			{
				if(map[i][j].type == CellType.WHITEWALKER)
				{
					return false;
				}
			}
		}
		return true;
	}
	
	public boolean equals(Grid other)
	{
		for (int i = 0; i < map.length && i < other.map.length; i++)
		{
			for (int j = 0; j < map[i].length && i < other.map[i].length; j++)
			{
				if(!map[i][j].equals(other.map[i][j]))
				{
					return false;
				}
			}
		}
		return true;
	}
	
	public static void main(String[] args)
	{
		Grid g = new Grid(6, 4);
		g.print();
		System.out.println(g.map.length);
	}
}