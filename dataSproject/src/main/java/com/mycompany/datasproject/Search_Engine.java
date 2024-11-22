package com.mycompany.datasproject;

import java.io.File;
import java.util.Scanner;

public class Search_Engine {
    int tokens = 0;
    int vocab = 0;
    
    index index;
    Inverted_Index invertedindex;
    Inverted_Index_BST invertedindexBST;
    Inverted_Index_AVL invertedindexAVL;
    
    IndexRanked  indexranked;
    Inverted_IndexRanked  invertedindexranked;
    BSTranked  invertedindexBSTranked;
    AVLranked  invertedindexAVLranked;
    
    /*=================================================================================
    constractor 
    */
    public Search_Engine ()
    {
        index = new index();
        invertedindex = new Inverted_Index();
        invertedindexBST = new Inverted_Index_BST ();
        invertedindexAVL = new Inverted_Index_AVL();
        
        indexranked = new IndexRanked();
        invertedindexranked = new Inverted_IndexRanked();
        invertedindexBSTranked = new BSTranked();
        invertedindexAVLranked = new AVLranked();
    }
    
    /*=================================================================================
    Document Processing: 
    o Read documents from a CSV file. 
    o Split the text into a list of words based on whitespace. 
    o Convert all text to lowercase 
    o Preprocess the text by removing stop-words (e.g., "the," "is," "and"). The list of 
    stop-words will be given to you. 
    o Remove all punctuations and non-alphanumerical characters 
    o Proceed to build the index     
     */
    public void LoadData (String stopFile, String fileName)
    {
            try{
                File stopfile = new File (stopFile);
                Scanner reader = new Scanner (stopfile).useDelimiter("\\Z");
                String stops = reader.next();
                
                stops = stops.replaceAll("\n", " ");
                
                File docsfile = new File(fileName);
                Scanner reader1 = new Scanner (docsfile);
                String line = reader1.nextLine();
                
                for ( int lineID = 0 ; lineID <50 ; lineID ++ ) 
                {
                    line = reader1.nextLine().toLowerCase();
                    
                    int pos = line.indexOf(',');
                    int docID = Integer.parseInt( line .substring(0, pos));

                    String data = line.substring(pos+1, line.length() - pos).trim();
                    data = data.substring(0, data.length() - 1);

                    data = data.toLowerCase();
                    data =  data.replaceAll("[\']", " ");
                    data = data.replaceAll("[^a-zA-Z0-9]", " ").trim() ;

                    String [] words = data.split(" "); // --1

                    for (int i = 0; i < words.length ; i++)
                    {
                        String word = words[i].trim(); //--2
                
                        if ( word.compareToIgnoreCase("") != 0)
                            tokens ++;

                        if ( ! stops.contains(word + " ")) //--3
                        { 
                            this.index.addDocument(docID, word);
                            this.invertedindex.addNew(docID, word);
                            this.invertedindexBST.addNew(docID, word);
                            this.invertedindexAVL.addNew(docID, word);

                            this.indexranked.addDocument(docID, word);
                            this.invertedindexranked.addNew(docID, word);
                            this.invertedindexBSTranked.addNew(docID, word);
                            this.invertedindexAVLranked.addNew(docID, word);
                        }
                    }

                    //this.index.printDocment(docID);
                    //this.indexranked.printDocment(docID);
                }
                //this.invertedindex.printDocment();
                //this.invertedindexBST.printDocument();
                //this.invertedindexAVL.printDocument();
                
                //this.invertedindexranked.printDocment();
                //this.invertedindexBSTranked.printDocument();
                //this.invertedindexAVLranked.printDocument();
                
                vocab = invertedindexAVL.invertedindexAVL.size();
      
                System.out.println("tokens " + tokens);
                System.out.println("vocabs " + vocab);
                
                reader.close();
                reader1.close();
            }
            catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
    }
    
    public boolean []  Boolean_Retrieval(String str , int DSType)
    {
        boolean [] docs = new boolean [50] ;
        for ( int i = 0 ; i < docs.length ; i++)
            docs[i] = false;
  
        switch (DSType)
        {
            case 1 :
                System.out.println(" Boolean_Retrieval using index list");
                docs = this.index.AND_OR_Function(str);
                break;
            case 2:    
                System.out.println(" Boolean_Retrieval using inverted index list");
                docs = this.invertedindex.AND_OR_Function(str);
                break;
            case 3:
                System.out.println(" Boolean_Retrieval using BST");
                docs = this.invertedindexBST.AND_OR_Function(str);
                break;
            case 4:
                System.out.println(" Boolean_Retrieval using AVL");
                docs = this.invertedindexAVL.AND_OR_Function(str);
                break;
            default :
                System.out.println("Bad data structure");
                
        }
        return docs;
    }
        
    public void Ranked_Index(String str)
    {
        this.indexranked.TF(str);
    }

    public void Ranked_RetrievalInvertedIndex(String str)
    {
        this.invertedindexranked.TF(str);
    }

    public void Ranked_RetrievalBST(String str)
    {
        this.invertedindexBSTranked.TF(str);
    }

    public void Ranked_RetrievalAVL(String str)
    {
        this.invertedindexAVLranked.TF(str);
    }
    
    public boolean []  Term_Retrieval(String str , int DSType)
    {
        boolean [] docs = new boolean [50] ;
        for ( int i = 0 ; i < docs.length ; i++)
            docs[i] = false;

        switch (DSType)
        {
            case 1 :
                docs = index.getDocs(str);
                break;
            case 2 :
               if (invertedindex.found(str))
                    docs = invertedindex.invertedindex.retrieve().getDocs();
                break;
             case 3:
                if (invertedindexBST.found(str))
                    docs = invertedindexBST.invertedindexBST.retrieve().getDocs();
                break;
            case 4:
                if (invertedindexAVL.found(str))
                    docs = invertedindexAVL.invertedindexAVL.retrieve().getDocs();
                break;
            default :
                System.out.println("Bad data structure");
        }
        return docs;
    }
    
    public void Indexed_Documents()
    {
        System.out.println("All Documents with the number of words in them ");
        for ( int i = 0 ; i < 50 ; i++ )
        {
            int size = index.indexes[i].index.size();
            System.out.println("Document# " + i +"  with size(" +  size + ")"  );
        }
        
    }
    
    public void Indexed_Tokens()
    {
        System.out.println("All tokens with the documents appear in it ");
        invertedindexBST.invertedindexBST.PrintData();
        invertedindexAVL.invertedindexAVL.PrintData();
    }
}