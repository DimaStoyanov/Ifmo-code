package Algorithm.Graph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Blackbird on 01.04.2016.
 * Project : Algoritm.Graph.Algorithm.Tree.A
 * Start time : 17:14
 */


public class A {
    private static int n;

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(new File("spantree.in"));
        PrintWriter out = new PrintWriter(new File("spantree.out"));
        n = sc.nextInt();
        int x[] = new int[n];
        int y[] = new int[n];
        for (int i = 0; i < n; i++) {
            x[i] = sc.nextInt();
            y[i] = sc.nextInt();
        }
        double d[] = new double[n];
        Arrays.fill(d, Integer.MAX_VALUE);
        double result = 0;
        boolean[] used = new boolean[n];
        int to = 0;
        while (true) {
            used[to] = true;
            double curMin = Double.MAX_VALUE;
            int index = -1;
            boolean ok = false;
            for (int i = 0; i < n; i++) {
                if (!used[i]) {
                    ok = true;
                    double temp = curMin;
                    d[i] = Math.min(d[i], Math.sqrt((x[i] - x[to]) * (x[i] - x[to]) + (y[i] - y[to]) * (y[i] - y[to])));
                    curMin = Math.min(curMin, d[i]);
                    if (temp != curMin) {
                        index = i;
                    }
                }
            }
            if (!ok) break;
            result += curMin;
            to = index;
        }
        out.println(result);
        out.close();
        sc.close();
    }


    static class Scanner {
        BufferedReader br;
        StringTokenizer t;

        Scanner(File file) throws Exception {
            br = new BufferedReader(new FileReader(file));
            t = new StringTokenizer("");
        }

        boolean hasNext() throws Exception {
            while (!t.hasMoreTokens()) {
                String line = br.readLine();
                if (line == null)
                    return false;
                t = new StringTokenizer(line);
            }
            return true;
        }


        String next() throws Exception {
            if (hasNext()) {
                return t.nextToken();
            }
            return null;
        }

        int nextInt() throws Exception {
            return Integer.parseInt(next());
        }

        void close() throws Exception {
            br.close();
        }
    }
}
