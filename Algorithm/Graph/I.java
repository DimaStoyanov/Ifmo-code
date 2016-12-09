package Algorithm.Graph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;


/**
 * Created by Blackbird on 12.04.2016.
 * Project : Algorithm.Graph.I
 * Start time : 22:22
 */


public class I {
    private static ArrayList<Integer> g[];
    private static int lvl = 0;
    private static int[] depth;
    private static int[] whoWin;

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(new File("game.in"));
        PrintWriter out = new PrintWriter(new File("game.out"));
        int n = sc.nextInt();
        int m = sc.nextInt();
        int s = sc.nextInt() - 1;
        g = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            g[i] = new ArrayList<>();
        }
        for (int i = 0; i < m; i++) {
            g[sc.nextInt() - 1].add(sc.nextInt() - 1);
        }
        depth = new int[n];
        whoWin = new int[n];
        Arrays.fill(whoWin, -2);
        out.println(dfs(s) == 1 ? "First player wins" : "Second player wins");
        out.close();
        sc.close();
    }

    private static int dfs(int v) {
        lvl++;
        if (whoWin[v] == -2) {
            depth[v] = lvl;
            int fs = depth[v] % 2;
            boolean isLeaf = true;
            boolean win = false;
            for (int i : g[v]) {
                isLeaf = false;
                if (dfs(i) == fs) win = true;
            }
            lvl--;
            if (isLeaf) {
                whoWin[v] = inverse(fs);
                return whoWin[v];
            }
            whoWin[v] = win ? fs : inverse(fs);

            return whoWin[v];
        } else {
            int res = (depth[v] % 2) == (lvl % 2) ? whoWin[v] : inverse(whoWin[v]);
            lvl--;
            return (res);
        }
    }

    private static int inverse(int x) {
        return x == 1 ? 0 : 1;
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
