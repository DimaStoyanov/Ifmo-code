package Discrete_Math.Combinatorics.GenerateObj;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * Created by Blackbird on 14.04.2016.
 * Project : DM.GenerateObj.C4
 * Start time : 3:37
 */

public class C4 {
    String fileName = "choose";
    BufferedReader br;
    StringTokenizer in;
    PrintWriter out;

    public static void main(String[] args) throws IOException {
        Locale.setDefault(Locale.US);
        new C4().run();
    }

    private boolean next(int a[], int n, int k) {
        int[] b = new int[k + 1];
        for (int i = 0; i < k; i++) {
            b[i] = a[i];
        }
        b[k] = n + 1;
        int i = k - 1;
        while (i >= 0 && b[i + 1] - b[i] < 2) {
            i--;
        }
        if (i < 0)
            return false;
        b[i]++;
        for (int j = i + 1; j < k; j++) {
            b[j] = b[j - 1] + 1;
        }
        for (i = 0; i < k; i++) {
            a[i] = b[i];
        }
        return true;
    }

    private void print(int a[], int n, int k) {
        for (int i = 0; i < k; i++) {
            out.print(a[i] + " ");
        }
        out.println();
    }

    public void solve() throws IOException {
        int n = nextInt();
        int k = nextInt();
        int a[] = new int[n];
        for (int i = 0; i < k; i++) {
            a[i] = i + 1;
        }
        print(a, n, k);
        while (next(a, n, k)) {
            print(a, n, k);
        }
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