/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.finalds;

/**
 *
 * @author 1234
 */
import java.util.Scanner;


/**
 *
 * @author Manal Alhihi
 */
public class Main {
       
     public static Scanner input = new Scanner (System.in);
     public static SearchEngine SE = new SearchEngine();
     
    
    /**
     * @param args the command line arguments
     */
    
    public static int menu()
    {
        System.out.println("1. Term Retrieval. ");
        System.out.println("2. Boolean Retrieval. ");
        System.out.println("3. Ranked Retrieval.");
        System.out.println("4. Indexed Documents.");
        System.out.println("5. Indexed Tokens.");
        System.out.println("6. Exist.");
        
        System.out.println("enter choice");
        int choice = input.nextInt();
        return choice;
    }

    public static void printBoolean(boolean [] result)
    {
        Term t = new Term ("", result);
        System.out.println(t);
    }

    public static void RetrievalTerm()
    {
        int choice1 ;
        System.out.println("################### Retrieval Term ####################");
        
        System.out.println("1. index");
        System.out.println("2. inverted index");
        System.out.println("3. inverted index using BST");
        System.out.println("4. inverted index using AVL");
        System.out.println("enter your choice");
        choice1 = input.nextInt();
        
        System.out.println("Enter Term :");
        String str = "";
        str = input.next();
        
        System.out.print("Result doc IDs: ");
        printBoolean(SE.TermRetrieval(str.trim().toLowerCase(), choice1 ));
        System.out.println("\n");

    }
    
    public static void Booleanmenu()
    {
        System.out.println("################### Boolean Retrieval ####################");
        System.out.println("1. index");
        System.out.println("2. inverted index");
        System.out.println("3. inverted index using BST");
        System.out.println("4. inverted index using AVL");
        System.out.println("enter your choice");
        int choice1 = input.nextInt();

        System.out.println("Enter boolean term ( AND / OR) : ");
        String str = input.nextLine();
        str = input.nextLine();
            
        System.out.print("Q#: ");
        System.out.println(str);

        System.out.print("Result doc IDs: ");
        printBoolean(SE.BooleanRetrieval(str.trim().toUpperCase(), choice1 ));
        System.out.println("\n");
    
    }

    public static void RankedMenu()
    {
        System.out.println("######## Ranked Retrieval ######## ");
        System.out.println("1. index");
        System.out.println("2. inverted index");
        System.out.println("3. inverted index using BST");
        System.out.println("4. inverted index using AVL");
        System.out.println("enter your choice");
        int choice1 = input.nextInt();

        System.out.print("enter term: ");
        String str = input.nextLine();
        str = input.nextLine();

        System.out.println("## Q: " + str);
        System.out.println("DocIDt\tScore");
        switch (choice1)
        {
            case 1:
                System.out.println("get ranked from index list");
                SE.RankedIndex(str);
                break;
            case 2:
                System.out.println("get ranked from inverted index list");
                SE.RankedRetrievalInvertedIndex(str);
                break;
            case 3:
                System.out.println("get ranked from BST");
                SE.RankedRetrievalBST(str);
                break;
            case 4:
                System.out.println("get ranked from AVL");
                SE.RankedRetrievalAVL(str);
                break;
        }
        System.out.println("\n");
    }
    
    public static void DocsMenu()
    {
        System.out.println("######## Indexed Documents ######## ");
        System.out.println("Indexed Documents " );
        SE.IndexedDocuments();
        System.out.println("");
    }
    
    public static void IndexedTokensmenu()
    {
        System.out.println("######## Indexed Tokens ######## ");
        System.out.println("tokens " );
        SE.IndexedTokens();
                
        System.out.println("");
    }
   
    public static void main(String[] args) {

        SE.ReadData("stop.txt", "dataset.CSV");

        // TODO code application logic here
        int choice;
        
        do {
                choice = menu();
                switch (choice)
                {
                    //term Retrieval
                    case 1:
                            RetrievalTerm();
                            break;

                    //Boolean Retrieval: to enter a Boolean query that will return a set of unranked documents  
                    case 2:
                            Booleanmenu();
                            break;
                            
                    //Ranked Retrieval: to enter a query that will return a ranked list of documents with their scores 
                    case 3:
                            RankedMenu();
                            break;
                    
                    //Indexed Documents: to show number of documents in the index 
                    case 4:
                            DocsMenu();
                            break;
                    
                    //Indexed Tokens: to show number of vocabulary and tokens in the index  
                    case 5:
                            IndexedTokensmenu();
                            break;
                     
                    case 6:
                            break;
                            
                    default:       
                            System.out.println("bad choice, try again!");
                }
        } while (choice != 6);
    }
    
}
