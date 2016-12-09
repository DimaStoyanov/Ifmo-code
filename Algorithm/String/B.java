package Algorithm.String;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * Created by Blackbird on 22.05.2016.
 * Project : Algorithm.String.Discrete_Math.Matroid.B
 * Start time : 22:14
 */

public class B {

    private BufferedReader br;
    private StringTokenizer in;
    private PrintWriter out;

    public static void main(String[] args) throws IOException {
        Locale.setDefault(Locale.US);
        new B().run();
    }

    private long[] getPowers(int t, int r, int n) {
        long[] p = new long[n];
        p[0] = 1;
        for (int i = 1; i < n; i++) {
            p[i] = (p[i - 1] * t) % r;
        }
        return p;
    }

    private long[] getPrefixHashes(String str, int n, int t, int r) {
        long hashes[] = new long[n];
        hashes[0] = str.charAt(0) % r;
        for (int i = 1; i < n; i++) {
            hashes[i] = (hashes[i - 1] * t) % r;
            hashes[i] = (hashes[i] + str.charAt(i)) % r;
        }
        return hashes;
    }

    private long getHash(int start, int end, long[] hashes, long[] powers, int r) {
        return start == 0 ? hashes[end] : ((hashes[end] - (hashes[start - 1] * powers[end - start + 1]) % r)) % r;
    }

    public void solve() throws IOException {
        int t = nextInt();
        int r = nextInt();
        String str = nextToken();
        int n = str.length();
        long tPow[] = getPowers(t, r, n);
        long hashes[] = getPrefixHashes(str, n, t, r);
        int m = nextInt();
        for (int i = 0; i < m; i++) {
            long h = getHash(nextInt(), nextInt(), hashes, tPow, r);
            out.println(h < 0 ? r + h : h);
        }

    }

    public void run() {
        try {
            br = new BufferedReader(new FileReader("substr" + ".in"));
            out = new PrintWriter("substr" + ".out");
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