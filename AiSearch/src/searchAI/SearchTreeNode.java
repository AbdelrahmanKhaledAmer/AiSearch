package searchAI;

import java.util.ArrayList;

public class SearchTreeNode
{
	// Maximum size of sequence of actions
	private static final int MAX_SIZE = 1000000;
	
	State state;
	public ArrayList<Integer> sequenceOfActions = new ArrayList<Integer>(MAX_SIZE);
	public int cost = 0;
	public int depth = 0;
}