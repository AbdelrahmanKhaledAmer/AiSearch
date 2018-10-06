package game;

import searchAI.GenericSearchProblem;

public class SaveWesteros extends GenericSearchProblem {
    public static final int EMPTY = 0;
    public static final int DRAGONSTONE = 1;
    public static final int WHITEWALKER = 2;

    public int[][] map;
    public int numDragonglassPieces;

    SaveWesterosState initialState;


    public SaveWesteros(int rows, int cols) {
        map = new int[rows][cols];
        genGrid();
    }


    public SaveWesteros(SaveWesteros other) {    // another constructor for copying the saveWesteros
        //remember the old num of dragon glass pieces
        numDragonglassPieces = other.numDragonglassPieces;
        map = new int[other.map.length][other.map[0].length];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                map[i][j] = other.map[i][j];
            }
        }
    }

    public void genGrid() {
        int numWhiteWalkers = (int) (1 + Math.random() * (Math.min(map.length, map[0].length)));
        numDragonglassPieces = (int) (1 + Math.random() * 2);
        System.out.println("the game has: "+numDragonglassPieces);

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                map[i][j] = EMPTY;
            }
        }

        int k = 0;
        while (k < numWhiteWalkers) {
            int newC = (int) (Math.random() * this.map[0].length);
            int newR = (int) (Math.random() * this.map.length);
            if (newC != map[0].length - 1 && newR != map.length - 1 && map[newR][newC] == EMPTY) {
                map[newR][newC] = WHITEWALKER;
                k++;
            }
        }

        boolean noStone = true;

        do {
            int newC = (int) (Math.random() * this.map[0].length);
            int newR = (int) (Math.random() * this.map.length);
            if (newC != this.map[0].length - 1 && newR != this.map.length - 1 && map[newR][newC] == EMPTY) {
                map[newR][newC] = DRAGONSTONE;
                noStone = false;
            }
        } while (noStone);
    }

//    public void kill(int row, int col) {
//        map[row][col] = EMPTY;
//    }

    public void print() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                System.out.print("[" + ((map[i][j] == EMPTY) ? " " : (map[i][j] == WHITEWALKER) ? "W" : "D") + "]");
            }
            System.out.println("");
        }
    }

//    public boolean isGoal() {
//        for (int i = 0; i < map.length; i++) {
//            for (int j = 0; j < map[i].length; j++) {
//                if (map[i][j] == WHITEWALKER) {
//                    return false;
//                }
//            }
//        }
//        return true;
//    }

//    public boolean equals(SaveWesteros other) {
//        System.out.println("lala");
//        for (int i = 0; i < map.length && i < other.map.length; i++) {
//            for (int j = 0; j < map[i].length && i < other.map[i].length; j++) {
//                if (map[i][j] != other.map[i][j]) {
//                    return false;
//                }
//            }
//        }
//        return true;
//    }

//    public boolean isEmpty(int row, int col) {
//        return map[row][col] == EMPTY;
//    }
//
//    public boolean isDragonstone(int row, int col) {
//        return map[row][col] == DRAGONSTONE;
//    }
//
//    public boolean isWhitewalker(int row, int col) {
//        return map[row][col] == WHITEWALKER;
//    }
}