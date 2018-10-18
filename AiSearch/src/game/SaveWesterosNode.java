/**
 * The SaveWesterosNode class is a subclass of the Node abstract.
 * It represents a node in a search tree that holds all the defining state
 * features of the SaveWesteros search problem.
 */

package game;

import searchAI.Node;
import searchAI.SearchQ;

public class SaveWesterosNode extends Node
{
	// action list available (operators)
	private static final char NORTH = 'N';
	private static final char SOUTH = 'S';
	private static final char EAST = 'E';
	private static final char WEST = 'W';
	private static final char PICK = 'P';
	private static final char KILL = 'K';
	// Indices of actions for isAncestor method to use
	private static final int NORTHI = 0;
	private static final int SOUTHI = 1;
	private static final int EASTI = 2;
	private static final int WESTI = 3;

	// Movement Costs
	private static int X_TO_W;
	private static int X_TO_E;

	// Picking up Dragonglass cost
	private static int D_PICK;

	// Killing Whitewalkers cost
	private static int KILL1;
	private static int KILL2;
	private static int KILL3;

	// Variables holding the grid-world's limits
	public final int CMAX;
	public final int RMAX;

	// Variables holding state features
	public int col, row, dragonglass, numDragonglassPieces, map[][];

	/**
	 * Constructor for the class SaveWesterosNode.
	 * @param grid A 2D array representing the grid-world.
	 * @param numDragonglassPieces The maximum dragonglass carrying capacity.
	 */
	public SaveWesterosNode(int[][] grid, int numDragonglassPieces)
	{
		this.map = clone(grid);
		this.RMAX = grid.length;
		this.CMAX = grid[0].length;
		this.row = grid.length - 1;
		this.col = grid[0].length - 1;
		this.numDragonglassPieces = numDragonglassPieces;
		sequenceOfActions = "";

		// Initialisation of cost weights as a a power of the grid-world size
		defineCostValues(Math.max(CMAX, RMAX));
	}

	/**
	 * Copy constructor for the class SaveWesterosNode.
	 * @param other The node to be copied.
	 */
	public SaveWesterosNode(SaveWesterosNode other)
	{
		this.map = clone(other.map);
		this.RMAX = other.RMAX;
		this.CMAX = other.CMAX;
		this.col = other.col;
		this.row = other.row;
		this.dragonglass = other.dragonglass;
		this.sequenceOfActions = other.sequenceOfActions;
		this.cost = other.cost;
		this.numDragonglassPieces = other.numDragonglassPieces;
		this.setMaxDepth(other.MaxDepth);
	}
	
	/**
	 * Set the permanent cost values once the first node is created.
	 * @param base The base number at which the remainder of the costs are calculated.
	 */
	public void defineCostValues(int base)
	{
		//cascading powers of base value
		KILL3  = 1;
		KILL2  = base * base;
		KILL1  = base * base * KILL2;
		X_TO_E = base * KILL1;
		D_PICK = base *X_TO_E;
		X_TO_W = base * D_PICK;
	}

	/**
	 * Makes an exact copy of a 2D array of integers
	 * @param grid The Array to be copied
	 * @return int[][] Returns a copy of the input array.
	 */
	public int[][] clone(int[][] grid)
	{
		int[][] array = new int[grid.length][grid[0].length];
		for (int i = 0; i < grid.length; i++)
		{
			for (int j = 0; j < grid[i].length; j++)
			{
				array[i][j] = grid[i][j];
			}
		}
		return array;
	}

	/**
	 * Checks if the current state in this node is a goal state.
	 * @return boolean If true, then the state is a goal state.
	 */
	public boolean isGoal()
	{
		for (int i = 0; i < map.length; i++)
		{
			for (int j = 0; j < map[i].length; j++)
			{
				if (map[i][j] == SaveWesteros.WHITEWALKER)
				{
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Checks if the state of this node is equal to another.
	 * @param other The node being compared with
	 * @return boolean If true then the two states are equal.
	 */
	public boolean equals(SaveWesterosNode other)
	{
		// First, loop through the grid to see if it's equal.
		for (int i = 0; i < map.length && i < other.map.length; i++)
		{
			for (int j = 0; j < map[i].length && i < other.map[i].length; j++)
			{
				if (map[i][j] != other.map[i][j])
				{
					return false;
				}
			}
		}
		// Afterwards, check all other parameters.
		return col == other.col &&
				row == other.row && dragonglass == other.dragonglass;
	}

	/**
	 * Print the all the state features for this node.
	 */
	public void print()
	{
		// First print the grid-world.
		System.out.println("***********************");
		for (int i = 0; i < this.map.length; i++)
		{
			for (int j = 0; j < this.map[i].length; j++)
			{
				System.out.print("[" + ((this.map[i][j] == SaveWesteros.EMPTY) ? " " :
					(this.map[i][j] == SaveWesteros.WHITEWALKER) ? "W" :
						(this.map[i][j] == SaveWesteros.OBSTACLE) ? "O" : "D") + "]");
			}
			System.out.println("");
		}
		// Then print Jon's position and the number of dragonglass he has in hand.
		System.out.println("Jon: (" + this.row + ", " + this.col + "). Dragonglass held: " + this.dragonglass);
		System.out.println("***********************");
	}

	/**
	 * Perform the kill action given the state features of this node.
	 * @return SaveWesterosNode The resulting child node from a kill action. 
	 */
	public SaveWesterosNode kill()
	{
		SaveWesterosNode s = new SaveWesterosNode(this);
		// Variable to increment for every whitewalker killed.
		int num = 0;
		// Check for a whitewalker south of Jon
		if (s.row < s.RMAX - 1 && s.isWhitewalker(s.row + 1, s.col))
		{
			s.map[s.row + 1][s.col] = SaveWesteros.EMPTY;
			num++;
		}

		// Check for a whitewalker north of Jon
		if (s.row > 0 && s.isWhitewalker(s.row - 1, s.col))
		{
			s.map[s.row - 1][s.col] = SaveWesteros.EMPTY;
			num++;
		}

		// Check for a whitewalker east of Jon
		if (s.col < s.CMAX - 1 && s.isWhitewalker(s.row, s.col + 1))
		{
			s.map[s.row][s.col + 1] = SaveWesteros.EMPTY;
			num++;
		}

		// Check for a whitewalker west of Jon
		if (s.col > 0 && s.isWhitewalker(s.row, s.col - 1))
		{
			s.map[s.row][s.col - 1] = SaveWesteros.EMPTY;
			num++;
		}
		
		// Add the kill action to the sequence and check how many whitewalkers were
		// killed to assign the appropriate cost.
		switch (num)
		{
		case 0:
			break;
		case 1:
			s.sequenceOfActions += KILL;
			s.cost += KILL1;
			break;
		case 2:
			s.sequenceOfActions += KILL;
			s.cost += KILL2;
			break;
		case 3:
			s.sequenceOfActions += KILL;
			s.cost += KILL3;
			break;
		}
		s.dragonglass--;
		return s;
	}

	/**
	 * Perform the pick action given the state features of this node.
	 * @return SaveWesterosNode The resulting child node from a pick action. 
	 */
	public SaveWesterosNode pick()
	{
		SaveWesterosNode s = new SaveWesterosNode(this);
		s.dragonglass = numDragonglassPieces;
		s.sequenceOfActions += PICK;
		s.cost += D_PICK;
		return s;
	}

	/**
	 * Checks to see if a grid-world position is a valid position for Jon
	 * @param col The column to be checked
	 * @param row The row to be checked
	 * @return boolean If true, then map[row][col] is a valid position
	 */
	private boolean isValid(int col, int row)
	{
		return !(col >= CMAX || row >= RMAX || col < 0 || row < 0) && map[row][col] != SaveWesteros.OBSTACLE;
	}

	/**
	 * Changes Jon's position to a new position if valid.
	 * @param col The column to move to.
	 * @param row The row to move to.
	 */
	public void updatePosition(int col, int row)
	{
		// Check if the target position is valid
		if (isValid(col, row))
		{
			// Add the movement cost and update Jon's coordinates
			this.cost += getMovementCost(col, row);
			this.col = col;
			this.row = row;
		}
	}

	/**
	 * Get the cost of moving to the position indicated by col and row.
	 * @param col The column in question
	 * @param row The row in question
	 * @return int The cost of the movement action
	 */
	public int getMovementCost(int col, int row)
	{
		// Cost of moving to a cell with a whitewalker.
		if (this.isWhitewalker(row, col))
		{
			return X_TO_W;
		}
		// Cost of moving to an empty cell
		return X_TO_E;
	}

	/**
	 * Perform the move north action given the state features of this node.
	 * @return SaveWesterosNode The resulting child node from a move north action.
	 */
	public SaveWesterosNode north()
	{
		SaveWesterosNode s = new SaveWesterosNode(this);
		// Update Jon's position and add the movement to the sequence of actions.
		s.updatePosition(this.col, this.row - 1);
		s.sequenceOfActions += NORTH;
		return s;
	}

	/**
	 * Perform the move south action given the state features of this node.
	 * @return SaveWesterosNode The resulting child node from a move south action.
	 */
	public SaveWesterosNode south()
	{
		SaveWesterosNode s = new SaveWesterosNode(this);
		// Update Jon's position and add the movement to the sequence of actions.
		s.updatePosition(this.col, this.row + 1);
		s.sequenceOfActions += SOUTH;
		return s;
	}

	/**
	 * Perform the move east action given the state features of this node.
	 * @return SaveWesterosNode The resulting child node from a move east action.
	 */
	public SaveWesterosNode east()
	{
		SaveWesterosNode s = new SaveWesterosNode(this);
		// Update Jon's position and add the movement to the sequence of actions.
		s.updatePosition(this.col + 1, this.row);
		s.sequenceOfActions += EAST;
		return s;
	}

	/**
	 * Perform the move west action given the state features of this node.
	 * @return SaveWesterosNode The resulting child node from a move west action.
	 */
	public SaveWesterosNode west()
	{
		SaveWesterosNode s = new SaveWesterosNode(this);
		// Update Jon's position and add the movement to the sequence of actions.
		s.updatePosition(this.col - 1, this.row);
		s.sequenceOfActions += WEST;
		return s;
	}

	/**
	 * Checks if this node has a sequence of movement actions that repeat an older state.
	 * @return boolean If true, then this node is equal to one of its ancestors.
	 */
	public boolean isAncestor()
	{
		boolean reduced = false;
		// Array containing counts of all the movement actions
		int[] counts = new int[4];
		for (int i = sequenceOfActions.length() - 1; i >= 0; i--)
		{
			//Looking for movement sequences that return Jon to a previous position
			if (sequenceOfActions.charAt(i) == 'N')
			{
				counts[NORTHI]++;
			} else if (sequenceOfActions.charAt(i) == 'S') {
				counts[SOUTHI]++;
			} else if (sequenceOfActions.charAt(i) == 'E') {
				counts[EASTI]++;
			} else if (sequenceOfActions.charAt(i) == 'W') {
				counts[WESTI]++;
			} else {
				break;
			}
			// If the number of counts is equal in any two opposing directions, then Jon is
			// moving in circles.
			if ((counts[NORTHI] == counts[SOUTHI] && counts[EASTI] == 0 && counts[WESTI] == 0)
					|| (counts[EASTI] == counts[WESTI] && counts[NORTHI] == 0 && counts[SOUTHI] == 0)
					|| (counts[NORTHI] == counts[SOUTHI] && counts[EASTI] == counts[WESTI]))
			{
				reduced = true;
				break;
			}
		}
		return reduced;
	}

	/**
	 * This function takes in a data structure of some sort
	 * @param queue The current SearchQ.
	 * @return SearchQ The same SearchQ as the input, but with added nodes.
	 */
	public SearchQ expandNode(SearchQ queue)
	{
		// Don't proceed if the maximum depth was reached.
		if(this.MaxDepth <= this.sequenceOfActions.length())
		{
			return  queue;
		}
		// Don't expand if state is terminal (but not goal).
		if (this.isWhitewalker(this.row, this.col))
		{
			return queue;
		} else {
			// Check for all possible actions. If action is available, and the resultant
			// node is not an ancestor, then proceed to add the new node to the SearchQ.
			SaveWesterosNode s1 = this.north();
			if (!s1.equals(this) && !s1.isAncestor())
			{
				queue.add(s1);
			}
			SaveWesterosNode s2 = this.south();
			if (!s2.equals(this) && !s2.isAncestor())
			{
				queue.add(s2);
			}
			SaveWesterosNode s3 = this.east();
			if (!s3.equals(this) && !s3.isAncestor())
			{
				queue.add(s3);
			}
			SaveWesterosNode s4 = this.west();
			if (!s4.equals(this) && !s4.isAncestor())
			{
				queue.add(s4);
			}
			if (this.dragonglass == 0 && this.isDragonstone(this.row, this.col))
			{
				SaveWesterosNode s5 = this.pick();
				queue.add(s5);
			}
			if (((this.row < this.RMAX - 1 && this.isWhitewalker(this.row + 1, this.col))
					|| (this.row > 0 && this.isWhitewalker(this.row - 1, this.col))
					|| (this.col < this.CMAX - 1 && this.isWhitewalker(this.row, this.col + 1))
					|| (this.col > 0 && this.isWhitewalker(this.row, this.col - 1)))
					&& this.dragonglass > 0)
			{
				SaveWesterosNode s6 = this.kill();
				queue.add(s6);
			}
		}
		return queue;
	}

	/**
	 * The cost of getting to a dragonstone and picking some dragonglass (if non are being
	 * carried) and moving to then killing the closest whitewalker. 
	 * @return int Number approximating the cost of getting to the goal from that state.
	 */
	public int heuristic1()
	{
		if (this.isGoal())
		{
			return 0;
		}
		int possibleCost = 0;
		int whitewalkerDistance = CMAX + RMAX + 1;
		if (this.dragonglass == 0)
		{
			int dRow = 0;
			int dCol = 0;
			int dragonstoneDistance = 0;
			// Get Manhattan Distance to dragonstone
			for (int r = 0; r < this.map.length; r++)
			{
				for (int c = 0; c < this.map[r].length; c++)
				{
					if (this.isDragonstone(r, c))
					{
						dragonstoneDistance = Math.abs(r - row) + Math.abs(c - col);
						dRow = r;
						dCol = c;
						break;
					}
				}
			}
			// Multiply dragonstoneDistance by movement cost
			possibleCost += (dragonstoneDistance) * X_TO_E;
			// Add dragonstone cost
			possibleCost += D_PICK;
			// Get Manhattan distance to a whitewalker
			for (int r = 0; r < this.map.length; r++)
			{
				for (int c = 0; c < this.map[r].length; c++)
				{
					if (this.isWhitewalker(r, c))
					{
						int d = Math.abs(r - dRow) + Math.abs(c - dCol);
						if (whitewalkerDistance > d)
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
			for (int r = 0; r < this.map.length; r++)
			{
				for (int c = 0; c < this.map[r].length; c++)
				{
					if (this.isWhitewalker(r, c))
					{
						int d = Math.abs(r - row) + Math.abs(c - col);
						if (whitewalkerDistance > d)
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

	/**
	 * The cost of moving to then killing the closest whitewalker. 
	 * @return int Number approximating the cost of getting to the goal from that state.
	 */
	public int heuristic2()
	{
		if (this.isGoal())
		{
			return 0;
		}
		int whitewalkerDistance = CMAX+RMAX+1;
		// Get Manhattan distance to a whitewalker
		for (int r = 0; r < this.map.length; r++)
		{
			for (int c = 0; c < this.map[r].length; c++)
			{
				if (this.isWhitewalker(r, c))
				{
					int d = Math.abs(r - row) + Math.abs(c - col);
					if (whitewalkerDistance > d)
					{
						whitewalkerDistance = d;
					}
				}
			}
		}
		// Multiply whitewalkerDistance - 1 by movement cost
		int possibleCost = (whitewalkerDistance - 1) * X_TO_E;
		// Add kill cost
		possibleCost += KILL3;
		return possibleCost;
	}

	/**
	 * Function to check if a certain cell in the grid-world is empty.
	 * @param row The row of the cell being investigated
	 * @param col The column of the cell being investigated
	 * @return boolean If true, then the target cell is empty.
	 */
	public boolean isEmpty(int row, int col)
	{
		return map[row][col] == SaveWesteros.EMPTY;
	}

	/**
	 * Function to check if a certain cell in the grid-world contains a dragonstone.
	 * @param row The row of the cell being investigated
	 * @param col The column of the cell being investigated
	 * @return boolean If true, then the target cell contains a dragonstone.
	 */
	public boolean isDragonstone(int row, int col)
	{
		return map[row][col] == SaveWesteros.DRAGONSTONE;
	}

	/**
	 * Function to check if a certain cell in the grid-world contains a whitewalker.
	 * @param row The row of the cell being investigated
	 * @param col The column of the cell being investigated
	 * @return boolean If true, then the target cell contains a whitewalker.
	 */
	public boolean isWhitewalker(int row, int col)
	{
		return map[row][col] == SaveWesteros.WHITEWALKER;
	}

	/**
	 * Function to check if a certain cell in the grid-world is an obstacle.
	 * @param row The row of the cell being investigated
	 * @param col The column of the cell being investigated
	 * @return boolean If true, then the target cell is an obstacle.
	 */
	public boolean isObstacle(int row, int col)
	{
		return map[row][col] == SaveWesteros.OBSTACLE;
	}
}
