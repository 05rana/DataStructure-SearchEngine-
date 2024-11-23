package com.mycompany.finalds;

public class InvertedIndexBST {

    final BST<String, Term> invertedindexBST;
    private int count;

    // Constructor
    public InvertedIndexBST() {
        invertedindexBST = new BST<>();
        count = 0;
    }

  

    // Add a new word to the index
    public boolean addDoc(int docID, String word) {
        if (invertedindexBST.find(word)) {
            Term term = invertedindexBST.retrieve();
            term.adddocID(docID);
            invertedindexBST.update(term);
            return false; // Word already exists; updated docID
        }

        Term newTerm = createTerm(docID, word);
        invertedindexBST.insert(word, newTerm);
        count++;
        return true; // Word inserted successfully
    }

    // Check if a word exists in the index
    public boolean isfound(String word) {
        return invertedindexBST.find(word);
    }

    // Print the document (traverse the BST)
    public void printDocs() {
        invertedindexBST.Traverse();
    }

    // Process AND/OR queries
    public boolean[] AndOrFunc(String str) {
        str = str.toLowerCase().trim();

        if (!str.contains(" or ") && !str.contains(" and ")) {
            return handleSingleTerm(str);
        } else if (str.contains(" or ") && str.contains(" and ")) {
            return handleMixedLogic(str);
        } else if (str.contains(" and ")) {
            return AndFunc(str);
        }
        return OrFunc(str);
    }

    // Process AND logic
    public boolean[] AndFunc(String str) {
        String[] terms = str.split(" and ");
        boolean[] result = initializeDocs(terms[0]);

        for (int i = 1; i < terms.length; i++) {
            boolean[] temp = initializeDocs(terms[i]);
            combineResults(result, temp, "and");
        }
        return result;
    }

    // Process OR logic
    public boolean[] OrFunc(String str) {
        String[] terms = str.split(" or ");
        boolean[] result = initializeDocs(terms[0]);

        for (int i = 1; i < terms.length; i++) {
            boolean[] temp = initializeDocs(terms[i]);
            combineResults(result, temp, "or");
        }
        return result;
    }

    // Handle a single-term query
    private boolean[] handleSingleTerm(String str) {
        return initializeDocs(str);
    }

    // Handle mixed AND/OR logic
    private boolean[] handleMixedLogic(String str) {
        String[] orParts = str.split(" or ");
        boolean[] result = AndFunc(orParts[0]);

        for (int i = 1; i < orParts.length; i++) {
            boolean[] temp = AndFunc(orParts[i]);
            combineResults(result, temp, "or");
        }
        return result;
    }

    // Initialize document matches for a term
    private boolean[] initializeDocs(String term) {
        boolean[] docs = new boolean[50]; // Default: all false
        if (isfound(term.trim())) {
            docs = invertedindexBST.retrieve().getDocs();
        }
        return docs;
    }

    // Combine results based on AND/OR operations
    private void combineResults(boolean[] result, boolean[] temp, String operation) {
        for (int i = 0; i < result.length; i++) {
            if ("and".equals(operation)) {
                result[i] = result[i] && temp[i];
            } else if ("or".equals(operation)) {
                result[i] = result[i] || temp[i];
            }
        }
    }

    // Create a new term for insertion
    private Term createTerm(int docID, String word) {
        Term term = new Term();
        term.setVocabulary(new Vocabulary(word));
        term.adddocID(docID);
        return term;
    }
}
