package Discrete_Math.Combinatorics.NextObj;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * Created by Blackbird on 16.04.2016.
 * Project : DM.Discrete_Math.Combinatorics.NextObj.E6
 * Start time : 21:26
 */

public class E6 {
    String fileName = "nextbrackets";
    BufferedReader br;
    StringTokenizer in;
    PrintWriter out;

    public static void main(String[] args) throws IOException {
        Locale.setDefault(Locale.US);
        new E6().run();
    }

    private String nextBrackets(String s) {
        int openCounter = 0;
        int closeCounter = 0;
        int len = s.length();
        for (int i = len - 1; i >= 0; i--) {
            if (s.charAt(i) == '(') {
                openCounter++;
                if (closeCounter > openCounter) {
                    break;
                }
            } else {
                closeCounter++;
            }
        }

        StringBuilder newS = new StringBuilder("");
        for (int i = 0; i < len - openCounter - closeCounter; i++) {
            newS.append(s.charAt(i));
        }
        if (newS.toString().equals("")) {
            return "-";
        }
        newS.append(')');
        for (int i = 0; i < openCounter; i++) {
            newS.append('(');
        }
        for (int i = 1; i < closeCounter; i++) {
            newS.append(')');
        }
        s = newS.toString();
        return s;
    }

    public void solve() throws IOException {
        String s = next();
        out.println(nextBrackets(s));
    }

    public void run() {
        try {
            br = new BufferedReader(new FileReader(fileName + ".in"));
            out = new PrintWriter(fileName + ".out");

            solve();

            out.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public String nextToken() throws IOException {
        while (in == null || !in.hasMoreTokens()) {
            in = new StringTokenizer(br.readLine());
        }
        return in.nextToken();
    }

    public String next() throws IOException {
        return nextToken();
    }

    public int nextInt() throws IOException {
        return Integer.parseInt(nextToken());
    }

    public double nextDouble() throws IOException {
        return Double.parseDouble(nextToken());
    }

    public long nextLong() throws IOException {
        return Long.parseLong(nextToken());
    }

}