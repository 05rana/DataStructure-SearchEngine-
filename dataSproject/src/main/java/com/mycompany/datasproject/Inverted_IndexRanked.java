/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.datasproject;

import com.mycompany.datasproject.LinkedList;
import com.mycompany.datasproject.Vocabulary;

import java.util.Arrays;

/**
 *
 * @author 1234
 */
public class Inverted_IndexRanked {
            class frequency
            {
                int docID = 0;
                int f = 0;
            }

            LinkedList <TermRank> invertedindex; 
            frequency [] freqs;
            
            public Inverted_IndexRanked() {
                invertedindex = new LinkedList <TermRank>();
                freqs = new frequency[50];
            }
            
            public int size()
            {
                return invertedindex.size();
            }

       
            
 public boolean addNew(int docId, String term) {
   

    // If the inverted index is empty, create and insert a new TermRank
    if (invertedindex.empty()) {
        TermRank newTerm = new TermRank();
        newTerm.setVocabulary(new Vocabulary(term));
        newTerm.addDocID(docId);
        invertedindex.insert(newTerm);
        return true;
    }

    // Start iterating through the list
    invertedindex.findFirst();
    do {
        TermRank currentTerm = invertedindex.retrieve();  // Retrieve once to avoid multiple calls
        if (currentTerm.word.word.equalsIgnoreCase(term)) {
            // Term found, update the existing TermRank with the new document ID
            currentTerm.addDocID(docId);
            invertedindex.update(currentTerm);
            return false;
        }
        invertedindex.findNext();
    } while (!invertedindex.last());  // Continue until the last term is checked

    // Term not found, create and insert a new TermRank
    TermRank newTerm = new TermRank();
    newTerm.setVocabulary(new Vocabulary(term));
    newTerm.addDocID(docId);
    invertedindex.insert(newTerm);
    return true;
}


        public boolean found(String word)
        {
               if (invertedindex.empty())
                   return false;

               invertedindex.findFirst();
               for ( int i = 0 ; i < invertedindex.size ; i++)
               {
                   if ( invertedindex.retrieve().word.word.compareTo(word) == 0)
                       return true;
                  invertedindex.findNext();
               }
               return false;
        }

        public void printDocment()
        {
            if (this.invertedindex.empty())
                System.out.println("Empty Inverted Index");
            else
            {
                this.invertedindex.findFirst();
                while ( ! this.invertedindex.last())
                {
                    System.out.println(invertedindex.retrieve());
                    this.invertedindex.findNext();
                }
                System.out.println(invertedindex.retrieve());
            }
        }

        //=================================================================
      
        
        public void computeTermFrequency(String inputText) {
    // Normalize the input text and split it into words
    String[] words = inputText.toLowerCase().trim().split(" ");
    
    // Initialize a frequency array for 50 documents
    frequency[] documentFrequencies = new frequency[50];
    for (int i = 0; i < 50; i++) {
        documentFrequencies[i] = new frequency();
        documentFrequencies[i].docID = i;
        documentFrequencies[i].f = 0;  // Initial frequency is 0
    }
    
    // Iterate through each word in the input
    for (String word : words) {
        if (found(word)) {
            // Get the documents where the word appears
            int[] documentIds = invertedindex.retrieve().getDocs();
            
            // For each document ID, update the frequency count
            for (int docId : documentIds) {
                if (docId != 0) {
                    documentFrequencies[docId].f += 1;  // Increment the frequency count
                }
            }
        }
    }

    // Sort the document frequencies by frequency in descending order
    Arrays.sort(documentFrequencies, (a, b) -> Integer.compare(b.f, a.f));

    // Output the results (DocID and Score)
    System.out.println("\nDocID\t\tScore");
    for (frequency docFreq : documentFrequencies) {
        if (docFreq.f > 0) {  // Only print documents with non-zero frequency
            System.out.println(docFreq.docID + "\t\t" + docFreq.f);
        }
    }
}


         //=================================================================
    public static void mergesort ( frequency [] A , int l , int r ) 
    {
        if ( l >= r )
            return;
        int m = ( l + r ) / 2;
        mergesort (A , l , m ) ;          // Sort first half
        mergesort (A , m + 1 , r ) ;    // Sort second half
        merge (A , l , m , r ) ;            // Merge
    }

    private static void merge(frequency[] A, int l, int m, int r) {
    int leftSize = m - l + 1;
    int rightSize = r - m;

    // Temporary arrays for left and right subarrays
    frequency[] left = new frequency[leftSize];
    frequency[] right = new frequency[rightSize];

    // Copy data into temporary arrays
    System.arraycopy(A, l, left, 0, leftSize);
    System.arraycopy(A, m + 1, right, 0, rightSize);

    // Merge the temp arrays back into A[]
    int i = 0, j = 0, k = l;
    while (i < leftSize && j < rightSize) {
        if (left[i].f >= right[j].f) {
            A[k++] = left[i++];
        } else {
            A[k++] = right[j++];
        }
    }

    // Copy remaining elements from left[] if any
    while (i < leftSize) {
        A[k++] = left[i++];
    }

    // Copy remaining elements from right[] if any
    while (j < rightSize) {
        A[k++] = right[j++];
    }
}


}