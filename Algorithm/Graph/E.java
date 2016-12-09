package Algorithm.Graph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;


/**
 * Created by Blackbird on 01.04.2016.
 * Project : E
 * Start time : 2:25
 */


public class E {

    private static ArrayList<Integer>[] g;
    private static int[] path;
    private static int[] status;
    private static int begin;
    private static int end;

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(new File("cycle.in"));
        PrintWriter out = new PrintWriter(new File("cycle.out"));
        int n = sc.nextInt();
        int m = sc.nextInt();
        //noinspection unchecked
        g = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            g[i] = new ArrayList<>();
        }
        status = new int[n];
        path = new int[n];
        for (int i = 0; i < n; i++) {
            path[i] = -1;
        }
        begin = -1;
        end = -1;
        for (int i = 0; i < m; i++) {
            g[sc.nextInt() - 1].add(sc.nextInt() - 1);
        }
        for (int i = 0; i < n; i++) {
            if (dfs(i))
                break;
        }

        if (begin == -1) {
            out.println("NO");
        } else {
            out.println("YES");
            ArrayList<Integer> cycle = new ArrayList<>();
            cycle.add(begin);
            int v = end;
            while (v != begin) {
                cycle.add(v);
                v = path[v];
            }
            for (int i = cycle.size() - 1; i >= 0; i--) {
                out.print((cycle.get(i) + 1) + " ");
            }
        }
        out.close();
        sc.close();
    }

    private static boolean dfs(int v) {
        if (status[v] == 2) {
            return false;
        }
        status[v] = 1;
        for (Integer i : g[v]) {
            if (status[i] == 0) {
                path[i] = v;
                if (dfs(i))
                    return true;
            } else if (status[i] == 1) {
                begin = i;
                end = v;
                return true;
            }
        }
        status[v] = 2;
        return false;
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
