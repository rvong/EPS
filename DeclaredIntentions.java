import java.util.*;
import java.util.Map.Entry;
import java.util.regex.*;
import java.io.*;
import java.nio.file.*;

/**
 *	Java: Accept and Return types are built-in.
 */

public class TwentyThree {
	public static List<String> extractWords(String path) throws IOException {
		Set<String> setStop = new HashSet<String>();
		String txtStop = new String(Files.readAllBytes(Paths.get("../stop_words.txt")));
		for (String s : txtStop.split(",")) setStop.add(s);
		
		String fileText = new String(Files.readAllBytes(Paths.get(path))).toLowerCase();
		Matcher m = Pattern.compile("[a-z]{2,}").matcher(fileText);
		List<String> words = new LinkedList<String>();
		
		while (m.find()) {
			String s = m.group();
    		if (! setStop.contains(s)) words.add(s);
		}
		return words;
	}
	
	public static Map<String, Integer> frequencies(List<String> words) {
		Map<String, Integer> wordFreqs = new HashMap<String, Integer>();
		
		for (String s : words) {
    		if (wordFreqs.containsKey(s))
    			wordFreqs.put(s, wordFreqs.get(s) + 1);
    		else
    			wordFreqs.put(s, 1);
		}
		return wordFreqs;
	}
	
	public static ArrayList<Entry<String, Integer>> sort(Map<String, Integer> wordFreqs) {
	    ArrayList<Entry<String, Integer>> sorted = new ArrayList<Entry<String, Integer>>();
	    sorted.addAll(wordFreqs.entrySet());
	    
	    Collections.sort(sorted, new Comparator<Entry<String, Integer>>() {
			@Override
			public int compare(Entry<String, Integer> a, Entry<String, Integer> b) {
				return a.getValue().compareTo(b.getValue()) * -1;
			}
	    });
	    
	    return sorted;
	}

	public static void main(String[] args) throws IOException {
		ArrayList<Entry<String, Integer>> wordFreqs = sort(frequencies(extractWords(args[0])));
	    
	    for (int i = 0; i < 25; i++) {
	    	Entry<String, Integer> p = wordFreqs.get(i);
	    	System.out.println(p.getKey() + " - " + p.getValue());
	    }
	}
}

