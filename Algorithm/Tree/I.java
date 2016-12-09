package Algorithm.Tree;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Дима1 on 20.12.2015.
 */
public class I {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(new File("candies.in"));
        PrintWriter out = new PrintWriter(new File("candies.out"));

        int n = sc.nextInt();
        int a[] = new int[n];
        for (int i = 0; i < n; i++)
            a[i] = sc.nextInt();
        Arrays.sort(a);
        Candies tree = new Candies(n);
        int m = sc.nextInt();
        for (int i = 0; i < m; i++) {
            int x = sc.nextInt();
            int last = tree.binSearch(a, x, n);
            if (last <= n - 1) {
                out.println(n - last);
                tree.update(last);
            } else out.println(0);
        }

        sc.close();
        out.close();
    }

    static class Candies {
        int list[];
        int n;
        int ans;

        Candies(int a) {
            n = 1;
            while (n < a) {
                n <<= 1;
            }
            list = new int[n << 1];
            for (int i = 0; i < (n << 1); i++)
                list[i] = 0;
        }

        private int up(int v) {
            ans = 0;
            while (v > 0) {
                ans += list[v];
                v /= 2;
            }
            return ans;
        }

        int binSearch(int arr[], int x, int t) {
            return binSearchR(0, t - 1, arr, x, t);
        }


        private int binSearchR(int tl, int tr, int arr[], int x, int tmp) {
            if (tl < tr) {
                int v = (tl + tr) / 2 + 1;
                int b = up(v + n);
                if (arr[v] - b == x) {
                    if (v != 0) {
                        int c = up(v + n - 1);
                        if (arr[v - 1] - c != x) {
                            return v;
                        } else {
                            return binSearchR(tl, v - 1, arr, x, tmp);
                        }
                    } else {
                        return v;
                    }
                } else if (arr[v] - b > x) {
                    return binSearchR(tl, v - 1, arr, x, tmp);
                } else {
                    return binSearchR(v + 1, tr, arr, x, tmp);
                }
            } else {
                int d = up(tl + n);
                if (tl >= tmp) return tl + 1;
                if (arr[tl] - d >= x)
                    return tl;
                else
                    return tl + 1;
            }
        }

        void update(int v) {
            v += n;
            if (n == v) list[1]++;
            else
                updateR(1, n, 2 * n - 1, v);
        }

        private void updateR(int v, int tl, int tr, int x) {
            if (tl != tr) {
                int tm = (tl + tr) / 2;
                if (tm >= x) {
                    if (tl >= x) {
                        list[2 * v]++;
                    } else {
                        updateR(v * 2, tl, tm, x);
                    }
                    list[2 * v + 1]++;
                } else if (tr >= x) {
                    if (tm + 1 >= x) {
                        list[2 * v + 1]++;
                    } else
                        updateR(v * 2 + 1, tm + 1, tr, x);
                }
            } else {
                list[v]++;
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

