package Algorithm.Stack;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 * Created by Дима1 on 14.11.2015.
 */
public class D {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(new File("stack-min.in"));
        PrintWriter out = new PrintWriter(new File("stack-min.out"));
        int y;
        int n = sc.nextInt();
        Stack st = new Stack();
        for (int i = 0; i < n; i++) {
            y = sc.nextInt();
            switch (y) {
                case 1: {
                    st.add(sc.nextInt());
                    break;
                }
                case 2: {
                    st.pop();
                    break;
                }
                case 3: {
                    out.println(st.min());
                    break;
                }
            }
        }
        sc.close();
        out.close();
    }

    static class Stack {
        Link end;

        Stack() {
            end = new Link(null, Integer.MAX_VALUE);
        }

        void add(int x) throws Exception {
            int a;
            if (x < end.min) {
                a = x;
            } else {
                a = end.min;
            }
            end = new Link(end, a);
        }

        void pop() throws Exception {
            end = end.prev;
        }

        int min() throws Exception {
            return end.min;
        }

        static class Link {
            Link prev;
            int min;

            Link(Link prev, int min) {
                this.prev = prev;
                this.min = min;
            }
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


