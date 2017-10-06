package poafs.peer;

import java.net.InetSocketAddress;

public class Peer {
	private InetSocketAddress addr;
	
	private String id;

	public Peer( String id, InetSocketAddress addr) {
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
