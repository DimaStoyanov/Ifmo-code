package Discrete_Math.Combinatorics.NumToObj;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * Created by Blackbird on 20.04.2016.
 * Project : Discrete_Math.Combinatorics.NumToObj.E5
 * Start time : 17:28
 */

public class E5 {
    String fileName = "num2brackets";
    BufferedReader br;
    StringTokenizer in;
    PrintWriter out;

    public static void main(String[] args) throws IOException {
        Locale.setDefault(Locale.US);
        new E5().run();
    }

    private String num2Brackets(int n, long k) {
        long d[][] = calculateDynamic(n);
        StringBuilder ans = new StringBuilder();
        int depth = 0;
        for (int i = n * 2 - 1; i >= 0; i--)
            if (depth + 1 <= n && d[i][depth + 1] >= k) {
                ans.append('(');
                depth++;
            } else {
                ans.append(')');
                if (depth + 1 <= n)
                    k -= d[i][depth + 1];
                depth--;
            }
        return ans.toString();
    }

    private long[][] calculateDynamic(int n) {
        long d[][] = new long[n * 2 + 1][n + 1];
        d[0][0] = 1;
        final int dn = n << 1;
        for (int i = 0; i < dn; i++)
            for (int j = 0; j <= n; j++) {
                if (j + 1 <= n)
                    d[i + 1][j + 1] += d[i][j];
                if (j > 0)
                    d[i + 1][j - 1] += d[i][j];
            }
        return d;
    }

    public void solve() throws IOException {
        out.println(num2Brackets(nextInt(), nextLong() + 1));
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