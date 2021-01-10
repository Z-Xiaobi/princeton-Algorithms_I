/******************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *  
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *  Original code implements the constructor and the draw(), drawTo(), and toString() methods.
 ******************************************************************************/

import java.util.Arrays;
import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point
    private final static double PINF = Double.POSITIVE_INFINITY; // positive infinity
    private final static double NINF = Double.NEGATIVE_INFINITY; // negative infinity
    private final static double PZERO = +0;
    
    /**
     * Initializes a new point.
     *
     * @param  x the <em>x</em>-coordinate of the point
     * @param  y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param  that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        /* MY CODE HERE */
    	double slope = 0; // initialize slop
    	int deltaX = this.x - that.x; // x0 - x1
    	int deltaY = this.y - that.y; // y0 - y1
    	
    	if ((deltaX == 0) && (deltaY != 0)) { // vertical
    		slope = PINF;
    	} else if ((deltaY == 0) && (deltaX != 0)) { // horizontal
			slope = PZERO;
		} else if ((deltaY == 0) && (deltaX == 0)) { // degenerate
			slope = NINF;
		} else {
			// keep all available bits rather than discard detail by floor operation
			slope = (double)deltaY / (double)deltaX; 
		}
    	
    	return slope;
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param  that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     *         point (x0 = x1 and y0 = y1);
     *         a negative integer if this point is less than the argument
     *         point; and a positive integer if this point is greater than the
     *         argument point
     */
    public int compareTo(Point that) {
        /* MY CODE HERE */
    	int deltaY = this.y - that.y; 
    	if (deltaY == 0) {
    		int deltaX = this.x - that.x;
    		if (deltaX == 0) {
    			return 0;
    		} else {
    			return deltaX;
    		}
    	} else {
    		return deltaY;
    	}
    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        /* MY CODE HERE */
    	// use lambda, so no need to overwrite compare()
    	
    	return (point1, point2) -> Double.compare(slopeTo(point1), slopeTo(point2));
    	/*
    	return new Comparator<Point>() {
        	@Override
        	public int compare(Point o1, Point o2) {
            	return Double.compare(slopeTo(o1), slopeTo(o2));
        	}
    	};
    	*/
    }


    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }
    
    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        /* MY CODE HERE */
    	Point p = new Point(0, 0);
    	Point p1 = new Point(10, 30);
    	Point p2 = new Point(20, 20);
    	
    	// test draw
    	p.draw();
    	p1.draw();
    	p2.draw();
    	p.drawTo(p1);
    	p.drawTo(p2);
    	
    	// test compareTo() slopeTo() slopeOrder()
    	Point[] points = {p, p1, p2};
    	for (int i = 0; i < points.length; i++) {
    		StdOut.println(points[i].toString());
    		StdOut.println("p slope: " + p.slopeTo(points[i]));
    		StdOut.println("compare to p: " + p.compareTo(points[i]));
    	}
    	
    	
    	Arrays.sort(points, p.slopeOrder());
    	for (int i = 0; i < points.length; i++) {
    		StdOut.println(points[i].toString());
    		StdOut.println("p slope: " + p.slopeTo(points[i]));
    		StdOut.println("compare to p: " + p.compareTo(points[i]));
    	}
    	
    }

}
