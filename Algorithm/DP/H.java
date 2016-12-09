package Algorithm.DP;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 * Created by Дима1 on 03.03.2016.
 */
public class H {
    private static int[] d;
    private static int x;
    private static int n;
    private static int dp[][];

    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(new File("tapcoder.in"));
            PrintWriter out = new PrintWriter(new File("tapcoder.out"));
            n = sc.nextInt();
            d = new int[n + 1];
            for (int i = 1; i <= n; i++)
                d[i] = sc.nextInt();
            x = sc.nextInt();
            dp = new int[n + 2][2200];
            initArray();
            out.println(findHameleonOfTheYear(1, x));
            sc.close();
            out.close();
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }

    private static void initArray() {
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j < 2200; j++) {
                dp[i][j] = -1;
            }
        }
    }

    private static int findHameleonOfTheYear(int i, int j) {
        if (i == n + 1) {
            return 0;
        }
        if (j >= 2200) {
            if (j - d[i] < 2200) {
                return 1 + findHameleonOfTheYear(i + 1, j - d[i] > 0 ? j - d[i] : 0);
            } else
                return -10;

        } else {
            if (dp[i][j] == -1) {
                int ensure = j + d[i] >= 2200 ? 1 : 0;
                int ans1 = findHameleonOfTheYear(i + 1, j + d[i]);
                ans1 = ans1 < 0 ? 0 : ans1 + ensure;
                int ans2 = findHameleonOfTheYear(i + 1, j - d[i] > 0 ? j - d[i] : 0);
                ans2 = ans2 < 0 ? 0 : ans2;
                return dp[i][j] = Math.max(ans1, ans2);
            } else
                return dp[i][j];
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
