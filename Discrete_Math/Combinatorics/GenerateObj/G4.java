package Discrete_Math.Combinatorics.GenerateObj;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * Created by Blackbird on 15.04.2016.
 * Project : DM.GenerateObj.Discrete_Math.Combinatorics.GenerateObj.G4
 * Start time : 17:37
 */

public class G4 {
    String fileName = "part2sets";
    BufferedReader br;
    StringTokenizer in;
    PrintWriter out;
    private int n;
    private int k;
    private int[] part;

    public static void main(String[] args) throws IOException {
        Locale.setDefault(Locale.US);
        new G4().run();
    }

    private void part(int i, int j) {
        if (i == n) {
            if (j != k) return;
            print();
        } else {
            for (int ii = 0; ii <= j; ii++) {
                part[i] = ii;
                if (ii == j) {
                    part(i + 1, j + 1);
                } else {
                    part(i + 1, j);
                }
            }
        }
    }

    private void print() {
        StringBuilder[] sb = new StringBuilder[k];
        for (int i = 0; i < k; i++) {
            sb[i] = new StringBuilder();
        }
        for (int i = 0; i < n; i++) {
            sb[part[i]].append(i + 1).append(" ");
        }
        for (StringBuilder s : sb) {
            out.println(s.toString());
        }
        out.println();
    }

    public void solve() throws IOException {
        n = nextInt();
        k = nextInt();
        part = new int[n];
        part(0, 0);
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