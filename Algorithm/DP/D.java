package Algorithm.DP;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;

/**
 * Created by Дима1 on 28.02.2016.
 */
public class D {
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new FileReader("levenshtein.in"));
            PrintWriter out = new PrintWriter(new File("levenshtein.out"));
            String a;
            String b;
            a = br.readLine();
            b = br.readLine();
            out.println(LD(a, b));
            br.close();
            out.close();
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }

    }

    static int LD(String a, String b) {
        int m = a.length();
        int n = b.length();
        if (n == 0)
            return m;
        else if (m == 0)
            return n;
        int[][] matrix = new int[m + 1][n + 1];
        for (int i = 0; i <= n; ++i) {
            matrix[0][i] = i;
        }
        for (int j = 0; j <= m; ++j) {
            matrix[j][0] = j;
        }
        int above, left, diagonal, cost;
        for (int i = 1; i <= m; ++i) {
            for (int j = 1; j <= n; ++j) {
                if (a.charAt(i - 1) == b.charAt(j - 1)) {
                    cost = 0;
                } else {
                    cost = 1;
                }
                above = matrix[i - 1][j];
                left = matrix[i][j - 1];
                diagonal = matrix[i - 1][j - 1];
                matrix[i][j] = Math.min(Math.min(above + 1, left + 1), diagonal + cost);
            }
        }
        return matrix[m][n];
    }


}
