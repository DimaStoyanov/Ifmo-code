package Algorithm.Tree;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 * Created by Дима1 on 17.12.2015.
 */
public class F {
    public static void main(String[] args) throws Exception {
        int n, m;
        Scanner sc = new Scanner(new File("parking.in"));
        PrintWriter out = new PrintWriter(new File("parking.out"));
        n = sc.nextInt();
        m = sc.nextInt();
        double tmp = Math.log10(n) / Math.log10(2);
        if ((tmp - (tmp % 1)) != tmp) {
            tmp = Math.pow(2, tmp - (tmp % 1) + 1);
        } else {
            tmp = Math.pow(2, tmp);
        }
        int rn = (int) tmp;
        RMQ tree = new RMQ(rn);
        tree.addition(n);
        tree.build(1, rn, 2 * rn - 1);
        for (int i = 0; i < m; i++) {
            String cmd = sc.next();
            if (cmd.equals("enter")) {
                int u = sc.nextInt();
                out.println(tree.add(u) + 1 - rn);

            } else {
                tree.set(rn + sc.nextInt() - 1, false);
            }
        }
        out.close();
        sc.close();
    }


    static class RMQ {
        boolean list[];
        int n;


        // boolean ts;
        RMQ(int n) {
            list = new boolean[2 * n];
            this.n = n;
        }

        void addition(int tmp) {
            for (int i = tmp + n; i < 2 * n; i++) list[i] = true;
        }

        void build(int v, int tl, int tr) {
            if (tl != tr) {
                int tm = (tl + tr) / 2;
                build(v * 2, tl, tm);
                build(v * 2 + 1, tm + 1, tr);

                list[v] = list[v * 2] && list[v * 2 + 1];
            }
        }


        int down(int v) {
            if (v * 2 < 2 * n) {
                if (!list[v * 2]) {
                    return down(v * 2);
                } else {
                    return down(v * 2 + 1);
                }
            } else {
                return v;
            }
        }

        int add(int x) {
            if (!list[x + n - 1]) {
                set(x + n - 1, true);
                return x + n - 1;
            }
            int t = find(1, n, 2 * n - 1, x + n - 1);
            if (t != 0) {
                set(t, true);
                return t;
            } else {
                int ans = down(1);
                set(ans, true);
                return ans;
            }
        }

        int find(int v, int tl, int tr, int x) {
            if (tl != tr) {
                int tm = (tl + tr) / 2;
                if (tm > x && !list[2 * v]) {
                    int a = find(2 * v, tl, tm, x);
                    if (a != 0)
                        return a;
                }
                if (tr > x && !list[2 * v + 1]) {
                    int b = find(2 * v + 1, tm + 1, tr, x);
                    if (b != 0) {
                        return b;
                    }
                }
                return 0;
            } else {
                return v;
            }
        }

        void set(int i, boolean value) {
            list[i] = value;
            while ((i /= 2) != 0) {
                list[i] = (list[2 * i] && list[2 * i + 1]);
            }
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
