/**
 * @author Richard Vong 65674401
 * @date April 11, 2014
 * Inf 102 Lopes
 * 
 * Homework 2: Exercise 4.1, 4.2
 * Same style in another language + Idempotence
 */
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.Map.Entry;

public class CookbookIdempotent {
	final static int MAX = 25;
	final static String STOP_WORDS = "../stop_words.txt";
	
	static char[] data;
	static Map<String, Integer> wordFreqs = new HashMap<String, Integer>();
	static ArrayList<String> words = new ArrayList<String>();
    static ArrayList<Entry<String, Integer>> sorted = new ArrayList<Entry<String, Integer>>();
	
	// Read file into data String var
	static void readFile(String path) {
		try {
			data = new String(Files.readAllBytes(Paths.get(path))).toCharArray();
		} catch (IOException e) {
			System.out.println("Input file IO Exception");
		}
	}
	
	// Replace all non-alphanumeric chars with whitespace
	static void filterCharsAndNormalize() {
		for (int i = 0; i < data.length; i++) {
			if (! Character.isLetterOrDigit(data[i]))
				data[i] = ' ';
			else
				data[i] = Character.toLowerCase(data[i]);
		}
	}
	
	// Scans data for words
	static void scan() {
		words.clear();
		String strData = String.valueOf(data);
		for (String s : strData.split(" ")) {
			if (s.length() >= 2)
				words.add(s);
		}
	}
	
	static void removeStopWords() {
		try {
			Set<String> setStopWords = new HashSet<String>();
			String stopWords = new String(Files.readAllBytes(Paths.get(STOP_WORDS)));
			for (String s : stopWords.split(","))
				setStopWords.add(s);

			// (4.2 example runs really really slow if done exactly the same way)
			ArrayList<String> newWords = new ArrayList<String>();
			for (String s : words) {
				if (! setStopWords.contains(s))
					newWords.add(s);
			}
			
			words = newWords;
		} catch (IOException e) {
			System.out.println("stop_words.txt IO Exception");
		}
	}
	
	// Counts occurrences, stored as pair of <K, V>
	static void frequencies() {
		wordFreqs.clear();
		for (String s : words) {
    		if (wordFreqs.containsKey(s))
    			wordFreqs.put(s, wordFreqs.get(s) + 1);
    		else
    			wordFreqs.put(s, 1);
		}
	}
	
	// Sorts wordFreqs by frequency
	static void sort() {
		sorted.clear();
	    sorted.addAll(wordFreqs.entrySet());
	    
	    Collections.sort(sorted, new Comparator<Entry<String, Integer>>() {
			@Override
			public int compare(Entry<String, Integer> a, Entry<String, Integer> b) {
				return a.getValue().compareTo(b.getValue()) * -1;	// Descending order
			}
	    });
	}
	
	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println("error: no input file");
			return;
		}
		
		/* Idempotent: Many Repeat Calls Applied = Same Result */
		readFile(args[0]);
		readFile(args[0]);
		filterCharsAndNormalize();
		filterCharsAndNormalize();
		filterCharsAndNormalize();
		scan();
		scan();
		scan();
		removeStopWords();
		removeStopWords();
		frequencies();
		frequencies();
		frequencies();
		sort();
		sort();
		sort();
		
	    for (int i = 0; i < sorted.size() && i < MAX; i++) {
	    	Entry<String, Integer> p = sorted.get(i);
	    	System.out.println(p.getKey() + " - " + p.getValue());
	    }
	}
}
