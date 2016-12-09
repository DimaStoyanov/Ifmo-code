package Algorithm.FlowNetwork;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * Created by Blackbird on 21.11.2016.
 * Project : Algorithm.Tree.A
 * Start time : 23:27
 */

public class A {

    private ArrayList<Integer>[] g;
    private int match[];
    private boolean[] kuhn_used;
    private BufferedReader br;
    private StringTokenizer in;
    private PrintWriter out;

    public static void main(String[] args) throws IOException {
        Locale.setDefault(Locale.US);
        new A().run();
    }

    public void solve() throws IOException {
        int n = nextInt();
        int m = nextInt();
        int k = nextInt();
        //noinspection unchecked
        g = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            g[i] = new ArrayList<>();
        }
        for (int i = 0; i < k; i++) {
            g[nextInt() - 1].add(nextInt() - 1);
        }
        match = new int[m];
        kuhn_used = new boolean[n];
        Arrays.fill(match, -1);
        int result = 0;
        for (int i = 0; i < n; i++) {
            Arrays.fill(kuhn_used, false);
            if (kuhn(i))
                result++;
        }
        out.println(result);
    }

    private boolean kuhn(int v) {
        if (kuhn_used[v]) return false;
        kuhn_used[v] = true;
        for (int e : g[v]) {
            if (match[e] == -1 || kuhn(match[e])) {
                match[e] = v;
                return true;
            }
        }
        return false;
    }

    public void run() {
        try {
            br = new BufferedReader(new FileReader("matching" + ".in"));
            out = new PrintWriter("matching" + ".out");
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