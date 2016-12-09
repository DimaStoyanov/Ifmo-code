package Algorithm.Graph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;


/**
 * Created by Blackbird on 01.04.2016.
 * Project : Algoritm.Graph.F
 * Start time : 3:11
 */


public class F {

    private static ArrayList<Integer>[] g;
    private static int[] color;
    private static int n;
    private static boolean[] used;
    private static ArrayList<Integer> answer;
    private static boolean isCyclic;

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(new File("topsort.in"));
        PrintWriter out = new PrintWriter(new File("topsort.out"));
        n = sc.nextInt();
        int m = sc.nextInt();
        //noinspection unchecked
        g = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            g[i] = new ArrayList<>();
        }
        for (int i = 0; i < m; i++) {
            g[sc.nextInt() - 1].add(sc.nextInt() - 1);
        }
        color = new int[n];
        used = new boolean[n];
        answer = new ArrayList<>();
        Arrays.fill(color, 0);
        isCyclic = false;
        int to = 0;
        while ((to = check(used, to)) != -1) {
            dfs(to);
        }
        if (isCyclic) {
            out.println(-1);
        } else {
            for (int i = answer.size() - 1; i >= 0; i--) {
                out.print((answer.get(i) + 1) + " ");
            }
        }
        sc.close();
        out.close();

    }

    private static int check(boolean[] used, int last) {
        for (int i = last; i < n; i++) {
            if (!used[i])
                return i;
        }
        return -1;
    }

    private static void dfs(int v) {
        if (color[v] == 1)
            isCyclic = true;
        color[v] = 1;
        used[v] = true;
        for (int i : g[v]) {
            if (color[i] == 1) {
                isCyclic = true;
            }
            if (!used[i]) {
                dfs(i);
            }
        }
        color[v] = 2;
        answer.add(v);
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

        void close() throws Exception {
            br.close();
        }
    }
}
