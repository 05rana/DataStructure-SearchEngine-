package com.mycompany.finalds;

public class RankedIndex {

    // Nested class for frequency
    class Frequency {
        int docID;
        int f;

        public Frequency(int docID, int f) {
            this.docID = docID;
            this.f = f;
        }

        public Frequency() {
            this(0, 0);
        }
    }

    // Nested class for document representation
    class Document {
        int docID;
        LinkedList<String> index;

        public Document() {
            this.docID = 0;
            this.index = new LinkedList<>();
        }

        public void add(String word) {
            index.insert(word);
        }

        public boolean ISfound(String word) {
            if (index.empty()) return false;

            index.findFirst();
            while (true) {
                if (index.retrieve().equals(word)) return true;
                if (index.last()) break;
                index.findNext();
            }
            return false;
        }
    }

    private Document[] indexes;
    private Frequency[] freqs;

    public RankedIndex() {
        freqs = new Frequency[50];
        indexes = new Document[50];
        for (int i = 0; i < indexes.length; i++) {
            indexes[i] = new Document();
            indexes[i].docID = i;
            freqs[i] = new Frequency(i, 0);
        }
    }

    public void addDoc(int docID, String data) {
        indexes[docID].add(data);
    }



    public void TF(String str) {
        str = str.toLowerCase().trim();
        String[] words = str.split(" ");

        resetFreqs();

        for (int docID = 0; docID < 50; docID++) {
            for (String word : words) {
                calcFreq(docID, word);
            }
        }

        mergesort(freqs, 0, freqs.length - 1);

        System.out.println("\nDocID\tScore");
        for (Frequency freq : freqs) {
            if (freq.f > 0) {
                System.out.println(freq.docID + "\t\t" + freq.f);
            }
        }
    }

    private void resetFreqs() {
        for (int i = 0; i < freqs.length; i++) {
            freqs[i].f = 0;
        }
    }

    private void calcFreq(int docID, String word) {
        indexes[docID].index.findFirst();
        int wordCount = 0;

        while (true) {
            if (indexes[docID].index.retrieve().equals(word)) {
                wordCount++;
            }
            if (indexes[docID].index.last()) break;
            indexes[docID].index.findNext();
        }

        freqs[docID].f += wordCount;
    }

public static void mergesort(Frequency[] A, int l, int r) {
    if (l >= r) {
        return;
    }

    int m = l + (r - l) / 2; // Prevents potential overflow for large indices
    mergesort(A, l, m);      // Sort first half
    mergesort(A, m + 1, r);  // Sort second half
    merge(A, l, m, r);       // Merge
}

private static void merge(Frequency[] A, int l, int m, int r) {
    int n1 = m - l + 1;      // Size of left subarray
    int n2 = r - m;          // Size of right subarray

    Frequency[] left = new Frequency[n1];
    Frequency[] right = new Frequency[n2];

    // Copy data to temporary arrays
    System.arraycopy(A, l, left, 0, n1);
    System.arraycopy(A, m + 1, right, 0, n2);

    int i = 0, j = 0, k = l;

    // Merge temporary arrays back into A
    while (i < n1 && j < n2) {
        if (left[i].f >= right[j].f) {
            A[k++] = left[i++];
        } else {
            A[k++] = right[j++];
        }
    }

    // Copy remaining elements of left, if any
    while (i < n1) {
        A[k++] = left[i++];
    }

    // Copy remaining elements of right, if any
    while (j < n2) {
        A[k++] = right[j++];
    }
}

}
