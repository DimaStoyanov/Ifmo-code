package Algorithm.Graph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;


/**
 * Created by Blackbird on 01.04.2016.
 * Project : H
 * Start time : 16:55
 */


public class H {

    private static ArrayList<Integer>[] g;
    private static int n;
    private static boolean[] used;
    private static ArrayList<Integer> answer;

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(new File("hamiltonian.in"));
        PrintWriter out = new PrintWriter(new File("hamiltonian.out"));
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

        used = new boolean[n];
        answer = new ArrayList<>();

        int to = 0;
        while ((to = check(used, to)) != -1) {
            dfs(to);
        }
        boolean ok = true;
        for (int i = answer.size() - 1; i > 0; i--) {
            if (!(g[answer.get(i)].contains(answer.get(i - 1)))) {
                ok = false;
            }
        }
        out.println(ok ? "YES" : "NO");

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

        used[v] = true;
        for (int i : g[v]) {
            if (!used[i]) {
                dfs(i);
            }
        }
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
