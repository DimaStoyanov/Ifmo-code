package Paradigm.hw3.test;

import Paradigm.hw3.ArrayQueue;

/**
 * Created by Blackbird on 19.03.2016.
 * Project : ArrayQueue
 * Start time : 21:10
 */

public class ArrayQueueTest {
    public static void main(String[] args) {
        ArrayQueue queue = new ArrayQueue();
        fill(queue);
        dump(queue);
        fill(queue);
        clear(queue);
        fill(queue);
        getArray(queue);
    }

    public static void fill(ArrayQueue queue) {
        for (int i = 0; i < 10; i++) {
            queue.enqueue(i);
        }
    }

    public static void dump(ArrayQueue queue) {
        while (!queue.isEmpty()) {
            System.out.println(queue.size() + " " + queue.element() + " " + queue.dequeue());
        }
    }

    public static void getArray(ArrayQueue queue) {
        Object[] arr = queue.toArray();
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
    }

    public static void clear(ArrayQueue queue) {
        queue.clear();
        System.out.println("Queue is empty? " + queue.isEmpty());
    }

}

