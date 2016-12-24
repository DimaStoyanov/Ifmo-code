import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import static java.util.Comparator.comparingInt;

/**
 * Created by Dima Stoyanov.
 */


/*      WA2 :( - правосторонний бинсерч Fixed
        TL13 :(


        1. Строим центр. декомпозицию. Для каждого центроида заполняем массив вершин, входящий в поддерево этого центроида.
        Для каждого считаем расстояние до центроида. Потом сортируем этот массив (по возрастанию расстояния) и по нему строим ДО.
        В вершинах до  будет храниться цвет и нужна реализация массового изменения цвета и гет(to).
        Также в каждом ДО нужен свой мап ревов, чтобы быстро найти нужную вершину. тогда гет будет подниматься.
        Помимо этого у каждого пуш запроса будет поле дата. Так что при поиске ответа замещаются на новый токо если дата позже.
        Для поиска границ обновления юзать бинсерч по массиву расстояний.
        2. На запрос покраски смотрим у текущей вершины все ее центроиды. Пусть надо покрасить на расст d, а расстояние до тек. центроида x.
        Тогда в этом центроиде делаем запросы на расст (d-x).
        3. На запрос гет по каждому центроиду смотрится ответ их гетов.
*/

public class BDBD {

    private class Edge {
        int to, w;

        Edge(int to, int w) {
            this.to = to;
            this.w = w;
        }
    }

    private class Node {
        int v, centroid, distance;

        Node(int v, int centroid, int distance) {
            this.v = v;
            this.centroid = centroid;
            this.distance = distance;
        }
    }

    private class SegmentNode {
        int color, date;

        SegmentNode() {
        }

        void update(SegmentNode node) {
            if (node.date > date) {
                color = node.color;
                date = node.date;
            }
        }

        @Override
        public String toString() {
            return "[" + color + " " + date + "]";
        }
    }

    private class SegmentTree implements Tree {
        int n;
        SegmentNode[] t;
        Map<Integer, Integer> rev;
        int[] dist;

        SegmentTree(ArrayList<Node> vertices) {
            this.n = vertices.size();
            vertices.sort(comparingInt(o -> o.distance));
            rev = new HashMap<>();
            dist = new int[n];
            for (int i = 0; i < vertices.size(); i++) {
                rev.put(vertices.get(i).v, i);
                dist[i] = vertices.get(i).distance;
            }
            t = new SegmentNode[n << 2];
            for (int i = 0; i < t.length; i++) {
                t[i] = new SegmentNode();
            }
        }

        private void push(int v) {
            if (t[v].color != -1) {
                t[v << 1].color = t[v].color;
                t[(v << 1) + 1].color = t[v].color;
                t[v << 1].date = t[v].date;
                t[(v << 1) + 1].date = t[v].date;
                t[v].color = -1;
            }
        }

        private void update(int v, int tl, int tr, int l, int r, int color) {
            if (l > r) return;
            if (l == tl && r == tr) {
                t[v].color = color;
                t[v].date = time;
            } else {
                push(v);
                int tm = (tl + tr) >> 1;
                update(v << 1, tl, tm, l, Math.min(r, tm), color);
                update((v << 1) + 1, tm + 1, tr, Math.max(l, tm + 1), r, color);
            }
        }

        private SegmentNode get(int v, int tl, int tr, int pos) {
            if (tl == tr) {
                return t[v];
            }
            push(v);
            int tm = (tl + tr) >> 1;
            return pos <= tm ? get(v << 1, tl, tm, pos) : get((v << 1) + 1, tm + 1, tr, pos);
        }

        public void update(int l, int r, int color) {
            update(1, 0, n - 1, l, r, color);
        }

        public void update(int d, int color) {
            int r = binarySearch(d) - 1;
            update(0, r, color);
        }

        private int binarySearch(int key) {
            int l = -1;
            int r = n;
            int m;
            while (l < r - 1) {
                m = (l + r) >> 1;
                if (dist[m] <= key) {
                    l = m;
                } else {
                    r = m;
                }
            }
            return r;
        }

        public SegmentNode get(int pos) {
            return get(1, 0, n - 1, rev.get(pos));
        }


    }

    interface Tree {
        SegmentNode get(int position);

        void update(int distance, int color);
    }


    private int time;
    private ArrayList<Node> vertices[], comp;
    private ArrayList<Edge> g[];
    private SegmentTree[] trees;
    private int[] size;
    private boolean used[];

    @SuppressWarnings("unchecked")
    private void solve() throws IOException {
        int n = nextInt();
        vertices = new ArrayList[n];
        g = new ArrayList[n];
        comp = new ArrayList<>();
        trees = new SegmentTree[n];
        size = new int[n];
        used = new boolean[n];
        for (int i = 0; i < n; i++) {
            vertices[i] = new ArrayList<>();
            g[i] = new ArrayList<>();
        }
        time = 0;
        int from, to, w;
        for (int i = 1; i < n; i++) {
            from = nextInt() - 1;
            to = nextInt() - 1;
            w = nextInt();
            g[from].add(new Edge(to, w));
            g[to].add(new Edge(from, w));
        }
        decompose(0);
        int q = nextInt();
        for (int i = 0; i < q; i++) {
            switch (nextInt()) {
                case 1:
                    update(nextInt() - 1, nextInt(), nextInt());
                    break;
                case 2:
                    out.println(get(nextInt() - 1));
                    break;
            }
        }
    }

    private int calculateSize(int v, int p) {
        size[v] = 1;
        for (Edge e : g[v]) {
            if (e.to != p && !used[e.to]) {
                size[v] += calculateSize(e.to, v);
            }
        }
        return size[v];
    }

    private void dfs(int v, int p, int centroid, int distance) {
        final Node node = new Node(v, centroid, distance);
        vertices[v].add(node);
        comp.add(node);
        for (Edge e : g[v]) {
            if (e.to != p && !used[e.to]) {
                dfs(e.to, v, centroid, distance + e.w);
            }
        }
    }

    private void decompose(int v) {
        calculateSize(v, -1);
        int sz = size[v], pv = -1;
        boolean changed = true;
        while (changed) {
            changed = false;
            for (Edge e : g[v]) {
                if (e.to != pv && !used[e.to]) {
                    if (size[e.to] << 1 > sz) {
                        pv = v;
                        v = e.to;
                        changed = true;
                        break;
                    }
                }
            }
        }
        comp = new ArrayList<>();
        dfs(v, -1, v, 0);
        trees[v] = new SegmentTree(comp);
        used[v] = true;
        for (Edge e : g[v]) {
            if (!used[e.to]) decompose(e.to);
        }
    }

    private void update(int v, int distance, int color) {
        time++;
        for (Node node : vertices[v]) {
            if (distance - node.distance >= 0) {
                trees[node.centroid].update(distance - node.distance, color);
            }
        }
    }

    private int get(int v) {
        SegmentNode ans = new SegmentNode();
        ans.date = -1;
        for (Node node : vertices[v]) {
            ans.update(trees[node.centroid].get(v));
        }
        return ans.color;
    }

    public void run() {
        try {
            br = new BufferedReader(new FileReader("lwdb.in"));
            out = new PrintWriter("lwdb.out");
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
        new BDBD().run();
    }
}