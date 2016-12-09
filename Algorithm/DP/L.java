package Algorithm.DP;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 * Created by Дима1 on 14.03.2016.
 */
public class L {
    private static final int INF = Integer.MAX_VALUE;

    public static void main(String[] args) {

        try {
            int weight[][] = new int[20][20];
            int dp[][] = new int[1 << 20][20];
            Scanner sc = new Scanner(new File("salesman.in"));
            PrintWriter out = new PrintWriter(new File("salesman.out"));
            int n = sc.nextInt();
            int m = sc.nextInt();
            for (int i = 0; i < 20; i++)
                for (int j = 0; j < 20; j++)
                    weight[i][j] = INF;

            for (int i = 0; i < m; i++) {
                int x, y;
                x = sc.nextInt();
                y = sc.nextInt();
                weight[x][y] = weight[y][x] = sc.nextInt();
            }
            n++;
            for (int i = 1; i < n; i++)
                weight[0][i] = weight[i][0] = 0;
            dp[1][0] = 0;
            int N = 1 << n;
            for (int i = 1; i < N; i += 2) {
                for (int j = (i == 1) ? 1 : 0; j < n; j++) {
                    dp[i][j] = INF;
                    if (j > 0 && get(i, j)) {
                        int t = i ^ (1 << j);
                        for (int k = 0; k < n; k++) {
                            if (get(i, k) && weight[k][j] != INF)
                                dp[i][j] = Math.min(dp[i][j], (dp[t][k] == INF || weight[k][j] == INF) ? INF : dp[t][k] + weight[k][j]);
                        }
                    }
                }
            }
            int ans = INF;
            for (int j = 1; j < n; j++) {
                if (weight[j][0] != INF)
                    ans = Math.min(ans, dp[N - 1][j] + weight[j][0]);
            }

            if (ans == INF)
                out.println(-1);
            else
                out.println(ans);
            sc.close();
            out.close();
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean get(int x, int bit) {
        return (x & (1 << bit)) != 0;
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