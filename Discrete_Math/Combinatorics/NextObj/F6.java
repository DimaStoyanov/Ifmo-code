package Discrete_Math.Combinatorics.NextObj;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * Created by Blackbird on 16.04.2016.
 * Project : DM.Discrete_Math.Combinatorics.NextObj.F6
 * Start time : 21:50
 */

public class F6 {
    String fileName = "nextmultiperm";
    BufferedReader br;
    StringTokenizer in;
    PrintWriter out;
    private int n;
    private int a[];

    public static void main(String[] args) throws IOException {
        Locale.setDefault(Locale.US);
        new F6().run();
    }

    public boolean nextMultiPerm() {

        int j = n - 2;
        while (j != -1 && a[j] >= a[j + 1]) j--;
        if (j == -1)
            return false;
        int k = n - 1;
        while (a[j] >= a[k]) k--;
        swap(j, k);
        int l = j + 1, r = n - 1;
        while (l < r)
            swap(l++, r--);
        return true;
    }

    public void print(boolean hasMultiPerm) {
        for (int i = 0; i < n; i++) {
            out.print((hasMultiPerm ? a[i] : 0) + " ");
        }
        out.println();
    }

    private void swap(int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    public void solve() throws IOException {
        n = nextInt();
        a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = nextInt();
        }
        print(nextMultiPerm());
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