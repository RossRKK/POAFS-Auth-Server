package poafs.db.entities;

import java.net.InetSocketAddress;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
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
	 * This peers keys.
	 */
	@Column(name = "encryption_keys", length = 2048)
	private KeyPair keys;

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

	public void addBlock(FileBlock block) {
		blocks.add(block);
	}

	public KeyPair getKeys() {
		return keys;
	}

	public void setBlocks(Collection<FileBlock> blocks) {
		this.blocks = blocks;
	}
	public void setKeys(KeyPair keys) {
		this.keys = keys;
	}

	public PrivateKey getPrivateKey() {
		return keys.getPrivate();
	}
	
	public PublicKey getPublicKey() {
		return keys.getPublic();
	}
}
