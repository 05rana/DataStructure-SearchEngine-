package com.mycompany.datasproject;
public class BST<T> {
    private BSTNode<T> root;

    public BST() {
        root = null;
    }

    public boolean empty() {
        return root == null;
    }

    public boolean insert(String key, T value) {
        if (root == null) {
            root = new BSTNode<>(key, value);
            return true;
        } else {
            return insertRecursively(root, key, value);
        }
    }

    private boolean insertRecursively(BSTNode<T> node, String key, T value) {
        if (key.compareToIgnoreCase(node.key) == 0) {
            return false; // Duplicate key, not inserted
        } else if (key.compareToIgnoreCase(node.key) < 0) {
            if (node.left == null) {
                node.left = new BSTNode<>(key, value);
                return true;
            } else {
                return insertRecursively(node.left, key, value);
            }
        } else {
            if (node.right == null) {
                node.right = new BSTNode<>(key, value);
                return true;
            } else {
                return insertRecursively(node.right, key, value);
            }
        }
    }

    public boolean findKey(String key) {
        return findKeyRecursively(root, key);
    }

    private boolean findKeyRecursively(BSTNode<T> node, String key) {
        if (node == null) {
            return false;
        }
        if (key.compareToIgnoreCase(node.key) == 0) {
            return true;
        } else if (key.compareToIgnoreCase(node.key) < 0) {
            return findKeyRecursively(node.left, key);
        } else {
            return findKeyRecursively(node.right, key);
        }
    }

    public T retrieve(String key) {
        BSTNode<T> node = retrieveNode(root, key);
        return (node != null) ? node.data : null;
    }

    private BSTNode<T> retrieveNode(BSTNode<T> node, String key) {
        if (node == null) {
            return null;
        }
        if (key.compareToIgnoreCase(node.key) == 0) {
            return node;
        } else if (key.compareToIgnoreCase(node.key) < 0) {
            return retrieveNode(node.left, key);
        } else {
            return retrieveNode(node.right, key);
        }
    }

    public void inOrder() {
        if (empty()) {
            System.out.println("empty tree");
        } else {
            inOrder(root);
        }
    }

    private void inOrder(BSTNode<T> node) {
        if (node != null) {
            inOrder(node.left);
            ((Word) node.data).display(); // Displaying the word data
            inOrder(node.right);
        }
    }

    public void preOrder() {
        if (empty()) {
            System.out.println("empty tree");
        } else {
            preOrder(root);
        }
    }

    private void preOrder(BSTNode<T> node) {
        if (node != null) {
            ((Word) node.data).display(); // Displaying the word data
            preOrder(node.left);
            preOrder(node.right);
        }
    }

 private static class BSTNode<T> {
    String key;          // The key for this node
    T data;             // The data associated with the key
    BSTNode<T> left;    // Pointer to the left child
    BSTNode<T> right;   // Pointer to the right child

    // Constructor to initialize the node with a key and data
    public BSTNode(String key, T data) {
        this.key = key;
        this.data = data;
        this.left = null;  // Initially, left child is null
        this.right = null; // Initially, right child is null
    }
}
        }
    
