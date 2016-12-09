package Paradigm.hw3;

/**
 * Created by Blackbird on 19.03.2016.
 * Project : ArrayQueueModule
 * Start time : 20:56
 */

public class ArrayQueueModule {
    private static final int START_CAPACITY = 100;
    private static Object array[];
    private static int size;
    private static int head;
    private static int tail;

    public ArrayQueueModule() {
        array = new Object[START_CAPACITY];
        size = head = tail = 0;
    }

    private static void ensureCapacity(int capacity) {
        if (capacity >= array.length) {
            Object[] temp = new Object[array.length << 1];
            int count = tail < head ? array.length - head : size;
            System.arraycopy(array, head, temp, 0, count);
            if (tail < head)
                System.arraycopy(array, 0, temp, count, tail);
            array = temp;
            head = 0;
            tail = size;
        }
    }

    public static void enqueue(Object element) {
        assert element != null;
        ensureCapacity(size + 1);
        array[tail] = element;
        tail = (tail + 1) % array.length;
        size++;
    }

    public static Object element() {
        assert size > 0;
        return array[head];
    }

    public static Object dequeue() {
        assert size > 0;
        Object result = element();
        array[head] = null;
        size--;
        head = (head + 1) % array.length;
        return result;
    }

    public static void push(Object element) {
        assert element != null;
        ensureCapacity(size + 1);
        head = (head == 0) ? array.length - 1 : head - 1;
        array[head] = element;
        size++;
    }

    public static Object peek() {
        assert size > 0;
        return array[(tail == 0) ? array.length - 1 : tail - 1];
    }

    public static Object remove() {
        assert size > 0;
        Object result = peek();
        tail = tail == 0 ? array.length - 1 : --tail;
        array[tail] = null;
        size--;
        return result;
    }

    public static int size() {
        return size;
    }

    public static boolean isEmpty() {
        return size == 0;
    }

    public static void clear() {
        size = head = tail = 0;
        array = new Object[START_CAPACITY];
    }

    public static Object[] toArray() {
        Object[] temp = new Object[size];
        int count = tail < head ? array.length - head : size;
        System.arraycopy(array, head, temp, 0, count);
        if (tail < head)
            System.arraycopy(array, 0, temp, count, tail);
        return temp;
    }
}
