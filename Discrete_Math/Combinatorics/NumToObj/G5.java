package Discrete_Math.Combinatorics.NumToObj;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * Created by Blackbird on 20.04.2016.
 * Project : Discrete_Math.Combinatorics.NumToObj.G5
 * Start time : 19:52
 */

public class G5 {
    String fileName = "num2brackets2";
    BufferedReader br;
    StringTokenizer in;
    PrintWriter out;

    public static void main(String[] args) throws IOException {
        Locale.setDefault(Locale.US);
        new G5().run();
    }

    private String num2Brackets(int n, long k) {
        StringBuilder ans = new StringBuilder();
        long[][] d = calculateDynamic(n);
        int balance = 0;
        long current;
        Stack st = new Stack(n);
        for (int i = (n << 1) - 1; i >= 0; i--) {
            current = balance < n ? d[i][balance + 1] << (i - balance - 1) / 2 : 0;
            if (current >= k) {
                ans.append('(');
                st.add('(');
                balance++;
                continue;
            }
            k -= current;
            current = !st.isEmpty() && st.get() == '(' && balance > 0 ?
                    d[i][balance - 1] << (i - balance + 1) / 2 : 0;
            if (current >= k) {
                ans.append(')');
                st.remove();
                balance--;
                continue;
            }
            k -= current;
            current = balance < n ? d[i][balance + 1] << (i - balance - 1) / 2 : 0;
            if (current >= k) {
                ans.append('[');
                st.add('[');
                balance++;
                continue;
            }
            k -= current;
            ans.append(']');
            st.remove();
            balance--;
        }
        return ans.toString();
    }

    private long[][] calculateDynamic(int n) {
        long d[][] = new long[n * 2 + 1][n + 1];
        d[0][0] = 1;
        final int dn = n << 1;
        for (int i = 0; i < dn; i++)
            for (int j = 0; j <= n; j++) {
                if (j + 1 <= n)
                    d[i + 1][j + 1] += d[i][j];
                if (j > 0)
                    d[i + 1][j - 1] += d[i][j];
            }
        return d;
    }

    public void solve() throws IOException {
        out.println(num2Brackets(nextInt(), nextLong() + 1));
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

    private class Stack {
        private char[] stack;
        private int size;

        public Stack(int n) {
            stack = new char[n << 1];
            size = 0;
        }

        private void add(char c) {
            stack[size++] = c;
        }

        private void remove() {
            size--;
        }

        private char get() {
            return stack[size - 1];
        }

        private boolean isEmpty() {
            return size == 0;
        }
    }

}