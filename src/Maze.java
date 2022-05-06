// Christian Ortiz
import java.io.File;
import java.util.*;

public class Maze {
    class Cell {
	public boolean deadend = false, noRight = false, noLeft = false, noDown = false, noUp = false, start = false,
		target = false;
	public int horizontalJump = 0;
	public int verticalJump = 0;
	public int wait = 0;
	public int shortest = -1;// not yet discovered
	public Cell nextStep;
	public int row, col;

	Cell(int row, int col) {
	    this.row = row;
	    this.col = col;
	}

	Cell(int row, int col, int wait) {
	    this.row = row;
	    this.col = col;
	    this.wait = wait;
	}

	public String toString() {
	    if (wait == 0)
		return "(" + row + ", " + col + ")";
	    return new Cell(row, col, wait - 1).toString() + "->(" + row + ", " + col + ")";
	}
    }

    private int rows, columns;
    private Cell[][] map;

    public int getRows() {
	return rows;
    }

    public int getColumns() {
	return columns;
    }

    public Maze(int rows, int columns) {
	this.rows = rows;
	this.columns = columns;
	map = new Cell[rows][columns];
	for (int r = 0; r < rows; r++)
	    for (int c = 0; c < columns; c++)
		map[r][c] = new Cell(r, c);
    }

    private Cell getStart() {
	for (int r = 0; r < rows; r++)
	    for (int c = 0; c < columns; c++)
		if (map[r][c].start)
		    return map[r][c];
	return null;
    }

    private Cell getTarget() {
	for (int r = 0; r < rows; r++)
	    for (int c = 0; c < columns; c++)
		if (map[r][c].target)
		    return map[r][c];
	return null;
    }

    public void solve() {
	// Step 1: find the start; it should be the top-left square if you don't do the
	// extra credit.
	Cell start = getStart(), target = getTarget();
	// Step 2: call the recursive helper function
	solveHelper(start, target);
    }


    private void solveHelper(Cell source, Cell target) {
		if (source.row == target.row && source.col == target.col)// base case
			return;

			if (source.row < rows - 1 && map[source.row + 1][source.col].shortest == -1)// down cell is not discovered yet!
				solveHelper(map[source.row + 1][source.col], target);// call it recursively for bottom neighbor

			if (source.col < columns - 1 && map[source.row][source.col + 1].shortest == -1)// right cell is not discovered yet!
				solveHelper(map[source.row][source.col + 1], target);// call it recursively for right neighbor
		
			if(source.deadend) // deadend
				source.shortest = Integer.MAX_VALUE;

			else if(source.horizontalJump + source.verticalJump > 0) // for ladders
			{
				source.shortest = map[source.verticalJump + source.row][source.horizontalJump + source.col].shortest + 1 + source.wait; // added waits throughout code
				source.nextStep = map[source.verticalJump + source.row][source.horizontalJump + source.col];

			}
			else if (source.noDown) // no down
			{
				if(source.col != this.columns - 1) // if you can still go right
				{
					source.shortest = map[source.row][source.col + 1].shortest + 1 + source.wait;
					source.nextStep = map[source.row][source.col + 1];
				}
				else
				{
					source.shortest = Integer.MAX_VALUE; // you cannot go down or right, deadend
				}
			}
			else if (source.noRight) // no right
			{
				if(source.row != this.rows - 1) // if you can still go down
				{
					source.shortest = map[source.row + 1][source.col].shortest + 1 + source.wait;
					source.nextStep = map[source.row + 1][source.col];
				}
				else
				{
					source.shortest = Integer.MAX_VALUE; // you cannot go right or down, deadend
				}
			}

			else if (source.row == rows - 1) {
				source.shortest = map[source.row][source.col + 1].shortest + 1 + source.wait;
				source.nextStep = map[source.row][source.col + 1];
			}
			else if (source.col == columns - 1) {
				source.shortest = map[source.row + 1][source.col].shortest + 1 + source.wait;
				source.nextStep = map[source.row + 1][source.col];
			}
			else {
				Cell rightNeighbor = map[source.row][source.col + 1], bottomNeighbor = map[source.row + 1][source.col];
				source.shortest = Math.min(bottomNeighbor.shortest, rightNeighbor.shortest) + 1 + source.wait;
				if (source.shortest == bottomNeighbor.shortest + 1)
					source.nextStep = bottomNeighbor;
				else
					source.nextStep = rightNeighbor;

			}

    }

    public void addTarget(int rowIndex, int columnIndex) {
	map[rowIndex][columnIndex].target = true;
	map[rowIndex][columnIndex].shortest = 0;

    }

    public void addStart(int rowIndex, int columnIndex) {
	map[rowIndex][columnIndex].start = true;
    }

    public void addDeadEnd(int rowIndex, int columnIndex) {
	map[rowIndex][columnIndex].deadend = true;
    }

    public void addNoDown(int rowIndex, int columnIndex) {
	map[rowIndex][columnIndex].noDown = true;
    }

    public void addNoUp(int rowIndex, int columnIndex) {
	map[rowIndex][columnIndex].noUp = true;
    }

    public void addNoLeft(int rowIndex, int columnIndex) {
	map[rowIndex][columnIndex].noLeft = true;
    }

    public void addVerticalJump(int rowIndex, int columnIndex, int length) {
	map[rowIndex][columnIndex].verticalJump = length;
    }

    public void addNoRight(int rowIndex, int columnIndex) {
	map[rowIndex][columnIndex].noRight = true;
    }

    public void addHorizontalJump(int rowIndex, int columnIndex, int length) {
	map[rowIndex][columnIndex].horizontalJump = length;
    }

    public void addDelay(int rowIndex, int columnIndex, int length) {
	map[rowIndex][columnIndex].wait = length;
    }

    public void printSolution() {
	Cell source = getStart();
	System.out.println("Shortest path of length " + source.shortest + ": ");
	Cell current = source;
	while (current != null) {
	    System.out.print(current + (current.nextStep == null ? "" : "->"));
	    current = current.nextStep;
	}

    }
}
