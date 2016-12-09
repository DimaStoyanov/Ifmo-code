package Paradigm.hw2;

/**
 * Created by Blackbird on 18.02.2016.
 * Project : BinarySearch
 * Start time : 17:19
 */

public class BinarySearch {
    public static void main(String[] args) {
        int x = Integer.parseInt(args[0]);
        final int size = args.length - 1;
        int[] a = new int[size];
        for (int i = 0; i < size; i++)
            a[i] = Integer.parseInt(args[i + 1]);
        //System.out.println(recursiveBinarySearch(a, x, -1, size));
        System.out.println(iterativeBinarySearch(a, x, -1, size));

    }

    static int recursiveBinarySearch(int a[], int key, int l, int r) {
        if (l < r - 1) {
            int m = (l + r) >> 1;
            if (a[m] > key) {
                return recursiveBinarySearch(a, key, m, r);
            } else {
                return recursiveBinarySearch(a, key, l, m);
            }
        } else
            return r;
    }

    static int iterativeBinarySearch(int a[], int key, int l, int r) {
        while (l < r - 1) {
            int m = (l + r) >> 1;
            if (a[m] <= key)
                r = m;
            else
                l = m;
        }
        return r;
    }
}
