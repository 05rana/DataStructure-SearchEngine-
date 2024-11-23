package com.mycompany.finalds;

public class RankedInvertedIndex {

    class Frequency {
        int docID = 0;
        int f = 0;
    }

    private LinkedList<TermRank> invertedindex;
    private Frequency[] freqs;

    public RankedInvertedIndex() {
        invertedindex = new LinkedList<>();
        freqs = new Frequency[50];
        initializeFrequencies();
    }



    public boolean addDoc(int docID, String word) {
        if (invertedindex.empty()) {
            invertedindex.insert(createTermRank(docID, word));
            return true;
        }

        invertedindex.findFirst();
        while (true) {
            TermRank currentTerm = invertedindex.retrieve();
            if (currentTerm.word.word.equals(word)) {
                currentTerm.adddocID(docID);
                invertedindex.update(currentTerm);
                return false; // Word already exists; updated docID
            }

            if (invertedindex.last()) break;
            invertedindex.findNext();
        }

        invertedindex.insert(createTermRank(docID, word));
        return true; // New word inserted
    }

    public boolean isfound(String word) {
        if (invertedindex.empty()) return false;

        invertedindex.findFirst();
        while (true) {
            if (invertedindex.retrieve().word.word.equals(word)) return true;

            if (invertedindex.last()) break;
            invertedindex.findNext();
        }

        return false;
    }



    public void TF(String str) {
        str = str.toLowerCase().trim();
        String[] words = str.split(" ");
        initializeFrequencies();

        for (String word : words) {
            if (isfound(word)) {
                int[] docs = invertedindex.retrieve().getDocs();
                for (int j = 0; j < docs.length; j++) {
                    if (docs[j] != 0) {
                        freqs[j].docID = j;
                        freqs[j].f += docs[j];
                    }
                }
            }
        }

        mergesort(freqs, 0, freqs.length - 1);

        System.out.println("\nDocID\tScore");
        for (Frequency freq : freqs) {
            if (freq.f != 0) {
                System.out.println(freq.docID + "\t\t" + freq.f);
            }
        }
    }

    public static void mergesort(Frequency[] A, int l, int r) {
    if (l < r) {
        int m = (l + r) >>> 1; // Faster midpoint calculation (equivalent to (l + r) / 2 without overflow)
        mergesort(A, l, m);    // Sort first half
        mergesort(A, m + 1, r); // Sort second half
        merge(A, l, m, r);      // Merge halves
    }
}

private static void merge(Frequency[] A, int l, int m, int r) {
    int leftSize = m - l + 1; // Size of the left subarray
    int rightSize = r - m;   // Size of the right subarray

    // Temporary arrays for left and right subarrays
    Frequency[] left = new Frequency[leftSize];
    Frequency[] right = new Frequency[rightSize];

    // Copy data to temporary arrays
    System.arraycopy(A, l, left, 0, leftSize);
    System.arraycopy(A, m + 1, right, 0, rightSize);

    int i = 0, j = 0, k = l;

    // Merge left and right subarrays back into the original array
    while (i < leftSize && j < rightSize) {
        A[k++] = (left[i].f >= right[j].f) ? left[i++] : right[j++];
    }

    // Copy any remaining elements from the left subarray
    while (i < leftSize) {
        A[k++] = left[i++];
    }

    // Copy any remaining elements from the right subarray
    while (j < rightSize) {
        A[k++] = right[j++];
    }
}

    // =====================================================================

    private void initializeFrequencies() {
        for (int i = 0; i < freqs.length; i++) {
            freqs[i] = new Frequency();
            freqs[i].docID = i;
            freqs[i].f = 0;
        }
    }

    private TermRank createTermRank(int docID, String word) {
        TermRank term = new TermRank();
        term.setVocabulary(new Vocabulary(word));
        term.adddocID(docID);
        return term;
    }
}
