package searchAI;

public abstract class State {
    public int cost;
    public String sequenceOfActions;
    public abstract boolean isGoal();
    public abstract int heuristic1();
    public abstract SearchQ expandNode(SearchQ q);

}
