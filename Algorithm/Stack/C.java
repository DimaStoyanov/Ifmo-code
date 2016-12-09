package Algorithm.Stack;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by Дима1 on 15.11.2015.
 */
public class C {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(new File("postfix.in"));
        PrintWriter out = new PrintWriter(new File("postfix.out"));
        Stack pf = new Stack();
        String c = sc.next();
        pf.push(Integer.parseInt(c));
        while (true) {
            c = sc.next();
            if (c == null) break;
            switch (c) {
                case "+": {
                    int a, b;
                    b = pf.pop();
                    a = pf.pop();
                    pf.push(a + b);
                    break;
                }
                case "-": {
                    int a, b;
                    b = pf.pop();
                    a = pf.pop();
                    pf.push(a - b);
                    break;
                }
                case "*": {
                    int a, b;
                    b = pf.pop();
                    a = pf.pop();
                    pf.push(a * b);
                    break;
                }
                default: {
                    pf.push(Integer.parseInt(c));
                }
            }
        }
        out.println(pf.result());
        sc.close();
        out.close();
    }

    static class Stack {
        ArrayList<Integer> st = new ArrayList<>();

        void push(int x) {
            st.add(x);
        }

        int pop() {
            int t = st.get(st.size() - 1);
            st.remove(st.size() - 1);
            return t;
        }

        int result() {
            return st.get(0);
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
