package Algorithm.ShortPath;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * Created by Blackbird on 10.05.2016.
 * Project : Algorithm.ShortPath.E
 * Start time : 19:39
 */

public class E {

    private int n;
    private ArrayList<Integer>[] g;
    private ArrayList<Integer> neg;
    private boolean used[];
    private int[] p;
    private BufferedReader br;
    private StringTokenizer in;
    private PrintWriter out;

    public static void main(String[] args) throws IOException {
        Locale.setDefault(Locale.US);
        new E().run();
    }

    public void solve() throws IOException {
        n = nextInt();
        int m = nextInt();
        int s = nextInt() - 1;
        Edge e[] = new Edge[m];
        g = new ArrayList[n];
        neg = new ArrayList<>();
        for (int i = 0; i < n; i++)
            g[i] = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            int from = nextInt() - 1;
            int to = nextInt() - 1;
            BigInteger w = new BigInteger(nextToken());
            g[from].add(to);
            e[i] = new Edge(from, to, w);
        }
        BigInteger[] d = new BigInteger[n];
        Arrays.fill(d, BigInteger.valueOf(Long.MAX_VALUE));
        d[s] = BigInteger.ZERO;
        p = new int[n];
        Arrays.fill(p, -1);
        int x = -1;
        for (int i = 0; i < n; i++) {
            x = -1;
            for (int j = 0; j < m; j++) {
                if (d[e[j].from].compareTo(BigInteger.valueOf(Long.MAX_VALUE)) < 0) {
                    if (d[e[j].to].compareTo(d[e[j].from].add(e[j].w)) > 0) {
                        d[e[j].to] = d[e[j].from].add(e[j].w);
                        p[e[j].to] = e[j].from;
                        x = e[j].to;
                    }
                }
            }
        }
        if (x != -1) {
            int vInCycle = x;
            for (int i = 0; i < n; i++)
                vInCycle = p[vInCycle];
            used = new boolean[n];
            dfs(vInCycle);
        }
        for (int i = 0; i < n; i++)
            out.println(neg.contains(i) ? "-" : d[i].compareTo(BigInteger.valueOf(Long.MAX_VALUE)) == 0 ? "*" : d[i].toString());
    }

    private int getVInCycle(int x) {
        int vInCycle = x;
        for (int i = 0; i < n; i++)
            vInCycle = p[vInCycle];
        return vInCycle;
    }

    private void dfs(int v) {
        neg.add(v);
        used[v] = true;
        (g[v]).stream().filter(i -> !used[i]).forEach(this::dfs);
    }

    public void run() {
        try {
            br = new BufferedReader(new FileReader("path" + ".in"));
            out = new PrintWriter("path" + ".out");
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

    private class Edge {
        public int from;
        public int to;
        public BigInteger w;

        public Edge(int a, int b, BigInteger c) {
            from = a;
            to = b;
            w = c;
        }
    }

}