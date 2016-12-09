package Paradigm.hw3.test;

import Paradigm.hw3.ArrayQueueADT;

/**
 * Created by Blackbird on 19.03.2016.
 * Project : ArrayQueue
 * Start time : 21:05
 */

public class ArrayQueueADTTest {
    public static void main(String[] args) {
        ArrayQueueADT queue = new ArrayQueueADT();
        fill(queue);
        dump(queue);
        fill(queue);
        clear(queue);
        fill(queue);
        getArray(queue);
    }

    public static void fill(ArrayQueueADT queue) {
        for (int i = 0; i < 10; i++) {
            ArrayQueueADT.enqueue(queue, i);
        }
    }

    public static void getArray(ArrayQueueADT queue) {
        Object[] arr = ArrayQueueADT.toArray(queue);
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
    }

    public static void dump(ArrayQueueADT queue) {
        while (!ArrayQueueADT.isEmpty(queue)) {
            System.out.println(
                    ArrayQueueADT.size(queue) + " " + ArrayQueueADT.element(queue) + " " + ArrayQueueADT.dequeue(queue));
        }
    }

    public static void clear(ArrayQueueADT queue) {
        ArrayQueueADT.clear(queue);
        System.out.println(ArrayQueueADT.isEmpty(queue));
    }

}
