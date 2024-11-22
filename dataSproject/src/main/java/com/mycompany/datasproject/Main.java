/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.datasproject;

import java.util.Scanner;

/**
 *
 * @author 1234
 */
public class Main {
       
     public static Scanner input = new Scanner (System.in);
     public static Search_Engine SE = new Search_Engine();
     
    
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

    public static void Retrieval_Term()
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
        printBoolean(SE.Boolean_Retrieval(str.trim().toLowerCase(), choice1 ));
        System.out.println("\n");

    }
    
    public static void Boolean_Retrieval_menu()
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
        printBoolean(SE.Boolean_Retrieval(str.trim().toUpperCase(), choice1 ));
        System.out.println("\n");
    
    }

    public static void Ranked_Retrieval_menu()
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
                SE.Ranked_Index(str);
                break;
            case 2:
                System.out.println("get ranked from inverted index list");
                SE.Ranked_RetrievalInvertedIndex(str);
                break;
            case 3:
                System.out.println("get ranked from BST");
                SE.Ranked_RetrievalBST(str);
                break;
            case 4:
                System.out.println("get ranked from AVL");
                SE.Ranked_RetrievalAVL(str);
                break;
        }
        System.out.println("\n");
    }
    
    public static void Indexed_Documents_menu()
    {
        System.out.println("######## Indexed Documents ######## ");
        System.out.println("Indexed Documents " );
        SE.Indexed_Documents();
        System.out.println("");
    }
    
    public static void Indexed_Tokens_menu()
    {
        System.out.println("######## Indexed Tokens ######## ");
        System.out.println("tokens " );
        SE.Indexed_Tokens();
        System.out.println("");
    }
    
    public static void main(String[] args) {

        SE.LoadData("C:\\Users\\Majdi\\Downloads\\data\\stop.txt", "C:\\Users\\Majdi\\Downloads\\data\\dataset.CSV");

        // TODO code application logic here
        int choice;
        
        do {
                choice = menu();
                switch (choice)
                {
                    //term Retrieval
                    case 1:
                            Retrieval_Term();
                            break;

                    //Boolean Retrieval: to enter a Boolean query that will return a set of unranked documents  
                    case 2:
                            Boolean_Retrieval_menu();
                            break;
                            
                    //Ranked Retrieval: to enter a query that will return a ranked list of documents with their scores 
                    case 3:
                            Ranked_Retrieval_menu();
                            break;
                    
                    //Indexed Documents: to show number of documents in the index 
                    case 4:
                            Indexed_Documents_menu();
                            break;
                    
                    //Indexed Tokens: to show number of vocabulary and tokens in the index  
                    case 5:
                            Indexed_Tokens_menu();
                            break;
                     
                    case 6:
                            break;
                            
                    default:       
                            System.out.println("bad choice, try again!");
                }
        } while (choice != 6);
    }
    
}