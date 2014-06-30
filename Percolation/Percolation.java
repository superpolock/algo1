public class Percolation {
    private QuickFindUF uf;
    private int width;
    private int bottomCell;
    private boolean[] openCells;
    public Percolation(int N) { // create N-by-N grid, with all sites blocked
        width = N;
        uf = new QuickFindUF(N*(N+2));
        bottomCell = N*(N+2)-1;
        openCells = new boolean[bottomCell+1];
        
        openCells[calcIndex(0,1)] = true;
        openCells[calcIndex(N+1,1)] = true;
        for ( int n = 1; n < width; n++ ) {
            // open the fake top row
            uf.union(0,n);
            openCells[calcIndex(0,n)] = true;// Make the top row all open
            openCells[calcIndex(N+1,n+1)] = true;
        }
                
    } 
    private int calcIndex( int i, int j ) {
        return ( (i) * width + (j - 1) );
    }
   public void open(int i, int j)         // open site (row i, column j) if it is not already
   {
       if ( i < 1 || i > width || j < 1 || j > width ) 
           throw new java.lang.IndexOutOfBoundsException();
       
       openCells[calcIndex(i,j)]=true;
       if ( i >= 1 && isOpen(i-1,j) )
           uf.union(calcIndex(i-1,j),calcIndex(i,j));
       if ( i <= width && isOpen(i+1,j) )
           uf.union(calcIndex(i+1,j),calcIndex(i,j));
       if ( j > 1 && isOpen(i,j-1) )
           uf.union(calcIndex(i,j-1),calcIndex(i,j));
       if ( j < width && isOpen(i,j+1) )
           uf.union(calcIndex(i,j+1),calcIndex(i,j));
   }
   public boolean isOpen(int i, int j)    // is site (row i, column j) open?
   {
       return openCells[calcIndex(i,j)];
   }
   public boolean isFull(int i, int j)    // is site (row i, column j) full?
   {
       return openCells[calcIndex(i,j)] && uf.connected(0,calcIndex(i,j));
   }
   public boolean percolates()            // does the system percolate?
   {
	   boolean doesPercolate = false;
	   int n = 1;
	   while ( n <= width && !doesPercolate) {
		   doesPercolate = uf.connected(0, calcIndex(width+1,n++) ); 
	   } ;
       return doesPercolate;
   }
   
   public static void main(String[] args) {  // test client, described below
   
   }
}