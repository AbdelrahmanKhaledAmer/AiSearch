package searchAI;

import game.Grid;
import java.util.ArrayList;

public class Node implements Comparable<Node>
{
	private final int MAX_SIZE = 1000000;
	
	private final int NORTH = 1;
	private final int SOUTH = 2;
	private final int EAST = 3;
	private final int WEST = 4;
	private final int PICK = 5;
	private final int KILL = 6;
	
	public final int CMAX;
	public final int RMAX;
	
	public Grid grid;
	public int col;
	public int row;
	public int dragonglass = 0;
	
	public ArrayList<Integer> sequenceOfActions = new ArrayList<Integer>(MAX_SIZE);
	public int cost=0;
	
	public Node(Grid grid)
	{	
		this.grid = new Grid(grid);
		this.RMAX = grid.map.length;
		this.CMAX = grid.map[0].length;
		this.row = grid.map.length - 1;
		this.col = grid.map[0].length - 1;
	}
	
	public Node(Node s)
	{
		this.grid = new Grid(s.grid);
		this.RMAX = s.RMAX;
		this.CMAX = s.CMAX;
		this.col = s.col;
		this.row = s.row;
		this.dragonglass = s.dragonglass;
		this.sequenceOfActions = (ArrayList<Integer>) s.sequenceOfActions.clone();
		this.cost = s.cost;
	}
	
	public boolean isGoal()
	{
		return grid.isGoal();
	}
	
	public boolean equals(Node other)
	{
		return grid.equals(other.grid) && col == other.col &&
				row == other.row && dragonglass == other.dragonglass;
	}

	@Override
	public int compareTo( Node o) {
		return this.cost-o.cost;
	}

	public void print()
	{
		System.out.println("***********************");
		this.grid.print();
		System.out.println("Jon: (" + this.col + ", " + this.row + ")");
		System.out.println("***********************");
	}
	
	public Node kill()
	{
		Node s = new Node(this);
		boolean killed = false;
		if(s.row < s.RMAX - 1 && s.grid.isWhitewalker(s.row + 1, s.col))
		{
			s.grid.kill(s.row + 1, s.col);
			killed = true;
		}
		
		if(s.row > 0 && s.grid.isWhitewalker(s.row - 1, s.col))
		{
			s.grid.kill(s.row - 1, s.col);
			killed = true;
		}
		
		if(s.col < s.CMAX - 1 && s.grid.isWhitewalker(s.row, s.col + 1))
		{
			s.grid.kill(s.row, s.col + 1);
			killed = true;
		}
		
		if(s.col > 0 && s.grid.isWhitewalker(s.row, s.col - 1))
		{
			s.grid.kill(s.row, s.col - 1);
			killed = true;
		}
		
		if(killed)
		{
			s.sequenceOfActions.add(KILL);
		}
		
		s.dragonglass--;
		return s;
	}
	
	public Node pick(int dragonglass)
	{
		Node s = new Node(this);
		s.dragonglass = dragonglass;
		s.sequenceOfActions.add(PICK);
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
	
	public Node north()
	{
		Node s = new Node(this);
		s.updatePosition(this.col, this.row - 1);
		s.sequenceOfActions.add(NORTH);
		return s;
	}
	
	public Node south()
	{
		Node s = new Node(this);
		s.updatePosition(this.col, this.row + 1);
		s.sequenceOfActions.add(SOUTH);
		return s;
	}
	
	public Node east()
	{
		Node s = new Node(this);
		s.updatePosition(this.col + 1, this.row);
		s.sequenceOfActions.add(EAST);
		return s;
	}

	public Node west()
	{
		Node s = new Node(this);
		s.updatePosition(this.col - 1, this.row);
		s.sequenceOfActions.add(WEST);
		return s;
	}
	
	public boolean isAncestor()
	{
		boolean reduced = false;
		int[] counts = new int[5];
		for(int i = sequenceOfActions.size() - 1; i >= 0; i--)
		{
			//Looking for these sequences only: ns, sn, ew, we, nesw, nwse, senw, swne
			if(sequenceOfActions.get(i) < 5)
			{
				counts[sequenceOfActions.get(i)]++;
			} else {
				break;
			}
			if((counts[NORTH] == counts[SOUTH] && counts[EAST] == 0 && counts[WEST] == 0) // ns and sn
			|| (counts[EAST] == counts[WEST] && counts[NORTH] == 0 && counts[SOUTH] == 0) // ew and we
			|| (counts[NORTH] == counts[SOUTH] && counts[NORTH] == counts[EAST] && counts[NORTH] == counts[WEST])) // nesw, nwse, senw and swne
			{
				reduced = true;
				break;
			}
		}
		return reduced;
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
		} else {
			// Get Manhattan distance to a whitewalker
			// Multiply distance - 1 by movement cost
			// Add kill cost			
		}
		return possibleCost;
	}
}