import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.List;
import java.util.ArrayList;
import java.util.TreeSet;

/**
 *  Brute-force implementation for range search 
 *  (find all of the points contained in a query rectangle)
 *  and nearest-neighbor search (find a closest point to a query point). 
 *  
 *  This class is a mutable data type that represents set of points in the unit square (plane).
 *  (all points have x- and y-coordinates between 0 and 1) using a red–black BST.
 * */

public class PointSET {
    
    // plane is a square, all x and y are in range [0, 1]
    private static final int PLANE_RANGE_MIN = 0;
    private static final int PLANE_RANGE_MAX = 1;
    private final TreeSet<Point2D> pointsSet;
    
    
    // construct an empty set of points 
    public PointSET() {
        
        // Initialization
        pointsSet = new TreeSet<Point2D>();
        
    }
    /**
     * Check whether the input argument is null
     * */
    private void checkArgument(Object argument) {
        
        if (argument == null) throw new IllegalArgumentException("Input argument should not be null.");
       
        if (argument.getClass().getName() == "edu.princeton.cs.algs4.Point2D") {
            Point2D tempPoint = (Point2D) argument;
            if (tempPoint.x() > PLANE_RANGE_MAX || tempPoint.y() > PLANE_RANGE_MAX 
                    || tempPoint.x() < PLANE_RANGE_MIN || tempPoint.y() < PLANE_RANGE_MIN)
                throw new IllegalArgumentException("Input Point2D argument's x and y should be in range [0, 1].");
        }
    }
    
    // is the set empty? 
    public boolean isEmpty() {
        if (pointsSet == null) return true;
        return pointsSet.isEmpty();
    }
    
    // number of points in the set
    public int size() {
        if (pointsSet == null) return 0;
        return pointsSet.size();
    }
    
    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        
        // check input argument
        checkArgument(p);
        // add point into set
        pointsSet.add(p);
        
    }
    
    // does the set contain point p?
    public boolean contains(Point2D p) {
        
        // check input argument
        checkArgument(p);
        
        if (p == null) { return false; }
        return pointsSet.contains(p);
    }
    
    // draw all points to standard draw 
    public void draw() {
        
//        Iterator<Point2D> pIterator = pointsSet.iterator();
//        while (pIterator.hasNext()) {
//            // similar to StdDraw.point(pIterator.next().x(), pIterator.next().y());
//            pIterator.next().draw();          
//        }
        StdDraw.setPenRadius(0.006);
        for (Point2D point : pointsSet)
            point.draw();
        
    }
    
    // all points that are inside the rectangle (or on the boundary) 
    public Iterable<Point2D> range(RectHV rect){
        
        // check input argument
        checkArgument(rect);
        
        // iterate over each point and check whether it is in the rectangle
        // don't really need to create a new iterator, List is enough
        // running time: c * N (c: constant  N: number of all points)
        List<Point2D> pointInRect = new ArrayList<Point2D>();
        for (Point2D point : pointsSet) {
            if (rect.contains(point))  pointInRect.add(point);
        }
        return pointInRect;
    }
    
    // a nearest neighbor in the set to point p; null if the set is empty 
    public Point2D nearest(Point2D p) {
        
        // check input argument
        checkArgument(p);
        
        // find the nearest neighbor
        Point2D nearestNeighbor = null; 
        double  minDistanceToP = Double.POSITIVE_INFINITY;
        // iterate over each point and check whether its' distance to p is smallest
        // running time: c * N (c: constant  N: number of all points)
        for (Point2D point : pointsSet) {
            
            double newDistance = point.distanceTo(p);
            if (newDistance < minDistanceToP) {
                nearestNeighbor = point;
                minDistanceToP = newDistance;
            }
            
        }
        
        return nearestNeighbor;
    }
    
    // unit testing of the methods (optional) 
    public static void main(String[] args) {
        
        
        /* Unit Test */
        // [ERROR] PointSET.java:211:11: Do not catch 'Exception' in this course. You may catch specific exceptions, such as 'java.lang.IllegalArgumentException'. [IllegalCatch]

        
        // Functionality
        PointSET pointSET = new PointSET();
        StdOut.println("check isEmpty() "+ pointSET.isEmpty());
        StdOut.println("check size() "+ pointSET.size());
        StdOut.println("Rect x [0, 1] y [0, 1]");
        for ( Point2D p : pointSET.range(new RectHV(0, 0, 1, 1)))
            StdOut.println(p.toString());
        StdOut.println("Rect x [-1, 0.3] y [0, 0.4]");
        for ( Point2D p : pointSET.range(new RectHV(-1, 0, 0.3, 0.4)))
            StdOut.println(p.toString());
        StdOut.println("Nearest for (0,0) " + pointSET.nearest(new Point2D(0, 0)));
        
        
        StdOut.println("Insert point (0.2, 0.4)");
        pointSET.insert(new Point2D(0.2, 0.4));
        StdOut.println("Insert point (0.5, 0.5)");
        pointSET.insert(new Point2D(0.5, 0.5));
        StdOut.println("Insert point (0.2, 0.25)");
        pointSET.insert(new Point2D(0.2, 0.25));
        StdOut.println("check isEmpty() "+ pointSET.isEmpty());
        StdOut.println("check size() "+ pointSET.size());
        StdOut.println("check contains(0.2, 0.4) "+ pointSET.contains(new Point2D(0.2, 0.4)));
        StdOut.println("check contains(0.5, 0.5) "+ pointSET.contains(new Point2D(0.5, 0.5)));
//            StdOut.println("Insert point (2, 2)");
//            pointSET.insert(new Point2D(2, 2));
//            StdOut.println("check isEmpty() "+ pointSET.isEmpty());
//            StdOut.println("check size() "+ pointSET.size());
        StdOut.println("Insert point (0.5, 0.5)");
        pointSET.insert(new Point2D(0.5, 0.5));
        StdOut.println("Insert point (0.3, 0.3)");
        pointSET.insert(new Point2D(0.3, 0.3));
        StdOut.println("Insert point (0, 1)");
        pointSET.insert(new Point2D(0, 1));
        StdOut.println("check isEmpty() "+ pointSET.isEmpty());
        StdOut.println("check size() "+ pointSET.size());    
        
        
        
        StdOut.println("Rect x [0, 1] y [0, 1]");
        for ( Point2D p : pointSET.range(new RectHV(0, 0, 1, 1)))
            StdOut.println(p.toString());
        StdOut.println("Rect x [-1, 0.3] y [0, 0.4]");
        for ( Point2D p : pointSET.range(new RectHV(-1, 0, 0.3, 0.4)))
            StdOut.println(p.toString());
        StdOut.println("Nearest for (0,0) " + pointSET.nearest(new Point2D(0, 0)));
        
        pointSET.draw();        
        
    }
}