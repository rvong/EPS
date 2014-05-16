package plugins;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class Words2 {
	public ArrayList<String> extractWords(Object path) throws IOException {
		Set<String> setStop = new HashSet<String>();
		String txtStop = new String(Files.readAllBytes(Paths.get("../stop_words.txt")));
		for (String s : txtStop.split(","))
			setStop.add(s);
		
		String fileText = new String(Files.readAllBytes(Paths.get((String) path))).toLowerCase();
		String words[] = fileText.replaceAll("[\\W_]+", " ").split(" ");
		ArrayList<String> results = new ArrayList<String>();
		for (String s : words) {
			if (s.length() >= 2 && ! setStop.contains(s))
				results.add(s);
		}
		return results;
	}
}
