package Discrete_Math.Automate;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.TreeSet;

/**
 * Created by Blackbird on 13.05.2016.
 * Project : Discrete_Math.Automate.F
 * Start time : 14:37
 */

public class F {

    private TreeSet<Character> alphabet = new TreeSet<>();
    private HashMap<Character, Integer>[] automate1;
    private HashMap<Character, Integer>[] automate2;
    private boolean terminal1[];
    private boolean terminal2[];
    private boolean used[];
    private BufferedReader br;
    private StringTokenizer in;
    private PrintWriter out;

    public static void main(String[] args) throws IOException {
        Locale.setDefault(Locale.US);
        new F().run();
    }

    private boolean[] readTerminal(int n, int k) throws IOException {
        boolean t[] = new boolean[n + 1];
        for (int i = 0; i < k; i++) {
            t[nextInt()] = true;
        }
        return t;
    }

    private HashMap<Character, Integer>[] readAutomate(int n, int m) throws IOException {
        HashMap<Character, Integer>[] a = new HashMap[n + 1];
        for (int i = 1; i <= n; i++) {
            a[i] = new HashMap<>();
        }
        for (int i = 0; i < m; i++) {
            int from = nextInt();
            int to = nextInt();
            char c = nextToken().charAt(0);
            if (!alphabet.contains(c))
                alphabet.add(c);
            a[from].put(c, to);
        }
        return a;
    }

    private boolean dfs(int u, int v) {
        used[u] = true;
        if (terminal1[u] != terminal2[v])
            return false;
        boolean isomorphism = true;
        for (char c : alphabet) {
            if (automate1[u].containsKey(c) != automate2[v].containsKey(c))
                return false;
            else if (automate1[u].containsKey(c) && automate2[v].containsKey(c) && !used[automate1[u].get(c)]) {
                isomorphism &= dfs(automate1[u].get(c), automate2[v].get(c));
            }
        }
        return isomorphism;
    }

    public void solve() throws IOException {
        int n1 = nextInt();
        int m1 = nextInt();
        int k1 = nextInt();
        terminal1 = readTerminal(n1, k1);
        automate1 = readAutomate(n1, m1);
        int n2 = nextInt();
        int m2 = nextInt();
        int k2 = nextInt();
        terminal2 = readTerminal(n2, k2);
        automate2 = readAutomate(n2, m2);
        used = new boolean[n1 + 1];
        out.println(dfs(1, 1) ? "YES" : "NO");
    }

    public void run() {
        try {
            br = new BufferedReader(new FileReader("isomorphism" + ".in"));
            out = new PrintWriter("isomorphism" + ".out");
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