package com.mycompany.finalds;

import java.util.function.Function;

public class RankedInvertedIndexAVL {

    // Inner class for frequency
    class Frequency {
        int docID = 0;
        int frequency = 0;
    }

    private AVLTree<Integer, AVLTree<String, Rank>> AVLrank; // Outer AVL tree
    private final Frequency[] freqs; // Frequency array for documents

    // Constructor
    public RankedInvertedIndexAVL() {
        AVLrank = new AVLTree<>();
        freqs = initializeFrequencies(50); // Initialize with default size
    }

    // Add a new word to the index
    public boolean addDoc(int docID, String word) {
        AVLTree<String, Rank> miniRank = AVLrank.find(docID) ? AVLrank.retrieve() : new AVLTree<>();

        if (miniRank.find(word)) {
            Rank rank = miniRank.retrieve();
            rank.addRank();
            miniRank.update(rank);
        } else {
            miniRank.insert(word, new Rank(word, 1));
        }

        AVLrank.insert(docID, miniRank); // Updates the document's AVLTree
        return !miniRank.find(word); // Returns true if the word was newly added
    }

    // Check if a word exists in a specific document
    public boolean isfound(int docID, String word) {
        return AVLrank.find(docID) && AVLrank.retrieve().find(word);
    }

    // Get the rank of a word in a specific document
    public int getrank(int docID, String word) {
        if (isfound(docID, word)) {
            return AVLrank.retrieve().retrieve().getRank();
        }
        return 0;
    }

    // Print all documents and their rankings
    public void printDocs() {
        AVLrank.Traverse();
    }

    // Calculate Term Frequency (TF) for a string
    public void TF(String str) {
        String[] words = preprocessInput(str);
        resetFrequencies();

        for (int docID = 0; docID < freqs.length; docID++) {
            int count = 0;
            for (String word : words) {
                count += getrank(docID, word);
            }
            if (count > 0) {
                freqs[docID].frequency = count;
            }
        }

        // Sort and print frequencies
        mergesort(freqs, 0, freqs.length - 1);
        printFrequencies();
    }

    // Merge sort for frequency array
    public static void mergesort(Frequency[] A, int l, int r) {
        if (l < r) {
            int m = (l + r) >>> 1; // Avoid overflow, same as (l + r) / 2
            mergesort(A, l, m);
            mergesort(A, m + 1, r);
            merge(A, l, m, r);
        }
    }

    // Merge step for merge sort
    private static void merge(Frequency[] A, int l, int m, int r) {
        Frequency[] left = new Frequency[m - l + 1];
        Frequency[] right = new Frequency[r - m];

        // Copy to temporary arrays
        System.arraycopy(A, l, left, 0, left.length);
        System.arraycopy(A, m + 1, right, 0, right.length);

        int i = 0, j = 0, k = l;

        // Merge back to original array
        while (i < left.length && j < right.length) {
            A[k++] = (left[i].frequency >= right[j].frequency) ? left[i++] : right[j++];
        }

        // Copy remaining elements
        while (i < left.length) A[k++] = left[i++];
        while (j < right.length) A[k++] = right[j++];
    }

    // Helper to preprocess input string
    private String[] preprocessInput(String str) {
        return str.toLowerCase().trim().split("\\s+");
    }

    // Helper to reset frequencies
    private void resetFrequencies() {
        for (Frequency freq : freqs) {
            freq.frequency = 0;
        }
    }

    // Helper to initialize frequencies
    private Frequency[] initializeFrequencies(int size) {
        Frequency[] frequencies = new Frequency[size];
        for (int i = 0; i < size; i++) {
            frequencies[i] = new Frequency();
            frequencies[i].docID = i;
        }
        return frequencies;
    }

    // Helper to print frequencies
    private void printFrequencies() {
        System.out.println("\nDocID\tScore");
        for (Frequency freq : freqs) {
            if (freq.frequency != 0) {
                System.out.println(freq.docID + "\t\t" + freq.frequency);
            }
        }
    }
}
