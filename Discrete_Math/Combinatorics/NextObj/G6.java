package Discrete_Math.Combinatorics.NextObj;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * Created by Blackbird on 16.04.2016.
 * Project : DM.Discrete_Math.Combinatorics.NextObj.G6
 * Start time : 20:29
 */

public class G6 {
    String fileName = "nextpartition";
    BufferedReader br;
    StringTokenizer in;
    PrintWriter out;
    private int part[];
    private int last;

    public static void main(String[] args) throws IOException {
        Locale.setDefault(Locale.US);
        new G6().run();
    }

    private boolean nextPart() {
        if (last == 1) {
            return false;
        }
        part[last - 1]--;
        part[last - 2]++;
        if (part[last - 2] > part[last - 1]) {
            part[last - 2] += part[last - 1];
            part[last - 1] = 0;
            last--;
        } else {
            while (part[last - 2] * 2 <= part[last - 1]) {
                part[last] = part[last - 1] - part[last - 2];
                last++;
                part[last - 2] = part[last - 3];
            }
        }
        return true;
    }

    private void print(int n) {
        out.print(n + "=");
        for (int i = 0; i < last; i++)
            out.print(part[i] + (i == (last - 1) ? "" : "+"));
        out.println();
    }

    public void solve() throws IOException {
        String s = next();
        StringTokenizer t = new StringTokenizer(s, "=+");
        int size = Integer.parseInt(t.nextToken());
        part = new int[size];
        int i = 0;
        while (t.hasMoreTokens()) {
            part[i++] = Integer.parseInt(t.nextToken());
        }
        last = i;
        if (nextPart()) {
            print(size);
        } else {
            out.println("No solution");
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

    public String next() throws IOException {
        return nextToken();
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