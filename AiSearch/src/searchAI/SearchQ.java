/**
 * The SearchQ class is a data structure that represents which queueing function
 * is being used by the search algorithm. It uses a stack for depth-first search,
 * a queue for breadth-first search and a priority queue for all the others.
 */

package searchAI;

import java.util.*;

public class SearchQ
{
	Stack<Node> s;
	Queue<Node> q;
	PriorityQueue<Node> pq;
	int which = -1; // 1, stack 2 queue, 3 pq
	private final int DFS = 1;  // valid for ID and DFS
	private final int BFS = 2;
	private final int UCS = 3;
	private final int GREEDY = 4;
	private final int ASTAR = 5;
	private final int GREEDY2 = 6;
	private final int ASTAR2 = 7;

	/**
	 * Constructor for the SearchQ class
	 * @param n The number signifying the search function to be used.
	 */
	public SearchQ(int n)
	{
		which = n;
		
		// Comparators for the priority queue depending on which n is used.
		Comparator<Node> OrderByCost = new Comparator<Node>()
		{
			public int compare(Node s1, Node e2)
			{
				return s1.cost - e2.cost;
			}
		};
		
		Comparator<Node> OrderByHeuristic = new Comparator<Node>()
		{
			public int compare(Node s1, Node e2)
			{
				return s1.heuristic1() - e2.heuristic1();
			}
		};
		
		Comparator<Node> AStarOrder = new Comparator<Node>()
		{
			public int compare(Node s1, Node e2)
			{
				return s1.heuristic1() + s1.cost - e2.heuristic1() - e2.cost;
			}
		};
		
		Comparator<Node> OrderByHeuristic2 = new Comparator<Node>()
		{
			public int compare(Node s1, Node e2)
			{
				return s1.heuristic2() - e2.heuristic2();
			}
		};
		
		Comparator<Node> AStarOrder2 = new Comparator<Node>()
		{
			public int compare(Node s1, Node e2)
			{
				return s1.heuristic2() + s1.cost - e2.heuristic2() - e2.cost;
			}
		};
		
		// Initialise the relevant data structure (with the relevant comparator if needed)
		switch (which)
		{
		case DFS:
			s = new Stack<Node>();
			break;
		case BFS:
			q = new LinkedList<Node>();
			break;
		case UCS:
			pq = new PriorityQueue<Node>(OrderByCost);
			break;
		case GREEDY:
			pq = new PriorityQueue<Node>(OrderByHeuristic);
			break;
		case ASTAR:
			pq = new PriorityQueue<Node>(AStarOrder);
			break;
		case GREEDY2:
			pq = new PriorityQueue<Node>(OrderByHeuristic2);
			break;
		case ASTAR2:
			pq = new PriorityQueue<Node>(AStarOrder2);
			break;
		}
	}

	/**
	 * Adds a node to the relevant data structure in SearchQ.
	 * @param item Node to add to the list.
	 */
	public void add(Node item)
	{
		switch (which)
		{
		case DFS:
			s.add(item);
			break;
		case BFS:
			q.add(item);
			break;
		case UCS:
		case GREEDY:
		case ASTAR:
		case GREEDY2:
		case ASTAR2:
			pq.add(item);
			break;
		}
	}

	/**
	 * Removes a node from the relevant data structure in SearchQ.
	 * @return Node The node removed from the SearchQ.
	 */
	public Node remove()
	{
		switch (which)
		{
		case DFS:
			return s.pop();
		case BFS:
			return q.remove();
		case UCS:
		case GREEDY:
		case ASTAR:
		case GREEDY2:
		case ASTAR2:
			return pq.remove();
		}
		return null;
	}

	/**
	 * Checks if the SearchQ's relevant data structure is empty.
	 * @return boolean If true, the the SearchQ is empty.
	 */
	public boolean isEmpty()
	{
		switch (which)
		{
		case DFS:
			return s.isEmpty();
		case BFS:
			return q.isEmpty();
		case UCS:
		case GREEDY:
		case ASTAR:
		case GREEDY2:
		case ASTAR2:
			return pq.isEmpty();
		}
		return false;
	}

	/**
	 * Find the number of nodes in the relevant data structure in the SearchQ.
	 * @return int number of nodes in SearchQ.
	 */
	public int size()
	{
		switch (which)
		{
		case DFS:
			return s.size();
		case BFS:
			return q.size();
		case UCS:
		case GREEDY:
		case ASTAR:
		case GREEDY2:
		case ASTAR2:
			return pq.size();
		}
		return -1;
	}
}
