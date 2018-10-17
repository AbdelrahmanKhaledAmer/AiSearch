/**
 * The Search class is a class filled with static functions that will be used by
 * the main module. Each function is a different search algorithm.
 */

package searchAI;

import game.SaveWesteros;
import game.SaveWesterosNode;

import java.util.Arrays;

public class Search
{
	// Variable representing number of nodes expanded during search.
	static int numNodes;
	
	/**
	 * Generic Search that finds the solution to a problem (if one exists).
	 * Depending on what kind of data structure it uses, it can be multiple different functions.
	 * @param problem The problem to be solved.
	 * @param SearchFunction Number of the desired function to use for queueing.
	 * @return Node The goal node for the problem. Null if there is no solution.
	 */
	public static Node generic_search(GenericSearchProblem problem, int SearchFunction)
	{
		// reset number of nodes
		numNodes = 0;
		// Get the initial node (initial state for the problem)
		Node s = problem.initialNode;
		// Initialise the desired data structure to fit the desired search function.
		SearchQ q = new SearchQ(SearchFunction);
		// Start by adding the initial node to the SearchQ.
		q.add(s);
		while (true)
		{
			// If the SearchQ i empty, then no further expansions can be made, hence
			// there is no solution to the problem.
			if (q.isEmpty())
			{
				System.out.println("No solution "+numNodes);
				return null;
			}

			// Get the node at the head of the SearchQ.
			Node current = q.remove();
			numNodes += 1;

			if (numNodes % 1000000 == 0)
			{
				System.out.println("Currently at " + numNodes);
			}

			// If the node is a goal node, then return that Node as the solution.
			if (current.isGoal())
			{
				System.out.println("Goal found! "+numNodes);
				System.out.println(Arrays.toString(current.sequenceOfActions.toCharArray()));
				return current;
			}
			
			// Expand the current node and add its children to the SearchQ.
			q = current.expandNode(q);
		}
	}

	/**
	 * Performs depth-first search on the problem.
	 * @param problem The problem to be solved.
	 * @return Node The goal node for the problem. Null if there is no solution.
	 */
	public static Node DFS(GenericSearchProblem problem)
	{
		return generic_search(problem, 1);
	}

	/**
	 * Performs iterative-depth search on the problem.
	 * @param problem The problem to be solved.
	 * @return Node The goal node for the problem. Null if there is no solution.
	 */
	public static Node IDS(GenericSearchProblem problem)
	{
		for (int i = 0; i > -1; ++i)
		{
			problem.initialNode.setMaxDepth(i);
			System.out.print(" At depth " + i + " ");
			Node n = DFS(problem);
			if (n != null) //generic search found a goal
			{
				return n;
			}
		}
		return null;
	}

	/**
	 * Performs depth-limited search on the problem.
	 * @param problem The problem to be solved.
	 * @param maxDepth The maximum depth allowed.
	 * @return Node The goal node for the problem. Null if there is no solution found until depth i.
	 */
	public static Node IDS(GenericSearchProblem problem, int maxDepth)
	{
		for (int i = 0; i < maxDepth; ++i)
		{
			problem.initialNode.setMaxDepth(i);
			System.out.print(" At depth "+i+" ");
			Node n = DFS(problem);
			if (n != null) //generic search found a goal
			{
				return n;
			}
		}
		return null;
	}

	/**
	 * Performs breadth-first search on the problem.
	 * @param problem The problem to be solved.
	 * @return Node The goal node for the problem. Null if there is no solution.
	 */
	public static Node BFS(GenericSearchProblem problem)
	{
		return generic_search(problem, 2);
	}

	/**
	 * Performs uniform cost search on the problem.
	 * @param problem The problem to be solved.
	 * @return Node The goal node for the problem. Null if there is no solution.
	 */
	public static Node UCS(GenericSearchProblem problem)
	{
		return generic_search(problem, 3);
	}

	/**
	 * Performs greedy search on the problem using heuristic1.
	 * @param problem The problem to be solved.
	 * @return Node The goal node for the problem. Null if there is no solution.
	 */
	public static Node Greedy(GenericSearchProblem problem)
	{
		return generic_search(problem, 4);
	}

	/**
	 * Performs A* search on the problem using heuristic1.
	 * @param problem The problem to be solved.
	 * @return Node The goal node for the problem. Null if there is no solution.
	 */
	public static Node AStar(GenericSearchProblem problem)
	{
		return generic_search(problem, 5);
	}

	/**
	 * Performs greedy search on the problem using heuristic 2.
	 * @param problem The problem to be solved.
	 * @return Node The goal node for the problem. Null if there is no solution.
	 */
	public static Node Greedy2(GenericSearchProblem problem)
	{
		return generic_search(problem, 6);
	}

	/**
	 * Performs A* search on the problem using heuristic 2.
	 * @param problem The problem to be solved.
	 * @return Node The goal node for the problem. Null if there is no solution.
	 */
	public static Node AStar2(GenericSearchProblem problem)
	{
		return generic_search(problem, 7);
	}

	public static void testGrid()
	{
		SaveWesteros g = new SaveWesteros(5, 5);
		for (int i = 0; i < ((SaveWesterosNode) g.initialNode).map.length; i++)
		{
			for (int j = 0; j < ((SaveWesterosNode) g.initialNode).map[0].length; j++)
			{
				((SaveWesterosNode) g.initialNode).map[i][j] = SaveWesteros.EMPTY;
			}
		}
		((SaveWesterosNode) g.initialNode).map[2][0] = SaveWesteros.OBSTACLE;
		((SaveWesterosNode) g.initialNode).map[3][0] = SaveWesteros.OBSTACLE;
		((SaveWesterosNode) g.initialNode).map[4][0] = SaveWesteros.DRAGONSTONE;
		((SaveWesterosNode) g.initialNode).map[2][1] = SaveWesteros.WHITEWALKER;
		((SaveWesterosNode) g.initialNode).map[3][1] = SaveWesteros.WHITEWALKER;
		((SaveWesterosNode) g.initialNode).map[3][2] = SaveWesteros.WHITEWALKER;
		((SaveWesterosNode) g.initialNode).map[2][3] = SaveWesteros.WHITEWALKER;
		((SaveWesterosNode) g.initialNode).map[3][4] = SaveWesteros.WHITEWALKER;
		((SaveWesterosNode) g.initialNode).map[1][2] = SaveWesteros.WHITEWALKER;
		((SaveWesterosNode) g.initialNode).map[1][1] = SaveWesteros.OBSTACLE;
		((SaveWesterosNode) g.initialNode).map[1][4] = SaveWesteros.OBSTACLE;

		g.print();
		System.out.print("DFS: ");
		DFS(g);
		System.out.print("IDS: ");
		IDS(g);
		System.out.print("BFS: ");
		BFS(g);
		System.out.print("UCS: ");
		UCS(g);
		System.out.print("Astar: ");
		AStar(g);
		AStar2(g);
		System.out.print("Greedy: ");
		Greedy(g);
		Greedy2(g);
	}
	public static void main (String [] args){
		testGrid();
	}
}