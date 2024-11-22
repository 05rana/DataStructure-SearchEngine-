
package data;
/*
Inverted Index with AVLs: Enhance the implementation of Inverted Index by using BSTs 
instead of Lists. 
 */


public class Inverted_Index_AVL {

    // Declare AVL Tree for the inverted index
    AVLTree<String, Term> invertedindexAVL;

    // Constructor initializing the AVL Tree
    public Inverted_Index_AVL() {
        invertedindexAVL = new AVLTree<String, Term>();
    }

    // Return the size of the inverted index
    public int size() {
        return invertedindexAVL.size();
    }

    // Add a new word to the inverted index
    public boolean addNew(int docID, String word) {
        // If the index is empty, create a new term entry
        if (invertedindexAVL.empty()) {
            Term t = new Term();
            t.setVocabulary(new Vocabulary(word)); // Set the vocabulary with the word
            t.add_docID(docID); // Add document ID
            invertedindexAVL.insert(word, t); // Insert the term into the AVL Tree
            return true;
        } else {
            // If the term already exists, update the document list
            if (invertedindexAVL.find(word)) {
                Term t = invertedindexAVL.retrieve();
                t.add_docID(docID); // Add document ID to the existing term
                invertedindexAVL.update(t); // Update the term in the AVL Tree
                return false;
            }

            // Otherwise, insert a new term into the index
            Term t = new Term();
            t.setVocabulary(new Vocabulary(word)); // Set vocabulary for the word
            t.add_docID(docID); // Add document ID
            invertedindexAVL.insert(word, t); // Insert the new term
            return true;
        }
    }

    // Check if a word exists in the inverted index
    public boolean found(String word) {
        return invertedindexAVL.find(word);
    }

    // Print the documents in the inverted index (traverse the AVL Tree)
    public void printDocument() {
        invertedindexAVL.traverse();
    }

    /*
     * Handle AND/OR queries. Parse the query to check for AND/OR operators
     * and return the results of the query as a boolean array of document IDs.
     */
    public boolean[] AND_OR_Function(String str) {
        str = str.toLowerCase().trim(); // Convert query to lowercase and trim extra spaces

        // If the query has no operators (just a single word)
        if (!str.contains(" or ") && !str.contains(" and ")) {
            boolean[] r1 = new boolean[50]; // Dynamically adjust this size later based on actual number of docs
            if (this.found(str)) {
                r1 = this.invertedindexAVL.retrieve().getDocs(); // Retrieve documents for the word
            }
            return r1;
        }

        // If the query contains both AND and OR operators
        else if (str.contains(" or ") && str.contains(" and ")) {
            String[] AND_ORs = str.split(" or ");
            boolean[] r1 = handleANDQuery(AND_ORs[0].trim()); // Start with the first part (AND part)

            // Process each subsequent OR part
            for (int i = 1; i < AND_ORs.length; i++) {
                boolean[] r2 = handleANDQuery(AND_ORs[i].trim());
                for (int j = 0; j < r1.length; j++) {
                    r1[j] = r1[j] || r2[j]; // Perform OR operation
                }
            }
            return r1;
        }

        // If the query only contains AND operators
        else if (str.contains(" and ")) {
            return handleANDQuery(str);
        }

        // If the query only contains OR operators
        return handleORQuery(str);
    }

    /*
     * Handle AND query. Process the query by splitting it into terms and performing 
     * AND operation across all terms to retrieve the documents that match all terms.
     */
    public boolean[] handleANDQuery(String query) {
        String[] terms = query.split(" AND ");
        boolean[] resultDocs = new boolean[50]; // Adjust dynamically later if needed

        // Check if the first term exists in the index
        if (this.found(terms[0].toLowerCase().trim())) {
            resultDocs = this.invertedindexAVL.retrieve().getDocs(); // Retrieve the docs for the first term
        }

        // Process subsequent terms in the AND query
        for (int i = 1; i < terms.length; i++) {
            boolean[] currentDocs = new boolean[50]; // Adjust dynamically later if needed
            if (this.found(terms[i].toLowerCase().trim())) {
                currentDocs = this.invertedindexAVL.retrieve().getDocs(); // Retrieve docs for the current term
            }

            // Perform AND operation: Only retain docs present in both resultDocs and currentDocs
            for (int j = 0; j < resultDocs.length; j++) {
                resultDocs[j] = resultDocs[j] && currentDocs[j];
            }
        }
        return resultDocs;
    }

    /*
     * Handle OR query. Process the query by splitting it into terms and performing 
     * OR operation across all terms to retrieve the documents that match any term.
     */
    public boolean[] handleORQuery(String query) {
        String[] terms = query.split(" OR ");
        boolean[] resultDocs = new boolean[50]; // Adjust dynamically later if needed

        // Check if the first term exists in the index
        if (this.found(terms[0].toLowerCase().trim())) {
            resultDocs = this.invertedindexAVL.retrieve().getDocs(); // Retrieve the docs for the first term
        }

        // Process subsequent terms in the OR query
        for (int i = 1; i < terms.length; i++) {
            boolean[] currentDocs = new boolean[50]; // Adjust dynamically later if needed
            if (this.found(terms[i].toLowerCase().trim())) {
                currentDocs = this.invertedindexAVL.retrieve().getDocs(); // Retrieve docs for the current term
            }

            // Perform OR operation: Include docs that are in either resultDocs or currentDocs
            for (int j = 0; j < resultDocs.length; j++) {
                resultDocs[j] = resultDocs[j] || currentDocs[j];
            }
        }
        return resultDocs;
    }
}
