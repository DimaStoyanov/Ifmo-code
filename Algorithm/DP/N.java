package Algorithm.DP;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 * Created by Дима1 on 20.03.2016.
 * Project : Algoritm.Algorithm.DP.N
 * Start time project: 22:05
 */

public class N {
    private static final int MAX = 3000;
    private static final int INF = Integer.MAX_VALUE;

    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(new File("bookshelf.in"));
            PrintWriter out = new PrintWriter(new File("bookshelf.out"));
            int h[] = new int[MAX];
            int w[] = new int[MAX];
            int d[] = new int[MAX];
            int n = sc.nextInt();
            int l = sc.nextInt();
            for (int i = 0; i < n; i++) {
                h[i] = sc.nextInt();
                w[i] = sc.nextInt();
            }
            for (int i = 0; i <= n; i++) {
                d[i] = INF;
            }
            d[0] = 0;
            for (int i = 0; i <= n; i++) {
                int curHeight = 0;
                int curWeight = 0;
                for (int j = i - 1; j >= 0; --j) {
                    curWeight += w[j];
                    curHeight = Math.max(curHeight, h[j]);
                    if (curWeight <= l) {
                        d[i] = Math.min(d[i], curHeight + d[j]);
                    } else {
                        break;
                    }
                }
            }
            out.println(d[n]);
            out.close();
            sc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static class Scanner {
        BufferedReader br;
        StringTokenizer t;

        Scanner(File file) throws Exception {
            br = new BufferedReader(new FileReader(file));
            t = new StringTokenizer("");
        }

        public boolean hasNext() throws Exception {
            while (!t.hasMoreTokens()) {
                String line = br.readLine();
                if (line == null)
                    return false;
                t = new StringTokenizer(line);
            }
            return true;
        }


        public String next() throws Exception {
            if (hasNext()) {
                return t.nextToken();
            }
            return null;
        }

        public int nextInt() throws Exception {
            return Integer.parseInt(next());
        }

        public void close() throws Exception {
            br.close();
        }
    }
}