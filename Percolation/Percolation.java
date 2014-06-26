public class Percolation {
    private QuickFindUF uf;
    private int width;
    private int bottomCell;
    private boolean[][] openCells;
    public Percolation(int N) { // create N-by-N grid, with all sites blocked
        width = N;
        uf = new QuickFindUF(N*(N+2));
        openCells = new boolean[N+2][N];
        
        bottomCell = N*(N+2)-1;
        for ( int n = 1; n < width; n++ ) {
            // open the fake top row
            uf.union(0,n);
            openCells[0][n-1] = true;// Make the top row all open
            // open the fake bottom row
            uf.union(calcIndex(N+1,0),calcIndex(N+1,n));
            openCells[N+1][n-1] = true;
        }
                
    } 
    protected int calcIndex( int i, int j ) {
        return ( (i) * width + (j - 1) );
    }
   public void open(int i, int j)         // open site (row i, column j) if it is not already
   {
       if ( i < 1 || i > width || j < 1 || j > width ) 
           throw new java.lang.IndexOutOfBoundsException();
       
       openCells[i][j-1]=true;
       if ( i > 1 && openCells[i-1][j-1] )
           uf.union(calcIndex(i-1,j),calcIndex(i,j));
       if ( i < width && openCells[i+1][j-1] )
           uf.union(calcIndex(i+1,j),calcIndex(i,j));
       if ( j > 1 && openCells[i][j-1-1] )
           uf.union(calcIndex(i,j-1),calcIndex(i,j));
       if ( j < width && openCells[i][j+1-1] )
           uf.union(calcIndex(i,j+1),calcIndex(i,j));
   }
   public boolean isOpen(int i, int j)    // is site (row i, column j) open?
   {
       return openCells[i][j-1];
   }
   public boolean isFull(int i, int j)    // is site (row i, column j) full?
   {
       return openCells[i][j-1] && uf.connected(0,calcIndex(i,j));
   }
   public boolean percolates()            // does the system percolate?
   {
       return uf.connected(0,bottomCell);
   }
   
   public static void main(String[] args) {  // test client, described below
       Percolation p=new Percolation(2);
       p.open(1,1);
       StdArrayIO.print(p.openCells);
       if ( p.isOpen(1,1) ) {
           p.open(2,1);
           StdArrayIO.print(p.openCells);
           if ( p.isOpen(2,1) ) {
               if ( p.percolates() ) {
               }
           }
       }

       StdArrayIO.print(p.openCells);
   
   }
}