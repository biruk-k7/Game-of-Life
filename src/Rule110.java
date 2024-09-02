
public class Rule110 {
    public static void main(String[] args) {
        int numCells = 100;// number of cells in our automaton
      //  int numCopy = Integer.parseInt(args[1]);
        int cellSize = 7; // side length of each cell in the canvas
        setupStdDraw(numCells, cellSize);
        // This array stores the states of the automaton over time:
        // row[numCells-1] is the current state at time t, row[numCells-2] is
        // the previous state at time t-1, row[numCells - 3] is the state as it
        // was at time t-2, and so on.
        int[][] rows = new int[numCells][numCells];
        // Initialize the last row (i.e., the current state) at random.
        // The other rows will all contain all zeroes by default.
        rows[numCells - 1] = initialize(numCells);
        // Draw all the rows, which really just means show the initial state
        drawRows(rows, cellSize);
        StdDraw.show();
        StdDraw.pause(100);
        // infinite loop to continuously update the state and redraw.
        while (true) {
            // Apply the 110 rule to the last row to compute the next state
            int[] nextState = apply110rule(rows[numCells - 1]);
            // Move all the rows up by 1, to make space for the new state.
            // Clearly this implies "forgetting" the row that used to be at
            // index 0.
            //moveRowsUp(rows);
            // Copy the new state as the last row
            rows[numCells - 1] = nextState;
            // Redraw all the rows.
            StdDraw.clear();
            drawRows(rows, cellSize);
            StdDraw.show();
            StdDraw.pause(100);
        }
    }

    // Setup double buffering, the canvas size, the scale, and so on.
    public static void setupStdDraw(int numCells, int cellSize) {
        StdDraw.enableDoubleBuffering();
        StdDraw.setCanvasSize(numCells * cellSize,numCells * cellSize);
        StdDraw.setScale(0, (numCells + 1) * cellSize);
        StdDraw.setPenColor(StdDraw.BOOK_BLUE);
    }

    // Return a new array of numCells elements, each element initialized
    // randomly as either 0 or 1.
    public static int[] initialize(int numCells) {
        int[] row = new int[numCells];
        for (int i = 0; i < numCells; i++) {
            row[i] = (int) (Math.random() * 2);
        }
        return row;
    }

    // Draw all the rows, with each cell having size cellSize.
    public static void drawRows(int[][] rows, int cellSize) {
        double halfLength = cellSize / 2;
        // Iterate over rows
        for (int r = 0; r < rows.length; r++) {
            // Iterate over "columns", i.e., cells
            for (int c = 0; c < rows[r].length; c++) {
                // Only draw something if the cell has state 1.
                if (rows[r][c] == 1) {
                    StdDraw.filledSquare(c * cellSize + halfLength,
                            (rows.length - r) * cellSize - halfLength,
                            halfLength);
                }
            }
        }
    }

    // Given an array of 0's and 1's, apply the 110 rule to its cells, and
    // return a new array with the updated state.
    public static int[] apply110rule(int[] state) {
        int[] nextState = new int[state.length];
        // Go over each cell to compute the next state for it.
        for (int i = 0; i < state.length; i++) {
            // Compute the indices of the neighbors using modulo, to take into
            // account that we are working on a lattice, i.e., it "wraps
            // around".
            int rightNeighborIndex = (i + 1) % state.length;
            int leftNeighborIndex = (state.length + i - 1) % state.length;
            // Compute the neighborhood state
            int neighborHoodState =  state[rightNeighborIndex] * 100 +
                    state[i] * 10 + state[leftNeighborIndex];
            // Apply the 110rule to this cell.
            switch (neighborHoodState) {
                case 0:
                case 100:
                case 111:
                    nextState[i] = 0;
                    break;
                case 1:
                case 10:
                case 11:
                case 101:
                case 110:
                    nextState[i] = 1;
                    break;
                default:
                    // We should never reach this code, but it is a good form of
                    // input validation
                    System.err.println("Error: Impossible state.");
                    System.exit(1);
            }
        }
        return nextState;
    }

    // Move each row back by one position. The last row is forgotten, and will
    // have value 'null' after this method has completed.
    // Operates on the passed array.


}

