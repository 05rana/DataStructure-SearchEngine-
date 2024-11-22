/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.datasproject;

/**
 *
 * @author 1234
 */
public class Rank {
    Vocabulary word;
    int rank ;

    public Rank() {
        rank = 0;
        word = new Vocabulary("");
    }

    public Rank(String word, int rank) {
        this.word = new Vocabulary(word);
        this.rank = rank ;
    }
    
    public int add_Rank ()
    {
        return ++rank;
    }

    public void setVocabulary(Vocabulary word)
    {
        this. word = word; 
    }
    
    public Vocabulary getVocabulary()
    {
         return word;
    }
    
    public int getRank ()
    {
        return this.rank;
    }
    
    @Override
    public String toString() {
        return "(" + word + ", " + rank + ")" ;
    }
    
    
}