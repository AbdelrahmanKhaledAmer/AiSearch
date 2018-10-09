package searchAI;

import game.SaveWesteros;
import game.SaveWesterosState;

import java.util.Arrays;

public class Search {
    static int numNodes;

    public static Node generic_search(GenericSearchProblem problem, int SearchFunction) { // SearchFunction number represents the search function to use
        //reset num of nodes
        numNodes = 0;
        SaveWesterosState s = (SaveWesterosState) ((SaveWesteros) problem).initialState; // initial state
        SearchQ q = new SearchQ(SearchFunction);
        q.add(new Node(s));
        while (true) {
            if (q.isEmpty()) {
                System.out.println("No solution");
                return null;
            }

            Node current = q.remove();
            numNodes += 1;

            if (numNodes % 1000000 == 0) {
                System.out.println("Currently at " + numNodes);
            }

            if (current.state.isGoal()) {
                System.out.println("Goal found!:   " + numNodes + ", numDragonglass: " + s.numDragonglassPieces);
                System.out.println(Arrays.toString(current.state.sequenceOfActions.toCharArray()));
                return current;
            }
            q = current.state.expandNode(q);
        }
    }

    public static Node DFS(GenericSearchProblem problem) {
        return generic_search(problem, 1);
    }

    public static Node BFS(GenericSearchProblem problem) {
        return generic_search(problem, 2);
    }

    public static Node UCS(GenericSearchProblem problem) {
        return generic_search(problem, 3);
    }

    public static Node Greedy(GenericSearchProblem problem) {
        return generic_search(problem, 4);
    }

    public static Node AStar(GenericSearchProblem problem) {
        return generic_search(problem, 5);
    }

    public static Node Greedy2(GenericSearchProblem problem) {
        return generic_search(problem, 6);
    }

    public static Node AStar2(GenericSearchProblem problem) {
        return generic_search(problem, 7);
    }

    public static void testGrid() {
        SaveWesteros g = new SaveWesteros(4, 4);
        for (int i = 0; i < ((SaveWesterosState) g.initialState).map.length; i++) {
            for (int j = 0; j < ((SaveWesterosState) g.initialState).map[0].length; j++) {
                ((SaveWesterosState) g.initialState).map[i][j] = SaveWesteros.EMPTY;
            }
        }

        ((SaveWesterosState) g.initialState).map[0][0] = SaveWesteros.DRAGONSTONE;
        ((SaveWesterosState) g.initialState).map[1][0] = SaveWesteros.WHITEWALKER;
        ((SaveWesterosState) g.initialState).map[2][0] = SaveWesteros.WHITEWALKER;
        ((SaveWesterosState) g.initialState).map[1][1] = SaveWesteros.WHITEWALKER;
        ((SaveWesterosState) g.initialState).map[2][1] = SaveWesteros.WHITEWALKER;
        ((SaveWesterosState) g.initialState).map[1][2] = SaveWesteros.WHITEWALKER;
        ((SaveWesterosState) g.initialState).map[2][2] = SaveWesteros.WHITEWALKER;

        g.print();

        System.out.print("Astar: ");
        AStar(g);
        System.out.print("Greedy: ");
        Greedy(g);
    }

    public static void main(String[] args) {
        int count = 0;
        int max = 50;

        for (int i = 0; i < max; i++) {
            System.out.println("Trial#" + i);
            try {
                SaveWesteros g = new SaveWesteros(4, 4);
                g.print();
                System.out.print("DFS: ");
                DFS(g);
                System.out.print("BFS: ");
                BFS(g);
                System.out.print("UCS: ");
                UCS(g);
                System.out.print("Astar: ");
                AStar(g);
//				System.out.print("ASTAR 2: ");
//				AStar2(g);
                System.out.print("Greedy: ");
                Greedy(g);
//				System.out.print("Greedy 2: ");
//				Greedy2(g);
            } catch (OutOfMemoryError e) {
                System.out.println("memory out of bound ");
                count--;
            }
            count++;
        }

        System.out.println((double) (count * 100) / (double) max + "% success");
    }
}