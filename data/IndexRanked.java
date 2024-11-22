
public class IndexRanked {
class frequency
    {
        int docID = 0;
        int f = 0;
    }

    class Document {
            int docID;
            LinkedList <String> index; 

            public Document() {
                docID = 0;
                index = new LinkedList <String>();
            }

            public void addNew (String word)
            {
                index.insert(word);
            }

           public boolean found(String word)
           {
               if (index.empty())
                   return false;

               index.findFirst();
               for ( int i = 0 ; i < index.size ; i++)
               {
                   if ( index.retrieve().compareTo(word) == 0)
                       return true;
                  index.findNext();
               }
               return false;
           }
    }   
    //===========================================================
    
    Document [] indexes;
    frequency [] freqs;

    
    public IndexRanked() {
        freqs = new frequency [50];
        indexes = new Document [50];
        for ( int i = 0 ; i < indexes.length ; i++)
        {
            indexes [i] = new Document();
            indexes [i].docID = i;
        }
    }
        
    public void addDocument ( int docID, String data)
    {
            indexes[docID].addNew(data);
    }
    
    public void printDocment (int docID)
    {
        if ( indexes[docID].index.empty())
            System.out.println("Empty Document");
        else
        {
            indexes[docID].index.findFirst();
            for ( int i = 0; i< indexes[docID].index.size ; i++)
            {
                System.out.print (indexes[docID].index.retrieve() + " ");
                indexes[docID].index.findNext();
            }
        }
    }
//=================================================================
public  boolean [] getDocs (String str)
{
    boolean [] result = new boolean [50];
    for (int i = 0 ; i < result.length ; i++)
        result[i] = false;
    
    for (int i = 0 ; i < result.length ; i++)
        if (indexes[i].found(str))
            result[i] = true;

    return result;
}


//=================================================================
        public void TF(String str)
        {
            str = str.toLowerCase().trim();
            String [] words = str.split(" ");
            freqs = new frequency[50];
            for ( int i = 0 ; i < 50 ; i++ )
            {
                freqs[i] = new frequency();
                freqs[i].docID = i;
                freqs[i].f = 0;
            }
            
            for ( int docs = 0 ; docs <50 ; docs++)
            {
                for ( int i = 0 ; i < words.length ; i++)
                {
                    indexes[docs].index.findFirst();
                    int wordcount = 0;
                    for ( int x = 0 ; x < indexes[docs].index.size() ; x++ )
                    {
                        if (indexes[docs].index.retrieve().compareTo(words[i])==0)
                            wordcount ++;
                        indexes[docs].index.findNext();
                    }
                    
                    if (wordcount > 0)
                        freqs[docs].f += wordcount;
                }
            }
            
            mergesort(freqs, 0, freqs.length-1 );
            
            System.out.println("\nDocIDt\tScore");
            for ( int x = 0 ;  freqs[x].f != 0 ; x++)
                System.out.println(freqs[x].docID + "\t\t" + freqs[x].f);
        }

         //=================================================================
    public static void mergesort ( frequency [] A , int l , int r ) 
    {
        if ( l >= r )
            return;
        int m = ( l + r ) / 2;
        mergesort (A , l , m ) ;          // Sort first half
        mergesort (A , m + 1 , r ) ;    // Sort second half
        merge (A , l , m , r ) ;            // Merge
    }

    private static void merge ( frequency [] A , int l , int m , int r ) 
    {
        frequency [] B = new frequency [ r - l + 1];
        int i = l , j = m + 1 , k = 0;
    
        while ( i <= m && j <= r )
        {
            if ( A [ i ].f >= A [ j ].f)
                B [ k ++] = A [ i ++];
            else
                B [ k ++] = A [ j ++];
        }
        
        if ( i > m )
            while ( j <= r )
                B [ k ++] = A [ j ++];
        else
            while ( i <= m )
                B [ k ++] = A [ i ++];
        
        for ( k = 0; k < B . length ; k ++)
            A [ k + l ] = B [ k ];
    }
    
}