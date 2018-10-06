package searchAI;

import game.SaveWesterosState;

public class Node {
    public SaveWesterosState state;
    public Node(SaveWesterosState s){
        state = new SaveWesterosState(s);
    }

}
