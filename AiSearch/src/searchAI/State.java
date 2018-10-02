package searchAI;

import game.Grid;

public class State
{
	public final int CMAX;
	public final int RMAX;
	
	public int col;
	public int row;
	public int dragonglass;

	public Grid grid;
	
	public State(Grid grid)
	{	
		this.grid = new Grid(grid);
		this.RMAX = grid.map.length;
		this.CMAX = grid.map[0].length;
		this.row = grid.map.length - 1;
		this.col = grid.map[0].length - 1;
	}
	
	public State(State s)
	{
		this.grid = new Grid(s.grid);
		this.RMAX = s.RMAX;
		this.CMAX = s.CMAX;
		this.col = s.col;
		this.row = s.row;
		this.dragonglass = s.dragonglass;
	}
	
	public boolean isGoal()
	{
		return grid.isGoal();
	}
	
	public boolean equals(State other)
	{
		return grid.equals(other.grid) && col == other.col &&
				row == other.row && dragonglass == other.dragonglass;
	}
	
	public void print()
	{
		System.out.println("***********************");
		this.grid.print();
		System.out.println("Jon: (" + this.col + ", " + this.row + ")");
		System.out.println("***********************");
	}
	
	public State kill()
	{
		State s = new State(this);
		if(s.row < s.RMAX - 1 && s.grid.isWhitewalker(s.row + 1, s.col))
		{
			s.grid.kill(s.row + 1, s.col);
		}
		
		if(s.row > 0 && s.grid.isWhitewalker(s.row - 1, s.col))
		{
			s.grid.kill(s.row - 1, s.col);
		}
		
		if(s.col < s.CMAX - 1 && s.grid.isWhitewalker(s.row, s.col + 1))
		{
			s.grid.kill(s.row, s.col + 1);
		}
		
		if(s.col > 0 && s.grid.isWhitewalker(s.row, s.col - 1))
		{
			s.grid.kill(s.row, s.col - 1);
		}
		
		s.dragonglass--;
		return s;
	}
	
	public State pick(int dragonglass)
	{
		State s = new State(this);
		s.dragonglass = dragonglass;
		return s;
	}
	
	private boolean isValid(int x, int y)
	{
		return !(x >= CMAX || y >= RMAX || x < 0 || y < 0);
	}
	
	public void updatePosition(int x, int y)
	{
		if(isValid(x , y))
		{
			this.col = x;
			this.row = y;
		}
	}
	
	public State north()
	{
		State s = new State(this);
		s.updatePosition(this.col, this.row - 1);
		return s;
	}
	
	public State south()
	{
		State s = new State(this);
		s.updatePosition(this.col, this.row + 1);
		return s;
	}
	
	public State east()
	{
		State s = new State(this);
		s.updatePosition(this.col + 1, this.row);
		return s;
	}

	public State west()
	{
		State s = new State(this);
		s.updatePosition(this.col - 1, this.row);
		return s;
	}
	
	public int heuristic1()
	{
		if(this.isGoal())
		{
			return 0;
		}
		
		int possibleCost = 0;
		if(this.dragonglass == 0)
		{
			// Get Manhattan Distance to dragonstone
			// Multiply distance - 1 by movement cost
			// Add dragonstone cost
			// Get Manhattan distance to a whitewalker
			// Multiply distance - 1 by movement cost
			// Add kill cost
		}
		else
		{
			// Get Manhattan distance to a whitewalker
			// Multiply distance - 1 by movement cost
			// Add kill cost			
		}
		return possibleCost;
	}
}