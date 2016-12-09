package Algorithm.LCA;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * Created by Blackbird on 04.11.2016.
 * Project : Algorithm.LCA.J
 * Start time : 16:47
 */

@SuppressWarnings("unchecked")
public class J {
    private boolean[][] adj;
    private ArrayList<Integer> g[];
    private int[][] revV, revH, dp, edgdp;
    private int[] pow, d;
    private int n, m;
    private int v = 0, ln;
    private BufferedReader br;
    private StringTokenizer in;
    private PrintWriter out;

    public static void main(String[] args) throws IOException {
        Locale.setDefault(Locale.US);
        new J().run();
    }

    public void solve() throws IOException {
        n = nextInt();
        m = nextInt();
        revV = new int[n][m];
        revH = new int[n][m];
        adj = new boolean[n][m];
        String line;
        boolean lastVertex;
        for (int i = 0; i < n; i++) {
            line = nextToken();
            lastVertex = false;
            for (int j = 0; j < m; j++) {
                if (line.charAt(j) == '#') {
                    adj[i][j] = true;
                    revH[i][j] = v;
                    lastVertex = true;
                    if (j == m - 1) v++;
                } else {
                    if (lastVertex) {
                        v++;
                        lastVertex = false;
                    }
                }
            }
        }
        calculateVerticalVertex();
        buildGraph();
        d = new int[v];
        ln = (int) (Math.log(v) / Math.log(2) + 1);
        dp = new int[v][ln];
        edgdp = new int[v][ln];
        dfs(0, 0, 0);
        preprocess();
        int a1, a2, a3, a4, res1, res2, res3, res4;
        int k = nextInt();
        for (int i = 0; i < k; i++) {
            a1 = nextInt() - 1;
            a2 = nextInt() - 1;
            a3 = nextInt() - 1;
            a4 = nextInt() - 1;
            res1 = lca(revV[a1][a2], revV[a3][a4]);
            res2 = lca(revV[a1][a2], revH[a3][a4]);
            res3 = lca(revH[a1][a2], revV[a3][a4]);
            res4 = lca(revH[a1][a2], revH[a3][a4]);
            out.println(min(res1, res2, res3, res4));
        }
    }

    private int min(int... a) {
        int min = Integer.MAX_VALUE;
        for (int x : a)
            if (x < min)
                min = x;
        return min;
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
                sum += edgdp[u][i];
                u = dp[u][i];
            }
        }
        if (v == u) return sum;
        for (int i = ln - 1; i >= 0; i--) {
            if (dp[v][i] != dp[u][i]) {
                sum += edgdp[v][i] + edgdp[u][i];
                v = dp[v][i];
                u = dp[u][i];
            }
        }
        return sum + edgdp[v][0] + edgdp[u][0];
    }

    private void calculateVerticalVertex() {
        boolean lastVertex;
        for (int i = 0; i < m; i++) {
            lastVertex = false;
            for (int j = 0; j < n; j++) {
                if (adj[j][i]) {
                    revV[j][i] = v;
                    lastVertex = true;
                    if (j == n - 1) v++;
                } else {
                    if (lastVertex) {
                        v++;
                        lastVertex = false;
                    }
                }
            }
        }
    }

    private void buildGraph() {
        g = new ArrayList[v];
        for (int i = 0; i < v; i++) {
            g[i] = new ArrayList<>();
        }
        HashSet<Integer> edges[] = new HashSet[v];
        for (int i = 0; i < v; i++) {
            edges[i] = new HashSet<>();
        }
//        boolean[][] edges = new boolean[v][v];
        int from, to;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (adj[i][j]) {
                    from = revH[i][j];
                    to = revV[i][j];
                    if (!edges[from].contains(to)) {
                        g[from].add(to);
                        g[to].add(from);
                        edges[from].add(to);
//                        edges[from][to] = true;
                    }
                }
            }
        }
    }

    private void dfs(int v, int p, int depth) {
        d[v] = depth;
        dp[v][0] = p;
        edgdp[v][0] = 1;
        (g[v]).stream().filter(e -> e != p).forEach(e -> dfs(e, v, depth + 1));
    }

    private void preprocess() {
        pow = new int[ln];
        pow[0] = 1;
        for (int i = 1; i < ln; i++) {
            pow[i] = pow[i - 1] << 1;
        }
        for (int j = 1; j < ln; j++) {
            for (int i = 1; i < v; i++) {
                dp[i][j] = dp[dp[i][j - 1]][j - 1];
                edgdp[i][j] = edgdp[i][j - 1] + edgdp[dp[i][j - 1]][j - 1];
            }
        }
    }

    public void run() {
        try {
            br = new BufferedReader(new FileReader("turtles" + ".in"));
            out = new PrintWriter("turtles" + ".out");
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