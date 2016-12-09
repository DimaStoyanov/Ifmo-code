package Algorithm.Tree;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 * Created by Дима1 on 17.12.2015.
 */
public class E {
    public static void main(String[] args) throws Exception {
        int n;
        Scanner sc = new Scanner(new File("rmq2.in"));
        PrintWriter out = new PrintWriter(new File("rmq2.out"));
        n = sc.nextInt();
        RMQ2 tree = new RMQ2(n);
        for (int i = 0; i < n; i++) {
            tree.addFirst(i, sc.nextLong());
        }
        tree.build();
        while (sc.hasNext()) {
            String cmd = sc.next();
            switch (cmd.charAt(0)) {
                case 's': {
                    tree.set(sc.nextInt(), sc.nextInt(), sc.nextLong());
                    break;
                }
                case 'a': {
                    tree.add(sc.nextInt(), sc.nextInt(), sc.nextLong());
                    break;
                }
                case 'm': {
                    out.println(tree.min(sc.nextInt(), sc.nextInt()));
                }
            }
        }
        sc.close();
        out.close();
    }

    static class RMQ2 {
        int n;
        Node list[];

        RMQ2(int n) {
            this.n = 1;
            while (this.n < n) {
                this.n <<= 1;
            }
            list = new Node[this.n << 1];
            for (int i = this.n + n - 1; i < (this.n << 1); i++)
                list[i] = new Node(Long.MAX_VALUE);
        }

        void addFirst(int num, long x) {
            list[n + num] = new Node(x);
            list[n + num].makeLeaf();
        }

        void build() {
            buildR(1, n, 2 * n - 1);
        }

        void buildR(int v, int tl, int tr) {

            if (tl != tr) {

                int tm = (tl + tr) / 2;
                buildR(v * 2, tl, tm);
                buildR(v * 2 + 1, tm + 1, tr);

                list[v] = new Node(Math.min(list[v * 2].value, list[v * 2 + 1].value));
            }
        }

        void push(int v) {
            if (list[v].isLeaf) return;
            if (list[v].hasColor) {
                list[2 * v].set(list[v].color);
                list[2 * v + 1].set(list[v].color);
                list[v].clearColor();
            }
            if (list[v].hasInconsistency) {
                list[v * 2].add(list[v].inconsistency);
                list[v * 2 + 1].add(list[v].inconsistency);
                list[v].clearInconsistency();
            }
        }

        Node compareNodes(Node a, Node b) {
            if (a.value >= b.value)
                return b;
            else
                return a;
        }

        long min(int l, int r) {
            return minR(1, n, 2 * n - 1, n + l - 1, n + r - 1).value;
        }

        Node minR(int v, int tl, int tr, int l, int r) {
            if (l > r) return new Node(Long.MAX_VALUE);
            if (tl == l && tr == r) return list[v];
            push(v);
            int tm = (tl + tr) / 2;
            Node left = minR(v * 2, tl, tm, l, Math.min(tm, r));
            Node right = minR(v * 2 + 1, tm + 1, tr, Math.max(tm + 1, l), r);
            return compareNodes(left, right);
        }

        void set(int l, int r, long x) {
            setR(1, n, 2 * n - 1, n + l - 1, n + r - 1, x);
        }

        void setR(int v, int tl, int tr, int l, int r, long value) {
            if (l > r) return;
            if (l == tl && r == tr) {
                list[v].set(value);
                return;
            }
            push(v);
            int tm = (tl + tr) / 2;
            setR(v * 2, tl, tm, l, Math.min(r, tm), value);
            setR(v * 2 + 1, tm + 1, tr, Math.max(l, tm + 1), r, value);
            list[v].value = compareNodes(list[2 * v], list[2 * v + 1]).value;
        }

        void add(int l, int r, long x) {
            addR(1, n, 2 * n - 1, n + l - 1, n + r - 1, x);
        }

        void addR(int v, int tl, int tr, int l, int r, long value) {
            if (l > r) return;
            if (tl == l && tr == r) {
                list[v].add(value);
                return;
            }
            push(v);
            int tm = (tl + tr) / 2;
            addR(v * 2, tl, tm, l, Math.min(r, tm), value);
            addR(v * 2 + 1, tm + 1, tr, Math.max(tm + 1, l), r, value);
            list[v].value = compareNodes(list[2 * v], list[2 * v + 1]).value;
        }

        static class Node {
            long value;
            boolean isLeaf;
            boolean hasColor;
            boolean hasInconsistency;
            long inconsistency;
            long color;

            Node(long a) {
                value = a;
                isLeaf = false;
                hasColor = false;
                hasInconsistency = false;
            }

            void makeLeaf() {
                isLeaf = true;
            }

            void clearColor() {
                hasColor = false;
            }

            void clearInconsistency() {
                hasInconsistency = false;
                inconsistency = 0;
            }

            void set(long x) {
                if (isLeaf) {
                    value = x;
                    return;
                }
                if (hasInconsistency)
                    clearInconsistency();

                color = x;
                hasColor = true;
                value = x;
            }

            void add(long x) {
                if (isLeaf) {
                    value += x;
                    return;
                }
                if (hasColor) {
                    color += x;
                    value += x;
                    return;
                }
                inconsistency += x;
                hasInconsistency = true;
                value += x;
            }

        }
    }

    static class Scanner {
        BufferedReader br;
        StringTokenizer t = new StringTokenizer("");
        String savedToken;
        boolean flag;

        Scanner(File file) throws Exception {


            br = new BufferedReader(new FileReader(file));
            StringTokenizer t = new StringTokenizer("");
        }

        int nextInt() throws Exception {
            return Integer.parseInt(next());
        }

        long nextLong() throws Exception {
            return Long.parseLong(next());
        }

        boolean hasNext() throws Exception {
            if (flag)
                return true;
            while (!t.hasMoreTokens()) {
                String line = br.readLine();
                if (line == null) {
                    return false;
                }
                t = new StringTokenizer(line);
            }
            return t.hasMoreTokens();
        }

        String next() throws Exception {
            if (hasNext()) {
                if (flag) {
                    flag = false;
                    return savedToken;
                }
                return t.nextToken();
            } else {
                return null;
            }
        }

        private String nextToken() throws Exception {
            savedToken = next();
            flag = true;
            return savedToken;
        }

        void close() throws Exception {
            br.close();
        }
    }
}
