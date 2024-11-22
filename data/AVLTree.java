public class Inverted_Index_BST {
    // The BST that stores the inverted index mapping words to terms
    BST<String, Term> invertedindexBST;
    
    // Counter for the number of terms
    int count = 0;

    // Constructor initializing the inverted index BST
    public Inverted_Index_BST() {
        invertedindexBST = new BST<String, Term>();
    }

    // Returns the size of the inverted index (number of terms)
    public int size() {
        return invertedindexBST.count;
    }

    // Adds a new word to the inverted index or updates an existing term with a new document ID
    public boolean addNew(int docID, String word) {
        Term t;

        // If the inverted index is empty, insert a new term
        if (invertedindexBST.empty()) {
            count++;
            t = new Term();
            t.setVocabulary(new Vocabulary(word));
            t.add_docID(docID);
            invertedindexBST.insert(word, t);
            return true;
        }

        // If the word is already found in the inverted index, update the term
        if (invertedindexBST.find(word)) {
            t = invertedindexBST.retrieve();
            t.add_docID(docID);
            invertedindexBST.update(t);
            return false; // The term already exists, no new insertion
        }

        // If the word is not found, create a new term and insert it
        count++;
        t = new Term();
        t.setVocabulary(new Vocabulary(word));
        t.add_docID(docID);
        invertedindexBST.insert(word, t);
        return true; // A new term was added
    }

    // Checks if the word exists in the inverted index
    public boolean found(String word) {
        return invertedindexBST.find(word);
    }

    // Prints all documents in the inverted index
    public void printDocument() {
        invertedindexBST.Traverse();
    }

    // Handles AND/OR queries and returns a boolean array of matching documents
    public boolean[] AND_OR_Function(String str) {
        str = str.toLowerCase().trim();

        // If no AND or OR operators are present, check for a single word
        if (!str.contains(" OR ") && !str.contains(" AND ")) {
            boolean[] result = new boolean[50];
            if (this.found(str)) {
                result = this.invertedindexBST.retrieve().getDocs();
            }
            return result;
        }

        // If both AND and OR operators are present, handle them accordingly
        if (str.contains(" AND ") && str.contains(" OR ")) {
            return handleAND_OR(str);
        }

        // If only AND operators are present, process with AND logic
        if (str.contains(" AND ")) {
            return AND_Function(str);
        }

        // Otherwise, process with OR logic
        return OR_Function(str);
    }

    // Handles the case where both AND and OR operators are present
    private boolean[] handleAND_OR(String str) {
        // Split the query by OR and process each part with AND operations
        String[] orParts = str.split(" OR ");
        boolean[] result = AND_Function(orParts[0]);

        for (int i = 1; i < orParts.length; i++) {
            boolean[] currentResult = AND_Function(orParts[i]);
            for (int j = 0; j < 50; j++) {
                result[j] = result[j] || currentResult[j]; // OR operation
            }
        }

        return result;
    }

    // Handles queries with AND operators. Returns a boolean array for documents
    // that match all the terms connected by AND.
    public boolean[] AND_Function(String str) {
        String[] terms = str.split(" AND ");
        boolean[] resultDocs = new boolean[50];

        // Process the first term and initialize resultDocs
        String firstTerm = terms[0].toLowerCase().trim();
        if (this.found(firstTerm)) {
            resultDocs = this.invertedindexBST.retrieve().getDocs();
        }

        // For the remaining terms, perform AND operation
        for (int i = 1; i < terms.length; i++) {
            String term = terms[i].toLowerCase().trim();
            boolean[] currentDocs = new boolean[50];
            
            if (this.found(term)) {
                currentDocs = this.invertedindexBST.retrieve().getDocs();
            }

            // Perform AND operation on each document
            for (int j = 0; j < 50; j++) {
                resultDocs[j] = resultDocs[j] && currentDocs[j];
            }
        }

        return resultDocs;
    }

    // Handles queries with OR operators. Returns a boolean array for documents
    // that match at least one of the terms connected by OR.
    public boolean[] OR_Function(String str) {
        String[] terms = str.split(" OR ");
        boolean[] resultDocs = new boolean[50];

        // Process the first term and initialize resultDocs
        String firstTerm = terms[0].toLowerCase().trim();
        if (this.found(firstTerm)) {
            resultDocs = this.invertedindexBST.retrieve().getDocs();
        }

        // For the remaining terms, perform OR operation
        for (int i = 1; i < terms.length; i++) {
            String term = terms[i].toLowerCase().trim();
            boolean[] currentDocs = new boolean[50];

            if (this.found(term)) {
                currentDocs = this.invertedindexBST.retrieve().getDocs();
            }

            // Perform OR operation on each document
            for (int j = 0; j < 50; j++) {
                resultDocs[j] = resultDocs[j] || currentDocs[j];
            }
        }

        return resultDocs;
    }
}
