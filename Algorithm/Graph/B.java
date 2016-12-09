package Algorithm.Graph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import java.util.StringTokenizer;

/**
 * Created by Blackbird on 01.04.2016.
 * Project : Algoritm.Graph.Algorithm.Tree.A
 * Start time : 17:14
 */


public class B {
    private static final Random rand = new Random();
    private static int[] p;
    private static int n;
    private static int result = 0;

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(new File("spantree2.in"));
        PrintWriter out = new PrintWriter(new File("spantree2.out"));
        n = sc.nextInt();
        int m = sc.nextInt();
        Node[] g = new Node[m];
        for (int i = 0; i < m; i++) {
            int a = sc.nextInt() - 1;
            int b = sc.nextInt() - 1;
            int c = sc.nextInt();
            g[i] = new Node(c, a, b);
        }
        Arrays.sort(g, Comparator.comparingInt(o -> o.w));
        p = new int[n];
        makeSet();
        for (Node i : g) {
            if (get(i.u) != get(i.v)) {
                union(i.u, i.v);
                result += i.w;
            }
        }
        out.println(result);
        out.close();
        sc.close();
    }


    private static int get(int v) {
        return v == p[v] ? v : (p[v] = get(p[v]));
    }

    private static void makeSet() {
        for (int i = 0; i < n; i++)
            p[i] = i;
    }

    private static void union(int a, int b) {
        a = get(a);
        b = get(b);
        if (rand.nextBoolean()) {
            int temp = a;
            a = b;
            b = temp;
        }
        if (a != b) {
            p[a] = b;
        }
    }

    private static class Node {
        int w;
        int u;
        int v;

        Node(int a, int b, int c) {
            w = a;
            u = b;
            v = c;
        }
    }


    public static class Scanner {
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

        void close() throws Exception {
            br.close();
        }
    }
}