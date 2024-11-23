package com.mycompany.finalds;

public class LinkedList<T> {

    // Node class
    private class Node<T> {
        private T data;
        private Node<T> next;

        public Node() {
            this(null);
        }

        public Node(T data) {
            this.data = data;
            this.next = null;
        }

        // Getters and Setters
        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }

        public Node<T> getNext() {
            return next;
        }

        public void setNext(Node<T> next) {
            this.next = next;
        }
    }

    // LinkedList attributes
    private Node<T> head;
    private Node<T> current;
    int size;

    // Constructor
    public LinkedList() {
        head = null;
        current = null;
        size = 0;
    }

    // Check if the list is empty
    public boolean empty() {
        return head == null;
    }

    // Get the size of the list
    public int size() {
        return size;
    }

    // Check if the current node is the last node
    public boolean last() {
        return current != null && current.next == null;
    }

    // Always returns false since linked lists are dynamic
    public boolean full() {
        return false;
    }

    // Set the current node to the first node
    public void findFirst() {
        current = head;
    }

    // Move the current node to the next node
    public void findNext() {
        if (current != null) {
            current = current.next;
        }
    }

    // Retrieve the data of the current node
    public T retrieve() {
        if (current == null) throw new IllegalStateException("No current element.");
        return current.data;
    }

    // Update the data of the current node
    public void update(T val) {
        if (current == null) throw new IllegalStateException("No current element.");
        current.data = val;
    }

    // Insert a new value into the list after the current node
    public void insert(T val) {
        Node<T> newNode = new Node<>(val);

        if (empty()) {
            head = current = newNode;
        } else {
            newNode.next = current.next;
            current.next = newNode;
            current = newNode;
        }
        size++;
    }

    // Remove the current node from the list
    public void remove() {
        if (empty() || current == null) throw new IllegalStateException("No element to remove.");

        if (current == head) {
            head = head.next;
        } else {
            Node<T> previous = head;
            while (previous.next != current) {
                previous = previous.next;
            }
            previous.next = current.next;
        }

        current = (current.next == null) ? head : current.next;
        size--;
    }
}
