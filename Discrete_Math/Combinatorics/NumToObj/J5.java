package Discrete_Math.Combinatorics.NumToObj;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * Created by Blackbird on 21.04.2016.
 * Project : Discrete_Math.Combinatorics.NumToObj.J5
 * Start time : 2:47
 */

public class J5 {
    String fileName = "part2num";
    BufferedReader br;
    StringTokenizer in;
    PrintWriter out;
    private long[][] d;

    public static void main(String[] args) throws IOException {
        Locale.setDefault(Locale.US);
        new J5().run();
    }

    private long get(int n, int k) {
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

    private long part2Num(ArrayList<Integer> a, int n) {
        d = new long[n + 1][n + 1];
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= n; j++) {
                d[i][j] = -1;
            }
        }
        long ans = 0;
        int curN = n;
        for (int i : a) {
            ans += get(curN, i + 1);
            curN -= i;
        }
        ans = get(n, 1) - ans - 1;
        return ans;
    }

    public void solve() throws IOException {
        String s = nextToken();
        StringTokenizer tk = new StringTokenizer(s, "+");
        ArrayList<Integer> part = new ArrayList<>();
        int n = 0;
        while (tk.hasMoreTokens()) {
            int cur = Integer.parseInt(tk.nextToken());
            n += cur;
            part.add(cur);
        }
        out.println(part2Num(part, n));
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