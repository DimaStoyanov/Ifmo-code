package Algorithm.LCA;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * Created by Blackbird on 07.11.2016.
 * Project : Algorithm.LCA.C
 * Start time : 0:06
 */

public class C {
    private int[] h, floor, rev;
    private int[][] st;
    private ArrayList<Integer> g[];
    private int time = 0;
    private BufferedReader br;
    private StringTokenizer in;
    private PrintWriter out;

    public static void main(String[] args) throws IOException {
        Locale.setDefault(Locale.US);
        new C().run();
    }

    public void solve() throws IOException {
        int n = nextInt();
        int m = nextInt();
        init(n);
        int a1 = nextInt();
        int a2 = nextInt();
        long x = nextInt() % n;
        long y = nextInt() % n;
        long z = nextInt() % n;
        int v = lca(a1, a2);
        long sum = v;
        for (int i = 2; i <= m; i++) {
            a1 = (int) ((x * a1 + y * a2 + z) % n);
            a2 = (int) ((x * a2 + y * a1 + z) % n);
            v = lca((a1 + v) % n, a2);
            sum += v;
        }
        out.print(sum);
    }

    private void init(int n) throws IOException {
        h = new int[n];
        g = new ArrayList[n];
        rev = new int[n];
        int v_sz = n * 2 - 1;
        int ln = (int) (Math.log(v_sz) / Math.log(2) + 1);
        st = new int[v_sz][ln];
        floor = new int[v_sz + 1];
        for (int i = 0; i < n; i++) {
            g[i] = new ArrayList<>();
        }

        for (int i = 1; i < n; i++)
            g[nextInt()].add(i);
        dfs(0, 0, 0);
        g = null;
        Arrays.fill(rev, -1);
        for (int i = 0; i < v_sz; i++) {
            if (rev[st[i][0]] == -1)
                rev[st[i][0]] = i;
        }
        preprocess(ln, v_sz);
    }

    private void dfs(int v, int p, int depth) {
        st[time++][0] = v;
        h[v] = depth;
        for (int e : g[v]) {
            if (e != p) {
                dfs(e, v, depth + 1);
                st[time++][0] = v;
            }
        }
    }

    private void preprocess(int ln, int v_sz) {
        for (int i = 2; i <= v_sz; i++) {
            floor[i] = floor[i / 2] + 1;
        }

        int a, b;
        for (int j = 1; j <= ln; j++) {
            for (int i = 1; i + (1 << j) - 1 < v_sz; i++) {
                a = st[i][j - 1];
                b = st[i + (1 << (j - 1))][j - 1];
                st[i][j] = h[a] < h[b] ? a : b;
            }
        }
    }

    private int lca(int v, int u) {
        v = rev[v];
        u = rev[u];
        if (v > u) {
            int temp = v;
            v = u;
            u = temp;
        }
        int sz = floor[u - v + 1];
        int a = st[v][sz];
        int b = st[u + 1 - (1 << sz)][sz];
        return h[a] < h[b] ? a : b;
    }

    public void run() {
        try {
            br = new BufferedReader(new FileReader("lca_rmq" + ".in"));
            out = new PrintWriter("lca_rmq" + ".out");
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