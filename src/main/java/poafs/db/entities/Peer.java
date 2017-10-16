package poafs.db.entities;

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
	 * The host this peer can be connected to on.
	 */
	private String host;
	
	/**
	 * The port this peer can be connected to on.
	 */
	private int port;
	
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
	public Peer(String id, String host, int port) {
		this.id = id;
		this.host = host;
		this.port = port;
	}
	
	public Peer() {
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
	
}
