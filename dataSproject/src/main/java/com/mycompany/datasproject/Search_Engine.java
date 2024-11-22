/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.datasproject;

/**
 *
 * @author 1234
 */
import java.io.File;
import java.util.Scanner;

public class Search_Engine {
    // Tracks the total number of tokens and vocabulary size
    int tokens = 0;
    int vocab = 0;
    
    // Index and inverted index objects for various types of indexes
    index index;
    Inverted_Index invertedindex;
    Inverted_Index_BST invertedindexBST;
    Inverted_Index_AVL invertedindexAVL;
    
    
    // These are commented out but could be used for ranked indexes
    IndexRanked indexranked;
    Inverted_IndexRanked invertedindexranked;
    Inverted_Index_BSTRanked invertedindexBSTranked;
    Inverted_Index_AVLRanked invertedindexAVLranked;
    
    
    // Constructor initializing different types of indexes
    public Search_Engine () {
        index = new index();
        invertedindex = new Inverted_Index();
        invertedindexBST = new Inverted_Index_BST();
        invertedindexAVL = new Inverted_Index_AVL();

        
        /*
        // These are commented out but could be used for ranked indexes
        indexranked = new IndexRanked();
        invertedindexranked = new Inverted_IndexRanked();
        invertedindexBSTranked = new Inverted_Index_BSTRanked();
        invertedindexAVLranked = new Inverted_Index_AVLRanked(); 
        */
    }
    
    // Loads data from the stopwords and document files, processes the documents
    public void LoadData(String stopFile, String fileName) {
        try {
            // Reading stop words
            File stopfile = new File(stopFile);
            Scanner reader = new Scanner(stopfile).useDelimiter("\\Z");
            String stops = reader.next();
            stops = stops.replaceAll("\n", " ");
            
            // Reading documents file
            File docsfile = new File(fileName);
            Scanner reader1 = new Scanner(docsfile);
            String line = reader1.nextLine();
            
            // Processing each document (up to 50 documents)
            for (int lineID = 0; lineID < 50; lineID++) {
                line = reader1.nextLine().toLowerCase();
                
                // Parsing document ID and content
                int pos = line.indexOf(',');
                int docID = Integer.parseInt(line.substring(0, pos));
                String data = line.substring(pos + 1, line.length() - pos).trim();
                data = data.substring(0, data.length() - 1);
                data = data.toLowerCase();
                data = data.replaceAll("[']", "");
                data = data.replaceAll("[^a-zA-Z0-9]", " ").trim();
                
                // Splitting the document content into words
                String[] words = data.split(" "); // --1
                
                for (int i = 0; i < words.length; i++) {
                    String word = words[i].trim(); //--2
                    
                    // If word is non-empty, increment token count
                    if (word.compareToIgnoreCase("") != 0) {
                        tokens++;
                    }
                    
                    // If word is not a stop word, add it to the index
                    if (!stops.contains(word + " ")) { //--3
                        this.index.addDocument(docID, word);
                        this.invertedindex.addNew(docID, word);
                        this.invertedindexBST.addNew(docID, word);
                        this.invertedindexAVL.addNew(docID, word);
                        
                        /*
                        // These are commented out but could be used for ranked indexes
                        this.indexranked.addDocument(docID, word);
                        this.invertedindexranked.addNew(docID, word);
                        this.invertedindexBSTranked.addNew(docID, word);
                        this.invertedindexAVLranked.addNew(docID, word);
                        */
                    }
                }
                
                //this.index.printDocment(docID);
                //this.indexranked.printDocment(docID);
            }
            
            // Printing documents (optional, commented out)
            //this.invertedindex.printDocment();
            //this.invertedindexBST.printDocument();
            //this.invertedindexAVL.printDocument();
            
            //this.invertedindexranked.printDocment();
            //this.invertedindexBSTranked.printDocument();
            //this.invertedindexAVLranked.printDocument();
            
            // Setting vocabulary size and printing token/vocab counts
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
    
    // Performs Boolean retrieval based on the query and data structure type (DSType)
    public boolean[] Boolean_Retrieval(String str, int StructureType) {
        boolean[] docs = new boolean[50];
        
        // Initializing docs array to false
        for (int i = 0; i < docs.length; i++) {
            docs[i] = false;
        }
        
        // Selecting the appropriate data structure for Boolean retrieval
        switch (StructureType) {
            case 1:
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
            default:
                System.out.println("Bad data structure");
        }
        return docs;
    }
    

    // These are commented out but could be used for ranked index retrieval
    public void Ranked_Index(String str) {
        this.IndexRanked.TF(str);
    }

    public void Ranked_RetrievalInvertedIndex(String str) {
        this.Inverted_IndexRanked.TF(str);
    }

    public void Ranked_RetrievalBST(String str) {
        this.BSTranked.TF(str);
    }

    public void Ranked_RetrievalAVL(String str) {
        this.AVLranked.TF(str);
    }
    
}
