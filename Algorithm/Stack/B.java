package Algorithm.Stack;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Дима1 on 15.11.2015.
 */
public class B {
    public static void main(String[] args) throws Exception {
        String line;
        Scanner sc = new Scanner(new File("brackets.in"));
        PrintWriter out = new PrintWriter(new File("brackets.out"));
        Stack st = new Stack();
        boolean flag = true;
        while (sc.hasNext()) {
            line = sc.next();
            final int len = line.length();
            for (int i = 0; i < len; i++) {
                char t = line.charAt(i);
                if (t == '[' || t == '{' || t == '(') {
                    st.push(t);
                } else {
                    if (st.compare(t)) {
                        st.pop();

                    } else {
                        flag = false;
                        break;
                    }
                }
            }
        }
        if (st.isEmpty() && flag) out.println("YES");
        else out.println("NO");
        sc.close();
        out.close();
    }

    static class Stack {
        ArrayList<Character> psp = new ArrayList<>();

        void push(char x) throws Exception {
            psp.add(x);
        }

        void pop() throws Exception {
            psp.remove(psp.size() - 1);
        }

        boolean compare(char y) throws Exception {
            if (psp.isEmpty()) {
                return false;
            } else {
                char s = psp.get(psp.size() - 1);
                return y == ']' && s == '[' || y == '}' && s == '{' || y == ')' && s == '(';
            }
        }

        boolean isEmpty() throws Exception {
            return psp.isEmpty();
        }
    }
}
