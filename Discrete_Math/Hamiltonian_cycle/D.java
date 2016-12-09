package Discrete_Math.Hamiltonian_cycle;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * Created by Blackbird on 03.11.2016.
 * Project : Algorithm.LCA.D
 * Start time : 21:48
 */

public class D {
    private boolean[][] adj;
    private LinkedList<Integer> answer;
    private BufferedReader br;
    private StringTokenizer in;
    private PrintWriter out;

    public static void main(String[] args) throws IOException {
        Locale.setDefault(Locale.US);
        new D().run();
    }

    public void solve() throws IOException {
        int n = nextInt();
        adj = new boolean[n][n];
        answer = new LinkedList<>();
        String line;
        int i, j, k;
        for (i = 1; i < n; i++) {
            line = nextToken();
            for (j = 0; j < i; j++)
                adj[j][i] = !(adj[i][j] = (line.charAt(j) == '1'));

        }
        answer.add(1);
        if (n > 1) {
            answer.add(adj[0][1] ? 1 : 0, 2);
            while (answer.size() < n) {
                binSearch(0, answer.size(), answer.size() + 1);

            }
        }
        int first = n;
        while (--first > 0) {
            if (adj[answer.get(first) - 1][answer.get(0) - 1])
                break;
        }

        boolean ok = true;
        while (ok) {
            ok = false;
            for (i = first + 1; i < n; i++) {
                for (j = 1; j <= first; j++)
                    if (adj[answer.get(i) - 1][answer.get(j) - 1]) {
                        int[] swap = new int[i - first];
                        for (k = 0; k <= i - first - 1; k++)
                            swap[k] = answer.get(k + first + 1);
                        for (k = i; k >= j + i - first; --k)
                            answer.set(k, answer.get(k - i + first));
                        for (k = j; k < j + i - first; k++)
                            answer.set(k, swap[k - j]);
                        first = i;
                        ok = true;
                        break;
                    }
                if (ok) break;
            }
        }
        answer.forEach(integer -> out.print(integer + " "));
    }

    private void binSearch(int l, int r, int insert) {
        if (l == r)
            answer.add(l, insert);
        else if (adj[insert - 1][answer.get(l + r >> 1) - 1])
            binSearch(l, l + r >> 1, insert);
        else
            binSearch((l + r >> 1) + 1, r, insert);
    }

    public void run() {
        try {
            br = new BufferedReader(new FileReader("guyaury" + ".in"));
            out = new PrintWriter("guyaury" + ".out");
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