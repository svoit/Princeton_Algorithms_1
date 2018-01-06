import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * The class with main method performs multiple percolation computation experiments.
 *
 * @author svoit
 */
public class PercolationStats {

    private final double mean;
    private final double stddev;
    private final double confidenceLow;
    private final double confidenceHigh;

    private final double[] experimentScores;

    public PercolationStats(int n, int trials) {

        if (n <= 0) {
            throw new IllegalArgumentException("The grid size must be bigger than zero");
        }

        if (trials <= 0) {
            throw new IllegalArgumentException("The number of experiments must be bigger than zero");
        }

        experimentScores = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);

            int runs = 0;
            while (!percolation.percolates()) {
                int row, col;

                do {
                  col = 1 + StdRandom.uniform(n);
                  row = 1 + StdRandom.uniform(n);
                } while (percolation.isOpen(row, col));

                percolation.open(row, col);
                runs++;
            }

            experimentScores[i] = runs / (double) (n * n);
        }

        // avoiding extra calls
        mean = StdStats.mean(experimentScores);
        stddev = StdStats.stddev(experimentScores);
        double confidenceFraction = (1.96 * stddev()) / Math.sqrt(experimentScores.length);
        confidenceLow = mean - confidenceFraction;
        confidenceHigh = mean + confidenceFraction;
    }

    public double mean() {
        return mean;
    }

    public double stddev() {
        return stddev;
    }

    public double confidenceLow() {
        return confidenceLow;
    }

    public double confidenceHigh() {
        return confidenceHigh;
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStats percolationStats = new PercolationStats(n, t);

        StdOut.printf("mean                    = %f\n", percolationStats.mean());
        StdOut.printf("stddev                  = %f\n", percolationStats.stddev());
        StdOut.println("95% confidence interval = " + percolationStats.confidenceLow() + ", " + percolationStats.confidenceHigh());
    }
}
