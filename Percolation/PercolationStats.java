public class PercolationStats {
    private double[] results; 
    private int sampleSize;
    public PercolationStats(int N, int T) {    // perform T independent computational experiments on an N-by-N grid
        results = new double[T];
        for ( int idx = 0; idx < T; ++idx ) {
            Percolation p = new Percolation(N);
            double cellsFilled=0;
            do {
                int i = StdRandom.uniform(1,N);
                int j = StdRandom.uniform(1,N);
                p.open(i,j);
                ++cellsFilled;
            } while ( !p.percolates() );
            results[idx] = cellsFilled;
        }
        sampleSize = T;
    }
    public double mean() {                     // sample mean of percolation threshold
        double sumTotal = 0;
        for ( int idx = 0; idx < sampleSize; ++idx ) {
            sumTotal += results[idx];
        }
        return sumTotal / sampleSize;
    }
    public double stddev() {                  // sample standard deviation of percolation threshold
        double totalDeviation = 0;
        double meanValue = mean();
        for ( int idx = 0; idx < sampleSize; ++idx ) {
            totalDeviation += Math.abs( results[idx] - meanValue );
        }
        return totalDeviation / sampleSize;
    }
    public double confidenceLo() {            // returns lower bound of the 95% confidence interval
        return mean() - (1.96*stddev());
    }
    public double confidenceHi() {            // returns upper bound of the 95% confidence interval
        return mean() + (1.96*stddev());
    }
    public static void main(String[] args) {  // test client, described below
        
    }
}