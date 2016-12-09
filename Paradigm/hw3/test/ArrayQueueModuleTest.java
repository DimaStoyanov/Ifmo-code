package Paradigm.hw3.test;

import Paradigm.hw3.ArrayQueueModule;

/**
 * Created by Blackbird on 19.03.2016.
 * Project : ArrayQueue
 * Start time : 21:07
 */

public class ArrayQueueModuleTest {
    public static void main(String[] args) {
        fill();
        dump();
        fill();
        clear();
        fill();
        getArray();
    }

    public static void fill() {
        for (int i = 0; i < 10; i++) {
            ArrayQueueModule.enqueue(i);
        }
    }

    public static void getArray() {
        Object[] arr = ArrayQueueModule.toArray();
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
    }

    public static void dump() {
        while (!(ArrayQueueModule.isEmpty())) {
            System.out.println(ArrayQueueModule.size() + " " + ArrayQueueModule.element() + " " + ArrayQueueModule.dequeue());
        }
    }

    public static void clear() {
        ArrayQueueModule.clear();
        System.out.println(ArrayQueueModule.isEmpty());
    }
}

