package Algorithm.ShortPath;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * Created by Blackbird on 09.05.2016.
 * Project : ShortPath.Algorithm.Tree.A
 * Start time : 19:18
 */

public class A {
    private int r = 0;
    private BufferedReader br;
    private StringTokenizer in;
    private PrintWriter out;

    public static void main(String[] args) throws IOException {
        Locale.setDefault(Locale.US);
        new A().run();
    }

    public void solve() throws IOException {
        int n = nextInt();
        int m = nextInt();
        ArrayList<Integer>[] g = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            g[i] = new ArrayList<>();
        }
        for (int i = 0; i < m; i++) {
            int from = nextInt() - 1;
            int to = nextInt() - 1;
            g[from].add(to);
            g[to].add(from);
        }
        bfs(g, n);
    }

    private void bfs(ArrayList<Integer>[] g, int n) {
        int[] ans = new int[n];
        Arrays.fill(ans, Integer.MAX_VALUE);
        ans[0] = 0;
        ArrayList<Integer> order = new ArrayList<>();
        order.add(0);
        boolean[] used = new boolean[n];
        used[0] = true;
        while (!order.isEmpty()) {
            int curV = order.remove(0);
            (g[curV]).stream().filter(v -> !used[v]).forEach(v -> {
                ans[v] = Math.min(ans[v], ans[curV] + 1);
                order.add(v);
                used[v] = true;
            });
        }
        print(ans);
    }

    private void print(int[] a) {
        for (int aA : a) {
            out.print(aA + " ");
        }
    }

    public void run() {
        try {
            br = new BufferedReader(new FileReader("D" + ".in"));
            out = new PrintWriter("D" + ".out");
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