package Discrete_Math.Combinatorics.NumToObj;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 * Created by Blackbird on 20.04.2016.
 * Project : Discrete_Math.Combinatorics.NumToObj.H5
 * Start time : 21:33
 */

public class H5 {
    String fileName = "brackets2num2";
    BufferedReader br;
    StringTokenizer in;
    PrintWriter out;

    public static void main(String[] args) throws IOException {
        Locale.setDefault(Locale.US);
        new H5().run();
    }

    private long brackets2Num(String sequence) {
        int n = sequence.length() >> 1;
        long[][] d = calculateDynamic((n << 1) + 1);
        long number = 0;
        int balance = 0;
        Stack<Character> stack = new Stack<>();
        int dn = n << 1;
        for (int i = 0; i < dn; i++) {
            if (sequence.charAt(i) == '(') {
                stack.push('(');
                balance++;
                continue;
            }

            int ndepth = balance + 1;
            if (balance < n)
                number += d[2 * n - i - 1][ndepth] << (2 * n - i - 1 - ndepth >> 1);

            if (sequence.charAt(i) == ')') {
                stack.remove(stack.size() - 1);
                balance--;
                continue;
            }

            if (!stack.isEmpty() && stack.peek() == '(' && balance > 0) {
                ndepth = balance - 1;
                number += d[2 * n - i - 1][ndepth] << (2 * n - i - 1 - ndepth >> 1);
            }

            if (sequence.charAt(i) == '[') {
                stack.push('[');
                balance++;
                continue;
            }

            if (balance < n) {
                ndepth = balance + 1;
                number += d[2 * n - i - 1][ndepth] << (2 * n - i - 1 - ndepth >> 1);
            }

            if (sequence.charAt(i) == ']') {
                stack.remove(stack.size() - 1);
                balance--;
            }
        }
        return number;
    }

    private long[][] calculateDynamic(int n) {
        long d[][] = new long[n * 2 + 1][n + 1];
        d[0][0] = 1;
        for (int i = 0; i < n; i++)
            for (int j = 0; j <= n; j++) {
                if (j + 1 <= n)
                    d[i + 1][j + 1] += d[i][j];
                if (j > 0)
                    d[i + 1][j - 1] += d[i][j];
            }
        return d;
    }

    public void solve() throws IOException {
        out.println(brackets2Num(nextToken()));
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