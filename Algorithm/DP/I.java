package Algorithm.DP;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 * Created by Дима1 on 04.03.2016.
 */
public class I {
    private static long dp[][];
    private static int n;

    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(new File("monsters.in"));
            PrintWriter out = new PrintWriter(new File("monsters.out"));

            n = sc.nextInt();
            long[] dread = new long[n + 1];
            int[] cost = new int[n + 1];
            for (int i = 0; i < n; i++) {
                dread[i] = sc.nextLong();
            }
            for (int i = 0; i < n; i++) {
                cost[i] = sc.nextInt();
            }
            dp = new long[n + 1][2 * n + 5];
            for (int i = 1; i < n + 1; i++) {
                for (int j = 0; j < 2 * n + 5; j++) {
                    dp[i][j] = Long.MIN_VALUE;
                }
            }
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < 2 * n; j++) {
                    dp[i + 1][j + (cost[i] == 2 ? 2 : 1)] = Math.max((dp[i][j] + dread[i]), dp[i + 1][j + (cost[i] == 2 ? 2 : 1)]);
                    if (!(dp[i][j] < dread[i])) {
                        dp[i + 1][j] = Math.max(dp[i][j], dp[i + 1][j]);
                    }
                }
            }


            for (int i = 0; i <= 2 * n; i++) {
                if (dp[n][i] > 0) {
                    out.println(i);
                    break;
                }
            }
            sc.close();
            out.close();


        } catch (java.lang.Exception e) {
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

        public long nextLong() throws Exception {
            return Long.parseLong(next());
        }

        public int nextInt() throws Exception {
            return Integer.parseInt(next());
        }

        public void close() throws Exception {
            br.close();
        }
    }
}
