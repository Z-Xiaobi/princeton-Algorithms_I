# Collinear Points
Coursework Specification: <a href="https://coursera.cs.princeton.edu/algs4/assignments/collinear/specification.php" target="_blank">https://coursera.cs.princeton.edu/algs4/assignments/collinear/specification.php</a>

Score: 87 (Jan 10, 2021)

**Brute Force**: just use 4 for-loops to access the array and find all 4-point set. Sort the four points, find two ends of the segement.


**Fast**: 


***Find Segment***: define an origin point in for-loop and compare the slopes to that origin.
I found two ways to find origin and other points, the first is to choose origin and sort (`slopeOrder()`) the points with that origin. 
So I can just pick the other points from the second entry. But it waste computations, also, my points list is not immutable, I didn't solve it, 
so it will change the list and following pickup of orgin is difficult. Then, I choose to just use another arrary to point to the other points with size N-1.


Only count the points with the same slope as a segment point set when then number of points is larger than 3.
All the points will be an origin at different times. So to avoid insert repeat segements, we need to check them first.

***Check Repeated***: convert point to string and check whether the list `contains` that string


Did not find a good way to check repeated entries. But `List.contains()` is not working on List<Point>. Because, `contains` will call `Object.equals`.
```
public boolean equals(Object obj) {
        return (this == obj);
}
```
So, the `contains` will check the address rather than content. Usually, I have to override the `equals` to compare content rather that address. 
However, we can't edit `Point.java` to override `public boolean equals()`.


**Note**: When I try to solve this problem, the biggest challenge is to find the two ends of segments properly. Because I totally misunderstood the explanation given by the specification.
I found I just need to utilize `Collections.sort()` to sort the points with the same slopes then find the first and last one. I forgot I `implements Comparable` and my `compareTo()` method.
Anyway, `Collections.sort` requires the sorting object to implement `Comparable` interface.



Some failed checks of my code:
```
Test 8: check for fragile dependence on return value of toString()
  * filename = equidistant.txt

    java.lang.IllegalArgumentException: Point list has repeat entries.

    FastCollinearPoints.<init>(FastCollinearPoints.java:166)
    TestFastCollinearPoints.testSegments(TestFastCollinearPoints.java:108)
    TestFastCollinearPoints.file(TestFastCollinearPoints.java:168)
    TestFastCollinearPoints.test8(TestFastCollinearPoints.java:353)
    TestFastCollinearPoints.main(TestFastCollinearPoints.java:828)

  * filename = input40.txt

    java.lang.IllegalArgumentException: Point list has repeat entries.

    FastCollinearPoints.<init>(FastCollinearPoints.java:166)
    TestFastCollinearPoints.testSegments(TestFastCollinearPoints.java:108)
    TestFastCollinearPoints.file(TestFastCollinearPoints.java:168)
    TestFastCollinearPoints.test8(TestFastCollinearPoints.java:354)
    TestFastCollinearPoints.main(TestFastCollinearPoints.java:828)

  * filename = input48.txt

    java.lang.IllegalArgumentException: Point list has repeat entries.

    FastCollinearPoints.<init>(FastCollinearPoints.java:166)
    TestFastCollinearPoints.testSegments(TestFastCollinearPoints.java:108)
    TestFastCollinearPoints.file(TestFastCollinearPoints.java:168)
    TestFastCollinearPoints.test8(TestFastCollinearPoints.java:355)
    TestFastCollinearPoints.main(TestFastCollinearPoints.java:828)


It is bad style to write code that depends on the particular format of
the output from the toString() method, especially if your reason for
doing so is to circumvent the public API (which intentionally does not
provide access to the x- and y-coordinates).

==> FAILED


Test 13: check that data type is immutable by testing whether each method
         returns the same value, regardless of any intervening operations
  * input8.txt
    - failed after 14 operations involving FastCollinearPoints
    - first and last call to segments() returned different arrays
    - sequence of operations was:
          FastCollinearPoints collinear = new FastCollinearPoints(points);
          collinear.numberOfSegments() -> 2
          mutate points[] array that was passed to constructor
          collinear.numberOfSegments() -> 2
          collinear.numberOfSegments() -> 2
          collinear.numberOfSegments() -> 2
          collinear.segments()
          collinear.segments()
          mutate points[] array that was passed to constructor
          collinear.numberOfSegments() -> 2
          mutate array returned by last call to segments()
          mutate points[] array that was passed to constructor
          mutate points[] array that was passed to constructor
          collinear.segments()
    - failed on trial 1 of 100

  * equidistant.txt
    - failed after 4 operations involving FastCollinearPoints
    - first and last call to segments() returned different arrays
    - sequence of operations was:
          FastCollinearPoints collinear = new FastCollinearPoints(points);
          collinear.segments()
          mutate array returned by last call to segments()
          collinear.segments()
    - failed on trial 1 of 100

==> FAILED
```
