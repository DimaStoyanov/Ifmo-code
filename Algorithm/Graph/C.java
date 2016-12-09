package Algorithm.Graph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.StringTokenizer;


/**
 * Created by Blackbird on 13.04.2016.
 * Project : Algoritm.Graph.Algorithm.LCA.C
 * Start time : 2:38
 */


public class C {

    private static final Random rand = new Random();
    private static int[] p;
    private static int n;
    private static double result = 0;

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(new File("transport.in"));
        PrintWriter out = new PrintWriter(new File("transport.out"));
        n = sc.nextInt();
        long[] x = new long[n];
        long[] y = new long[n];
        for (int i = 0; i < n; i++) {
            x[i] = sc.nextLong();
        }
        for (int i = 0; i < n; i++) {
            y[i] = sc.nextLong();
        }
        double r = sc.nextDouble();
        double a = sc.nextDouble();
        Node[] g = new Node[n * n];
        int cnt = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                double temp = Math.sqrt((Math.pow(x[i] - x[j], 2) + Math.pow(y[j] - y[i], 2))) * r;
                g[cnt++] = new Node(temp, i, j);
            }
        }
        Arrays.sort(g, (o1, o2) -> {
            if (o1.w < o2.w)
                return -1;
            if (o1.w == o2.w)
                return 0;
            return 1;
        });
        p = new int[n];
        double another = 0.0;
        makeSet();
        for (Node i : g) {
            if (get(i.u) != get(i.v)) {
                union(i.u, i.v);
                another += i.w;
            }
        }
        makeSet();
        for (Node i : g) {
            if (get(i.u) != get(i.v) && i.w < a) {
                union(i.u, i.v);
                result += i.w;
            }
        }
        ArrayList<Integer> leaders = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (!leaders.contains(get(i))) {
                leaders.add(get(i));
            }
        }
        result += a * leaders.size();

        out.println(Math.min(result, another));
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
        double w;
        int u;
        int v;

        Node(double a, int b, int c) {
            w = a;
            u = b;
            v = c;
        }
    }


    private static class Scanner {
        private BufferedReader br;
        private StringTokenizer t;

        public Scanner(File file) throws Exception {
            br = new BufferedReader(new FileReader(file));
            t = new StringTokenizer("");
        }

        public boolean hasNext() throws Exception {
            while (!t.hasMoreTokens()) {
                String line = br.readLine();
                if (line == null)
                    return false;
                t = new StringTokenizer(line);
            }
            return true;
        }


        public String next() throws Exception {
            if (hasNext()) {
                return t.nextToken();
            }
            return null;
        }

        public int nextInt() throws Exception {
            return Integer.parseInt(next());
        }

        public long nextLong() throws Exception {
            return Long.parseLong(next());
        }

        public double nextDouble() throws Exception {
            return Double.parseDouble(next());
        }


        public void close() throws Exception {
            br.close();
        }
    }
}