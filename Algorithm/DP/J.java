package Algorithm.DP;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 * Created by Дима1 on 13.03.2016.
 */
public class J {
    private static int n;
    private static int m;

    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(new File("nice.in"));
            PrintWriter out = new PrintWriter(new File("nice.out"));
            int x1 = sc.nextInt();
            int x2 = sc.nextInt();
            m = Math.min(x1, x2);
            n = Math.max(x1, x2);
            int x = 1;
            for (int i = 1; i <= m; i++) {
                x <<= 1;
            }
            int a[][] = new int[n + 1][x + 1];
            for (int i = 0; i < x; i++) {
                a[1][i] = 1;
            }

            for (int i = 2; i <= n; i++) {
                for (int j = 0; j < x; j++) {
                    for (int k = 0; k < x; k++) {
                        if (checkProfile(j, k)) {
                            a[i][k] = a[i - 1][j] + a[i][k];
                        }
                    }
                }
            }
            int result = 0;
            for (int i = 0; i < x; i++) {
                result += a[n][i];
            }
            out.println(result);
            sc.close();
            out.close();
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean checkProfile(int x, int y) {
        int a[] = new int[m + 1];
        int b[] = new int[m + 1];
        for (int i = 1; i <= m; i++) {
            a[i] = x & 1;
            b[i] = y & 1;
            x >>= 1;
            y >>= 1;
            if ((i > 1) && (a[i] == a[i - 1]) && (a[i] == b[i]) && (a[i] == b[i - 1]))
                return false;
        }
        return true;
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
