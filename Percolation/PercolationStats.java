public class PercolationStats {
    private double m_mean = 0;
    private double m_stddev = 0;
    private double m_conLo = 0;
    private double m_conHi = 0;
    
    public PercolationStats(int N, int T) {    // perform T independent computational experiments on an N-by-N grid
        double[] results = new double[T]; 
        int sampleSize=0;
        
    	if ( N <= 0 || T <= 0 )
    		throw new java.lang.IllegalArgumentException();
        
        int[] availableCells = new int[N*N];
        for ( int idx = 0; idx < T; ++idx ) {
            Percolation p = new Percolation(N);
            int cellsFilled=0;
            int cellsRemaining = N * N;
            for ( int n = 0; n < cellsRemaining; ++n ) {
            	availableCells[n]=n;
            }
            do {
            	int availableIdx = StdRandom.uniform( 0, cellsRemaining );
            	int cellToPopulate = availableCells[ availableIdx ];
            	if ( --cellsRemaining != availableIdx ) {
	            	availableCells[ availableIdx ] = availableCells[ cellsRemaining ];
	            	availableCells[ cellsRemaining ] = cellToPopulate;
            	}
            	int i = (cellToPopulate / N) + 1;
            	int j = (cellToPopulate % N) + 1;
            	assert( p.isOpen(i, j) );
                p.open(i,j);
                ++cellsFilled;
            } while ( !p.percolates() );
            results[idx] = (double)cellsFilled/availableCells.length;
        }
        sampleSize = T;
        m_mean = StdStats.mean(results);
        m_stddev = StdStats.stddev(results);
        m_conLo = mean() - (1.96*stddev()/Math.sqrt(sampleSize));
        m_conHi = mean() + (1.96*stddev()/Math.sqrt(sampleSize));
    }
    public double mean() {                     // sample mean of percolation threshold
        return m_mean;
    }
    public double stddev() {                  // sample standard deviation of percolation threshold
        return m_stddev;
    }
    public double confidenceLo() {            // returns lower bound of the 95% confidence interval
        return m_conLo;
    }
    public double confidenceHi() {            // returns upper bound of the 95% confidence interval
        return m_conHi;
    }
    public static void main(String[] args) {  // test client, described below
        if ( args.length == 2 ) {
        	PercolationStats p = new PercolationStats( Integer.parseInt(args[0]), Integer.parseInt(args[1]) );
        	StdOut.printf("mean                    = %f\n", p.mean());
        	StdOut.printf("stddev                  = %f\n", p.stddev());
        	StdOut.printf("95%% confidence interval = %f, %f\n", p.confidenceLo(), p.confidenceHi());
        }
        else {
        	StdOut.printf("args = %d",  args.length);
        	StdOut.print(args);
        }
    }
}