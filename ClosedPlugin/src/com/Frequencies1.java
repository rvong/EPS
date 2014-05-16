package com;

import java.util.*;
import java.util.Map.Entry;

public class Frequencies1 {
	public static ArrayList<Entry<String, Integer>> top25(ArrayList<String> al) {
		Map<String, Integer> wordFreqs = new HashMap<String, Integer>();
		for (String s : al) {
    		if (wordFreqs.containsKey(s))
    			wordFreqs.put(s, wordFreqs.get(s) + 1);
    		else
    			wordFreqs.put(s, 1);
		}
	    
		ArrayList<Entry<String, Integer>> results = new ArrayList<Entry<String, Integer>>();
	    results.addAll(wordFreqs.entrySet());

		Collections.sort(results, new Comparator<Entry<String, Integer>>() {
			public int compare(Entry<String, Integer> a, Entry<String, Integer> b) {
				return a.getValue().compareTo(b.getValue()) * -1;
			}
	    });
	    
	    return results;
	}
}
