import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class StopWordManager extends ActiveWFObject {
	private Set<String> stopWords = new HashSet<String>();
	private WordFrequencyManager wordFreqManager;
	
	public void dispatch(List<Object> message) throws IOException {
		String cmd = (String) message.get(0);
		if (cmd.equals("init"))
			init(getRest(message));
		else if (cmd.equals("filter"))
			filter(getRest(message));
		else
			Main.send(wordFreqManager, message);
	}
	
	public void init(List<Object> message) throws IOException {
		String txtStop = new String(Files.readAllBytes(Paths.get("../stop_words.txt")));
		for (String s : txtStop.split(",")) stopWords.add(s);
		wordFreqManager = (WordFrequencyManager) message.get(0);
	}
	
	public void filter(List<Object> message) {
		String word = (String) message.get(0);
		if (! stopWords.contains(word))
			Main.send(wordFreqManager, msgCreate("word", word));
	}
}
