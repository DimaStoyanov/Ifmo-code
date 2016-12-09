package Algorithm.Tree;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;


/**
 * Created by Дима1 on 17.12.2015.
 */
public class B {
    public static void main(String[] args) throws Exception {
        int n;
        Scanner sc = new Scanner(new File("rsq.in"));
        PrintWriter out = new PrintWriter(new File("rsq.out"));
        n = sc.nextInt();
        double tmp = Math.log10(n) / Math.log10(2);
        if ((tmp - (tmp % 1)) != tmp) {
            tmp = Math.pow(2, tmp - (tmp % 1) + 1);
        } else {
            tmp = Math.pow(2, tmp);
        }
        int rn = (int) tmp;
        RSQ tree = new RSQ(rn);
        for (int i = 0; i < n; i++) {
            tree.add(sc.nextInt(), i);
        }
        tree.addition(n);
        tree.build(1, rn, 2 * rn - 1);

        while (sc.hasNext()) {
            String cmd = sc.next();
            if (cmd.equals("sum")) {
                out.println(tree.sum(sc.nextInt(), sc.nextInt()));
            } else {
                tree.set(sc.nextInt(), sc.nextLong());
            }
        }
        out.close();
        sc.close();
    }

    static class RSQ {

        int n;
        long list[];

        RSQ(int n) {
            list = new long[2 * n];
            this.n = n;
        }

        void add(long x, int num) {
            list[n + num] = x;
        }

        void addition(int tmp) {
            for (int i = tmp + n; i < 2 * n; i++) list[i] = 0;
        }

        void build(int v, int tl, int tr) {
            if (tl != tr) {
                int tm = (tl + tr) / 2;
                build(v * 2, tl, tm);
                build(v * 2 + 1, tm + 1, tr);

                list[v] = list[v * 2] + list[v * 2 + 1];
            }
        }

        void set(int i, long x) {
            int q = n;
            i += q - 1;
            list[i] = x;
            while ((i /= 2) != 0)
                list[i] = list[2 * i] + list[2 * i + 1];
        }


        long sum(int l, int r) {
            long ans = 0;
            int q = n;
            l += q - 1;
            r += q - 1;
            while (l <= r) {
                if ((l & 1) == 1)
                    ans += list[l];
                if ((r & 1) == 0)
                    ans += list[r];
                l = (l + 1) / 2;
                r = (r - 1) / 2;
            }
            return ans;
        }
    }

    static class Scanner {
        BufferedReader br;
        StringTokenizer t = new StringTokenizer("");
        String savedToken;
        boolean flag;

        Scanner(File file) throws Exception {


            br = new BufferedReader(new FileReader(file));
            StringTokenizer t = new StringTokenizer("");
        }

        int nextInt() throws Exception {
            return Integer.parseInt(next());
        }

        long nextLong() throws Exception {
            return Long.parseLong(next());
        }

        boolean hasNext() throws Exception {
            if (flag)
                return true;
            while (!t.hasMoreTokens()) {
                String line = br.readLine();
                if (line == null) {
                    return false;
                }
                t = new StringTokenizer(line);
            }
            return t.hasMoreTokens();
        }

        String next() throws Exception {
            if (hasNext()) {
                if (flag) {
                    flag = false;
                    return savedToken;
                }
                return t.nextToken();
            } else {
                return null;
            }
        }

        private String nextToken() throws Exception {
            savedToken = next();
            flag = true;
            return savedToken;
        }

        void close() throws Exception {
            br.close();
        }
    }
}
