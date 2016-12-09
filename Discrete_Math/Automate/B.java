package Discrete_Math.Automate;

import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by Blackbird on 10.05.2016.
 * Project : Discrete_Math.Automate.Discrete_Math.Hamiltonian_cycle.Discrete_Math.Matroid.B
 * Start time : 17:08
 */

public class B {
    private String word;
    private ArrayList<Integer> success;
    private int wordLen;
    private ArrayList<Pair<String, Integer>>[] pos;
    private int[][] d;
    private BufferedReader br;
    private StringTokenizer in;
    private PrintWriter out;

    public static void main(String[] args) throws IOException {
        Locale.setDefault(Locale.US);
        new B().run();
    }

    public void solve() throws IOException {
        word = nextToken();
        wordLen = word.length();
        int n = nextInt();
        int m = nextInt();
        int k = nextInt();
        success = new ArrayList<>();
        for (int i = 0; i < k; i++)
            success.add(nextInt());
        pos = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++)
            pos[i] = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            int from = nextInt();
            int to = nextInt();
            String c = nextToken();
            pos[from].add(new Pair<>(c, to));
        }
        d = new int[wordLen + 1][m + 1];
        for (int i = 0; i <= wordLen; i++)
            Arrays.fill(d[i], -1);
        out.println(nka(0, 1) ? "Accepts" : "Rejects");
    }

    private boolean nka(int index, int position) {
        if (d[index][position] != -1)
            return d[index][position] == 1;
        if (index == wordLen) {
            d[index][position] = success.contains(position) ? 1 : 0;
            return d[index][position] == 1;
        }
        for (Pair<String, Integer> p : pos[position]) {
            if (Objects.equals(p.getKey(), String.valueOf(word.charAt(index))) && nka(index + 1, p.getValue())) {
                d[index][position] = 1;
                return true;
            }
        }
        d[index][position] = 0;
        return false;
    }

    public void run() {
        try {
            br = new BufferedReader(new FileReader("problem2" + ".in"));
            out = new PrintWriter("problem2" + ".out");
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