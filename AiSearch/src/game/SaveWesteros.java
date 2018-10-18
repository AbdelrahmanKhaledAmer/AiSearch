/**
 * The SaveWesteros class is a subclass of the GenericSearchProblem abstract class.
 * It represents a problem where an agent (Jon) must traverse a 2D grid-world. Each
 * cell of this grid-world is either a dragonstone, a whitewalker, an obstacle or
 * an otherwise empty cell. Jon must defeat all the whitewalkers using
 * dragonglasses which can be obtained from a dragonstone.
 */

package game;

import searchAI.GenericSearchProblem;

public class SaveWesteros extends GenericSearchProblem
{
	// Enumerators representing the different cell types
	public static final int EMPTY = 0;
	public static final int DRAGONSTONE = 1;
	public static final int WHITEWALKER = 2;
	public static final int OBSTACLE = 3;

	// The maximum number of dragonglass pieces allowed for Jon to carry at a time.
	public int numDragonglassPieces;

	/**
	 * Constructor for the class SaveWesteros.
	 * @param rows The height of the grid-world
	 * @param cols The width of the grid-world
	 */
	public SaveWesteros(int rows, int cols)
	{
		genGrid(rows, cols);
	}
	
	/**
	 * Constructor for the class SaveWesteros.
	 */
	public SaveWesteros()
	{
		genGrid();
	}
	
	/**
	 * This function generates the grid for the initial node of the SaveWesteros GenericSearchProblem.
	 * It randomly generates a number of rows and columns for the grid, and passes that number to
	 * genGrid(rows, columns) which then generates the whitewalkers and dragonstone randomly.
	 */
	public void genGrid()
	{
		int rows = (int) (4 + Math.random() * 3);
		int cols = (int) (4 + Math.random() * 3);
		genGrid(rows, cols);
	}

	/**
	 * This function generates the grid for the initial node of the SaveWesteros GenericSearchProblem.
	 * It randomly generates a number of white walkers, obstacles and the maximum carrying capacity
	 * of dragonglass pieces that John can carry at one time. It then populates the grid-world with
	 * these at random positions.
	 * @param rows The height of the grid-world
	 * @param cols The width of the grid-world
	 */
	public void genGrid(int rows, int cols)
	{
		// Initialise grid-world map
		int[][] grid = new int[rows][cols];
		// Generate a number of whitewalkers and obstacles to some random number in between 1 and the
		// longest dimension of the grid-world
		int numWhiteWalkers = (int) (1 + Math.random() * (Math.max(rows, cols)));
		int numObstacles =  (int) (1 + Math.random() * (Math.max(rows, cols)));
		// Generate the number of maximum dragonglass carrying capacity to some random number in
		// between 1 and half the number of white walkers generated (minimum 1)
		numDragonglassPieces = (int) (1 + Math.random() * (double)numWhiteWalkers / 1.5);

		// Make sure all cells of the grid-world are empty at first
		for (int i = 0; i < grid.length; i++)
		{
			for (int j = 0; j < grid[i].length; j++) 
			{
				grid[i][j] = EMPTY;
			}
		}
		
		// Add all the obstacles to the grid-world to random locations
		int k = 0;
		while (k < numObstacles)
		{
			int newC = (int) (Math.random() * grid[0].length);
			int newR = (int) (Math.random() * grid.length);
			if (newC != grid[0].length - 1 && newR != grid.length - 1 && grid[newR][newC] == EMPTY)
			{
				grid[newR][newC] = OBSTACLE;
				k++;
			}
		}

		// Add all the whitewalkers to the grid-world to random locations
		k = 0;
		while (k < numWhiteWalkers)
		{
			int newC = (int) (Math.random() * grid[0].length);
			int newR = (int) (Math.random() * grid.length);
			if (newC != grid[0].length - 1 && newR != grid.length - 1 && grid[newR][newC] == EMPTY)
			{
				grid[newR][newC] = WHITEWALKER;
				k++;
			}
		}
		
		// Keep looking for a random location to add the dragonstone until one is found
		boolean noStone = true;
		do
		{
			int newC = (int) (Math.random() * grid[0].length);
			int newR = (int) (Math.random() * grid.length);
			if (newC != grid[0].length - 1 && newR != grid.length - 1 && grid[newR][newC] == EMPTY)
			{
				grid[newR][newC] = DRAGONSTONE;
				noStone = false;
			}
		} while (noStone);
		
		// Set the SaveWesteros initial Node to a node with this grid-world and maximum dragonglass
		// carrying capacity
		initialNode = new SaveWesterosNode(grid, numDragonglassPieces);
	}

	/**
	 * Prints the initial node (state) of the problem.
	 */
	public void print()
	{
		this.initialNode.print();
	}
}