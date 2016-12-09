package Discrete_Math.Combinatorics.NumToObj;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * Created by Blackbird on 20.04.2016.
 * Project : DM.Discrete_Math.Combinatorics.NumToObj.A5
 * Start time : 15:27
 */

public class A5 {
    String fileName = "num2perm";
    BufferedReader br;
    StringTokenizer in;
    PrintWriter out;

    public static void main(String[] args) throws IOException {
        Locale.setDefault(Locale.US);
        new A5().run();
    }

    private int[] num2Perm(int n, long k) {
        long numbersUsed;
        int curFreeNumber;
        int[] perm = new int[n + 1];
        boolean used[] = new boolean[n + 1];
        for (int i = 1; i <= n; i++) {
            long factorial = factorial(n - i);
            numbersUsed = k / factorial;
            k %= factorial;
            curFreeNumber = 0;
            for (int j = 1; j <= n; j++) {
                if (!used[j]) {
                    curFreeNumber++;
                    if (curFreeNumber == numbersUsed + 1) {
                        perm[i] = j;
                        used[j] = true;
                    }
                }
            }
        }
        return perm;
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
        long k = nextLong();
        int[] ans = num2Perm(n, k);
        for (int i = 1; i <= n; i++) {
            out.print(ans[i] + " ");
        }
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