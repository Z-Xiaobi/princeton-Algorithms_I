import edu.princeton.cs.algs4.Stopwatch;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {
    
    // Declare static variables before 
    // instance variables, constructors, and methods. 
    private static final double COEFFIENT = 1.96; // defined coefficient in formula
    private final int tri; //  performs T independent computational experiments 
    private final double[] maxOpenSitesRaio;
    private final double mean;
    private final double s;
    
    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if ((n <= 0) || (trials <= 0)) {
            throw new IllegalArgumentException();
        }
        tri = trials; 
        maxOpenSitesRaio = new double[tri];
        for (int t = 0; t < trials; t++) {
            Percolation p =  new Percolation(n);
            while (!p.percolates()) {
                int row = StdRandom.uniform(n) + 1;
                int col = StdRandom.uniform(n) + 1;
                p.open(row, col);
            }
            int numOfOpenSites = p.numberOfOpenSites();
            maxOpenSitesRaio[t] = 1.0 * numOfOpenSites / (n * n); // 1.0 for double
        }
        mean = mean();
        s = stddev();
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(maxOpenSitesRaio);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(maxOpenSitesRaio);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean - COEFFIENT * s / Math.sqrt(tri);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean + COEFFIENT * s / Math.sqrt(tri);
    }

   // test client (see below)
   public static void main(String[] args) {
       int n = Integer.parseInt(args[0]);
       int t = Integer.parseInt(args[1]);
       // extra StdOut output will recognized as failed
       // Stopwatch timer = new Stopwatch();
       PercolationStats ps = new PercolationStats(n, t);
       StdOut.printf("mean = %f\n"
               + "stddev = %f\n"
               + "95%% confidence interval = [%f, %f]", 
               ps.mean, ps.s, ps.confidenceLo(), ps.confidenceHi());
       // double time = timer.elapsedTime();
       // StdOut.println("Time:");
       // StdOut.print(time);
       
       
   }
}
