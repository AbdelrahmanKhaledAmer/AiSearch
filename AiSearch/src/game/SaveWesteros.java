package game;

import searchAI.GenericSearchProblem;

public class SaveWesteros extends GenericSearchProblem {
    public static final int EMPTY = 0;
    public static final int DRAGONSTONE = 1;
    public static final int WHITEWALKER = 2;

    public int numDragonglassPieces;
//    public SaveWesterosState initialState;

    public SaveWesteros(int rows, int cols) {
        genGrid(rows, cols);
    }

    public void genGrid(int rows, int cols) {
        int[][] grid = new int[rows][cols];
        int numWhiteWalkers = (int) (1 + Math.random() * (Math.min(grid.length, grid[0].length)));
        numDragonglassPieces = (int) (1 + Math.random() * 2);
        System.out.println("the game has: " + numDragonglassPieces);

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = EMPTY;
            }
        }
        int k = 0;
        while (k < numWhiteWalkers) {
            int newC = (int) (Math.random() * grid[0].length);
            int newR = (int) (Math.random() * grid.length);
            if (newC != grid[0].length - 1 && newR != grid.length - 1 && grid[newR][newC] == EMPTY) {
                grid[newR][newC] = WHITEWALKER;
                k++;
            }
        }
        boolean noStone = true;
        do {
            int newC = (int) (Math.random() * grid[0].length);
            int newR = (int) (Math.random() * grid.length);
            if (newC != grid[0].length - 1 && newR != grid.length - 1 && grid[newR][newC] == EMPTY) {
                grid[newR][newC] = DRAGONSTONE;
                noStone = false;
            }
        } while (noStone);
        initialState = new SaveWesterosState(grid, numDragonglassPieces, this);
    }

    public void print() {

        this.initialState.print();
    }
}