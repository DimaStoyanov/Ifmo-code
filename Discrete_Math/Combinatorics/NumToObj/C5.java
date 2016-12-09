package Discrete_Math.Combinatorics.NumToObj;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * Created by Blackbird on 20.04.2016.
 * Project : DM.Discrete_Math.Combinatorics.NumToObj.C5
 * Start time : 16:11
 */

public class C5 {
    String fileName = "num2choose";
    BufferedReader br;
    StringTokenizer in;
    PrintWriter out;

    public static void main(String[] args) throws IOException {
        Locale.setDefault(Locale.US);
        new C5().run();
    }

    private ArrayList<Integer> num2Choose(int n, int k, long m) {
        int next = 1;
        ArrayList<Integer> ans = new ArrayList<>();
        while (k > 0) {
            long curChoose = choose(n - 1, k - 1);
            if (m < curChoose) {
                ans.add(next);
                k--;
            } else {
                m -= curChoose;
            }
            n--;
            next++;
        }
        return ans;
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
        ArrayList<Integer> ans = num2Choose(nextInt(), nextInt(), nextLong());
        ans.forEach(integer -> out.print(integer + " "));
        out.println();
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