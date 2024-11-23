package com.mycompany.finalds;

public class Rank {
    private Vocabulary word; // Encapsulated for better data protection
    private int rank;

    // Default constructor
    public Rank() {
        this("", 0);
    }

    // Parameterized constructor
    public Rank(String word, int rank) {
        this.word = new Vocabulary(word);
        this.rank = rank;
    }

    // Increment rank and return the new rank
    public int addRank() {
        return ++rank;
    }

    // Setter for Vocabulary
    public void setVocabulary(Vocabulary word) {
        this.word = word;
    }

    // Getter for Vocabulary
    public Vocabulary getVocabulary() {
        return word;
    }

    // Getter for rank
    public int getRank() {
        return rank;
    }

    // Override toString for clean output
    @Override
    public String toString() {
        return "(" + word + ", " + rank + ")";
    }
}
