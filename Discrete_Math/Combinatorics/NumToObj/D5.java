package Discrete_Math.Combinatorics.NumToObj;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * Created by Blackbird on 20.04.2016.
 * Project : DM.Discrete_Math.Combinatorics.NumToObj.D5
 * Start time : 16:35
 */

public class D5 {
    String fileName = "choose2num";
    BufferedReader br;
    StringTokenizer in;
    PrintWriter out;

    public static void main(String[] args) throws IOException {
        Locale.setDefault(Locale.US);
        new D5().run();
    }

    private long choose2Num(int[] choose, int n, int k) {
        long number = 0;
        for (int i = 1; i <= k; i++) {
            for (int j = choose[i - 1] + 1; j < choose[i]; j++) {
                number += choose(n - j, k - i);
            }
        }
        return number;
    }

    private long choose(int n, int k) {
        long result = 1;
        for (int i = n - k + 1; i <= n; i++) {
            result *= i;
        }
        return result / factorial(k);
    }

    private long factorial(int x) {
        long result = 1;
        for (int i = 2; i <= x; i++) {
            result *= i;
        }
        return result;
    }

    public void solve() throws IOException {
        int n = nextInt();
        int k = nextInt();
        int choose[] = new int[n + 1];
        for (int i = 1; i <= k; i++) {
            choose[i] = nextInt();
        }
        out.println(choose2Num(choose, n, k));
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