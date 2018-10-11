package searchAI;

import java.util.HashSet;

public abstract class Node {
    public int cost,MaxDepth = 1000000;
    public Node prev; // Not used in our implementation of SavewestrosNode because we are using the sequence of actions
    public String sequenceOfActions;

    public abstract boolean isGoal();

    public abstract int heuristic1();

    public abstract int heuristic2();

    public abstract SearchQ expandNode(SearchQ q);
    public void setMaxDepth(int n){
        MaxDepth = n;
    }
    public abstract void print();
}