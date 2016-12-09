package Paradigm.hw4;

/**
 * Created by Blackbird on 19.03.2016.
 * Project : LinkedQueue
 * Start time : 19:00
 */

public class LinkedQueue extends AbstractQueue {
    private Node tail;
    private Node head;

    protected void enqueueRealisation(Object element) {
        tail = new Node(element, null, tail);
        if (head == null)
            head = tail;
        else
            tail.next.prev = tail;


    }

    protected Object elementRealisation() {
        return head.value;
    }

    protected void dequeueRealisation() {
        if (head.prev != null) head.prev.next = null;
        head = head.prev;

    }

    protected void pushRealisation(Object element) {
        head = new Node(element, head, null);
        if (tail == null) {
            tail = head;
        } else
            head.prev.next = head;

    }

    protected Object peekRealisation() {
        return tail.value;
    }

    protected void removeRealisation() {
        tail = tail.next;
        if (tail != null) {
            tail.prev = null;
        }
    }

    private class Node {
        Object value;
        Node prev;
        Node next;

        Node(Object element, Node prev, Node next) {
            value = element;
            this.prev = prev;
            this.next = next;
        }
    }


}
