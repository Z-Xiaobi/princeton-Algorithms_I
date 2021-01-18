# Kd Tree
Coursework Specification: <a href="https://coursera.cs.princeton.edu/algs4/assignments/kdtree/specification.php" target="_blank">https://coursera.cs.princeton.edu/algs4/assignments/kdtree/specification.php</a>

Score: 91 (Jan 18, 2021)

**Brute Force**
Just use java.util.TreeSet to store points and traverse the whole set to find the target point(s). O(N)

**2d Tree**
Inner class KdNode includes:
```
Key: Point2D point
KdNode: left, right
Value: level (the level of this node in the tree, start from 0)
```
I use `level` rather than a rectangle because I want to reduce the calls to `RectHV`. However, it causes too much calls to `Point2D`.


When I organize my `range()`, I use the x-coordinate and y-coordiante of the vertices of rectangle to judge the relative position to current KdNode's `point`.
If KdNode `isVertical`, compare the x coordinates of two vertical segments to current point's x-axis, otherwise use y coordinates.


When I organize my `nearest()`, similarly, I use the x and y coordinates to locate relative position between query point and current point. By recursivly checking the two
 subtrees of current KdNode (first check the query point side subtree), update the nearest KdNode. If return values are not `null`, the result will always be the second 
 subtree's result.







Some failed checks of my code:
```
Test 5d: insert general points; check nearest() with random query points
  * 10000 random general points in a 16-by-16 grid
  * 10000 random general points in a 128-by-128 grid
  * 10000 random general points in a 1024-by-1024 grid

    
        ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
        OperationCountLimitExceededException
        Number of calls to methods in Point2D exceeds limit: 1000000000
        ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

==> FAILED

Test 6a: insert points from file; check nearest() with random query points
         and check traversal of kd-tree
  * input5.txt
    - student   nearest() = (0.9, 0.6)
    - reference nearest() = (0.9, 0.6)
    - performs incorrect traversal of kd-tree during call to nearest()
    - query point = (0.94, 0.81)
    - sequence of points inserted: 
      A  0.7 0.2
      B  0.5 0.4
      C  0.2 0.3
      D  0.4 0.7
      E  0.9 0.6
    - student sequence of kd-tree nodes involved in calls to Point2D methods:
      A E B D C 
    - reference sequence of kd-tree nodes involved in calls to Point2D methods:
      A E 
    - failed on trial 1 of 1000

  * input10.txt
    - student   nearest() = (0.862, 0.825)
    - reference nearest() = (0.862, 0.825)
    - performs incorrect traversal of kd-tree during call to nearest()
    - query point = (0.89, 0.9)
    - sequence of points inserted: 
      A  0.372 0.497
      B  0.564 0.413
      C  0.226 0.577
      D  0.144 0.179
      E  0.083 0.51
      F  0.32 0.708
      G  0.417 0.362
      H  0.862 0.825
      I  0.785 0.725
      J  0.499 0.208
    - student sequence of kd-tree nodes involved in calls to Point2D methods:
      A B H I G J C F D E 
    - reference sequence of kd-tree nodes involved in calls to Point2D methods:
      A B H I 
    - failed on trial 1 of 1000

==> FAILED

Test 6b: insert non-degenerate points; check nearest() with random query points
         and check traversal of kd-tree
  * 5 random non-degenerate points in a 8-by-8 grid
    - student   nearest() = (0.25, 0.875)
    - reference nearest() = (0.25, 0.875)
    - performs incorrect traversal of kd-tree during call to nearest()
    - query point = (0.0, 1.0)
    - sequence of points inserted: 
      A  0.625 0.5
      B  0.25 0.875
      C  0.875 0.75
      D  1.0 0.0
      E  0.5 0.125
    - student sequence of kd-tree nodes involved in calls to Point2D methods:
      A B E C D 
    - reference sequence of kd-tree nodes involved in calls to Point2D methods:
      A B E 
    - failed on trial 1 of 1000

  * 10 random non-degenerate points in a 16-by-16 grid
    - student   nearest() = (0.0, 0.25)
    - reference nearest() = (0.0, 0.25)
    - performs incorrect traversal of kd-tree during call to nearest()
    - query point = (0.25, 0.0625)
    - sequence of points inserted: 
      A  0.5 0.375
      B  0.0625 0.625
      C  0.9375 0.75
      D  0.1875 0.8125
      E  0.5625 0.5625
      F  0.0 0.25
      G  0.4375 1.0
      H  0.6875 0.0
      I  1.0 0.9375
      J  0.8125 0.5
    - student sequence of kd-tree nodes involved in calls to Point2D methods:
      A B F D G C E H J I 
    - reference sequence of kd-tree nodes involved in calls to Point2D methods:
      A B F C E 
    - failed on trial 1 of 1000

  * 20 random non-degenerate points in a 32-by-32 grid
    - student   nearest() = (0.71875, 0.84375)
    - reference nearest() = (0.71875, 0.84375)
    - performs incorrect traversal of kd-tree during call to nearest()
    - query point = (0.75, 0.8125)
    - sequence of points inserted: 
      A  0.0625 0.1875
      B  0.28125 0.15625
      C  0.03125 0.53125
      D  0.6875 0.78125
      E  0.78125 0.625
      F  0.625 0.5
      G  0.875 0.46875
      H  0.8125 0.65625
      I  0.71875 0.84375
      J  0.125 0.3125
      K  0.4375 0.9375
      L  0.34375 0.75
      M  0.65625 0.875
      N  0.25 0.59375
      O  0.5625 0.40625
      P  0.1875 0.25
      Q  0.21875 0.28125
      R  0.90625 0.09375
      S  0.46875 0.0625
      T  0.59375 1.0
    - student sequence of kd-tree nodes involved in calls to Point2D methods:
      A B D E H I G F K M T L N J O P Q R S C 
    - reference sequence of kd-tree nodes involved in calls to Point2D methods:
      A B D E H I 
    - failed on trial 1 of 1000

  * 30 random non-degenerate points in a 64-by-64 grid
    - student   nearest() = (0.28125, 0.9375)
    - reference nearest() = (0.28125, 0.9375)
    - performs incorrect traversal of kd-tree during call to nearest()
    - number of student   entries = 30
    - number of reference entries = 10
    - entry 4 of the two sequences are not equal
    - student   entry 4 = (0.140625, 0.234375)
    - reference entry 4 = (0.0, 0.625)

    - failed on trial 1 of 1000

  * 50 random non-degenerate points in a 128-by-128 grid
    - student   nearest() = (0.8359375, 0.96875)
    - reference nearest() = (0.8359375, 0.96875)
    - performs incorrect traversal of kd-tree during call to nearest()
    - number of student   entries = 50
    - number of reference entries = 11
    - failed on trial 1 of 1000

  * 1000 random non-degenerate points in a 2048-by-2048 grid
    - student   nearest() = (0.98876953125, 0.4462890625)
    - reference nearest() = (0.98876953125, 0.4462890625)
    - performs incorrect traversal of kd-tree during call to nearest()
    - number of student   entries = 1000
    - number of reference entries = 22
    - entry 16 of the two sequences are not equal
    - student   entry 16 = (0.94482421875, 0.44921875)
    - reference entry 16 = (0.98583984375, 0.37109375)

    - failed on trial 1 of 1000

==> FAILED

Test 4a-h: Perform nearest() queries after inserting n points into a 2d tree. The table gives
           the average number of calls to methods in RectHV and Point per call to nearest().

                                         Point2D                 RectHV
               n      ops per second     distanceSquaredTo()     distanceSquaredTo()        x()               y()
------------------------------------------------------------------------------------------------------------------------

    
        ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
        OperationCountLimitExceededException
        Number of calls to methods in Point2D exceeds limit: 1000000000
        ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

==> 0/8 tests passed



Total: 20/28 tests passed!


```
