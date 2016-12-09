package Algorithm.ShortPath;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * Created by Blackbird on 10.05.2016.
 * Project : ShortPath.Algorithm.LCA.Discrete_Math.Matroid.C
 * Start time : 1:07
 */

public class C {

    private BufferedReader br;
    private StringTokenizer in;
    private PrintWriter out;

    public static void main(String[] args) throws IOException {
        Locale.setDefault(Locale.US);
        new C().run();
    }

    public void solve() throws IOException {
        int n = nextInt();
        int m = nextInt();
        long[][] d = new long[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                d[i][j] = i == j ? 0 : Integer.MAX_VALUE;
        for (int i = 0; i < m; i++) {
            d[nextInt() - 1][nextInt() - 1] = nextInt();
        }
        for (int k = 0; k < n; k++)
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++)
                    d[i][j] = Math.min(d[i][j], d[i][k] + d[k][j]);
        for (long a[] : d) {
            for (long aA : a)
                out.print(aA + " ");
            out.println();
        }
    }

    public void run() {
        try {
            br = new BufferedReader(new FileReader("pathsg" + ".in"));
            out = new PrintWriter("pathsg" + ".out");
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