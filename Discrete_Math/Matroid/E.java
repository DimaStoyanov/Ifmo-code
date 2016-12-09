package Discrete_Math.Matroid;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.StringTokenizer;

import static java.util.Arrays.fill;

/**
 * Created by Dima Stoyanov.
 */

public class E {


    private int[] w;
    private boolean[] s;
    private int n;
    @SuppressWarnings("FieldCanBeLocal")
    private int powN, capacity;
    private BufferedReader br;
    private StringTokenizer in;
    private PrintWriter out;

    public static void main(String[] args) throws IOException {
        Locale.setDefault(Locale.US);
        new E().run();
    }

    private void solve() throws IOException {
        n = nextInt();
        int m = nextInt();
        powN = 1 << n;
        s = new boolean[powN];
        fill(s, true);
        w = new int[n];
        for (int i = 0; i < n; i++) {
            w[i] = nextInt();
        }
        int element, index;
        for (int i = 0; i < m; i++) {
            capacity = nextInt();
            index = 0;
            for (int j = 0; j < capacity; j++) {
                element = nextInt();
                if (element == 0) continue;
                index |= 1 << (element - 1);
            }
            s[index] = false;
        }
        out.println(findBases());
    }

    private int findBases() {
        ArrayList<Integer> bases = new ArrayList<>();
        int maxCapacity = 0, curCapacity;
        for (int i = 1; i < powN; i++) {
            for (int j = 1; j < powN; j <<= 1) {
                if ((i & j) == j && (!s[i - j])) {
                    s[i] = false;
                }
            }
            curCapacity = getCapacity(i);
            if (s[i] && curCapacity > maxCapacity) {
                maxCapacity = curCapacity;
                bases.clear();
                bases.add(i);
            } else if (s[i] && curCapacity == maxCapacity) {
                bases.add(i);
            }
        }
        return calculateMax(bases);
    }

    private int calculateMax(ArrayList<Integer> bases) {
        int max = 0, sum;
        for (int el : bases) {
            sum = 0;
            for (int i = 1, j = 0; i < powN; i <<= 1, j++) {
                if ((el & i) == i) {
                    sum += w[j];
                }
            }
            max = Math.max(max, sum);
        }
        return max;
    }

    private int getCapacity(int index) {
        int power = 0;
        for (int i = 1; i < powN; i <<= 1) {
            if ((i & index) == i) {
                power++;
            }
        }
        return power;
    }

    private void run() {
        try {
            br = new BufferedReader(new FileReader("cycles.in"));
            out = new PrintWriter("cycles.out");
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