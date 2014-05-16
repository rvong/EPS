package com;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;

public class Words1 {
	public static ArrayList<String> extractWords(String path) throws IOException {
		Set<String> setStop = new HashSet<String>();
		String txtStop = new String(Files.readAllBytes(Paths.get("../stop_words.txt")));
		for (String s : txtStop.split(","))
			setStop.add(s);
		
		String fileText = new String(Files.readAllBytes(Paths.get(path))).toLowerCase();
		Matcher m = Pattern.compile("[a-z]{2,}").matcher(fileText);
		
		ArrayList<String> al = new ArrayList<String>();
		while (m.find()) {
			String s = m.group();
			if (! setStop.contains(s))
				al.add(m.group());
		}
		
		return al;
	}
}
