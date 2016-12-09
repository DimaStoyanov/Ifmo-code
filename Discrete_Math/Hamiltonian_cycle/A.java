package Discrete_Math.Hamiltonian_cycle;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * Created by Blackbird on 01.11.2016.
 * Project : Discrete_Math.Hamiltonian_cycle.A
 * Start time : 21:35
 */

public class A {

    private BufferedReader br;
    private StringTokenizer in;
    private PrintWriter out;

    public static void main(String[] args) throws IOException {
        Locale.setDefault(Locale.US);
        new A().run();
    }

    public void solve() throws IOException {

        int n = nextInt();
        boolean g[][] = new boolean[n][n];
        LinkedList<Integer> order = new LinkedList<>();
        String temp;
        order.add(0);
        for (int i = 1; i < n; i++) {
            order.add(i);
            temp = nextToken();
            for (int j = 0; j < i; j++) {
                g[j][i] = g[i][j] = String.valueOf(temp).charAt(j) == '1';
            }
        }


        int squareN = n * n;
        for (int i = 0; i < squareN; i++) {
            if (!g[order.getFirst()][order.get(1)]) {
                int j = 2;
                while (!g[order.getFirst()][order.get(j)] || (!g[order.get(1)][order.get(j + 1)])) {
                    j++;
                }
                Collections.reverse(order.subList(1, j + 1));
            }
            order.add(order.pop());

        }
        order.forEach(o -> out.print((o + 1) + " "));
    }

    public void run() {
        try {
            br = new BufferedReader(new FileReader("chvatal" + ".in"));
            out = new PrintWriter("chvatal" + ".out");
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