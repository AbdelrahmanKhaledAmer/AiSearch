package game;

import searchAI.Node;
import searchAI.SearchQ;

import java.util.ArrayList;

public class SaveWesterosState
{
	private static final int MAX_SIZE = 1000000;


	// action list available (operators)
	private static final int NORTH = 1;
	private static final int SOUTH = 2;
	private static final int EAST  = 3;
	private static final int WEST  = 4;
	private static final int PICK  = 5;
	private static final int KILL  = 6;

	//costs

	// Movement Costs
	private static final int X_TO_W       = 64;
	private static final int X_TO_E       = 16;
	private static final int E_TO_D_NO_DG = 8;

	// Picking up Dragonglass cost
	private static final int D_PICK = 4;

	// Killing Whitewalkers cost
	private static final int KILL1 = 4;
	private static final int KILL2 = 2;
	private static final int KILL3 = 1;

	public final int CMAX;
	public final int RMAX;


	//state

	public SaveWesteros saveWesteros;

	public int col,row,cost, dragonglass,numDragonglassPieces,map[][];


	public ArrayList<Integer> sequenceOfActions = new ArrayList<Integer>(MAX_SIZE);

	public SaveWesterosState(SaveWesteros grid)
	{
		this.saveWesteros = new SaveWesteros(grid);
		this.map = this.saveWesteros.map;
		this.RMAX = grid.map.length;
		this.CMAX = grid.map[0].length;
		this.row = grid.map.length - 1;
		this.col = grid.map[0].length - 1;
		this.numDragonglassPieces = this.saveWesteros.numDragonglassPieces;
	}

	public SaveWesterosState(SaveWesterosState s)
	{
		this.saveWesteros = new SaveWesteros(s.saveWesteros);
		this.map = this.saveWesteros.map;
		this.RMAX = s.RMAX;
		this.CMAX = s.CMAX;
		this.col = s.col;
		this.row = s.row;
		this.dragonglass = s.dragonglass;
		this.sequenceOfActions = (ArrayList<Integer>) s.sequenceOfActions.clone();
		this.cost = s.cost;
		this.numDragonglassPieces = saveWesteros.numDragonglassPieces;
	}

	public boolean isGoal()
	{
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				if (map[i][j] == SaveWesteros.WHITEWALKER) {
					return false;
				}
			}
		}
		return true;
	}

	public boolean equals(SaveWesterosState other)
	{
		for (int i = 0; i < map.length && i < other.map.length; i++) {
            for (int j = 0; j < map[i].length && i < other.map[i].length; j++) {
                if (map[i][j] != other.map[i][j]) {
                    return false;
                }
            }
        }
		return  col == other.col &&
				row == other.row && dragonglass == other.dragonglass;
	}

	public void print()
	{
		System.out.println("***********************");
		this.saveWesteros.print();
		System.out.println("Jon: (" + this.col + ", " + this.row + ")");
		System.out.println("***********************");
	}

	public SaveWesterosState kill()
	{
		SaveWesterosState s = new SaveWesterosState(this);
		int num = 0;
		boolean killed = false;
		if(s.row < s.RMAX - 1 && s.isWhitewalker(s.row + 1, s.col))
		{
			s.saveWesteros.kill(s.row + 1, s.col);
			killed = true;
			num++;
		}

		if(s.row > 0 && s.isWhitewalker(s.row - 1, s.col))
		{
			s.saveWesteros.kill(s.row - 1, s.col);
			killed = true;
			num++;
		}

		if(s.col < s.CMAX - 1 && s.isWhitewalker(s.row, s.col + 1))
		{
			s.saveWesteros.kill(s.row, s.col + 1);
			killed = true;
			num++;
		}

		if(s.col > 0 && s.isWhitewalker(s.row, s.col - 1))
		{
			s.saveWesteros.kill(s.row, s.col - 1);
			killed = true;
			num++;
		}

		if(killed)
		{
			s.sequenceOfActions.add(KILL);
			switch(num)
			{
			case 1:
				s.cost += KILL1;
				break;
			case 2:
				s.cost += KILL2;
				break;
			case 3:
				s.cost += KILL3;
				break;
			}
		}

		s.dragonglass--;
		return s;
	}

	public SaveWesterosState pick()
	{
		SaveWesterosState s = new SaveWesterosState(this);
		s.dragonglass = numDragonglassPieces;
		s.sequenceOfActions.add(PICK);
		s.cost += D_PICK;
//		System.out.println("returned from pick");
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
			this.cost += getMovementCost(x, y);
			this.col = x;
			this.row = y;
		}
	}

	public int getMovementCost(int col, int row)
	{
		if(this.isWhitewalker(row, col))
		{
			return X_TO_W;
		}
		if(dragonglass == 0 && this.isDragonstone(row, col))
		{
			return E_TO_D_NO_DG;
		}
		return X_TO_E;
	}

	public SaveWesterosState north() //TODO
	{
		SaveWesterosState s = new SaveWesterosState(this);
		s.updatePosition(this.col, this.row - 1);
		s.sequenceOfActions.add(NORTH);
		return s;
	}

	public SaveWesterosState south()
	{
		SaveWesterosState s = new SaveWesterosState(this);
		s.updatePosition(this.col, this.row + 1);
		s.sequenceOfActions.add(SOUTH);
		return s;
	}

	public SaveWesterosState east()
	{
		SaveWesterosState s = new SaveWesterosState(this);
		s.updatePosition(this.col + 1, this.row);
		s.sequenceOfActions.add(EAST);
		return s;
	}

	public SaveWesterosState west()
	{
		SaveWesterosState s = new SaveWesterosState(this);
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
			|| (counts[NORTH] == counts[SOUTH] && counts[EAST] == counts [WEST]))
			{
				reduced = true;
				break;
			}
		}
		return reduced;
	}

	public SearchQ expandNode(SearchQ q)
	{
		if(this.isWhitewalker(this.row, this.col))
		{
			return q;
		}
		else
		{
//			System.out.println("checking possible states");
			SaveWesterosState s1 = this.north();
			if(!s1.equals(this) && !s1.isAncestor())
			{
//				System.out.println("adding node 1");
				q.add(new Node(s1));
			}
			SaveWesterosState s2 = this.south();

			if(!s2.equals(this) && !s2.isAncestor())
			{
//				System.out.println("adding node 2");
				q.add(new Node(s2));
			}
			SaveWesterosState s3 = this.east();
//			System.out.println("checking "+(s3.isAncestor())+" jon "+row+" "+col);
//			this.print();
//			s3.print();
//			System.out.println("nehahahahahahahahaha");
			if(!s3.equals(this) && !s3.isAncestor())
			{

//				System.out.println("adding node 3");

				q.add(new Node(s3));
			}
			SaveWesterosState s4 = this.west();
			if(!s4.equals(this) && !s4.isAncestor())
			{
//				System.out.println("adding node 4");

				q.add(new Node(s4));
			}
			if(this.dragonglass == 0 && this.isDragonstone(this.row, this.col))
			{
//				System.out.println("adding node 5");

				SaveWesterosState s5 = this.pick();
				q.add(new Node(s5));
			}
			if(((this.row < this.RMAX - 1 && this.isWhitewalker(this.row + 1, this.col))
					|| (this.row > 0 && this.isWhitewalker(this.row - 1, this.col))
					|| (this.col < this.CMAX - 1 && this.isWhitewalker(this.row, this.col + 1))
					|| (this.col > 0 && this.isWhitewalker(this.row, this.col - 1)))
					&& this.dragonglass > 0)
			{
				SaveWesterosState s6 = this.kill();
//				System.out.println("adding node 6");
				q.add(new Node(s6));
			}
		}
		return q;
	}

	public int heuristic1()
	{
		if(this.isGoal())
		{
			return 0;
		}
		int possibleCost = 0;
		int whitewalkerDistance = CMAX + RMAX + 1;
		if(this.dragonglass == 0)
		{
			int dRow = 0;
			int dCol = 0;
			int dragonstoneDistance = 0;
			// Get Manhattan Distance to dragonstone
			for (int r = 0; r < this.map.length; r++)
			{
				for (int c = 0; c < this.map[r].length; c++)
				{
					if(this.isDragonstone(r, c))
					{
						dragonstoneDistance = Math.abs(r - row) + Math.abs(c - col);
						dRow = r;
						dCol = c;
						break;
					}
				}
			}
			// Multiply dragonstoneDistance - 1 by movement cost
			possibleCost += (dragonstoneDistance - 1) * X_TO_E;
//			System.out.println(possibleCost);
			// Add dragonstone cost
			possibleCost += E_TO_D_NO_DG;
			possibleCost += D_PICK;
//			System.out.println(possibleCost);
			// Get Manhattan distance to a whitewalker
			for (int r = 0; r < this.map.length; r++)
			{
				for (int c = 0; c < this.map[r].length; c++)
				{
					if(this.isWhitewalker(r, c)) {
						int d = Math.abs(r - dRow) + Math.abs(c - dCol);
						if(whitewalkerDistance > d)
						{
							whitewalkerDistance = d;
						}
					}
				}
			}
			// Multiply whitewalkerDistance - 1 by movement cost
			possibleCost += (whitewalkerDistance - 1) * X_TO_E;
//			System.out.println(possibleCost);
			// Add kill cost
			possibleCost += KILL3;
//			System.out.println(possibleCost);
		} else {
			// Get Manhattan distance to a whitewalker
			for (int r = 0; r < this.map.length; r++)
			{
				for (int c = 0; c < this.map[r].length; c++)
				{
					if(this.isWhitewalker(r, c)) {
						int d = Math.abs(r - row) + Math.abs(c - col);
						if(whitewalkerDistance > d)
						{
							whitewalkerDistance = d;
						}
					}
				}
			}
			// Multiply whitewalkerDistance - 1 by movement cost
			possibleCost += (whitewalkerDistance - 1) * X_TO_E;
			// Add kill cost
			possibleCost += KILL3;
		}
		return possibleCost;
	}


	public boolean isEmpty(int row, int col) {
		return map[row][col] == SaveWesteros.EMPTY;
	}

	public boolean isDragonstone(int row, int col) {
		return map[row][col] == SaveWesteros.DRAGONSTONE;
	}

	public boolean isWhitewalker(int row, int col) {
		return map[row][col] == SaveWesteros.WHITEWALKER;
	}
}