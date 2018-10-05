package game;

import searchAI.State;

public class SaveWesterosState extends State
{
	// Grid Variables
	private static final int EMPTY = 0;
	private static final int DRAGONSTONE = 1;
	private static final int WHITEWALKER = 2;
	
	// Max numbers
	private final int MAXWW;
	private final int MAXDG;
	
	// Map dimensions
	public final int CMAX;
	public final int RMAX;

	// Local variables
	public int[][] map;
	public int row;
	public int col;
	public int dragonglass = 0;

	public SaveWesterosState(SaveWesteros prob, int rows, int cols)
	{
		this.RMAX = rows;
		this.CMAX = cols;
		this.row = rows - 1;
		this.col = cols - 1;
		this.MAXWW = prob.numWhitewalkers;
		this.MAXDG = prob.numDragonglassPieces;
		this.map = new int[rows][cols];
		for (int i = 0; i < map.length; i++)
		{
			for (int j = 0; j < map[i].length; j++)
			{
				map[i][j] = EMPTY;
	        }
		} 

	    int k = 0;
	    while (k < prob.numWhitewalkers) {
	    	int newC = (int) (Math.random() * this.map[0].length);
	        int newR = (int) (Math.random() * this.map.length);
	        if (newC != map[0].length - 1 && newR != map.length - 1 && map[newR][newC] == EMPTY) {
	        	map[newR][newC] = WHITEWALKER;
	            k++;
	        }
	    }

	    boolean noStone = true;

	    do {
	    	int newC = (int) (Math.random() * this.map[0].length);
	        int newR = (int) (Math.random() * this.map.length);
	        if (newC != this.map[0].length - 1 && newR != this.map.length - 1 && map[newR][newC] == EMPTY) {
	        	map[newR][newC] = DRAGONSTONE;
	            noStone = false;
	        }
	    } while (noStone);
	}
	
	public SaveWesterosState(SaveWesterosState s)
	{
		this.RMAX = s.RMAX;
		this.CMAX = s.CMAX;
		this.MAXWW = s.MAXWW;
		this.MAXDG = s.MAXDG;
		this.map = s.map.clone();
		this.col = s.col;
		this.row = s.row;
		this.dragonglass = s.dragonglass;
	}
	
	public boolean equals(SaveWesterosState other)
	{
		for (int i = 0; i < map.length; i++)
		{
			for (int j = 0; j < map[i].length; j++)
			{
				if(map[i][j] != other.map[i][j])
				{
					return false;
				}
			}
		}
		return col == other.col && row == other.row && dragonglass == other.dragonglass;
	}
	

	public void print()
	{
		System.out.println("***********************");
		for (int i = 0; i < map.length; i++)
		{
            for (int j = 0; j < map[i].length; j++)
            {
                System.out.print("[" + ((map[i][j] == EMPTY) ? " " : (map[i][j] == WHITEWALKER) ? "W" : "D") + "]");
            }
            System.out.println("");
        }
		System.out.println("Jon: (" + this.col + ", " + this.row + ")");
		System.out.println("DragonGlass Pieces: " + this.dragonglass);
		System.out.println("***********************");
	}
	
    public boolean isEmpty(int row, int col)
    {
        return map[row][col] == EMPTY;
    }

    public boolean isDragonstone(int row, int col)
    {
        return map[row][col] == DRAGONSTONE;
    }

    public boolean isWhitewalker(int row, int col)
    {
        return map[row][col] == WHITEWALKER;
    }

    public SaveWesterosState kill(int row, int col)
    {
    	SaveWesterosState s = new SaveWesterosState(this);
    	s.map[row][col] = EMPTY;
    	return s;
    }
    
	public SaveWesterosState pick()
	{
		SaveWesterosState s = new SaveWesterosState(this);
		s.dragonglass = s.MAXDG;
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
	
	public SaveWesterosState north()
	{
		SaveWesterosState s = new SaveWesterosState(this);
		s.updatePosition(this.col, this.row - 1);
		return s;
	}
	
	public SaveWesterosState south()
	{
		SaveWesterosState s = new SaveWesterosState(this);
		s.updatePosition(this.col, this.row + 1);
		return s;
	}
	
	public SaveWesterosState east()
	{
		SaveWesterosState s = new SaveWesterosState(this);
		s.updatePosition(this.col + 1, this.row);
		return s;
	}

	public SaveWesterosState west()
	{
		SaveWesterosState s = new SaveWesterosState(this);
		s.updatePosition(this.col - 1, this.row);
		return s;
	}
}