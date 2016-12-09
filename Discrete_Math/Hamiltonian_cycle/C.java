package Discrete_Math.Hamiltonian_cycle;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Objects;
import java.util.Scanner;

/**
 * Created by Blackbird on 04.11.2016.
 * Project : Algorithm.LCA.C
 * Start time : 0:16
 */

public class C {

    private LinkedList<Integer> answer;
    private Scanner sc;

    public static void main(String[] args) throws IOException {
        Locale.setDefault(Locale.US);
        new C().run();
    }

    public void solve() throws IOException {
        sc = new Scanner(System.in);
        int n = sc.nextInt();
        answer = new LinkedList<>();
        answer.add(1);
        if (n > 1) {
            answer.add(isAdjective(0, 1) ? 1 : 0, 2);
            while (answer.size() < n)
                binSearch(0, answer.size(), answer.size() + 1);
        }
        System.out.print("0 ");
        answer.forEach(integer -> System.out.print(integer + " "));
    }

    private boolean isAdjective(int v, int u) {
        System.out.println(1 + " " + (v + 1) + " " + (u + 1));
        System.out.flush();
        return Objects.equals(sc.next(), "YES");
    }

    private void binSearch(int l, int r, int insert) {
        if (l == r)
            answer.add(l, insert);
        else if (isAdjective(insert - 1, answer.get(l + r >> 1) - 1))
            binSearch(l, l + r >> 1, insert);
        else
            binSearch((l + r >> 1) + 1, r, insert);
    }

    public void run() {
        try {
            solve();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

}