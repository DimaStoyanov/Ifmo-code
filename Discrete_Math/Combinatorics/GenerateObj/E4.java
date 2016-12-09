package Discrete_Math.Combinatorics.GenerateObj;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * Created by Blackbird on 14.04.2016.
 * Project : DM.GenerateObj.E4
 * Start time : 4:44
 */

public class E4 {
    String fileName = "partition";
    BufferedReader br;
    StringTokenizer in;
    PrintWriter out;
    private int part[];
    private int last;

    public static void main(String[] args) throws IOException {
        Locale.setDefault(Locale.US);
        new E4().run();
    }

    private boolean next() {
        if (last == 1) {
            return false;
        }
        part[last - 1]--;
        part[last - 2]++;
        if (part[last - 2] > part[last - 1]) {
            part[last - 2] += part[last - 1];
            part[last - 1] = 0;
            last--;
        } else {
            while (part[last - 2] * 2 <= part[last - 1]) {
                part[last] = part[last - 1] - part[last - 2];
                last++;
                part[last - 2] = part[last - 3];
            }
        }
        return true;
    }

    private void print() {
        for (int i = 0; i < last; i++)
            out.print(part[i] + (i == (last - 1) ? "" : "+"));
        out.println();
    }

    public void solve() throws IOException {
        int n = nextInt();
        part = new int[n];
        for (int i = 0; i < n; i++) {
            part[i] = 1;
        }
        last = n;
        print();
        while (next()) {
            print();
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