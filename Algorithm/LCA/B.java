package Algorithm.LCA;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * Created by Blackbird on 28.10.2016.
 * Project : B
 * Start time : 2:52
 */

public class B {
    private int ln;
    private ArrayList<Integer> d;
    private ArrayList<ArrayList<Integer>> dp;
    private BufferedReader br;
    private StringTokenizer in;
    private PrintWriter out;

    public static void main(String[] args) throws IOException {
        Locale.setDefault(Locale.US);
        new B().run();
    }

    public void solve() throws IOException {
        int n = nextInt();
        ln = (int) (Math.log(n) / Math.log(2)) + 1;
        d = new ArrayList<>();
        d.add(0);
        dp = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            dp.add(i, new ArrayList<>());
        }
        for (int i = 0; i < ln; i++) {
            dp.get(0).add(i, 0);
        }
        for (int i = 1; i < n; i++) {
            int from = nextInt() - 1;
            dp.get(i).add(0, from);
            d.add(i, d.get(from) + 1);
        }

        for (int j = 1; j < ln; j++) {
            for (int i = 1; i < n; i++) {
                dp.get(i).add(j, dp.get(dp.get(i).get(j - 1)).get(j - 1));
            }
        }

        int m = nextInt();
        for (int i = 0; i < m; i++) {
            out.println(lca(nextInt() - 1, nextInt() - 1) + 1);
        }


    }

    private int lca(int v, int u) {
        if (d.get(v) > d.get(u)) {
            int temp = v;
            v = u;
            u = temp;
        }
        for (int i = ln - 1; i >= 0; i--) {
            if (d.get(u) - d.get(v) >= (1 << i)) {
                u = dp.get(u).get(i);
            }
        }
        if (v == u) return v;
        for (int i = ln - 1; i >= 0; i--) {
            if (!dp.get(v).get(i).equals(dp.get(u).get(i))) {
                v = dp.get(v).get(i);
                u = dp.get(u).get(i);
            }
        }
        return dp.get(v).get(0);
    }

    public void run() {
        try {
            br = new BufferedReader(new FileReader("lca" + ".in"));
            out = new PrintWriter("lca" + ".out");
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