// Implements a square cell which tracks open/closed 
//  cells and determines if cell "percolates"
public class Percolation {
	// Stores which cells are joined
	private WeightedQuickUnionUF uf;
	private WeightedQuickUnionUF percTester;
	// Number of rows/cols
	private int width;
	// Right bottom most cell, to determine if top percolates to bottom
	private int bottomCell;
	// Stores whether or not the cell is open
	private boolean[] openCells;
	private boolean m_doesPercolate;

	// // create N-by-N grid, with all sites blocked
	public Percolation(final int N) {
		if ( N <= 0 ) {
			throw new java.lang.IllegalArgumentException();
		}
		width = N;
		uf = new WeightedQuickUnionUF(N * (N + 2));
		percTester = new WeightedQuickUnionUF(N * (N + 2));
		bottomCell = N * (N + 2) - 1;
		openCells = new boolean[bottomCell + 1];

		for (int n = 1; n < width; n++) {
			// open the fake top row
			uf.union(0, n);
			// We have a duplicate UF with a fake bottom row to quickly test if we percolate
			percTester.union(0,n);
			percTester.union(bottomCell, bottomCell-n);
		}
		for (int n = 0; n < width; n++) {
			openCells[n] = true;
			openCells[openCells.length-n-1] = true;
		}
		
		m_doesPercolate = false;

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
		int localIdx = calcIndex( i, j );
		
		if ( !openCells[localIdx] ) {
			openCells[calcIndex(i, j)] = true;
			if (i > 1 ) {
				if (isOpen(i - 1, j)) {
					uf.union(calcIndex(i - 1, j), calcIndex(i, j));
					percTester.union(calcIndex(i - 1, j), calcIndex(i, j));
				}
			}
			else if ( i == 1 ) {
				uf.union(calcIndex(i,j)-width, calcIndex(i,j));
				percTester.union(calcIndex(i,j)-width, calcIndex(i,j));
			}
			if (i < width && isOpen(i + 1, j)) {
				uf.union(calcIndex(i + 1, j), calcIndex(i, j));
				percTester.union(calcIndex(i + 1, j), calcIndex(i, j));
			}
			else if ( i == width ) {
				uf.union(calcIndex(i, j)+width, calcIndex(i, j));
				percTester.union(calcIndex(i, j)+width, calcIndex(i, j));
			}
			if (j > 1 ) {
				if ( isOpen(i, j - 1) ) {
					uf.union(calcIndex(i, j - 1), calcIndex(i, j));
					percTester.union(calcIndex(i, j - 1), calcIndex(i, j));
				}
			}
			if (j < width && isOpen(i, j + 1)) {
				uf.union(calcIndex(i, j + 1), calcIndex(i, j));
				percTester.union(calcIndex(i, j + 1), calcIndex(i, j));
			}
			
/*			if ( !m_doesPercolate && uf.connected(0,localIdx) ) {
				int n = 0;
				do {
					m_doesPercolate = uf.connected( localIdx, this.bottomCell - n );
				} while ( !m_doesPercolate && ++n < width ); 
			}*/
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
		return percTester.find(0) == percTester.find(bottomCell);
	}

	public static void main(final String[] args) { // test client, described
													// below

	}
}
