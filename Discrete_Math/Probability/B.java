package Discrete_Math.Probability;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * Created by Blackbird on 04.05.2016.
 * Project : Discrete_Math.Probability.Discrete_Math.Automate.Discrete_Math.Hamiltonian_cycle.Discrete_Math.Matroid.B
 * Start time : 0:45
 */

public class B {
    String fileName = "shooter";
    BufferedReader br;
    StringTokenizer in;
    PrintWriter out;

    public static void main(String[] args) throws IOException {
        Locale.setDefault(Locale.US);
        new B().run();
    }

    public void solve() throws IOException {
        int n = nextInt();
        int m = nextInt();
        int k = nextInt();
        double totalP = 0;
        double kthP = 0;
        for (int i = 0; i < n; i++) {
            double curAccuracy = 1 - nextDouble();
            totalP += Math.pow(curAccuracy, m);
            if (i == k - 1)
                kthP = Math.pow(curAccuracy, m);
        }
        out.println(kthP / totalP);
    }

    public void run() {
        try {
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

}