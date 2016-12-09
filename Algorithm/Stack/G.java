package Algorithm.Stack;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 * Created by Дима1 on 16.11.2015.
 */
public class G {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(new File("saloon.in"));
        PrintWriter out = new PrintWriter(new File("saloon.out"));
        Order or = new Order();
        int r = sc.nextInt();
        for (int i = 0; i < r; i++) {
            out.println(or.add(sc.nextInt() * 60 + sc.nextInt(), sc.nextInt()));
        }
        sc.close();
        out.close();
    }

    static class Order {
        int bt; // begin time of cutting first person in order
        int n;

        Order() {
            n = 0;
        }


        String add(int t, int k) {
            if (n == 0) {
                bt = t;
                n++;
                return ((bt + 20) / 60 + " " + (bt + 20) % 60);
            } else {
                if (t - bt < 20) {
                    return add1(t, k);
                } else {
                    int q = (t - bt) / 20;
                    n -= q;
                    bt += 20 * q;
                    if (n <= 0) {
                        n = 0;
                        bt = t;
                    }
                    return add1(t, k);
                }
            }
        }

        String add1(int t, int k) {
            if (k >= n) {
                n++;
                int result = bt + n * 20;
                return (result / 60 + " " + result % 60);
            } else
                return t / 60 + " " + t % 60;
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

        int nextInt() throws Exception {
            return Integer.parseInt(next());
        }

        String next() throws Exception {
            if (hasNext()) {
                return t.nextToken();
            }
            return null;
        }

        void close() throws Exception {
            br.close();
        }
    }

}
