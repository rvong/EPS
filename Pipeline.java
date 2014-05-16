/**
 * @author Richard Vong 65674401
 * @date April 11, 2014
 * Inf 102 Lopes
 * 
 * Homework 2: Exercise 5.1, 5.2
 * Pipelining in another language + stop_words.txt as 2nd program argument
 */
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.Map.Entry;

public class Five {
	final static int MAX = 25;
	//final static String STOP_WORDS = "../stop_words.txt";
	
	// Read file into data String var
	static String readFile(String path) {
		String result = null;
		try {
			result = new String(Files.readAllBytes(Paths.get(path)));
		} catch (IOException e) {
			System.out.println("Input file IO Exception");
		}
		return result;
	}
	
	// Replace all non-alphanumeric chars with whitespace
	static String filterCharsAndNormalize(String data) {
		return data.toLowerCase().replaceAll("[\\W_]+", " ");
	}
	
	// Scans data for words
	static ArrayList<String> scan(String data) {
		ArrayList<String> result = new ArrayList<String>();
		for (String s : data.split(" ")) {
			if (s.length() >= 2)
				result.add(s);
		}
		return result;
	}
	
	static ArrayList<String> removeStopWords(ArrayList<String> words) {
		ArrayList<String> newWords = new ArrayList<String>();
		try {
			Set<String> setStopWords = new HashSet<String>();
			
			// stop_words.txt path will be the first "word"
			String filePath = words.get(0);
			words.remove(0);
			
			String stopWords = new String(Files.readAllBytes(Paths.get(filePath)));
			for (String s : stopWords.split(","))
				setStopWords.add(s);

			for (String s : words) {
				if (! setStopWords.contains(s))
					newWords.add(s);
			}
		} catch (IOException e) {
			System.out.println("stop_words.txt IO Exception");
		}
		return newWords;
	}
	
	// Counts occurrences, stored as pair of <K, V>
	static Map<String, Integer> frequencies(ArrayList<String> words) {
		Map<String, Integer> wordFreqs = new HashMap<String, Integer>();
		for (String s : words) {
    		if (wordFreqs.containsKey(s))
    			wordFreqs.put(s, wordFreqs.get(s) + 1);
    		else
    			wordFreqs.put(s, 1);
		}
		return wordFreqs;
	}
	
	// Sorts wordFreqs by frequency
	static ArrayList<Entry<String, Integer>> sort(Map<String, Integer> wordFreqs) {
	    ArrayList<Entry<String, Integer>> sorted = new ArrayList<Entry<String, Integer>>();
		sorted.addAll(wordFreqs.entrySet());
	    
	    Collections.sort(sorted, new Comparator<Entry<String, Integer>>() {
			@Override
			public int compare(Entry<String, Integer> a, Entry<String, Integer> b) {
				return a.getValue().compareTo(b.getValue()) * -1;	// Descending order
			}
	    });
	    return sorted;
	}
	
	static void printAll(ArrayList<Entry<String, Integer>> sorted) {
	    for (int i = 0; i < sorted.size() && i < MAX; i++) {
	    	Entry<String, Integer> p = sorted.get(i);
	    	System.out.println(p.getKey() + " - " + p.getValue());
	    }
	}
	
	public static void main(String[] args) {
		if (args.length <= 1) {
			System.out.println("error: missing input file or stop_words.txt args");
			return;
		}
		printAll(sort(frequencies(removeStopWords(scan(args[1] + " " + filterCharsAndNormalize(readFile(args[0])))))));
	}
}
