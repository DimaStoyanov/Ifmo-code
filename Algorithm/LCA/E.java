package Algorithm.LCA;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * Created by Blackbird on 05.11.2016.
 * Project : Algorithm.LCA.E
 * Start time : 0:14
 */

public class E {
    private final int MAX = 100000;
    private final int LN = 18;
    private ArrayList<Integer> g[] = new ArrayList[MAX];
    private int[] d = new int[MAX];
    private int[] tin = new int[MAX];
    private int[] tout = new int[MAX];
    private int[] p = new int[MAX];
    private int[][] sptable = new int[MAX][LN];
    private int time = 0;
    private BufferedReader br;
    private StringTokenizer in;
    private PrintWriter out;

    public static void main(String[] args) throws IOException {
        Locale.setDefault(Locale.US);
        new E().run();
    }

    public void solve() throws IOException {
        int n = nextInt();
        while (n != 0) {
            build(n);
            n = nextInt();
        }
    }

    private void build(int n) throws IOException {
        clear();
        int from, to;
        for (int i = 0; i < n - 1; i++) {
            from = nextInt() - 1;
            to = nextInt() - 1;
            g[from].add(to);
            g[to].add(from);
        }
        dfs(0, 0, 0);
        calculate(n);
        int m = nextInt(), a, b, defLca, rootStartLca, rootEndLca;
        int currentRoot = 0;
        for (int i = 0; i < m; i++) {
            switch (nextToken()) {
                case "?":
                    a = nextInt() - 1;
                    b = nextInt() - 1;
                    defLca = lca(a, b);
                    rootStartLca = lca(a, currentRoot);
                    rootEndLca = lca(currentRoot, b);
                    if (d[rootStartLca] > d[defLca])
                        defLca = rootStartLca;
                    if (d[rootEndLca] > d[defLca])
                        defLca = rootEndLca;
                    out.println(defLca + 1);
                    break;
                case "!":
                    currentRoot = nextInt() - 1;
                    break;
            }
        }
    }

    private void calculate(int n) {
        for (int i = 0; i < n; i++) {
            sptable[i][0] = p[i];
        }
        for (int i = 0; i < LN - 1; i++) {
            for (int j = 0; j < n; j++) {
                sptable[j][i + 1] = sptable[sptable[j][i]][i];
            }
        }
    }

    private int lca(int u, int v) {
        for (int i = LN - 1; i >= 0; i--)
            if (!isAncestor(sptable[u][i], v))
                u = sptable[u][i];
        return isAncestor(u, v) ? u : p[u];
    }

    private boolean isAncestor(int u, int v) {
        return tin[u] <= tin[v] && tout[u] >= tout[v];
    }

    private void dfs(int v, int curP, int depth) {
        tin[v] = time++;
        p[v] = curP;
        d[v] = depth;
        (g[v]).stream().filter(e -> e != curP).forEach(e -> dfs(e, v, depth + 1));
        tout[v] = time++;
    }

    @SuppressWarnings("unchecked")
    private void clear() {
        for (int i = 0; i < MAX; i++) {
            g[i] = new ArrayList<>();
        }
    }

    public void run() {
        try {
            br = new BufferedReader(new FileReader("dynamic" + ".in"));
            out = new PrintWriter("dynamic" + ".out");
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