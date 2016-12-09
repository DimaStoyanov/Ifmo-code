package Discrete_Math.Probability;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * Created by Blackbird on 02.05.2016.
 * Project : Discrete_Math.Probability.Algorithm.LCA.D
 * Start time : 14:08
 */

public class D {

    private BufferedReader br;
    private StringTokenizer in;
    private PrintWriter out;

    public static void main(String[] args) throws IOException {
        Locale.setDefault(Locale.US);
        new D().run();
    }

    public void solve() throws IOException {
        int n = nextInt();
        int m = nextInt();
        Triple chain[] = new Triple[m];
        boolean[] absorbing = new boolean[n];
        int absorbingCount = 0;
        for (int i = 0; i < m; i++) {
            chain[i] = new Triple(nextInt(), nextInt(), nextDouble());
            if (chain[i].first == chain[i].second && chain[i].p == 1) {
                absorbing[chain[i].first] = true;
                absorbingCount++;
            }
        }
        int nonAbsorbingCount = n - absorbingCount;
        int countQ = 0;
        int countR = 0;
        int position[] = new int[n];
        double R[][] = new double[n][n];
        double Q[][] = new double[n][n];


        for (int i = 0; i < n; i++) {
            position[i] = absorbing[i] ? countR++ : countQ++;
        }
        for (int i = 0; i < m; i++) {
            if (absorbing[chain[i].second]) {
                if (!absorbing[chain[i].first]) {
                    R[position[chain[i].first]][position[chain[i].second]] = chain[i].p;
                }
            } else {
                Q[position[chain[i].first]][position[chain[i].second]] = chain[i].p;
            }
        }


        double[][] E = new double[nonAbsorbingCount][nonAbsorbingCount];
        double[][] N = new double[nonAbsorbingCount][nonAbsorbingCount];
        for (int i = 0; i < nonAbsorbingCount; i++) {
            E[i][i] = N[i][i] = 1;
            for (int j = 0; j < nonAbsorbingCount; j++) {
                E[i][j] -= Q[i][j];
            }
        }


        double mul;
        for (int i = 0; i < nonAbsorbingCount; i++) {
            if (E[i][i] != 1) {
                mul = E[i][i];
                for (int j = 0; j < nonAbsorbingCount; j++) {
                    E[i][j] /= mul;
                    N[i][j] /= mul;
                }
            }
            for (int row = 0; row < nonAbsorbingCount; row++) {
                if (i != row) {
                    mul = E[row][i];
                    for (int j = 0; j < nonAbsorbingCount; j++) {
                        E[row][j] -= mul * E[i][j];
                        N[row][j] -= mul * N[i][j];
                    }
                }
            }
        }


        double[][] G = new double[nonAbsorbingCount][absorbingCount];
        for (int i = 0; i < nonAbsorbingCount; i++) {
            for (int j = 0; j < absorbingCount; j++) {
                G[i][j] = 0;
                for (int k = 0; k < nonAbsorbingCount; k++) {
                    G[i][j] += N[i][k] * R[k][j];
                }
            }
        }
        double curP;
        for (int i = 0; i < n; i++) {
            curP = 0;
            if (absorbing[i]) {
                for (int j = 0; j < nonAbsorbingCount; j++) {
                    curP += G[j][position[i]];
                }
                curP++;
                curP /= n;
            }
            out.println(curP);
        }

    }

    public void run() {
        try {
            String fileName = "absmarkchain";
            br = new BufferedReader(new FileReader(fileName + ".in"));
            out = new PrintWriter(fileName + ".out");

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

    private class Triple {
        int first;
        int second;
        double p;

        Triple(int first, int second, double p) {
            this.first = first - 1;
            this.second = second - 1;
            this.p = p;
        }
    }

}