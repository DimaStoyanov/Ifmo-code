import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * Created by Dima Stoyanov.
 */

public class C {


    private Vertex g[];


    private class Vertex {
        private ArrayList<Integer> edges;
        private int parent;
        private int size;
        private boolean used;


        Vertex() {
            edges = new ArrayList<>();
        }

        @Override
        public String toString() {
            return edges.toString() + " p " + parent + " s " + size + " from " + used;
        }
    }

    private void solve() throws IOException {
        int n = nextInt();
        //noinspection unchecked
        g = new Vertex[n];
        for (int i = 0; i < n; i++) {
            g[i] = new Vertex();
        }
        int from, to;
        for (int i = 1; i < n; i++) {
            from = nextInt() - 1;
            to = nextInt() - 1;
            g[from].edges.add(to);
            g[to].edges.add(from);
        }
        dfs(0, -1);
        for (Vertex vertex : g) {
            out.print((vertex.parent + 1) + " ");
        }
    }

    private void preProcess(int v, int parent) {
        g[v].parent = parent;
        g[v].size = 1;
        for (int e : g[v].edges) {
            if (e != parent && !g[e].used) {
                preProcess(e, v);
                g[v].size += g[e].size;
            }
        }
    }


    private void dfs(int v, int parent) {
        preProcess(v, -1);
        v = findCentroid(v, g[v].size);
        g[v].used = true;
        g[v].parent = parent;
        for (int e : g[v].edges) {
            if (!g[e].used) {
                dfs(e, v);
            }
        }
    }

    private int findCentroid(int v, int n) {
        n = n >> 1;
        int maxSize, maxVertex = -1;
        while (true) {
            maxSize = 0;
            for (int e : g[v].edges) {
                if (!g[e].used && getSize(v, e) > maxSize) {
                    maxSize = getSize(v, e);
                    maxVertex = e;
                }
            }
            if (maxSize <= n) return v;
            v = maxVertex;
        }
    }


    private int getSize(int v, int e) {
        return g[v].parent == e ? g[e].size - g[v].size : g[e].size;
    }


    public void run() {
        try {
            br = new BufferedReader(new FileReader("decomposition.in"));
            out = new PrintWriter("decomposition.out");
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