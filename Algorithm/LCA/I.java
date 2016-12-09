package Algorithm.LCA;

import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * Created by Blackbird on 02.11.2016.
 * Project : I
 * Start time : 22:26
 */

public class I {

    private int[] d, pow;
    private int dp[][];
    private int costd[][];
    private ArrayList<Pair<Integer, Integer>> g[];
    private int ln;
    private int curDepth;
    private boolean[] used;
    private BufferedReader br;
    private StringTokenizer in;
    private PrintWriter out;

    public static void main(String[] args) throws IOException {
        Locale.setDefault(Locale.US);
        new I().run();
    }

    @SuppressWarnings("unchecked")
    public void solve() throws IOException {
        int n = nextInt();
        ln = (int) (Math.log(n) / Math.log(2) + 1);
        g = new ArrayList[n];
        dp = new int[n][ln];
        costd = new int[n][ln];
        int from, to, curW;
        for (int i = 0; i < n; i++) {
            g[i] = new ArrayList<>();
        }
        for (int i = 0; i < n - 1; i++) {
            from = nextInt();
            to = nextInt();
            curW = nextInt();
            g[from].add(new Pair<>(to, curW));
            g[to].add(new Pair<>(from, curW));
        }
        d = new int[n];
        used = new boolean[n];
        dfs(0);

        for (int j = 1; j < ln; j++) {
            for (int i = 0; i < n; i++) {
                dp[i][j] = dp[dp[i][j - 1]][j - 1];
                costd[i][j] = costd[i][j - 1] + costd[dp[i][j - 1]][j - 1];
            }
        }
        pow = new int[ln];
        pow[0] = 1;
        for (int i = 1; i < ln; i++) {
            pow[i] = pow[i - 1] * 2;
        }
        int m = nextInt();
        for (int i = 0; i < m; i++) {
            out.println(lca(nextInt(), nextInt()));
        }
    }

    private void dfs(int v) {
        used[v] = true;
        d[v] = curDepth++;
        for (Pair<Integer, Integer> p : g[v]) {
            int e = p.getKey();
            if (!used[e]) {
                costd[e][0] = p.getValue();
                dp[e][0] = v;
                dfs(e);
            }
        }
        curDepth--;
    }

    private int lca(int v, int u) {
        int sum = 0;
        if (d[v] > d[u]) {
            int temp = v;
            v = u;
            u = temp;
        }
        for (int i = ln - 1; i >= 0; i--) {
            if (d[u] - d[v] >= pow[i]) {
                sum += costd[u][i];
                u = dp[u][i];
            }
        }
        if (v == u) return sum;
        for (int i = ln - 1; i >= 0; i--) {
            if (dp[v][i] != dp[u][i]) {
                sum += costd[v][i] + costd[u][i];
                v = dp[v][i];
                u = dp[u][i];
            }
        }
        sum += costd[v][0] + costd[u][0];
        return sum;
    }

    public void run() {
        try {
            br = new BufferedReader(new FileReader("tree" + ".in"));
            out = new PrintWriter("tree" + ".out");
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