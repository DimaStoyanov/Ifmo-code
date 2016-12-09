package Algorithm.DP;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 * Created by Дима1 on 27.02.2016.
 */
public class B {
    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(new File("lcs.in"));
            PrintWriter out = new PrintWriter(new File("lcs.out"));
            int n = sc.nextInt();
            int a[] = new int[n + 1];
            for (int i = 1; i <= n; i++) {
                a[i] = sc.nextInt();
            }
            int m = sc.nextInt();
            int b[] = new int[m + 1];
            for (int i = 1; i < m + 1; i++) {
                b[i] = sc.nextInt();
            }
            int lcs[][] = new int[n + 1][m + 1];
            Pair[][] prev = new Pair[n + 1][m + 1];
            for (int i = 1; i <= n; i++) {
                lcs[i][0] = 0;
            }
            for (int j = 0; j <= m; j++) {
                lcs[0][j] = 0;
            }
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= m; j++) {
                    if (a[i] == b[j]) {
                        lcs[i][j] = lcs[i - 1][j - 1] + 1;
                        prev[i][j] = new Pair(i - 1, j - 1);
                    } else {
                        if (lcs[i - 1][j] >= lcs[i][j - 1]) {
                            lcs[i][j] = lcs[i - 1][j];
                            prev[i][j] = new Pair(i - 1, j);
                        } else {
                            lcs[i][j] = lcs[i][j - 1];
                            prev[i][j] = new Pair(i, j - 1);
                        }
                    }
                }
            }
            out.println(lcs[n][m]);
            printLCS(n, m, prev, a, out);
            sc.close();
            out.close();
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }

    static void printLCS(int i, int j, Pair prev[][], int[] a, PrintWriter out) {
        if ((i == 0) || (j == 0))
            return;
        if (prev[i][j].x == i - 1 && prev[i][j].y == j - 1) {
            printLCS(i - 1, j - 1, prev, a, out);
            out.print(a[i] + " ");
        } else {
            if (prev[i][j].x == i - 1 && prev[i][j].y == j) {
                printLCS(i - 1, j, prev, a, out);
            } else {
                printLCS(i, j - 1, prev, a, out);
            }
        }
    }

    static class Pair {
        int x;
        int y;

        Pair(int a, int b) {
            x = a;
            y = b;
        }
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
