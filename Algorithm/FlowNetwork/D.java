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
 * Created by Blackbird on 02.12.2016.
 * Project : D
 * Start time : 18:41
 */

public class D {

    private BufferedReader br;
    private StringTokenizer in;
    private PrintWriter out;

    public static void main(String[] args) throws IOException {
        Locale.setDefault(Locale.US);
        new D().run();
    }

    public void solve() throws IOException {
        out.println(new Kuhn().findMatching());
    }

    public void run() {
        try {
            br = new BufferedReader(new FileReader("paths" + ".in"));
            out = new PrintWriter("paths" + ".out");
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

        private ArrayList<Integer>[] g;
        private int match[];
        private boolean[] kuhn_used;

        @SuppressWarnings("unchecked")
        private int findMatching() throws IOException {
            int n = nextInt();
            int k = nextInt();
            g = new ArrayList[n];
            for (int i = 0; i < n; i++) {
                g[i] = new ArrayList<>();
            }
            for (int i = 0; i < k; i++) {
                g[nextInt() - 1].add(nextInt() - 1);
            }
            match = new int[n];
            kuhn_used = new boolean[n];
            Arrays.fill(match, -1);
            for (int i = 0; i < n; i++) {
                Arrays.fill(kuhn_used, false);
                kuhn(i);
            }
            return findPaths(n);
        }


        private int findPaths(int n) {
            int paths = 0;
            boolean used[] = new boolean[n];
            for (int i = 0; i < n; i++) {
                if (match[i] != -1) {
                    paths++;
                    used[i] = true;
                    int v = i;
                    while ((v = match[v]) != -1) {
                        if (used[v]) {
                            paths--;
                            break;
                        }
                        used[v] = true;
                    }
                }
            }
            for (int i = 0; i < n; i++) {
                if (!used[i]) {
                    paths++;
                }
            }
            return paths;
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
    }

}