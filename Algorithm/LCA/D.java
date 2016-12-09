package Algorithm.LCA;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * Created by Blackbird on 05.11.2016.
 * Project : Algorithm.LCA.D
 * Start time : 18:13
 */

public class D {

    private BufferedReader br;
    private StringTokenizer in;
    private PrintWriter out;

    public static void main(String[] args) throws IOException {
        Locale.setDefault(Locale.US);
        new D().run();
    }

    public void solve() throws IOException {
        int n = nextInt();
        Node[] tree = new Node[n + 1];
        int to;
        for (int i = 1; i <= n; i++) {
            tree[i] = new Node(i);
        }
        for (int i = 1; i <= n; i++) {
            to = nextInt();
            if (to != 0)
                link(tree[i], tree[to]);
        }
        int m = nextInt();
        int lastLca = 0;
        for (int i = 0; i < m; i++) {
            switch (nextInt()) {
                case 0:
                    lastLca = lca(tree[(nextInt() - 1 + lastLca) % n + 1], tree[(nextInt() - 1 + lastLca) % n + 1]);
                    out.println(lastLca);
                    break;
                case 1:
                    link(tree[(nextInt() - 1 + lastLca) % n + 1], tree[(nextInt() - 1 + lastLca) % n + 1]);
                    break;
            }
        }
    }

    private void connect(Node v, Node p, Boolean isLeft) {
        if (v != null)
            v.p = p;
        if (isLeft != null) {
            if (isLeft)
                p.l = v;
            else
                p.r = v;
        }
    }

    private void rotate(Node v) {
        Node p = v.p;
        Node g = p.p;
        boolean isRootP = p.isRoot();
        boolean leftChildX = (v == p.l);
        connect(leftChildX ? v.r : v.l, p, leftChildX);
        connect(p, v, !leftChildX);
        connect(v, g, !isRootP ? p == g.l : null);
    }

    private void splay(Node v) {
        while (!v.isRoot()) {
            Node p = v.p;
            Node g = p.p;
            if (!p.isRoot())
                rotate((v == p.l) == (p == g.l) ? p : v);
            rotate(v);
        }
    }

    private Node expose(Node x) {
        Node last = null;
        for (Node u = x; u != null; u = u.p) {
            splay(u);
            u.l = last;
            last = u;
        }
        splay(x);
        return last;
    }

    private Node findRoot(Node x) {
        expose(x);
        Node t = x;
        while (t.r != null)
            t = t.r;
        splay(t);
        return t;
    }

    private void link(Node x, Node y) {
        expose(x);
        x.p = y;
    }

    public int lca(Node v, Node u) {
        if (findRoot(v) != findRoot(u))
            return 0;
        expose(v);
        return expose(u).id;
    }

    public void run() {
        try {
            br = new BufferedReader(new FileReader("lca3" + ".in"));
            out = new PrintWriter("lca3" + ".out");
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

    public static class Node {
        Node l;
        Node r;
        Node p;
        int id;

        public Node(int id) {
            this.id = id;
        }

        boolean isRoot() {
            return p == null || (p.l != this && p.r != this);
        }
    }

}