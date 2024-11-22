

public class BST<T> {
    private BSTNode<T> root;

    // Constructor to initialize the BST
    public BST() {
        root = null;
    }

    // Check if the tree is empty
    public boolean empty() {
        return root == null;
    }

    // Insert a new key-value pair into the BST
    public boolean insert(String key, T value) {
        if (root == null) {
            root = new BSTNode<>(key, value);
            return true; // Successfully inserted the first node
        } else {
            return insertRecursively(root, key, value);
        }
    }

    // Recursive helper method for inserting a new node
    private boolean insertRecursively(BSTNode<T> node, String key, T value) {
        if (key.compareToIgnoreCase(node.key) == 0) {
            return false; // Duplicate key, not inserted
        } else if (key.compareToIgnoreCase(node.key) < 0) {
            // If the key is less than the current node's key, go left
            if (node.left == null) {
                node.left = new BSTNode<>(key, value); // Insert as left child
                return true;
            } else {
                return insertRecursively(node.left, key, value); // Recurse left
            }
        } else {
            // If the key is greater than the current node's key, go right
            if (node.right == null) {
                node.right = new BSTNode<>(key, value); // Insert as right child
                return true;
            } else {
                return insertRecursively(node.right, key, value); // Recurse right
            }
        }
    }

    // Find if a key exists in the tree
    public boolean findKey(String key) {
        return findKeyRecursively(root, key);
    }

    // Recursive helper method to find a key in the tree
    private boolean findKeyRecursively(BSTNode<T> node, String key) {
        if (node == null) {
            return false; // Key not found
        }
        if (key.compareToIgnoreCase(node.key) == 0) {
            return true; // Key found
        } else if (key.compareToIgnoreCase(node.key) < 0) {
            return findKeyRecursively(node.left, key); // Search left
        } else {
            return findKeyRecursively(node.right, key); // Search right
        }
    }

    // Retrieve the data associated with a key
    public T retrieve(String key) {
        BSTNode<T> node = retrieveNode(root, key);
        return (node != null) ? node.data : null; // Return the data or null if not found
    }

    // Recursive helper method to retrieve a node with a specific key
    private BSTNode<T> retrieveNode(BSTNode<T> node, String key) {
        if (node == null) {
            return null; // Key not found
        }
        if (key.compareToIgnoreCase(node.key) == 0) {
            return node; // Node found
        } else if (key.compareToIgnoreCase(node.key) < 0) {
            return retrieveNode(node.left, key); // Search left
        } else {
            return retrieveNode(node.right, key); // Search right
        }
    }

    // In-order traversal of the tree to display the data
    public void inOrder() {
        if (empty()) {
            System.out.println("empty tree");
        } else {
            inOrder(root);
        }
    }

    // Recursive helper method for in-order traversal
    private void inOrder(BSTNode<T> node) {
        if (node != null) {
            inOrder(node.left); // Visit left subtree
            ((Word) node.data).display(); // Display the data (casting to Word for display)
            inOrder(node.right); // Visit right subtree
        }
    }

    // Pre-order traversal of the tree to display the data
    public void preOrder() {
        if (empty()) {
            System.out.println("empty tree");
        } else {
            preOrder(root);
        }
    }

    // Recursive helper method for pre-order traversal
    private void preOrder(BSTNode<T> node) {
        if (node != null) {
            ((Word) node.data).display(); // Display the data (casting to Word for display)
            preOrder(node.left); // Visit left subtree
            preOrder(node.right); // Visit right subtree
        }
    }

    // Inner class for BST nodes
    private static class BSTNode<T> {
        String key;          // The key for this node
        T data;              // The data associated with the key
        BSTNode<T> left;     // Pointer to the left child
        BSTNode<T> right;    // Pointer to the right child

        // Constructor to initialize the node with a key and data
        public BSTNode(String key, T data) {
            this.key = key;
            this.data = data;
            this.left = null;  // Initially, left child is null
            this.right = null; // Initially, right child is null
        }
    }
}
