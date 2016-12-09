package Algorithm.String;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * Created by Blackbird on 23.05.2016.
 * Project : String.Algorithm.LCA.Discrete_Math.Matroid.C
 * Start time : 0:06
 */

public class C {

    private BufferedReader br;
    private StringTokenizer in;
    private PrintWriter out;

    public static void main(String[] args) throws IOException {
        Locale.setDefault(Locale.US);
        new C().run();
    }

    private int[] getPrefixFunction(String str) {
        int n = str.length();
        int pf[] = new int[n]; // prefix function
        for (int i = 1; i < n; i++) {
            int curLen = pf[i - 1];
            while (curLen > 0 && str.charAt(i) != str.charAt(curLen)) {
                curLen = pf[curLen - 1];
            }
            pf[i] = str.charAt(i) == str.charAt(curLen) ? ++curLen : curLen;
        }
        return pf;
    }

    public void solve() throws IOException {
        int[] pf = getPrefixFunction(nextToken());
        for (int i = 0; i < pf.length; i++) {
            out.print(pf[i] + " ");
        }
    }

    public void run() {
        try {
            br = new BufferedReader(new FileReader("prefix" + ".in"));
            out = new PrintWriter("prefix" + ".out");
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