import java.io.IOException;
import java.util.ArrayList;

public class SteepestDescent {
    private double eps;
    private int maxIter;
    private double stepSize;
    private double x0;
    private ArrayList<double[]> bestPoint;
    private double[] bestObjVal;
    private double[] bestGradNorm;
    private long[] compTime;
    private int[] nIter;
    private boolean resultsExist;

    public SteepestDescent() {
        eps = 0.001;
        maxIter = 100;
        stepSize = 0.05;
        x0 = 1;

        bestPoint = null;
        bestObjVal = null;
        bestGradNorm = null;
        compTime = null;
        nIter = null;
        resultsExist = false;
    }

    public SteepestDescent(double eps, int maxIter, double stepSize, double x0) {
        this.eps = eps;
        this.maxIter = maxIter;
        this.stepSize = stepSize;
        this.x0 = x0;

        bestPoint = null;
        bestObjVal = null;
        bestGradNorm = null;
        compTime = null;
        nIter = null;
        resultsExist = false;
    }

    public double getEps() {
        return eps;
    }

    public int getMaxIter() {
        return maxIter;
    }

    public double getStepSize() {
        return stepSize;
    }

    public double getX0() {
        return x0;
    }

    public double[] getBestObjVal() {
        return bestObjVal;
    }

    public double[] getBestGradNorm() {
        return bestGradNorm;
    }

    public double[] getBestPoint(int i) {
        return bestPoint.get(i);
    }

    public int[] getNIter() {
        return nIter;
    }

    public long[] getCompTime() {
        return compTime;
    }

    public boolean hasResults() {
        return resultsExist;
    }

    public void setEps(double a) {
        eps = a;
    }

    public void setMaxIter(int a) {
        maxIter = a;
    }

    public void setStepSize(double a) {
        stepSize = a;
    }

    public void setX0(double a) {
        x0 = a;
    }

    public void setBestObjVal(int i, double a) {
        bestObjVal[i] = a;
    }

    public void setBestGradNorm(int i, double a) {
        bestGradNorm[i] = a;
    }

    public void setBestPoint(int i, double[] a) {
        bestPoint.set(i, a);
    }

    public void setCompTime(int i, long a) {
        compTime[i] = a;
    }

    public void setNIter(int i, int a) {
        nIter[i] = a;
    }

    public void setHasResults(boolean a) {
        resultsExist = a;
    }

    public void init(ArrayList<Polynomial> P) {
        bestPoint = new ArrayList<double[]>();
        for (int i = 0; i < P.size(); i++) {
            bestPoint.add(new double[P.get(i).getN()]);
        }
        bestObjVal = new double[P.size()];
        bestGradNorm = new double[P.size()];
        compTime = new long[P.size()];
        nIter = new int[P.size()];
        resultsExist = false;
    }

    public void run(int i, Polynomial P) {
        if (!P.isSet()) {
            System.out.println("Polynomial not set!");
            return;
        }

        long startTime = System.currentTimeMillis();
        int n = (int) P.getN();
        double[] point = new double[n];
        for (int j = 0; j < n; j++) {
            point[j] = x0;
        }

        bestObjVal[i] = P.f(point);
        bestGradNorm[i] = P.gradientNorm(point);
        nIter[i] = 0;
        compTime[i] = System.currentTimeMillis() - startTime;

        while (P.gradientNorm(point) > eps && nIter[i] < maxIter) {
            double[] direction = this.direction(P, point);
            for (int j = 0; j < n; j++) {
                point[j] += stepSize * direction[j];
            }

            bestObjVal[i] = P.f(point);
            bestGradNorm[i] = P.gradientNorm(point);
            nIter[i]++;
            compTime[i] = System.currentTimeMillis() - startTime;
        }

        bestPoint.set(i, point);

        System.out.printf("Polynomial %d done in %dms.\n", i + 1, compTime[i]);

        resultsExist = true;
    }

    public double lineSearch() {
        return stepSize;
    }

    public double[] direction(Polynomial P, double[] x) {
        int n = (int) P.getN();
        double[] grad = P.gradient(x);
        double[] d = new double[n];
        for (int j = 0; j < n; j++) {
            d[j] = -grad[j];
        }
        return d;
    }

    public void getParamsUser() {
        try {
            String a;

            double eps;
            while (true) {
                try {
                    System.out.print("Enter tolerance epsilon (0 to cancel): ");
                    a = Pro4_cakirog3.in.readLine();
                    if (a.equals("0")) {
                        System.out.println("\nProcess canceled. No changes made to algorithm parameters.");
                        return;
                    }
                    eps = Double.parseDouble(a);
                    if (eps < 0) {
                        throw new NumberFormatException();
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("ERROR: Input must be a real number in [0.00, infinity]!\n");
                }
            }

            int maxIter;
            while (true) {
                try {
                    System.out.print("Enter maximum number of iterations (0 to cancel): ");
                    a = Pro4_cakirog3.in.readLine();
                    if (a.equals("0")) {
                        System.out.println("\nProcess canceled. No changes made to algorithm parameters.");
                    }
                    maxIter = Integer.parseInt(a);
                    if (maxIter < 0 || maxIter > 10000) {
                        throw new NumberFormatException();
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("ERROR: Input must be an integer in [0, 10000]!\n");
                }
            }

            double stepSize;
            while (true) {
                try {
                    System.out.print("Enter step size alpha (0 to cancel): ");
                    a = Pro4_cakirog3.in.readLine();
                    if (a.equals("0")) {
                        System.out.println("\nProcess canceled. No changes made to algorithm parameters.");
                        return;
                    }
                    stepSize = Double.parseDouble(a);
                    if (stepSize < 0) {
                        throw new NumberFormatException();
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("ERROR: Input must be a real number in [0.00, infinity]!\n");
                }
            }

            double x0;
            while (true) {
                try {
                    System.out.print("Enter value for starting point (0 to cancel): ");
                    a = Pro4_cakirog3.in.readLine();
                    if (a.equals("0")) {
                        System.out.println("\nProcess canceled. No changes made to algorithm parameters.");
                        return;
                    }
                    x0 = Double.parseDouble(a);
                    if (x0 < 0) {
                        throw new NumberFormatException();
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("ERROR: Input must be a real number in [0.00, infinity]!\n");
                }
            }

            this.eps = eps;
            this.maxIter = maxIter;
            this.stepSize = stepSize;
            this.x0 = x0;
        } catch (IOException e) {
            System.out.println("Error reading input.");
        }

        System.out.println("\nAlgorithm parameters set.");
    }

    public void print() {
        System.out.printf("Tolerance (epsilon): %f\n", eps);
        System.out.printf("Maximum iterations: %d\n", maxIter);
        System.out.printf("Step size (alpha): %f\n", stepSize);
        System.out.printf("Starting point (x0): %f\n", x0);
    }

    private static String[] printStatsRow(String name, double norm, double iter, double ms, boolean printHeader) {
        String[] values = {
            name,
            String.format("%f", norm),
            String.format("%f", iter),
            String.format("%f", ms)
        };
        return values;
    }

    private static String[] printPolynomialResult(int idx, double result, double norm, long iter, long ms, String bestPointStr) {
        String[] values = {
            String.format("%d", idx),
            String.format("%f", result),
            String.format("%f", norm),
            String.format("%d", iter),
            String.format("%d", ms),
            bestPointStr
        };
        return values;
    }

    private static String joinDoubles(double[] arr) {
        String[] strArr = new String[arr.length];
        for (int i = 0; i < arr.length; i++) {
            strArr[i] = String.format("%f", arr[i]);
        }
        return String.join(", ", strArr);
    }

    public void printStats() {
        if (!resultsExist) {
            System.out.println("ERROR: No results exist.");
            return;
        }

        System.out.println("Statistical summary:");
        double[] avg = new double[3];
        double[] stddev = new double[3];
        double[] min = new double[3];
        double[] max = new double[3];
        for (int i = 0; i < 3; i++) {
            avg[i] = 0;
            stddev[i] = 0;
            min[i] = Double.MAX_VALUE;
            max[i] = Double.MIN_VALUE;
        }
        for (int i = 0; i < bestGradNorm.length; i++) {
            avg[0] += bestGradNorm[i];
            avg[1] += nIter[i];
            avg[2] += compTime[i];
            if (bestGradNorm[i] < min[0]) {
                min[0] = bestGradNorm[i];
            }
            if (nIter[i] < min[1]) {
                min[1] = nIter[i];
            }
            if (compTime[i] < min[2]) {
                min[2] = compTime[i];
            }
            if (bestGradNorm[i] > max[0]) {
                max[0] = bestGradNorm[i];
            }
            if (nIter[i] > max[1]) {
                max[1] = nIter[i];
            }
            if (compTime[i] > max[2]) {
                max[2] = compTime[i];
            }
        }
        for (int i = 0; i < 3; i++) {
            avg[i] /= bestGradNorm.length;
        }
        for (int i = 0; i < bestGradNorm.length; i++) {
            stddev[0] += Math.pow(bestGradNorm[i] - avg[0], 2);
            stddev[1] += Math.pow(nIter[i] - avg[1], 2);
            stddev[2] += Math.pow(compTime[i] - avg[2], 2);
        }
        for (int i = 0; i < 3; i++) {
            stddev[i] = Math.sqrt(stddev[i] / bestGradNorm.length);
        }

        String[] headers = {
            "       ",
            "norm(grad)",
            "# iter",
            "Comp time (ms)"
        };
        String[][] columns = {
            SteepestDescent.printStatsRow("Average", avg[0], avg[1], avg[2], true),
            SteepestDescent.printStatsRow("St Dev", stddev[0], stddev[1], stddev[2], false),
            SteepestDescent.printStatsRow("Minimum", min[0], min[1], min[2], false),
            SteepestDescent.printStatsRow("Maximum", max[0], max[1], max[2], false),
        };
        Pro4_cakirog3.printResults(headers, columns);
    }

    public void printAll() {
        if (!resultsExist) {
            System.out.println("ERROR: No results exist.");
            return;
        }

        System.out.println("Detailed results:");
        String[] headers = {
            "Poly no.",
            "f(x)",
            "norm(grad)",
            "# iter",
            "Comp time (ms)",
            "Best point"
        };
        String[][] columns = new String[bestGradNorm.length][];
        for (int i = 0; i < bestGradNorm.length; i++) {
            columns[i] = SteepestDescent.printPolynomialResult(i + 1, bestObjVal[i], bestGradNorm[i], nIter[i], compTime[i], SteepestDescent.joinDoubles(bestPoint.get(i)));
        }
        Pro4_cakirog3.printResults(headers, columns);
        System.out.println();
        System.out.println();

        printStats();
        System.out.println();
    }
}
