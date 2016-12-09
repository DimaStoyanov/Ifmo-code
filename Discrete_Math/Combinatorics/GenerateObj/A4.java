package Discrete_Math.Combinatorics.GenerateObj;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * Created by Blackbird on 13.04.2016.
 * Project : DM.GenerateObj.A4
 * Start time : 22:54
 */

public class A4 {
    String fileName = "vectors";
    BufferedReader br;
    StringTokenizer in;
    PrintWriter out;

    public static void main(String[] args) throws IOException {
        Locale.setDefault(Locale.US);
        new A4().run();
    }

    public void solve() throws IOException {
        int n = nextInt();
        int countOfVectors = 1 << n;
        boolean[][] vectors = new boolean[countOfVectors][n];
        boolean carry;
        int i = 1;
        int counter = 0;
        boolean hasTwoNum = false;
        boolean temp;
        while (true) {
            counter++;
            if (counter == (countOfVectors)) break;
            carry = true;
            temp = false;
            for (int j = n - 1; j >= 0; j--) {
                vectors[i][j] = vectors[hasTwoNum ? i : i - 1][j] != carry;
                carry = carry && !vectors[i][j];
                if (j < n - 1 && vectors[i][j] && vectors[i][j + 1]) {
                    temp = true;
                }
            }
            hasTwoNum = temp;
            if (!hasTwoNum) i++;
        }
        out.println(i);
        for (int j = 0; j < i; j++) {
            for (int k = 0; k < n; k++) {
                out.print(vectors[j][k] ? 1 : 0);
            }
            out.println();
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