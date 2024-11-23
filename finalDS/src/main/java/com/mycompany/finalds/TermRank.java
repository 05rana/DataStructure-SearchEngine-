package com.mycompany.finalds;

public class TermRank {
    Vocabulary word;
    private final int[] docIDS;

    // Default constructor initializes docIDS and sets default vocabulary
    public TermRank() {
        this.docIDS = new int[50]; // Automatically initialized to 0
        this.word = new Vocabulary("");
    }

    // Parameterized constructor initializes with a word and docIDS
    public TermRank(String word, int[] docIDS) {
        this.word = new Vocabulary(word);
        this.docIDS = docIDS.clone(); // Create a copy for encapsulation
    }

    // Increment the count for a given document ID
    public void adddocID(int docID) {
        if (docID >= 0 && docID < docIDS.length) {
            this.docIDS[docID]++;
        }
    }

    // Set the vocabulary for the term
    public void setVocabulary(Vocabulary word) {
        this.word = word;
    }



    // Return a copy of the docIDS array
    public int[] getDocs() {
        return docIDS.clone(); // Return a copy to ensure encapsulation
    }

    // Convert the TermRank object to a readable string representation
    @Override
    public String toString() {
        StringBuilder docs = new StringBuilder();
        boolean isFirst = true;

        for (int i = 0; i < docIDS.length; i++) {
            if (docIDS[i] != 0) {
                if (isFirst) {
                    docs.append(i); // Append without comma for the first entry
                    isFirst = false;
                } else {
                    docs.append(", ").append(i); // Append with a comma for subsequent entries
                }
            }
        }

        return word + "[" + docs + "]";
    }
}
