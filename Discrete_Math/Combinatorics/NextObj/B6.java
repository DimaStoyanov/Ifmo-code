package Discrete_Math.Combinatorics.NextObj;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * Created by Blackbird on 16.04.2016.
 * Project : DM.Discrete_Math.Combinatorics.NextObj.B6
 * Start time : 19:21
 */

public class B6 {
    private static int n;
    private static int prev[];
    private static int next[];
    String fileName = "nextperm";
    BufferedReader br;
    StringTokenizer in;
    PrintWriter out;

    public static void main(String[] args) throws IOException {
        Locale.setDefault(Locale.US);
        new B6().run();
    }

    public boolean nextPerm() {

        int j = n - 2;
        while (j != -1 && next[j] >= next[j + 1]) j--;
        if (j == -1)
            return false;
        int k = n - 1;
        while (next[j] >= next[k]) k--;
        swapNext(j, k);
        int l = j + 1, r = n - 1;
        while (l < r)
            swapNext(l++, r--);
        return true;
    }

    private boolean prevPerm() {
        for (int i = n - 2; i >= 0; i--) {
            if (prev[i] > prev[i + 1]) {
                int max = i + 1;
                for (int j = i + 1; j < n; j++) {
                    if (prev[j] > prev[max] && prev[j] < prev[i]) {
                        max = j;
                    }
                }
                swapPrev(i, max);
                int l = i + 1;
                int r = n - 1;
                while (l < r) {
                    swapPrev(l++, r--);
                }
                return true;
            }
        }
        return false;
    }

    public void print(boolean hasPerm, boolean type) {
        if (!hasPerm) {
            for (int i = 0; i < n; i++)
                out.print(0 + " ");
            out.println();
        } else {
            for (int i = 0; i < n; i++) {
                out.print((type ? next[i] : prev[i]) + " ");
            }
            out.println();
        }
    }

    public void swapNext(int i, int j) {
        int temp = next[i];
        next[i] = next[j];
        next[j] = temp;
    }

    private void swapPrev(int i, int j) {
        int temp = prev[i];
        prev[i] = prev[j];
        prev[j] = temp;
    }

    public void solve() throws IOException {
        n = nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = nextInt();
        }
        prev = new int[n];
        next = new int[n];
        System.arraycopy(a, 0, prev, 0, n);
        System.arraycopy(a, 0, next, 0, n);
        print(prevPerm(), false);
        print(nextPerm(), true);
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