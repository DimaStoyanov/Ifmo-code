package Algorithm.Stack;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by Дима1 on 14.11.2015.
 */
public class A {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(new File("Algoritm/decode.in"));

        String str;
        Stack st = new Stack();
        while (sc.hasNext()) {
            str = sc.next();
            final int len = str.length();
            for (int i = 0; i < len; i++) {
                st.add(str.charAt(i));
            }
        }
        st.get();
        sc.close();
    }

    static class Stack {
        ArrayList<Character> line = new ArrayList<>();


        void add(Character c) throws Exception {
            int size = line.size();
            if (size > 0) {
                if (line.get(size - 1) == c) {

                    pop();
                } else {
                    line.add(c);
                }
            } else {
                line.add(c);
            }

        }

        void pop() throws Exception {
            line.remove(line.size() - 1);
        }

        void get() throws Exception {
            final int arlen = line.size();
            PrintWriter out = new PrintWriter((new File("Algoritm/decode.out")));
            for (Character aLine : line) {
                out.print(aLine);
            }
            out.close();
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

        double nextDouble() throws Exception {
            return Double.parseDouble(next());
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
