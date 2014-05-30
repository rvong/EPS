import java.util.*;
import java.util.Map.Entry;

public class WordFrequencyManager extends ActiveWFObject {
	private Map<String, Integer> freqs;
	
	WordFrequencyManager() { freqs = new HashMap<String, Integer>(); }
	
	public void dispatch(List<Object> message) {
		String cmd = (String) message.get(0);
		if (cmd.equals("word"))
			incrementCount(getRest(message));
		else if (cmd.equals("top25"))
			top25(getRest(message));
	}
	
	public void incrementCount(List<Object> message) {
		String w = (String) message.get(0);
		if (freqs.containsKey(w))
			freqs.put(w, freqs.get(w) + 1);
		else
			freqs.put(w, 1);
	}
	
	public void top25(List<Object> message) {
	    ArrayList<Entry<String, Integer>> sorted = new ArrayList<Entry<String, Integer>>();
	    sorted.addAll(freqs.entrySet());

	    Collections.sort(sorted, new Comparator<Entry<String, Integer>>() {
			@Override
			public int compare(Entry<String, Integer> a, Entry<String, Integer> b) {
				return a.getValue().compareTo(b.getValue()) * -1;
			}
	    });
	    Main.send((ActiveWFObject) message.get(0), msgCreate("top25", sorted));
	}
}
