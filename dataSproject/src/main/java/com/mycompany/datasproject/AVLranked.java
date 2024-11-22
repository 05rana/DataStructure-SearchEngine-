/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.datasproject;

import com.mycompany.datasproject.AVLTree;


/**
 *
 * @author 1234
 */
public class AVLranked {
 class frequency
            {
                int docID = 0;
                int f = 0;
            }
    
           AVLTree <Integer, AVLTree <String,Rank>> AVLrank; 
           frequency [] freqs = new frequency[50];
            
            
            
            public AVLranked() {
                AVLrank = new AVLTree <Integer, AVLTree <String,Rank>>();
                
            }

        
public boolean addNew(int docID, String word) {
    // Check if the AVLrank tree is empty
    if (AVLrank.empty()) {
        AVLTree<String, Rank> miniRank = new AVLTree<String, Rank>();
        miniRank.insert(word, new Rank(word, 1));
        AVLrank.insert(docID, miniRank);
        return true;
    }
    
    // Check if the document already exists in the AVLrank tree
    if (AVLrank.find(docID)) {
        AVLTree<String, Rank> miniRank = AVLrank.retrieve();
        
        // If the word exists, increment its rank
        if (miniRank.find(word)) {
            Rank rank = miniRank.retrieve();
            rank.add_Rank();  // Increase the rank of the word
            miniRank.update(rank);  // Update the miniRank tree
            AVLrank.update(miniRank);  // Update the AVLrank tree
            return false;  // No new word inserted, only rank was updated
        }
        
        // If the word does not exist, insert it with rank 1
        miniRank.insert(word, new Rank(word, 1));
        AVLrank.update(miniRank);
        return true;  // New word inserted with rank 1
    }
    
    // If the document does not exist, create a new miniRank tree and insert the word
    AVLTree<String, Rank> miniRank = new AVLTree<String, Rank>();
    miniRank.insert(word, new Rank(word, 1));
    AVLrank.insert(docID, miniRank);
    return true;
}


        public boolean found(int docID, String word)
        {
               if (AVLrank.find(docID) )
                  if (AVLrank.retrieve().find(word))
                      return true;
               return false;
        }
        
        public int getrank (int docID, String word)
        {
            int value = 0;
               if (AVLrank.find(docID) )
                  if (AVLrank.retrieve().find(word))
                      return AVLrank.retrieve().retrieve().getRank();
               return value;
            
        }
        public void printDocument()
        {
                AVLrank.Traverse();
        }

        //=================================================================
/*public void TF(String str) {
    // Convert string to lowercase and remove leading/trailing spaces
    str = str.toLowerCase().trim();
    // Split the string into words
    String[] words = str.split(" ");

    // Initialize an index to track frequency results
    int index = 0;

    // Iterate over all documents (assuming there are 50 documents)
    for (int docID = 0; docID < 50; docID++) {
        int count = 0;

        // Count the total occurrences of the words in the document
        for (String word : words) {
            count += this.getrank(docID, word);
        }

        // If the word count is greater than 0, store the frequency
        if (count > 0) {
            freqs[index] = new frequency();
            freqs[index].docID = docID;
            freqs[index].f = count;
            index++;
        }
    }*/
        public void TF(String str)
        {
            str = str.toLowerCase().trim();
            String [] words = str.split(" ");
            
            int index = 0;
            for ( int docID = 0 ; docID < 50 ; docID++ )
            {
                int count = 0 ;
                for ( int j = 0 ;j < words.length ; j++ )
                    count += this.getrank(docID, words[j]);
                if (count > 0)
                {
                    freqs[index] = new frequency();
                    freqs[index].docID = docID;
                    freqs[index].f = count;
                    index ++;
                }
            }

    // Sort the frequencies in descending order
    mergesort(freqs, 0, index - 1);

    // Output the results (document ID and frequency)
    for (int x = 0; x < index; x++) {
        System.out.println(freqs[x].docID + "\t\t" + freqs[x].f);
    }
}

         //=================================================================
   public static void mergesort(frequency[] A, int l, int r) {
    if (l < r) {
        int m = (l + r) / 2;
        mergesort(A, l, m);  // Sort the left half
        mergesort(A, m + 1, r);  // Sort the right half
        merge(A, l, m, r);  // Merge the sorted halves
    }
}

private static void merge(frequency[] A, int l, int m, int r) {
    // Create a temporary array to hold the merged result
    frequency[] B = new frequency[r - l + 1];
    int i = l, j = m + 1, k = 0;

    // Merge the two halves
    while (i <= m && j <= r) {
        if (A[i].f >= A[j].f) {
            B[k++] = A[i++];
        } else {
            B[k++] = A[j++];
        }
    }

    // Copy the remaining elements if any
    while (i <= m) {
        B[k++] = A[i++];
    }
    while (j <= r) {
        B[k++] = A[j++];
    }

    // Copy the merged array back into the original array
    System.arraycopy(B, 0, A, l, B.length);
}
}