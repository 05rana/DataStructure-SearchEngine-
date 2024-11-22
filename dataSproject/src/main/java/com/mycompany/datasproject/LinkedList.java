/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.datasproject;

/**
 *
 * @author 1234
 */
import java.util.*;



import java.util.*;

// Generic Node class to represent a node in a linked list
 class Node<T> {


    public T data;
    
    public Node<T> next;

    // Default constructor initializes data and next to null
    public Node() {
        data = null;
        next = null;
    }

    // Parameterized constructor initializes the node with a value
    public Node(T val) {
        data = val;
        next = null;
    }

    // Getter method to retrieve the data stored in the node
    public T getData() {
        return data;
    }

    // Setter method to update the data stored in the node
    public void setData(T data) {
        this.data = data;
    }

    // Getter method to retrieve the reference to the next node
    public Node<T> getNext() {
        return next;
    }

    // Setter method to update the reference to the next node
    public void setNext(Node<T> next) {
        this.next = next;
    }
}



// Generic LinkedList class
public class LinkedList<T> {
    // Head node to mark the start of the list
    private Node<T> head;

    // Current node to keep track of the current position in the list
    private Node<T> current;

    // Size to store the number of elements in the linked list
    int size;

    // Constructor to initialize an empty linked list
    public LinkedList() {
        head = current = null;
        size = 0;
    }

    // Check if the list is empty
    public boolean empty() {
        return head == null;
    }

    // Get the size of the linked list
    public int size() {
        return size;
    }

    // Check if the current node is the last node in the list
    public boolean last() {
        return current != null && current.next == null;
    }

    // Check if the list is full (always false for a dynamic linked list)
    public boolean full() {
        return false;
    }

    // Move the current pointer to the next node
    public void findNext() {
        if (current != null) {
            current = current.next;
        }
    }

    // Move the current pointer to the first node (head)
    public void findFirst() {
        current = head;
    }

    // Retrieve the data of the current node
    public T retrieve() {
        if (current != null) {
            return current.data;
        }
        return null; // Return null if the current node is not valid
    }

    // Update the data of the current node
    public void update(T value) {
        if (current != null) {
            current.data = value;
        }
    }

    // Insert a new node after the current node
    public void insert(T val) {
        Node<T> tmp;
        if (empty()) {
            // If the list is empty, the new node becomes the head and current node
            head = current = new Node<T>(val);
        } else {
            // Insert the new node after the current node
            tmp = current.next;
            current.next = new Node<T>(val);
            current = current.next;
            current.next = tmp;
        }
        size++; // Increment the size of the list
    }

    // Remove the current node from the list
    public void remove() {
        if (current == head) {
            // If the current node is the head, update the head to the next node
            head = head.next;
        } else {
            // Traverse the list to find the node before the current node
            Node<T> temp = head;
            while (temp.next != current) {
                temp = temp.next;
            }
            // Update the next pointer of the previous node to skip the current node
            temp.next = current.next;
        }
        // Update the current pointer
        if (current.next == null) {
            current = head; // Reset to head if at the end of the list
        } else {
            current = current.next;
        }
        size--; // Decrement the size of the list
    }

    // Display all elements in the linked list
    public void display() {
        Node<T> temp = head;
        if (temp == null) {
            System.out.println("The list is empty.");
            return;
        }
        System.out.print("LinkedList: ");
        while (temp != null) {
            System.out.print(temp.data + " "); // Print data of each node
            temp = temp.next; // Move to the next node
        }
        System.out.println("null"); // Indicate the end of the list
    }
}

