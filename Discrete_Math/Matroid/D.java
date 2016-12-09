package Discrete_Math.Matroid;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * Created by Dima Stoyanov.
 */

public class D {

    private int powN, n;
    private boolean[] s;
    private BufferedReader br;
    private StringTokenizer in;
    private PrintWriter out;

    public static void main(String[] args) throws IOException {
        Locale.setDefault(Locale.US);
        new D().run();
    }

    private void solve() throws IOException {
        n = nextInt();
        int m = nextInt();
        powN = 1 << n;
        s = new boolean[powN];
        int capacity, index, element;
        for (int i = 0; i < m; i++) {
            capacity = nextInt();
            index = 0;
            for (int j = 0; j < capacity; j++) {
                element = nextInt();
                if (element == 0) break;
                index |= 1 << (element - 1);

            }
            s[index] = true;
        }
        out.println(checkAxioms() && check3Axiom() ? "YES" : "NO");
    }

    private boolean checkAxioms() {
        if (!s[0]) return false; // 1 axiom
        for (int i = 1; i < powN; i++)
            if (s[i])
                for (int j = 1; j < i; j++)
                    if ((i & j) == j && !s[j]) // if B in A
                        return false;
        return true;
    }

    private boolean check3Axiom() {
        ArrayList<Integer> sets[] = generateSets();
        int capacity;
        boolean ok;
        for (int i = 1; i < powN; i++) {
            if (s[i]) {
                capacity = getCapacity(i);
                for (int j = 1; j < capacity; j++) {
                    for (int el : sets[j]) {
                        ok = false;
                        for (int k = 1; k < powN; k <<= 1) {
                            if ((k & i) == k && (k & el) == 0 && s[el | k]) {
                                ok = true;
                                break;
                            }
                        }
                        if (!ok) return false;
                    }
                }
            }
        }
        return true;
    }

    private int getCapacity(int x) {
        int result = 0;
        for (int i = 1; i < powN; i <<= 1) {
            if ((i & x) == i)
                result++;
        }
        return result;
    }

    private ArrayList<Integer>[] generateSets() {
        @SuppressWarnings("unchecked") ArrayList<Integer> sets[] = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) {
            sets[i] = new ArrayList<>();
        }
        for (int i = 1; i <= n; i++) {
            int a[] = new int[i];
            for (int j = 0; j < i; j++) {
                a[j] = j + 1;
            }
            int number;
            do {
                number = getNumber(a);
                if (s[number])
                    sets[i].add(number);
            } while (next(a, n, i));
        }
        return sets;
    }

    private int getNumber(int[] a) {
        int result = 0;
        for (int anA : a) {
            result |= 1 << (anA - 1);
        }
        return result;
    }

    private boolean next(int a[], int n, int k) {
        int[] b = new int[k + 1];
        System.arraycopy(a, 0, b, 0, k);
        b[k] = n + 1;
        int i = k - 1;
        while (i >= 0 && b[i + 1] - b[i] < 2) {
            i--;
        }
        if (i < 0)
            return false;
        b[i]++;
        for (int j = i + 1; j < k; j++) {
            b[j] = b[j - 1] + 1;
        }
        for (i = 0; i < k; i++) {
            a[i] = b[i];
        }
        return true;
    }

    private void run() {
        try {
            br = new BufferedReader(new FileReader("check.in"));
            out = new PrintWriter("check.out");
            solve();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private String nextToken() throws IOException {
        while (in == null || !in.hasMoreTokens()) {
            in = new StringTokenizer(br.readLine());
        }
        return in.nextToken();
    }

    private int nextInt() throws IOException {
        return Integer.parseInt(nextToken());
    }
}