package game;

public class Grid
{
	public Cell[][] map;
	
	public Grid(int rows, int cols)
	{
		map = new Cell[rows][cols];
		genGrid();
	}
	
	public Grid copy()
	{
		Grid newGrid = new Grid(map.length, map[0].length);
		for(int i = 0; i < map.length; i++)
		{
			for(int j = 0; j < map[i].length; j++)
			{
				newGrid.map[i][j] = new Cell(map[i][j].r, map[i][j].c, map[i][j].type);
			}
		}
		return newGrid;
	}
	
	public void genGrid()
	{
		int numWhiteWalkers = (int)(1 + Math.random() * Math.min(map.length, map[0].length));
		
		for(int i = 0; i < map.length; i++)
		{
			for(int j = 0; j < map[i].length; j++)
			{
				map[i][j] = new Cell(i, j, CellType.EMPTY);
			}
		}
		
		int k = 0;
		while(k < numWhiteWalkers)
		{
			int newC = (int)(Math.random() * this.map[0].length);
			int newR = (int)(Math.random() * this.map.length);
			if(newC != map[0].length - 1 && newR != map.length -1 && map[newR][newC].type == CellType.EMPTY)
			{
				map[newR][newC] = new Cell(newR, newC, CellType.WHITEWALKER);
				k++;
			}
		}
		
		boolean noStone = true;
		
		do
		{
			int newC = (int)(Math.random() * this.map[0].length);
			int newR = (int)(Math.random() * this.map.length);
			if(newC != this.map[0].length - 1 && newR != this.map.length - 1 && map[newR][newC].type == CellType.EMPTY)
			{
				map[newR][newC] = new Cell(newR, newC, CellType.DRAGONSTONE);
				noStone = false;
			}
		}while(noStone);
	}
	
	public void kill(int row, int col)
	{
		this.map[row][col].type = CellType.EMPTY;
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
		System.out.println(g.isGoal());
		for (int i = 0; i < g.map.length; i++) {
			for (int j = 0; j < g.map[i].length; j++) {
				g.kill(i, j);
			}
		}
		g.print();
		System.out.println(g.isGoal());
		System.out.println(g.map.length);
	}
}