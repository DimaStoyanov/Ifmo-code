package Algorithm.FlowNetwork;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * Created by Dima Stoyanov.
 */

public class J {


    private Kuhn kuhn;
    private int[][] cost;
    private int[] edge;
    private int lastMin;
    private BufferedReader br;
    private StringTokenizer in;
    private PrintWriter out;

    public static void main(String[] args) throws IOException {
        Locale.setDefault(Locale.US);
        new J().run();
    }

    private void solve() throws IOException {
        int n = nextInt();
        cost = new int[n][n];
        edge = new int[n * n];
        int edgeCost, count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                edgeCost = nextInt();
                cost[i][j] = edgeCost;
                edge[count++] = edgeCost;
            }
        }
        Arrays.sort(edge);
        kuhn = new Kuhn();
        out.println(binSearch(0, edge.length));
// System.out.println(Arrays.toString(kuhn.match));
    }

    private int binSearch(int left, int right) {
        if (left == right - 1) return lastMin;
        int k = (left + right) >> 1;
        int match = kuhn.findMatching(edge[k]);
        return binSearch(match == -1 ? left : k, match == -1 ? k : right);
    }

    public void run() {
        try {
            br = new BufferedReader(new FileReader("minimax.in"));
            out = new PrintWriter("minimax.out");
            solve();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public String nextToken() throws IOException {
        while (in == null || !in.hasMoreTokens()) {
            in = new StringTokenizer(br.readLine());
        }
        return in.nextToken();
    }

    public int nextInt() throws IOException {
        return Integer.parseInt(nextToken());
    }

    public double nextDouble() throws IOException {
        return Double.parseDouble(nextToken());
    }

    public long nextLong() throws IOException {
        return Long.parseLong(nextToken());
    }

    private class Kuhn {
        private int match[];
        private boolean[] used;
        private int w, n, minEdge;

        Kuhn() {
            n = cost.length;
            match = new int[n];
            used = new boolean[n];
        }

        private int findMatching(int w) {
            this.w = w;
            Arrays.fill(match, -1);
            Arrays.fill(used, false);
            int result = 0;
            minEdge = Integer.MAX_VALUE;

            for (int i = 0; i < n; i++) {
                Arrays.fill(used, false);
                if (kuhn(i))
                    result++;
            }
            // System.out.println("Match size " + result);
            if (result == n) {
                lastMin = minEdge;
            }
            return result == n ? result : -1;
        }

        private boolean kuhn(int v) {
            if (used[v]) return false;
            used[v] = true;
            for (int i = 0; i < n; i++) {
                if (cost[v][i] < w) continue;
                if (match[i] == -1 || kuhn(match[i])) {
                    match[i] = v;
                    minEdge = Math.min(minEdge, cost[v][i]);
                    return true;
                }
            }
            return false;
        }
    }
}