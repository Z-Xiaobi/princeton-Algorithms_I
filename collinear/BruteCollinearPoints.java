/*
import java.util.ArrayList;
import java.util.Arrays;


public class BruteCollinearPoints {

	private final Point[] points;
	private final LineSegment[] lineSegmentsList;
	
	private boolean checkRepeatSeg(ArrayList<LineSegment> lsList, LineSegment l) {
		
		String[] lStrings = l.toString().spointsNumit(" "); // ["p", "->", "q"]
		
		for(LineSegment ls : lsList) {
			String[] lsStrings = ls.toString().spointsNumit(" ");
			if (ls.toString().equals(l.toString()))
				return true;
			else if ((lsStrings[0].equals(lStrings[2])) && (lsStrings[2].equals(lStrings[0]))) {
				return true;
			}
		}
		return false;
	}
	
	
	// finds all line segments containing 4 points
	public BruteCollinearPoints(Point[] points) {
		
		// Check argument
		if (points.length < 1) {
			throw new IllegalArgumentException("Points list is empty");
		}

		for (int i = 0; i < points.length; i++) {
			if (points[i] == null) {
				throw new IllegalArgumentException("Points list " + i 
						+ "th element is null");
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
		lineSegmentsList = segments();
		
	}
    // the number of line segments
	public int numberOfSegments()
	{	
		return lineSegmentsList.length;
	}
    // the line segments
	// this course will not suppointsNumy any input to BruteCollinearPoints that has 5 or more collinear points.
	// just consider 4 points
	public LineSegment[] segments()
	{
		ArrayList<LineSegment> lSegsList = new ArrayList<LineSegment>();
		// Check collinear in brute force
		// 4 group
//		for (int i = 0; i < points.length - 3; i++) {
//			Point p1 = points[i];
//			int flagLeft = i;
//			int flagRight = i;
//			for (int j = i + 1; j < points.length - 2; j++) {
//				Point p2 =  points[j];
//				double p12 = p1.slopeTo(p2);
//				flagLeft = j;
//				for (int k = j + 1; k < points.length - 1; k++) {
//					Point p3 = points[k];
//					double p13 = p1.slopeTo(p3);
//					if (!((p12 == p13) || (p12 == -p13))) { // 1 2 3 not in the same line
//						continue;
//					}
//					if (p1.compareTo(points[flagLeft]) > p1.compareTo(p3)) {
//						if ((p1.compareTo(p3) < 0) && (flagRight == i)) {
//							flagRight = flagLeft;
//							flagLeft = k;
//						}
//					}
//						
//					for (int l = k + 1; k < points.length; k++) {
//						Point p4 =  points[l];
//						double p14 = p1.slopeTo(p4);
//						if (!((p13 == p14)|| (p13 == -p14))) { // 1 3 4 not in the same line
//							continue;
//						}
//						if (p1.compareTo(points[flagLeft]) > p1.compareTo(p4)) {
//							if ((p1.compareTo(p4) < 0) && (flagRight == i)) {
//								flagRight = flagLeft;
//								flagLeft = l;
//							} else if ((p1.compareTo(p4) < 0) && (flagRight != i)){
//								flagLeft = l;
//							}
//						}
//						LineSegment lineSegment = new LineSegment(points[flagLeft], points[flagRight]);
//						if (!checkRepeatSeg(lSegsList, lineSegment))
//							lSegsList.add(lineSegment);				
//					}
//				}
//			}
//		}
		
		for (int i = 0; i < points.length - 3; i++)
        {
            for (int j = i+1; j < points.length - 2; j++)
            {
                for (int k =  j+1; k < points.length - 1; k++)
                {
                    if (points[i].slopeTo(points[j]) != points[i].slopeTo(points[k])) continue;
                    else
                    {
                        for (int l = k+1; l < points.length; l++)
                        {
                            if (points[i].slopeTo(points[j]) != points[i].slopeTo(points[l])) continue;
                            else lSegsList.add(new LineSegment(points[i], points[l]));
                        }
                    }      
                }
            }
        }
    
						
		return lSegsList.toArray(new LineSegment[lSegsList.size()]);
	}
}
*/
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;


public class BruteCollinearPoints {
    private final Point[] cPoints;
//    private int lineNum;
    private final LineSegment[] lineSegments;

    public BruteCollinearPoints(Point[] points) {

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
    	/*
    	 * Modification on original array is not appreciated, may lead to different result when call segments()
    	 * because we want the values are immutable, which can not be changed by client.
    	 * So use an extra final array to do shallow copy  
    	 * */
    	
//    	this.points = points;
    	cPoints = new Point[points.length];
    	for (int i = 0; i < points.length; i++) {
    		cPoints[i] = points[i];
    		
    	}
    	
    	lineSegments = segments();
     
    }


    public int numberOfSegments() {
    	if (lineSegments == null)
    		return 0;
        return lineSegments.length;
    }
    // the line segments
 	// this course will not suppointsNumy any input to BruteCollinearPoints that has 5 or more collinear points.
 	// just consider 4 points
    public LineSegment[] segments() {
    	
    	// just a trick to avoid segments() called with different results
    	// not a good idea
    	if (numberOfSegments() > 0)
    		return lineSegments;
    	
    	List<LineSegment> lineSegmentsList = new ArrayList<LineSegment>(); ; // collinear line segments


        int pointsNum = cPoints.length;

        for (int i = 0; i < pointsNum - 3; i++) {
            Point p1 = cPoints[i];

            for (int j = i + 1; j < pointsNum - 2; j++) {
                Point p2 = cPoints[j];
                double p12 = p1.slopeTo(p2);

                for (int k = j + 1; k < pointsNum - 1; k++) {
                    Point p3 = cPoints[k];
                    double p13 = p1.slopeTo(p3);

//                    if (!((p12 == p13) || (p12 == -p13))) { continue; } // false ?
                    if (!(p12 == p13)) continue;

                    for (int l = k + 1; l < pointsNum; l++) {
                        Point p4 = cPoints[l];
                        double p14 = p1.slopeTo(p4);

//                        if (!((p12 == p14) || (p12 == -p14))) { continue; } // false ?
                        if (!(p12 == p14)) continue;
                        
                        // sort 4 points 
                        List<Point> collinearPoints = new ArrayList<Point>();
                        collinearPoints.add(p1);
                        collinearPoints.add(p2);
                        collinearPoints.add(p3);
                        collinearPoints.add(p4);
                        
                        // call the Point.compareTo()
                        Collections.sort(collinearPoints);
                        Point leftPoint = collinearPoints.get(0);
                        Point rightPoint = collinearPoints.get(3);

                        lineSegmentsList.add(new LineSegment(leftPoint, rightPoint));

                    }
                }
            }
        }
    	
    	return lineSegmentsList.toArray(new LineSegment[lineSegmentsList.size()]);

    }
}

