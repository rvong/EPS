/**
 * @author Richard Vong 65674401
 * @date April 4, 2014
 * Inf 102 Lopes
 * 
 * Homework 1
 */

import java.util.*;
import java.util.Map.Entry;
import java.util.regex.*;
import java.io.*;
import java.nio.file.*;

public class Freestyle {
	final static int MAX = 25;
	final static String STOP_WORDS = "../stop_words.txt";

	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println("error: no input file");
			return;
		}
		String fileName = args[0];
		
		Set<String> setStopWords = new HashSet<String>();
		Map<String, Integer> countWords = new HashMap<String, Integer>();

		try {
			String stopWords = new String(Files.readAllBytes(Paths.get(STOP_WORDS)));
			for (String s : stopWords.split(","))
				setStopWords.add(s);
		} catch (IOException e) {
			System.out.println("stop_words.txt IO Exception");
			return;
		}
		
		try {
			String fileText = new String(Files.readAllBytes(Paths.get(fileName))).toLowerCase();
			Matcher m = Pattern.compile("[a-z]{2,}").matcher(fileText);
			
			while (m.find()) {
				String s = m.group();
	    		if (! setStopWords.contains(s)) {
		    		if (countWords.containsKey(s))
		    			countWords.put(s, countWords.get(s) + 1);
		    		else
		    			countWords.put(s, 1);
	    		}
			}
		} catch (IOException e) {
			System.out.println("Input file IO Exception");
			return;
		}
		
	    ArrayList<Entry<String, Integer>> sortedResults = new ArrayList<Entry<String, Integer>>();
	    sortedResults.addAll(countWords.entrySet());
	    
	    Collections.sort(sortedResults, new Comparator<Entry<String, Integer>>() {
			@Override
			public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
				return o1.getValue().compareTo(o2.getValue()) * -1;	// Descending order
			}
	    });
	    
	    for (int i = 0; i < sortedResults.size() && i < MAX; i++) {
	    	Entry<String, Integer> p = sortedResults.get(i);
	    	System.out.println(p.getKey() + "  -  " + p.getValue());
	    }
	}
	
}

