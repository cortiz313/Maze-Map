# Maze-Map
This java program will find the shortest path that a hare needs to take to go through a grid-shaped maze using recursion. 
The hare enters the maze from a specific square (start) and leaves the maze at another specific square (target).

The program starts by asking the user to enter the name of a txt file containing the maze map. The file needs to be a tab-delimited rectangular shape table with 
multiple rows and columns. Here is an example of input file and the maze it represents:

S\t N\t R2\t N\t D3
N\t R1,D2\t N\t W4\t N
N\t DE\t N\t N\t N
N\t N\t XR,D2,W2\t D1,R1\t W1
N\t N\t XD\t XR\t N
N\t N\t N\t N\t T

As you see in the example, content of each table cell is a string str that can be interpreted in the following way:
- str.equals(“S”): cell is the start square where hare starts the journey. This cell is always at row 0 and column 0.
- str.equals(“T”): cell is the target square where hare ends the journey. This cell is always at the last row and last column.
- str.equals(“N”): cell has no special property. Hare can move either one square to the right or one square to the bottom of the map.
- str.equals(“DE”): cell is a dead-end which means that if hare enters this cell, there is no way out of it.
- str.contains(“W”): cell is a waiting square. If hare enters this cell, it needs to stay and wait in the cell for a specific
units of time before leaving it. The length of waiting time is determined by the number that comes after ‘W’ (e.g. “W5” means that hare needs to stay and 
wait for 5 consequent steps before moving out of the cell).
- str.contains(“XR”): cell is a no-right square! If hare enters this cell, it can’t exit by moving to the right square.
- !str.contains(“XR”) && str.contains(“R”): cell has a ladder on it! If hare enters this cell, it can either move normally (one step to the neighboring squares) or 
use the ladder to move multiple squares to the right. The length of ladder comes after the letter ‘R’ (e.g. “R3” means that there is a horizontal ladder of length 3).
- str.contains(“XD”): cell is a no-down square! If hare enters this cell, it can’t exit by moving down.
- !str.contains(“XD”) && str.contains(“D”): cell has a ladder on it! If hare enters this cell, it can either move normally (one step to the neighboring squares) or 
use the ladder to move down multiple squares. The length of ladder comes after the letter ‘D’ (e.g. “D4” means that there is a vertical ladder of length 4, 
while “R3,D4” means that there is a ladder that moves hare 3 cells to right and 4 cells to the bottom of the maze).
