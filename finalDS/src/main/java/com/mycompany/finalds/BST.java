package com.mycompany.finalds;

public class BST<K extends Comparable<K>, T> {

    /*==================================================================
        Inner class: BSTNode
    ==================================================================*/
    class BSTNode<K extends Comparable<K>, T> {
        public K key;
        public T data;
        public BSTNode<K, T> left, right;

        public BSTNode(K key, T data) {
            this(key, data, null, null);
        }

        public BSTNode(K key, T data, BSTNode<K, T> left, BSTNode<K, T> right) {
            this.key = key;
            this.data = data;
            this.left = left;
            this.right = right;
        }
    }

    private BSTNode<K, T> root;
    private BSTNode<K, T> curr;
    private int count;

    public BST() {
        root = curr = null;
        count = 0;
    }

    // Returns the number of elements in the tree
    public int size() {
        return count;
    }

    // Returns true if the tree is empty
    public boolean empty() {
        return root == null;
    }

    // Removes all elements from the tree
    public void clear() {
        root = curr = null;
        count = 0;
    }

    // Retrieves the data of the current node
    public T retrieve() {
        return (curr != null) ? curr.data : null;
    }

    // Updates the data of the current node
    public void update(T data) {
        if (curr != null) curr.data = data;
    }

    // Searches for an element with the specified key
    public boolean find(K key) {
        curr = root;
        while (curr != null) {
            int cmp = key.compareTo(curr.key);
            if (cmp == 0) {
                return true; // Found
            } else if (cmp < 0) {
                curr = curr.left; // Go left
            } else {
                curr = curr.right; // Go right
            }
        }
        return false; // Not found
    }

    // Inserts a new element into the tree
    public boolean insert(K key, T data) {
        if (empty()) {
            root = curr = new BSTNode<>(key, data);
            count++;
            return true;
        }

        BSTNode<K, T> parent = null;
        BSTNode<K, T> node = root;
        while (node != null) {
            int cmp = key.compareTo(node.key);
            if (cmp == 0) {
                return false; // Key already exists
            }
            parent = node;
            node = (cmp < 0) ? node.left : node.right;
        }

        BSTNode<K, T> newNode = new BSTNode<>(key, data);
        if (key.compareTo(parent.key) < 0) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
        }
        curr = newNode;
        count++;
        return true;
    }

    // Removes an element with the specified key
    public boolean remove(K key) {
        boolean[] removed = {false}; // Use an array to modify flag in recursion
        root = removeAux(key, root, removed);
        if (removed[0]) count--;
        if (curr != null && curr.key.equals(key)) curr = root;
        return removed[0];
    }

    private BSTNode<K, T> removeAux(K key, BSTNode<K, T> node, boolean[] removed) {
        if (node == null) return null; // Key not found

        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.left = removeAux(key, node.left, removed);
        } else if (cmp > 0) {
            node.right = removeAux(key, node.right, removed);
        } else {
            removed[0] = true;
            if (node.left != null && node.right != null) { // Two children
                BSTNode<K, T> successor = findMin(node.right);
                node.key = successor.key;
                node.data = successor.data;
                node.right = removeAux(successor.key, node.right, removed);
            } else { // One or no children
                node = (node.left != null) ? node.left : node.right;
            }
        }
        return node;
    }

    private BSTNode<K, T> findMin(BSTNode<K, T> node) {
        while (node != null && node.left != null) {
            node = node.left;
        }
        return node;
    }

    // Traverses the tree in-order and prints each node's data
    public void Traverse() {
        traverseTree(root);
    }

    private void traverseTree(BSTNode<K, T> node) {
        if (node == null) return;
        traverseTree(node.left);
        System.out.println(node.data);
        traverseTree(node.right);
    }

    // Traverses the tree with extended functionality for nested BSTs
    public void TraverseT() {
        traverseTreeT(root);
    }

    private void traverseTreeT(BSTNode<K, T> node) {
        if (node == null) return;
        traverseTreeT(node.left);
        if (node.data instanceof BST) {
            System.out.println("Node key ==== " + node.key);
            ((BST<String, Rank>) node.data).Traverse();
        } else {
            System.out.println(node.data);
        }
        traverseTreeT(node.right);
    }

    // Prints the tree data in a custom format
    public void PrintData() {
        printData(root);
    }

    private void printData(BSTNode<K, T> node) {
        if (node == null) return;
        printData(node.left);
        System.out.print(node.key);
        if (node.data instanceof Term) {
            System.out.print("   docs: ");
            boolean[] docs = ((Term) node.data).getDocs();
            for (int i = 0; i < docs.length; i++) {
                if (docs[i]) System.out.print(" " + i + " ");
            }
            System.out.println();
        }
        printData(node.right);
    }
}
