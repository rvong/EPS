import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataStorageManager extends ActiveWFObject {
	private StopWordManager stopWordManager;
	private String data;
	
	public void dispatch(List<Object> message) throws IOException {
		String cmd = (String) message.get(0);
		if (cmd.equals("init"))
			init(getRest(message));
		else if (cmd.equals("send_word_freqs"))
			processWords(getRest(message));
		else
			Main.send(stopWordManager, message);
	}
	
	public void init(List<Object> message) throws IOException {		
		String path = (String) message.get(0);
		stopWordManager = (StopWordManager) message.get(1);
		data = new String(Files.readAllBytes(Paths.get(path))).toLowerCase();
	}
	
	public void processWords(List<Object> message) {
		Matcher m = Pattern.compile("[a-z]{2,}").matcher(data);
		List<String> words = new ArrayList<String>();
		while (m.find())
			words.add(m.group());

		for (String w : words)
			Main.send(stopWordManager, msgCreate("filter", w));

		Main.send(stopWordManager,  msgCreate("top25", message.get(0)));
	}
}
