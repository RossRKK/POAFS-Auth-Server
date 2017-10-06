package poafs.file;

import java.util.Collection;
import java.util.HashMap;

import poafs.peer.Peer;

/**
 * A class representing a single block of a file on the network.
 * @author rossrkk
 *
 */
public class FileBlock {
	/**
	 * The index of this file block.
	 */
	private int index;
	
	/**
	 * A hashmap containing the peers that have a copy of this file block.
	 */
	private HashMap<String, Peer> peers = new HashMap<String, Peer>();
	
	public Collection<Peer> getPeers() {
		return peers.values();
	}
	
	public int getIndex() {
		return index;
	}
	
	public void addPeer(Peer p) {
		peers.put(p.getId(), p);
	}
	
	public void removePeer(String id) {
		peers.remove(id);
	}
}
