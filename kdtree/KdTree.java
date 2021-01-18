import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.List;
import java.util.ArrayList;



/**
 *  2d-tree implementation for range search 
 *  (find all of the points contained in a query rectangle)
 *  and nearest-neighbor search (find a closest point to a query point). 
 *  
 *  This class is a mutable data type that represents set of points in the unit square (plane).
 *  (all points have x- and y-coordinates between 0 and 1) using a 2d-tree BST.
 * */

public class KdTree {
    
    // plane is a square, all x and y are in range [0, 1]
    private static final int PLANE_RANGE_MIN = 0;
    private static final int PLANE_RANGE_MAX = 1;
    private KdNode root;
    private int size;
    
    // construct an empty set of points 
    public KdTree() {
        
        root = null;
        size = 0;
        
    }
    
    private void checkArgument(Object argument) {
        
        if (argument == null) throw new IllegalArgumentException("Input argument should not be null.");
        
        if (argument.getClass().getName() == "edu.princeton.cs.algs4.Point2D") {
            Point2D tempPoint = (Point2D) argument;
            if (tempPoint.x() > PLANE_RANGE_MAX || tempPoint.y() > PLANE_RANGE_MAX 
                    || tempPoint.x() < PLANE_RANGE_MIN || tempPoint.y() < PLANE_RANGE_MIN)
                throw new IllegalArgumentException("Input Point2D argument's x and y should be in range [0, 1].");
        }
    
    }
    
    // inner class that defines KdTree's node
    private class KdNode {
        
        private Point2D point;  // key : the point on the plane
        private int level;      // value: level (start from 0)
                                // odd level : divide by vertical line
                                // even level : divide by horizontal line 
        private KdNode left;    // left child
        private KdNode right;   // right child
        
        
        // constructors
        public KdNode(Point2D p, int lev) {
            point = p;
            level = lev;
            left = null;
            right = null;          
        }
        
        // get
        public Point2D getPoint() { return point; }
        public KdNode getLeft() { return left; }
        public KdNode getRight() { return right; }
        public int getLevel() { return level; }
        
        // if current point divide the plane by vertical line
        public boolean isVertical() {
            return getLevel() % 2 == 0;
        }
        public String toString() {

            Point2D leftPoint = getLeft().getPoint();
            Point2D rightPoint = getRight().getPoint();
            return "current (" + point.x() + ", " + point.y()+")\n"
                    + "level: "+ level
                    + "left (" + leftPoint.x() + ", " + leftPoint.y() + ")\n"
                    + "right (" + rightPoint.x() + ", " + rightPoint.y() + ")";
            
        }
             
    }
    
    /**
     * Insert a new node to KdTree recursively.
     * All the point values are different. Repeated values will not be added.
     * @param currentRoot  the current node that program investigate, if null, just create new node
     * @param newPoint  the point that will be added into current tree
     * @param currentLevel  the current tree level for currentRoot node
     * */
    private KdNode addKdNode(KdNode currentRoot, Point2D newPoint, int currentLevel ) {
        
        // check node
        if (currentRoot == null) {
            size++;
            return new KdNode(newPoint, currentLevel);
        }
        
        // insert node recursively
        
        // compare point's x coordinate and y coordinate at even and odd level
        double newX = newPoint.x();
        double newY = newPoint.y();
        // vertical compare x; horizontal compare y
        double cmp = currentRoot.isVertical() ? newX - currentRoot.point.x() : newY - currentRoot.point.y();
        
        // go down the tree
        if (cmp < 0) {
            currentRoot.left = addKdNode(currentRoot.left, newPoint, currentLevel+1);
        } else {
            // check repeated
            if (newPoint.compareTo(currentRoot.point) == 0) {
                return currentRoot;
            }
            currentRoot.right = addKdNode(currentRoot.right, newPoint, currentLevel+1);
        }
        return currentRoot;
    }
    
    
    /**
     * Find a node with the key -- point, recursively
     * @param point  the target point value 
     * */
    private KdNode getKdNode(Point2D point) {
        
        if (root == null) { return null; }
        
        KdNode currentRoot = root; // assign root as start node
        double newX = point.x();
        double newY = point.y();
        // use while loop, faster than recursive method
        while (currentRoot != null) {
            
            double cmp = currentRoot.isVertical() ? newX - currentRoot.point.x() : newY - currentRoot.point.y();
            if (cmp < 0) {
                currentRoot = currentRoot.left;
            } else {
                if (point.compareTo(currentRoot.point) == 0) { return currentRoot; }
                currentRoot = currentRoot.right;
            }
        }
        
        return null;
    }
    
    
    
    
    // is the set empty? 
    public boolean isEmpty() {
        return size == 0;
    }
    
    // number of points in the set
    public int size() {
        return size;
    }
    
    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        
        // check input argument
        checkArgument(p);
        
        // add node p to the tree 
        root = addKdNode(root, p, 0);
    }
    
    // does the set contain point p?
    public boolean contains(Point2D p) {
        
        // check input argument
        checkArgument(p);
        
        return getKdNode(p) != null;
    }
    
    // draw all points to standard draw 
    public void draw() {
        
        // no need to set canvas because the default values satisfy the problem
        // root has vertical divider, so use x as initial value, 
        drawNode(root, root.point.x());
        
    }
    
    /**
     * Draw each node and their divide line on plane
     * @param current node
     * @param divider  the divide line of current node's root (x-coordinate for vertical,
     *  y coordinate for horizontal)
     * */
    private void drawNode(KdNode currentRoot, double divider) {
        
      // points should be black
      StdDraw.setPenColor(StdDraw.BLACK);
      StdDraw.setPenRadius(0.006);
      Point2D point = currentRoot.point;
      point.draw();
      
      // lines
      // check the position of current point and the vertical divider 
      // <0 left / bottom  >0 right / top  0 on
      double pPosition = 0;
      
      if (currentRoot.isVertical()) {
          
          StdDraw.setPenColor(StdDraw.RED); // set color
          StdDraw.setPenRadius(0.002); // set pen radius
          
          pPosition = currentRoot.point.y() - divider;
          // find and draw line segment
          if (pPosition > 0) {
            StdDraw.line(point.x(), divider, point.x(), PLANE_RANGE_MAX);
          } else if (pPosition < 0) {
            StdDraw.line(point.x(), divider, point.x(), PLANE_RANGE_MIN);
          } else { // draw root
            StdDraw.line(point.x(), PLANE_RANGE_MIN, point.x(), PLANE_RANGE_MAX);
          }
          
          // update divider
          divider = point.x();
      }
      else {
          
          StdDraw.setPenColor(StdDraw.BLUE); // set color
          StdDraw.setPenRadius(0.002); // set pen radius
          
          pPosition = currentRoot.point.x() - divider;
          // find and draw line segment
          if (pPosition > 0) {
            StdDraw.line( divider, point.y(), PLANE_RANGE_MAX, point.y());
          } else if (pPosition < 0) {
            StdDraw.line(divider, point.y(), PLANE_RANGE_MIN, point.y());
          }
          
          // update divider
          divider = point.y();
          
      }
      
      // draw childs
      if (currentRoot.left != null)
          drawNode(currentRoot.left, divider);
      if (currentRoot.right != null)
          drawNode(currentRoot.right, divider);
      
    }
    
    
    
    
    
    
    
    
    
    // all points that are inside the rectangle (or on the boundary) 
    public Iterable<Point2D> range(RectHV rect){
        
        // check input argument
        checkArgument(rect);
        
        // store all points in list
        List<Point2D> pointsInRec = new ArrayList<Point2D>();
        findNodesInRect(rect, root, pointsInRec);
        
        
        return pointsInRec;
    }
    
    /**
     * Check the position of rectangle area and the divide line
     * @return -1 for left or bottom  1 for right and top  0 for intersect 
     * */
    private int checkRecPositionToNode (RectHV rect, KdNode node){
        
        // need to consider the input rectangle is actually a line segment
        
        if (node.isVertical()) {
            
            double checkLeft = rect.xmax() - node.point.x();
            double checkRight = rect.xmin() - node.point.x();
            
            if (checkLeft < 0 && checkRight < 0) // rectangle is on the left side of point
                return -1;
            else if (checkLeft > 0 && checkRight > 0)
                return 1;
            else if (checkLeft >= 0 && checkRight <= 0)
                return 0;
            
            
        } else {
            
            double checkUp = rect.ymax() - node.point.y();
            double checkBottom = rect.ymin() - node.point.y();
            

            if (checkUp < 0 && checkBottom < 0) // rectangle is lower than point
                return -1;
            else if (checkUp > 0 && checkBottom > 0)
               return 1;
            else if (checkUp >= 0 && checkBottom <= 0)
                return 0;
            
        }
        return 2; // if check goes wrong, return an irrelevant value
    }
    
    /**
     * Recursively check points in rectangle
     * @param currentRoot  current node that program investigates
     * @param rect  defined rectangular range 
     * @param a list to store all points that are in the rectangular range
     * */
    private void findNodesInRect (RectHV rect, KdNode currentRoot, List<Point2D> points) {
        
        if (currentRoot == null) return;
        
        // check if point in node lies in the rectangle
        if (rect.contains(currentRoot.point))
            points.add(currentRoot.point);
        
        // check rectangle's and divide line's position
        int recPosition = checkRecPositionToNode(rect, currentRoot);
        if (recPosition == -1) { // search left / bottom subtree
            findNodesInRect(rect, currentRoot.left, points);
        } else if (recPosition == 1) { // search right / top subtree
            findNodesInRect(rect, currentRoot.right, points);
        } else if (recPosition == 0) {
            findNodesInRect(rect, currentRoot.left, points);
            findNodesInRect(rect, currentRoot.right, points);
        }
                
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    // a nearest neighbor in the set to point p; null if the set is empty 
    public Point2D nearest(Point2D p) {
        
        // check input argument
        checkArgument(p);
        
        KdNode nearestNode = nearestNode(p, root, null);
        if (nearestNode == null) { return null; }
        return nearestNode.point;
    }
    /**
     * Recursively search the nearest point's node to the defined point
     * Did not consider the conditions that find more than 1 nearest points (just pick one of them)
     * */

private KdNode nearestNode(Point2D p, KdNode currentRoot, KdNode nearest) {
        
        if (currentRoot == null) return null;
        if (nearest == null) {
            nearest = currentRoot;
        }
        

        double distance =  p.distanceSquaredTo(currentRoot.point);
        double currentMinDistance = p.distanceSquaredTo(nearest.point);
        
        // update distance and nearest node
        if (distance < currentMinDistance) {
            currentMinDistance = distance;
            nearest = currentRoot;  

        }

        // next node
        KdNode newNodeLeft = null;
        KdNode newNodeRight = null;
        
        // -1 left or bottom  1 right or top    0 on
        int pPosition = currentRoot.isVertical() ? Point2D.X_ORDER.compare(p, currentRoot.point): Point2D.Y_ORDER.compare(p, currentRoot.point); 
        
        // search children
        // always go towards the query point first
        if (pPosition == -1 || pPosition == 0) {
            
            if (currentRoot.left != null) {
                newNodeLeft =  nearestNode(p, currentRoot.left, nearest);                
            }
            if (currentRoot.right != null ){
                
                if (newNodeLeft != null) {
                    nearest = newNodeLeft;
                    newNodeRight = nearestNode(
                            p,
                            currentRoot.right,
                            newNodeLeft
                    );
                } else {
                    newNodeRight = nearestNode(
                            p,
                            currentRoot.right,
                            nearest
                    );
                }
            }
            
            // update nearest from child
            if (newNodeRight != null) {
                
                nearest = newNodeRight;
                currentMinDistance = p.distanceSquaredTo(nearest.point);

            } else if (newNodeLeft != null && newNodeRight == null) {
                nearest = newNodeLeft;
                currentMinDistance = p.distanceSquaredTo(nearest.point);

            } 

            
        } else if (pPosition == 1) {
            
            if (currentRoot.right != null) {
                newNodeRight =  nearestNode(p, currentRoot.right, nearest);
            }
            if (currentRoot.left != null ){
                
                if (newNodeRight != null) {
                    nearest = newNodeRight;
                    newNodeLeft = nearestNode(
                            p,
                            currentRoot.left,
                            newNodeRight
                    );
                } else {
                    newNodeLeft = nearestNode(
                            p,
                            currentRoot.left,
                            nearest
                    );
                }
            }
            
            // update nearest from child
            if (newNodeLeft != null) {
                nearest = newNodeLeft;
                currentMinDistance = p.distanceSquaredTo(nearest.point);
            } else if (newNodeRight != null && newNodeLeft == null) {
                nearest = newNodeRight;
                currentMinDistance = p.distanceSquaredTo(nearest.point);
            }
            
        }
        
        
        // return current nearest node
        // newNode is always be closer than current node
        return nearest;
    }
    
    // unit testing of the methods (optional) 
    public static void main(String[] args) {
        
        
        
        KdTree kdTree = new KdTree();
        StdOut.println("check isEmpty() "+ kdTree.isEmpty());
        StdOut.println("check size() "+ kdTree.size());
        StdOut.println("insert (0.2, 0.4)");
        kdTree.insert(new Point2D(0.2, 0.4));
        StdOut.println("insert (0.5, 0.5)");
        kdTree.insert(new Point2D(0.5, 0.5));
        StdOut.println("insert (0.5, 0.5)");
        kdTree.insert(new Point2D(0.5, 0.5));
        StdOut.println("check isEmpty() "+ kdTree.isEmpty());
        StdOut.println("check size() "+ kdTree.size());
        
        StdOut.println("check contains (0.2, 0.4) " + kdTree.contains(new Point2D(0.2, 0.4)));
        StdOut.println("check contains (0.5, 0.5) " + kdTree.contains(new Point2D(0.5, 0.5)));
        StdOut.println("check contains (0.3, 0.3) " + kdTree.contains(new Point2D(0.3, 0.3)));
        

        
        StdOut.println("check range(RectHV(0.1, 0, 0.3, 1))");        
        RectHV rectHV = new RectHV(0.1, 0, 0.3, 1);
        for (Point2D point : kdTree.range(rectHV) ) {
            StdOut.println(point.toString());
            
        }
        StdOut.println("insert (0.3, 0.3)");
        kdTree.insert(new Point2D(0.3, 0.3));
        StdOut.println("check range(RectHV(0.1, 0, 0.3, 1))");  
        for (Point2D point : kdTree.range(rectHV) ) {
            StdOut.println(point.toString());
            
        }
        
        StdOut.println("insert (0.1, 0.05)");
        kdTree.insert(new Point2D(0.1, 0.05));
        StdOut.println("check nearest (0, 0): " + kdTree.nearest(new Point2D(0, 0)));
        StdOut.println("check nearest (0.3, 0.5): " + kdTree.nearest(new Point2D(0.3, 0.5)));
        StdOut.println("check nearest (0.6, 0.6): " + kdTree.nearest(new Point2D(0.6, 0.6)));
        
        
        
        
        kdTree.draw();
        
        
        // test nearest
        KdTree kdTree1 = new KdTree();
        StdOut.println("insert (0.5, 0.0)");
        kdTree1.insert(new Point2D(0.5, 0.0));
        StdOut.println("insert (0.25, 0.25)");
        kdTree1.insert(new Point2D(0.25, 0.25));
        StdOut.println("insert (0.625, 0.75)");
        kdTree1.insert(new Point2D(0.625, 0.75));
        StdOut.println("insert (0.0, 0.375)");
        kdTree1.insert(new Point2D(0.0, 0.375));
        StdOut.println("insert (0.875, 0.875)");
        kdTree1.insert(new Point2D(0.875, 0.875));
        
        
        
        StdOut.println("check nearest (1.0, 0.5): " + kdTree1.nearest(new Point2D(1.0, 0.5))); 
        //reference nearest() = (0.875, 0.875)
        
        
        StdOut.println("========================================");
        KdTree kdTree2 = new KdTree();
        StdOut.println("insert (0.0, 1.0)");
        kdTree2.insert(new Point2D(0.0, 1.0));
        StdOut.println("insert (1.0, 0.0)");
        kdTree2.insert(new Point2D(1.0, 0.0));
        StdOut.println("insert (1.0, 0.25)");
        kdTree2.insert(new Point2D(1.0, 0.25));
        StdOut.println("insert (0.0, 0.5)");
        kdTree2.insert(new Point2D(0.0, 0.5));
        
        StdOut.println("insert (0.75, 0.5)");
        kdTree2.insert(new Point2D(0.75, 0.5));
        StdOut.println("insert (0.25, 0.5)");
        kdTree2.insert(new Point2D(0.25, 0.5));
        StdOut.println("insert (0.5, 0.5)");
        kdTree2.insert(new Point2D(0.5, 0.5));
        StdOut.println("insert (0.75, 1.0)");
        kdTree2.insert(new Point2D(0.75, 1.0));
        StdOut.println("insert (1.0, 0.75)");
        kdTree2.insert(new Point2D(1.0, 0.75));
        StdOut.println("insert (0.0, 0.75)");
        kdTree2.insert(new Point2D(0.0, 0.75));

        StdOut.println("check nearest (0.0, 0.25): " + kdTree2.nearest(new Point2D(0.0, 0.25))); 

        
        
    }
}