package ai;

import game.*;

import java.util.LinkedList;
import java.util.Queue;

public class Main
{	
	public static Queue<State> q = new LinkedList<State>();
	public static int numDragonglassPieces = (int)(1 + Math.random() * 2);
	
	public static void BFS()
	{	
		Grid g = new Grid(5, 5);
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
		if(s.grid.map[s.row][s.col].type == CellType.WHITEWALKER)
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
			if(s.grid.map[s.row][s.col].type == CellType.DRAGONSTONE)
			{
				State s5 = s.pick(numDragonglassPieces);
				q.add(s5);
			}
			if((s.row < s.RMAX - 1 && s.grid.map[s.row + 1][s.col].type == CellType.WHITEWALKER)
			|| (s.row > 0 && s.grid.map[s.row - 1][s.col].type == CellType.WHITEWALKER)
			|| (s.col < s.CMAX - 1 && s.grid.map[s.row][s.col + 1].type == CellType.WHITEWALKER)
			|| (s.col > 0 && s.grid.map[s.row][s.col - 1].type == CellType.WHITEWALKER))
			{
//				System.out.println("kill");
				State s6 = s.kill();
				q.add(s6);
//				s6.print();
//				System.out.println("done");
			}
		}
	}
	
	public static void main(String[] args)
	{
		BFS();
	}
}
