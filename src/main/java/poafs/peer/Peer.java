package poafs.peer;

import java.net.InetSocketAddress;
import java.util.HashMap;

public class Peer {
	
	private static HashMap<String, Peer> register = new HashMap<String, Peer>();
	
	public static void registerPeer(Peer p) {
		register.put(p.id, p);
	}
	public static Peer getPeer(String id) {
		return register.get(id);
	}
	
	private InetSocketAddress addr;
	
	private String id;

	public Peer(String id, InetSocketAddress addr) {
		this.addr = addr;
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public InetSocketAddress getAddress() {
		return addr;
	}
	
	public void setAddress(InetSocketAddress addr) {
		this.addr = addr;
	}
}
