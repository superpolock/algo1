// Implements a square cell which tracks open/closed 
//  cells and determines if cell "percolates"
public class Percolation {
	// Stores which cells are joined
	private WeightedQuickUnionUF uf;
	// Number of rows/cols
	private int width;
	// Right bottom most cell, to determine if top percolates to bottom
	private int bottomCell;
	// Stores whether or not the cell is open
	private boolean[] openCells;

	// // create N-by-N grid, with all sites blocked
	public Percolation(final int N) {
		if ( N <= 0 ) {
			throw new java.lang.IllegalArgumentException();
		}
		width = N;
		uf = new WeightedQuickUnionUF(N * (N + 2));
		bottomCell = N * (N + 2) - 1;
		openCells = new boolean[bottomCell + 1];

		for (int n = 1; n < width; n++) {
			// open the fake top row
			uf.union(0, n);
		}
		for (int n = 0; n < width; n++) {
			openCells[n] = true;
			openCells[openCells.length-n-1] = true;
		}

	}

	// Internally used to generate offset from row/col into single index array
	private int calcIndex(final int i, final int j) {
		
		return ((i) * width + (j - 1));
	}
	
	private void validateRowCol( int i, int j) {
		if ( i < 1 || i > width || j < 1 || j > width )
			throw new java.lang.IndexOutOfBoundsException();
	}

	// open site (row i, column j) if it is not already
	public void open(final int i, final int j) {
		validateRowCol(i, j);
		openCells[calcIndex(i, j)] = true;
		if ( i == 1 ) {
			uf.union(calcIndex(i,j)-width, calcIndex(i,j));
		}
		if (i > 1 && isOpen(i - 1, j)) {
			uf.union(calcIndex(i - 1, j), calcIndex(i, j));
		}
		if (i < width && isOpen(i + 1, j)) {
			uf.union(calcIndex(i + 1, j), calcIndex(i, j));
		}
		if ( i == width ) {
			uf.union(calcIndex(i, j)+width, calcIndex(i, j));
		}
		if (j > 1 && isOpen(i, j - 1)) {
			uf.union(calcIndex(i, j - 1), calcIndex(i, j));
		}
		if (j < width && isOpen(i, j + 1)) {
			uf.union(calcIndex(i, j + 1), calcIndex(i, j));
		}
	}

	// is site (row i,column j) open?
	public boolean isOpen(final int i, final int j) {
		validateRowCol(i, j);
		return openCells[calcIndex(i, j)];
	}

	// is site (row i,column j) full?
	public boolean isFull(final int i, final int j) {
		validateRowCol(i, j);
		return (openCells[calcIndex(i, j)] && uf.connected(0, calcIndex(i, j)));
	}

	// Is there a path of "open" cells from top to bottom?
	public boolean percolates() { 
		boolean doesPercolate = false;
		int n = 1;
		while (n <= width && !doesPercolate) {
			doesPercolate = uf.connected(0, calcIndex(width + 1, n++));
		}
		return doesPercolate;
	}

	public static void main(final String[] args) { // test client, described
													// below

	}
}
