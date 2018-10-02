package searchAI;

import game.Grid;

import java.util.LinkedList;
import java.util.Queue;

public class Main
{
	// MAKE A GENERAL SEARCH AS IN THE LECTURE
	// MAKE A NODE DATA STRUCTURE WITH STATE, ACTION(SEQUENCE) AND COST(TOTAL)
	// MAKE A FUNCTION THAT EXPANDS THE NODE
	
	public static Queue<State> q = new LinkedList<State>();
	public static int numDragonglassPieces = (int)(1 + Math.random() * 2);
	
	public static void BFS()
	{	
		Grid g = new Grid(4, 4);
		g.print();
		
		State s = new State(g);
		q.add(s);
		do
		{
			if(q.isEmpty())
			{
				System.out.println("No solution");
				break;
			}
			
			State current = q.remove();
			if(current.isGoal())
			{
				System.out.println("Goal found!");
				break;
			}
			addNextStates(current);
		}while(true);
	}
	
	public static void addNextStates(State s)
	{
		if(s.grid.isWhitewalker(s.row, s.col))
		{
			return;
		}
		else
		{
			State s1 = s.north();
			if(!s1.equals(s))
			{
				q.add(s1);
			}
			State s2 = s.south();
			if(!s2.equals(s))
			{
				q.add(s2);
			}
			State s3 = s.east();
			if(!s3.equals(s))
			{
				q.add(s3);
			}
			State s4 = s.west();
			if(!s4.equals(s))
			{
				q.add(s4);
			}
			if(s.grid.isDragonstone(s.row, s.col))
			{
				State s5 = s.pick(numDragonglassPieces);
				q.add(s5);
			}
			if(((s.row < s.RMAX - 1 && s.grid.isWhitewalker(s.row + 1, s.col))
					|| (s.row > 0 && s.grid.isWhitewalker(s.row - 1, s.col))
					|| (s.col < s.CMAX - 1 && s.grid.isWhitewalker(s.row, s.col + 1))
					|| (s.col > 0 && s.grid.isWhitewalker(s.row, s.col - 1)))
					&& s.dragonglass > 0)
			{
				State s6 = s.kill();
				q.add(s6);
			}
		}
	}
	
	public static void main(String[] args)
	{
		int count  = 0;
		int max = 50;
		for(int i = 0; i < max; i++)
		{
			System.out.println("Trial#" + i);
			try
			{
				q.clear();
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
