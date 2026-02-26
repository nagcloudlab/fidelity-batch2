package com.example.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * author: chatgpt
 * date: 2024-06-01
 * description: A simple implementation of a singly linked list.
 */

/*
 * 
 * Node
 * - value: the data stored in the node
 * - next: reference to the next node in the list
 * 
 * Head: reference to the first node in the list
 * Tail: reference to the last node in the list (optional)
 * 
 */

/*
 * 
 * data-structure + iterator => Iterable
 * iterator: an object that allows you to traverse a collection, typically
 * providing methods like hasNext() and next()
 * 
 */

public class LinkedList<E> implements Iterable<E> {

    private Node head;
    private Node tail;
    private int size;

    public void add(E value) {
        Node newNode = new Node(value);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.setNext(newNode);
            tail = newNode;
        }
        size++;
    }

    public void add(int index, E value) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        Node newNode = new Node(value);
        if (index == 0) {
            newNode.setNext(head);
            head = newNode;
            if (size == 0) {
                tail = newNode;
            }
        } else {
            Node current = head;
            for (int i = 0; i < index - 1; i++) {
                current = current.getNext();
            }
            newNode.setNext(current.getNext());
            current.setNext(newNode);
            if (newNode.getNext() == null) {
                tail = newNode;
            }
        }
        size++;
    }

    public int size() {
        return size;
    }

    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }
        return current.getValue();
    }

    public Iterator<E> iterator() {
        return new LinkedListIterator();
    }

    private class Node {
        private E value;
        private Node next;

        public Node(E value) {
            this.value = value;
            this.next = null;
        }

        public Node(E value, Node next) {
            this.value = value;
            this.next = next;
        }

        public E getValue() {
            return value;
        }

        public void setValue(E value) {
            this.value = value;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }

    private class LinkedListIterator implements Iterator<E> {
        private Node current;

        public LinkedListIterator() {
            this.current = head;
        }

        public boolean hasNext() {
            return current != null;
        }

        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            E value = current.getValue();
            current = current.getNext();
            return value;
        }
    }

}
