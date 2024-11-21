package com.mycompany.datasproject;

import java.util.LinkedList;

public class Word {
    String text;
    LinkedList<Integer> doc_IDS;

    public Word(String w) {
        text = w;
        doc_IDS = new LinkedList<Integer>();
    }

    public void add_Id(int id) {
        if (!existsIn_doc_IDS(id)) {
            doc_IDS.add(id);
        }
    }

    public void display() {
        System.out.println("\n----------------------------");
        System.out.print("word: " + text);
        System.out.print("[");
        for (Integer id : doc_IDS) {
            System.out.print(id + " ");
        }
        System.out.println("]");
    }

    public boolean existsIn_doc_IDS(Integer id) {
        if (doc_IDS.isEmpty()) return false;
        for (Integer docId : doc_IDS) {
            if (docId.equals(id)) {
                return true;
            }
        }
        return false;
    }
}