package Algorithm.Tree;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 * Created by Дима1 on 17.12.2015.
 */
public class C {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(new File("bst.in"));
        PrintWriter out = new PrintWriter(new File("bst.out"));
        avl.Node p = new avl.Node();
        avl bst = new avl();
        while (sc.hasNext()) {
            String cmd = sc.next();
            switch (cmd.charAt(0)) {
                case 'i': {
                    p = bst.insert(p, sc.nextInt(), true);
                    break;
                }
                case 'd': {
                    p = bst.remove(p, sc.nextInt(), true);
                    break;
                }
                case 'e': {
                    out.println(bst.exists(p, sc.nextInt()));
                    break;
                }
                case 'n': {
                    out.println(bst.next(p, sc.nextInt()));
                    break;
                }
                case 'p': {
                    out.println(bst.prev(p, sc.nextInt()));
                }
            }
        }
        sc.close();
        out.close();
    }

    static class avl {


        int height(Node p) {
            return (p != null) ? p.height : 0;
        }

        int bfactor(Node p) {
            return height(p.right) - height(p.left);
        }

        void fixHeight(Node p) {
            int hl = height(p.left);
            int hr = height(p.right);
            p.height = ((hl > hr) ? hl : hr) + 1;
        }

        Node rotateR(Node p) {
            Node q = p.left;
            p.left = q.right;
            q.right = p;
            fixHeight(p);
            fixHeight(q);
            return q;
        }

        Node rotateL(Node q) {
            Node p = q.right;
            q.right = p.left;
            p.left = q;
            fixHeight(q);
            fixHeight(p);
            return p;
        }

        Node balance(Node p) {
            fixHeight(p);
            if (bfactor(p) == 2) {
                if (bfactor(p.right) < 0)
                    p.right = rotateR(p.right);
                return rotateL(p);
            }
            if (bfactor(p) == -2) {
                if (bfactor(p.left) > 0)
                    p.left = rotateL(p.left);
                return rotateR(p);
            }
            return p;
        }

        Node insert(Node p, int k, boolean first) {
            if (p == null) return new Node(k);
            if (p.height == 0) return new Node(k);
            if (first) {
                if (exists(p, k)) return p;
            }

            if (k < p.key) {
                p.left = insert(p.left, k, false);
            } else {
                p.right = insert(p.right, k, false);
            }

            return balance(p);
        }

        Node findMin(Node p) {
            return (p.left != null) ? findMin(p.left) : p;
        }

        Node removeMin(Node p) {
            if (p.left == null)
                return p.right;
            p.left = removeMin(p.left);
            return balance(p);
        }

        Node remove(Node p, int k, boolean first) {
            if (first) {
                if (!(exists(p, k))) return p;
            }
            if (p == null) return null;
            if (k < p.key)
                p.left = remove(p.left, k, false);
            else if (k > p.key)
                p.right = remove(p.right, k, false);
            else {
                Node q = p.left;
                Node r = p.right;
                if (r == null) return q;
                Node min = findMin(r);
                min.right = removeMin(r);
                min.left = q;
                return balance(min);
            }

            return balance(p);
        }

        boolean exists(Node p, int x) {
            if (p == null) return false;
            if (x == p.key) return true;
            if (x < p.key) {
                if (p.left == null) return false;
                return exists(p.left, x);
            } else {
                if (p.right == null) return false;
                return exists(p.right, x);
            }
        }

        String next(Node p, int x) {
            if (p == null) return "none";
            Node current = p;
            Node successor = null;
            while (current != null) {
                if (current.key > x) {
                    successor = current;
                    current = current.left;
                } else
                    current = current.right;
            }
            if (successor == null) return "none";
            return String.valueOf(successor.key);
        }

        String prev(Node p, int x) {
            if (p == null) return "none";
            Node current = p;
            Node successor = null;
            while (current != null) {
                if (current.key < x) {
                    successor = current;
                    current = current.right;
                } else
                    current = current.left;
            }
            if (successor == null) return "none";
            return String.valueOf(successor.key);
        }

        static class Node {
            int key;
            int height;
            Node left;
            Node right;

            Node() {
                left = null;
                right = null;
            }

            Node(int k) {
                key = k;
                left = null;
                right = null;
                height = 1;

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