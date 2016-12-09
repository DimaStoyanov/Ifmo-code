package Algorithm.Tree;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 * Created by Дима1 on 17.12.2015.
 */
public class D {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(new File("crypto.in"));
        PrintWriter out = new PrintWriter(new File("crypto.out"));
        int r = sc.nextInt();
        int n = sc.nextInt();
        int m = sc.nextInt();
        double tmp = Math.log10(n) / Math.log10(2);
        if ((tmp - (tmp % 1)) != tmp) {
            tmp = Math.pow(2, tmp - (tmp % 1) + 1);
        } else {
            tmp = Math.pow(2, tmp);
        }
        int rn = (int) tmp;
        Matrix tree = new Matrix(rn, r);
        Matrix.Node res;

        for (int i = 0; i < n; i++) {
            tree.add(sc.nextInt(), sc.nextInt(), sc.nextInt(), sc.nextInt(), i);
        }
        tree.addition(n);
        tree.build(1, rn, 2 * rn - 1);
        for (int i = 0; i < m; i++) {
            res = tree.increase(sc.nextInt(), sc.nextInt());
            out.println(res.x1 + " " + res.x2);
            out.println(res.x3 + " " + res.x4);
            out.println();
        }
        out.close();
        sc.close();
    }

    static class Matrix {
        Node list[];
        int n;
        int R;

        Matrix(int n, int mod) {
            list = new Node[2 * n];

            for (int i = 0; i < 2 * n; i++) {
                list[i] = new Node(0, 0, 0, 0);
            }
            this.n = n;
            R = mod;
        }

        void add(int q, int w, int e, int r, int num) {
            list[n + num].x1 = q;
            list[n + num].x2 = w;
            list[n + num].x3 = e;
            list[n + num].x4 = r;
        }

        void addition(int tmp) {
            for (int i = tmp + n; i < 2 * n; i++) {
                list[i].x1 = 1;
                list[i].x2 = 0;
                list[i].x3 = 0;
                list[i].x4 = 1;
            }
        }

        void build(int v, int tl, int tr) {
            if (tl != tr) {
                int tm = (tl + tr) / 2;
                build(v * 2, tl, tm);
                build(v * 2 + 1, tm + 1, tr);
                matrixIncrease(v, v * 2, v * 2 + 1);
            }
        }

        void set(int i, int q, int w, int e, int t) {
            int u = n;
            i += u - 1;
            list[i].x1 = q;
            list[i].x2 = w;
            list[i].x3 = e;
            list[i].x4 = t;
            while ((i /= 2) != 0) {
                matrixIncrease(i, 2 * i, 2 * i + 1);
            }
        }

        private Node increaseRecursive(int v, int l, int r, int vl, int vr) {
            if (l > r) {
                return new Node(1, 0, 0, 1);
            }
            if (l == vl && r == vr) {
                return list[v];
            }
            int t = (vl + vr) >> 1;
            return ansIncrease(increaseRecursive(v << 1, l, Math.min(r, t), vl, t), increaseRecursive((v << 1) + 1, Math.max(l, t + 1), r, t + 1, vr));
        }

        Node increase(int l, int r) {
            return increaseRecursive(1, l, r, 1, n);
        }

        void matrixIncrease(int i, int j, int k) {
            list[i].x1 = (list[j].x1 * list[k].x1 + list[j].x2 * list[k].x3) % R;
            list[i].x2 = (list[j].x1 * list[k].x2 + list[j].x2 * list[k].x4) % R;
            list[i].x3 = (list[j].x3 * list[k].x1 + list[j].x4 * list[k].x3) % R;
            list[i].x4 = (list[j].x3 * list[k].x2 + list[j].x4 * list[k].x4) % R;
        }

        Node ansIncrease(Node a, Node b) {
            Node temp = new Node(1, 0, 0, 1);
            temp.x1 = (a.x1 * b.x1 + a.x2 * b.x3) % R;
            temp.x2 = (a.x1 * b.x2 + a.x2 * b.x4) % R;
            temp.x3 = (a.x3 * b.x1 + a.x4 * b.x3) % R;
            temp.x4 = (a.x3 * b.x2 + a.x4 * b.x4) % R;
            return temp;
        }

        static class Node {
            int x1;
            int x2;
            int x3;
            int x4;

            Node(int a, int b, int c, int d) {
                x1 = a;
                x2 = b;
                x3 = c;
                x4 = d;
            }

            Node() {
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
