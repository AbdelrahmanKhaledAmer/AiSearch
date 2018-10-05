package game;

import searchAI.GenericSearchProblem;
import searchAI.State;

public class SaveWesteros extends GenericSearchProblem
{	
	public int numDragonglassPieces;
	public int numWhitewalkers;
	
	public SaveWesteros()
	{
		
	}
	
    public SaveWesteros(int rows, int cols)
    {
    	genGrid(rows, cols);
    }

    public SaveWesteros(SaveWesteros other)
    {   // another constructor for copying the saveWesteros
    	// remember the old num of dragon glass pieces
        numDragonglassPieces = other.numDragonglassPieces;
        numWhitewalkers = other.numWhitewalkers;
    }

    public void genGrid(int rows, int cols)
    {
    	numWhitewalkers = (int) (1 + Math.random() * (Math.min(rows, cols) - 1));
        numDragonglassPieces = (int) (1 + Math.random() * numWhitewalkers);
        
        initialState = new SaveWesterosState(this, rows, cols);
    }

    public void print()
    {
    	initialState.print();
    }

    public boolean isGoal(State state)
    {
    	SaveWesterosState s = (SaveWesterosState)state;
    	for (int i = 0; i < s.map.length; i++)
		{
            for (int j = 0; j < s.map[i].length; j++)
            {
                if (s.isWhitewalker(i, j))
                {
                    return false;
                }
            }
        }
		return true;
    }
}