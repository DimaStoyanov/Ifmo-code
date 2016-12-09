package Algorithm.DP;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 * Created by Дима1 on 16.03.2016.
 */
public class K {
    private static final int inf = Integer.MAX_VALUE;
    private static Node tree[];
    private static int d[][];
    private static boolean root;

    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(new File("boolean.in"));
            PrintWriter out = new PrintWriter(new File("boolean.out"));
            int n = sc.nextInt();
            root = sc.nextBoolean();
            tree = new Node[2 * n];
            int vn = (n - 1) / 2;
            for (int i = 1; i <= vn; i++) {
                tree[i] = new Node(sc.nextBoolean(), sc.nextBoolean());
            }
            for (int l = vn + 1; l <= n; l++) {
                tree[l] = new Node(sc.nextBoolean());
            }
            build();

            d = new int[n + 1][2];
            for (int i = 0; i <= n; i++) {
                for (int j = 0; j < 2; j++)
                    d[i][j] = -1;
            }
            int ans = lazy();

            out.println(ans == inf ? "IMPOSSIBLE" : ans);
            sc.close();
            out.close();
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }

    private static void build() {
        build(1);
    }

    private static void build(int i) {
        if (!tree[i].isLeaf) {
            build(i << 1);
            build((i << 1) + 1);
            tree[i].value = binaryOperation(i, tree[i].typeOfOperation);
        }
    }

    private static int lazy() {
        return lazy(1, tree[1].typeOfOperation);
    }

    private static int lazy(int i, boolean v) {
        int w = v ? 1 : 0;
        if (tree[i].isLeaf) {
            return d[i][w] = tree[i].value == root ? 0 : inf;
        }
        if (d[i][w] != -1) {
            return d[i][w];
        }
        if (binaryOperation(i, v) == root) {
            return d[i][w] = 0;
        }
        if ((root && v) || (!root && !v)) {
            d[i][w] = sum(lazy(i << 1, tree[(i << 1)].typeOfOperation), lazy((i << 1) + 1, tree[(i << 1) + 1].typeOfOperation));
        } else {
            d[i][w] = Math.min(lazy(i << 1, tree[(i << 1)].typeOfOperation), lazy((i << 1) + 1, tree[(i << 1) + 1].typeOfOperation));
        }
        int change = inf;
        if (tree[i].changingOperation) {
            if (tree[i].typeOfOperation == v) {
                change = lazy(i, !v);
            }
        }
        return d[i][w] = Math.min(d[i][w], sum(change, 1));
    }


    private static int sum(int op1, int op2) {
        return (op1 == inf || op2 == inf) ? inf : op1 + op2;
    }


    private static boolean binaryOperation(int i, boolean v) {
        boolean op1 = tree[i << 1].value;
        boolean op2 = tree[(i << 1) + 1].value;
        return v ? op1 && op2 : op1 || op2;
    }

    private static class Node {
        boolean value;
        boolean typeOfOperation; // true == && : false == ||
        boolean changingOperation;  // can we change && to || and || to &&?
        boolean isLeaf;


        Node(boolean a, boolean b) { // for create not a leaf Nodes
            typeOfOperation = a;
            changingOperation = b;
            isLeaf = false;
            value = false;
        }

        Node(boolean a) { // for create leaf Nodes
            value = a;
            typeOfOperation = false;
            changingOperation = false;
            isLeaf = true;
        }

    }

    static class Scanner {
        BufferedReader br;
        StringTokenizer t;

        Scanner(File file) throws Exception {
            br = new BufferedReader(new FileReader(file));
            t = new StringTokenizer("");
        }

        boolean hasNext() throws Exception {
            while (!t.hasMoreTokens()) {
                String line = br.readLine();
                if (line == null)
                    return false;
                t = new StringTokenizer(line);
            }
            return true;
        }


        String next() throws Exception {
            if (hasNext()) {
                return t.nextToken();
            }
            return null;
        }

        int nextInt() throws Exception {
            return Integer.parseInt(next());
        }

        public boolean nextBoolean() throws Exception {
            return nextInt() == 1;
        }

        void close() throws Exception {
            br.close();
        }
    }
}
