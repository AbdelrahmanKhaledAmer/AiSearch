package searchAI;

import game.SaveWesteros;
import game.SaveWesterosState;

import java.util.ArrayList;

public class WesterosNode extends SearchTreeNode
{
	// action list available (operators)
	private static final int NORTH = 1;
	private static final int SOUTH = 2;
	private static final int EAST  = 3;
	private static final int WEST  = 4;
	private static final int PICK  = 5;
	private static final int KILL  = 6;

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

	public WesterosNode(State state)
	{
		this.state = new SaveWesterosState((SaveWesterosState) state);
	}
	
	public WesterosNode(WesterosNode n)
	{
		this.state = new SaveWesterosState((SaveWesterosState) n.state);
		this.sequenceOfActions = (ArrayList<Integer>) n.sequenceOfActions.clone();
		this.cost = n.cost;
		this.depth = n.depth;
	}

	public void print()
	{
		this.state.print();
	}
	
	public WesterosNode kill()
	{
		SaveWesterosState s = (SaveWesterosState) this.state;
		
		int num = 0;
		boolean killed = false;
		
		if(s.row < s.RMAX - 1 && s.isWhitewalker(s.row + 1, s.col))
		{
			s = s.kill(s.row + 1, s.col);
			killed = true;
			num++;
		}
		
		if(s.row > 0 && s.isWhitewalker(s.row - 1, s.col))
		{
			s = s.kill(s.row - 1, s.col);
			killed = true;
			num++;
		}
		
		if(s.col < s.CMAX - 1 && s.isWhitewalker(s.row, s.col + 1))
		{
			s = s.kill(s.row, s.col + 1);
			killed = true;
			num++;
		}
		
		if(s.col > 0 && s.isWhitewalker(s.row, s.col - 1))
		{
			s = s.kill(s.row, s.col - 1);
			killed = true;
			num++;
		}
		
		WesterosNode n = new WesterosNode(this);
		
		if(killed)
		{
			n.sequenceOfActions.add(KILL);
			switch(num)
			{
			case 1:
				n.cost += KILL1;
				break;
			case 2:
				n.cost += KILL2;
				break;
			case 3:
				n.cost += KILL3;
				break;
			}
			s.dragonglass--;
			n.state = s;
		}
		return n;
	}
	
	public WesterosNode pick()
	{
		SaveWesterosState s = (SaveWesterosState) this.state;
		s = s.pick();
		WesterosNode n = new WesterosNode(this);
		n.sequenceOfActions.add(PICK);
		n.cost += D_PICK;
		n.state = s;
		return n;
	}
	
	public int getMovementCost(int col, int row)
	{
		SaveWesterosState s = (SaveWesterosState) this.state;
		if(s.isWhitewalker(row, col))
		{
			return X_TO_W;
		}
		if(s.dragonglass == 0 && s.isDragonstone(row, col))
		{
			return E_TO_D_NO_DG;					
		}
		return X_TO_E;
	}
	
	public WesterosNode north()
	{
		SaveWesterosState s = (SaveWesterosState) this.state;
		s = s.north();
		WesterosNode n = new WesterosNode(this);
		n.sequenceOfActions.add(NORTH);
		n.cost += n.getMovementCost(s.col, s.row);
		n.state = s;
		return n;
	}
	
	public WesterosNode south()
	{	
		SaveWesterosState s = (SaveWesterosState) this.state;
		s = s.south();
		WesterosNode n = new WesterosNode(this);
		n.sequenceOfActions.add(SOUTH);
		n.cost += n.getMovementCost(s.col, s.row);
		n.state = s;
		return n;
	}
	
	public WesterosNode east()
	{	
		SaveWesterosState s = (SaveWesterosState) this.state;
		s = s.east();
		WesterosNode n = new WesterosNode(this);
		n.sequenceOfActions.add(EAST);
		n.cost += n.getMovementCost(s.col, s.row);
		n.state = s;
		return n;
	}

	public WesterosNode west()
	{	
		SaveWesterosState s = (SaveWesterosState) this.state;
		s = s.west();
		WesterosNode n = new WesterosNode(this);
		n.sequenceOfActions.add(WEST);
		n.cost += n.getMovementCost(s.col, s.row);
		n.state = s;
		return n;
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
		SaveWesterosState s = (SaveWesterosState) this.state;
		if(s.isWhitewalker(s.row, s.col))
		{
			return q;
		} else {
			WesterosNode n1 = this.north();
			if(!n1.equals(this) && !n1.isAncestor())
			{
				q.add(n1);
			}
			WesterosNode n2 = this.south();
			if(!n2.equals(this) && !n2.isAncestor())
			{
				q.add(n2);
			}
			WesterosNode n3 = this.east();
			if(!n3.equals(this) && !n3.isAncestor())
			{
				q.add(n3);
			}
			WesterosNode n4 = this.west();
			if(!n4.equals(this) && !n4.isAncestor())
			{
				q.add(n4);
			}
			if(s.dragonglass == 0 && s.isDragonstone(s.row, s.col))
			{
				WesterosNode n5 = this.pick();
				q.add(n5);
			}
			if(((s.row < s.RMAX - 1 && s.isWhitewalker(s.row + 1, s.col))
					|| (s.row > 0 && s.isWhitewalker(s.row - 1, s.col))
					|| (s.col < s.CMAX - 1 && s.isWhitewalker(s.row, s.col + 1))
					|| (s.col > 0 && s.isWhitewalker(s.row, s.col - 1)))
					&& s.dragonglass > 0)
			{
				WesterosNode n6 = this.kill();
				q.add(n6);
			}
		}
		return q;
	}
	
	public int heuristic1()
	{
		SaveWesterosState s = (SaveWesterosState)this.state;
		SaveWesteros prob = new SaveWesteros();
		if(prob.isGoal(s))
		{
			return 0;
		}
		int possibleCost = 0;
		int whitewalkerDistance = s.CMAX + s.RMAX + 1;
		if(s.dragonglass == 0)
		{
			int dRow = 0;
			int dCol = 0;
			int dragonstoneDistance = 0;
			// Get Manhattan Distance to dragonstone
			for (int r = 0; r < s.map.length; r++)
			{
				for (int c = 0; c < s.map[r].length; c++)
				{
					if(s.isDragonstone(r, c))
					{
						dragonstoneDistance = Math.abs(r - s.row) + Math.abs(c - s.col);
						dRow = r;
						dCol = c;
						break;
					}
				}
			}
			// Multiply dragonstoneDistance - 1 by movement cost
			possibleCost += (dragonstoneDistance - 1) * X_TO_E;
			// Add dragonstone cost
			possibleCost += E_TO_D_NO_DG;
			possibleCost += D_PICK;
			// Get Manhattan distance to a whitewalker
			for (int r = 0; r < s.map.length; r++)
			{
				for (int c = 0; c < s.map[r].length; c++)
				{
					if(s.isWhitewalker(r, c)) {
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
			// Add kill cost
			possibleCost += KILL3;
		} else {
			// Get Manhattan distance to a whitewalker
			for (int r = 0; r < s.map.length; r++)
			{
				for (int c = 0; c < s.map[r].length; c++)
				{
					if(s.isWhitewalker(r, c)) {
						int d = Math.abs(r - s.row) + Math.abs(c - s.col);
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
}