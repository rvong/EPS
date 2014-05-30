import java.util.*;
import java.util.Map.Entry;

public class WordFrequencyController extends ActiveWFObject {
	private DataStorageManager dataStorageManager;
	
	public void dispatch(List<Object> message) {
		String cmd = (String) message.get(0);
		if (cmd.equals("run"))
			run(getRest(message));
		else if (cmd.equals("top25"))
			display(getRest(message));
	}

	public void run(List<Object> message) {
		dataStorageManager = (DataStorageManager) message.get(0);
		Main.send(dataStorageManager, msgCreate("send_word_freqs", this));
	}
	
	@SuppressWarnings("unchecked")
	public void display(List<Object> message) {
		List<Entry<String,Integer>> wordFreqs = (ArrayList<Entry<String, Integer>>) message.get(0);
	    for (int i = 0; i < 25; i++) {
	    	Entry<String, Integer> p = wordFreqs.get(i);
	    	System.out.println(p.getKey() + " - " + p.getValue());
	    	Main.send(dataStorageManager,  msgCreate("die", null));
	    	stop = true;
	    }
		
	}
}
