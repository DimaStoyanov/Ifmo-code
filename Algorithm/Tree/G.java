package Algorithm.Tree;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Random;
import java.util.StringTokenizer;

public class G {
    private static final Random rand = new Random();

    public static void main(String args[]) throws Exception {
        Scanner sc = new Scanner(new File("river.in"));
        PrintWriter out = new PrintWriter(new File("river.out"));
        RiverTree rvt = new RiverTree();
        int n = sc.nextInt();
        sc.nextInt();
        for (int i = 0; i < n; i++) {
            rvt.add(i, sc.nextLong());
        }
        out.println(rvt.ans());
        int m = sc.nextInt();
        for (int i = 0; i < m; i++) {
            if (sc.nextInt() == 1) {
                rvt.bankrupt(sc.nextInt());
                out.println(rvt.ans());
            } else {
                rvt.fragmentation(sc.nextInt());
                out.println(rvt.ans());
            }

        }
        out.close();
        sc.close();
    }

    static class RiverTree {
        Node root;
        int size;

        RiverTree() {
            root = null;
            size = 0;
        }

        private int size(Node node) {
            return (node != null) ? node.size : 0;
        }

        private Node merge(Node l, Node r) {
            if (l == null) {
                return r;
            }
            if (r == null) {
                return l;
            }

            if (l.y > r.y) {
                return new Node(l.len, l.y, l.left, merge(l.right, r));
            } else {
                return new Node(r.len, r.y, merge(l, r.left), r.right);
            }
        }

        private PairNode split(Node current, int x) {
            PairNode temp = new PairNode(null, null);
            PairNode t = new PairNode(null, null);
            int currentIndex = size(current.left) + 1;

            if (currentIndex <= x) {
                if (current.right == null) {
                    temp.right = null;
                } else {
                    t = split(current.right, x - currentIndex);
                    temp.right = t.right;
                }
                temp.left = new Node(current.len, current.y, current.left, t.left);
            } else {
                if (current.left == null) {
                    temp.left = null;
                } else {
                    t = split(current.left, x);
                    temp.left = t.left;
                }
                temp.right = new Node(current.len, current.y, t.right, current.right);
            }

            return temp;
        }

        void add(int x, long len) {
            if (root == null) {
                root = new Node(len, rand.nextInt(), null, null);
            } else {
                PairNode temp = split(root, x);
                root = merge(merge(temp.left, new Node(len, rand.nextInt(), null, null)), temp.right);
            }

            size++;
        }

        long remove(int x) {
            long len = 0;

            if (root != null) {
                Node t;
                PairNode temp = split(root, x);
                t = temp.left;
                temp = split(temp.right, 1);
                root = merge(t, temp.right);
                len = temp.left.len;
            }

            size--;
            return len;
        }

        void fragmentation(int x) {
            x--;
            long len = remove(x);
            add(x, len >> 1);
            add(x + 1, (len >> 1) + len % 2);
        }

        void bankrupt(int x) {
            x--;
            if (x == 0) {
                long lf = remove(0);
                long ls = remove(0);
                add(0, lf + ls);
            } else if (x == size - 1) {
                long ll = remove(size - 1);
                long lp = remove(size - 1);
                add(size, lp + ll);
            } else {
                long lenBefore = remove(x - 1);
                long len = remove(x - 1);
                long ln = remove(x - 1);
                add(x - 1, lenBefore + (len >> 1));
                add(x, (len >> 1) + len % 2 + ln);
            }
        }

        long ans() {
            return root.square;
        }

        static class PairNode {
            Node left;
            Node right;

            PairNode(Node l, Node r) {
                left = l;
                right = r;
            }
        }

        static class Node {
            long len;
            long square;

            int y;
            int size;
            Node left;
            Node right;

            Node(long len, int y, Node left, Node right) {
                this.len = len;
                this.y = y;
                this.left = left;
                this.right = right;
                fix();
            }

            private int size(Node node) {
                return (node != null) ? node.size : 0;
            }


            private long square(Node node) {
                return (node != null) ? node.square : 0;
            }

            private void fix() {
                square = square(left) + square(right) + len * len;
                size = size(left) + size(right) + 1;
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
