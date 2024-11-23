package com.mycompany.finalds;

public class Term {
    Vocabulary word;
    private final boolean[] docIDS;

    // Default constructor initializes docIDS and sets default vocabulary
    public Term() {
        this.docIDS = new boolean[50];
        this.word = new Vocabulary("");
    }

    // Parameterized constructor to initialize with a word and docIDS
    public Term(String word, boolean[] docIDS) {
        this.word = new Vocabulary(word);
        this.docIDS = docIDS.clone(); // Avoid modifying the original array
    }

    // Adds a document ID to docIDS if it doesn't already exist
    public boolean add_docID(int docID) {
        if (!docIDS[docID]) {
            docIDS[docID] = true;
            return true; // Successfully added
        }
        return false; // Already exists
    }

    // Sets the vocabulary for the term
    public void setVocabulary(Vocabulary word) {
        this.word = word;
    }

    // Retrieves the vocabulary
    public Vocabulary getVocabulary() {
        return word;
    }

    // Returns a copy of the docIDS array
    public boolean[] getDocs() {
        return docIDS.clone();
    }

    // Converts the term to a readable string representation
    @Override
    public String toString() {
        StringBuilder docs = new StringBuilder();
        boolean firstDoc = true;

        for (int i = 0; i < docIDS.length; i++) {
            if (docIDS[i]) {
                if (firstDoc) {
                    docs.append(i); // No comma before the first doc ID
                    firstDoc = false;
                } else {
                    docs.append(", ").append(i); // Add comma before subsequent doc IDs
                }
            }
        }
        
        return word + "[" + docs + ']';
    }
}
