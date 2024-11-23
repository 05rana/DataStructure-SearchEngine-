package com.mycompany.finalds;

import java.io.File;
import java.util.Scanner;

public class SearchEngine {
    private int tokens = 0;
    private int vocab = 0;

    private final Index index;
    private final InvertedIndex invertedindex;
    private final InvertedIndexBST invertedindexBST;
    private final InvertedIndexAVL invertedindexAVL;

    private final RankedIndex indexranked;
    private final RankedInvertedIndex invertedindexranked;
    private final RankedInvertedIndexBST invertedindexBSTranked;
    private final RankedInvertedIndexAVL invertedindexAVLranked;

    // Constructor
    public SearchEngine() {
        this.index = new Index();
        this.invertedindex = new InvertedIndex();
        this.invertedindexBST = new InvertedIndexBST();
        this.invertedindexAVL = new InvertedIndexAVL();

        this.indexranked = new RankedIndex();
        this.invertedindexranked = new RankedInvertedIndex();
        this.invertedindexBSTranked = new RankedInvertedIndexBST();
        this.invertedindexAVLranked = new RankedInvertedIndexAVL();
    }

    // Document Processing: Load data from CSV and process it
public void ReadData(String stopFile, String fileName) {
    try {
        // Read stop words
        File stopFileObj = new File(stopFile);
        Scanner stopReader = new Scanner(stopFileObj).useDelimiter("\\Z");
        String stops = stopReader.next().replaceAll("\n", " ").toLowerCase();

        // Read document file
        File docFile = new File(fileName);
        Scanner docReader = new Scanner(docFile);
        String line = docReader.nextLine(); // Skip header

        for (int lineID = 0; lineID < 50; lineID++) {
            if (!docReader.hasNextLine()) {
                System.err.println("Missing lines in file, filling with empty documents.");
                break; // Exit loop if there are no more lines
            }

            line = docReader.nextLine().toLowerCase();

            // Extract docID and content
            int pos = line.indexOf(',');
            if (pos == -1) {
                System.err.println("Skipping malformed line: " + line);
                continue;
            }

            int docID = Integer.parseInt(line.substring(0, pos));
            String data = line.substring(pos + 1).trim();

            // Preprocess content
            data = data.replaceAll("[']", " "); // Replace apostrophes
            data = data.replaceAll("[^a-zA-Z0-9]", " ").trim(); // Remove non-alphanumeric
            String[] words = data.split(" ");

            // Process words
            for (String word : words) {
                word = word.trim();
                if (word.isEmpty()) continue;

                tokens++; // Count tokens

                // Add to index only if not a stop word
                if (!stops.contains(word + " ")) {
                    index.addDoc(docID, word);
                    invertedindex.addDoc(docID, word);
                    invertedindexBST.addDoc(docID, word);
                    invertedindexAVL.addDoc(docID, word);

                    indexranked.addDoc(docID, word);
                    invertedindexranked.addDoc(docID, word);
                    invertedindexBSTranked.addDoc(docID, word);
                    invertedindexAVLranked.addDoc(docID, word);
                }
            }
        }

        // Update vocab and print stats
        vocab = invertedindexAVL.size();
        System.out.println("tokens: " + tokens);
        System.out.println("vocab: " + vocab);

    } catch (Exception ex) {
        System.err.println("Error while loading data: " + ex.getMessage());
    }
}


    // Boolean Retrieval
    public boolean[] BooleanRetrieval(String str, int DSType) {
        boolean[] docs = new boolean[50];
        switch (DSType) {
            case 1:
                System.out.println("Boolean Retrieval using index list");
                docs = index.AndOrFunc(str);
                break;
            case 2:
                System.out.println("Boolean Retrieval using inverted index list");
                docs = invertedindex.AndOrFunc(str);
                break;
            case 3:
                System.out.println("Boolean Retrieval using BST");
                docs = invertedindexBST.AndOrFunc(str);
                break;
            case 4:
                System.out.println("Boolean Retrieval using AVL");
                docs = invertedindexAVL.AndOrFunc(str);
                break;
            default:
                System.err.println("Invalid data structure type");
        }
        return docs;
    }

    // Ranked Retrieval
    public void RankedIndex(String str) {
        indexranked.TF(str);
    }

    public void RankedRetrievalInvertedIndex(String str) {
        invertedindexranked.TF(str);
    }

    public void RankedRetrievalBST(String str) {
        invertedindexBSTranked.TF(str);
    }

    public void RankedRetrievalAVL(String str) {
        invertedindexAVLranked.TF(str);
    }

    // Term Retrieval
    public boolean[] TermRetrieval(String str, int DSType) {
        boolean[] docs = new boolean[50];
        switch (DSType) {
            case 1:
                docs = index.getDocs(str);
                break;
            case 2:
                if (invertedindex.isfound(str)) {
                    docs = invertedindex.invertedindex.retrieve().getDocs();
                }
                break;
            case 3:
                if (invertedindexBST.isfound(str)) {
                    docs = invertedindexBST.invertedindexBST.retrieve().getDocs();
                }
                break;
            case 4:
                if (invertedindexAVL.isfound(str)) {
                    docs = invertedindexAVL.invertedindexAVL.retrieve().getDocs();
                }
                break;
            default:
                System.err.println("Invalid data structure type");
        }
        return docs;
    }

    // Print indexed documents
    public void IndexedDocuments() {
        System.out.println("All Documents with the number of words in them:");
        for (int i = 0; i < 50; i++) {
            int size = index.indexes[i].index.size();
            System.out.println("Document# " + i + "  with size(" + size + ")");
        }
    }

    // Print indexed tokens
    public void IndexedTokens() {
        System.out.println("All tokens with the documents they appear in:");
        invertedindexBST.invertedindexBST.PrintData();
        invertedindexAVL.invertedindexAVL.PrintData();
    }

    // Helper methods
    private String loadFileContents(String filePath) throws Exception {
        Scanner reader = new Scanner(new File(filePath)).useDelimiter("\\Z");
        String content = reader.hasNext() ? reader.next() : "";
        reader.close();
        return content;
    }

    private String[] preprocessData(String data, String stops) {
        data = data.replaceAll("[^a-zA-Z0-9']", " ").toLowerCase();
        String[] words = data.split("\\s+");
        for (int i = 0; i < words.length; i++) {
            if (stops.contains(words[i] + " ")) {
                words[i] = ""; // Remove stop words
            }
        }
        return words;
    }

    private void processWords(int docID, String[] words) {
        for (String word : words) {
            if (!word.isEmpty()) {
                tokens++;
                index.addDoc(docID, word);
                invertedindex.addDoc(docID, word);
                invertedindexBST.addDoc(docID, word);
                invertedindexAVL.addDoc(docID, word);

                indexranked.addDoc(docID, word);
                invertedindexranked.addDoc(docID, word);
                invertedindexBSTranked.addDoc(docID, word);
                invertedindexAVLranked.addDoc(docID, word);
            }
        }
    }
}
