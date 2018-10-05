package searchAI;

public abstract class GenericSearchProblem {
    public State initialState;
    public abstract boolean isGoal(State s);
}
