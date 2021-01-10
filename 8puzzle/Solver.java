import java.util.Comparator;


import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

public class Solver {
    
    private static Stack<Board> solution; // solution list
    private static boolean solvable;
    
    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        
        // check argument
        if (initial == null) throw new IllegalArgumentException("Argument initial board should not be null.");
        
        // initialization
        BoardNode<Board> initialNode = new BoardNode<Board>(initial, null, 0);
        // assume this board is solvable
        solvable = true;
        
        
        // A*
        // current node (goal)
        BoardNode<Board> currNode = runAStar(initialNode, new BoardNodeManhattanComparator());
        if (solvable) {
            solution = new Stack<Board>();
            solution.push(currNode.curr); // enque goal
            while (currNode.parent != null) {
                currNode = currNode.parent;
                solution.push(currNode.curr);
                
                
            }
        }
    }
    
    // use a inner class to define a specified search node for min priority queue
    private static class BoardNode<Board> {
        
        private Board curr; // current board
        private BoardNode<Board> parent; // parent 
        private int moves; // consumptions made from the initial board
        
        // constructor
        public BoardNode(Board currentBoard, BoardNode<Board> parentNode, int moves) {
            curr = currentBoard;
            parent = parentNode;
            this.moves = moves;
        }

    }
    // comparators for BoardNode
    /*
     * The Hamming priority function is the Hamming distance of a board plus the number of moves 
     * made so far to get to the search node. Intuitively, a search node with a small 
     * number of tiles in the wrong position is close to the goal, and we prefer 
     * a search node if has been reached using a small number of moves.
     * */
    
    private class BoardNodeHammingComparator implements Comparator<BoardNode<Board>> {
        
        @Override
        public int compare(BoardNode<Board> bNode1, BoardNode<Board> bNode2) {
            int hamming1 = bNode1.curr.hamming() + bNode1.moves;
            int hamming2 = bNode2.curr.hamming() + bNode2.moves;
            return hamming1 - hamming2;
        }
        
    }
    /* The Manhattan priority function is the Manhattan distance of a board plus 
     * the number of moves made so far to get to the search node.
     * */
    private class BoardNodeManhattanComparator implements Comparator<BoardNode<Board>> {
        
        @Override
        public int compare(BoardNode<Board> bNode1, BoardNode<Board> bNode2) {
            int manhattan1 = bNode1.curr.manhattan() + bNode1.moves;
            int manhattan2 = bNode2.curr.manhattan() + bNode2.moves;
            return manhattan1 - manhattan2;
        }
        
    }
    // A* to find goal from a node
    // return the goal's node
    private static BoardNode<Board> runAStar(BoardNode<Board> startNode, Comparator comparator) {
        
        MinPQ<BoardNode<Board>> open = new MinPQ<BoardNode<Board>>(comparator);
        // twin boards for each node on solution path (check solvable)
        MinPQ<BoardNode<Board>> openTwin = new MinPQ<BoardNode<Board>>(comparator);
        // insert start node and its twin
        open.insert(startNode);
        openTwin.insert(new BoardNode<Board>(
                startNode.curr.twin(),
                startNode.parent,
                startNode.moves)
        );
        
        
        // loop to add neighbor and find path
        while (true) {
            
            // add the node with highest priority into solution path
            BoardNode<Board> minNode = open.delMin();
            // add the node's twin into t
            BoardNode<Board> minNodeTwin = openTwin.delMin();
            
            
            // check when to stop loop
            if (minNode.curr.isGoal()) { return minNode; }
            // if twin board is goal, then board will never reach goal
            if (minNodeTwin.curr.isGoal()) {
                solvable = false;
                return null; 
            }
            
            // add current neighbors
            for (Board neighbor : minNode.curr.neighbors()) {
                
                // OPTIMIZATION
                // check dead loop of possible path parent->current->parent->current...
                if ((minNode.parent != null) && (minNode.parent.curr.equals(neighbor))) {
                    continue;
                }
                
                BoardNode<Board> neighborNode = new BoardNode<Board>(
                        neighbor,
                        minNode,
                        minNode.moves + 1 // each time move one piece on board
                );

                open.insert(neighborNode);
                
            }
            // add neighbors for initial board's twin
            for (Board neighbor : minNodeTwin.curr.neighbors()) {
                
                // OPTIMIZATION
                // check dead loop of possible path parent->current->parent->current...
                if ((minNodeTwin.parent != null) && (minNodeTwin.parent.curr.equals(neighbor))) {
                    continue;
                }
                
                BoardNode<Board> neighborNode = new BoardNode<Board>(
                        neighbor,
                        minNodeTwin,
                        minNodeTwin.moves + 1 // each time move one piece on board
                );

                openTwin.insert(neighborNode);
                
            }
            
        }
        
    }
    
    

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        
        if (!isSolvable()) return -1;
        
        // remove the initial board in the count
        return solution.size() - 1;
        
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution(){
        
        if (!isSolvable()) return null;
        return solution;
        
    }

    // test client 
    public static void main(String[] args) {
        // create initial board from file
//        In in = new In(args[0]);
//        int n = in.readInt();
//        int[][] tiles = new int[n][n];
//        for (int i = 0; i < n; i++)
//            for (int j = 0; j < n; j++)
//                tiles[i][j] = in.readInt();
        // create initial board in program
        int[][] tiles = {
                {2, 3, 5}, 
                {1, 0, 4}, 
                {7, 8, 6}
                };
        
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

}