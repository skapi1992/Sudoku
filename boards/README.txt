How to create board template?
The board is 9x9. Everything is in JSON format.

If you want to create  for example starting board like below
- - - - - - - - - - - -
9 5 2 | . . . | . . 1 |
. 4 . | . 2 . | . . . |
. . . | 7 . 3 | 4 . . |
- - - - - - - - - - - -
. . . | . . . | . . 9 |
. 2 9 | 3 . 8 | 5 1 . |
6 . . | . . . | . . 9 |
- - - - - - - - - - - -
. . 8 | 1 . 6 | . . . |
. . . | . 7 . | . 8 . |
5 . . | . . . | 7 3 6 |
- - - - - - - - - - - -

Your file should look like this:
[[9,5,2,0,0,0,0,0,1],[0,4,0,0,2,0,0,0,0],[0,0,0,7,0,3,4,0,0],[0,0,0,0,0,0,0,0,9],[0,2,9,3,0,8,5,1,0],[6,0,0,0,0,0,0,0,0],[0,0,8,1,0,6,0,0,0],[0,0,0,0,7,0,0,8,0],[5,0,0,0,0,0,7,3,6]]

Like you see above we need to write row by row.
If we want to have 2 boards or more we need to add another board in new line in file.

The file directory and name: boards/boards.txt

Enjoy!