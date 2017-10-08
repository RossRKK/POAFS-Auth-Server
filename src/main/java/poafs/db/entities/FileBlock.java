package poafs.db.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * A class representing a single block of a file on the network.
 * @author rossrkk
 *
 */
@Entity
public class FileBlock {
	/**
	 * The index of this file block.
	 */
	@Id
	private BlockKey id;
	
	/**
	 * A hashmap containing the peers that have a copy of this file block.
	 */
	private HashMap<String, Peer> peers = new HashMap<String, Peer>();
	
	
	public void addPeer(Peer p) {
		peers.put(p.getId(), p);
	}
	
	/* Getters and Setters */
	
	public void removePeer(String id) {
		peers.remove(id);
	}
	
	public Collection<Peer> getPeers() {
		return peers.values();
	}
	
	public int getIndex() {
		return id.getIndex();
	}

	public void setIndex(int index) {
		this.id.setIndex(index);
	}

	public void setPeers(HashMap<String, Peer> peers) {
		this.peers = peers;
	}
}

@Embeddable
class BlockKey implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 8674604801424355356L;

	private int index;
    
    private String parentFile;

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getParentFile() {
		return parentFile;
	}

	public void setParentFile(String parentFile) {
		this.parentFile = parentFile;
	}
    
}
