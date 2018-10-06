package searchAI;

import game.SaveWesteros;
import game.SaveWesterosState;

public class Main {
    static int numNodes;
    public static void generic_search(GenericSearchProblem problem, int SearchFunction) { // SearchFunction number represents the search function to use
        //reset num of nodes
        numNodes =0;
        SaveWesterosState s = new SaveWesterosState((SaveWesteros) problem); // initial state
        SearchQ q = new SearchQ(SearchFunction);
        q.add(new Node(s));
        while (true) {
            if (q.isEmpty()) {
                System.out.println("No solution");
                break;
            }

            Node current = q.remove();
            numNodes+=1;
            if(numNodes%20000 ==0)
            {
                current.state.print();
                System.out.println(numNodes+" "+s.numDragonglassPieces+" "+s.dragonglass);
            }
//            System.out.println(current.state.sequenceOfActions);

            if (current.state.isGoal()) {
                System.out.println("Goal found!:   " +numNodes+", numDragonglass: "+s.numDragonglassPieces);

                System.out.println(current.state.sequenceOfActions);
                break;
            }
            q = current.state.expandNode(q);
        }
    }


    public static void DFS(GenericSearchProblem problem) {
        generic_search(problem, 1);
    }

    public static void BFS(GenericSearchProblem problem) {
        generic_search(problem, 2);
    }

    public static void UCS(GenericSearchProblem problem) {
        generic_search(problem, 3);
    }

    public static void Greedy(GenericSearchProblem problem) { generic_search(problem, 4);}

    public static void AStar(GenericSearchProblem problem) {
        generic_search(problem, 5);
    }


    public static void main(String[] args) {
        int count = 0;
        int max = 10;

        for (int i = 0; i < max; i++) {
            System.out.println("Trial#" + i);
            try {
                SaveWesteros g = new SaveWesteros(4, 4);
                g.print();
                System.out.print("DFS: ");
                DFS(g);
                System.out.print("UCS: ");
                UCS(g);
                System.out.print("Astar: ");
                AStar(g);
                System.out.print("Greedy: ");
                Greedy(g);

            } catch (OutOfMemoryError e) {
                count--;
            }
            count++;
        }
        System.out.println((double) (count * 100) / (double) max + "% success");
    }
}
