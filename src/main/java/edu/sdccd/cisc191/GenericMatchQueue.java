package edu.sdccd.cisc191;

import java.util.LinkedList;

public class GenericMatchQueue<T> {

    private final LinkedList<T> items = new LinkedList<>();

    public void enqueue(T item) {
        // TODO: add the item to the back of the queue
        items.addLast(item);
    }

    public T dequeue() {
        // TODO: remove and return the front item
        if (items.isEmpty()){ // throw IllegalStateException if the queue is empty
            throw new IllegalStateException("List can't be empty");
        } else {
            T item = items.getFirst();
            items.removeFirst();
            return item;
        }
    }

    public T peek() {
        if (items.isEmpty()){ // throw IllegalStateException if the queue is empty
            throw new IllegalStateException("List can't be empty");
        } else {
            return items.getFirst();
        }
    }

    public boolean isEmpty() {
        // TODO: return true when the queue has no items

        return items.isEmpty();
    }

    public int size() {
        return items.size();
    }
}
