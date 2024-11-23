package com.mycompany.finalds;

public class InvertedIndex {

    LinkedList<Term> invertedindex;

    public InvertedIndex() {
        invertedindex = new LinkedList<>();
    }



    public boolean addDoc(int docID, String word) {
        if (invertedindex.empty()) {
            invertedindex.insert(createTerm(docID, word));
            return true;
        }

        invertedindex.findFirst();
        while (true) {
            Term currentTerm = invertedindex.retrieve();
            if (currentTerm.word.word.equals(word)) {
                currentTerm.adddocID(docID);
                invertedindex.update(currentTerm);
                return false; // Word already exists, docID updated
            }

            if (invertedindex.last()) break;
            invertedindex.findNext();
        }

        invertedindex.insert(createTerm(docID, word));
        return true; // Word inserted successfully
    }

    public boolean isfound(String word) {
        if (invertedindex.empty()) return false;

        invertedindex.findFirst();
        while (true) {
            if (invertedindex.retrieve().word.word.equals(word)) return true;

            if (invertedindex.last()) break;
            invertedindex.findNext();
        }

        return false; // Word not found
    }

    public boolean[] AndOrFunc(String str) {
        str = str.toLowerCase().trim();
        if (!str.contains(" or ") && !str.contains(" and ")) {
            return handleSingleTerm(str);
        } else if (str.contains(" or ") && str.contains(" and ")) {
            return handleMixedLogic(str);
        } else if (str.contains(" and ")) {
            return AndFunc(str);
        } else {
            return OrFunc(str);
        }
    }

    public boolean[] AndFunc(String str) {
        String[] terms = str.split(" and ");
        boolean[] result = getDocs(terms[0].trim());

        for (int i = 1; i < terms.length; i++) {
            combineResults(result, getDocs(terms[i].trim()), "and");
        }
        return result;
    }

    public boolean[] OrFunc(String str) {
        String[] terms = str.split(" or ");
        boolean[] result = getDocs(terms[0].trim());

        for (int i = 1; i < terms.length; i++) {
            combineResults(result, getDocs(terms[i].trim()), "or");
        }
        return result;
    }



    // =====================================================================

    private Term createTerm(int docID, String word) {
        Term term = new Term();
        term.setVocabulary(new Vocabulary(word));
        term.adddocID(docID);
        return term;
    }

    private boolean[] handleSingleTerm(String term) {
        return isfound(term) ? invertedindex.retrieve().getDocs() : new boolean[50];
    }

    private boolean[] handleMixedLogic(String str) {
        String[] orParts = str.split(" or ");
        boolean[] result = AndFunc(orParts[0]);

        for (int i = 1; i < orParts.length; i++) {
            combineResults(result, AndFunc(orParts[i]), "or");
        }
        return result;
    }

    private boolean[] getDocs(String word) {
        return isfound(word) ? invertedindex.retrieve().getDocs() : new boolean[50];
    }

    private void combineResults(boolean[] result, boolean[] temp, String operation) {
        for (int i = 0; i < result.length; i++) {
            if ("and".equals(operation)) {
                result[i] = result[i] && temp[i];
            } else if ("or".equals(operation)) {
                result[i] = result[i] || temp[i];
            }
        }
    }
}
