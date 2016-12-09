package Discrete_Math.Combinatorics.NumToObj;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * Created by Blackbird on 21.04.2016.
 * Project : Discrete_Math.Combinatorics.NumToObj.I5
 * Start time : 3:40
 */

public class I5 {
    String fileName = "num2part";
    BufferedReader br;
    StringTokenizer in;
    PrintWriter out;
    private int[][] d;

    public static void main(String[] args) throws IOException {
        Locale.setDefault(Locale.US);
        new I5().run();
    }

    public void solve() throws IOException {
        ArrayList<Integer> ans = num2Part(nextInt(), nextInt());
        final int len = ans.size() - 1;
        for (int i = 0; i < len; i++) {
            out.print(ans.get(i) + "+");
        }
        out.println(ans.get(len));
    }

    private ArrayList<Integer> num2Part(int n, int k) {
        d = new int[n + 1][n + 1];
        for (int i = 0; i <= n; i++) {
            Arrays.fill(d[i], -1);
        }
        k = get(n, 1) - k;
        ArrayList<Integer> ans = new ArrayList<>();
        while (k > 0 && n > 0) {
            int m = 0;
            for (int i = n; i >= 0; i--) {
                if (get(n, i) >= k) {
                    m = i;
                    break;
                }
            }

            ans.add(m);
            k -= get(n, m + 1);
            n -= m;
        }
        return ans;
    }

    private int get(int n, int k) {
        if (n < k) {
            return 0;
        }
        if (d[n][k] != -1) {
            return d[n][k];
        }
        if (n == k) {
            d[n][k] = 1;
            return d[n][k];
        }
        d[n][k] = get(n - k, k) + get(n, k + 1);
        return d[n][k];
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