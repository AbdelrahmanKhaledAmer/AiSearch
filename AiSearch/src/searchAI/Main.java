package searchAI;

import game.Grid;

public class Main
{
	public static void generic_search(GenericSearchProblem problem, int SearchFunction )
	{ // SearchFunction number represents the search function to use
		Node s = new Node((Grid)problem); // initial state
		SearchQ q = new SearchQ(SearchFunction);
		q.add(s);
		while(true)
		{
			if(q.isEmpty())
			{
				System.out.println("No solution");
				break;
			}

			Node current = q.remove();
			if(current.isGoal())
			{
				System.out.println("Goal found!");
				System.out.println(q.size());
				break;
			}
			q = current.expandNode(q);
		}
	}


	public static void DFS(GenericSearchProblem problem)
	{
		//TODO test with a special grid
		generic_search(problem,1);
	}

	public static void BFS(GenericSearchProblem problem)
	{
		generic_search(problem,2);
	}

	public static void Greedy(GenericSearchProblem problem)
	{
		//TODO put proper cost and heuristic func
		generic_search(problem,3);
	}

	public void AStar(GenericSearchProblem problem)
	{
		//TODO put proper cost and heuristic func
		generic_search(problem,4);
	}


	
	public static void main(String[] args)
	{
		int count  = 0;
		int max = 10;

		for(int i = 0; i < max; i++)
		{
			System.out.println("Trial#" + i);
			try
			{
				Grid g = new Grid(4, 4);
				g.print();
				BFS(g);
			}
			catch (OutOfMemoryError e)
			{
				count--;
			}
			count++;
		}
		System.out.println((double)(count*100)/(double)max + "% success");
	}
}
