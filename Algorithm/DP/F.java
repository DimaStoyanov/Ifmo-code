package Algorithm.DP;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;

/**
 * Created by Дима1 on 29.02.2016.
 */
public class F {
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new FileReader("palindrome.in"));
            PrintWriter out = new PrintWriter(new File("palindrome.out"));
            String line = br.readLine();
            final int len = line.length();
            StringBuilder str = new StringBuilder();
            for (int i = len - 1; i >= 0; i--) {
                str.append(line.charAt(i));
            }
            lcs(line, str.toString(), out);
            out.close();
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }

    private static void lcs(String a, String b, PrintWriter out) {
        int n = a.length();
        int m = b.length();
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
                if (a.charAt(i - 1) == b.charAt(j - 1)) {
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
        out.close();
    }

    static private void printLCS(int i, int j, Pair prev[][], String a, PrintWriter out) {
        if ((i == 0) || (j == 0))
            return;
        if (prev[i][j].x == i - 1 && prev[i][j].y == j - 1) {
            printLCS(i - 1, j - 1, prev, a, out);
            out.print(a.charAt(i - 1));
        } else {
            if (prev[i][j].x == i - 1 && prev[i][j].y == j) {
                printLCS(i - 1, j, prev, a, out);
            } else {
                printLCS(i, j - 1, prev, a, out);
            }
        }

    }

    static private class Pair {
        int x;
        int y;

        Pair(int a, int b) {
            x = a;
            y = b;
        }
    }
}
