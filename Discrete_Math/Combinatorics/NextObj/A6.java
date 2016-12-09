package Discrete_Math.Combinatorics.NextObj;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * Created by Blackbird on 16.04.2016.
 * Project : DM.Discrete_Math.Combinatorics.NextObj.A6
 * Start time : 18:52
 */

public class A6 {
    String fileName = "nextvector";
    BufferedReader br;
    StringTokenizer in;
    PrintWriter out;
    private int n;

    public static void main(String[] args) throws IOException {
        Locale.setDefault(Locale.US);
        new A6().run();
    }

    private String nextVector(String s) {
        boolean vector[] = getVector(s);
        for (int i = n - 1; i >= 0; i--) {
            if (vector[i]) {
                vector[i] = false;
            } else {
                vector[i] = true;
                return print(vector);
            }
        }
        return "-";
    }

    private String prevVector(String s) {
        boolean vector[] = getVector(s);
        for (int i = n - 1; i >= 0; i--) {
            if (vector[i]) {
                vector[i] = false;
                return print(vector);
            } else {
                vector[i] = true;
            }
        }
        return "-";
    }

    private boolean[] getVector(String s) {
        boolean[] vector = new boolean[n];
        for (int i = 0; i < n; i++) {
            vector[i] = s.charAt(i) == '1';
        }
        return vector;
    }

    private String print(boolean[] vector) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append(vector[i] ? 1 : 0);
        }
        return sb.toString();
    }

    public void solve() throws IOException {
        String s = next();
        n = s.length();
        out.println(prevVector(s));
        out.println(nextVector(s));
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

    public String next() throws IOException {
        return nextToken();
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