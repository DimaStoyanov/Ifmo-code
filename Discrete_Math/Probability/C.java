package Discrete_Math.Probability;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * Created by Blackbird on 04.05.2016.
 * Project : Discrete_Math.Probability.Algorithm.LCA.Discrete_Math.Matroid.C
 * Start time : 1:06
 */

public class C {
    String fileName = "lottery";
    BufferedReader br;
    StringTokenizer in;
    PrintWriter out;

    public static void main(String[] args) throws IOException {
        Locale.setDefault(Locale.US);
        new C().run();
    }

    public void solve() throws IOException {
        int n = nextInt();
        int m = nextInt();
        int money = 0;
        double fields = 0;
        double p = 1;
        double result = 0;
        for (int i = 0; i < m; i++) {
            fields = nextInt();
            result += (fields - 1) / fields * p * money;
            money = nextInt();
            p *= 1 / fields;
        }
        result += p * money;
        out.println(n - result);
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