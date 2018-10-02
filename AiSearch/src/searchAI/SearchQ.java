package searchAI;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class SearchQ {
    Stack<Node> s;
    Queue<Node> q;
    PriorityQueue<Node> pq;
    int which =-1; // 1, stack 2 queue, 3 pq
    private final int STACK = 1;
    private final int QUEUE =2;
    private final int PRIORITYQUEUE = 3;
    public SearchQ(int n){
        which = n;
        switch (which) {
            case STACK:
                s = new Stack<Node>(); break;
            case QUEUE:
                q = new LinkedList<Node>(); break;
            case PRIORITYQUEUE:
                pq = new PriorityQueue<Node>(); break;
        }

    }

    public void add(Node e){
        switch (which) {
            case STACK:
                s.add(e); break;
            case QUEUE:
                q.add(e); break;
            case PRIORITYQUEUE:
                pq.add(e); break;
        }

    }
    public Node remove(){
        switch (which) {
            case STACK:
                return s.pop();
            case QUEUE:
                return q.remove();
            case PRIORITYQUEUE:
                return pq.remove();
        }
        return null;
    }
    public boolean isEmpty(){
        switch (which) {
            case STACK:
                return s.isEmpty();
            case QUEUE:
                return q.isEmpty();
            case PRIORITYQUEUE:
                return pq.isEmpty();

        }
        return false;
    }

    public int size(){
        switch (which) {
            case STACK:
                return s.size();
            case QUEUE:
                return q.size();
            case PRIORITYQUEUE:
                return pq.size();

        }
        return -1;
    }


}
