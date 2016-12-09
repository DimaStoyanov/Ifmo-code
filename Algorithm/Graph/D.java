package Algorithm.Graph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;


/**
 * Created by Blackbird on 01.04.2016.
 * Project : D
 * Start time : 1:01
 */


public class D {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(new File("pathbge1.in"));
        PrintWriter out = new PrintWriter(new File("pathbge1.out"));
        int n = sc.nextInt();
        int m = sc.nextInt();
        @SuppressWarnings("unchecked") ArrayList<Integer>[] g = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            g[i] = new ArrayList<>();
        }

        boolean[] status = new boolean[n];
        int result[] = new int[n];
        for (int i = 0; i < n; i++) {
            result[i] = 0;
        }
        for (int i = 0; i < m; i++) {
            int a = sc.nextInt() - 1;
            int b = sc.nextInt() - 1;
            g[a].add(b);
            g[b].add(a);
        }
        bfs(g, status, result);
        for (int i = 0; i < n; i++) {
            out.print(result[i] + " ");
        }
        out.close();
        sc.close();
    }

    private static void bfs(ArrayList<Integer>[] g, boolean[] status, int[] result) {
        ArrayList<Integer> cur = new ArrayList<>();
        ArrayList<Integer> next = new ArrayList<>();
        int lvl = 1;
        cur.add(0);
        result[0] = 0;
        status[0] = true;
        while (cur.size() > 0) {
            for (Integer current : cur) {
                for (Integer to : g[current]) {
                    if (!status[to]) {
                        status[to] = true;
                        next.add(to);
                        result[to] = lvl;
                    }

                }
            }
            lvl++;
            cur = next;
            next = new ArrayList<>();
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

        void close() throws Exception {
            br.close();
        }
    }
}
