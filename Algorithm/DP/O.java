package Algorithm.DP;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Blackbird on 21.03.2016.
 * Project : O
 * Start Time : 20:04
 */
public class O {
    private static final int INF = Integer.MAX_VALUE;

    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(new File("umbrella.in"));
            PrintWriter out = new PrintWriter(new File("umbrella.out"));
            int x[] = new int[6000];
            int c[] = new int[200000];
            int d[] = new int[6000];


            int n, m;
            n = sc.nextInt();
            m = sc.nextInt();

            for (int i = 0; i < n; i++)
                x[i] = sc.nextInt();

            for (int i = 0; i < m; i++)
                c[i] = sc.nextInt();

            for (int i = m - 2; i >= 0; i--)
                c[i] = Math.min(c[i], c[i + 1]);

            for (int i = 0; i < n + 1; i++)
                d[i] = INF;

            d[0] = 0;
            Arrays.sort(x);
       /*     int temp[] = new int[6000];
            for(int i=0; i<n; i++){
                temp[i]=x[n-i-1];
            }
            x=temp;
         */
            for (int i = 1; i <= n; i++)
                for (int j = 0; j < i; j++)
                    d[i] = Math.min(d[i], sum(d[j], c[sub(x[i - 1], x[j])]));

            out.println(d[1]);
            out.close();
            sc.close();


            sc.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int sum(int op1, int op2) {
        return (op1 == INF || op2 == INF) ? INF : op1 + op2;
    }

    private static int sub(int op1, int op2) {
        return (op1 == INF || op2 == INF) ? INF : op1 - op2;
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