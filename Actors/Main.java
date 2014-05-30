import java.util.*;

public class Main {
	public static List<Object> msgCreate(String cmd, Object o) {
		List<Object> msg = new ArrayList<Object>();
		msg.add(cmd);
		msg.add(o);
		return msg;
	}
	
	public static List<Object> msgCreate(String cmd, Object o, Object o2) {
		List<Object> msg = new ArrayList<Object>();
		msg.add(cmd);
		msg.add(o);
		msg.add(o2);
		return msg;
	}
	
	public static void send(ActiveWFObject receiver, List<Object> message) {
		receiver.addMsg(message);
	}
	
	public static void main(String[] args) {
		WordFrequencyManager wordFreqManager = new WordFrequencyManager();
		
		StopWordManager stopWordManager = new StopWordManager();
		send(stopWordManager, msgCreate("init", wordFreqManager));
		
		DataStorageManager storageManager = new DataStorageManager();
		send(storageManager, msgCreate("init", args[0], stopWordManager));
		
		WordFrequencyController wfController = new WordFrequencyController();
		Main.send(wfController, msgCreate("run", storageManager));
		
		try {
			wordFreqManager.join();
			stopWordManager.join();
			storageManager.join();
			wfController.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
