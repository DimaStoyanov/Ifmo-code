package Algorithm.LCA;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * Created by Blackbird on 04.11.2016.
 * Project : G
 * Start time : 22:37
 */

public class G {
    private final int MAX = 200000;
    private final int LN = (int) (Math.log(MAX) / Math.log(2) + 1);
    private int[][] dp;
    private int[] d, rp, pow;
    private int n = 0;
    private BufferedReader br;
    private StringTokenizer in;
    private PrintWriter out;

    public static void main(String[] args) throws IOException {
        Locale.setDefault(Locale.US);
        new G().run();
    }

    public void solve() throws IOException {
        int m = nextInt();
        dp = new int[MAX][LN];
        d = new int[MAX];
        rp = new int[MAX];
        pow = new int[LN];
        pow[0] = 1;
        for (int i = 1; i < LN; i++) {
            pow[i] = pow[i - 1] * 2;
        }
        int cur;
        for (int i = 0; i < m; i++) {
            switch (nextToken()) {
                case "+":
                    cur = nextInt() - 1;
                    d[++n] = d[cur] + 1;
                    dp[n][0] = cur;
                    rp[n] = n;
                    for (int j = 1; j < LN; j++) {
                        dp[n][j] = dp[dp[n][j - 1]][j - 1];
                    }
                    break;
                case "?":
                    out.println(get(lca(nextInt() - 1, nextInt() - 1)) + 1);
                    break;
                case "-":
                    cur = nextInt() - 1;
                    rp[cur] = dp[cur][0];
            }
        }
    }

    private int lca(int v, int u) {
        if (d[v] > d[u]) {
            int temp = v;
            v = u;
            u = temp;
        }
        for (int i = LN - 1; i >= 0; i--) {
            if (d[u] - d[v] >= pow[i])
                u = dp[u][i];
        }
        if (v == u) return v;
        for (int i = LN - 1; i >= 0; i--) {
            if (dp[v][i] != dp[u][i]) {
                v = dp[v][i];
                u = dp[u][i];
            }
        }
        return dp[v][0];
    }

    private int get(int v) {
        if (v == rp[v])
            return v;
        rp[v] = get(rp[v]);
        return rp[v];
    }

    public void run() {
        try {
            br = new BufferedReader(new FileReader("carno" + ".in"));
            out = new PrintWriter("carno" + ".out");
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

}