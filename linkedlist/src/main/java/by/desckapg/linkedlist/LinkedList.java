package by.desckapg.linkedlist;

import java.util.NoSuchElementException;

/**
 * Generic doubly linked list implementation.
 * Provides operations to add, retrieve, and remove elements by index
 * and at the ends of the list. Not thread-safe.
 * <p>
 * Indexing:
 * - Valid indices are in the range [0, size - 1].
 * - add(index, value) inserts a new element before the element at the given index.
 * To insert at the beginning or end, use addFirst(value) or addLast(value).
 * <p>
 * Complexity (amortized):
 * - addFirst/addLast/removeFirst/removeLast: O(1)
 * - get/add/remove by index: O(n)
 *
 * @param <T> the type of elements stored in this list
 */
public class LinkedList<T> {

    private Node<T> head;
    private Node<T> tail;
    private int size = 0;

    /**
     * Returns the number of elements in this list.
     *
     * @return the list size
     */
    public int size() {
        return size;
    }

    /**
     * Returns the value of the first element.
     *
     * @return the first element's value
     * @throws NoSuchElementException if the list is empty
     */
    public T getFirst() {
        if (head == null) {
            throw new NoSuchElementException();
        }
        return head.value;
    }

    /**
     * Returns the value of the last element.
     *
     * @return the last element's value
     * @throws NoSuchElementException if the list is empty
     */
    public T getLast() {
        if (tail == null) {
            throw new NoSuchElementException();
        }
        return tail.value;
    }

    /**
     * Returns the value at the specified position in this list.
     *
     * @param index index of the element to return (0..size-1)
     * @return the value at the specified position
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public T get(int index) {
        if (!checkElementIndex(index)) {
            throw new IndexOutOfBoundsException();
        }
        var node = head;
        for (; index > 0 && node != null; index--) {
            node = node.next;
        }
        return node.value;
    }

    /**
     * Inserts the specified element before the element at the specified position.
     * For index == 0, this is equivalent to addFirst(value).
     * Note: valid indices are [0, size-1]; to append use addLast(value).
     *
     * @param index index before which the specified element is to be inserted (0..size-1)
     * @param value element to be inserted
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public void add(int index, T value) {
        if (!checkElementIndex(index)) {
            throw new IndexOutOfBoundsException();
        } else {
            if (index  == 0) {
                addFirst(value);
            } else {
                Node<T> slow = null;
                Node<T> fast = head;
                for (; index > 0 && fast != null; index--) {
                    slow = fast;
                    fast = fast.next;
                }
                var node = new Node<>(value, slow, fast);
                slow.next = node;
                fast.prev = node;
                size++;
            }
        }
    }

    /**
     * Inserts the specified element at the beginning of this list.
     *
     * @param value element to be inserted
     */
    public void addFirst(T value) {
        var node = new Node<>(value, null, head);
        if (head != null) {
            head.prev = node;
            head = node;
        } else {
            head = tail = node;
        }
        size++;
    }

    /**
     * Appends the specified element to the end of this list.
     *
     * @param value element to be appended
     */
    public void addLast(T value) {
        var node = new Node<>(value, tail, null);
        if (tail != null) {
            tail.next = node;
            tail = node;
        } else {
            tail = head = node;
        }
        size++;
    }

    /**
     * Removes and returns the first element from this list.
     *
     * @return the value of the removed first element
     * @throws NoSuchElementException if the list is empty
     */
    public T removeFirst() {
        if (head == null) {
            throw new NoSuchElementException();
        }
        var value = head.value;
        if (head.next == null) {
            head = tail = null;
        } else {
            head = head.next;
            head.prev = null;
        }
        size--;
        return value;
    }

    /**
     * Removes and returns the last element from this list.
     *
     * @return the value of the removed last element
     * @throws NoSuchElementException if the list is empty
     */
    public T removeLast() {
        if (tail == null) {
            throw new NoSuchElementException();
        }
        var value = tail.value;
        if (tail.prev == null) {
            head = tail = null;
        } else {
            tail = tail.prev;
            tail.next = null;
        }
        size--;
        return value;
    }

    /**
     * Removes and returns the element at the specified position in this list.
     * For index == 0 this is equivalent to removeFirst(); for index == size - 1 — removeLast().
     *
     * @param index the index of the element to be removed (0..size-1)
     * @return the value of the removed element
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public T remove(int index) {
        if (!checkElementIndex(index)) {
            throw new IndexOutOfBoundsException();
        } else if (index == 0) {
            return removeFirst();
        } else if (index == size - 1) {
            return removeLast();
        } else {
            Node<T> slow = null;
            Node<T> fast = head;
            for (; index > 0 && fast != null; index--) {
                slow = fast;
                fast = fast.next;
            }
            slow.next = fast.next;
            if (fast.next != null) {
                fast.next.prev = slow;
            }
            size--;
            return fast.value;
        }
    }

    private boolean checkElementIndex(int index) {
        return index >= 0 && index < size;
    }

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        private Node(T value, Node<T> prev, Node<T> next) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }

    }

}
