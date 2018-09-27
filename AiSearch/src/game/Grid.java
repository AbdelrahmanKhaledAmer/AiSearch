package game;

import java.util.Arrays;

public class Grid
{
	public int width;
	public int height;
	public Cell[][] map;
	
	public Grid(int height, int width)
	{
		this.height = height;
		this.width = width;
		map = new Cell[this.width][this.height];
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
			int newX = (int)(Math.random() * this.width);
			int newY = (int)(Math.random() * this.height);
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
			int newX = (int)(Math.random() * this.width);
			int newY = (int)(Math.random() * this.height);
			if(newX != this.width - 1 && newY != this.height - 1 && map[newX][newY].type == CellType.EMPTY)
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
	
	public static void main(String[] args)
	{
		Grid g = new Grid(4, 4);
		g.print();
	}
}