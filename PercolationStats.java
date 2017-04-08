import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;

 public class PercolationStats
 {
     private Percolation percolation;
     private double[] trialsThresholds;
     private int mtrials;
     
     public PercolationStats(int n, int trials)
     {
         if (n <= 0 || trials <= 0) 
             throw new java.lang.IllegalArgumentException();
         
         trialsThresholds = new double[trials];
         mtrials = trials;
         while (trials != 0)
         {
             percolation = new Percolation(n);
             while (!percolation.percolates())
             {
                 int randomRow = StdRandom.uniform(1, n+1);
                 int randomCol = StdRandom.uniform(1, n+1);
                 
                 if (!percolation.isOpen(randomRow, randomCol))
                     percolation.open(randomRow, randomCol);
             }
             trialsThresholds[mtrials - trials] = percolationThreshold(n);
             trials--;
         }
         
     }
     
     private double percolationThreshold(int n)
     {
         int totalSites = n*n;
         int openSites = percolation.numberOfOpenSites();
         double threshold = (double) openSites/totalSites;
         
         return threshold;
     }
     
     public double mean()
     {
         return StdStats.mean(trialsThresholds);
     }
     
     public double stddev()
     {
         return StdStats.stddev(trialsThresholds);
     }
     
     public double confidenceLo()
     {
         double common = 1.96*stddev()/Math.sqrt(mtrials);
         double confLo = mean() - common;
         return confLo;
     }
     
     public double confidenceHi()
     {
         double common = 1.96*stddev()/Math.sqrt(mtrials);
         double confLo = mean() + common;
         return confLo;
     }
     
     public static void main(String[] args)
     {
         if (args.length < 2)
             throw new java.lang.IllegalArgumentException();
         
         int n = Integer.parseInt(args[0]);
         int t = Integer.parseInt(args[1]);
         
         PercolationStats percStats = new PercolationStats(n, t);
         
         double mean = percStats.mean();
         double dev = percStats.stddev();
         double confLo = percStats.confidenceLo();
         double confHi = percStats.confidenceHi();
         
         StdOut.println(mean);
         StdOut.println(dev);
         StdOut.println(confLo);
         StdOut.println(confHi);
         
     }
     
}