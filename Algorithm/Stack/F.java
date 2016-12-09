package Algorithm.Stack;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 * Created by Дима1 on 15.11.2015.
 */
public class F {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(new File("hospital.in"));
        PrintWriter out = new PrintWriter(new File("hospital.out"));
        doublyLinkedList dlf = new doublyLinkedList();
        int r = sc.nextInt();
        String s;

        for (int i = 0; i < r; i++) {
            s = sc.next();
            switch (s.charAt(0)) {
                case '+': {
                    dlf.add(sc.nextInt());
                    break;
                }
                case '*': {
                    dlf.addmiddle(sc.nextInt());
                    break;
                }
                case '-': {
                    out.println(dlf.left());
                    break;
                }
            }
        }
        sc.close();
        out.close();
    }

    static class doublyLinkedList {
        int n;
        Node mark;
        Node tail;
        Node head;

        doublyLinkedList() {
            tail = new Node(null, null, 0);
            head = new Node(null, null, 0);
            head.prev = tail;
            tail.next = head;
            n = 0;
            mark = head;
        }

        void add(int x) {

            Node temp = new Node(tail, tail.next, x);
            tail.next.prev = temp;
            tail.next = temp;
            n++;
            if (n % 2 == 1) {
                mark = mark.prev;
            }
        }

        void addmiddle(int x) {
            Node temp = new Node(mark.prev, mark, x);
            mark.prev.next = temp;
            mark.prev = temp;
            n++;
            if (n % 2 == 1) {
                mark = mark.prev;
            }
        }

        int left() {
            Node tmp = head.prev;
            head.prev = head.prev.prev;
            head.prev.next = head;
            n--;
            if (n == 0) {
                mark = head;
            } else if (n % 2 == 1) {
                mark = mark.prev;
            }
            return tmp.i;
        }

        static class Node {
            Node prev;
            Node next;
            int i;

            Node(Node prev, Node next, int i) {
                this.prev = prev;
                this.next = next;
                this.i = i;
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

        double nextDouble() throws Exception {
            return Double.parseDouble(next());
        }

        void close() throws Exception {
            br.close();
        }
    }


}
