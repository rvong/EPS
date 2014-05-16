package com;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Frequencies2 {
	public static ArrayList<Entry<String, Integer>> top25(ArrayList<String> al) {
		Map<String, Integer> wordFreqs = new HashMap<String, Integer>();
		for (String s : al) {
    		if (wordFreqs.containsKey(s))
    			wordFreqs.put(s, wordFreqs.get(s) + 1);
    		else
    			wordFreqs.put(s, 1);
		}
	    
		ArrayList<Entry<String, Integer>> results = new ArrayList<Entry<String, Integer>>();
	    for (Entry<String, Integer> e : wordFreqs.entrySet())
	    	results.add(e);
	    
		Collections.sort(results, new Comparator<Entry<String, Integer>>() {
			public int compare(Entry<String, Integer> a, Entry<String, Integer> b) {
				return a.getValue().compareTo(b.getValue()) * -1;
			}
	    });
		
	    return results;
	}
}
