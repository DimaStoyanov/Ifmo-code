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

public class B {

    private int[] p, rank;

    private class Edge {
        private final int from;
        private final int to;
        private final long cost;
        private final int index;

        Edge(int from, int to, long cost, int index) {
            this.from = from;
            this.to = to;
            this.cost = cost;
            this.index = index;
        }
    }

    private void solve() throws IOException {
        int n = nextInt();
        int m = nextInt();
        long limit = nextLong();
        Edge[] edges = new Edge[m];
        for (int i = 0; i < m; i++) {
            edges[i] = new Edge(nextInt(), nextInt(), nextLong(), i + 1);
        }
        Arrays.sort(edges, (o1, o2) -> o2.cost > o1.cost ? 1 : o2.cost == o1.cost ? 0 : -1);
        p = new int[n + 1];
        rank = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            makeSet(i);
        }
        ArrayList<Edge> restEdges = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            if (find(edges[i].from) != find(edges[i].to)) {
                union(edges[i].from, edges[i].to);
            } else {
                restEdges.add(edges[i]);
            }
        }
        int index = restEdges.size() - 1;
        long sum = 0;
        ArrayList<Integer> deletingEdges = new ArrayList<>();
        while (index >= 0 && sum + restEdges.get(index).cost <= limit) {
            deletingEdges.add(restEdges.get(index).index);
            sum += restEdges.get(index--).cost;
        }
        out.println(deletingEdges.size());
        deletingEdges.forEach(integer -> out.print(integer + " "));
    }

    private void makeSet(int v) {
        p[v] = v;
        rank[v] = 0;
    }

    private int find(int v) {
        if (v == p[v])
            return v;
        return p[v] = find(p[v]);
    }

    private void union(int a, int b) {
        a = find(a);
        b = find(b);
        if (a != b) {
            if (rank[a] < rank[b]) {
                int temp = a;
                a = b;
                b = temp;
            }
            p[b] = a;
            if (rank[a] == rank[b])
                ++rank[a];
        }
    }

    public void run() {
        try {
            br = new BufferedReader(new FileReader("destroy.in"));
            out = new PrintWriter("destroy.out");
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
        new B().run();
    }
}