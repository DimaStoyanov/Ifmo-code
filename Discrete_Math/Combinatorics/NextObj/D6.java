package Discrete_Math.Combinatorics.NextObj;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * Created by Blackbird on 19.04.2016.
 * Project : DM.Discrete_Math.Combinatorics.NextObj.D6
 * Start time : 1:47
 */

public class D6 {
    String fileName = "nextsetpartition";
    BufferedReader br;
    StringTokenizer in;
    PrintWriter out;
    private ArrayList<Integer>[] a;
    private int last;
    private ArrayList<Integer> used;
    private int n;

    public static void main(String[] args) throws IOException {
        Locale.setDefault(Locale.US);
        new D6().run();
    }

    private void nextPart() {
        used = new ArrayList<>();
        boolean fl = false;
        for (int i = last - 1; i >= 0; i--) {
            if (!used.isEmpty()) {
                int bestVal = Integer.MAX_VALUE;
                int bestIndex = -1;
                for (int k = 0; k < used.size(); k++) {
                    if (used.get(k) < bestVal && used.get(k) > a[i].get(a[i].size() - 1)) {
                        bestVal = used.get(k);
                        bestIndex = k;
                    }
                }
                if (bestIndex != -1) {
                    a[i].add(bestVal);
                    used.remove(bestIndex);
                    break;
                }
            }
            int j;
            for (j = a[i].size() - 1; j >= 0; j--) {
                if (!used.isEmpty() && j != 0) {
                    int bestVal = Integer.MAX_VALUE;
                    int bestIndex = -1;
                    for (int k = 0; k < used.size(); k++) {
                        if (used.get(k) < bestVal && used.get(k) > a[i].get(j)) {
                            bestVal = used.get(k);
                            bestIndex = k;
                        }
                    }
                    if (bestIndex != -1) {
                        used.remove(bestIndex);
                        used.add(a[i].get(j));
                        a[i].set(j, bestVal);
                        fl = true;
                        break;
                    }
                }
                used.add(a[i].get(j));
                a[i].remove(j);
                if (a[i].isEmpty()) {
                    last--;
                }
            }
            if (fl) break;


        }
        used.sort((o1, o2) -> o1 - o2);
        used.forEach(integer -> a[last++].add(integer));
    }

    private void print() {
        out.println(n + " " + last);
        for (int i = 0; i < last; i++) {
            for (int j = 0; j < a[i].size(); j++) {
                out.print(a[i].get(j) + " ");
            }
            out.println();
        }
        out.println();
    }

    public void solve() throws IOException {
        while (true) {
            n = nextInt();
            int k = nextInt();
            if (n == k && k == 0)
                break;
            a = new ArrayList[n];
            for (int i = 0; i < n; i++) {
                a[i] = new ArrayList<>();
            }
            for (int i = 0; i < k; i++) {
                StringTokenizer tk = new StringTokenizer(br.readLine());
                while (tk.hasMoreTokens()) {
                    a[i].add(Integer.parseInt(tk.nextToken()));
                }
            }
            last = k;
            nextPart();
            print();
        }
    }

    public void run() {
        try {
            br = new BufferedReader(new FileReader(fileName + ".in"));
            out = new PrintWriter(fileName + ".out");

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