package Discrete_Math.Combinatorics.NumToObj;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * Created by Blackbird on 20.04.2016.
 * Project : DM.Discrete_Math.Combinatorics.NumToObj.B5
 * Start time : 15:54
 */

public class B5 {
    String fileName = "perm2num";
    BufferedReader br;
    StringTokenizer in;
    PrintWriter out;

    public static void main(String[] args) throws IOException {
        Locale.setDefault(Locale.US);
        new B5().run();
    }

    private long perm2Num(int[] a) {
        long number = 0;
        final int n = a.length - 1;
        boolean[] used = new boolean[n + 1];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j < a[i]; j++) {
                if (!used[j]) {
                    number += factorial(n - i);
                }
            }
            used[a[i]] = true;
        }
        return number;
    }

    private long factorial(long x) {
        long result = 1;
        for (int i = 2; i <= x; i++) {
            result *= i;
        }
        return result;
    }

    public void solve() throws IOException {
        int n = nextInt();
        int[] a = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            a[i] = nextInt();
        }
        out.println(perm2Num(a));
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