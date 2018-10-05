package searchAI;

import java.util.*;

public class SearchQ {
    Stack<Node> s;
    Queue<Node> q;
    PriorityQueue<Node> pq;
    int which =-1; // 1, stack 2 queue, 3 pq
    private final int DFS = 1;  // valid for ID and DFS
    private final int BFS =2;
    private final int UCS = 3;
    private final int GREEDY = 4;
    private final int ASTAR = 5;


    public SearchQ(int n){
        which = n;

        Comparator<Node> OrderByCost =  new Comparator<Node>() {
            public int compare(Node s1, Node e2) {
                return s1.cost - e2.cost;
            }
        };

        Comparator<Node> OrderByHeuristic =  new Comparator<Node>() {
            public int compare(Node s1, Node e2) {
                return s1.heuristic1() - e2.heuristic1();
            }
        };

        Comparator<Node> AStarOrder =  new Comparator<Node>() {
            public int compare(Node s1, Node e2) {
                return s1.heuristic1()+s1.cost - e2.heuristic1()-e2.cost;
            }
        };


        switch (which) {
            case DFS:
                s = new Stack<Node>(); break;
            case BFS:
                q = new LinkedList<Node>(); break;
            case UCS:
                pq = new PriorityQueue<Node>(OrderByCost); break;
            case GREEDY:
                pq = new PriorityQueue<Node>(OrderByHeuristic); break;
            case ASTAR:
                pq = new PriorityQueue<Node>(AStarOrder); break;
        }

    }

    public void add(Node e){
        switch (which) {
            case DFS:
                s.add(e); break;
            case BFS:
                q.add(e); break;
            case UCS:
            case GREEDY:
            case ASTAR:
                pq.add(e); break;
        }

    }
    public Node remove(){
        switch (which) {
            case DFS:
                return s.pop();
            case BFS:
                return q.remove();
            case UCS:
            case GREEDY:
            case ASTAR:
                return pq.remove();
        }
        return null;
    }
    public boolean isEmpty(){
        switch (which) {
            case DFS:
                return s.isEmpty();
            case BFS:
                return q.isEmpty();
            case UCS:
            case GREEDY:
            case ASTAR:
                return pq.isEmpty();

        }
        return false;
    }

    public int size(){
        switch (which) {
            case DFS:
                return s.size();
            case BFS:
                return q.size();
            case UCS:
            case GREEDY:
            case ASTAR:
                return pq.size();

        }
        return -1;
    }


}