# Percolation
Coursework Specification: <a href="https://coursera.cs.princeton.edu/algs4/assignments/percolation/specification.php" target="_blank">https://coursera.cs.princeton.edu/algs4/assignments/percolation/specification.php</a>


The biggest improvement is change size of **WeightedQuickUnionUF** from `n*n` to `n*n+2`. In this case, I don't have to use nested `for-loops` (two `for` loops) to retrieve all connection relationships between current node and upside nodes (first line nodes) or between upside nodes and downside nodes (second line nodes).
That is, `isFull` and `percolates` in **Percolation.java** will save more resources. 


Basically, I use two extra nodes (as you may noticed my declaration of size of **WeightedQuickUnionUF**). One is the root of the first line nodes, another is the root of the last line nodes.
Therefore, I can check `isFull` by checking whether current node and the first line root are connected (`WeightedQuickUnionUF.find(up_root) == WeightedQuickUnionUF.find(current_open_node)`).
Similarly, `percolates` just need to check two roots are connected (`WeightedQuickUnionUF.find(up_root) == WeightedQuickUnionUF.find(down_root)`).

I defined the roots as the last two nodes in array. You can choose other indexes as you like.


Some Failed Checks of my code
```
Test 18: check for backwash with predetermined sites
  * filename = input20.txt
    - isFull() returns wrong value after 231 sites opened
    - student   isFull(18, 1) = true
    - reference isFull(18, 1) = false

  * filename = input10.txt
    - isFull() returns wrong value after 56 sites opened
    - student   isFull(9, 1) = true
    - reference isFull(9, 1) = false

  * filename = input50.txt
    - isFull() returns wrong value after 1412 sites opened
    - student   isFull(22, 28) = true
    - reference isFull(22, 28) = false

  * filename = jerry47.txt
    - isFull() returns wrong value after 1076 sites opened
    - student   isFull(11, 47) = true
    - reference isFull(11, 47) = false

  * filename = sedgewick60.txt
    - isFull() returns wrong value after 1577 sites opened
    - student   isFull(21, 59) = true
    - reference isFull(21, 59) = false

  * filename = wayne98.txt
    - isFull() returns wrong value after 3851 sites opened
    - student   isFull(69, 9) = true
    - reference isFull(69, 9) = false

==> FAILED

Test 19: check for backwash with predetermined sites that have
         multiple percolating paths
  * filename = input3.txt
    - isFull() returns wrong value after 4 sites opened
    - student   isFull(3, 1) = true
    - reference isFull(3, 1) = false

  * filename = input4.txt
    - isFull() returns wrong value after 7 sites opened
    - student   isFull(4, 4) = true
    - reference isFull(4, 4) = false

  * filename = input7.txt
    - isFull() returns wrong value after 12 sites opened
    - student   isFull(6, 1) = true
    - reference isFull(6, 1) = false

==> FAILED

Test 16: check distribution of number of sites opened until percolation
  * n = 2, trials = 100000
  * n = 3, trials = 100000
  * n = 4, trials = 100000

    
        ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
        OperationCountLimitExceededException
        Number of calls to methods in StdStats exceeds limit: 1000000
        ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

==> FAILED

Test 17: check that each site is opened the expected number of times
  * n = 2, trials = 100000
  * n = 3, trials = 100000
  * n = 4, trials = 100000

    
        ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
        OperationCountLimitExceededException
        Number of calls to methods in StdStats exceeds limit: 1000000
        ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

==> FAILED
```
