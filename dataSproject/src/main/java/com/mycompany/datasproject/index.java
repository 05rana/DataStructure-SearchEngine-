package com.mycompany.datasproject;

/**
 * Index: A mapping from document IDs to a list of words contained in the document.
 */



//Index: A mapping from document IDs to a list of words contained in the document.

public class index {

    // Inner class to represent a document
    class Document {
        int docID;                      // Document ID
        LinkedList<String> index;       // Linked list of words in the document

        // Constructor to initialize a Document
        public Document() {
            docID = 0;
            index = new LinkedList<String>();
        }

        // Add a new word to the document's index
        public void addNew(String word) {
            index.insert(word);
        }

        // Check if a word is present in the document
        public boolean found(String word) {
            if (index.empty()) {
                return false; // Return false if the index is empty
            }

            index.findFirst(); // Start from the first word
            for (int i = 0; i < index.size(); i++) {
                if (index.retrieve().compareTo(word) == 0) {
                    return true; // Word found
                }
                index.findNext(); // Move to the next word
            }
            return false; // Word not found
        }
    }

    //====================================================================
    
    // Array of documents (simulating a fixed-sized document repository)
    Document[] indexes;

    // Constructor to initialize the document index
    public index() {
        indexes = new Document[50]; // Array of 50 documents
        for (int i = 0; i < indexes.length; i++) {
            indexes[i] = new Document(); // Initialize each document
            indexes[i].docID = i;        // Assign a document ID
        }
    }

    // Add a word to a specific document
    public void addDocument(int docID, String data) {
        indexes[docID].addNew(data);
    }

    // Print the words in a specific document
    public void printDocment(int docID) {

    LinkedList<String> documentIndex = indexes[docID].index;     // Retrieve the document's index 

    // Check if the document is empty
    if (documentIndex.empty()) {
        System.out.println("Empty Document"); // Output for an empty document
        return;
    }
     // Print all words in the document
    System.out.print("Document " + docID + ": ");
    documentIndex.findFirst(); // Start from the first word
    int size = documentIndex.size(); // Store the size of the index

    for (int i = 0; i < size; i++) {
        System.out.print(documentIndex.retrieve() + " "); // Print the current word
        documentIndex.findNext(); // Move to the next word
    }
    System.out.println(); // End the line after printing all words
}


    //====================================================================

    // Get documents containing a specific word
    public boolean[] getDocs(String str) {
        boolean[] result = new boolean[50]; // Boolean array to track documents containing the word
        for (int i = 0; i < result.length; i++) {
            result[i] = false; // Initialize all values to false
        }

        for (int i = 0; i < result.length; i++) {
            if (indexes[i].found(str)) {
                result[i] = true; // Mark true for documents containing the word
            }
        }

        return result;
    }

    //====================================================================

    // Handle complex queries with AND/OR operators
    public boolean[] AND_OR_Function(String str) {
        // Convert the query to lowercase and trim whitespace
        str = str.toLowerCase().trim();

        // If the query has no operators, handle it as a simple query
        if (!str.contains(" or ") && !str.contains(" and ")) {
            return getDocs(str);
        }
        
       
        // If the query contains both AND and OR operators
           if (str.contains(" or ") && str.contains(" and ")) {
               // Split the query by " OR " first
               String[] orParts = str.split(" or ");
               boolean[] result = AND_Function(orParts[0]); // Start with the first part processed by AND


            for (int i = 1; i < orParts.length; i++) {
                boolean[] result2 = AND_Function(orParts[i]);
                for (int j = 0; j < 50; j++) {
                    result[j] = result[j] || result2[j]; // Perform OR operation
                }
            }
            return result;
        }

        // If the query contains only AND operators
        else if (str.contains(" AND ")) {
            return AND_Function(str);
        }

        // Otherwise, process as an OR query
        return OR_Function(str);
    }

    //====================================================================

    // Handle queries with AND operators
    public boolean[] AND_Function(String str) {
      String[] ANDs = str.toLowerCase().trim().split(" and ");
      boolean[] b1 = getDocs(ANDs[0]); // Get the result for the first part

        for (int i = 1; i < ANDs.length; i++) {
            boolean[] b2 = getDocs(ANDs[i].toLowerCase().trim());
            for (int j = 0; j < 50; j++) {
                b1[j] = b1[j] && b2[j]; // Perform AND operation
            }
        }
        return b1;
    }

    // Handle queries with OR operators
    public boolean[] OR_Function(String str) {
        String[] ORs = str.split(" OR "); // Split query by OR
        boolean[] b1 = getDocs(ORs[0].toLowerCase().trim());

        for (int i = 1; i < ORs.length; i++) {
            boolean[] b2 = getDocs(ORs[i].toLowerCase().trim());
            for (int j = 0; j < 50; j++) {
                b1[j] = b1[j] || b2[j]; // Perform OR operation
            }
        }
        return b1;
    }
}
