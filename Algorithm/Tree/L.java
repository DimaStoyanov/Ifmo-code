package Algorithm.Tree;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Дима1 on 05.01.2016.
 */
public class L {


    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("cutting.in"));
        PrintWriter out = new PrintWriter(new File("cutting.out"));
        String line = br.readLine();
        String[] t = line.split(" ");
        int n = Integer.parseInt(t[0]);
        int m = Integer.parseInt(t[1]);
        int k = Integer.parseInt(t[2]);
        for (int i = 0; i < m; i++) {
            br.readLine();
        }
        String comands[] = new String[k + 1];
        for (int i = 1; i <= k; i++) {
            comands[i] = br.readLine();
        }
        DSU snm = new DSU(n);
        ArrayList<Boolean> ans = new ArrayList<>();
        for (int i = k; i > 0; i--) {
            String cmd[] = comands[i].split(" ");
            if (cmd[0].equals("ask")) {
                ans.add(snm.find(Integer.parseInt(cmd[1])) == snm.find(Integer.parseInt(cmd[2])));
            } else {
                snm.unite(Integer.parseInt(cmd[1]), Integer.parseInt(cmd[2]));
            }
        }
        final int size = ans.size() - 1;
        for (int i = size; i >= 0; i--) {
            out.println(ans.get(i) ? "YES" : "NO");
        }
        out.close();
        br.close();
    }

    static class DSU {
        private static final Random rand = new Random();
        int p[];

        DSU(int a) {
            p = new int[a + 1];
            for (int i = 1; i <= a; i++) {
                makeSet(i);
            }
        }


        void makeSet(int num) {
            p[num] = num;
        }

        int find(int x) {
            if (p[x] == x)
                return x;
            return p[x] = find(p[x]);
        }

        void unite(int x, int y) {
            x = find(x);
            y = find(y);
            if (rand.nextBoolean()) {
                int temp = x;
                x = y;
                y = temp;
            }
            p[x] = y;

        }

    }


}
