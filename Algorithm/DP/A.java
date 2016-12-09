package Algorithm.DP;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by Дима1 on 27.02.2016.
 */
public class A {
    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(new File("lis.in"));
            PrintWriter out = new PrintWriter(new File("lis.out"));
            int n = sc.nextInt();
            int a[] = new int[n];
            for (int i = 0; i < n; i++) {
                a[i] = sc.nextInt();
            }
            int d[] = new int[n + 1];
            int position[] = new int[n + 1];
            int prev[] = new int[n];
            int length = 0;
            position[0] = -1;
            d[0] = -Integer.MAX_VALUE;
            for (int i = 1; i <= n; i++) {
                d[i] = Integer.MAX_VALUE;
            }
            for (int i = 0; i < n; i++) {
                int j = binarySearch(d, a[i]);
                if ((d[j - 1] < a[i]) && a[i] < d[j]) {
                    d[j] = a[i];
                    position[j] = i;
                    prev[i] = position[j - 1];
                    length = Math.max(length, j);
                }
            }
            out.println(length);
            int curPosition = position[length];
            ArrayList<Integer> answer = new ArrayList<>();
            while (curPosition != -1) {
                answer.add(a[curPosition]);
                curPosition = prev[curPosition];
            }
            final int len = answer.size() - 1;
            for (int i = len; i >= 0; i--) {
                out.print(answer.get(i) + " ");
            }
            sc.close();
            out.close();
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }

    static int binarySearch(int a[], int key) {
        return binarySearch(a, key, -1, a.length);
    }

    static int binarySearch(int a[], int key, int l, int r) {
        while (l < r - 1) {
            int m = (l + r) >> 1;
            if (a[m] < key)
                l = m;
            else
                r = m;
        }
        return a[r] > key ? r : r + 1;
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
