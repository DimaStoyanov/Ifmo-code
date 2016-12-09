package Algorithm.DP;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by Дима1 on 28.02.2016.
 */
public class C {
    static ArrayList<Integer> ans = new ArrayList<>();

    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(new File("knapsack.in"));
            PrintWriter out = new PrintWriter(new File("knapsack.out"));
            int n = sc.nextInt();
            int m = sc.nextInt();
            int[] mas = new int[n + 1];
            int[] cost = new int[n + 1];
            for (int i = 1; i <= n; i++) {
                mas[i] = sc.nextInt();
            }
            for (int j = 1; j <= n; j++) {
                cost[j] = sc.nextInt();
            }

            int d[][] = new int[n + 1][m + 1];
            for (int i = 0; i <= m; i++) {
                d[0][i] = 0;
            }

            for (int j = 0; j <= n; j++) {
                d[j][0] = 0;
            }

            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= m; j++) {
                    if (j >= mas[i]) {
                        d[i][j] = Math.max(d[i - 1][j], d[i - 1][j - mas[i]] + cost[i]);
                    } else {
                        d[i][j] = d[i - 1][j];
                    }
                }
            }
            findAnsForKnapsack(n, m, d, mas, out);
            int count = ans.size();
            out.println(count);
            for (Integer an : ans) out.print(an + " ");
            sc.close();
            out.close();
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }

    static void findAnsForKnapsack(int i, int j, int d[][], int mas[], PrintWriter out) {
        if (d[i][j] == 0)
            return;
        if (d[i - 1][j] == d[i][j]) {
            findAnsForKnapsack(i - 1, j, d, mas, out);
        } else {
            findAnsForKnapsack(i - 1, j - mas[i], d, mas, out);
            ans.add(i);
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
