# Deques and Randomdized Queue

Course Spercification: <a href="https://coursera.cs.princeton.edu/algs4/assignments/queues/specification.php" target="_blank">https://coursera.cs.princeton.edu/algs4/assignments/queues/specification.php</a>

Score: 91 (Dec 30, 2020)

Deque is implemented by a data structure which is slightly simialr to linked list. I just define additional variable to store the pointer that refers to the previous node.
Randomized queue is implemented by an array. In Java, we can't initialize an array without a defined size. So I give a size `50`. When enqueing new items, we can just
check out the current number of items and resize (enlarge) the array. As introduced in the previous lecture, double the size is a good choice to save resources. 
This code don't consider shrinking the array after some operations of deque. You can add it by your own ). 

The problem is randomness, I didn't find an efficient way to give high randomness on an array. My implementation of Randomized Queue is not random. When increase the test set, 
there are many identical results. Besides, as I used shuffle on array of [0,n), the running time is linear, which does not satisfy the running time requirement.


Some Failed Checks of My Code:
```
Test 18: check randomness of iterator() by enqueueing n items, iterating over those
         n items, and seeing whether each of the n! permutations is equally likely
  * n = 2, trials = 12000

            value  observed  expected   2*O*ln(O/E)
        -------------------------------------------
               AB         0    6000.0          0.00
               BA     12000    6000.0      16635.53
        -------------------------------------------
                      12000   12000.0      16635.53
    
    G-statistic = 16635.53 (p-value = 0.000000, reject if p-value <= 0.0001)
    Note: a correct solution will fail this test by bad luck 1 time in 10,000.

  * n = 3, trials = 12000

            value  observed  expected   2*O*ln(O/E)
        -------------------------------------------
              ABC         0    2000.0          0.00
              ACB         0    2000.0          0.00
              BAC         0    2000.0          0.00
              BCA         0    2000.0          0.00
              CAB         0    2000.0          0.00
              CBA     12000    2000.0      43002.23
        -------------------------------------------
                      12000   12000.0      43002.23
    
    G-statistic = 43002.23 (p-value = 0.000000, reject if p-value <= 0.0001)
    Note: a correct solution will fail this test by bad luck 1 time in 10,000.

  * n = 4, trials = 12000

            value  observed  expected   2*O*ln(O/E)
        -------------------------------------------
             ABCD         0     500.0          0.00
             ABDC         0     500.0          0.00
             ACBD         0     500.0          0.00
             ACDB         0     500.0          0.00
             ADBC         0     500.0          0.00
             ADCB         0     500.0          0.00
             BACD         0     500.0          0.00
             BADC         0     500.0          0.00
             BCAD         0     500.0          0.00
             BCDA         0     500.0          0.00
             BDAC         0     500.0          0.00
             BDCA         0     500.0          0.00
             CABD         0     500.0          0.00
             CADB         0     500.0          0.00
             CBAD         0     500.0          0.00
             CBDA         0     500.0          0.00
             CDAB         0     500.0          0.00
             CDBA         0     500.0          0.00
             DABC         0     500.0          0.00
             DACB         0     500.0          0.00
             DBAC         0     500.0          0.00
             DBCA         0     500.0          0.00
             DCAB         0     500.0          0.00
             DCBA     12000     500.0      76273.29
        -------------------------------------------
                      12000   12000.0      76273.29
    
    G-statistic = 76273.29 (p-value = 0.000000, reject if p-value <= 0.0001)
    Note: a correct solution will fail this test by bad luck 1 time in 10,000.

  * n = 5, trials = 12000

==> FAILED

Test 3 (bonus): check that maximum size of any or Deque or RandomizedQueue object
                created is equal to k
  * filename = tale.txt, n = 138653, k = 5
    - max size of RandomizedQueue object = 138653

  * filename = tale.txt, n = 138653, k = 50
    - max size of RandomizedQueue object = 138653

  * filename = tale.txt, n = 138653, k = 500
    - max size of RandomizedQueue object = 138653

  * filename = tale.txt, n = 138653, k = 5000
    - max size of RandomizedQueue object = 138653

  * filename = tale.txt, n = 138653, k = 50000
    - max size of RandomizedQueue object = 138653

==> FAILED

Test 5a-5i: Total memory usage after inserting n items,
            and then deleting all but one item.

                 n        bytes
----------------------------------------------------------
=> FAILED       32          456   (1.9x)
=> FAILED       64          856   (3.6x)
=> FAILED      128         1656   (6.9x)
=> FAILED      256         3256  (13.6x)
=> FAILED      512         6456  (26.9x)
=> FAILED     1024        12856  (53.6x)
=> FAILED     2048        25656 (106.9x)
=> FAILED     4096        51256 (213.6x)
=> FAILED     8192       102456 (426.9x)
==> 0/9 tests passed


Test 10: Total memory usage after inserting 4096 items, then successively
         deleting items, seeking values of n where memory usage is maximized
         as a function of n

                 n        bytes
----------------------------------------------------------
=> passed     3200        51256         
=> passed     1600        51256         
=> FAILED      800        51256   (1.3x)
=> FAILED      400        51256   (2.6x)
=> FAILED      200        51256   (5.2x)
=> FAILED      100        51256  (10.3x)
=> FAILED       50        51256  (19.8x)
==> 2/7 tests passed


Test 1: make n calls to enqueue() followed by n calls to dequeue();
        count calls to StdRandom
  * n = 10
    - dequeue() should call StdRandom at most once
    - number of elementary StdRandom operations = 10
    - failed enqueueing item 1 of 10

  * n = 100
    - dequeue() should call StdRandom at most once
    - number of elementary StdRandom operations = 100
    - failed enqueueing item 1 of 100

  * n = 1000
    - dequeue() should call StdRandom at most once
    - number of elementary StdRandom operations = 1000
    - failed enqueueing item 1 of 1000

==> FAILED

Test 2: make n calls to enqueue() follwed by n calls to sample();
        count calls to StdRandom
  * n = 10
    - sample() should call StdRandom at most once
    - number of elementary StdRandom operations = 11
    - failed sampling item 1 of 10

    - sample() should call StdRandom() at most once per item
    - number of items                             = 10
    - number of elementary StdRandom() operations = 11

  * n = 100
    - sample() should call StdRandom at most once
    - number of elementary StdRandom operations = 101
    - failed sampling item 1 of 100

    - sample() should call StdRandom() at most once per item
    - number of items                             = 100
    - number of elementary StdRandom() operations = 101

  * n = 1000
    - sample() should call StdRandom at most once
    - number of elementary StdRandom operations = 1001
    - failed sampling item 1 of 1000

    - sample() should call StdRandom() at most once per item
    - number of items                             = 1000
    - number of elementary StdRandom() operations = 1001

==> FAILED

Test 4a-k: make n calls to enqueue() followed by n calls to dequeue()

                    n  seconds
----------------------------------
=> passed        1024     0.01
=> passed        2048     0.03
=> passed        4096     0.13
=> passed        8192     0.49

    
        ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
        OperationCountLimitExceededException
        Number of calls to methods in StdRandom exceeds limit: 100000000
        ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

=> FAILED       16384   [ Test did not complete due to an exception. ]

==> 4/11 tests passed

```
