package searchAI;

import java.util.ArrayList;

public abstract class State {
    public int cost;
    public ArrayList<Integer> sequenceOfActions;
    public abstract boolean isGoal();
    public abstract int heuristic1();
    public abstract SearchQ expandNode(SearchQ q);

}
