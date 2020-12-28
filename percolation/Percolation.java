import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int n;
    private boolean[][] grid;
    private int siteCount;
    private final WeightedQuickUnionUF wquf; // instance for weighted quick union option

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) 
            throw new IllegalArgumentException();
        else
            this.n = n;
        grid = new boolean[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                grid[i][j] = false; // initialize all grids blocked
        siteCount = 0;
        // wquf = new WeightedQuickUnionUF(n*n);
        // remove if-statement check full and percolation
        // use the second last node to denote "connection to the first row / upside"
        // use the last node to denote "connection to the last row / downside"
        wquf = new WeightedQuickUnionUF(n*n + 2);
    }
    
    private void checkArgument(int row, int col) {
        if ((row <= 0) || (col <= 0) || (row > this.n) || (col > this.n))
            throw new IllegalArgumentException();
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        checkArgument(row, col);

        if (!isOpen(row, col)) {
            grid[row-1][col-1] = true;
            siteCount += 1;
        }
        
        // connect opened first or last row nodes to specified node
        int oneDIndex = (row-1)*n+col-1; // corresponding index of grid node in 1 D Weighted Quick Union UF
        if (row == 1) {
            wquf.union(n*n, oneDIndex);
        }
        if (row == n) {
            wquf.union(n*n+1, oneDIndex);
        }
        
        // connect new grid with surrounding nodes
        // left
        if (col > 1)
            if (isOpen(row, col-1) && (wquf.find(oneDIndex) != wquf.find(oneDIndex-1))) {
                wquf.union(oneDIndex-1, oneDIndex); // connects to left node
            }
        // right
        if (col < n)
            if (isOpen(row, col+1) && (wquf.find(oneDIndex) != wquf.find(oneDIndex+1))) {
                wquf.union(oneDIndex+1, oneDIndex);
            }
        // up
        if (row > 1)
            if (isOpen(row-1, col) && (wquf.find(oneDIndex) != wquf.find(oneDIndex-n))) {
                wquf.union(oneDIndex-n, oneDIndex); // connects to up node
            }
        // down
        if (row < n)
            if (isOpen(row+1, col) &&  (wquf.find(oneDIndex) != wquf.find(oneDIndex+n))) {
                wquf.union(oneDIndex+n, oneDIndex);
            }
        
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        checkArgument(row, col);
        return grid[row-1][col-1];
    }

    // is the site (row, col) full?
    // A full site is an open site that can be connected to an open site 
    // in the top row via a chain of neighboring (left, right, up, down) open sites. 
    public boolean isFull(int row, int col) {
	checkArgument(row, col);
        return wquf.find(n*n) == wquf.find((row-1)*n+col-1); // Notice: return wrong values some time
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
    	return siteCount;
    }

    // does the system percolate?
    // We say the system percolates if there is a full site in the bottom row. 
    public boolean percolates() {
        // replace if-statement that check all downside nodes isFull 
        // with a downside-end node wquf 
        return wquf.find(n*n) == wquf.find(n*n+1);
    }
    
}
