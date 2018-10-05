package searchAI;

import game.SaveWesteros;

public class Main
{
    static int numNodes;

    public static void generic_search(GenericSearchProblem problem, int SearchFunction)
    {   // SearchFunction number represents the search function to use
    	// reset num of nodes
        numNodes = 0;
        WesterosNode s = new WesterosNode(problem.initialState); // initial state
        SearchQ q = new SearchQ(SearchFunction);
        q.add(s);
        while (true)
        {
            if (q.isEmpty())
            {
                System.out.println("No solution");
                break;
            }

            WesterosNode current = q.remove();
            current.state.print();
            numNodes+=1;
            if(numNodes%10000 ==0)
                System.out.println(numNodes);
            if (problem.isGoal(current.state))
            {
                System.out.println("Goal found!:   " +numNodes);

                System.out.println(current.sequenceOfActions);
                break;
            }
            q = current.expandNode(q);
        }
    }

    public static void DFS(GenericSearchProblem problem)
    {
        generic_search(problem, 1);
    }

    public static void BFS(GenericSearchProblem problem)
    {
        generic_search(problem, 2);
    }

    public static void UCS(GenericSearchProblem problem)
    {
        generic_search(problem, 3);
    }

    public static void Greedy(GenericSearchProblem problem)
    {
    	generic_search(problem, 4);
    }

    public static void AStar(GenericSearchProblem problem)
    {
        generic_search(problem, 5);
    }


    public static void main(String[] args)
    {
        int count = 0;
        int max = 1;

        for (int i = 0; i < max; i++)
        {
            System.out.println("Trial#" + i);
            try
            {
                SaveWesteros g = new SaveWesteros(3, 3);
                g.print();
//                System.out.print("DFS: ");
//                DFS(g);
//                System.out.print("UCS: ");
//                UCS(g);
//                System.out.print("Astar: ");
//                AStar(g);
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
