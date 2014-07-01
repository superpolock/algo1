public class PercolationStats {
    private int[] results; 
    private int sampleSize;
    public PercolationStats(int N, int T) {    // perform T independent computational experiments on an N-by-N grid
        results = new int[T];
        for ( int idx = 0; idx < T; ++idx ) {
            Percolation p = new Percolation(N);
            int cellsFilled=0;
            int attempts = 0;
            do {
            	int i;
            	int j;
            	do {
                	i = StdRandom.uniform(1,N);
                	j = StdRandom.uniform(1,N);
                	++attempts;
            	} while ( p.isOpen(i, j) && attempts < N*N );
                p.open(i,j);
                ++cellsFilled;
            } while ( !p.percolates() && attempts < 2*N );
            results[idx] = cellsFilled;
        }
        sampleSize = T;
    }
    public double mean() {                     // sample mean of percolation threshold
        return StdStats.mean(results);
    }
    public double stddev() {                  // sample standard deviation of percolation threshold
        return StdStats.stddev(results);
    }
    public double confidenceLo() {            // returns lower bound of the 95% confidence interval
        return mean() - (1.96*stddev()/Math.sqrt(sampleSize));
    }
    public double confidenceHi() {            // returns upper bound of the 95% confidence interval
        return mean() + (1.96*stddev()/Math.sqrt(sampleSize));
    }
    public static void main(String[] args) {  // test client, described below
        if ( args.length == 3 ) {
        	PercolationStats p = new PercolationStats( Integer.parseInt(args[1]), Integer.parseInt(args[2]) );
        	StdOut.printf("mean \t= %f\n", p.mean());
        	StdOut.printf("stddev \t= %f\n", p.stddev());
        	StdOut.printf("95% confidence interval = %f, %f",p.confidenceLo(), p.confidenceHi());
        }
    }
}