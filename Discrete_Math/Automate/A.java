package Discrete_Math.Automate;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * Created by Blackbird on 10.05.2016.
 * Project : Discrete_Math.Automate.Discrete_Math.Hamiltonian_cycle.Algorithm.Tree.A
 * Start time : 11:34
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
        String word = nextToken();
        int n = nextInt();
        int m = nextInt();
        int k = nextInt();
        ArrayList<Integer> success = new ArrayList<>();
        for (int i = 0; i < k; i++)
            success.add(nextInt());
        HashMap<Character, Integer> pos[] = new HashMap[n + 1];
        for (int i = 1; i <= n; i++)
            pos[i] = new HashMap<>();
        for (int i = 0; i < m; i++) {
            int from = nextInt();
            int to = nextInt();
            char p = nextToken().charAt(0);
            pos[from].put(p, to);
        }

        int curPosition = 1;
        final int lenWord = word.length();
        boolean ok = true;
        for (int i = 0; i < lenWord; i++) {
            char c = word.charAt(i);
            if (!pos[curPosition].containsKey(c)) {
                ok = false;
                break;
            } else {
                curPosition = pos[curPosition].get(c);
            }
        }
        if (!ok) out.println("Rejects");
        else out.println(success.contains(curPosition) ? "Accepts" : "Rejects");
    }

    public void run() {
        try {
            br = new BufferedReader(new FileReader("problem1" + ".in"));
            out = new PrintWriter("problem1" + ".out");
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