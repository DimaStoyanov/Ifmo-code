package Algorithm.Stack;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 * Created by Дима1 on 16.11.2015.
 */
public class I {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(new File("bureaucracy.in"));
        PrintWriter out = new PrintWriter(new File("bureaucracy.out"));
        int n = sc.nextInt();
        int m = sc.nextInt();
        if (n == 0) out.println(-1);
        else {
            circularQueue queue = new circularQueue(n, m, out);
            for (int i = 0; i < n; i++) {
                queue.add(sc.nextInt());
            }
            int p = queue.calculate();
            if (p == -1) out.println(p);
            else {
                out.println(p);
                queue.showQueue(out);
            }
        }
        sc.close();
        out.close();
    }

    static class circularQueue {
        Node tail;
        Node head;
        int n; // count of people in queue
        int m;
        int count;
        boolean first;

        circularQueue(int x, int y, PrintWriter out) {
            head = null;
            tail = null;
            n = x;
            m = y;
            count = 0;
            first = true;
        }

        void add(int k) {
            if (first) {
                head = new Node(null, null, k);
                tail = head;
                first = false;
            } else {
                tail = new Node(head, tail, k);
                tail.next.prev = tail;
                head.next = tail;
            }
        }

        void pop() {
            if (n == 1) {
                n--;
            }
            head.prev.next = tail;
            tail.prev = head.prev;
            head = head.prev;
            n--;
        }

        void shift() {
            tail = head;
            head = head.prev;
        }

        int calculate() {
            count = m % n;
            m /= n;
            int w = n;
            step(w);
            while (count > 0 && n > 0) {
                w = n;
                m = count / n;
                count %= n;
                step(w);
            }
            return n == 0 ? -1 : n;
        }

        void step(int w) {
            if (m == 0) {
                for (int i = 0; i < count; i++)
                    skipOneQueue();
                count = 0;
            } else {
                for (int i = 0; i < w; i++) {
                    skipTheQueue();
                }
            }
        }

        void skipTheQueue() {
            if (m >= head.k) {
                count += m - head.k;
                head.k = 0;
                pop();
            } else {
                head.k -= m;
                shift();
            }
        }

        void skipOneQueue() {
            if (head.k == 1) {
                head.k--;
                pop();
            } else {
                head.k--;
                shift();
            }
        }

        void showQueue(PrintWriter out) {
            int w = n;
            for (int i = 0; i < w - 1; i++) {
                out.print(head.k + " ");
            }
            out.println(head.k);
        }

        static class Node {
            Node prev;
            Node next;
            int k; //   the number of required reference

            Node(Node prev, Node next, int k) {
                this.prev = prev;
                this.next = next;
                this.k = k;
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

