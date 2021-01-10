import java.util.Iterator;
import java.util.Arrays;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;


public class Board {
    private int[][] board;
    private int dimension;
    
    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        checkBoard(tiles);
        // deep copy
        board = new int[tiles.length][];
        for (int i = 0; i < tiles.length; i++) {
            board[i] = Arrays.copyOf(tiles[i], tiles[i].length);
        }
        dimension = dimension();
    }
      
    

    // Redundant 'final' modifier. [RedundantModifier]
    private static void checkBoard(int[][] tiles) {
        
        if (tiles == null) {
            throw new IllegalArgumentException("Argument tiles should not be null.");
        }
        int n = tiles.length;
//        if (n < 2) throw new IllegalArgumentException("Argument tiles should have larger size.");
        for (int i = 0; i < n; i++) {
            if (tiles[i].length != n)
                throw new IllegalArgumentException("Argument tiles should be a n-by-n array.");
        }
    }
                                         
    // string representation of this board
    public String toString() {
        
        String boardSize = Integer.toString(dimension);
        String boardString = "";
        for (int i = 0; i < dimension; i++) {
            boardString += " ";

            for (int j = 0; j < dimension; j++) {
                if (j == dimension - 1)
                    boardString += Integer.toString(board[i][j]);
                else
                    boardString += Integer.toString(board[i][j]) + "  ";
            }
            boardString += '\n';
            
        }
        return boardSize + '\n' + boardString;
    }

    // board dimension n
    public int dimension() {
        return board.length;
    }

    // number of tiles out of place
    // The Hamming distance betweeen a board and the goal board 
    // is the number of tiles in the wrong position. 
    public int hamming() {
        
        int harm = 0; // num of tiles in wrong position 
        
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                
                // here the formula can't be 0, so always counted the last one
                if (board[i][j] != j + 1 + i * dimension)
                    harm += 1;
                
            }
        }
        // 0 is not counted as a position
        harm -= 1;
            
        return harm;
    }

    // sum of Manhattan distances between tiles and goal (not include zero)
    // The Manhattan distance between a board and the goal board is 
    // the sum of the Manhattan distances 
    // (sum of the vertical and horizontal distance) 
    // from the tiles to their goal positions.
    public int manhattan() {
        
        int manhattan = 0;
        
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                
                if (board[i][j] != 0) { 
                    if (board[i][j] != j + 1 + i * dimension) {
                        // this will lead to wrong calculation for fringe values
//                        int goalI = board[i][j] / dimension;
//                        int goalJ = board[i][j] % dimension - 1;
                        int goalI = (board[i][j] - 1) / dimension;
                        int goalJ = (board[i][j] - 1) % dimension;
                        manhattan += Math.abs(goalI - i) + Math.abs(goalJ - j);
                    }
                }  
                
                
            }
        }
        
        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        
        // check 0 first
        // count the 0 here
        if (board[dimension - 1][dimension - 1] != 0)
            return false;
        
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                
                // the board[n-1][n-1] can't be 0 in formula: j + 1 + i * dimension
                if ((board[i][j] != j + 1 + i * dimension ) && (i != dimension - 1) && (j != dimension - 1))
                    return false;
                
            }
        }
        
        return true;
        
    }

    // does this board equal y?
    public boolean equals(Object y) {
        
        if (y == null) { return false; }
        
//        if (y instanceof Board) { // not recommended
        if (y.getClass().getName().equals("Board")) {
            Board anotherBoard = (Board) y;
            if (anotherBoard.dimension != dimension) {
                return false;
            } else {
                for (int i = 0; i < dimension; i++) {
                    if (anotherBoard.board[i].length != dimension) return false;
                    for (int j = 0; j < dimension; j++) {
                        if (anotherBoard.board[i][j] != board[i][j]) return false;
                    }
                }
                return true;
            }
            
        } 
        
        
        return false;

    }

    
    /* Find neighbors */
    // find the 0's index in board
    private int[] findZero() {
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (board[i][j] == 0) return new int[] {i, j};
            }
        }
        return null;
    }
    // count the number of neighbour of an index
    // only used for test findNeighbours
    private int countNeighbor(int[] idx) {
        
        int i =  idx[0];
        int j = idx[1];
        int count = 4; // max num of neighbor for a point is 4
        if (i == 0 || i == dimension - 1) count--; // no up or down neighbor
        if (j == 0 || j == dimension - 1) count--; // no left or right neighbor
        return count;
        
    }
    // find the neighbor points of an index
    private Queue<int[]> findNeighbors(int[] idx) {
        
        int i =  idx[0];
        int j = idx[1];
        
        Queue<int[]> neighbors = new Queue<int[]>();
        
        if (i == 0) { // no up neighbor
            neighbors.enqueue(new int[] {i + 1, j});
        } else if (i == dimension - 1) { //  no down neighbor
            neighbors.enqueue(new int[] {i - 1, j});
        } else {
            neighbors.enqueue(new int[] {i + 1, j});
            neighbors.enqueue(new int[] {i - 1, j});
        }
        
        if (j == 0) { // no left neighbor
            neighbors.enqueue(new int[] {i, j + 1});
        } else if (j == dimension - 1) { //  no right neighbor
            neighbors.enqueue(new int[] {i, j - 1});
        } else {
            neighbors.enqueue(new int[] {i, j + 1});
            neighbors.enqueue(new int[] {i, j - 1});
        }
        
        return neighbors;
    }
    // all neighboring boards
    public Iterable<Board> neighbors(){

        // can't pass check 
        
        return new Iterable<Board>() {
            
            @Override
            public Iterator<Board> iterator() {
                
                return new Iterator<Board>() {
                    
                    int[] initialZero = findZero();    
                    private Queue<int[]> nghbs = findNeighbors(initialZero);
                    private int[][] initialBoard = board;
                    
                    @Override
                    // Use the 'isEmpty()' method instead of 
                    // comparing 'size()' to '0'. [UseCollectionIsEmpty]
                    public boolean hasNext() { return !nghbs.isEmpty(); }
                    
                    @Override
                    public Board next() {
                        
                        int[] neighbor = nghbs.dequeue();
                        
                        // deep copy of current board
                        int[][] newBoardItems = new int[dimension][dimension];
                        
                        for (int i = 0; i < dimension; i++) {
                            newBoardItems[i] = Arrays.copyOf(initialBoard[i], initialBoard[i].length);
                        }
                        // swap value
                        newBoardItems[initialZero[0]][initialZero[1]] =  newBoardItems[neighbor[0]][neighbor[1]];
                        newBoardItems[neighbor[0]][neighbor[1]] = 0;
                        
                        // return new Board
                        return new Board(newBoardItems);
                        
                    }
                    @Override
                    public void remove() { throw new UnsupportedOperationException(); }// Not implemented
                    
                }; // return new Iterator<Board>()
            }
        }; // return new Iterable<Board>()
        
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        
        int[][] newBoardItems = new int[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            newBoardItems[i] = Arrays.copyOf(board[i], board[i].length);
        }
        
        // a trick to avoid choosing the same tile 
        // change two neighbors of zero
        Queue<int[]> neigbors = findNeighbors(findZero());
        int[] point1 = neigbors.dequeue(); 
        int[] point2 = neigbors.dequeue();
        int i1 = point1[0];
        int j1 = point1[1];
        int i2 = point2[0];
        int j2 = point2[1];  
        
        // swap two tiles
        int temp = 0;
        temp = newBoardItems[i1][j1];
        newBoardItems[i1][j1] = newBoardItems[i2][j2];
        newBoardItems[i2][j2] = temp;
        
        
        return new Board(newBoardItems);
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        
//        int[][] tiles = {
//                {0, 1, 3},
//                {4, 2, 5},
//                {7, 8, 6}
//                };
        int[][] tiles = {
                {1, 0},
                {2, 3},
                };
        
        Board board = new Board(tiles);
        StdOut.println(board.toString());
        StdOut.println(board.hamming());
        
        // check equals
        Board board2 = new Board(tiles);
        StdOut.println("Check equals with same input: "+board.equals(board2));
        
        // check harmming
        StdOut.println("Harmming of " + board.toString());
        StdOut.println(board.hamming());
        // check manhattan
        StdOut.println("Manhattan:");
        StdOut.println(board.manhattan());
        // check neighbors
        StdOut.println("find zero's position: (" + board.findZero()[0] + ", " + board.findZero()[1] + ")");
        StdOut.println("number of neighbors: " + board.countNeighbor(board.findZero()));
        Queue<int[]> nei = board.findNeighbors(board.findZero());
        StdOut.println("number of neighbors in Queue: " + nei.size());
        while (!nei.isEmpty()) {
            int[] point = nei.dequeue();
            StdOut.println("(" + point[0] + "," + point[1] + ")");
            
        }

        StdOut.println("Original board:");
        StdOut.println(board.toString());
        StdOut.println("Neighbor boards:");
        for (Board neighbor : board.neighbors()) {
            StdOut.println(neighbor.toString());
            
        }        
        
        // check twin
        StdOut.println("Board Twin:");
        StdOut.println(board.twin().toString());
        
        // check isGoal
        int[][] tiles1 = {
                {0, 1, 3},
                {4, 2, 5},
                {7, 8, 6}
        };
        int[][] tiles2 = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 0}
        };
        Board board3 = new Board(tiles1);
        Board board4 = new Board(tiles2);
        StdOut.println("is goal: " + board3.isGoal());
        StdOut.println("is goal: " + board4.isGoal());
//       
    }

}