package Discrete_Math.Probability;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * Created by Blackbird on 04.05.2016.
 * Project : Discrete_Math.Probability.Discrete_Math.Automate.F
 * Start time : 2:45
 */

public class E {

    private BufferedReader br;
    private StringTokenizer in;
    private PrintWriter out;

    public static void main(String[] args) throws IOException {
        Locale.setDefault(Locale.US);
        new E().run();
    }

    private double[][] matrixMultiply(double[][] A, int power) {
        final int rank = A.length;
        double[][] C = A;
        for (int p = 0; p < power - 1; p++) {
            double[][] B = new double[rank][rank];
            for (int i = 0; i < rank; i++)
                for (int j = 0; j < rank; j++) {
                    for (int k = 0; k < rank; k++)
                        B[i][j] += C[i][k] * A[k][j];
                }
            C = B;
        }
        return C;
    }

    private void printMatrix(double[][] A) {
        for (double aA : A[0]) {
            out.println(aA);
        }
    }

    public void solve() throws IOException {
        int n = nextInt();
        double P[][] = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                P[i][j] = nextDouble();
            }
        }
        printMatrix(matrixMultiply(P, 10));
    }

    public void run() {
        try {
            br = new BufferedReader(new FileReader("markchain" + ".in"));
            out = new PrintWriter("markchain" + ".out");
            solve();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public String nextToken() throws IOException {
        while (in == null || !in.hasMoreTokens()) {
            in = new StringTokenizer(br.readLine());
        }
        return in.nextToken();
    }

    public int nextInt() throws IOException {
        return Integer.parseInt(nextToken());
    }

    public double nextDouble() throws IOException {
        return Double.parseDouble(nextToken());
    }

    public long nextLong() throws IOException {
        return Long.parseLong(nextToken());
    }

}