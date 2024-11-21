package com.mycompany.datasproject;



/*
Inverted Index: A mapping from terms (unique words) to a list of documents containing 
those terms. Both of Index and Inverted Index will be implemented using list of lists.
*/

public class Inverted_Index {

    // Linked list to hold the inverted index
    LinkedList<Term> invertedindex; 

    // Constructor to initialize the inverted index
    public Inverted_Index() {
        invertedindex = new LinkedList<Term>();
    }

    // Returns the size of the inverted index
    public int size() {
        return invertedindex.size();
    }

    /*
     * Adds a new word to the inverted index. If the word exists, it adds the docID 
     * to the existing term. Otherwise, it creates a new term with the word and docID.
     */
    public boolean addNew(int docID, String word) {
        if (invertedindex.empty()) {
            Term newTerm = new Term();
            newTerm.setVocabulary(new Vocabulary(word)); // Set the word as vocabulary
            newTerm.add_docID(docID); // Add the document ID to the term
            invertedindex.insert(newTerm); // Insert the term into the inverted index
            return true;
        } 
        else {

            invertedindex.findFirst();
            while (!invertedindex.last()) {
                // If the word already exists, update the document list
                if (invertedindex.retrieve().word.word.compareTo(word) == 0) {
                    Term t = invertedindex.retrieve();
                    t.add_docID(docID);
                    invertedindex.update(t);
                    return false;
                }
                invertedindex.findNext();
            }
            
            
            // Check the last term
            if (invertedindex.retrieve().word.word.compareTo(word) == 0) {
                Term t = invertedindex.retrieve();
                t.add_docID(docID);
                invertedindex.update(t);
                return false;
            } else {
                // If the word doesn't exist, create a new term
                Term t = new Term();
                t.setVocabulary(new Vocabulary(word));
                t.add_docID(docID);
                invertedindex.insert(t);
            }
            return true;
        }
    }

    /*
     * Checks if the given word exists in the inverted index.
     */
    public boolean found(String word) {
    while (!invertedindex.last()) {
        Term currentTerm = invertedindex.retrieve();
        if (currentTerm.word.word.equals(word)) {
            return true; // Word found
        }
        invertedindex.findNext(); // Continue to the next term
    }
    
    // Check the last term
    Term lastTerm = invertedindex.retrieve();
    if (lastTerm.word.word.equals(word)) {
        return true; // Word found
    }
    
    return false; // Word not found
}
    
    
    

    // Handle complex queries with AND/OR operators
    public boolean[] AND_OR_Function(String str) {
        // Convert the query string to lowercase and trim whitespace
        str = str.toLowerCase().trim();

        // If the query has no operators (just a single word)
        if (!str.contains(" or ") && !str.contains(" and ")) {
            boolean[] result = new boolean[50];

            // Check if the word exists in the index and retrieve the docs
            if (this.found(str)) {
                result = this.invertedindex.retrieve().getDocs();
            }
            return result;
        }
    // If the query contains both AND and OR operators
        if (str.contains(" or ") && str.contains(" and ")) {
            String[] orParts = str.split(" or ");
            boolean[] result = handleANDQuery(orParts[0]);  // Start with the first part (AND part)

            // Process each subsequent OR part
            for (int i = 1; i < orParts.length; i++) {
                boolean[] currentResult = handleANDQuery(orParts[i]);
                for (int j = 0; j < 50; j++) {
                    result[j] = result[j] || currentResult[j];  // Perform OR operation
                }
            }
            return result;
        }

        // If the query contains only AND operators
        if (str.contains(" and ")) {
            return handleANDQuery(str);
        }

        // If the query only contains OR operators, process as an OR query
        return handleORQuery(str);
    }


 /*
 * Handles queries with AND operators. Returns a boolean array for documents
 * that match all the terms connected by AND.
 */
public boolean[] handleANDQuery(String query) {
    String[] terms = query.split(" AND "); // Split the query by AND
    boolean[] result = new boolean[50]; // Initialize the result array
    
    // Check if the first term exists in the inverted index
    String firstTerm = terms[0].toLowerCase().trim();
    if (this.found(firstTerm)) {
        result = this.invertedindex.retrieve().getDocs(); // Get the docs for the first term
    }

    // Process subsequent terms and apply AND operation
    for (int i = 1; i < terms.length; i++) {
        boolean[] currentTermDocs = new boolean[50];
        String currentTerm = terms[i].toLowerCase().trim();
        
        // Check if the current term exists in the inverted index
        if (this.found(currentTerm)) {
            currentTermDocs = this.invertedindex.retrieve().getDocs();
        }
        
        // Apply AND operation across all documents
        for (int j = 0; j < 50; j++) {
            result[j] = result[j] && currentTermDocs[j];
        }
    }

    return result;
}

/*
 * Handles queries with OR operators. Returns a boolean array for documents
 * that match at least one of the terms connected by OR.
 */
public boolean[] handleORQuery(String query) {
    String[] terms = query.split(" OR "); // Split the query by OR
    boolean[] result = new boolean[50]; // Initialize the result array
    
    // Check if the first term exists in the inverted index
    String firstTerm = terms[0].toLowerCase().trim();
    if (this.found(firstTerm)) {
        result = this.invertedindex.retrieve().getDocs(); // Get the docs for the first term
    }

    // Process subsequent terms and apply OR operation
    for (int i = 1; i < terms.length; i++) {
        boolean[] currentTermDocs = new boolean[50];
        String currentTerm = terms[i].toLowerCase().trim();
        
        // Check if the current term exists in the inverted index
        if (this.found(currentTerm)) {
            currentTermDocs = this.invertedindex.retrieve().getDocs();
        }
        
        // Apply OR operation across all documents
        for (int j = 0; j < 50; j++) {
            result[j] = result[j] || currentTermDocs[j];
        }
    }

    return result;
}

    /*
     * Prints the entire inverted index. If it's empty, it prints a message.
     */
    public void printDocument() {
        if (this.invertedindex.empty()) {
            System.out.println("Empty Inverted Index");
        } else {
            this.invertedindex.findFirst();
            while (!this.invertedindex.last()) {
                System.out.println(invertedindex.retrieve());
                this.invertedindex.findNext();
            }
            System.out.println(invertedindex.retrieve());
        }
    }
}
