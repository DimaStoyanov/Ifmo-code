package Algorithm.DP;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 * Created by Дима1 on 01.03.2016.
 */
public class G {
    private static int n;
    private static int m;
    private static int[] d;
    private static boolean[][] dp;

    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(new File("concert.in"));
            PrintWriter out = new PrintWriter(new File("concert.out"));
            n = sc.nextInt();
            d = new int[n + 1];
            for (int i = 1; i <= n; i++) {
                d[i] = sc.nextInt();
            }
            int b = sc.nextInt();
            m = sc.nextInt();
            dp = new boolean[n + 2][m + 1];
            maxVolume(1, b);
            out.println(getMaxVolume());
            sc.close();
            out.close();
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }


    private static void maxVolume(int i, int j) {
        if (j < 0 || j > m) return;
        if (i == n + 1) {
            dp[i][j] = true;
            return;
        }
        if (!dp[i][j]) {
            dp[i][j] = true;
            maxVolume(i + 1, j + d[i]);
            maxVolume(i + 1, j - d[i]);
        }
    }

    private static int getMaxVolume() {
        for (int j = m; j >= 0; j--) {
            if (dp[n + 1][j]) return j;
        }
        return -1;
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
