package com.mycompany.datasproject;
public class Term {
    Vocabulary word;
    boolean [] docIDS ;

    public Term() {
        docIDS = new boolean [50];
        for (int i = 0 ; i < docIDS.length ; i++)
            docIDS [i] = false;
        word = new Vocabulary("");
    }

    public Term(String word, boolean [] docIDS) {
        this.word = new Vocabulary(word);
        this.docIDS = new boolean [docIDS.length];
        for (int i = 0 ; i < docIDS.length ; i++)
            this.docIDS [i] = docIDS[i];

    }
    
    public boolean add_docID ( int docID)
    {
        if (! docIDS[docID])
        {
            this.docIDS[docID] = true;
            return true;
        }
        return false;
    }

    public void setVocabulary(Vocabulary word)
    {
        this. word = word; 
    }
    
    public Vocabulary getVocabulary()
    {
         return word;
    }
    
    public boolean [] getDocs ()
    {
        boolean [] test = new boolean [docIDS.length];
        for ( int i = 0 ; i < test.length ; i++)
            test[i] = docIDS[i];
        return test;
    }
    
    @Override
    public String toString() {
        String docs = "";
        for (int i = 0, j = 0 ; i < docIDS.length; i++)
            if ( j == 0 && docIDS [i]==true )
            {
                docs += " " + String.valueOf(i) ;
                j++;
            }
            else if ( docIDS [i]==true )
            {
                docs += ", " + String.valueOf(i) ;
                j++;
            }
        
        return word + "[" + docs + ']';
    }
    
    
}

