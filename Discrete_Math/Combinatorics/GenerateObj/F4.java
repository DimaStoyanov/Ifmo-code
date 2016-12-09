package Discrete_Math.Combinatorics.GenerateObj;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * Created by Blackbird on 14.04.2016.
 * Project : DM.GenerateObj.F4
 * Start time : 14:00
 */

public class F4 {
    String fileName = "subsets";
    BufferedReader br;
    StringTokenizer in;
    PrintWriter out;
    private ArrayList<Integer> s = new ArrayList<>();
    private int n;

    public static void main(String[] args) throws IOException {
        Locale.setDefault(Locale.US);
        new F4().run();
    }

    public boolean next() {
        if (s.get(0) == n)
            return false;
        if (s.get(s.size() - 1) == n) {
            s.remove(s.size() - 1);
            s.set(s.size() - 1, s.get(s.size() - 1) + 1);
        } else {
            s.add(s.get(s.size() - 1) + 1);
        }
        return true;
    }

    private void print() throws IOException {
        final int len = s.size() - 1;
        for (int i = 0; i < len; i++) {
            out.print(s.get(i) + " ");
        }
        out.println(s.get(len));
    }

    public void solve() throws IOException {
        n = nextInt();
        s.add(1);
        out.println();
        out.println(1);
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