package com.mycompany.finalds;

public class InvertedIndexAVL {

    AVLTree<String, Term> invertedindexAVL;

    public InvertedIndexAVL() {
        invertedindexAVL = new AVLTree<>();
    }

    public int size() {
        return invertedindexAVL.size();
    }

    public boolean addDoc(int docID, String word) {
        if (invertedindexAVL.empty()) {
            invertedindexAVL.insert(word, createTerm(docID, word));
            return true;
        }

        if (invertedindexAVL.find(word)) {
            Term term = invertedindexAVL.retrieve();
            term.adddocID(docID);
            invertedindexAVL.update(term);
            return false; // Word already exists; docID updated
        }

        invertedindexAVL.insert(word, createTerm(docID, word));
        return true; // New word inserted
    }

    public boolean isfound(String word) {
        return invertedindexAVL.find(word);
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
        return isfound(term) ? invertedindexAVL.retrieve().getDocs() : new boolean[50];
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
        return isfound(word) ? invertedindexAVL.retrieve().getDocs() : new boolean[50];
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
