package com;

import java.io.*;
import java.lang.reflect.*;
import java.net.*;
import java.util.*;
import java.util.Map.Entry;

public class Nineteen {
	private static String tfWords, tfFreqs;
	
	public static void loadPlugins() {
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream("config.properties"));
			tfWords = prop.getProperty("words");
			tfFreqs = prop.getProperty("frequencies");
		}
		catch (IOException io) { io.printStackTrace(); }
	}
	
	public static void savePlugins() {
		Properties prop = new Properties();
		try {
			prop.setProperty("words", "plugins/Words1.jar");
			prop.setProperty("frequencies", "plugins/Frequencies1.jar");
			prop.store(new FileOutputStream("config.properties"), null);
		}
		catch (IOException io) { io.printStackTrace(); }
	}
	
	public static Object loadClass(String jarPath, String name, String meth, Object arg) throws
				NoSuchMethodException, SecurityException,
				InstantiationException, IllegalAccessException,
				IllegalArgumentException, InvocationTargetException,
				IOException, ClassNotFoundException {
		URL url = new URL("file:" + jarPath);
		URLClassLoader loader = new URLClassLoader(new URL[]{url});
		Class<?> cl = Class.forName(name, false, loader);
		Method p = cl.getDeclaredMethod(meth, Object.class);
		loader.close();
		return p.invoke(cl.newInstance(), arg);
	}
	
	public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException,
							SecurityException, IllegalAccessException, IllegalArgumentException,
							InvocationTargetException, InstantiationException, IOException {
		loadPlugins();
		ArrayList<String> aw = (ArrayList<String>) loadClass(tfWords, tfWords.replace("/",  ".").replace(".jar", ""), "extractWords", args[0]);
		ArrayList<Entry<String, Integer>> freqs = (ArrayList<Entry<String, Integer>>) loadClass(tfFreqs, tfFreqs.replace("/",  ".").replace(".jar", ""), "top25", aw);
		
		// Moved out of freq (works fine Eclipse, err ClassLoader.loadClass(Unknown Source) in cmd line)
		Collections.sort(freqs, new Comparator<Entry<String, Integer>>() {
			public int compare(Entry<String, Integer> a, Entry<String, Integer> b) {
				return a.getValue().compareTo(b.getValue()) * -1;
			}
	    });
	    
		for (int i = 0; i < 25; i++) {
	    	Entry<String, Integer> p = freqs.get(i);
	    	System.out.println(p.getKey() + " - " + p.getValue());
	    }
	}
	
}
