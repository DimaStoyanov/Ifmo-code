package Discrete_Math.Matroid;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.TreeSet;

import static java.util.Arrays.sort;

/**
 * Created by Dima Stoyanov.
 */

public class A {


    private BufferedReader br;
    private StringTokenizer in;
    private PrintWriter out;

    public static void main(String[] args) throws IOException {
        Locale.setDefault(Locale.US);
        new A().run();
    }

    private void solve() throws IOException {
        int n = nextInt();
        Task[] tasks = new Task[n];
        TreeSet<Integer> time = new TreeSet<>();

        for (int i = 0; i < n; i++) {
            tasks[i] = new Task(nextInt(), nextInt());
            time.add(i);
        }
        sort(tasks, (o1, o2) -> o2.cost - o1.cost);
        long result = 0;
        Integer index;
        for (int i = 0; i < n; i++) {
            index = time.floor(tasks[i].time - 1);
            if (index == null) {
                time.remove(time.last());
                result += tasks[i].cost;
            } else {
                time.remove(index);
            }
        }
        out.println(result);
    }

    public void run() {
        try {
            br = new BufferedReader(new FileReader("schedule.in"));
            out = new PrintWriter("schedule.out");
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

    public int nextInt() throws IOException {
        return Integer.parseInt(nextToken());
    }

    public double nextDouble() throws IOException {
        return Double.parseDouble(nextToken());
    }

    public long nextLong() throws IOException {
        return Long.parseLong(nextToken());
    }

    private class Task {

        private final int time;
        private final int cost;

        Task(final int time, final int cost) {
            this.time = time;
            this.cost = cost;
        }

    }
}