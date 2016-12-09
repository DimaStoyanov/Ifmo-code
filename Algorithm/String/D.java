package Algorithm.String;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * Created by Blackbird on 23.05.2016.
 * Project : String.Algorithm.LCA.D
 * Start time : 0:42
 */

public class D {

    private BufferedReader br;
    private StringTokenizer in;
    private PrintWriter out;

    public static void main(String[] args) throws IOException {
        Locale.setDefault(Locale.US);
        new D().run();
    }

    private int[] getZ(String s) {
        int n = s.length();
        int left = 0;
        int right = 0;
        int[] z = new int[n];
        for (int i = 1; i < n; i++) {
            if (i <= right) {
                z[i] = Math.min(right - i + 1, z[i - left]);
            }
            while (z[i] + i < n && s.charAt(z[i]) == s.charAt(i + z[i])) {
                z[i]++;
            }
            if (i >= right - z[i]) {
                left = i;
                right = i + z[i] - 1;
            }
        }
        return z;
    }

    public void solve() throws IOException {
        int[] z = getZ(nextToken());
        for (int i = 1; i < z.length; i++) {
            out.print(z[i] + " ");
        }
    }

    public void run() {
        try {
            br = new BufferedReader(new FileReader("z" + ".in"));
            out = new PrintWriter("z" + ".out");
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