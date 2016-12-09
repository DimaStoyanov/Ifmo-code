package Discrete_Math.Matroid;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * Created by Dima Stoyanov.
 */

public class C {


    private boolean[] used;
    private int match[];
    private ArrayList<Integer> g[];

    private void solve() throws IOException {
        int n = nextInt();
        Vertex[] vertices = new Vertex[n];
        for (int i = 0; i < n; i++) {
            vertices[i] = new Vertex(i, nextInt());
        }
        Arrays.sort(vertices, (o1, o2) -> o2.cost - o1.cost);
        g = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            g[i] = new ArrayList<>();
        }
        int cap;
        for (int i = 0; i < n; i++) {
            cap = nextInt();
            for (int j = 0; j < cap; j++) {
                g[i].add(nextInt() - 1);
            }
        }
        used = new boolean[n];
        match = new int[n];
        Arrays.fill(match, -1);
        for (Vertex v : vertices) {
            Arrays.fill(used, false);
            kuhn(v.v);
        }
        int[] ans = new int[n + 1];
        for (int i = 0; i < n; i++) {
            if (match[i] != -1)
                ans[match[i] + 1] = i + 1;

        }
        for (int i = 1; i <= n; i++) {
            out.print((ans[i]) + " ");
        }
    }

    private class Vertex {
        private final int v;
        private final int cost;

        Vertex(final int v, final int cost) {
            this.v = v;
            this.cost = cost;
        }

        @Override
        public String toString() {
            return "v= " + v + " cost= " + cost;
        }
    }

    private boolean kuhn(int v) {
        if (used[v]) return false;
        used[v] = true;
        for (int e : g[v]) {
            if (match[e] == -1 || kuhn(match[e])) {
                match[e] = v;
                return true;
            }
        }
        return false;
    }


    public void run() {
        try {
            br = new BufferedReader(new FileReader("matching.in"));
            out = new PrintWriter("matching.out");
            solve();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }


    private BufferedReader br;
    private StringTokenizer in;
    private PrintWriter out;

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

    public static void main(String[] args) throws IOException {
        Locale.setDefault(Locale.US);
        new C().run();
    }
}