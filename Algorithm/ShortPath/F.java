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
 * Created by Blackbird on 10.05.2016.
 * Project : Algorithm.ShortPath.F
 * Start time : 2:43
 */

public class F {
    private BufferedReader br;
    private StringTokenizer in;
    private PrintWriter out;

    public static void main(String[] args) throws IOException {
        Locale.setDefault(Locale.US);
        new F().run();
    }

    public void solve() throws IOException {
        int n = nextInt();
        int sqrN = n * n;
        int count = 0;
        Edge[] e = new Edge[sqrN];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) {
                long w = nextLong();
                if (w != 1e9)
                    e[count++] = new Edge(i, j, w);
            }
        long[] d = new long[n];
        int[] p = new int[n];
        Arrays.fill(p, -1);
        int last = -1;
        for (int i = 0; i < n; ++i) {
            last = -1;
            for (int j = 0; j < count; ++j)
                if (d[e[j].from] < Long.MAX_VALUE)
                    if (d[e[j].to] > d[e[j].from] + e[j].w) {
                        d[e[j].to] = Math.max(-Long.MAX_VALUE, d[e[j].from] + e[j].w);
                        p[e[j].to] = e[j].from;
                        last = e[j].to;
                    }
        }
        if (last == -1)
            out.println("NO");
        else {
            int y = last;
            for (int i = 0; i < n; ++i)
                y = p[y];
            ArrayList<Integer> path = new ArrayList<>();
            for (int cur = y; ; cur = p[cur]) {
                path.add(cur);
                if (cur == y && path.size() > 1) break;
            }

            out.println("YES");
            out.println(path.size());
            for (int i = path.size() - 1; i > -1; i--)
                out.print((path.get(i) + 1) + " ");
        }
    }

    public void run() {
        try {
            br = new BufferedReader(new FileReader("negcycle" + ".in"));
            out = new PrintWriter("negcycle" + ".out");
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

    public class Edge {
        public int from;
        public int to;
        public long w;

        public Edge(int a, int b, long c) {
            from = a;
            to = b;
            w = c;
        }
    }

}