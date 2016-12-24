import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by Dima Stoyanov.
 */

public class E {


    private class Edge {
        int from, to, w;

        Edge(int from, int to, int w) {
            this.from = from;
            this.to = to;
            this.w = w;
        }
    }


    private class Node {
        int v, d;

        Node(int v, int d) {
            this.v = v;
            this.d = d;
        }
    }


    private ArrayList<Node> nodes[];
    private ArrayList<Edge> edges[];
    private int[] size;
    private boolean[] used, working;
    private int n;
    private int[] maxSon, maxSonW;
    private ArrayList<Pair<Integer, Integer>> comp;

    @SuppressWarnings("unchecked")
    private void solve() throws IOException {
        n = nextInt();
        nodes = new ArrayList[n];
        edges = new ArrayList[n];
        size = new int[n];
        maxSon = new int[n];
        maxSonW = new int[n];
        Arrays.fill(maxSon, -1);
        used = new boolean[n];
        comp = new ArrayList<>();
        working = new boolean[n];
        Treap[] best = new Treap[n];
        for (int i = 0; i < n; i++) {
            edges[i] = new ArrayList<>();
            nodes[i] = new ArrayList<>();
        }
        int from, to, w;
        for (int i = 1; i < n; i++) {
            from = nextInt() - 1;
            to = nextInt() - 1;
            w = nextInt();
            edges[from].add(new Edge(from, to, w));
            edges[to].add(new Edge(to, from, w));
        }
        decompose(0);
        int m = nextInt();
        working[0] = true;
        for (Node e : nodes[0]) {
            best[e.v] = best[e.v].insert(e.d);
        }
        int v;
        String op;
        for (int i = 0; i < m; i++) {
            op = nextToken();
            v = nextInt() - 1;
            switch (op) {
                case "?":
                    int ans = Integer.MAX_VALUE;
                    for (Node e : nodes[v]) {
                        if (!best[e.v].isEmpty()) {
                            ans = Math.min(ans, e.d + best[e.v].kth(0));
                        }
                    }
                    out.println(ans);
                    break;
                case "+":
                    for (Node e : nodes[v]) {
                        best[e.v] = best[e.v].insert(e.d);
                    }
                    break;
                case "-":
                    for (Node e : nodes[v]) {
                        best[e.v] = best[e.v].remove(e.d);
                    }
            }
        }
    }

    private void calculateSize(int v, int p, int distance) {
        size[v] = 1;
        comp.add(new Pair<>(v, distance));
        int maxSonSize = 0;
        for (Edge e : edges[v]) {
            if (e.to != p && !used[e.to]) {
                calculateSize(e.to, v, distance + e.w);
                if (size[e.to] > maxSonSize) {
                    maxSonSize = size[e.to];
                    maxSon[v] = e.to;
                    maxSonW[v] = e.w;
                }
                size[v] += size[e.to];
            }
        }
    }

    private void dfs(int v, int p, int centroid, int d) {
        nodes[v].add(new Node(centroid, d));
        for (Edge e : edges[v]) {
            if (e.to != p && !used[e.to]) {
                dfs(e.to, v, centroid, d + e.w);
            }
        }
    }


    private void decompose(int v) {
        calculateSize(v, -1, 0);
        int vSize = size[v];
        comp.clear();
        int distFromVToCentr = 0;
        while (true) {
            if (maxSon[v] != -1 && !used[maxSon[v]] && size[maxSon[v]] << 1 > vSize) {
                distFromVToCentr += maxSonW[v];
                v = maxSon[v];
            } else {
                break;
            }
        }
        final int dist = distFromVToCentr;
        final int curV = v;
        comp.forEach(o -> nodes[o.getKey()].add(new Node(curV, o.getValue() - dist)));
        used[v] = true;
        for (Edge e : edges[v]) {
            if (!used[e.to])
                decompose(e.to);
        }
    }


    public void run() {
        try {
            br = new BufferedReader(new FileReader("treeeg.in"));
            out = new PrintWriter("treeeg.out");
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
        new E().run();
    }


    Random random = new Random();

    class Treap {
        int key;
        long prio;
        Treap left;
        Treap right;
        int count;

        Treap(int key) {
            this.key = key;
            prio = random.nextLong();
            count = 1;
        }

        void update() {
            count = 1 + getCount(left) + getCount(right);
        }


        int getCount() {
            return getCount(this);
        }

        boolean isEmpty() {
            return getCount() == 0;
        }

        int getCount(Treap root) {
            return root == null ? 0 : root.count;
        }

        class TreapPair {
            Treap left;
            Treap right;

            TreapPair(Treap left, Treap right) {
                this.left = left;
                this.right = right;
            }
        }

        TreapPair split(Treap root, int minRight) {
            if (root == null)
                return new TreapPair(null, null);
            if (root.key >= minRight) {
                TreapPair leftSplit = split(root.left, minRight);
                root.left = leftSplit.right;
                root.update();
                leftSplit.right = root;
                return leftSplit;
            } else {
                TreapPair rightSplit = split(root.right, minRight);
                root.right = rightSplit.left;
                root.update();
                rightSplit.left = root;
                return rightSplit;
            }
        }

        Treap merge(Treap left, Treap right) {
            if (left == null)
                return right;
            if (right == null)
                return left;
            if (left.prio > right.prio) {
                left.right = merge(left.right, right);
                left.update();
                return left;
            } else {
                right.left = merge(left, right.left);
                right.update();
                return right;
            }
        }

        Treap insert(int x) {
            return insert(this, x);
        }

        Treap insert(Treap root, int x) {
            TreapPair t = split(root, x);
            return merge(merge(t.left, new Treap(x)), t.right);
        }

        Treap remove(int x) {
            return remove(this, x);
        }

        Treap remove(Treap root, int x) {
            if (root == null) {
                return null;
            }
            if (x < root.key) {
                root.left = remove(root.left, x);
                root.update();
                return root;
            } else if (x > root.key) {
                root.right = remove(root.right, x);
                root.update();
                return root;
            } else {
                return merge(root.left, root.right);
            }
        }

        int kth(int k) {
            return kth(this, k);
        }

        int kth(Treap root, int k) {
            if (k < getCount(root.left))
                return kth(root.left, k);
            else if (k > getCount(root.left))
                return kth(root.right, k - getCount(root.left) - 1);
            return root.key;
        }
    }
}