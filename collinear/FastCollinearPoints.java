import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Collections;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;


public class FastCollinearPoints {

    private final Point[] cPoints;
    private final LineSegment[] lineSegments;
    private List<String> segStrings;

    public FastCollinearPoints(Point[] points) {

    	// Check argument
    	if (points == null) {
    		throw new IllegalArgumentException("Point list should not be null.");
    	}
    	if (points.length < 1) {
    		throw new IllegalArgumentException("Point list should not be empty.");
    	}
    	
    	List<String> pntStrings = new ArrayList<String>();
    	
    	for (int i = 0; i < points.length; i++) {
    		if (points[i] == null) {
    			throw new IllegalArgumentException("Point list's " + i 
    					+ "th element is null");
    		}
    		/* It is bad style to write code that depends on the particular format of
    		 * the output from the toString() method, especially if your reason for
    		 * doing so is to circumvent the public API (which intentionally does not
    		 * provide access to the x- and y-coordinates).*/
//    		for (int j = i + 1; j < points.length; j++) {
//    			if (points[i].toString().equals(points[j].toString()))
//    				throw new IllegalArgumentException("Point list has repeat entries.");
//    		}
    		if (pntStrings.contains(points[i].toString())) {
                throw new IllegalArgumentException("Point list has repeat entries.");
            } else {
            	pntStrings.add(points[i].toString());
            }
    	}


    	// Initialize
    	// not acheive immutable
    	cPoints = new Point[points.length];
    	for (int i = 0; i < points.length; i++) {
    		cPoints[i] = points[i];
    	}
    	lineSegments = segments();
        

    }

    private void addLineSegment(List<LineSegment> lineSegmentsList, List<Point> points) {
        Collections.sort(points);
        LineSegment ls = new LineSegment(points.get(0), points.get(points.size() - 1));

        // contains can check string directly
        // because when contains(Point p), it will call : return indexOf(o) >= 0;  
        // that statement will call o.equals(), while Point has no equals()
        // so it will call: return (this == obj); two Point objects' address is different, not work
        // String overrides equals(), so safe to use
        if (!segStrings.contains(ls.toString())) { 
        	 
            lineSegmentsList.add(ls);
            segStrings.add(ls.toString());

        }
    }

    public LineSegment[] segments() {
    	
    	// just a trick to avoid segments() called with different results
    	// not a good idea
    	if (numberOfSegments() > 0)
    		return lineSegments;

    	List<LineSegment> lineSegmentsList = new ArrayList<LineSegment>();
        segStrings = new ArrayList<String>();

        // trace over each point as origins
        for (int o = 0; o < cPoints.length; o++) {
        	
            Point origin = cPoints[o];
            Point[] restPoints = new Point[cPoints.length - 1];

            for (int i = 0; i < cPoints.length; i++) {
                if (o != i) {
                    int index = i < o ? i : i - 1;
                    restPoints[index] = cPoints[i];
                }
            }

            // sort array by slope order
            Arrays.sort(restPoints, origin.slopeOrder());

            // store slopes respect to origin
            double[] slopes = new double[restPoints.length];
            for (int i = 0; i < restPoints.length; i++) {
                slopes[i] = origin.slopeTo(restPoints[i]);
            }


            double currentSlope = slopes[0];

            List<Point> tmp = new ArrayList<Point>();

            for (int i = 0; i < restPoints.length; i++) {

                if (currentSlope == slopes[i]) {
                    tmp.add(restPoints[i]);

                    if ((i == restPoints.length - 1) && (tmp.size() > 2)) {
                        tmp.add(origin);
                        addLineSegment(lineSegmentsList, tmp);
                    }

                } else {

                    if (tmp.size() > 2) {
                        tmp.add(origin);
                        addLineSegment(lineSegmentsList, tmp);
                    }

                    tmp = new ArrayList<Point>();
                    tmp.add(restPoints[i]);
                }

                currentSlope = slopes[i];
            }
        }

        return lineSegmentsList.toArray(new LineSegment[lineSegmentsList.size()]);
    }

    public int numberOfSegments() {
    	if (lineSegments == null)
    		return 0;
        return lineSegments.length;
    }



	public static void main (String[] args) {
		
	    // read the n points from a file
	    In in = new In(args[0]);
	    int n = in.readInt();
	    Point[] points = new Point[n];
	    for (int i = 0; i < n; i++) {
	        int x = in.readInt();
	        int y = in.readInt();
	        points[i] = new Point(x, y);
	    }

	    // draw the points
	    StdDraw.enableDoubleBuffering();
	    StdDraw.setXscale(0, 32768);
	    StdDraw.setYscale(0, 32768);
	    for (Point p : points) {
	        p.draw();
	    }
	    StdDraw.show();

	    // print and draw the line segments
	    FastCollinearPoints collinear = new FastCollinearPoints(points);
	    for (LineSegment segment : collinear.segments()) {
	        StdOut.println(segment);
	        segment.draw();
	    }
	    StdDraw.show();
	}
}
