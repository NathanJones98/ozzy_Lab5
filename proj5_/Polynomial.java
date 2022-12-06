import java.io.IOException;

public class Polynomial {
    private int n; // no. of variables
    private int degree; // degree of polynomial
    private double[][] coefs; // coefficients

    // constructors
    public Polynomial() {
        n = 1;
        degree = 0;
        coefs = null;
    }

    public Polynomial(int n, int degree, double[][] coefs) {
        this.n = n;
        this.degree = degree;
        this.coefs = coefs;
    }

    // getters
    public int getN() {
        return n;
    }

    int getDegree() {
        return degree;
    }

    public double[][] getCoefs() {
        return coefs;
    }

    // setters
    public void setN(int a) {
        n = a;
    }

    public void setDegree(int a) {
        degree = a;
    }

    public void setCoef(int j, int d, double a) {
        coefs[j][d] = a;
    }

    // other methods
    public void init() {
        coefs = new double[n][degree + 1];
    }

    public double f(double[] x) {
        double sum = 0;
        for (int j = 0; j < n; j++) {
            for (int d = 0; d < degree; d++) {
                sum += coefs[j][d] * Math.pow(x[j], (degree - d));
            }
            sum += coefs[j][degree];
        }
        return sum;
    }

    public double[] gradient(double[] x) {
        double[] grad = new double[n];
        for (int j = 0; j < n; j++) {
            for (int d = 0; d < degree; d++) {
                double d_degree = degree - d;
                grad[j] += coefs[j][d] * d_degree * Math.pow(x[j], d_degree - 1);
            }
        }
        return grad;
    }

    public double gradientNorm(double[] x) {
        double[] grad = gradient(x);
        double sum = 0;
        for (int j = 0; j < n; j++) {
            sum += grad[j] * grad[j];
        }
        return Math.sqrt(sum);
    }

    public boolean isSet() {
        return coefs != null;
    }

    public void print() {
        System.out.print("f(x) = ");
        for (int j = 0; j < n; j++) {
            System.out.print("( ");
            for (int d = 0; d < degree; d++) {
                System.out.printf("%.2fx%d^%d + ", coefs[j][d], j + 1, degree - d);
            }
            System.out.printf("%.2f )", coefs[j][degree]);
            if (j < n - 1) {
                System.out.print(" + ");
            }
        }
    }
}
