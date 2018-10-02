package searchAI;

import game.Grid;

import java.util.LinkedList;
import java.util.Queue;

public class Main
{
	// MAKE A GENERAL SEARCH AS IN THE LECTURE
	// MAKE A NODE DATA STRUCTURE WITH STATE, ACTION(SEQUENCE) AND COST(TOTAL)
	// MAKE A FUNCTION THAT EXPANDS THE NODE
	
	public static SearchQ q;
	public static int numDragonglassPieces = (int)(1 + Math.random() * 2);

	public static void generic_search(GenericSearchProblem problem, int SearchFunction )
	{ // SearchFunction number represents the search function to use
		Node s = new Node((Grid)problem); // initial state
		q = new SearchQ(SearchFunction);
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
			addNextStates(current);
		}
	}


	public static void BFS()
	{	
		Grid g = new Grid(4, 4);
		g.print();

		generic_search(g,2);
	}


	public static void addNextStates(Node s)
	{
		if(s.grid.isWhitewalker(s.row, s.col))
		{
			return;
		}
		else
		{
			Node s1 = s.north();
			if(!s1.equals(s) && !s1.isAncestor())
			{
				q.add(s1);
			}
			Node s2 = s.south();
			if(!s2.equals(s) && !s1.isAncestor())
			{
				q.add(s2);
			}
			Node s3 = s.east();
			if(!s3.equals(s) && !s1.isAncestor())
			{
				q.add(s3);
			}
			Node s4 = s.west();
			if(!s4.equals(s) && !s1.isAncestor())
			{
				q.add(s4);
			}
			if(s.grid.isDragonstone(s.row, s.col))
			{
				Node s5 = s.pick(numDragonglassPieces);
				q.add(s5);
			}
			if(((s.row < s.RMAX - 1 && s.grid.isWhitewalker(s.row + 1, s.col))
					|| (s.row > 0 && s.grid.isWhitewalker(s.row - 1, s.col))
					|| (s.col < s.CMAX - 1 && s.grid.isWhitewalker(s.row, s.col + 1))
					|| (s.col > 0 && s.grid.isWhitewalker(s.row, s.col - 1)))
					&& s.dragonglass > 0)
			{
				Node s6 = s.kill();
				q.add(s6);
			}
		}
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
				BFS();
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
