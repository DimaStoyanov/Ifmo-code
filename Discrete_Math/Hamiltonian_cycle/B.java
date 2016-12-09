package Discrete_Math.Hamiltonian_cycle;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * Created by Blackbird on 04.11.2016.
 * Project : Discrete_Math.Hamiltonian_cycle.Discrete_Math.Matroid.B
 * Start time : 1:22
 */

public class B {
    private int n;
    private boolean adj[][];
    private LinkedList<Integer> order;
    private int temp;
    private BufferedReader br;
    private StringTokenizer in;
    private PrintWriter out;

    public static void main(String[] args) throws IOException {
        Locale.setDefault(Locale.US);
        new B().run();
    }

    public void solve() throws IOException {
        n = nextInt();
        adj = new boolean[n][n];
        order = new LinkedList<Integer>() {
            @Override
            public Integer get(int index) {
                return super.get(index % n);
            }

            @Override
            public Integer set(int index, Integer element) {
                return super.set(index % n, element);
            }
        };
        order.add(0);
        String line;
        for (int i = 1; i < n; i++) {
            order.add(i);
            line = nextToken();
            for (int j = 0; j < i; j++) {
                adj[j][i] = adj[i][j] = line.charAt(j) == '1';
            }
        }
        int start = 0;
        int changes = 0;
        int sqrN = (n - 1) * n;
        int left;
        int middle;

        for (int i = 0; i < sqrN; i++) {
            if (!checkAdj(start, start + 1)) {
                left = start + 1;
                middle = start + 2;
                while (middle - start != n && (!checkAdj(start, middle) || !checkAdj(start + 1, middle + 1))) {
                    middle++;
                }
                if (middle - start == n) {
                    middle = start + 2;
                    while (!checkAdj(start, middle)) {
                        middle++;
                    }
                }
                while (left <= middle) {
                    swap(left++, middle);
                }
                changes = 0;
            } else if (++changes > n) break;
            start++;
        }
        order.forEach(integer -> out.print((integer + 1) + " "));
    }

    private boolean checkAdj(int i, int j) {
        return adj[order.get(i)][order.get(j)];
    }

    private void swap(int i, int j) {
        temp = order.get(i);
        order.set(i, order.get(j));
        order.set(j, temp);
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