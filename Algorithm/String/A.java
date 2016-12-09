package Algorithm.String;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * Created by Blackbird on 22.05.2016.
 * Project : Algorithm.String.Algorithm.Tree.A
 * Start time : 21:36
 */

public class A {

    private BufferedReader br;
    private StringTokenizer in;
    private PrintWriter out;

    public static void main(String[] args) throws IOException {
        Locale.setDefault(Locale.US);
        new A().run();
    }

    public void solve() throws IOException {
        int t = nextInt();
        int r = nextInt();
        String str = nextToken();
        int n = str.length();
        long hashes[] = new long[n];
        hashes[0] = str.charAt(0) % r;
        for (int i = 1; i < n; i++) {
            hashes[i] = (hashes[i - 1] * t) % r;
            hashes[i] = (hashes[i] + str.charAt(i)) % r;
        }
        for (int i = 0; i < n; i++) {
            out.println(hashes[i]);
        }
    }

    public void run() {
        try {
            br = new BufferedReader(new FileReader("hash" + ".in"));
            out = new PrintWriter("hash" + ".out");
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