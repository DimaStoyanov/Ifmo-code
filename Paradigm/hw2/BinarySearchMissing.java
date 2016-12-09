package Paradigm.hw2;

/**
 * Created by Blackbird on 18.02.2016.
 * Project : BinarySearchMissing
 * Start time : 13:45
 */

public class BinarySearchMissing {
    public static void main(String[] args) {
        int x = Integer.parseInt(args[0]);
        final int size = args.length - 1;
        int[] a = new int[size];
        for (int i = 0; i < size; i++)
            a[i] = Integer.parseInt(args[i + 1]);
        int result;
        System.out.println((result = recursiveBinarySearch(a, x, 0, size)) == iterativeBinarySearch(a, x, 0, size) ? result : "Error");

    }

    // Pre : i = 0..(size - 1) && a[i] >= a[i-1]
    // Post : Result = i && a[i-1] > x >= a[i]
    // Post : if (Result = 0) x = max(a[0..size])
    static int recursiveBinarySearch(int a[], int key, int l, int r) {
        // INV: a[-1]...a[l-1] > key >= a[r]....a[size]
        // if r = l, r = min(-1...size) && a[r] >= key
        if (l < r) {
            // // INV && l < r
            int m = (l + r) >>> 1;
            //  l <= m < r
            if (a[m] > key) {
                // INV && key < a[m]
                // a[-1]...a[m] > a[m] >= a[r]...a[size]
                return recursiveBinarySearch(a, key, m + 1, r);
            } else {
                // INV && key >= a[m]
                // a[-1]...a[l-1] >= a[m] > a[r]...a[size]
                return recursiveBinarySearch(a, key, l, m);
            }
        } else
            // Post: INV  &&  l >= r
            //  a[l-1] > x >= a[r]  &&  l-1 >= r-1
            //  a[r-1] > x >= a[r]
            //  Result = r
            //  if(r < size  &&  a[r] == x)  Result = r
            //  if(r >= size  ||  a[r] != x) Result = -r - 1
            return (r < a.length) && (key == a[r]) ? r : -r - 1;
    }

    // Pre : i = 0...size && a[i] >= a[i-1]
    // Post : Result = i && a[i-1] > x >= a[i]
    static int iterativeBinarySearch(int a[], int key, int l, int r) {
        // INV: a[-1]...a[l-1] > key >= a[r]....a[size]
        // if r = l, r = min(-1...size) && a[r] >= key
        while (l < r) {
            // INV && l < r
            int m = (l + r) >>> 1;
            //  l <= m < r
            if (a[m] <= key)
                // INV && key >= a[m]
                r = m;
                // a[-1]...a[l-1] >= a[m] > a[r]...a[size]
            else
                // INV && key < a[m]
                l = m + 1;
            // a[-1]...a[m] > a[m] >= a[r]...a[size]
        }
        // POST: INV  &&  l >= r
        //  a[l-1] > x >= a[r]  &&  l-1 >= r-1
        //  a[r-1] > x >= a[r]
        //  Result = r
        //  if(r < size  &&  a[r] == x)  Result = r
        //  if(r >= size  ||  a[r] != x) Result = -r - 1
        return (r < a.length) && (a[r] == key) ? r : (-r - 1);
    }
}
