import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by Dima Stoyanov.
 */

public class Rainbow {


    private final int MAX_N = 100;
    private final int MAX_M = 5000;
    private final int MAX_COLOR = 100;


    class Edge {
        int from, to, color;

        Edge(int from, int to, int color) {
            this.from = from;
            this.to = to;
            this.color = color;
        }
    }


    class DSU implements DSUSet {
        private DSUNode[] nodes;

        DSU() {
            nodes = new DSUNode[MAX_N + 1];
        }

        class DSUNode {
            int rank;
            int parent;

            DSUNode(int rank, int parent) {
                this.rank = rank;
                this.parent = parent;
            }
        }

        public int get(int o) {
            return nodes[o].parent == o ? o : (nodes[o].parent = get(nodes[o].parent));
        }

        public void union(int x, int y) {
            x = get(x);
            y = get(y);
            if (x != y) {
                if (nodes[x].rank == nodes[y].rank) {
                    nodes[x].rank++;
                }
                if (nodes[x].rank < nodes[y].rank) {
                    nodes[x].parent = y;
                } else {
                    nodes[y].parent = x;
                }

            }
        }

        public void build() {
            for (int i = 1; i <= n; i++) {
                nodes[i] = new DSUNode(0, i);
            }
            for (int i : j) {
                union(edges[i].from, edges[i].to);
            }
        }

    }

    interface DSUSet {
        int get(int x);

        void union(int x, int y);

        void build();
    }

    private void buildColor() {
        Arrays.fill(colorsUsed, false);
        for (int i : j) {
            colorsUsed[edges[i].color] = true;
        }
    }

    private int n, m;
    private Edge[] edges;
    private Set<Integer> j = new HashSet<>();
    private boolean sj[] = new boolean[MAX_M + 1];
    private boolean[] colorsUsed = new boolean[MAX_COLOR + 1];
    private DSU dsu = new DSU();
    private int[][] subEdges = new int[MAX_M + 1][MAX_M + 1];
    private int[] subEdgesCounter = new int[MAX_M + 1];


    void solve() throws IOException {
        n = nextInt();
        m = nextInt();
        edges = new Edge[m + 1];
        for (int i = 1; i <= m; i++) {
            edges[i] = new Edge(nextInt(), nextInt(), nextInt());
            sj[i] = true;
        }
        boolean[] x2 = new boolean[m + 1];
        boolean[] used = new boolean[m + 1];
        int[] parent = new int[m + 1];
        Set<Integer> jj;
        int q[] = new int[MAX_M + 1];
        int left, right;

        while (true) {
            for (int i = 1; i <= m; i++) {
                subEdgesCounter[i] = 0;
            }
            buildColor();
            jj = j;
            Object array[] = jj.toArray();
            Integer i;
            for (Object o : array) {
                i = (Integer) o;
                j.remove(i);
                sj[i] = true;
                dsu.build();
                for (int j = 1; j <= m; j++) {
                    if (sj[j]) {
                        if (dsu.get(edges[j].from) != dsu.get(edges[j].to)) {
                            subEdges[i][subEdgesCounter[i]++] = j;
                        }
                        if (edges[i].color == edges[j].color || !colorsUsed[edges[j].color]) {
                            subEdges[j][subEdgesCounter[j]++] = i;
                        }
                    }
                }

                sj[i] = true;
                j.add(i);
            }

            dsu.build();
            buildColor();
            Arrays.fill(x2, false);
            Arrays.fill(used, false);
            Arrays.fill(parent, -1);
            left = right = 0;
            for (i = 1; i <= m; i++) {
                if (sj[i]) {
                    if (dsu.get(edges[i].from) != dsu.get(edges[i].to)) {
                        q[right++] = i;
                        used[i] = true;
                    }
                    if (!colorsUsed[edges[i].color]) {
                        x2[i] = true;
                    }
                }
            }

            int last = -1;
            while (left != right) {
                int u = q[left++];
                if (x2[u]) {
                    last = u;
                    break;
                }
                for (i = 0; i < subEdgesCounter[u]; i++) {
                    int v = subEdges[u][i];
                    if (!used[v]) {
                        used[v] = true;
                        parent[v] = u;
                        q[right++] = v;
                    }
                }
            }

            if (last == -1) break;
            while (last != -1) {
                if (!j.contains(last)) {
                    j.add(last);
                    sj[last] = false;
                } else {
                    j.remove(last);
                    sj[last] = true;
                }
                last = parent[last];
            }

        }

        out.println(j.size());

        for (int i : j) {
            out.print(i + " ");
        }
    }


    public void run() {
        try {
            br = new BufferedReader(new FileReader("rainbow.in"));
            out = new PrintWriter("rainbow.out");
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
        new Rainbow().run();
    }
}