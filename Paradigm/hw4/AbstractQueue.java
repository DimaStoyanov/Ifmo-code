package Paradigm.hw4;

/**
 * Created by Blackbird on 19.03.2016.
 * Project : AbstractQueue
 * Start time : 10:02
 */

public abstract class AbstractQueue implements Queue {
    protected int size = 0;

    protected abstract void enqueueRealisation(Object element);

    protected abstract Object elementRealisation();

    protected abstract void dequeueRealisation();

    protected abstract void pushRealisation(Object element);

    protected abstract Object peekRealisation();

    protected abstract void removeRealisation();

    public void enqueue(Object element) {
        assert element != null;
        enqueueRealisation(element);
        size++;
    }

    public Object element() {
        assert size > 0;
        return elementRealisation();
    }

    public Object dequeue() {
        assert size > 0;
        Object result = elementRealisation();
        dequeueRealisation();
        --size;
        return result;
    }

    public void push(Object element) {
        System.out.println("Push");
        assert element != null;
        pushRealisation(element);
        size++;
    }

    public Object peek() {
        assert size > 0;
        return peekRealisation();
    }

    public Object remove() {
        assert size > 0;
        Object result = peek();
        removeRealisation();
        --size;
        return result;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        while (!isEmpty()) {
            dequeue();
        }
    }

    public Object[] toArray() {
        Object[] newArray = new Object[size];
        for (int i = 0; i < size; i++) {
            newArray[i] = dequeue();
            enqueue(newArray[i]);
        }
        return newArray;
    }
}
