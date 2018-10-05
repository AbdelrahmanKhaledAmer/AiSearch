package searchAI;

import java.util.*;

public class SearchQ {
    Stack<WesterosNode> s;
    Queue<WesterosNode> q;
    PriorityQueue<WesterosNode> pq;
    int which =-1; // 1, stack 2 queue, 3 pq
    private final int DFS = 1;  // valid for ID and DFS
    private final int BFS =2;
    private final int UCS = 3;
    private final int GREEDY = 4;
    private final int ASTAR = 5;


    public SearchQ(int n){
        which = n;

        Comparator<WesterosNode> OrderByCost =  new Comparator<WesterosNode>() {
            public int compare(WesterosNode s1, WesterosNode e2) {
                return s1.cost - e2.cost;
            }
        };

        Comparator<WesterosNode> OrderByHeuristic =  new Comparator<WesterosNode>() {
            public int compare(WesterosNode s1, WesterosNode e2) {
                return s1.heuristic1() - e2.heuristic1();
            }
        };

        Comparator<WesterosNode> AStarOrder =  new Comparator<WesterosNode>() {
            public int compare(WesterosNode s1, WesterosNode e2) {
                return s1.heuristic1()+s1.cost - e2.heuristic1()-e2.cost;
            }
        };


        switch (which) {
            case DFS:
                s = new Stack<WesterosNode>(); break;
            case BFS:
                q = new LinkedList<WesterosNode>(); break;
            case UCS:
                pq = new PriorityQueue<WesterosNode>(OrderByCost); break;
            case GREEDY:
                pq = new PriorityQueue<WesterosNode>(OrderByHeuristic); break;
            case ASTAR:
                pq = new PriorityQueue<WesterosNode>(AStarOrder); break;
        }

    }

    public void add(WesterosNode e){
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
    public WesterosNode remove(){
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
