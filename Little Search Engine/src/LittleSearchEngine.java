package lse;

import java.io.*;
import java.util.*;

/**
 * This class builds an index of keywords. Each keyword maps to a set of pages in
 * which it occurs, with frequency of occurrence in each page.
 *
 */
public class LittleSearchEngine {
	
	/**
	 * This is a hash table of all keywords. The key is the actual keyword, and the associated value is
	 * an array list of all occurrences of the keyword in documents. The array list is maintained in 
	 * DESCENDING order of frequencies.
	 */
	HashMap<String,ArrayList<Occurrence>> keywordsIndex;
	
	/**
	 * The hash set of all noise words.
	 */
	HashSet<String> noiseWords;
	
	/**
	 * Creates the keyWordsIndex and noiseWords hash tables.
	 */
	public LittleSearchEngine() {
		keywordsIndex = new HashMap<String,ArrayList<Occurrence>>(1000,2.0f);
		noiseWords = new HashSet<String>(100,2.0f);
	}
	
	/**
	 * Scans a document, and loads all keywords found into a hash table of keyword occurrences
	 * in the document. Uses the getKeyWord method to separate keywords from other words.
	 * 
	 * @param docFile Name of the document file to be scanned and loaded
	 * @return Hash table of keywords in the given document, each associated with an Occurrence object
	 * @throws FileNotFoundException If the document file is not found on disk
	 */
	public HashMap<String,Occurrence> loadKeywordsFromDocument(String docFile) 
	throws FileNotFoundException {
		/** COMPLETE THIS METHOD **/
		Scanner sc = new Scanner(new File(docFile)); 
		HashMap<String, Occurrence> keyWords = new HashMap<String, Occurrence>();
		while (sc.hasNext()) {
			
			String x = sc.next(); 
			

			if (getKeyword(x) != null) {
				String word = getKeyword(x); 
				if (keyWords.containsKey(word)) {
					keyWords.get(word).frequency++; 
				} else {
					Occurrence occ = new Occurrence(docFile, 1);  
					keyWords.put(word, occ); 
				}
			}
		}
		sc.close();
		
		
		 
		// following line is a placeholder to make the program compile
		// you should modify it as needed when you write your code
		return keyWords;
	}
	
	/**
	 * Merges the keywords for a single document into the master keywordsIndex
	 * hash table. For each keyword, its Occurrence in the current document
	 * must be inserted in the correct place (according to descending order of
	 * frequency) in the same keyword's Occurrence list in the master hash table. 
	 * This is done by calling the insertLastOccurrence method.
	 * 
	 * @param kws Keywords hash table for a document
	 */
	public void mergeKeywords(HashMap<String,Occurrence> kws) {
		/** COMPLETE THIS METHOD **/
		
		for (String p : kws.keySet()) {
			// boolean found = false; 
			
			if (keywordsIndex.containsKey(p)) {
				keywordsIndex.get(p).add(kws.get(p)); 
				insertLastOccurrence(keywordsIndex.get(p)); 
				
			} else {
				ArrayList<Occurrence> occ = new ArrayList<Occurrence>(); 
				occ.add(kws.get(p));  
				keywordsIndex.put(p, occ);
			}
			/*
			for (String f : keywordsIndex.keySet()) {
				found = false; 
				if (p.equals(f)) {
					keywordsIndex.get(f).add(kws.get(p)); 
					insertLastOccurrence(keywordsIndex.get(f)); 
					found = true; 
					break;
				}

			}
			
			if (found == false) {
				ArrayList<Occurrence> occ = new ArrayList<Occurrence>(); 
				occ.add(kws.get(p)); 
				keywordsIndex.put(p, occ);
			}
			*/ 
			 
		}

		
		
	}
	
	/**
	 * Given a word, returns it as a keyword if it passes the keyword test,
	 * otherwise returns null. A keyword is any word that, after being stripped of any
	 * trailing punctuation, consists only of alphabetic letters, and is not
	 * a noise word. All words are treated in a case-INsensitive manner.
	 * 
	 * Punctuation characters are the following: '.', ',', '?', ':', ';' and '!'
	 * 
	 * @param word Candidate word
	 * @return Keyword (word without trailing punctuation, LOWER CASE)
	 */
	public String getKeyword(String word) {
		/** COMPLETE THIS METHOD **/
		word = word.toLowerCase(); 
		if (noiseWords.contains(word)) {
			return null; 
		}
		int lastLetter = word.length()-1; 
		char x = word.charAt(lastLetter); 
		while ((x == '.' || x == ','|| x == '?'|| x == ':'|| x == ';'|| x == '!') && word.length() > 1) {
			word = word.substring(0, lastLetter); 
			lastLetter--; 
			x = word.charAt(lastLetter);
		}
		
		for (int i = 0; i < word.length(); i++) {
			if (Character.isLetter(word.charAt(i)) == false) {
				return null; 
			}
		}
		
		return word.toLowerCase();
	}
	
	/**
	 * Inserts the last occurrence in the parameter list in the correct position in the
	 * list, based on ordering occurrences on descending frequencies. The elements
	 * 0..n-2 in the list are already in the correct order. Insertion is done by
	 * first finding the correct spot using binary search, then inserting at that spot.
	 * 
	 * @param occs List of Occurrences
	 * @return Sequence of mid point indexes in the input list checked by the binary search process,
	 *         null if the size of the input list is 1. This returned array list is only used to test
	 *         your code - it is not used elsewhere in the program.
	 */
	public ArrayList<Integer> insertLastOccurrence(ArrayList<Occurrence> occs) {
		/** COMPLETE THIS METHOD **/
		if (occs.size() <= 1) {
			return null; 
		}
		Occurrence x = occs.get(occs.size()-1); 
		occs.remove(occs.size()-1);
		int l = 0, r = occs.size() - 1;
		ArrayList<Integer> intArray = new ArrayList<Integer>();  
		int m = 0; 
        while (l <= r) {
            m = l + (r-l)/2;
            intArray.add(m); 
 
            // Check if x is present at mid
            if (occs.get(m).frequency == x.frequency)
                break; 
 
            // If x greater, ignore right half
            if (occs.get(m).frequency > x.frequency)
                l = m + 1;
            	
 
            // If x is smaller, ignore left half
            else if (occs.get(m).frequency < x.frequency) {
            	 r = m - 1;
            }
               
        }
       
        int i = m; 
       
      
       
        
        if (i+1 < occs.size()) {
        	
        	 if (x.frequency > occs.get(i+1).frequency) {
             	occs.add(i, x); 
             } else if (x.frequency < occs.get(i+1).frequency) {
             	occs.add(i+1, x);
             } else {
             	occs.add(i, x);
             }
        } else if (i-1 >= 0) {
        	
        	if (x.frequency > occs.get(i).frequency) {
        		
             	occs.add(i, x); 
             } else if (x.frequency < occs.get(i).frequency) {
            	  
             	occs.add(i+1, x);
             } else {
             	occs.add(i, x);
             }
        	
        } else {
        	
        	occs.add(x);
        	if (x.frequency > occs.get(i).frequency) {
        		Occurrence temp = occs.get(0); 
        		occs.set(0, x); 
        		occs.set(1, temp); 
        	} else {
        		Occurrence temp = occs.get(0); 
        		occs.set(1, x); 
        		occs.set(0, temp); 
        	}
        }
       
    	 
    	/*
    	 * i++; 
    	 
    	while (i < occs.size()) {
        	temp = occs.get(i); 
        	occs.set(i, temp); 
        	i++; 
        }
    	
        occs.add(temp); 
        */
        System.out.println(occs);
        System.out.println(intArray);
		// following line is a placeholder to make the program compile
		// you should modify it as needed when you write your code
		return intArray;
	}
	
	/**
	 * This method indexes all keywords found in all the input documents. When this
	 * method is done, the keywordsIndex hash table will be filled with all keywords,
	 * each of which is associated with an array list of Occurrence objects, arranged
	 * in decreasing frequencies of occurrence.
	 * 
	 * @param docsFile Name of file that has a list of all the document file names, one name per line
	 * @param noiseWordsFile Name of file that has a list of noise words, one noise word per line
	 * @throws FileNotFoundException If there is a problem locating any of the input files on disk
	 */
	public void makeIndex(String docsFile, String noiseWordsFile) 
	throws FileNotFoundException {
		// load noise words to hash table
		Scanner sc = new Scanner(new File(noiseWordsFile));
		while (sc.hasNext()) {
			String word = sc.next();
			noiseWords.add(word);
		}
		
		// index all keywords
		sc = new Scanner(new File(docsFile));
		while (sc.hasNext()) {
			String docFile = sc.next();
			HashMap<String,Occurrence> kws = loadKeywordsFromDocument(docFile);
			mergeKeywords(kws);
		}
		sc.close();
	}
	
	/**
	 * Search result for "kw1 or kw2". A document is in the result set if kw1 or kw2 occurs in that
	 * document. Result set is arranged in descending order of document frequencies. (Note that a
	 * matching document will only appear once in the result.) Ties in frequency values are broken
	 * in favor of the first keyword. (That is, if kw1 is in doc1 with frequency f1, and kw2 is in doc2
	 * also with the same frequency f1, then doc1 will take precedence over doc2 in the result. 
	 * The result set is limited to 5 entries. If there are no matches at all, result is null.
	 * 
	 * @param kw1 First keyword
	 * @param kw1 Second keyword
	 * @return List of documents in which either kw1 or kw2 occurs, arranged in descending order of
	 *         frequencies. The result size is limited to 5 documents. If there are no matches, returns null.
	 */
	public ArrayList<String> top5search(String kw1, String kw2) {
		/** COMPLETE THIS METHOD **/
		ArrayList<Occurrence> w1 = new ArrayList<Occurrence>(); 
		ArrayList<Occurrence> w2 = new ArrayList<Occurrence>(); 
		ArrayList<String> searchResult = new ArrayList<String>(); 
		if (keywordsIndex.containsKey(kw1)) {
			w1 = keywordsIndex.get(kw1);
		}
		if (keywordsIndex.containsKey(kw2)) {
			w2 = keywordsIndex.get(kw2);
		}
		int intW1 = 0; 
		int intW2 = 0; 
		int searchInt = 0; 
		for (String name: keywordsIndex.keySet()){

            String key =name.toString();
            String value = keywordsIndex.get(name).toString();  
            System.out.println(key + " " + value);  


		}
		
		while((intW1 < w1.size() && intW2 < w2.size()) && (searchInt <= 5)) {
		
				if ((w1.get(intW1).frequency > w2.get(intW2).frequency)) {
					if (!searchResult.contains(w1.get(intW1).document)) {
						searchResult.add(w1.get(intW1).document); 
						
						searchInt++; 
					}
					intW1++; 
				} else if ((w1.get(intW1).frequency == w2.get(intW2).frequency)) {
				
					if (!searchResult.contains(w1.get(intW1).document)) {
						searchResult.add(w1.get(intW1).document); 
						
						searchInt++; 
					}
					intW1++;  
				} else  {
					if (!searchResult.contains(w2.get(intW2).document)) {
						searchResult.add(w2.get(intW2).document); 
						
						searchInt++; 
					}
					intW2++; 

				}
			
				
		}
		while (intW1 < w1.size() && searchInt <= 5) {
			if (!searchResult.contains(w1.get(intW1).document)) {
				searchResult.add(w1.get(intW1).document); 
				
				searchInt++; 
			}
			intW1++;  
		}
		while (intW2 < w2.size() && searchInt <= 5) {
			if (!searchResult.contains(w2.get(intW2).document)) {
				searchResult.add(w2.get(intW2).document); 
				
				searchInt++; 
			}
			intW2++;  
		}
		
		
		
		
		
		
		System.out.println(searchResult);
		
		if (searchResult.size() > 0) {
			return searchResult;
		} else {
			return null; 
		}
		
	
	}
}