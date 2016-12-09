package Algorithm.Stack;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 * Created by Дима1 on 15.11.2015.
 */
public class E {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(new File("formation.in"));
        PrintWriter out = new PrintWriter(new File("formation.out"));
        String action;
        int n = sc.nextInt();
        int m = sc.nextInt();
        LinkedList form = new LinkedList(n);
        for (int i = 0; i < m; i++) {
            action = sc.next();
            switch (action) {
                case "left": {
                    form.left(sc.nextInt(), sc.nextInt());
                    break;
                }
                case "right": {

                    form.right(sc.nextInt(), sc.nextInt());
                    break;
                }
                case "leave": {

                    form.leave(sc.nextInt());
                    break;
                }
                case "name": {
                    int x = sc.nextInt();
                    out.println(form.list[x].left + " " + form.list[x].right);
                }
            }
        }
        sc.close();
        out.close();
    }

    static class LinkedList {
        Node list[];

        LinkedList(int n) {
            list = new Node[n + 1];
            for (int i = 1; i <= n; i++) {
                list[i] = new Node();
            }
        }

        void left(int i, int j) {
            if (list[j].left != 0) {
                list[list[j].left].right = i;
                list[i].left = list[j].left;
            }
            list[i].right = j;
            list[j].left = i;
        }

        void right(int i, int j) {
            if (list[j].right != 0) {
                list[list[j].right].left = i;
                list[i].right = list[j].right;
            }
            list[i].left = j;
            list[j].right = i;


        }

        void leave(int i) {
            if (list[i].left != 0) {
                list[list[i].left].right = list[i].right;
            }
            if (list[i].right != 0) {
                list[list[i].right].left = list[i].left;
            }
            list[i].left = 0;
            list[i].right = 0;
        }

        static class Node {
            int left;
            int right;

            Node() {
                left = 0;
                right = 0;
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
