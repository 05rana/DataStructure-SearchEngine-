/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.datasproject;

/**
 *
 * @author 1234
 */
public class TermRank {
    Vocabulary word;
    int  [] docIDS ;

    public TermRank() {
        docIDS = new int [50];
        for (int i = 0 ; i < docIDS.length ; i++)
            docIDS [i] = 0;
        word = new Vocabulary("");
    }

    public TermRank(String word, int [] docIDS) {
        this.word = new Vocabulary(word);
        this.docIDS = new int [docIDS.length];
        for (int i = 0 ; i < docIDS.length ; i++)
            this.docIDS [i] = docIDS[i];

    }
    
    public void addDocID ( int docID)
    {
        this.docIDS[docID] ++;
    }

    public void setVocabulary(Vocabulary word)
    {
        this. word = word; 
    }
    
    public Vocabulary getVocabulary()
    {
         return word;
    }
    
    public int [] getDocs ()
    {
        int [] test = new int [docIDS.length];
        for ( int i = 0 ; i < test.length ; i++)
            test[i] = docIDS[i];
        return test;
    }
    
    @Override
    public String toString() {
        String docs = "";
        for (int i = 0, j = 0 ; i < docIDS.length; i++)
            if ( j == 0 && docIDS [i] != 0 )
            {
                docs += " " + String.valueOf(i) ;
                j++;
            }
            else if ( docIDS [i] != 0 )
            {
                docs += ", " + String.valueOf(i) ;
                j++;
            }
        
        return word + "[" + docs + ']';
    }
    
    
}