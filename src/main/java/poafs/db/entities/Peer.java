package poafs.db.entities;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

/**
 * A class that represents a peer on the network.
 * @author rossrkk
 *
 */
@Entity
public class Peer {
	@Id
	/**
	 * The id of this peer.
	 */
	private String id;

	/**
	 * The inet address that this peer can be reached at.
	 */
	private InetSocketAddress address;
	
	/**
	 * The file blocks this peer has access to.
	 */
	@ManyToMany(mappedBy = "peers")
	private Collection<FileBlock> blocks = new ArrayList<FileBlock>();

	/**
	 * Create a new peer.
	 * @param id The id of the peer.
	 * @param addr The address of the peer.
	 */
	public Peer(String id, InetSocketAddress addr) {
		this.address = addr;
		this.id = id;
	}
	
	public Peer() {
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public InetSocketAddress getAddress() {
		return address;
	}
	
	public void setAddress(InetSocketAddress addr) {
		this.address = addr;
	}

	public Collection<FileBlock> getBlocks() {
		return blocks;
	}

	public void setBlocks(List<FileBlock> blocks) {
		this.blocks = blocks;
	}
	
	public void addBlock(FileBlock block) {
		blocks.add(block);
	}
}
