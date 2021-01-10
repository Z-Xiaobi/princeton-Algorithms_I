import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Collections;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

/*
public class FastCollinearPoints {

	private final Point[] points;
	private final LineSegment[] lineSegments;

	private boolean checkRepeatSeg(ArrayList<LineSegment> lsList, LineSegment l) {
		
		String[] lStrings = l.toString().split(" "); // ["p", "->", "q"]
		
		for(LineSegment ls : lsList) {
			String[] lsStrings = ls.toString().split(" ");
			if (ls.toString().equals(l.toString()))
				return true;
			else if ((lsStrings[0].equals(lStrings[2])) && (lsStrings[2].equals(lStrings[0]))) {
				return true;
			}
		}
		return false;
	}
	
    // finds all line segments containing 4 or more points
	public FastCollinearPoints(Point[] points){
		// Check argument
		if (points.length < 1) {
			throw new IllegalArgumentException("Points list is empty");
		}
		
		for (int i = 0; i < points.length; i++) {
			if (points[i] == null) {
				throw new IllegalArgumentException("Points list " + i + "th element is null");
			}
			for (int j = i + 1; j < points.length; j++) {
				if (points[i] == points[j])
					throw new IllegalArgumentException("Points list has repeat entries");
				}
			}
		// Initialize
		// Modification on original array is not appreciated, so copy one (not clone)
		this.points = new Point[points.length];
		for (int i = 0; i < points.length; i++) {
			this.points[i] = points[i];
		}
		Arrays.sort(points);
		lineSegments = segments();
		
	}
    // the number of line segments
	public int numberOfSegments()
	{
		return lineSegments.length;
	}
	// the line segments
	public LineSegment[] segments()
	{
		List<LineSegment> lineSegmentsList = new ArrayList<LineSegment>();
	
		// Iterate origins
		for (int o = 0; o < points.length; o++) {
			Point origin = points[o];
			Arrays.sort(points, origin.slopeOrder()); // sort the points
			// Calculate all slopes
			Double[] slopes = new Double[points.length - 1];
			for (int i = 1; i < points.length; i++) {
				slopes[i-1] = origin.slopeTo(points[i]);
			}
			
			// Check slopes
			for (int i = 0; i < slopes.length - 2; i++) { // no need to iterate the last two slopes
				
				int countCollinear = 0; // number of coolinear segments for current segment
				//  -infinity ... -2, -1  +0  1, 2, ..... +infinity
				int flagLeft = 0; // leftmost point on 1-D axis (smallest except -infinity) default: origin
				int flagRight = 0; // rightmost point on 1-D axis (largest)  default: origin
				
				
				
				for (int j = i + 1; j < slopes.length; j++) {
					
					// count segments and shift vertex of segment
					if (slopes[i].equals(slopes[j])) {
						// means the counted slopes are currently the same
						countCollinear++;
						if (origin.compareTo(points[j+1]) < origin.compareTo(points[flagLeft]))
							flagLeft = j+1;
						
					} else if ((slopes[i].equals(-slopes[j]))) {
						// means the counted slopes of some previous nodes are negative
						countCollinear++;
						if (origin.compareTo(points[j+1]) > origin.compareTo(points[flagRight]))
							flagRight = j+1;
					} 
					
					// add segments
					if ((j == slopes.length) && (countCollinear >= 3)) {
						
						LineSegment lineSegment = new LineSegment(points[flagLeft], points[flagRight]);
						if (!checkRepeatSeg(lineSegmentsList, lineSegment))
							lineSegmentsList.add(lineSegment);
							
					}

				}
			}
		}
		
		return lineSegmentsList.toArray(new LineSegment[lineSegmentsList.size()]);
	}
	*/

public class FastCollinearPoints {
	
//	private final Point[] points;
	private final Point[] cPoints;
	private final LineSegment[] lineSegments;

//    private int num;
//    private List<LineSegment> segments;
    private List<String> segStrings;

    public FastCollinearPoints(Point[] points) {

    	// Check argument
    	if (points == null) {
    		throw new IllegalArgumentException("Point list should not be null.");
    	}
    	if (points.length < 1) {
    		throw new IllegalArgumentException("Point list should not be empty.");
    	}
//    	for (int i = 0; i < points.length; i++) {
//    		if (points[i] == null) {
//    			throw new IllegalArgumentException("Point list's " + i 
//    					+ "th element is null");
//    			}
//    		for (int j = i + 1; j < points.length; j++) {
//    			if (points[i].toString().equals(points[j].toString()))
//    				throw new IllegalArgumentException("Point list has repeat entries.");
//    		}
//    	}
    	
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
    	/*
    	 * Modification on original array is not appreciated, may lead to different result when call segments()
    	 * so copy one and use deep copy or the values are immutable. the cPoints will change when points change
    	 * however, we can't edit the Points to implement Serializable or Cloneable
    	 * the following two methods all cause immutable results
    	 * but the second one has no ImmutableField error
    	 * */
//    	this.points = points;
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
//            num++;
        }
    }

    public LineSegment[] segments() {
    	
    	// just a trick to avoid segments() called with different results
    	// not a good idea
    	if (numberOfSegments() > 0)
    		return lineSegments;
    	
//    	num = 0;
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
    	
    	
    	
    	
    	
//        LineSegment[] all = new LineSegment[num];
//        for (int i = 0; i < num; i++) {
//            all[i] = segments.get(i);
//        }
//        return all;
        return lineSegmentsList.toArray(new LineSegment[lineSegmentsList.size()]);
    }

    public int numberOfSegments() {
    	if (lineSegments == null)
    		return 0;
        return lineSegments.length;
    }



	public static void main (String[] args) {
		// test checkRepeatSeg
		/*
		ArrayList<LineSegment> lineSegmentsList = new ArrayList<LineSegment>();
		Point ori = new Point(0, 0);
		Point[] points = new Point[10];
		for (int i = 0; i < 10; i++) {
	        int x = i;
	        int y = 4;
	        points[i] = new Point(x, y);
	        lineSegmentsList.add(new LineSegment(ori, points[i]));
	    }
		FastCollinearPoints fcp = new FastCollinearPoints(points);
		LineSegment anotherL = new LineSegment(ori, new Point(1, 4));
		StdOut.println(fcp.checkRepeatSeg(lineSegmentsList, anotherL));
		*/
		
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