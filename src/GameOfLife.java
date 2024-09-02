public class GameOfLife {
    public static void main(String[] args) {
        //check if the validity of the comments then take them if valid
        if (args.length != 2) {
            System.err.println("Error: You need two command line arguments for this program!");
            System.exit(1);
        }
        int numCell = Integer.parseInt(args[0]);
        int numCopies = Integer.parseInt(args[1]);
        //The program should print error in case the arguments are less than zero
        if (numCell < 0 || numCopies < 0) {
            System.err.println("Error: Please input positive numbers!");
            System.exit(1);
        }
        //initialize both the array and the drawing
        StdDraw.setScale(0, numCell);
        int[][] state= new int[numCell*100][numCell*100];
        for (int i = 0; i < numCopies; i++) {
            blinker(state, numCell);
            beacon(state, numCell);
            glider(state, numCell);
            glider(state, numCell);
        }
        StdDraw.enableDoubleBuffering();
        StdDraw.clear(StdDraw.GRAY);

        draw(state);
        StdDraw.show();
        StdDraw.pause(100);

        //loop infinitely by drawing and recording each step
        while(true){
            StdDraw.enableDoubleBuffering();
            state = applyConwaysRule(state,numCell);
            draw(state);
            StdDraw.show();
            StdDraw.pause(200);
            StdDraw.clear(StdDraw.GRAY);
        }
    }
    //model I
    public static void blinker(int[][] state, int numCell) {
        int x = (int) (Math.random() * numCell);
        int y = (int) (Math.random() * numCell);
        state[(state.length+x - 1)% state.length][y] = 1;
        state[x][y] = 1;
        state[(x + 1)% state.length][y] = 1;
    }
    //model II
    public static void beacon(int[][] state, int numCell) {
        int x = (int) (Math.random() * numCell - 1);
        int y = (int) (Math.random() * numCell - 1);
        state[x][y] = 1;
        state[(state.length+x - 1)% state.length][y] = 1;
        state[(state.length+x - 1)% state.length][(state.length+y - 1)% state.length] = 1;
        state[(x + 1)% state.length][(state.length+y - 2)% state.length] = 1;
        state[x][(state.length+y - 3)% state.length] = 1;
        state[(x + 1)% state.length][(state.length+y - 3)% state.length] = 1;
    }
    //model III
    public static void glider(int[][] state, int numCell) {
        int x = (int) (Math.random() * numCell - 1);
        int y = (int) (Math.random() * numCell - 1);
        state[x][y] = 1;
        state[(state.length+x - 2)% state.length][y] = 1;
        state[x][(state.length+y - 1)% state.length] = 1;
        state[(state.length+x - 1)% state.length][(state.length+y - 1)% state.length] = 1;
        state[(state.length+x - 1)% state.length][(state.length+y - 2)% state.length] = 1;
    }
    //a method to deal with all the drawings
    public static void draw(int[][] state) {

        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state.length; j++) {
                if (state[i][j] == 1) {
                    StdDraw.setPenColor(StdDraw.GREEN);
                    StdDraw.filledSquare(i, j, 0.6);
                }
            }
        }
    }
    //the logic/rules of the game
    public static int[][] applyConwaysRule(int[][] state, int numCell) {
        int[][] nextState = new int[numCell][numCell];
        for(int i=0; i<numCell;i++){
            for(int j=0; j<numCell;j++){
                int sum = state[(state.length+i-1)% state.length][j]
                         +state[(i+1)% state.length][j]
                         +state[i][(j+1)% state.length]
                         +state[i][(state.length+j-1)% state.length]
                         +state[(i+1)% state.length][(j+1)% state.length]
                         +state[(i+1)% state.length][(state.length+j-1)% state.length]
                         +state[(state.length+i-1)% state.length][(j+1)% state.length]
                         +state[(state.length+i-1)% state.length][(state.length+j-1)% state.length];
                if((state[i][j]==1)&&(sum<2||sum>3)){
                        nextState[i][j]=0;
                }
                else if((sum == 2||sum==3)&&(state[i][j]==1)){
                        nextState[i][j]=1;
                }
                else if((state[i][j]==0)&&(sum==3)){
                        nextState[i][j]=1;
                }
            }
        }
            return nextState;
    }
}

