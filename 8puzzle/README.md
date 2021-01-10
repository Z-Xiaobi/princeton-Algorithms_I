# 8 Puzzle
Coursework Specification: <a href="https://coursera.cs.princeton.edu/algs4/assignments/8puzzle/faq.php" target="_blank">https://coursera.cs.princeton.edu/algs4/assignments/8puzzle/faq.php</a>

Score: 90 (Dec 30, 2020)

Didn't implement cache.


Didn't find a good way to implement `equal`. I just accessed the current board and another Board's board to do a comparison. So I can't use the `static` keyword, which is not appreciated.
The most important information is manhattan, hamming and twin DO NOT perform calculations and operations on `zero`, `zero` is not a tile in this project!

Basically, A* algorithm in this project is to trace nodes (Board + parent node pointer + moves) and find the goal.
Here, manhattan + moves or hamming + moves is the priority. (Because A* use [future / perdicated consumption from current position to the goal] + 
[the consumption to move to current position] as priority). Once we find the Goal Board from Min Queue, we can trace the parent pointer to review the full solution.

Some failed checks of my code:
```
Test 4: create two Solver objects at the same time
  * puzzle04.txt and puzzle04.txt
  * puzzle00.txt and puzzle04.txt
    - wrong number of moves()
    - student   moves() for puzzle00.txt = 4
    - reference moves() for puzzle00.txt = 0

  * puzzle04.txt and puzzle00.txt
    - wrong number of moves()
    - student   moves() for puzzle04.txt = 0
    - reference moves() for puzzle04.txt = 4

==> FAILED

Test 4b: count Board operations (that should get called),
         rejecting if doesn't adhere to stricter caching limits

               filename    Board()            equals()         manhattan()
--------------------------------------------------------------------------
=> FAILED  puzzle20.txt       2289                2279               19593   (4.9x)
=> FAILED  puzzle22.txt       5549                5543               55223   (5.7x)
=> FAILED  puzzle21.txt       5619                5611               56779   (5.8x)
=> FAILED  puzzle23.txt       8445                8437               84891   (5.7x)
=> FAILED  puzzle24.txt       8683                8673               90319   (5.9x)
=> FAILED  puzzle25.txt      16416               16408              178407   (6.2x)
=> FAILED  puzzle27.txt      17947               17939              196755   (6.3x)
=> FAILED  puzzle29.txt      18711               18703              215665   (6.6x)
=> FAILED  puzzle26.txt      18990               18984              216419   (6.5x)
=> FAILED  puzzle28.txt      43202               43192              556059   (7.4x)
=> FAILED  puzzle30.txt      69148               69142              918633   (7.6x)
=> FAILED  puzzle31.txt      73809               73801              973639   (7.5x)
=> FAILED  puzzle39.txt     106459              106451             1330491   (7.1x)
=> FAILED  puzzle41.txt     166497              166487             2267133   (7.8x)
=> FAILED  puzzle34.txt     224829              224823             3230903   (8.2x)
=> FAILED  puzzle37.txt     246893              246885             3510733   (8.1x)
=> FAILED  puzzle44.txt     398823              398813             5790137   (8.3x)
=> FAILED  puzzle32.txt     771088              771078            12703027   (9.4x)
=> FAILED  puzzle35.txt     785712              785702            12448947   (9.1x)
=> FAILED  puzzle33.txt     921232              921224            15272641   (9.5x)
=> FAILED  puzzle43.txt    1565635             1565627            25775637   (9.4x)
=> FAILED  puzzle46.txt    1549058             1549050            25679235   (9.5x)
=> FAILED  puzzle40.txt    1649907             1649901            27742359   (9.6x)
=> FAILED  puzzle36.txt    3097813             3097803            56218043  (10.4x)
=> FAILED  puzzle45.txt    3607829             3607821            63336317  (10.0x)
```
