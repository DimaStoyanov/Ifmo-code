package Paradigm.hw3;

/**
 * Created by Blackbird on 19.03.2016.
 * Project : ArrayQueue
 * Start time : 20:21
 */

public class ArrayQueue {
    private final int START_CAPACITY = 10;
    private Object array[];
    private int size;
    private int head;
    private int tail;

    public ArrayQueue() {
        array = new Object[START_CAPACITY];
        size = head = tail = 0;
    }


    // Pre: capacity' > 0
    // Post: if required capacity of array bigger then current
    // Post: capacity' = capacity * 2
    // Inv: if (capacity' = capacity * 2) head < tail
    private void ensureCapacity(int capacity) {
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

    // Pre: value != null
    // Pre: value is immutable
    // Post: queue[size - 1] = element
    // Post: size' = size + 1
    public void enqueue(Object element) {
        assert element != null;
        ensureCapacity(size + 1);
        array[tail] = element;
        tail = (tail + 1) % array.length;
        size++;
    }

    // Pre: size > 0
    // Post: Result = queue[0]
    public Object element() {
        assert size > 0;
        return array[head];
    }

    // Pre: size > 0
    // Post: Result = queue[0]
    // Post: size' = size - 1
    public Object dequeue() {
        assert size > 0;
        Object result = element();
        array[head] = null;
        size--;
        head = (head + 1) % array.length;
        return result;
    }

    // Pre: element != null
    // Pre: element is immutable
    // Post: queue[0] = element
    // Post: size' = size + 1
    public void push(Object element) {
        assert element != null;
        ensureCapacity(size + 1);
        head = (head == 0) ? array.length - 1 : head - 1;
        array[head] = element;
        size++;
    }

    // Pre: size > 0
    // Post: Result = queue[size - 1]
    public Object peek() {
        assert size > 0;
        return array[(tail == 0) ? array.length - 1 : tail - 1];
    }

    // Pre: size > 0
    // Post: Result = queue[size - 1]
    // Post: size' = size - 1
    public Object remove() {
        assert size > 0;
        Object result = peek();
        tail = tail == 0 ? array.length - 1 : --tail;
        array[tail] = null;
        size--;
        return result;
    }

    // Post: Result = size
    public int size() {
        return size;
    }

    // Post: Result = size > 0?
    public boolean isEmpty() {
        return size == 0;
    }

    // INV: size' = 0;
    // Post: Clear the queue
    public void clear() {
        size = head = tail = 0;
        array = new Object[START_CAPACITY];
    }

    // Post: Result = queue[0...size - 1]
    public Object[] toArray() {
        Object[] temp = new Object[size];
        int count = tail < head ? array.length - head : size;
        System.arraycopy(array, head, temp, 0, count);
        if (tail < head)
            System.arraycopy(array, 0, temp, count, tail);
        return temp;
    }
}
