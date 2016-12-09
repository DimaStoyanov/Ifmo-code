package Algorithm.Graph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;


/**
 * Created by Blackbird on 13.04.2016.
 * Project : Algorithm.Graph.G
 * Start time : 0:30
 */


public class G {
    private static int[] point;
    private static boolean[] color;
    private static Node[] g;
    private static ArrayList<Integer> newGraph;
    private static boolean[] pathColor;
    private static int[] path;
    private static ArrayList<Integer>[] outEdge;

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(new File("shortpath.in"));
        PrintWriter out = new PrintWriter(new File("shortpath.out"));
        int n = sc.nextInt();
        int m = sc.nextInt();
        int s = sc.nextInt() - 1;
        int t = sc.nextInt() - 1;
        g = new Node[m];
        for (int i = 0; i < m; i++) {
            g[i] = new Node(sc.nextInt() - 1, sc.nextInt() - 1, sc.nextInt());
        }
        ArrayList<Integer>[] inEdge = new ArrayList[n];
        outEdge = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            inEdge[i] = new ArrayList<>();
            outEdge[i] = new ArrayList<>();
        }
        for (int i = 0; i < m; i++) {
            inEdge[g[i].u].add(i);
            outEdge[g[i].u].add(i);
        }
        color = new boolean[n];
        newGraph = new ArrayList<>();
        point = new int[n];
        topsort(s);
        if (!newGraph.contains(t)) {
            out.println("Unreachable");
        } else {
            path = new int[n];
            pathColor = new boolean[n];
            Arrays.fill(path, Integer.MAX_VALUE);
            path[s] = 0;
            point[s] = 0;
            while (!pathColor[t]) {
                int len = newGraph.size() - 1;
                for (int i = len; i >= 0; i--) {
                    findPath(newGraph.get(i));
                }
            }
            out.println(path[t]);
        }
        out.close();
        sc.close();
    }

    private static void topsort(int v) {
        point[v]++;
        if (color[v]) return;
        color[v] = true;
        for (int i : outEdge[v]) {
            topsort(g[i].v);
        }

        newGraph.add(v);
    }

    private static void findPath(int v) {
        if (pathColor[v]) {
            return;
        }
        if (point[v] != 0) {
            return;
        }
        pathColor[v] = true;
        for (int i : outEdge[v]) {
            if (path[g[i].v] > path[v] + g[i].len) {
                path[g[i].v] = path[v] + g[i].len;
            }
            point[g[i].v]--;
        }
    }

    private static class Node {
        private int u;
        private int v;
        private int len;

        public Node(int u, int v, int len) {
            this.u = u;
            this.v = v;
            this.len = len;
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

        public double nextDouble() throws Exception {
            return Double.parseDouble(next());
        }

        public void close() throws Exception {
            br.close();
        }
    }
}
