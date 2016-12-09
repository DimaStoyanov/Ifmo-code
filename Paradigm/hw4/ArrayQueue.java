package Paradigm.hw4;

/**
 * Created by Blackbird on 19.03.2016.
 * Project : ArrayQueue
 * Start time : 10:21
 */

public class ArrayQueue extends AbstractQueue {
    private final int START_CAPACITY = 100;
    private Object array[];
    private int head;
    private int tail;

    public ArrayQueue() {
        array = new Object[START_CAPACITY];
        head = tail = 0;
    }

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

    protected void enqueueRealisation(Object element) {
        ensureCapacity(size + 1);
        array[tail] = element;
        tail = (tail + 1) % array.length;
    }

    public Object elementRealisation() {
        return array[head];
    }

    protected void dequeueRealisation() {
        array[head] = null;
        head = (head + 1) % array.length;
    }

    protected void pushRealisation(Object element) {
        ensureCapacity(size + 1);
        head = (this.head - 1 >= 0) ? array.length - 1 : head - 1;
        array[head] = element;
    }

    protected Object peekRealisation() {
        return array[(tail == 0) ? array.length - 1 : tail - 1];
    }

    protected void removeRealisation() {
        tail = tail == 0 ? array.length - 1 : --tail;
        array[tail] = null;
    }
}
