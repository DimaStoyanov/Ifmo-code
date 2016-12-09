package Algorithm.DP;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 * Created by Дима1 on 18.03.2016.
 */
public class M {
    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(new File("free.in"));
            PrintWriter out = new PrintWriter(new File("free.out"));
            int a = sc.nextInt();
            int b = sc.nextInt();
            int c = sc.nextInt();
            int freeStars[][][] = new int[c + 1][b + 1][a + 1];
            int count[][][] = new int[c + 1][b + 1][a + 1];
            int k = sc.nextInt();
            int i, j, n, curFreeStars, curCount;
            for (i = 0; i <= c; i++) {
                for (j = 0; j <= b; j++) {
                    for (n = 0; n <= a; n++) {
                        curFreeStars = 0;
                        curCount = 0;
                        if (n != 0) {
                            freeStars[i][j][n] = freeStars[i][j][n - 1] + 1;
                            if (freeStars[i][j][n] >= k) {
                                freeStars[i][j][n] = 0;
                                count[i][j][n] = count[i][j][n - 1] + 1;
                            } else count[i][j][n] = count[i][j][n - 1];
                        }

                        if (j != 0) {
                            curFreeStars = freeStars[i][j - 1][n] + 2;
                            if (curFreeStars >= k) {
                                curFreeStars = 0;
                                curCount = count[i][j - 1][n] + 1;
                            } else curCount = count[i][j - 1][n];
                        }
                        if ((curCount > count[i][j][n]) || ((curCount == count[i][j][n]) && (curFreeStars > freeStars[i][j][n]))) {
                            count[i][j][n] = curCount;
                            freeStars[i][j][n] = curFreeStars;
                        }
                        if (i != 0) {
                            curFreeStars = freeStars[i - 1][j][n] + 3;
                            if (curFreeStars >= k) {
                                curFreeStars = 0;
                                curCount = count[i - 1][j][n] + 1;
                            } else curCount = count[i - 1][j][n];
                        }

                        if ((curCount > count[i][j][n]) || ((curCount == count[i][j][n]) && (curFreeStars > freeStars[i][j][n]))) {
                            count[i][j][n] = curCount;
                            freeStars[i][j][n] = curFreeStars;
                        }
                    }
                }
            }

            out.println(count[c][b][a]);
            sc.close();
            out.close();

        } catch (java.lang.Exception e) {
            e.printStackTrace();
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

        public boolean nextBoolean() throws Exception {
            return nextInt() == 1;
        }

        void close() throws Exception {
            br.close();
        }
    }
}
