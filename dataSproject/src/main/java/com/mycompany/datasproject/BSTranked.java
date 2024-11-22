/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.datasproject;

/**
 *
 * @author 1234
 */
public class Inverted_Index_BSTRanked {
            class frequency
            {
                int docID = 0;
                int f = 0;
            }
    
           BST <Integer, BST <String,Rank>> BSTrank; 
           frequency [] freqs = new frequency[50];
            
            
            
            public BSTRanked() {
                BSTrank = new BST <Integer, BST <String,Rank>>();
                
            }

public boolean addNew(int docID, String word) {
    // Case 1: If BSTrank is empty, create and insert a new document with the word
    if (BSTrank.empty()) {
        BST<String, Rank> miniRank = new BST<String, Rank>();
        miniRank.insert(word, new Rank(word, 1));
        BSTrank.insert(docID, miniRank);
        return true;
    }

    // Case 2: If the document exists in BSTrank
    if (BSTrank.find(docID)) {
        BST<String, Rank> miniRank = BSTrank.retrieve();

        // Case 2.1: If the word exists, increment its rank
        if (miniRank.find(word)) {
            Rank rank = miniRank.retrieve();
            rank.add_Rank();
            miniRank.update(rank);
            BSTrank.update(miniRank);
            return false; // Word already exists, no new insertion
        }

        // Case 2.2: If the word doesn't exist, insert it with initial rank
        miniRank.insert(word, new Rank(word, 1));
        BSTrank.update(miniRank);
        return true; // Word added
    }

    // Case 3: If the document doesn't exist, create a new one and insert the word
    BST<String, Rank> miniRank = new BST<String, Rank>();
    miniRank.insert(word, new Rank(word, 1));
    BSTrank.insert(docID, miniRank);
    return true;
}

        public boolean found(int docID, String word)
        {
               if (BSTrank.find(docID) )
                  if (BSTrank.retrieve().find(word))
                      return true;
               return false;
        }
        
        public int getrank (int docID, String word)
        {
            int value = 0;
               if (BSTrank.find(docID) )
                  if (BSTrank.retrieve().find(word))
                      return BSTrank.retrieve().retrieve().getRank();
               return value;
            
        }
        public void printDocument()
        {
                BSTrank.TraverseT();
        }

        //=================================================================
public void TF(String str) {
    str = str.toLowerCase().trim();
    String[] words = str.split(" ");

    int index = 0;

    // Iterate through all documents
    for (int docID = 0; docID < 50; docID++) {
        int count = 0;

        // Count occurrences of words in the document
        for (String word : words) {
            count += this.getrank(docID, word);
        }

        // If any word is found, store the document ID and frequency
        if (count > 0) {
            freqs[index] = new frequency();
            freqs[index].docID = docID;
            freqs[index].f = count;
            index++;
        }
    }

    // Sort the frequencies in descending order
    mergesort(freqs, 0, index - 1);

    // Print the document IDs and their frequencies
    for (int i = 0; i < index; i++) {
        System.out.println(freqs[i].docID + "\t\t" + freqs[i].f);
    }
}


         //=================================================================
public static void sortByFrequency(frequency[] A, int l, int r) {
    // Base case: when the array has one or no element
    if (l < r) {
        int m = (l + r) / 2;
        
        // Recursively sort the two halves
        sortByFrequency(A, l, m);
        sortByFrequency(A, m + 1, r);
        
        // Merge the two sorted halves
        mergeSortedFrequencies(A, l, m, r);
    }
}

private static void mergeSortedFrequencies(frequency[] A, int l, int m, int r) {
    int leftSize = m - l + 1;
    int rightSize = r - m;

    frequency[] left = new frequency[leftSize];
    frequency[] right = new frequency[rightSize];

    // Copy data to temporary arrays
    System.arraycopy(A, l, left, 0, leftSize);
    System.arraycopy(A, m + 1, right, 0, rightSize);

    int i = 0, j = 0, k = l;

    // Merge the two arrays while maintaining order
    while (i < leftSize && j < rightSize) {
        if (left[i].f >= right[j].f) {
            A[k++] = left[i++];
        } else {
            A[k++] = right[j++];
        }
    }

    // Copy remaining elements of left[] if any
    while (i < leftSize) {
        A[k++] = left[i++];
    }

    // Copy remaining elements of right[] if any
    while (j < rightSize) {
        A[k++] = right[j++];
    }
}


}