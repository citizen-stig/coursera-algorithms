import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final double mean;
    private final double stdDev;
    private final double confidenceLo;
    private final double confidenceHi;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (trials <= 0) {
            throw new IllegalArgumentException("Number of trials should be more than zero");
        }
        double[] results = new double[trials]; // how many open sites, when percolated;
        double totalOpen = n * n;
        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);
            while (!p.percolates()) {
                p.open(
                        StdRandom.uniform(n) + 1,
                        StdRandom.uniform(n) + 1
                );
            }
            results[i] = (double) p.numberOfOpenSites() / totalOpen;
        }

        this.mean = StdStats.mean(results);
        this.stdDev = StdStats.stddev(results);
        double threshold = 1.96 * stdDev / Math.sqrt(trials);
        this.confidenceLo = mean - threshold;
        this.confidenceHi = mean + threshold;
    }

    // sample mean of percolation threshold
    public double mean() {
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return stdDev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return confidenceLo;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return confidenceHi;
    }

    // test client (see below)
    public static void main(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("Please provide n and T arguments");
        }
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats stat = new PercolationStats(n, trials);
        System.out.println(String.format("mean                    = %f", stat.mean()));
        System.out.println(String.format("stddev                  = %f", stat.stddev()));
        System.out.println(String.format("95%% confidence interval = [%f, %f]", stat.confidenceLo(), stat.confidenceHi()));
    }
}
