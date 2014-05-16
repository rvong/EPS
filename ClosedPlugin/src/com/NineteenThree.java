package com;

import java.io.*;
import java.lang.reflect.*;
import java.net.*;
import java.util.*;
import java.util.Map.Entry;

public class NineteenThree {
	private static String tfWords, tfFreqs;
	
	public static void loadChoice() {
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream("config.properties"));
			tfWords = prop.getProperty("words");
			tfFreqs = prop.getProperty("frequencies");
		}
		catch (IOException io) { io.printStackTrace(); }
	}
	
	public static void main(String[] args) throws IOException {
		loadChoice();
		ArrayList<String> words = null;
		ArrayList<Entry<String, Integer>> results = null;
		
		if (tfWords.compareTo("Words1") == 0)
			words = Words1.extractWords(args[0]);
		else if (tfWords.compareTo("Words2") == 0)
			words = Words2.extractWords(args[0]);
		else
			System.out.println("Invalid Word Selection");
		
		if (tfFreqs.compareTo("Frequencies1") == 0)
			results = Frequencies1.top25(words);
		else if (tfFreqs.compareTo("Frequencies2") == 0)
			results = Frequencies2.top25(words);
		else
			System.out.println("Invalid Freq Selection");
		
		for (int i = 0; i < 25; i++) {
	    	Entry<String, Integer> p = results.get(i);
	    	System.out.println(p.getKey() + " - " + p.getValue());
	    }
	}
	
}
