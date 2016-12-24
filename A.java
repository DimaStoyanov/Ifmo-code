import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;

/**
 * Created by Dima Stoyanov.
 */

public class A {

    public class HeavyLight {

        int getNeutralValue() {
            return 0;
        }

        final List<Integer>[] tree;
        final SegmentTree segmentTree;
        final int[] parent;
        final int[] heavy;
        final int[] depth;
        final int[] pathRoot;
        final int[] pos;

        HeavyLight(List<Integer>[] tree) {
            this.tree = tree;
            int n = tree.length;
            segmentTree = new SegmentTree(n);
            parent = new int[n];
            heavy = new int[n];
            depth = new int[n];
            pathRoot = new int[n];
            pos = new int[n];
            Arrays.fill(heavy, -1);
            parent[0] = -1;
            depth[0] = 0;
            dfs(0);
            for (int u = 0, p = 0; u < n; u++) {
                if (parent[u] == -1 || heavy[parent[u]] != u) {
                    for (int v = u; v != -1; v = heavy[v]) {
                        pathRoot[v] = u;
                        pos[v] = p++;
                    }
                }
            }
        }

        int dfs(int u) {
            int size = 1;
            int maxSubtree = 0;
            for (int v : tree[u]) {
                if (v != parent[u]) {
                    parent[v] = u;
                    depth[v] = depth[u] + 1;
                    int subtree = dfs(v);
                    if (maxSubtree < subtree) {
                        maxSubtree = subtree;
                        heavy[u] = v;
                    }
                    size += subtree;
                }
            }
            return size;
        }

        public int query(int u, int v) {
            AtomicInteger res = new AtomicInteger(getNeutralValue()); // just mutable integer
            processPath(u, v, (a, b) -> res.set(segmentTree.queryOperation(res.get(), segmentTree.query(a, b))));
            return res.get();
        }

        public void modify(int u, int v, int delta) {
            processPath(u, v, (a, b) -> segmentTree.modify(a, b, delta));
        }

        void processPath(int u, int v, BiConsumer<Integer, Integer> op) {
            for (; pathRoot[u] != pathRoot[v]; v = parent[pathRoot[v]]) {
                if (depth[pathRoot[u]] > depth[pathRoot[v]]) {
                    int t = u;
                    u = v;
                    v = t;
                }
                op.accept(pos[pathRoot[v]], pos[v]);
            }
            if (u == v) return;
            op.accept(Math.min(pos[u], pos[v]) + (1), Math.max(pos[u], pos[v]));
        }

        class SegmentTree {
            // Modify the following 5 methods to implement your custom operations on the tree.
            // This example implements Add/Sum operations. Operations like Add/Max, Set/Max can also be implemented.
            int modifyOperation(int x, int y) {
                return x + y;
            }

            // query (or combine) operation
            int queryOperation(int leftValue, int rightValue) {
                return leftValue + rightValue;
            }

            int deltaEffectOnSegment(int delta, int segmentLength) {
                if (delta == getNeutralDelta()) return getNeutralDelta();
                // Here you must write a fast equivalent of following slow code:
                // int result = delta;
                // for (int i = 1; i < segmentLength; i++) result = queryOperation(result, delta);
                // return result;
                return delta * segmentLength;
            }

            int getNeutralDelta() {
                return 0;
            }

            int getInitValue() {
                return 0;
            }

            // generic code
            int[] value;
            int[] delta; // delta[i] affects value[i], delta[2*i+1] and delta[2*i+2]

            int joinValueWithDelta(int value, int delta) {
                if (delta == getNeutralDelta()) return value;
                return modifyOperation(value, delta);
            }

            int joinDeltas(int delta1, int delta2) {
                if (delta1 == getNeutralDelta()) return delta2;
                if (delta2 == getNeutralDelta()) return delta1;
                return modifyOperation(delta1, delta2);
            }

            void pushDelta(int i) {
                int d = 0;
                for (; (i >> d) > 0; d++) {
                }
                for (d -= 2; d >= 0; d--) {
                    int x = i >> d;
                    value[x >> 1] = joinNodeValueWithDelta(x >> 1, 1 << (d + 1));
                    delta[x] = joinDeltas(delta[x], delta[x >> 1]);
                    delta[x ^ 1] = joinDeltas(delta[x ^ 1], delta[x >> 1]);
                    delta[x >> 1] = getNeutralDelta();
                }
            }

            SegmentTree(int n) {
                value = new int[2 * n];
                for (int i = 0; i < n; i++) {
                    value[i + n] = getInitValue();
                }
                for (int i = 2 * n - 1; i > 1; i -= 2) {
                    value[i >> 1] = queryOperation(value[i], value[i ^ 1]);
                }
                delta = new int[2 * n];
                Arrays.fill(delta, getNeutralDelta());
            }

            int joinNodeValueWithDelta(int i, int len) {
                return joinValueWithDelta(value[i], deltaEffectOnSegment(delta[i], len));
            }

            public int query(int from, int to) {
                from += value.length >> 1;
                to += value.length >> 1;
                pushDelta(from);
                pushDelta(to);
                int res = 0;
                boolean found = false;
                for (int len = 1; from <= to; from = (from + 1) >> 1, to = (to - 1) >> 1, len <<= 1) {
                    if ((from & 1) != 0) {
                        res = found ? queryOperation(res, joinNodeValueWithDelta(from, len)) : joinNodeValueWithDelta(from, len);
                        found = true;
                    }
                    if ((to & 1) == 0) {
                        res = found ? queryOperation(res, joinNodeValueWithDelta(to, len)) : joinNodeValueWithDelta(to, len);
                        found = true;
                    }
                }
                if (!found) throw new RuntimeException();
                return res;
            }

            public void modify(int from, int to, int delta) {
                from += value.length >> 1;
                to += value.length >> 1;
                pushDelta(from);
                pushDelta(to);
                int a = from;
                int b = to;
                for (; from <= to; from = (from + 1) >> 1, to = (to - 1) >> 1) {
                    if ((from & 1) != 0) {
                        this.delta[from] = joinDeltas(this.delta[from], delta);
                    }
                    if ((to & 1) == 0) {
                        this.delta[to] = joinDeltas(this.delta[to], delta);
                    }
                }
                for (int i = a, len = 1; i > 1; i >>= 1, len <<= 1) {
                    value[i >> 1] = queryOperation(joinNodeValueWithDelta(i, len), joinNodeValueWithDelta(i ^ 1, len));
                }
                for (int i = b, len = 1; i > 1; i >>= 1, len <<= 1) {
                    value[i >> 1] = queryOperation(joinNodeValueWithDelta(i, len), joinNodeValueWithDelta(i ^ 1, len));
                }
            }
        }


    }

    private void solve() throws IOException {
        int n = nextInt();
        int m = nextInt();
        @SuppressWarnings("unchecked") List<Integer> g[] = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            g[i] = new ArrayList<>();
        }
        int from, to;
        for (int i = 0; i < n - 1; i++) {
            from = nextInt() - 1;
            to = nextInt() - 1;
            g[from].add(to);
            g[to].add(from);
        }
        HeavyLight hl = new HeavyLight(g);
        for (int i = 0; i < m; i++) {
            switch (nextToken()) {
                case "P":
                    hl.modify(nextInt() - 1, nextInt() - 1, 1);
                    break;
                case "Q":
                    out.println(hl.query(nextInt() - 1, nextInt() - 1));
            }
        }

    }


    public void run() {
        try {
            br = new BufferedReader(new FileReader("grassplant.in"));
            out = new PrintWriter("grassplant.out");
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
        new A().run();
    }
}