package Algorithm.FlowNetwork;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import static java.lang.Long.MAX_VALUE;

/**
 * Created by Dima Stoyanov.
 */

public class H {


    private BufferedReader br;
    private StringTokenizer in;
    private PrintWriter out;

    public static void main(String[] args) throws IOException {
        Locale.setDefault(Locale.US);
        new H().run();
    }

    private void solve() throws IOException {
        new MinCostFlow().main();
    }

    public void run() {
        try {
            br = new BufferedReader(new FileReader("mincost.in"));
            out = new PrintWriter("mincost.out");
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

    public class MinCostFlow {

        List<Edge>[] createGraph(int n) {
            @SuppressWarnings("unchecked") List<Edge>[] graph = new List[n];
            for (int i = 0; i < n; i++)
                graph[i] = new ArrayList<>();
            return graph;
        }

        void addEdge(List<Edge>[] graph, int s, int t, int cap, int cost) {
            graph[s].add(new Edge(t, cap, cost, graph[t].size()));
            graph[t].add(new Edge(s, 0, -cost, graph[s].size() - 1));
        }

        void bellmanFord(List<Edge>[] graph, int[] dist) {
            int n = graph.length;
            Arrays.fill(dist, Integer.MAX_VALUE);
            dist[0] = 0;
            boolean[] inQueue = new boolean[n];
            int[] q = new int[n];
            int qt = 0;
            q[qt++] = 0;
            for (int qh = 0; (qh - qt) % n != 0; qh++) {
                int u = q[qh % n];
                inQueue[u] = false;
                for (int i = 0; i < graph[u].size(); i++) {
                    Edge e = graph[u].get(i);
                    if (e.cap <= e.f)
                        continue;
                    int v = e.to;
                    int ndist = dist[u] + e.cost;
                    if (dist[v] > ndist) {
                        dist[v] = ndist;
                        if (!inQueue[v]) {
                            inQueue[v] = true;
                            q[qt++ % n] = v;
                        }
                    }
                }
            }
        }

        private long minCostFlow(List<Edge>[] graph, int t, long maxF) {
            int n = graph.length;
            long[] priority = new long[n];
            long[] curFlow = new long[n];
            int[] prevEdge = new int[n];
            int[] prevNode = new int[n];
            int[] pot = new int[n];

// bellmanFord invocation can be skipped if edges costs are non-negative
            bellmanFord(graph, pot);
            int flow = 0;
            long flowCost = 0;
            while (flow < maxF) {
                PriorityQueue<Long> q = new PriorityQueue<>();
                q.add((long) 0);
                Arrays.fill(priority, MAX_VALUE);
                priority[0] = 0;
                boolean[] finished = new boolean[n];
                curFlow[0] = MAX_VALUE;
                while (!finished[t] && !q.isEmpty()) {
                    long cur = q.remove();
                    int u = (int) (cur & 0xFFFF_FFFFL);
                    int prioU = (int) (cur >>> 32);
                    if (prioU != priority[u])
                        continue;
                    finished[u] = true;
                    for (int i = 0; i < graph[u].size(); i++) {
                        Edge e = graph[u].get(i);
                        if (e.f >= e.cap)
                            continue;
                        int v = e.to;
                        long nPriority = priority[u] + e.cost + pot[u] - pot[v];
                        if (priority[v] > nPriority) {
                            priority[v] = nPriority;
                            q.add((nPriority << 32) + v);
                            prevNode[v] = u;
                            prevEdge[v] = i;
                            curFlow[v] = Math.min(curFlow[u], e.cap - e.f);
                        }
                    }
                }
                if (priority[t] == MAX_VALUE)
                    break;
                for (int i = 0; i < n; i++)
                    if (finished[i])
                        pot[i] += priority[i] - priority[t];
                long df = Math.min(curFlow[t], maxF - flow);
                flow += df;
                for (int v = t; v != 0; v = prevNode[v]) {
                    Edge e = graph[prevNode[v]].get(prevEdge[v]);
                    e.f += df;
                    graph[v].get(e.rev).f -= df;
                    flowCost += df * e.cost;
                }
            }
            return flowCost;
        }

        public void main() throws IOException {
            int n = nextInt();
            int m = nextInt();
            List<Edge>[] graph = createGraph(n);
            for (int i = 0; i < m; i++) {
                addEdge(graph, nextInt() - 1, nextInt() - 1, nextInt(), nextInt());
            }
            out.println(minCostFlow(graph, n - 1, MAX_VALUE));
        }

        class Edge {
            int to, f, cap, cost, rev;

            Edge(int v, int cap, int cost, int rev) {
                this.to = v;
                this.cap = cap;
                this.cost = cost;
                this.rev = rev;
            }
        }
    }
}