package Algorithm.Stack;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 * Created by Дима1 on 15.11.2015.
 */
public class H {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(new File("hemoglobin.in"));
        PrintWriter out = new PrintWriter(new File("hemoglobin.out"));
        int n = sc.nextInt();
        String s;
        Stack st = new Stack(n);
        for (int i = 0; i < n; i++) {
            s = sc.next();
            switch (s.charAt(0)) {
                case '+': {
                    int x = Integer.parseInt(s.substring(1));
                    st.push(x);
                    break;
                }
                case '-': {
                    out.println(st.pop());
                    break;
                }
                case '?': {
                    int k = Integer.parseInt(s.substring(1));
                    out.println(st.kth(k));
                    break;
                }
            }
        }
        sc.close();
        out.close();
    }

    static class Stack {
        int greg[];
        int top;

        Stack(int n) {
            top = 0;
            greg = new int[n];
        }

        void push(int x) {
            if (top != 0) {
                greg[top] = greg[top - 1] + x;
            } else greg[top] = x;
            top++;
        }

        int pop() {
            if (top == 1) {
                return greg[--top];
            } else {
                int ans = greg[top - 1] - greg[top - 2];
                top--;
                return ans;
            }
        }

        int kth(int w) {
            if (top - w == 0) return greg[top - 1];
            else
                return greg[top - 1] - greg[top - 1 - w];
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

        double nextDouble() throws Exception {
            return Double.parseDouble(next());
        }

        void close() throws Exception {
            br.close();
        }
    }

}
