import java.io.IOException;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

public abstract class ActiveWFObject extends Thread {
	@SuppressWarnings("unused")
	private String name;
	private Queue<List<Object>> queue;
	protected boolean stop;

	public ActiveWFObject() {
		name = this.getClass().getSimpleName();
		queue = new LinkedBlockingQueue<List<Object>>();
		stop = false;
		this.start();
	}

	public static List<Object> msgCreate(String cmd, Object o) {
		List<Object> msg = new ArrayList<Object>();
		msg.add(cmd);
		msg.add(o);
		return msg;
	}
	
	public List<Object> getRest(List<Object> message) {
		return message.subList(1, message.size());
	}
	
	public void addMsg(List<Object> message) { queue.add(message); }
	public List<Object> getMsg() { return queue.poll(); }
	
	public void run() {
		while (! stop){
			List<Object> message = queue.poll();
			try {
				if (message != null) {
					dispatch(message);
					String cmd = (String) message.get(0);
					if (cmd.equals("die")) stop = true;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public abstract void dispatch(List<Object> message) throws IOException;
}
