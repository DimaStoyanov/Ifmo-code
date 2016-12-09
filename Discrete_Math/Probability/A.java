package Discrete_Math.Probability;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * Created by Blackbird on 02.05.2016.
 * Project : Discrete_Math.Probability.Discrete_Math.Automate.Discrete_Math.Hamiltonian_cycle.Algorithm.Tree.A
 * Start time : 13:25
 */

public class A {
    String fileName = "exam";
    BufferedReader br;
    StringTokenizer in;
    PrintWriter out;

    public static void main(String[] args) throws IOException {
        Locale.setDefault(Locale.US);
        new A().run();
    }

    public void solve() throws IOException {
        int k = nextInt();
        int n = nextInt();
        double res = 0.0;
        for (int i = 0; i < k; i++) {
            res += nextDouble() / n * nextDouble() / 100;
        }
        out.println(res);
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