package Algorithm.LCA;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * Created by Blackbird on 02.11.2016.
 * Project : Algorithm.LCA.H
 * Start time : 22:05
 */

public class H {

    ArrayList<Integer> g[];
    int[] p, d;
    ArrayList<ArrayList<Integer>> dp;
    int n, ln, curDepth;
    boolean[] used;
    private BufferedReader br;
    private StringTokenizer in;
    private PrintWriter out;

    public static void main(String[] args) throws IOException {
        Locale.setDefault(Locale.US);
        new H().run();
    }

    public void solve() throws IOException {
        n = nextInt();
        g = new ArrayList[n + 1];
        d = new int[n + 1];
        p = new int[n + 1];
        dp = new ArrayList<>();
        used = new boolean[n + 1];
        for (int i = 0; i < n + 1; i++) {
            g[i] = new ArrayList<>();
            dp.add(i, new ArrayList<>());
        }
        int from;

        for (int i = 0; i < n; i++) {
            from = nextInt();
            g[from].add(i + 1);
            p[i + 1] = from;
        }
        dfs(0);
        ln = (int) (Math.log(n) / Math.log(2) + 2);
        for (int i = 0; i < ln; i++) {
            dp.get(0).add(i, 0);
        }

        for (int i = 0; i < n + 1; i++) {
            dp.get(i).add(0, p[i]);
        }

        for (int j = 1; j < ln; j++) {
            for (int i = 1; i < n + 1; i++) {
                dp.get(i).add(j, dp.get(dp.get(i).get(j - 1)).get(j - 1));
            }
        }
        int m = nextInt();
        for (int i = 0; i < m; i++) {
            from = nextInt();
            out.println(from == lca(from, nextInt()) ? "1" : "0");
        }
    }

    private int lca(int v, int u) {
        if (d[v] > d[u]) {
            int temp = v;
            v = u;
            u = temp;
        }
        for (int i = ln - 1; i >= 0; i--) {
            if (d[u] - d[v] >= (1 << i)) {
                u = dp.get(u).get(i);
            }
        }
        if (v == u) return v;
        for (int i = ln - 1; i >= 0; i--) {
            if (!dp.get(v).get(i).equals(dp.get(u).get(i))) {
                v = dp.get(v).get(i);
                u = dp.get(u).get(i);
            }
        }
        return dp.get(v).get(0);
    }

    private void dfs(int i) {
        used[i] = true;
        d[i] = curDepth++;
        (g[i]).stream().filter(e -> !used[e]).forEach(this::dfs);
        curDepth--;
    }

    public void run() {
        try {
            br = new BufferedReader(new FileReader("ancestor" + ".in"));
            out = new PrintWriter("ancestor" + ".out");
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