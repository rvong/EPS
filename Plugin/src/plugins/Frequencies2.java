package plugins;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Frequencies2 {
	public ArrayList<Entry<String, Integer>> top25(Object al) {
		Map<String, Integer> wordFreqs = new HashMap<String, Integer>();
		for (String s : (ArrayList<String>) al) {
    		if (wordFreqs.containsKey(s))
    			wordFreqs.put(s, wordFreqs.get(s) + 1);
    		else
    			wordFreqs.put(s, 1);
		}
	    
		ArrayList<Entry<String, Integer>> results = new ArrayList<Entry<String, Integer>>();
	    for (Entry<String, Integer> e : wordFreqs.entrySet())
	    	results.add(e);
	    
	    return results;
	}
}
