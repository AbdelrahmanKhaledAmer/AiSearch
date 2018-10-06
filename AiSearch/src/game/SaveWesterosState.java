package game;

import searchAI.Node;
import searchAI.SearchQ;
import searchAI.State;

import java.util.ArrayList;

public class SaveWesterosState extends State {
    private static final int MAX_SIZE = 1000000;
 // action list available (operators)
 	private static final char NORTH = 'N';
 	private static final int NORTHI = 0;
 	private static final char SOUTH = 'S';
 	private static final int SOUTHI = 1;
 	private static final char EAST  = 'E';
 	private static final int EASTI  = 2;
 	private static final char WEST  = 'W';
 	private static final int WESTI  = 3;
 	private static final char PICK  = 'P';
 	private static final char KILL  = 'K';

 	// Movement Costs
 	private static final int X_TO_W       = 243;
 	private static final int X_TO_E       = 81;
 	private static final int E_TO_D_NO_DG = 27;

 	// Picking up Dragonglass cost
 	private static final int D_PICK = 27;
 	
    // Killing Whitewalkers cost
    private static final int KILL1 = 9;
    private static final int KILL2 = 3;
    private static final int KILL3 = 1;

    public final int CMAX;
    public final int RMAX;

    public int col, row, dragonglass, numDragonglassPieces, map[][];

    public SaveWesterosState(int[][] grid, int numDragonglassPieces, SaveWesteros s) {
        this.map = clone(grid);
        this.RMAX = grid.length;
        this.CMAX = grid[0].length;
        this.row = grid.length - 1;
        this.col = grid[0].length - 1;
        this.numDragonglassPieces = numDragonglassPieces;
        sequenceOfActions = "";
    }

    public SaveWesterosState(SaveWesterosState s) {

        this.map = clone(s.map);
        this.RMAX = s.RMAX;
        this.CMAX = s.CMAX;
        this.col = s.col;
        this.row = s.row;
        this.dragonglass = s.dragonglass;
        this.sequenceOfActions = s.sequenceOfActions;
        this.cost = s.cost;
        this.numDragonglassPieces = s.numDragonglassPieces;
    }

    public int[][] clone(int[][] grid) {
        int[][] array = new int[grid.length][grid[0].length];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                array[i][j] = grid[i][j];
            }
        }
        return array;
    }

    public boolean isGoal() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == SaveWesteros.WHITEWALKER) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean equals(SaveWesterosState other) {
        for (int i = 0; i < map.length && i < other.map.length; i++) {
            for (int j = 0; j < map[i].length && i < other.map[i].length; j++) {
                if (map[i][j] != other.map[i][j]) {
                    return false;
                }
            }
        }
        return col == other.col &&
                row == other.row && dragonglass == other.dragonglass;
    }

    public void print() {
        System.out.println("***********************");
        for (int i = 0; i < this.map.length; i++) {
            for (int j = 0; j < this.map[i].length; j++) {
                System.out.print("[" + ((this.map[i][j] == SaveWesteros.EMPTY) ? " " : (this.map[i][j] == SaveWesteros.WHITEWALKER) ? "W" : "D") + "]");
            }
            System.out.println("");
        }
        System.out.println("Jon: (" + this.row + ", " + this.col + ")");
        System.out.println("***********************");
    }

    public SaveWesterosState kill() {
        SaveWesterosState s = new SaveWesterosState(this);
//        this.print();
//        s.print();
        int num = 0;
        if (s.row < s.RMAX - 1 && s.isWhitewalker(s.row + 1, s.col)) {
            s.map[s.row + 1][s.col] = SaveWesteros.EMPTY;
            num++;
        }

        if (s.row > 0 && s.isWhitewalker(s.row - 1, s.col)) {
            s.map[s.row - 1][s.col] = SaveWesteros.EMPTY;
            num++;
        }

        if (s.col < s.CMAX - 1 && s.isWhitewalker(s.row, s.col + 1)) {
            s.map[s.row][s.col + 1] = SaveWesteros.EMPTY;
            num++;
        }

        if (s.col > 0 && s.isWhitewalker(s.row, s.col - 1)) {
            s.map[s.row][s.col - 1] = SaveWesteros.EMPTY;
            num++;
        }
        switch (num) {
            case 0:
                break;
            case 1:
                s.sequenceOfActions += KILL;
                s.cost += KILL1;
                break;
            case 2:
                s.sequenceOfActions += KILL;

                s.cost += KILL2;
                break;
            case 3:
                s.sequenceOfActions += KILL;

                s.cost += KILL3;
                break;
        }
        s.dragonglass--;
        return s;
    }

    public SaveWesterosState pick() {
        SaveWesterosState s = new SaveWesterosState(this);
        s.dragonglass = numDragonglassPieces;
        s.sequenceOfActions += PICK;
        s.cost += D_PICK;
        return s;
    }

    private boolean isValid(int x, int y) {
        return !(x >= CMAX || y >= RMAX || x < 0 || y < 0);
    }

    public void updatePosition(int x, int y) {
        if (isValid(x, y)) {
            this.cost += getMovementCost(x, y);
            this.col = x;
            this.row = y;
        }
    }

    public int getMovementCost(int col, int row) {
        if (this.isWhitewalker(row, col)) {
            return X_TO_W;
        }
        if (dragonglass == 0 && this.isDragonstone(row, col)) {
            return E_TO_D_NO_DG;
        }
        return X_TO_E;
    }

    public SaveWesterosState north() //TODO
    {
        SaveWesterosState s = new SaveWesterosState(this);
        s.updatePosition(this.col, this.row - 1);
        s.sequenceOfActions += NORTH;
        return s;
    }

    public SaveWesterosState south() {
        SaveWesterosState s = new SaveWesterosState(this);
        s.updatePosition(this.col, this.row + 1);
        s.sequenceOfActions += SOUTH;
        return s;
    }

    public SaveWesterosState east() {
        SaveWesterosState s = new SaveWesterosState(this);
        s.updatePosition(this.col + 1, this.row);
        s.sequenceOfActions += EAST;
        return s;
    }

    public SaveWesterosState west() {
        SaveWesterosState s = new SaveWesterosState(this);
        s.updatePosition(this.col - 1, this.row);
        s.sequenceOfActions += WEST;
        return s;
    }

    public boolean isAncestor()
	{
		boolean reduced = false;
		int[] counts = new int[4];
		for(int i = sequenceOfActions.length() - 1; i >= 0; i--)
		{
			//Looking for these sequences only: ns, sn, ew, we, nesw, nwse, senw, swne
			if(sequenceOfActions.charAt(i) == 'N')
			{
				counts[NORTHI]++;
			} else if(sequenceOfActions.charAt(i) == 'S') {
				counts[SOUTHI]++;
			} else if(sequenceOfActions.charAt(i) == 'E') {
				counts[EASTI]++;
			} else if(sequenceOfActions.charAt(i) == 'W') {
				counts[WESTI]++;
			} else {
				break;
			}
			if((counts[NORTHI] == counts[SOUTHI] && counts[EASTI] == 0 && counts[WESTI] == 0) // ns and sn
				|| (counts[EASTI] == counts[WESTI] && counts[NORTHI] == 0 && counts[SOUTHI] == 0) // ew and we
				|| (counts[NORTHI] == counts[SOUTHI] && counts[EASTI] == counts [WESTI]))
			{
				reduced = true;
				break;
			}
		}
		return reduced;
	}

    public SearchQ expandNode(SearchQ q) {
        if (this.isWhitewalker(this.row, this.col)) {
            return q;
        } else {
            SaveWesterosState s1 = this.north();
            if (!s1.equals(this) && !s1.isAncestor()) {
                q.add(new Node(s1));
            }
            SaveWesterosState s2 = this.south();

            if (!s2.equals(this) && !s2.isAncestor()) {
                q.add(new Node(s2));
            }
            SaveWesterosState s3 = this.east();
            if (!s3.equals(this) && !s3.isAncestor())
                q.add(new Node(s3));
            SaveWesterosState s4 = this.west();
            if (!s4.equals(this) && !s4.isAncestor())
                q.add(new Node(s4));
            if (this.dragonglass == 0 && this.isDragonstone(this.row, this.col)){
                SaveWesterosState s5 = this.pick();
                q.add(new Node(s5));
            }
            if (((this.row < this.RMAX - 1 && this.isWhitewalker(this.row + 1, this.col))
                    || (this.row > 0 && this.isWhitewalker(this.row - 1, this.col))
                    || (this.col < this.CMAX - 1 && this.isWhitewalker(this.row, this.col + 1))
                    || (this.col > 0 && this.isWhitewalker(this.row, this.col - 1)))
                    && this.dragonglass > 0) {
                SaveWesterosState s6 = this.kill();
                q.add(new Node(s6));
            }
        }
        return q;
    }

    public int heuristic1() {
        if (this.isGoal()) {
            return 0;
        }
        int possibleCost = 0;
        int whitewalkerDistance = CMAX + RMAX + 1;
        if (this.dragonglass == 0) {
            int dRow = 0;
            int dCol = 0;
            int dragonstoneDistance = 0;
            // Get Manhattan Distance to dragonstone
            for (int r = 0; r < this.map.length; r++) {
                for (int c = 0; c < this.map[r].length; c++) {
                    if (this.isDragonstone(r, c)) {
                        dragonstoneDistance = Math.abs(r - row) + Math.abs(c - col);
                        dRow = r;
                        dCol = c;
                        break;
                    }
                }
            }
            // Multiply dragonstoneDistance - 1 by movement cost
            possibleCost += (dragonstoneDistance - 1) * X_TO_E;
//			System.out.println(possibleCost);
            // Add dragonstone cost
            possibleCost += E_TO_D_NO_DG;
            possibleCost += D_PICK;
//			System.out.println(possibleCost);
            // Get Manhattan distance to a whitewalker
            for (int r = 0; r < this.map.length; r++) {
                for (int c = 0; c < this.map[r].length; c++) {
                    if (this.isWhitewalker(r, c)) {
                        int d = Math.abs(r - dRow) + Math.abs(c - dCol);
                        if (whitewalkerDistance > d) {
                            whitewalkerDistance = d;
                        }
                    }
                }
            }
            // Multiply whitewalkerDistance - 1 by movement cost
            possibleCost += (whitewalkerDistance - 1) * X_TO_E;
//			System.out.println(possibleCost);
            // Add kill cost
            possibleCost += KILL3;
//			System.out.println(possibleCost);
        } else {
            // Get Manhattan distance to a whitewalker
            for (int r = 0; r < this.map.length; r++) {
                for (int c = 0; c < this.map[r].length; c++) {
                    if (this.isWhitewalker(r, c)) {
                        int d = Math.abs(r - row) + Math.abs(c - col);
                        if (whitewalkerDistance > d) {
                            whitewalkerDistance = d;
                        }
                    }
                }
            }
            // Multiply whitewalkerDistance - 1 by movement cost
            possibleCost += (whitewalkerDistance - 1) * X_TO_E;
            // Add kill cost
            possibleCost += KILL3;
        }
        return possibleCost;
    }


    public boolean isEmpty(int row, int col) {
        return map[row][col] == SaveWesteros.EMPTY;
    }

    public boolean isDragonstone(int row, int col) {
        return map[row][col] == SaveWesteros.DRAGONSTONE;
    }

    public boolean isWhitewalker(int row, int col) {
        return map[row][col] == SaveWesteros.WHITEWALKER;
    }
}