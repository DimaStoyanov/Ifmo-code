package Algorithm.FlowNetwork;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Locale;
import java.util.StringTokenizer;

import static java.lang.Integer.MAX_VALUE;
import static java.lang.Integer.MIN_VALUE;
import static java.util.Arrays.fill;

/**
 * Created by Dima Stoyanov.
 */

public class F {


    private int n;
    private ArrayList<Edge>[] g;
    private int[] d, o;
    private BufferedReader br;
    private StringTokenizer in;
    private PrintWriter out;

    public static void main(String[] args) throws IOException {
        Locale.setDefault(Locale.US);
        new F().run();
    }

    private void solve() throws IOException {
        n = nextInt() + 1;
        int m = nextInt();
//noinspection unchecked
        g = new ArrayList[n + 1];
        for (int i = 0; i < n + 1; i++) {
            g[i] = new ArrayList<>();
        }
        d = new int[n + 1];
        o = new int[n + 1];

        ArrayList<Edge> ordered = new ArrayList<>();
        int from, to, maxCap, minCap;
        for (int i = 1; i <= m; i++) {
            from = nextInt();
            to = nextInt();
            minCap = nextInt();
            maxCap = nextInt();
            ordered.add(add(from, to, minCap, maxCap - minCap));
            add(from, n, MIN_VALUE, minCap);
            add(0, to, MIN_VALUE, minCap);
        }

        maxFlow();
        for (Edge e : g[0]) {
            if (e.flow < e.maxCap) {
                out.println("NO");
                return;
            }
        }
        out.println("YES");
        ordered.forEach(v -> out.println(v.flow + v.minCap + " "));
    }

    private Edge add(int from, int to, int minCap, int maxCap) {
        if (g[from] == null) g[from] = new ArrayList<>();
        Edge e = new Edge(to, maxCap, minCap);
        g[from].add(e);
        if (g[to] == null) g[to] = new ArrayList<>();
        Edge e2 = new Edge(from, 0, minCap);
        g[to].add(e2);
        e.back = e2;
        e2.back = e;
        return e;
    }

    private void maxFlow() {
        for (int lim = MAX_VALUE; lim > 0; ) {
            if (bfs(lim)) {
                fill(o, 0);
                for (; ; ) if (!dfs(0, lim)) break;
            } else {
                lim /= 2;
            }
        }
    }

    private boolean bfs(int lim) {
        LinkedList<Integer> queue = new LinkedList<>();
        queue.add(0);
        fill(d, -1);
        d[0] = 0;
        int u;
        while (!queue.isEmpty()) {
            u = queue.poll();
            ArrayList<Edge> edges = g[u];
            if (edges != null) {
                for (Edge e : edges) {
                    if (d[e.to] == -1 && e.maxCap - e.flow >= lim) {
                        queue.add(e.to);
                        d[e.to] = d[u] + 1;
                    }
                }
            }
        }
        return d[n] != -1;
    }

    private boolean dfs(int u, int flow) {
        if (flow == 0) return false;
        if (u == n) return true;

        ArrayList<Edge> edges = g[u];
        for (; o[u] < edges.size(); o[u]++) {
            Edge e = edges.get(o[u]);
            if (d[e.to] == d[u] + 1 && e.maxCap - e.flow >= flow) {
                if (dfs(e.to, flow)) {
                    e.flow += flow;
                    e.back.flow -= flow;
                    return true;
                }
            }
        }
        return false;
    }

    public void run() {
        try {
            br = new BufferedReader(new FileReader("circulation.in"));
            out = new PrintWriter("circulation.out");
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

    private static class Edge {
        final int to;
        final int minCap;
        final int maxCap;
        int flow;
        Edge back;


        Edge(int to, int maxCap, int minCap) {
            this.to = to;
            this.minCap = minCap;
            this.maxCap = maxCap;
        }


    }
}