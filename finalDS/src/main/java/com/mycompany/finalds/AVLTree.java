package com.mycompany.finalds;

import java.util.NoSuchElementException;

public class AVLTree<K extends Comparable<K>, T> {

    /*==================================================================
        Inner class: AVLNode
    ==================================================================*/
    class AVLNode<K extends Comparable<K>, T> {
        public K key;
        public T data;
        public AVLNode<K, T> parent, left, right; // Node relations
        public int bf; // Balance factor

        public AVLNode() {
            this(null, null, null, null, null);
        }

        public AVLNode(K key, T data) {
            this(key, data, null, null, null);
        }

        public AVLNode(K key, T data, AVLNode<K, T> parent, AVLNode<K, T> left, AVLNode<K, T> right) {
            this.key = key;
            this.data = data;
            this.parent = parent;
            this.left = left;
            this.right = right;
            this.bf = 0;
        }

        public AVLNode<K, T> getLeft() {
            return left;
        }

        public AVLNode<K, T> getRight() {
            return right;
        }

        public T getData() {
            return data;
        }

        @Override
        public String toString() {
            return "AVL Node{" + "key=" + key + ", data=" + data + '}';
        }
    }

    /*==================================================================
        Fields
    ==================================================================*/
    private AVLNode<K, T> root;
    private AVLNode<K, T> curr;
    private int count;

    public AVLTree() {
        root = curr = null;
        count = 0;
    }

    /*==================================================================
        Basic Operations
    ==================================================================*/
    public boolean empty() {
        return root == null;
    }

    public int size() {
        return count;
    }

    public void clear() {
        root = curr = null;
        count = 0;
    }

    public T retrieve() {
        return (curr != null) ? curr.data : null;
    }

    public void update(T data) {
        if (curr != null) curr.data = data;
    }

    /*==================================================================
        Search Operations
    ==================================================================*/
    private T searchTreeHelper(AVLNode<K, T> node, K key) {
        if (node == null) return null;

        int cmp = key.compareTo(node.key);
        if (cmp == 0) {
            curr = node;
            return node.data;
        }
        return (cmp < 0) ? searchTreeHelper(node.left, key) : searchTreeHelper(node.right, key);
    }

    public boolean find(K key) {
        return searchTreeHelper(root, key) != null;
    }

    /*==================================================================
        Insert Operations
    ==================================================================*/
    public boolean insert(K key, T data) {
        AVLNode<K, T> node = new AVLNode<>(key, data);
        AVLNode<K, T> parent = null;
        AVLNode<K, T> current = root;

        while (current != null) {
            parent = current;
            int cmp = key.compareTo(current.key);
            if (cmp == 0) return false;
            current = (cmp < 0) ? current.left : current.right;
        }

        node.parent = parent;
        if (parent == null) {
            root = node;
        } else if (key.compareTo(parent.key) < 0) {
            parent.left = node;
        } else {
            parent.right = node;
        }

        count++;
        updateBalance(node);
        return true;
    }

    /*==================================================================
        Delete Operations
    ==================================================================*/
    public boolean remove(K key) {
        AVLNode<K, T> node = root, parent = null;

        while (node != null) {
            int cmp = key.compareTo(node.key);
            if (cmp == 0) break;

            parent = node;
            node = (cmp < 0) ? node.left : node.right;
        }

        if (node == null) return false; // Key not found

        if (node.left != null && node.right != null) { // Two children
            AVLNode<K, T> successor = node.right;
            while (successor.left != null) successor = successor.left;

            node.key = successor.key;
            node.data = successor.data;
            node = successor; // Proceed to remove successor
        }

        AVLNode<K, T> child = (node.left != null) ? node.left : node.right;
        if (child != null) child.parent = parent;

        if (parent == null) {
            root = child;
        } else if (node == parent.left) {
            parent.left = child;
        } else {
            parent.right = child;
        }

        count--;
        updateBalance(parent);
        return true;
    }

    /*==================================================================
        Balance Operations
    ==================================================================*/
    private void updateBalance(AVLNode<K, T> node) {
        while (node != null) {
            int bf = height(node.left) - height(node.right);
            node.bf = bf;

            if (Math.abs(bf) > 1) {
                rebalance(node);
                break;
            }
            node = node.parent;
        }
    }

    private void rebalance(AVLNode<K, T> node) {
        if (node.bf > 0) {
            if (node.left.bf < 0) leftRotate(node.left);
            rightRotate(node);
        } else {
            if (node.right.bf > 0) rightRotate(node.right);
            leftRotate(node);
        }
    }

    private void leftRotate(AVLNode<K, T> node) {
        AVLNode<K, T> pivot = node.right;
        node.right = pivot.left;

        if (pivot.left != null) pivot.left.parent = node;
        pivot.parent = node.parent;

        if (node.parent == null) {
            root = pivot;
        } else if (node == node.parent.left) {
            node.parent.left = pivot;
        } else {
            node.parent.right = pivot;
        }

        pivot.left = node;
        node.parent = pivot;

        node.bf = node.bf - 1 - Math.max(0, pivot.bf);
        pivot.bf = pivot.bf - 1 + Math.min(0, node.bf);
    }

    private void rightRotate(AVLNode<K, T> node) {
        AVLNode<K, T> pivot = node.left;
        node.left = pivot.right;

        if (pivot.right != null) pivot.right.parent = node;
        pivot.parent = node.parent;

        if (node.parent == null) {
            root = pivot;
        } else if (node == node.parent.right) {
            node.parent.right = pivot;
        } else {
            node.parent.left = pivot;
        }

        pivot.right = node;
        node.parent = pivot;

        node.bf = node.bf + 1 - Math.min(0, pivot.bf);
        pivot.bf = pivot.bf + 1 + Math.max(0, node.bf);
    }

    private int height(AVLNode<K, T> node) {
        if (node == null) return 0;
        return 1 + Math.max(height(node.left), height(node.right));
    }

    /*==================================================================
        Traversal Operations
    ==================================================================*/
    public void Traverse() {
        traverseTree(root);
    }

    private void traverseTree(AVLNode<K, T> node) {
        if (node != null) {
            traverseTree(node.left);
            System.out.println(node.data);
            traverseTree(node.right);
        }
    }

    public void PrintData() {
        printTree(root);
    }

    private void printTree(AVLNode<K, T> node) {
        if (node == null) return;

        printTree(node.left);
        System.out.print(node.key);

        if (node.data instanceof Term) {
            boolean[] docs = ((Term) node.data).getDocs();
            System.out.print(" docs: ");
            for (int i = 0; i < docs.length; i++) {
                if (docs[i]) System.out.print(i + " ");
            }
            System.out.println();
        }

        printTree(node.right);
    }
}
