package com.mycompany.finalds;

public class RankedInvertedIndexBST {

    // Inner class for frequency
    class Frequency {
        int docID = 0;
        int frequency = 0;
    }

    private final BST<Integer, BST<String, Rank>> BSTrank; // Outer BST for documents
    private final Frequency[] freqs; // Frequency array for documents

    // Constructor
    public RankedInvertedIndexBST() {
        BSTrank = new BST<>();
        freqs = initializeFrequencies();
    }

    // Add a new word to the index
    public boolean addDoc(int docID, String word) {
        BST<String, Rank> miniRank;

        if (BSTrank.find(docID)) {
            miniRank = BSTrank.retrieve();
        } else {
            miniRank = new BST<>();
            BSTrank.insert(docID, miniRank);
        }

        if (miniRank.find(word)) {
            Rank rank = miniRank.retrieve();
            rank.addRank();
            miniRank.update(rank);
            BSTrank.update(miniRank);
            return false; // Word exists; rank updated
        }

        miniRank.insert(word, new Rank(word, 1));
        BSTrank.update(miniRank);
        return true; // New word inserted
    }

    // Check if a word exists in a specific document
    public boolean isfound(int docID, String word) {
        return BSTrank.find(docID) && BSTrank.retrieve().find(word);
    }

    // Get the rank of a word in a specific document
    public int getrank(int docID, String word) {
        if (BSTrank.find(docID)) {
            BST<String, Rank> miniRank = BSTrank.retrieve();
            if (miniRank.find(word)) {
                return miniRank.retrieve().getRank();
            }
        }
        return 0;
    }


    // Calculate Term Frequency (TF) for a string
    public void TF(String str) {
        str = str.toLowerCase().trim();
        String[] words = str.split("\\s+");
        Frequency[] localFreqs = initializeFrequencies();

        for (int docID = 0; docID < 50; docID++) {
            int count = 0;
            for (String word : words) {
                count += getrank(docID, word);
            }
            if (count > 0) {
                localFreqs[docID].docID = docID;
                localFreqs[docID].frequency = count;
            }
        }

        // Sort and print frequencies
        mergesort(localFreqs, 0, localFreqs.length - 1);
        printFrequencies(localFreqs);
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

    // Helper to initialize frequencies
    private Frequency[] initializeFrequencies() {
        Frequency[] frequencies = new Frequency[50];
        for (int i = 0; i < frequencies.length; i++) {
            frequencies[i] = new Frequency();
            frequencies[i].docID = i;
            frequencies[i].frequency = 0;
        }
        return frequencies;
    }

    // Helper to print frequencies
    private void printFrequencies(Frequency[] frequencies) {
        System.out.println("\nDocID\tScore");
        for (Frequency freq : frequencies) {
            if (freq.frequency != 0) {
                System.out.println(freq.docID + "\t\t" + freq.frequency);
            }
        }
    }
}
